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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.utils.Debug;

public class CppRemoteTestGeneratorImpl
    extends CppGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.

    private final static String[] local_output_types =
    { "MComponentDef" };

    public CppRemoteTestGeneratorImpl(Driver d, File out_dir)
        throws IOException
    {
        super("CppRemoteTest", d, out_dir, local_output_types);

        base_namespace.add("CCM_Remote");

	Debug.println(Debug.METHODS,"CppRemoteTestGeneratorImpl("
		      + d + ", " + out_dir + ")");
    }



    /**
     * Overwrites the CppGenerator's method to handle namespaces in different
     * ways. There are local (CCM_Local) namespaces, remote (CCM_Remote)
     * namespaces and the namespaces of the generated stubs and skeletons.
     *
     * "FileNamespace": is used to create the directory in which the
     *                  remote component logic will be generated
     *
     * "IncludeNamespace": is used to create the include path depending
     *                     on the component's namespace.
     *                     The CCM_Remote namespace is cut off because 
     *                     there can be local and remote include paths.
     *
     * "Namespace": is used to create the scope for classes.
     *              The CCM_Remote namespace is cut off because 
     *              there can be local and remote include paths.
     *              If a namespace is defined, it has to start with "::"
     *
     * "ShortNamespace": corresponds with the module hierarchy in the IDL file,
     *                   there is no CCM_Local, CCM_Remote or CCM_Session_ included.
     *
     * "IdlFileNamespace":
     *
     * "IdlNamespace":
     **/
    protected String handleNamespace(String data_type, String local)
    {
        List names = new ArrayList(namespace);
		
	if (!local.equals("")) names.add("CCM_Session_" + local);

	if(data_type.equals("FileNamespace")) {
            return join("_", slice(names, 0));
        } 
	else if(data_type.equals("IncludeNamespace")) {
	    return join("/", slice(names, 1));
	}
	else if (data_type.equals("Namespace")) {
	    List NamespaceList = slice(names, 1);
	    if(NamespaceList.size() > 0) {
		return "::" + join("::", NamespaceList);
	    }
	    return "";
        } 
	else if (data_type.equals("ShortNamespace")) {
	    if(names.size() > 1) {
		List shortList = new ArrayList(names.subList(1, names.size()-1));
		if(shortList.size() > 0)
		    return join("::", shortList) + "::";
	    }
	    return "";
        }
	else if (data_type.equals("IdlFileNamespace")) {
	    if(names.size() > 1) {
		List IdlFileList = new ArrayList(names.subList(1, names.size()-1));
		if(IdlFileList.size() > 0)
		    return join("_", IdlFileList) + "_";
	    }
	    return "";
        } 
	else if (data_type.equals("IdlNamespace")) {
	    if(names.size() > 2) {
		List IdlFileList = new ArrayList(names.subList(2, names.size()-1));
		return join("::", IdlFileList) + "::";
	    }
	    return "";
        }
        return super.handleNamespace(data_type, local);
    }


    /**
     * Overwrites the CppGenerator's method to change between CCM_Local
     * CCM_Remote.
     *
    protected String handleNamespace(String data_type, String local)
    {
	Debug.println(Debug.METHODS,"CppRemoteTestGenerator.handleNamespace(" + 
		      data_type + ", " + local + ")");

        List names = new ArrayList(namespace);

	// ShortNamespace corresponds with the module hierarchy in the IDL file,
	// there is no CCM_Local, CCM_Remote or CCM_Session_ included.
	if (data_type.equals("ShortNamespace"))
            return join("::", slice(names, 1));

	if (!local.equals("")) names.add("CCM_Session_" + local);

        if (data_type.equals("Namespace")) {
            return join("::", slice(names, 1));
        } else if (data_type.equals("FileNamespace")) {
            return join("_", slice(names, 1));
        } else if (data_type.equals("IdlFileNamespace")) {
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
        return super.handleNamespace(data_type, local);
    }
    */

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
        String file_dir = "test";
        String file_name = "_check_" +
            handleNamespace("FileNamespace", node_name) + "_remote.cc";

        writeFinalizedFile(file_dir, file_name, generated_code);

	// generate an empty Makefile.py in the CCM_Test
	// directory - needed by Confix
	writeFinalizedFile(file_dir, "Makefile.py", "");
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





