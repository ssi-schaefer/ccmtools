/* CCM Tools : C++ Code Generator Library
 * Robert Lechner <rlechner@sbox.tugraz.at>
 * Egon Teiniker  <egon.teiniker@tugraz.at>
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
import java.util.Iterator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Local C++ adapter generator with DbC support
 * 
 * 
 */
public class CppLocalDbcGeneratorImpl
    extends CppGenerator
{
    //====================================================================
    // Definition of arrays that determine the generator's behavior 
    //====================================================================

    /**
     * Top level node types:
     * Types for which we have a global template; that is, a template that is
     * not contained inside another template.
     */
    private final static String[] local_output_types =
    {
	"MHomeDef",
	"MComponentDef"
    };


    //====================================================================


    public CppLocalDbcGeneratorImpl(Driver d, File out_dir)
        throws IOException
    {
        super("CppLocalDbc", d, out_dir, local_output_types);

        Debug.setDebugLevel(Debug.NONE);
        Debug.println(Debug.METHODS,"CppLocalDbcGeneratorImpl.CppLocalDbcGeneratorImpl()");
    }


    //====================================================================
    // Code generator core functions
    //====================================================================

    /**
     * Overwrites the CodeGenerator's method...
     */
    public void startNode(Object node, String scope_id)
    {
        super.startNode(node, scope_id);
	Debug.println(Debug.METHODS,"CppLocalDbcGeneratorImpl.startNode()");

	// Things that must be done bevor starting the code generation
        if ((node instanceof MContainer) &&
            (((MContainer) node).getDefinedIn() == null)) {
            namespace.push("CCM_Local");

	    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    // TODO Robert
	    String OclFileName = ((MContainer) node).getIdentifier() + ".ocl";
	    System.out.println("Parse OCL File: " + OclFileName);
	    // Parse the OCL file and build the OCL model
	    // ...
	}
    }



    protected String handleNamespace(String data_type, String local)
    {
        List names = new ArrayList(namespace);
        if (! local.equals("")) names.add("CCM_Session_" + local);

	if (data_type.equals("FileNamespace")) {
            return join("_", slice(names, 0));
	}
	return super.handleNamespace(data_type,local);
    }


    /**
     * Overwrites the CppGenerator's method...
     */
    protected String getLocalValue(String variable)
    {
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.getLocalValue()");

	String value = super.getLocalValue(variable);
	if (current_node instanceof MAttributeDef) { 
            return data_MAttributeDef(variable, value);
	}
	return value;
    }

    /**
     * Overwrites the CppGenerator's method...
     * Handles the tags that are related to the MFactoryDef* templates
     * and calls the Pre- and PostInvocation methods.
     */
    protected String data_MFactoryDef(String data_type, String data_value)
    {
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.data_MFactoryDef()");

	// Handle %(FactoryPreInvocation)s tag 
	if(data_type.equals("FactoryPreInvocation")) {
	    return getFactoryPreInvocation((MOperationDef)current_node);
	}
	// Handle %(FactoryPostInvocation)s tag 
	else if(data_type.equals("FactoryPostInvocation")) {
	    return getFactoryPostInvocation((MOperationDef)current_node);
	}
	// For any other cases call CppGenerator's method
	return super.data_MFactoryDef(data_type, data_value);
    }

    /**
     * Handles the tags that are related to the MAttributeDef* templates
     * and calls the Pre- and PostInvocation methods.
     */
    protected String data_MAttributeDef(String data_type, String data_value)
    {
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.data_MAttributeDef()");
	
	// Handle %(AttributeGetPreInvocation)s tag 
        if (data_type.equals("AttributeGetPreInvocation")) {
	    return getAttributeGetPreInvocation((MAttributeDef)current_node);
        }
	// Handle %(AttributeGetPostInvocation)s tag 
	else if(data_type.equals("AttributeGetPostInvocation")) {
	    return getAttributeGetPostInvocation((MAttributeDef)current_node);
	}
	// Handle %(AttributeSetPreInvocation)s tag 
	else if(data_type.equals("AttributeSetPreInvocation")) {
	    return getAttributeSetPreInvocation((MAttributeDef)current_node);
	}
	// Handle %(AttributeSetPostInvocation)s tag 
	else if(data_type.equals("AttributeSetPostInvocation")) {
	    return getAttributeSetPostInvocation((MAttributeDef)current_node);
	}
        return data_value;
    }



    /**
     * Implements the CodeGenerator's abstract method...
     */
    protected void writeOutput(Template template)
        throws IOException
    {
	Debug.println(Debug.METHODS,"CppLocalDbcGeneratorImpl.writeOutput()");

        String out_string = template.substituteVariables(output_variables);
        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");
	String[] out_file_types = { "_dbc.h", "_dbc.cc" };

        for (int i = 0; i < out_strings.length; i++) {
	    // If the out_string is empty, skip the file creation
	    if (out_strings[i].trim().equals("")) 
		continue;

	    // If the current node is a ComponentDef, create the component's files
  	    if (current_node instanceof MComponentDef) {
		String component_name = ((MContained) current_node).getIdentifier();
		String file_dir = handleNamespace("FileNamespace", component_name);

		writeFinalizedFile(file_dir,  
				   component_name + out_file_types[i], 
				   out_strings[i]);
	    }
	    // If the current node is a HomeDef, create the home's files
	    else if (current_node instanceof MHomeDef)  {
		MHomeDef home = (MHomeDef)current_node;
		String component_name = ((MContained)home.getComponent()).getIdentifier();  
		String home_name = home.getIdentifier();
		String file_dir = handleNamespace("FileNamespace", component_name);

		writeFinalizedFile(file_dir, 
				   home_name + out_file_types[i], 
				   out_strings[i]);
	    }
	}
    }


    /**
     * Implements the CppGenerator's abstract method.
     *
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function. This version of the function fills in
     * operation information from the given interface.
     *
     * @param operation the particular interface operation that we're filling in
     *        a template for.
     * @param container the container in which the given interface is defined.
     * @return a map containing the keys and values needed to fill in the
     *         template for this interface.
     */   
    protected Map getTwoStepOperationVariables(MOperationDef operation,
					       MContained container)
    {
	Debug.println(Debug.METHODS,"getTwoStepVariables()");
        String lang_type = getLanguageType(operation);
        Map vars = new Hashtable();
        vars.put("Object",            container.getIdentifier());
        vars.put("Identifier",        operation.getIdentifier());
        vars.put("LanguageType",      lang_type);
        vars.put("MExceptionDef",     getOperationExcepts(operation));
        vars.put("MParameterDefAll",  getOperationParams(operation));
        vars.put("MParameterDefName", getOperationParamNames(operation));

	vars.put("SupportsPreInvocation"  , getSupportsPreInvocation(operation));
	vars.put("SupportsPostInvocation" , getSupportsPostInvocation(operation));
	vars.put("ProvidesPreInvocation"  , getProvidesPreInvocation(operation));
	vars.put("ProvidesPostInvocation" , getProvidesPostInvocation(operation));

        if (! lang_type.equals("void")) 
	    vars.put("Return", "return ");
        else                            
	    vars.put("Return", "");
        return vars;
    }



    //====================================================================
    // Code generator DbC extensions
    //====================================================================

    /**
     * These methods are used to generate code that is inserted before
     * and after an adapter's method call.  
     */

    protected String getFactoryPreInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getFactoryPreInvocation()");
	// TODO Robert
	return "  cout << \"PreInvocation...\" << endl;";
    }

    protected String getFactoryPostInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getFactoryPostInvocation()");
	// TODO Robert
	return "  cout << \"PostInvocation...\" << endl;";
    }


    protected String getAttributeGetPreInvocation(MAttributeDef attr)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getAttributeGetPreInvocation()");
	// TODO Robert
	return "  cout << \"PreInvocation...\" << endl;";
    }

    protected String getAttributeGetPostInvocation(MAttributeDef attr)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getAttributeGetPostInvocation()");
	// TODO Robert
	return "  cout << \"PostInvocation...\" << endl;";
    }

    protected String getAttributeSetPreInvocation(MAttributeDef attr)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getAttributeSetPreInvocation()");
	// TODO Robert
	return "  cout << \"PreInvocation...\" << endl;";
    }

    protected String getAttributeSetPostInvocation(MAttributeDef attr)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getAttributeSetPostInvocation()");
	// TODO Robert
	return "  cout << \"PostInvocation...\" << endl;";
    }


    protected String getSupportsPreInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
		      "CppLocalDbCGenerator.getSupportsPreInvocation()");
	// TODO Robert
	return "  cout << \"PreInvocation...\" << endl;";
    }

    protected String getSupportsPostInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
		      "CppLocalDbCGenerator.getProvidesPostInvocation()");
	// TODO Robert
	return "  cout << \"PostInvocation...\" << endl;";
    }

    protected String getProvidesPreInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getProvidesPreInvocation()");
	// TODO Robert
	return "  cout << \"PreInvocation...\" << endl;";
    }

    protected String getProvidesPostInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getProvidesPostInvocation()");
	// TODO Robert
	return "  cout << \"PostInvocation...\" << endl;";
    }
}


