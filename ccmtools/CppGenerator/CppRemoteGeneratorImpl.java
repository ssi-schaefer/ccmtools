/* CCM Tools : C++ Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>, Egon Teiniker <egon.teiniker@tugraz.at>
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
import ccmtools.Metamodel.BaseIDL.*;
import ccmtools.Metamodel.ComponentIDL.*;

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
|-- Calculator.idl
|
|-- CCM_Session_Calculator
|   |-- CalculatorHome_remote.cc
|   |-- CalculatorHome_remote.h
|   |-- Calculator_remote.cc
|   |-- Calculator_remote.h
|   |-- Makefile.py               // from local Generator
|
|
|-- CCM_Session_Calculator_Stubs  // created from IDL2 generator 
|   |-- Calculator.idl
|       => Calculator.cc            // from IDL generated   
|       => Calculator.h             // from IDL generated
|   |-- Makefile.py
|
|
|-- CCM_Session_Container         // Environment files
|   |-- CCMContainer.cc
|   |-- CCMContainer.h
|   |-- Makefile.py
|
|-- remoteComponents              // Environment files
|   |-- Components.idl2
|       => Components.cc            // from IDL generated
|       => Components.h             // from IDL generated
|   |-- Makefile.py

************************************************************/



//====================================================================

public class CppRemoteGeneratorImpl
    extends CppGenerator
{
    /**
     * Defines the IDL to C++ Mappings for primitive types
     *
     */
    private final static String[] remote_language_map =
    {
	"",
	"CORBA::Any",         // PK_ANY
	"CORBA::Boolean",     // PK_BOOLEAN
	"CORBA::Char",        // PK_CHAR
	"CORBA::Double",      // PK_DOUBLE
	"",                   // PK_FIXED
	"CORBA::Float",       // PK_FLOAT
	"CORBA::Long",        // PK_LONG
	"CORBA::LongDouble",  // PK_LONGDOUBLE
	"CORBA::LongLong",    // PK_LONGLONG
	"",                   // PK_NULL
	"",                   // PK_OBJREF
	"CORBA::Octet",       // PK_OCTET
	"",                   // PK_PRINCIPAL
	"CORBA::Short",       // PK_SHORT
	"char*",              // PK_STRING
	"",                   // PK_TYPECODE
	"CORBA::ULong",       // PK_ULONG
	"CORBA::ULongLong",   // PK_ULONGLONG
	"CORBA::UShort",      // PK_USHORT
	"",                   // PK_VALUEBASE
	"",                   // PK_VOID
	"CORBA::WChar",       // PK_WCHAR
	"CORBA::WChar*"       // PK_WSTRING
    };
    
    /**
     * Types for which we have a global template; that is, a template that is
     * not contained inside another template.
     */
    private final static String[] local_output_types =
    {
        "MHomeDef", 
	"MComponentDef"
    };

    /**
     * Output locations and templates for "environment files", the files that we
     * need to output once per project. the length of this list needs to be the
     * same as the length of the following list ; this list provides the file
     * names, and the next one provides the templates to use for each file.
     */
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

    
    //====================================================================
    // ? should we put the filling of language_mapping into each Generator
    
    //====================================================================

    public CppRemoteGeneratorImpl(Driver d, File out_dir)
        throws IOException
    {
        super("CppRemote", d, out_dir, local_output_types,
              local_environment_files, local_environment_templates);

	// fill the language_mappings with IDL to C++ Mapping types 
	String[] labels = MPrimitiveKind.getLabels();
	language_mappings = new Hashtable();
	for (int i = 0; i < labels.length; i++)
	    language_mappings.put(labels[i], remote_language_map[i]);
    }



    /**
     * Return the C++ language mapping for the given object. 
     *
     * @param object the node object to use for type finding.
     */
    protected String getLanguageType(MTyped object)
    {
	System.out.println("CppRemoteGeneratorImpl.getLanguageType()");

        MIDLType idl_type = object.getIdlType();

        String base_type = getBaseIdlType(object);
        if (language_mappings.containsKey(base_type))
            base_type = (String) language_mappings.get(base_type);

	// handling of operation parameter types and passing rules 
        if (object instanceof MParameterDef) {
            MParameterDef param = (MParameterDef) object;
            MParameterMode direction = param.getDirection();

            String prefix = "";
            String suffix = "";

	    // Henning/Vinoski P296
	    // simple IDL types are passed as IN parameter witout const 
            if (direction == MParameterMode.PARAM_IN) {
		if(!(idl_type instanceof MPrimitiveDef)) 
		    prefix = "const";
	    }
		
            if ((idl_type instanceof MTypedefDef) ||
                (idl_type instanceof MStringDef) ||
                (idl_type instanceof MFixedDef)) {
		suffix = "&";
	    }

            return prefix + base_type + suffix;
        } 
	
	else if ((object instanceof MAliasDef) &&
		 (idl_type instanceof MTyped)) {
            return getLanguageType((MTyped) idl_type);
        } 
	// handle IDL sequence mapping
	else if (object instanceof MSequenceDef) {
            // FIXME : can we implement bounded sequences in C++ ?
            return "std::"+sequence_type+"<"+base_type+"> ";
        } 
	// handle IDL array mapping
	else if (object instanceof MArrayDef) {
            Iterator i = ((MArrayDef) object).getBounds().iterator();
            Long bound = (Long) i.next();
            String result = base_type + "[" + bound;
            while (i.hasNext()) result += "][" + (Long) i.next();
            return result + "]";
        }

        return base_type;
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

	// only calls the overwritten method of the base class
        String value = super.getLocalValue(variable);

	// ...

	return value;
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

	// only calls the overwritten method of the base class
        String lang_type = super.getLanguageType(operation);
        Map vars = new Hashtable();
 
	vars.put("Object",            container.getIdentifier());
        vars.put("Identifier",        operation.getIdentifier());
	vars.put("ProvidesType",      iface.getIdentifier());
        vars.put("SupportsType",      iface.getIdentifier());
        vars.put("LanguageType",      lang_type);
        vars.put("MExceptionDef",     getOperationExcepts(operation));
        vars.put("MParameterDefAll",  getOperationParams(operation, "all"));
        vars.put("MParameterDefName", getOperationParams(operation, "name"));

	 if (! lang_type.equals("void")) 
	     vars.put("Return", "return ");
	 else 
	     vars.put("Return", "");

        return vars;
    }



    /**     
     * Write generated code to an output file.
     *
     * @param template the template object to get the generated code structure
     *        from; variable values should come from the node handler object.
     */
    public void writeOutput(Template template)
        throws IOException
    {
	System.out.println("CppRemoteGeneratorImpl.writeOutput()");
	
	// Each global template consists of two sections separated by 
	// "<<<<<<<SPLIT>>>>>>>"
	// that are written in two different files node_name + "_remote.h" and
	// node_name + "_remote.cc"
        String out_string = template.substituteVariables(output_variables);
        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");
	String node_name = ((MContained) current_node).getIdentifier();
        String[] out_files = { node_name + "_remote.h",
                               node_name + "_remote.cc" };

        for (int i = 0; i < out_strings.length; i++) {
            String generated_code = out_strings[i];
	    String file_name = out_files[i];
	    String file_dir;
  	    if (current_node instanceof MComponentDef) {
		// ComponentDef node
		file_dir = handleNamespace("FileNamespace", node_name);
		if (generated_code.trim().equals("")) continue;
		writeFinalizedFile(file_dir, file_name, generated_code);
	    }
	    else if (current_node instanceof MHomeDef)  {
		// HomeDef node
		MHomeDef home = (MHomeDef)current_node;
		node_name = ((MContained)home.getComponent()).getIdentifier();
		file_dir = handleNamespace("FileNamespace", node_name);
		if (generated_code.trim().equals("")) continue;
		writeFinalizedFile(file_dir, file_name, generated_code);
	    }
        }
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

	//...
    }
}



