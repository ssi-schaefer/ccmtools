/* CCM Tools : Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * copyright (c) 2002, 2003 Salomon Automation
 *
 * $Id$
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

import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MUnionFieldDef;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GraphTraverserImpl
    implements GraphTraverser
{
    private Set handlers = null;

    /**
     * A CCM MOF graph traverser similar in pattern to the SAX XML parser.
     *
     * This class is designed to traverse a CORBA Component Model (CCM) Meta
     * Object Framework (MOF) library graph object. It is conceptually similar
     * to the SAX XML parsers: This class only performs the task of traversing
     * the graph. It sends node traversal events to an object derived from the
     * NodeHandler class to perform task-specific actions with each node.
     */
    public GraphTraverserImpl()
    { handlers = new HashSet(); }

    /**
     * A CCM MOF graph traverser similar in pattern to the SAX XML parser.
     *
     * This class is designed to traverse a CORBA Component Model (CCM) Meta
     * Object Framework (MOF) library graph object. It is conceptually similar
     * to the SAX XML parsers: This class only performs the task of traversing
     * the graph. It sends node traversal events to an object derived from the
     * NodeHandler class to perform task-specific actions with each node.
     *
     * @param h an object to handle node events.
     */
    public GraphTraverserImpl(NodeHandler h)
    { handlers = new HashSet(); addHandler(h); }

    /**
     * Get the node handler objects for this traverser.
     *
     * @return a set of NodeHandler objects currently used by this traverser.
     */
    public Set getHandlers()
    { return handlers; }

    /**
     * Add a node handler object for this traverser.
     *
     * @param h a NodeHandler object to assign to this traverser.
     */
    public void addHandler(NodeHandler h)
    { handlers.add(h); }

    /**
     * Remove a node handler object from this traverser.
     *
     * @param h a NodeHandler object to remove from this traverser. This
     *        function does nothing if h is not currently a handler.
     */
    public void removeHandler(NodeHandler h)
    { if (handlers.contains(h)) handlers.remove(h); }

    /**
     * Traverse the subgraph starting at the given node.
     *
     * @param node a node to use for starting traversal.
     */
    public void traverseGraph(MContained node)
    {
        if (handlers.size() > 0) {
            for (Iterator h = handlers.iterator(); h.hasNext(); )
                ((NodeHandler) h.next()).startGraph();
            traverseRecursive(node, "", new HashSet());
            for (Iterator h = handlers.iterator(); h.hasNext(); )
                ((NodeHandler) h.next()).endGraph();
        } else {
            throw new RuntimeException(
                "No node handler objects are available for traversal");
        }
    }

    /**
     * Recursively examine a subgraph centered around the given node. Repeat
     * visits are prevented by keeping a list of already visited nodes.
     *
     * @param node A node object to use for starting traversal.
     * @param context The context of the current node, represented as a string
     *                containing names of parent nodes joined using double
     *                colons (::).
     * @param visited The nodes already visited in the graph. This is intended
     *                to prevent visiting a node more than once when the graph
     *                contains cycles.
     */
    private void traverseRecursive(Object node,
                                   String context,
                                   Set visited)
    {
        if (node == null) {
            return;
        }

        // this type check comb is silly.

        String id = null;
        if (node instanceof MContained) {
            id = ((MContained) node).getIdentifier();
        } else if (node instanceof MFieldDef) {
            id = ((MFieldDef) node).getIdentifier();
        } else if (node instanceof MParameterDef) {
            id = ((MParameterDef) node).getIdentifier();
        } else if (node instanceof MUnionFieldDef) {
            id = ((MUnionFieldDef) node).getIdentifier();
        }

        if (id == null) {
            throw new RuntimeException("Node "+node+" in context "+context+
                                       " has no identifier");
        }

        // nodes are identified by their scope identifier. we should change this
        // eventually to use the absoluteName attribute.

        String scope_id = new String(context + "::" + id);
        if (scope_id.startsWith("::")) {
            scope_id = scope_id.substring(2);
        }

        if (visited.contains(scope_id)) {
            return;
        } else {
            visited.add(scope_id);
        }

        for (Iterator h = handlers.iterator(); h.hasNext(); )
            ((NodeHandler) h.next()).startNode(node, scope_id);

        List node_children = processNodeData(node);

        for (Iterator i = node_children.iterator(); i.hasNext(); ) {
            traverseRecursive(i.next(), scope_id, visited);
        }

        for (Iterator h = handlers.iterator(); h.hasNext(); )
            ((NodeHandler) h.next()).endNode(node, scope_id);
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
     * @param node A node to process.
     * @return A set containing all children of the input node that are lists,
     *         collections, or sets themselves. It doesn't make much sense to
     *         process these children since they do not represent one single
     *         data element, so they will be recursively processed by the
     *         calling function.
     */
    private List processNodeData(Object node)
    {
        List children = new ArrayList();

        Method[] node_methods = node.getClass().getMethods();

        for (int i = 0; i < node_methods.length; i++) {
            String method_name = node_methods[i].getName();
            String method_type = node_methods[i].getReturnType().getName();

            if ((node_methods[i].getParameterTypes().length > 0) ||

                // only look at data retrieval functions.

                ((! method_name.startsWith("get")) &&
                 (! method_name.startsWith("is"))) ||

                // we don't need reflection for this task.

                method_name.equals("getClass") ||

                // we don't want to process the member children of MEnumDef,
                // since they're simple string constants and don't have
                // identifiers.

                ((node instanceof MEnumDef) && method_name.equals("getMembers")) ||

                // we don't want to visit the homes during a visit to the
                // component. the homes will get their own chance as
                // self-standing members of the corba community, er, graph.

                method_name.equals("getHomes")) {

                continue;
            }

            Object value = null;
            Method access = null;

            // the field_id is the capitalized name of the corresponding data
            // field in the class. it's used to fill out template information.

            String field_id = method_name.substring(2);

            try {
                if (! method_type.endsWith("boolean")) {
                    field_id = field_id.substring(1);
                }

                value = node_methods[i].invoke(node, null);
            } catch (IllegalAccessException e) {
                continue;
            } catch (InvocationTargetException e) {
                continue;
            }

            if (method_type.endsWith("List")) {
                children.addAll((List) value);
            } else if (method_type.endsWith("Set")) {
                children.addAll((Set) value);
            } else {
                for (Iterator h = handlers.iterator(); h.hasNext(); )
                    ((NodeHandler) h.next()).handleNodeData(method_type,
                                                            field_id, value);
            }
        }

        return children;
    }
}

