/* CCM Tools : C++ Code Generator Library
 * Robert Lechner <rlechner@sbox.tugraz.at>
 * Egon Teiniker  <egon.teiniker@tugraz.at>
 * copyright (c) 2002-2004 Salomon Automation
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

import oclmetamodel.*;
import ccmtools.OCL.parser.*;
import ccmtools.OCL.utils.*;
import ccmtools.OCL.generators.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


/**
 * Local C++ adapter generator with DbC support.
 *
 * @author Egon Teiniker (templates and base implementation)
 * @author Robert Lechner (DbC code generation)
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
        super("CppLocalDbc", d, out_dir, local_output_types );

        Debug.setDebugLevel(Debug.NONE);
        Debug.println(Debug.METHODS,"CppLocalDbcGeneratorImpl.CppLocalDbcGeneratorImpl()");
    }


    //====================================================================
    // Code generator core functions
    //====================================================================

    /**
     * The parse tree creator.
     *
     * @see {@link ccmtools.OCL.utils.Factory#getElementCreator()}
     */
    protected OclElementCreator creator_;

    /**
     * The normalized parse tree.
     */
    protected MFile oclParseTree_;

    /**
     * Calculates the type of OCL expressions by using the IDL parse tree.
     */
    protected TypeCreator typeChecker_;

    /**
     * The OCL generator for C++ code.
     */
    protected OclCodeGenerator generator_;

    /**
     * The current instance of MComponentDef or MHomeDef.
     */
    protected MInterfaceDef currentMainModule_;


    /**
     * Overwrites the CodeGenerator's method...
     *
     * @throws IllegalStateException  if the creation of the OCL parse tree fails
     */
    public void startNode(Object node, String scope_id)
    {
        super.startNode(node, scope_id);
        Debug.println(Debug.METHODS,"CppLocalDbcGeneratorImpl.startNode()");

        // Things that must be done bevor starting the code generation
        if ((node instanceof MContainer) &&
            (((MContainer) node).getDefinedIn() == null))
        {
            namespace.push("CCM_Local");

            // Parse the OCL file and build the OCL model.
            try
            {
                creator_ = ccmtools.OCL.utils.Factory.getElementCreator();
                typeChecker_ = new TypeCreator(creator_);
                String oclFileName = ((MContainer) node).getIdentifier() + ".ocl";
                if( (new File(oclFileName)).isFile() )
                {
                    System.out.println("parse OCL file: " + oclFileName);
                    MFile oclTree = OclParser.parseFile(oclFileName,creator_);
                    /*OclXmlWriter writer0 = new OclXmlWriter(new FileWriter(oclFileName+".org.xml"));
    	            writer0.write(oclTree);
    	            writer0.close();*/
                    //
                    OclNormalization normalization = new OclNormalization(creator_);
                    oclParseTree_ = normalization.normalize(oclTree);
                    /*OclXmlWriter writer2 = new OclXmlWriter(new FileWriter(oclFileName+".norm.xml"));
    	            writer2.write(oclParseTree_);
    	            writer2.close();*/
        		}
        		else
        		{
        		    System.out.println("cannot find OCL file: " + oclFileName);
        		    oclParseTree_ = creator_.createFile();
        		    MPackage pkg = creator_.createPackage(OclCodeGenerator.GLOBAL_CONTEXT_NAME);
        		    creator_.add(oclParseTree_,pkg);
        		}
        		generator_ = new OclCppGenerator(creator_, oclParseTree_, typeChecker_);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                throw new IllegalStateException("cannot create OCL parse tree");
            }
        }
        if( (node instanceof MHomeDef)||(node instanceof MComponentDef) )
        {
            currentMainModule_ = (MInterfaceDef)node;
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


//    private String getLocalValue( String variable, MInterfaceDef theClass )
//    {
//        String value = getLocalValue(variable);
//        if( value.length()>0 )
//        {
//            return value;
//        }
//        return "/*"+variable+"*/"+theClass.getIdentifier();  // HACK !!!
//    }


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
     * Overwrites the CppGenerator's method...
     * Handles the tags that are related to the MComponentDef* templates
     * and calls the {@link #getInvariantInvocation} method.
     */
    protected String data_MComponentDef(String data_type, String data_value)
    {
    	Debug.println(Debug.METHODS,"CppLocalDbcGeneratorImpl.data_MComponentDef()");

    	// Handle %(InvariantInvocation)s tag
        if( data_type.equals("InvariantInvocation") )
        {
	        return getInvariantInvocation((MComponentDef)current_node);
        }

    	// For any other cases call CppGenerator's method
    	return super.data_MComponentDef(data_type, data_value);
    }


    /**
     * Overwrites the CppGenerator's method...
     * Handles the tags that are related to the MHomeDef* templates
     * and calls the {@link #getInvariantInvocation} method.
     */
    protected String data_MHomeDef(String data_type, String data_value)
    {
    	Debug.println(Debug.METHODS,"CppLocalDbcGeneratorImpl.data_MHomeDef)");

    	// Handle %(InvariantInvocation)s tag
        if( data_type.equals("InvariantInvocation") )
        {
	        return getInvariantInvocation((MHomeDef)current_node);
        }

    	// For any other cases call CppGenerator's method
    	return super.data_MHomeDef(data_type, data_value);
    }


    /**
     * Overwrites the CppGenerator's method...
     * Handles the tags that are related to the MProvidesDef* templates
     * and calls the {@link #getInvariantInvocation} method.
     */
    protected String data_MProvidesDef(String data_type, String data_value)
    {
    	Debug.println(Debug.METHODS,"CppLocalDbcGeneratorImpl.data_MProvidesDef)");

    	// Handle %(InvariantInvocation)s tag
        if( data_type.equals("InvariantInvocation") )
        {
	        return getInvariantInvocation( ((MProvidesDef)current_node).getProvides() );
        }

    	// For any other cases call CppGenerator's method
    	return super.data_MProvidesDef(data_type, data_value);
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


    public void endGraph()
    {
        super.endGraph();
	    Debug.println(Debug.METHODS,"CppLocalDbcGeneratorImpl.endGraph()");
        if( generator_!=null )
        {
            try
            {
                generator_.writeConstraintInformation("constraints.txt", typeChecker_);
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }
            int e = generator_.getErrorCounter();
            if( e>0 )
            {
                if( e==1 )  System.err.println("-->  1 error");
                else  System.err.println("-->  "+e+" errors");
                System.err.println("code generation failed");
                System.exit(1);
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

        if(! lang_type.equals("void"))
        {
            vars.put("Return", "return ");
            vars.put("OperationCall", lang_type+" result = ");
            vars.put("OperationReturn", "return result;");
        }
        else
        {
            vars.put("Return", "");
            vars.put("OperationCall", "");
            vars.put("OperationReturn", "");
        }
        return vars;
    }



    //====================================================================
    // Code generator DbC extensions
    //====================================================================

    private static final String CHECK_INVARIANT_CALL_ON_ENTRY =
        "  DbC_check_invariant(DbC_FUNCTION_NAME,false);";

    private static final String CHECK_INVARIANT_CALL_ON_EXIT =
        "  DbC_check_invariant(DbC_FUNCTION_NAME,true);";


    /**
     * These methods are used to generate code that is inserted before
     * and after an adapter's method call.
     */

    protected String getFactoryPreInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getFactoryPreInvocation()");
    	// TODO Robert
    	return "  /* FactoryPreInvocation */";
    }

    protected String getFactoryPostInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getFactoryPostInvocation()");
    	// TODO Robert
    	return "  /* FactoryPostInvocation */";
    }


    protected String getAttributeGetPreInvocation(MAttributeDef attr)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getAttributeGetPreInvocation()");
        return ""; // nothing to do
    }

    protected String getAttributeGetPostInvocation(MAttributeDef attr)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getAttributeGetPostInvocation()");
        return ""; // nothing to do
    }


    protected String getAttributeSetPreInvocation(MAttributeDef attr)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getAttributeSetPreInvocation()");
        return CHECK_INVARIANT_CALL_ON_ENTRY;
    }

    /**
     * Checks the invariant after the change of an attribute.
     */
    protected String getAttributeSetPostInvocation(MAttributeDef attr)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getAttributeSetPostInvocation()");
        return CHECK_INVARIANT_CALL_ON_EXIT;
    }


    /**
     * Checks the precondition of a supported interface.
     */
    protected String getSupportsPreInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
		      "CppLocalDbCGenerator.getSupportsPreInvocation()");
        return getCodeForPrecondition(op,false);
    }

    /**
     * Checks the postcondition of a supported interface.
     */
    protected String getSupportsPostInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
		      "CppLocalDbCGenerator.getSupportsPostInvocation()");
        return getCodeForPostcondition(op,false);
    }


    /**
     * Checks the precondition of a provided interface.
     */
    protected String getProvidesPreInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getProvidesPreInvocation()");
        return getCodeForPrecondition(op,true);
    }


    /**
     * Checks the postcondition of a provided interface.
     */
    protected String getProvidesPostInvocation(MOperationDef op)
    {
        Debug.println(Debug.METHODS,
                      "CppLocalDbCGenerator.getProvidesPostInvocation()");
        return getCodeForPostcondition(op,true);
    }


    //////////////////////////////////////////////////////////////////////////


    /**
     * Handles the %(InvariantInvocation)s tag.
     *
     * @param theClass  the class (interface, component, etc.)
     */
    protected String getInvariantInvocation( MContainer theClass )
    {
        if( theClass==null )
        {
            return "/* theClass==null */";
        }
        return generator_.makeCodeForInvariant(theClass).statements_;
    }


    /**
     * Returns the full C++ code of an invariant.
     *
     * @param theClass  the class (interface, component, etc.)
     */
    protected String getCodeForInvariant( MContainer theClass )
    {
        if( theClass==null )
        {
            return "/* theClass==null */";
        }
        return "  {\n" + generator_.makeCodeForInvariant(theClass).statements_ + "  }";
    }


    /**
     * Returns the full C++ code of a precondition.
     *
     * @param op  the operation definition
     * @param providedInterface  true if the operation is part of a provided interface
     */
    protected String getCodeForPrecondition( MOperationDef op, boolean providedInterface )
    {
        MContainer theClass = providedInterface ? op.getDefinedIn() : currentMainModule_;
        if( theClass==null )
        {
            return "/* theClass==null */";
        }
        ConstraintCode preCode = generator_.makeCodeForPrecondition(theClass, op);
        String code = CHECK_INVARIANT_CALL_ON_ENTRY+"\n" + preCode.statements_;
        ConstraintCode postCode = generator_.makeCodeForPostcondition(theClass, op);
        if( postCode.preStatements_.length()<1 )
        {
            return code;
        }
        return code+"  // @pre\n"+postCode.preStatements_;
    }


    /**
     * Returns the full C++ code of a postcondition.
     *
     * @param op  the operation definition
     * @param providedInterface  true if the operation is part of a provided interface
     */
    protected String getCodeForPostcondition( MOperationDef op, boolean providedInterface )
    {
        MContainer theClass = providedInterface ? op.getDefinedIn() : currentMainModule_;
        if( theClass==null )
        {
            return "/* theClass==null */";
        }
        ConstraintCode postCode = generator_.makeCodeForPostcondition(theClass,op);
        return CHECK_INVARIANT_CALL_ON_EXIT+"\n" + postCode.statements_;
    }


    //////////////////////////////////////////////////////////////////////////


    /**
     * Calculates the type of OCL expressions by using the IDL parse tree.
     */
    class TypeCreator extends OclNormalization implements OclTypeChecker
    {
        private MContainer theClass_;
        private MIDLType idlType_;
        private MOperationContext context_;
        private MTyped returnType_;

        TypeCreator( OclElementCreator creator )
        {
            super(creator);
        }

        /**
        * Returns the variable or function name for an attribute.
        *
        * @param oclName  the OCL name of the attribute
        * @param et       the type of the parent (or null, if the type is unknown)
        */
        public String getAttributeName( String oclName, ElementType et )
        {
            if( et!=null )
            {
                if( et.idlType_!=null )
                {
                    if( et.idlType_ instanceof MStructDef )
                    {
                        return oclName;
                    }
                    // TODO
                    System.out.println("***   et.idlType_.CLASS=="+
                                       et.idlType_.getClass().getName()+"   oclName=="+oclName);
                }
                /*else if( OclCodeGenerator.OCL_DEBUG_OUTPUT )
                {
                    System.out.println("***   et.idlType_==null   oclName=="+oclName);
                }*/
            }
            /*else if( OclCodeGenerator.OCL_DEBUG_OUTPUT )
            {
                System.out.println("***   et==null   oclName=="+oclName);
            }*/
            return oclName+"()";
        }


        /**
         * Returns the class name of the local adpter.
         *
         * @param theClass  home, component or interface
         * @return class name or null
         */
        public String getLocalAdapterName( MContainer theClass )
        {
            return "__LOCAL__ADAPTER__NAME__";
//            if( theClass!=null )
//            {
//                if( theClass instanceof MComponentDef )
//                {
//                    return getLocalValue("ComponentType",(MInterfaceDef)theClass);
//                }
//                if( theClass instanceof MHomeDef )
//                {
//                    return getLocalValue("HomeType",(MInterfaceDef)theClass);
//                }
                // HACK:
//                int index = current_name.lastIndexOf(":");
//                if( index>0 )
//                {
//                    String part1 = current_name.substring(index+1);
//                    String part2 = theClass.getIdentifier();
//                    return "/*facet adapter*/"+part1+part2;
//                }
//            }
//            return null;
        }

        /**
         * Calculates and sets the type of an expression.
         *
         * @param expr  the expression
         * @param conCode  no change
         * @return the type of the expression
         */
        public ElementType makeType( MExpression expr, ConstraintCode conCode )
        {
            theClass_ = conCode.theClass_;
            idlType_ = conCode.idlType_;
            context_ = conCode.opCtxt_;
            returnType_ = conCode.returnType_;
            ElementType result = new ElementType();
            last_attribute_idlType_ = null;
            result.oclType_ = setAndGetOclType(expr);
            if( expr instanceof MPropertyCall )
            {
                result.idlType_ = last_attribute_idlType_;
            }
            return result;
        }

        private MIDLType last_attribute_idlType_;

        /**
         * Returns the type of a property call or null.
         */
        protected OclType getOclType( MPropertyCall pc )
        {
            String name = pc.getName();
            if( context_!=null )
            {
                if( name.equals(OclConstants.KEYWORD_RESULT) )
                {
                    return makeOclType( returnType_ );
                }
                Iterator it = context_.getParameters().iterator();
                while( it.hasNext() )
                {
                    MFormalParameter fp = (MFormalParameter)it.next();
                    if( fp.getName().equals(name) )
                    {
                        return convertOclType( fp.getType() );
                    }
                }
            }
            if( theClass_!=null )
            {
                OclType type = getPropertyType(theClass_,name);
                if( type!=null )
                {
                    return type;
                }
                ClassIterator it = ClassIterator.getIterator(theClass_,true);
                while( it.hasNext() )
                {
                    type = getPropertyType(it.next(),name);
                    if( type!=null )
                    {
                        return type;
                    }
                }
                if( OclCodeGenerator.OCL_DEBUG_OUTPUT )
                {
                    System.err.println("TypeCreator.getOclType: could not find '"+
                        name+"' in class '"+theClass_.getIdentifier()+"'");
                }
            }
            if( idlType_!=null )
            {
                if( idlType_ instanceof MStructDef )
                {
                    MStructDef sd = (MStructDef)idlType_;
                    Iterator it = sd.getMembers().iterator();
                    while( it.hasNext() )
                    {
                        MFieldDef fd = (MFieldDef)it.next();
                        if( fd.getIdentifier().equals(name) )
                        {
                            return makeOclType(fd);
                        }
                    }
                }
                // TODO
                if( OclCodeGenerator.OCL_DEBUG_OUTPUT )
                {
                    System.err.println("TypeCreator.getOclType: could not find '"+
                        name+"' in IDL-type '"+idlType_.getClass().getName()+"'");
                }
            }
            return null;
        }

        private OclType getPropertyType( MContainer def, String name )
        {
            Iterator it1 = def.getContentss().iterator();
            while( it1.hasNext() )
            {
                Object obj = it1.next();
                if( obj instanceof MAttributeDef )
                {
                    MAttributeDef attr = (MAttributeDef)obj;
                    if( attr.getIdentifier().equals(name) )
                    {
                        last_attribute_idlType_ = attr.getIdlType();
                        return makeOclType(attr);
                    }
                }
                else if( obj instanceof MOperationDef )
                {
                    MOperationDef op = (MOperationDef)obj;
                    if( op.getIdentifier().equals(name) )
                    {
                        return makeOclType(op);
                    }
                }
            }
            return null;
        }
    }

}


