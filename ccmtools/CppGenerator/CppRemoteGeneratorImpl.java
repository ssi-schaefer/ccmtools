/* CCM Tools : C++ Code Generator Library
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

package ccmtools.CppGenerator;

import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/***********************************************************
    File structure of remote C++ prototype:

Calculator
|-- CCM_Session_Calculator
|   |-- Calculator.idl
|   |-- Calculator_remote.cc
|   |-- Calculator_remote.h
|   |-- Makefile.py
|
|-- CCM_Session_Calculator_Stubs // from IDL2 generator 
|   |-- Calculator.idl
|       => Calculator.cc               
|       => Calculator.h
|   |-- Makefile.py
|
|-- CCM_Session_Container        // Environment files
|   |-- CCMContainer.cc
|   |-- CCMContainer.h
|   |-- Makefile.py
|
|-- remoteComponents             // Environment files
|   |-- Components.idl
|       => Components.cc
|       => Components.h
|   |-- Makefile.py
|
|-- test                         // Test client hand crafted
|   |-- Makefile.py
|   |-- client.cc
|   |-- runClient
|   |-- runLister
|   |-- runNameService
|   |-- runServer

************************************************************/


public class CppRemoteGeneratorImpl
    extends CppGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.
    private final static String[] local_output_types =
    {
        "MHomeDef", 
	"MComponentDef"
    };

    // output locations and templates for "environment files", the files that we
    // need to output once per project. the length of this list needs to be the
    // same as the length of the following list ; this list provides the file
    // names, and the next one provides the templates to use for each file.

    private final static File[] local_environment_files =
    {
        new File("CCM_Session_Container", "CCMContainer.h"),
	new File("CCM_Session_Container", "CCMContainer.cc"),
	new File("CCM_Session_Container", "Makefile.py"),

        new File("remoteComponents", "Components.idl"),
        new File("remoteComponents", "Makefile.py"),
    };

    private final static String[] local_environment_templates =
    {
        "CCMContainerHeader", // Template for CCMContainer.h
	"CCMContainerImpl",   // Template for CCMContainer.cc
	"Blank",              // Template for Makefile.py

	"ComponentsIdl",      // Template for Components.idl
	"Blank"               // Template for Makefile.py
    };

    /**************************************************************************/

    public CppRemoteGeneratorImpl(Driver d, File out_dir)
        throws IOException
    {
        super("CppRemote", d, out_dir, local_output_types,
              local_environment_files, local_environment_templates);
    }


    /**
     * Finalize the output files. This function's implementation writes two
     * global files, a global Remote value conversion header, and a global
     * user_types.h file based on the individual <file>_user_types.h files.
     *
     * @param defines a map of environment variables and their associated
     *        values. This usually contains things like the package name,
     *        version, and other generation info.
     * @param files a list of the filenames (usually those that were provided to
     *        the generator front end).
     */
    public void finalize(Map defines, List files)
    {
	System.out.println("CppRemoteGeneratorImpl.finalize()");

        // write a global python type conversion header.
        // e.g. CCM_Remote/convert_remote.h

        StringBuffer output = new StringBuffer("");
        output.append("\n#ifndef ___CCM__REMOTE__CONVERT__H___\n");
        output.append("#define ___CCM__REMOTE__CONVERT__H___\n\n");

        for (Iterator i = files.iterator(); i.hasNext(); ) {
            File file = new File((String) i.next());
            String name = file.getName().toString().split("\\.")[0];
            if (file.isFile())
                output.append("#include \"convert_"+name+".h\"\n");
        }

        Template includes = template_manager.getRawTemplate("ConvertRemoteHeader");
        output.append(includes.substituteVariables(defines));
        output.append("\n#endif // ___CCM__REMOTE__CONVERT__H___\n\n");

	//        writeFinalizedFile("CCM_Remote", "convert_remote.h", output.toString());
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
	System.out.println("CppRemoteGeneratorImpl.writeOutput()");

        String node_name = ((MContained) current_node).getIdentifier();
	
	// Each template consists of two sections separated by "<<<<<<<SPLIT>>>>>>>"
	// that are written in two different files node_name + "_remote.h" and
	// node_name + "_remote.cc"
        String out_string = template.substituteVariables(output_variables);
        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");
        String[] out_files = { node_name + "_remote.h",
                               node_name + "_remote.cc" };

        for (int i = 0; i < out_strings.length; i++) {
            String generated_code = out_strings[i];
	    String file_name = out_files[i];

  	    if (current_node instanceof MComponentDef 
		|| current_node instanceof MHomeDef)  {
		String file_dir = handleNamespace("FileNamespace", node_name) + "_remote";
		if (generated_code.trim().equals("")) continue;
		writeFinalizedFile(file_dir, file_name, generated_code);
	    }
        }
    }



    /**
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function.
     *
     * @param iface the interface from which we're starting the two step
     *        operation.
     * @param operation the particular interface operation that we're filling in
     *        a template for.
     * @param container the container in which the given interface is defined.
     * @return a map containing the keys and values needed to fill in the
     *         template for this interface.
     */
    protected Map getTwoStepVariables(MInterfaceDef iface,
                                      MOperationDef operation,
                                      MContained container)
    {
	System.out.println("CppRemoteGeneratorImpl.getTwoStepVariables()");

        String lang_type = super.getLanguageType(operation);
        Map vars = new Hashtable();
 
	//...

        return vars;
    }


    /**
     * Get a local value for the given variable name.
     *
     * @param variable The variable name to get a value for.
     * @return the value of the variable available from the current
     *         output_variables hash table. Could be an empty string.
     */
    protected String getLocalValue(String variable)
    {
	System.out.println("CppRemoteGeneratorImpl.getLocalValue("+variable+")");

        String value = super.getLocalValue(variable);

        if (current_node instanceof MAliasDef) {
            return data_MAliasDef(variable, value);
        }

        return value;
    }
}

