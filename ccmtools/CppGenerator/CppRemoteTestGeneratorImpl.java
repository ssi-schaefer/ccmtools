/* CCM Tools : C++ Code Generator Library
 * Egon Teiniker <egon.teiniker@tugraz.at>
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

package ccmtools.CppGenerator;

import ccmtools.utils.Debug;

import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;

import ccmtools.Metamodel.BaseIDL.*;
import ccmtools.Metamodel.ComponentIDL.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class CppRemoteTestGeneratorImpl
    extends CppGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.

    private final static String[] local_output_types =
    {
        "MComponentDef"
    };


    public CppRemoteTestGeneratorImpl(Driver d, File out_dir)
        throws IOException
    {
        super("CppRemoteTest", d, out_dir, local_output_types, null, null);

	Debug.println(Debug.METHODS,"CppRemoteTestGeneratorImpl(" 
		      + d + ", " + out_dir + ")");

    }


    /**
     * Acknowledge the start of the given node during graph traversal. If the
     * node is a MContainer type and is not defined in anything, assume it's the
     * global parse container, and push "CCM_Local" onto the namespace stack,
     * indicating that this code is for local CCM components.
     *
     * @param node the node that the GraphTraverser object is about to
     *        investigate.
     * @param scope_id the full scope identifier of the node. This identifier is
     *        a string containing the names of parent nodes, joined together
     *        with double colons.
     */
    public void startNode(Object node, String scope_id)
    {
        super.startNode(node, scope_id);

	Debug.println(Debug.METHODS,"startNode(" + node + ", " + scope_id + ")");

        if ((node instanceof MContainer) &&
            (((MContainer) node).getDefinedIn() == null))
            namespace.push("CCM_Remote");
    }


    /**
     * Overwrites the CppGenerator's method to change between CCM_Local
     * CCM_Remote.
     */ 
    protected String handleNamespace(String data_type, String local)
    {
	Debug.println(Debug.METHODS,"CppRemoteTestGenerator.handleNamespace(" + 
		      data_type + ", " + local + ")");

        List names = new ArrayList(namespace);

	// ShortNamespace corresponds with the module hierarchy in the IDL file,
	// there is no CCM_Local, CCM_Remote or CCM_Session_ included.
	if (data_type.equals("ShortNamespace")) {
            return join("::", slice(names, 1));
        } 

	if (!local.equals("")) { 
	    names.add("CCM_Session_" + local); 
	}

        if (data_type.equals("Namespace")) {
            return join("::", slice(names, 1));
        } 
	else if (data_type.equals("FileNamespace")) {
            return join("_", slice(names, 1));
        } 
	else if (data_type.equals("IdlFileNamespace")) {
	    if(names.size() > 2) {
		List IdlFileList = new ArrayList(names.subList(2, names.size()-1));
		if(IdlFileList.size() > 0)
		    return join("_", IdlFileList) + "_";
		else
		    return "";
	    }
	    else
		return "";
        } 
	else if (data_type.equals("IncludeNamespace")) {
            return join("/", slice(names, 1));
        } 
	else if (data_type.equals("UsingNamespace")) {
            List tmp = new ArrayList();
            for (Iterator i = names.iterator(); i.hasNext(); )
                tmp.add("using namespace "+i.next()+";\n");
            return join("", tmp);
        } 
	else if (data_type.equals("OpenNamespace")) {
            List tmp = new ArrayList();
            for (Iterator i = names.iterator(); i.hasNext(); )
                tmp.add("namespace "+i.next()+" {\n");
            return join("", tmp);
        } 
	else if (data_type.equals("CloseNamespace")) {
            Collections.reverse(names);
            List tmp = new ArrayList();
            for (Iterator i = names.iterator(); i.hasNext(); )
                tmp.add("} // /namespace "+i.next()+"\n");
	    return join("", tmp);
        }
        return "";
    }


    /**
     * Write generated code to an output file.
     *
     * @param template the template object to get the generated code structure
     *        from ; variable values should come from the node handler object.
     */
    public void writeOutput(Template template)
        throws IOException
    {
	Debug.println(Debug.METHODS,"writeOutput(" + template + ")");

        String generated_code = template.substituteVariables(output_variables);

        if (generated_code.trim().equals("")) return;

        String node_name = ((MContained) current_node).getIdentifier();
        String file_dir = "CCM_Test";
        String file_name = "_check_" +
            handleNamespace("FileNamespace", node_name) + "_remote.cc";

        writeFinalizedFile(file_dir, file_name, generated_code);
	
	// generate an empty Makefile.py in the CCM_Test
	// directory - needed by Confix
	writeFinalizedFile(file_dir, "Makefile.py", "");
    }


    /**
     * Finalize the output files. This function's implementation does nothing;
     * it serves only to override the inherited function from CppGeneratorImpl.
     *
     * @param defines a map of environment variables and their associated
     *        values. This usually contains things like the package name,
     *        version, and other generation info.
     * @param files a list of the filenames (usually those that were provided to
     *        the generator front end).
     */
    public void finalize(Map defines, List files) 
    { 
	Debug.println(Debug.METHODS,"finalize(" + defines + ", " + files + ")");

	return; 
    }


    /**************************************************************************/

    protected Map getTwoStepOperationVariables(MOperationDef operation,
                                               MContained container)
    {
        String lang_type = getLanguageType(operation);
        Map vars = new Hashtable();

        vars.put("Object",              container.getIdentifier());
        vars.put("Identifier",          operation.getIdentifier());
        vars.put("LanguageType",        lang_type);
        vars.put("MExceptionDefThrows", getOperationExcepts(operation));
        vars.put("MParameterDefAll",    getOperationParams(operation));
        vars.put("MParameterDefName",   getOperationParamNames(operation));

        if (! lang_type.equals("void")) vars.put("Return", "return ");
        else                            vars.put("Return", "");

        return vars;
    }

    protected String data_MComponentDef(String data_type, String data_value)
    {
        MComponentDef component = (MComponentDef) current_node;
        MHomeDef home = null;

        try {
            home = (MHomeDef) component.getHomes().iterator().next();
            if (home == null) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("Component '"+component.getIdentifier()+
                                       "' does not have exactly one home.");
        }
	List HomeScope = getScope((MContained)home);
	List ComponentScope = getScope((MContained)component);

        if (data_type.equals("HomeType")) {
	    return home.getIdentifier();	   
        }
	else if (data_type.equals("IdlHomeType")) {
	    if(HomeScope.size() > 0)
		return "::" + join("::", HomeScope) + "::" + home.getIdentifier();
	    else
		return "::" + home.getIdentifier();
        }
	else if(data_type.equals("Identifier")) {
	    return component.getIdentifier();
	}
	else if(data_type.equals("IdlIdentifier")) {
	    if(ComponentScope.size() > 0)
		return "::" + join("::", ComponentScope) + "::" + component.getIdentifier();
	    else
		return "::" + component.getIdentifier();
	}
        return super.data_MComponentDef(data_type, data_value);
    }

    protected String data_MProvidesDef(String data_type, String data_value)
    {
	MInterfaceDef iface = ((MProvidesDef) current_node).getProvides();
	MComponentDef component = ((MProvidesDef) current_node).getComponent();
	List scope = getScope((MContained)iface);

        if(data_type.equals("IdlProvidesType")) {
	    if(scope.size() > 0)
		return "::" + join("::", scope) + "::" + iface.getIdentifier();
	    else
		return "::" + iface.getIdentifier();
	}
	else if(data_type.equals("ProvidesType")) {
	    return iface.getIdentifier();
	}
	else if(data_type.equals("ComponentType")) {
	    return component.getIdentifier();
	}
	return super.data_MProvidesDef(data_type, data_value);
    }
}





