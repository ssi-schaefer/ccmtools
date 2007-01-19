/* CCM Tools : Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2002, 2003 Salomon Automation
 *
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package ccmtools.CodeGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ccmtools.parser.idl.metamodel.BaseIDL.MContained;
import ccmtools.parser.idl.metamodel.BaseIDL.MEnumDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MFieldDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MModuleDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MParameterDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MUnionFieldDef;

/**
 * A CCM MOF graph traverser similar in pattern to the SAX XML parser.
 * 
 * This class is designed to traverse a CORBA Component Model (CCM) Meta Object
 * Framework (MOF) library graph object. It is conceptually similar to the SAX
 * XML parsers: This class only performs the task of traversing the graph. It
 * sends node traversal events to an object derived from the NodeHandler class
 * to perform task-specific actions with each node.
 */
public class CcmGraphTraverser 
	implements GraphTraverser
{
    private List handlers = null;

    public CcmGraphTraverser()
    {
        handlers = new ArrayList();
    }

    /**
     * Get the node handler objects for this traverser.
     * 
     * @return The NodeHandler objects currently used by this traverser.
     */
    public List getHandlers()
    {
        return handlers;
    }

    /**
     * Add a node handler object to this traverser.
     * 
     * @param h
     *            A NodeHandler object to assign to this traverser.
     */
    public void addHandler(NodeHandler h)
    {
        handlers.add(h);
    }

    /**
     * Remove the given node handler object from this traverser.
     * 
     * @param h
     *            A NodeHandler object to remove from this traverser.
     */
    public void removeHandler(NodeHandler h)
    {
        if(handlers.contains(h)) 
        {
            handlers.remove(h);
        }
    }

    /**
     * Traverse the subgraph starting at the given node.
     * 
     * @param node
     *            A node to use for starting traversal.
     */
    public void traverseGraph(MContained node)
    {
        if(handlers.size() > 0) 
        {
        	// Call startGraph() on each NodeHandler
            for(Iterator i = handlers.iterator(); i.hasNext();) 
            {
                NodeHandler nh = (NodeHandler) i.next();
                nh.startGraph();
            }
            
            traverseRecursive(node, "", new HashSet());

            // Call endGraph() on each NodeHandler
            for(Iterator i = handlers.iterator(); i.hasNext();) 
            {
                NodeHandler nh = (NodeHandler) i.next();
                nh.endGraph();
            }
        }
        else 
        {
            throw new RuntimeException("No node handler objects are available "
                                       	+ "for traversing a graph");
        }
    }

    /**
     * Recursively examine a subgraph centered around the given node. Repeat
     * visits are prevented by keeping a list of already visited nodes.
     * 
     * @param node
     *            A node object to use for starting traversal.
     * @param context
     *            The context of the current node, represented as a string
     *            containing names of parent nodes joined using double colons
     *            (::).
     * @param visited
     *            The nodes already visited in the graph. This is intended to
     *            prevent visiting a node more than once when the graph contains
     *            cycles.
     */
    private void traverseRecursive(Object node, String context, Set visited)
    {
        if(node == null)
        {
            return;
        }
        
        // this type check comb is silly.

        String id = null;
        if(node instanceof MContained) 
        {
            id = ((MContained) node).getIdentifier();
        }
        else if(node instanceof MFieldDef) 
        {
            id = ((MFieldDef) node).getIdentifier();
        }
        else if(node instanceof MParameterDef) 
        {
            id = ((MParameterDef) node).getIdentifier();
        }
        else if(node instanceof MUnionFieldDef) 
        {
            id = ((MUnionFieldDef) node).getIdentifier();
        }

        if(id == null)
        {
            throw new RuntimeException("Node " + node + " in context "
                    + context + " has no identifier");
        }
        
        // nodes are identified by their scope identifier. we should change this
        // eventually to use the absoluteName attribute, but that's more of a
        // parser issue.

        String scopeId = context + "::" + id;
        if(scopeId.startsWith("::"))
        {
            scopeId = scopeId.substring(2);
        }
        
        if(visited.contains(scopeId)) 
        {
            // There can be many modules with the same name !!!
            if(!(node instanceof MModuleDef)) 
                return;
        }
        else 
        {
            visited.add(scopeId);
        }
        
        // Call startNode() on each registered NodeHandler
        for(Iterator i = handlers.iterator(); i.hasNext();) 
        {
            NodeHandler nh = (NodeHandler) i.next();
            nh.startNode(node, scopeId);
        }
        
        // Call this method for each child of the current node
        List children = processNodeData(node);        
        for(Iterator i = children.iterator(); i.hasNext();) 
        {
            traverseRecursive(i.next(), scopeId, visited);
        }
        
        // Call endNode() on each registered NodeHandler
        for(Iterator i = handlers.iterator(); i.hasNext();) 
        {
            NodeHandler nh = (NodeHandler) i.next();
            nh.endNode(node, scopeId);
        }
    }

    /**
     * Visit all data fields in this node. Add node children (of type Set or
     * List) to a list of node children and return the list after processing
     * non-list data ; the traverser will process list children after this
     * function returns.
     * 
     * A lot of the java version of this part came from an online tutorial :
     * http://java.sun.com/docs/books/tutorial/reflect/class/getMethods.html
     * 
     * @param node
     *            A node to process.
     * @return A set containing all children of the input node that are lists,
     *         collections, or sets themselves. It doesn't make much sense to
     *         process these children since they do not represent one single
     *         data element, so they will be recursively processed by the
     *         calling function.
     */
    private List processNodeData(Object node)
    {
        List children = new ArrayList();

        Method[] methods = node.getClass().getMethods();

        for(int i = 0; i < methods.length; i++) {
            String name = methods[i].getName();
            String type = methods[i].getReturnType().getName();

            if((methods[i].getParameterTypes().length > 0) ||

            // only look at data retrieval functions.

                    ((!name.startsWith("get")) && (!name.startsWith("is"))) ||

                    // we don't need reflection for this task.

                    name.equals("getClass") ||

                    // we don't want to process the member children of MEnumDef,
                    // since they're simple string constants and don't have
                    // identifiers.

                    ((node instanceof MEnumDef) && name.equals("getMembers")) ||

                    // we don't want to visit the homes (or bases) during a
                    // visit to
                    // components (or interfaces). the homes will get their own
                    // chance as self-standing members of the corba community,
                    // er,
                    // graph.

                    name.equals("getHomes") || name.equals("getBases"))

                continue;

            Object value = null;

            // the field is the capitalized name of the corresponding data
            // field in the class. it's used to fill out template information.

            String field = name.substring(2);
            if(!type.endsWith("oolean"))
                field = field.substring(1);

            try 
            {
                value = methods[i].invoke(node, (Object[])null);
            }
            catch(IllegalAccessException e) {
                continue;
            }
            catch(InvocationTargetException e) {
                continue;
            }

            if(type.endsWith("List"))
            {
                children.addAll((List) value);
            }
            else if(type.endsWith("Set"))
            {
                children.addAll((Set) value);
            }
            else
            {
                for(Iterator x = handlers.iterator(); x.hasNext();)
                {
                    ((NodeHandler) x.next()).handleNodeData(type, field, value);
                }
            }
        }
        return children;
    }
}

