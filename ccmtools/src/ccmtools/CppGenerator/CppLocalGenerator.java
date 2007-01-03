/*
 * CCM Tools : C++ Code Generator Library 
 * Leif Johnson <leif@ambient.2y.net>
 * Egon Teiniker <egon.teiniker@salomon.at> 
 * Copyright (C) 2002 - 2005 Salomon Automation
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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.CodeGenerator.Template;
import ccmtools.CppGenerator.plugin.AnyPluginManager;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MArrayDef;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MTypedefDef;
import ccmtools.metamodel.BaseIDL.MUnionDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.metamodel.ComponentIDL.MComponentDef;
import ccmtools.metamodel.ComponentIDL.MHomeDef;
import ccmtools.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.ConfigurationLocator;
import ccmtools.utils.Confix;
import ccmtools.utils.SourceFileHelper;
import ccmtools.utils.Text;

/***
 * Local C++ component generator
 * 
 * This generator creates local C++ interfaces and component implementations
 * that conforms to the CORBA Component Model but does not use CORBA types
 * and libraries.
 ***/
public class CppLocalGenerator 
	extends CppGenerator
{
    protected AnyPluginManager anyManager = null;
    
    protected Set<String> OutputDirectories;
    
    
    //====================================================================
    // Definition of arrays that determine the generator's behavior
    //====================================================================
    
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.
    protected final static String[] LOCAL_OUTPUT_TEMPLATE_TYPES = {
            "MComponentDef", "MInterfaceDef", "MHomeDef", "MStructDef",
            "MUnionDef", "MAliasDef", "MEnumDef", "MExceptionDef",
            "MProvidesDef"
    };

    
    public CppLocalGenerator(UserInterfaceDriver uiDriver, File outDir) 
    		throws IOException, CcmtoolsException
    {
        super("CppLocal", uiDriver, outDir, LOCAL_OUTPUT_TEMPLATE_TYPES);
        logger = Logger.getLogger("ccm.generator.cpp.local");
        logger.fine("begin");        
        cxxGenNamespace = ConfigurationLocator.getInstance().getCppLocalNamespaceExtension();        
        anyManager = new AnyPluginManager();
        logger.fine("end");
    }

    
    
    
    //====================================================================
    // Code generator core methods
    //====================================================================
       
    /**
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function. This version of the function fills in
     * operation information from the given interface.
     * 
     * @param operation
     *            the particular interface operation that we're filling in a
     *            template for.
     * @param container
     *            the container in which the given interface is defined.
     * @return a map containing the keys and values needed to fill in the
     *         template for this interface.
     */
    protected Map getTwoStepOperationVariables(MOperationDef operation,
                                               MContained container)
    {
        logger.fine("enter getTwoStepOperationVariables()");
        String langType = getLanguageType(operation);
        Map vars = new Hashtable();

        vars.put("Object", container.getIdentifier());
        vars.put("Identifier", operation.getIdentifier());
        vars.put("LanguageType", langType);
        
        vars.put("ExceptionThrows", getOperationExcepts(operation));
        vars.put("OperationParameterList", getOperationParams(operation));
        vars.put("MParameterDefName", getOperationParamNames(operation));
        
        // these tags are used to generate local adapter implementations
        vars.put("OperationToFacetDelegation", 
                 getOperationDelegation(operation,"facet"));
        vars.put("OperationToLocalComponentDelegation",
                 getOperationDelegation(operation,"local_component"));
        vars.put("OperationResult", 
                 getOperationResult(operation));         
        vars.put("Return", getOperationReturn(operation));
        
        logger.fine("leave getTwoStepOperationVariables()");
        return vars;
    }

    /**
     * Overrides the CppGenerator method to handle MAliasDef subtypes
     * TODO: Refactor - move this code up to the superclass
     */
    protected String getLocalValue(String variable)
    {
        logger.fine("getLocalValue()");
        // Get local value of CppGenerator 
        String value = super.getLocalValue(variable);
        
        if(variable.equals("LanguageTypeInclude")) 
        {
            MTyped type = (MTyped) currentNode;
            MIDLType idlType = type.getIdlType();
            return getLanguageTypeInclude(idlType);
        }
        
        // Handle simple tags from templates which are related to
        // the c++local generator
        if (currentNode instanceof MAttributeDef) {
            return data_MAttributeDef(variable, value);
        }
        else if (currentNode instanceof MFieldDef) {
            return data_MFieldDef(variable, value);
        }
        else if(currentNode instanceof MConstantDef) {
            return data_MConstantDef(variable, value);
        }
        
        return value;
    }
            
    
    protected String data_MFieldDef(String dataType, String dataValue) 
    {
        logger.fine("begin");
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        if(dataType.equals("FieldDefInclude"))
        {
    			 return getLanguageTypeInclude(idlType);
        }
        logger.fine("end");
        return dataValue;
    }
    
    
    protected String data_MAliasDef(String dataType, String dataValue) 
    {
        logger.fine("enter data_MAliasDef()");
        MAliasDef alias = (MAliasDef)currentNode;
        MTyped type = (MTyped) alias;
        MIDLType idlType = type.getIdlType();

        if(dataType.equals("TypedefInclude")) 
        {
            if(anyManager.isTypedefToAny(idlType)) 
            {
            		dataValue = anyManager.generateCode(alias, dataType);
            }
            else if(idlType instanceof MSequenceDef)
            {
            		MTyped singleType = (MTyped) idlType;
            		MIDLType singleIdlType = singleType.getIdlType();
                dataValue = getLanguageTypeInclude(singleIdlType);
            }
            else 
            {
                dataValue = getLanguageTypeInclude(idlType);
            }
        }
        else if(dataType.equals("TypedefDefinition")) 
        {
            if(anyManager.isTypedefToAny(idlType)) 
            {
            		dataValue = anyManager.generateCode(alias, dataType);
            }
            else 
            {
                dataValue = getTypedef(alias);
            }
        }
        else 
        { // fallback to super class
            dataValue = super.data_MAliasDef(dataType,dataValue);
        }
        logger.fine("leave data_MAliasDef()");
        return dataValue;
    }
      
        
    protected String data_MEnumDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MEnumDef()");
        dataValue = super.data_MEnumDef(dataType, dataValue);
        logger.fine("leave data_MEnumDef()");
        return dataValue;
    }
    
    
    protected String data_MAttributeDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MAttributeDef()");
        MAttributeDef attr = (MAttributeDef)currentNode;
        if(dataType.equals("InterfaceIdentifier")) {
            dataValue = attr.getDefinedIn().getIdentifier();
        }
        else {
            dataValue = super.data_MAttributeDef(dataType, dataValue);
        }
        logger.fine("leave data_MAttributeDef()");
        return dataValue;
    }

    
    protected String data_MOperationDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MOperationDef()");
        MOperationDef op = (MOperationDef)currentNode;
        if(dataType.equals("InterfaceIdentifier")) {
            dataValue = op.getDefinedIn().getIdentifier();
        }
        else if(dataType.equals("ExceptionThrows")) {
            dataValue = getOperationExcepts(op);
        }
        else if(dataType.equals("OperationToFacetDelegation")) {
            dataValue = getOperationDelegation(op,"facet_");
        }
        else if(dataType.equals("OperationResult")) {
            dataValue = getOperationResult(op);
        }
        else {
            dataValue = super.data_MOperationDef(dataType, dataValue);
        }
        logger.fine("leave data_MOperationDef()");
        return dataValue;
    }
        
    
    protected String data_MInterfaceDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MInterfaceDef()");
        MInterfaceDef iface = (MInterfaceDef) currentNode;
        if(dataType.equals("InterfaceInclude")) 
        {
            MContained contained = (MContained)currentNode;
            dataValue = getLocalCxxIncludeName(contained);
        }
        else if(dataType.equals("InterfaceIncludes"))
        {
        		StringBuffer code = new StringBuffer();
        		Set includeSet = getInterfaceIncludes(iface);
        		includeSet.addAll(getBaseInterfaceIncludes(iface));
        		dataValue = Text.join("\n", includeSet);
        }
        else if(dataType.equals("BaseInterfaceAdapterAttributeHeader")) {
            List baseInterfaceList = iface.getBases();
            boolean isImpl = false;
            dataValue = getBaseInterfaceAttributes(isImpl,iface,baseInterfaceList);
        }
        else if(dataType.equals("BaseInterfaceAdapterAttributeImpl")) {
            List baseInterfaceList = iface.getBases();
            boolean isImpl = true;
            dataValue = getBaseInterfaceAttributes(isImpl,iface,baseInterfaceList);
        }
        else if(dataType.equals("BaseInterfaceAdapterOperationHeader")) {
            List baseInterfaceList = iface.getBases();
            boolean isImpl = false;
            dataValue = getBaseInterfaceOperations(isImpl,iface,baseInterfaceList);
        }
        else if(dataType.equals("BaseInterfaceAdapterOperationImpl")) {
            List baseInterfaceList = iface.getBases();
            boolean isImpl = true;
            dataValue = getBaseInterfaceOperations(isImpl,iface,baseInterfaceList);
        }
        else if(dataType.equals("ConstantImplementation")) {
            // we handle the constant impl here because we need the interface
            // identifier to generate the class implementation code
            StringBuffer buffer = new StringBuffer();
            for(Iterator i = iface.getContentss().iterator(); i.hasNext();) {
                MContained contained = (MContained)i.next();
                if(contained instanceof MConstantDef) {
                    MConstantDef constant = (MConstantDef)contained;
                    buffer.append(generateConstantImpl(iface,constant));                    
                }
            }
            dataValue = buffer.toString();
        }
        else if(dataType.equals("InterfaceCCMName")) 
        {
            dataValue = getLocalCxxNamespace(iface, "::") + "CCM_" + iface.getIdentifier();
        }
        else 
        {
            dataValue = super.data_MInterfaceDef(dataType, dataValue);
        }
        logger.fine("leave data_MInterfaceDef()");
        return dataValue;
    }

    
    protected String data_MProvidesDef(String dataType, String dataValue)
    {
        MProvidesDef provides = (MProvidesDef)currentNode;
        StringBuilder code = new StringBuilder();
        
        if(dataType.equals("generateOperationsImpl"))
        {
            List<MOperationDef> opList = getOperationList(provides.getProvides());
            for(MOperationDef op : opList)
            {
                code.append(generateOperationImpl(provides,op));                
            }            
        }
        else if(dataType.equals("generateAttributeImpl"))
        {
            List<MAttributeDef> attrList = getAttributeList(provides.getProvides());
            for(MAttributeDef attr : attrList)
            {
                code.append(generateAttributeImpl(provides,attr));                
            }    
        }
        else
        {
            code.append(super.data_MProvidesDef(dataType, dataValue));
        }
        return code.toString();
    }
    
    
    protected List<MAttributeDef> getAttributeList(MInterfaceDef iface)
    {
        List<MAttributeDef> attrList = new ArrayList<MAttributeDef>();
        for(Iterator i = iface.getBases().iterator(); i.hasNext();)
        {
            MInterfaceDef base = (MInterfaceDef)i.next();
            attrList.addAll(getAttributeList(base));
        }
        for(Iterator i = iface.getContentss().iterator(); i.hasNext();)
        {
            MContained contained = (MContained)i.next();
            if(contained instanceof MAttributeDef)
            {
                attrList.add((MAttributeDef)contained);
            }     
        }
        return attrList;
    }
    
    
    protected List<MOperationDef> getOperationList(MInterfaceDef iface)
    {
        List<MOperationDef> opList = new ArrayList<MOperationDef>();
        for(Iterator i = iface.getBases().iterator(); i.hasNext();)
        {
            MInterfaceDef base = (MInterfaceDef)i.next();
            opList.addAll(getOperationList(base));
        }
        for(Iterator i = iface.getContentss().iterator(); i.hasNext();)
        {
            MContained contained = (MContained)i.next();
            if(contained instanceof MOperationDef)
            {
                opList.add((MOperationDef)contained);
            }     
        }
        return opList;
    }
    
    protected String generateOperationImpl(MProvidesDef provides, MOperationDef op)
    {
        StringBuilder code = new StringBuilder();        
        code.append(getLanguageType(op)).append("\n");
        code.append(provides.getComponent().getIdentifier()).append("_").append(provides.getIdentifier());
        code.append("_impl::").append(op.getIdentifier()).append("(");
        code.append(getOperationParams(op)).append(")\n");
        code.append("    ").append(getOperationExcepts(op)).append("\n");
        code.append("{\n");
        code.append("    // TODO : IMPLEMENT ME HERE !\n");
        code.append("}\n\n");
        return code.toString();
    }


    protected String generateAttributeImpl(MProvidesDef provides, MAttributeDef attr)
    {
        StringBuilder code = new StringBuilder(); 
        code.append("const ").append(getLanguageType(attr)).append("\n");
        code.append(provides.getComponent().getIdentifier()).append("_").append(provides.getIdentifier());
        code.append("_impl::").append(attr.getIdentifier()).append("() const\n");
        code.append("    throw(::Components::CCMException)\n");
        code.append("{\n");
        code.append("    return ").append(attr.getIdentifier()).append("_;\n");
        code.append("}\n\n");
        code.append("void\n");
        code.append(provides.getComponent().getIdentifier()).append("_").append(provides.getIdentifier());
        code.append("_impl::").append(attr.getIdentifier()).append("(const ");
        code.append(getLanguageType(attr)).append(" value)\n");
        code.append("    throw(::Components::CCMException)\n");
        code.append("{\n");
        code.append("    ").append(attr.getIdentifier()).append("_ = value;\n");
        code.append("}\n\n");
        return code.toString();
    }
    
    protected String getUserTypeName(MIDLType idlType)
    {
    		if(idlType instanceof MPrimitiveDef || 
    				idlType instanceof MStringDef ||
    				idlType instanceof MWstringDef)
    		{
    			return "";    			
    		}
    		else if(idlType instanceof MContained)
    		{
    			return ((MContained)idlType).getIdentifier();
    		}
    		else
    		{
    			throw new RuntimeException("Unhandled IDLType: " + idlType);
    		}
    }
    
    protected Set getAttributeIncludes(MInterfaceDef iface)
    {
    		Set includeSet = new HashSet();
    		for(Iterator i = iface.getContentss().iterator(); i.hasNext(); )
    		{
    			MContained contained = (MContained)i.next();
    			if(contained instanceof MAttributeDef)
    			{
    				MAttributeDef attr = (MAttributeDef)contained;
    				MTyped type = (MTyped)attr;    				
    				includeSet.add(getLanguageTypeInclude(type.getIdlType()));
    			}
    		}
    		return includeSet;
    }
    
    protected Set getOperationIncludes(MInterfaceDef iface)
    {
    		Set includeSet = new HashSet();
		for(Iterator i = iface.getContentss().iterator(); i.hasNext(); )
		{
			MContained contained = (MContained)i.next();
			if(contained instanceof MOperationDef)
			{
				MOperationDef op = (MOperationDef)contained;
				MTyped type = (MTyped)op;    												
				if(op.getIdlType() instanceof MContained)
				{
                    includeSet.add(getLanguageTypeInclude(op.getIdlType()));
				}
				
				for(Iterator e = op.getExceptionDefs().iterator(); e.hasNext();)
				{
					MExceptionDef ex = (MExceptionDef)e.next();
                    includeSet.add(getLanguageTypeInclude(ex));
				}
				
				for(Iterator p = op.getParameters().iterator(); p.hasNext();)
				{
					MParameterDef param = (MParameterDef)p.next();
                    includeSet.add(getLanguageTypeInclude(param.getIdlType()));
				}
			}
		}
		return includeSet;	
    }
    
    protected Set getInterfaceIncludes(MInterfaceDef iface)
    {
    		Set includeSet = new HashSet(); 
    		includeSet.addAll(getAttributeIncludes(iface));
		includeSet.addAll(getOperationIncludes(iface));
		return includeSet;
    }
    
    protected Set getBaseInterfaceIncludes(MInterfaceDef iface)
    {
    		Set includeSet = new HashSet(); 
    		
    		for(Iterator i = iface.getBases().iterator(); i.hasNext();)
    		{
    			MInterfaceDef base = (MInterfaceDef)i.next();
    			includeSet.add(getLanguageTypeInclude((MContained)base));
    		}
    		return includeSet;
    }
    
    protected String data_MConstantDef(String dataType, String dataValue)
    {
        MConstantDef constant = (MConstantDef) currentNode;
        
        return dataValue;
    }
    

    protected String generateConstantImpl(MInterfaceDef iface, MConstantDef constant)
    {
        if(constant == null) return "";

        StringBuffer code = new StringBuffer();
        String type = generateConstantType(constant);
        String value = generateConstantValue(constant);
        code.append("const ");        
        code.append(" ").append(type).append(" ").append(iface.getIdentifier());
        code.append(Text.SCOPE_SEPARATOR);
        code.append(constant.getIdentifier()).append(" = ");
        code.append(value).append(";\n");
        return code.toString();
    }
    
    protected String generateConstantType(MConstantDef constant)
    {
        MIDLType idlType = constant.getIdlType();
        String type;
        if(idlType instanceof MStringDef) {
            type = "std::string";
        }
        else if(idlType instanceof MWstringDef) {
            type = "std::wstring";
        }
        else if(idlType instanceof MPrimitiveDef) {
            type = (String) language_mappings.get(((MPrimitiveDef)idlType).getKind().toString());
        }
        else {
            throw new RuntimeException("generateConstantValue(): Unhandled constant type!");
        }        
        return type;
    }

    protected String generateConstantValue(MConstantDef constant)
    {
        MIDLType idlType = constant.getIdlType();
        Object valueObject = constant.getConstValue();
        String value;
        try { 
            if(idlType instanceof MStringDef 
                    || idlType instanceof MWstringDef) {
                value = "\"" + (String) valueObject + "\"";
            }
            else if(idlType instanceof MPrimitiveDef) {
                MPrimitiveDef primitive = (MPrimitiveDef) idlType;
                if(primitive.getKind() == MPrimitiveKind.PK_OCTET
                        || primitive.getKind() == MPrimitiveKind.PK_SHORT
                        || primitive.getKind() == MPrimitiveKind.PK_USHORT) {
                    value = ((Integer) valueObject).toString();
                }
                else if(primitive.getKind() == MPrimitiveKind.PK_BOOLEAN) {
                    value = ((Boolean) valueObject).toString();
                }
                else if(primitive.getKind() == MPrimitiveKind.PK_LONG
                        || primitive.getKind() == MPrimitiveKind.PK_LONGLONG) {
                    value = ((Long) valueObject).toString() + "L";
                }
                else if(primitive.getKind() == MPrimitiveKind.PK_ULONG
                        || primitive.getKind() == MPrimitiveKind.PK_ULONGLONG) {
                    value = ((Long) valueObject).toString() + "UL";
                }
                else if(primitive.getKind() == MPrimitiveKind.PK_CHAR) {
                    value = (String) valueObject;
                }
                else if(primitive.getKind() == MPrimitiveKind.PK_FLOAT) {
                    value = ((Float) valueObject).toString();
                }
                else if(primitive.getKind() == MPrimitiveKind.PK_DOUBLE) {
                    value = ((Double) valueObject).toString();
                }
                else {
                    throw new RuntimeException("CppLocalGenerator."
                            + "generateConstantValue(): Unhandled IDL type!");
                }
            }
            else {
                throw new RuntimeException("CppLocalGenerator."
                        + "generateConstantValue(): Unhandled constant value!");
            }
        }
        catch(Exception e) {
            throw new RuntimeException("CppLocalGenerator." +
                                       "generateConstantValue():" +
                                       e.getMessage());
        }
        return value;
    }
    
    
    
    //====================================================================
    // Code generator utility methods
    //====================================================================

    /***
     * Generate the #include<> statement for a MIDLType model element
     * with full scope.
     * e.g: #include <world/europe/austria/ccm/local/Person.h>
     */
    protected String getLanguageTypeInclude(MIDLType idlType) 
    {
        logger.fine("begin");
        StringBuffer code = new StringBuffer();
        if(idlType instanceof MStringDef) 
        {
            code.append("#include <string>\n");
        }
        else if(idlType instanceof MSequenceDef
                || idlType instanceof MArrayDef) 
        {
            MTyped singleType = (MTyped)idlType;
            MIDLType singleIdlType = singleType.getIdlType();
            code.append("#include <vector>\n");
            code.append(getLanguageTypeInclude(singleIdlType));
        }
        else if(idlType instanceof MPrimitiveDef)
        {
        	    MPrimitiveDef type = (MPrimitiveDef)idlType;
        	    if(type.getKind() == MPrimitiveKind.PK_ANY)
        	    {
        	        code.append("#include <wamas/platform/utils/Value.h>\n");
        	    }
        }
        else if(idlType instanceof MContained)
        {
            code.append(getScopedInclude((MContained) idlType));
        }
        logger.fine("end");
        return code.toString();
    }
        
    protected String getLanguageTypeInclude(MContained contained) 
    {
        return getScopedInclude(contained);
    }
    
    
    //====================================================================
    // Code generation methods
    //====================================================================    

    protected String getTypedef(MAliasDef alias) 
    {
        StringBuffer code = new StringBuffer();
        MTyped type = (MTyped) alias;
        MTypedefDef typedef = (MTypedefDef) alias;
        code.append("typedef ");
        code.append(getLanguageType(type));
        code.append(" ");
        code.append(typedef.getIdentifier());
        code.append(";\n");
        return code.toString();
    }
    
    
    protected String getBaseInterfaceOperations(boolean isImpl, MInterfaceDef iface, List baseInterfaceList)
    {
        StringBuffer code = new StringBuffer();
        for(Iterator i = baseInterfaceList.iterator(); i.hasNext();) {
            MInterfaceDef baseIface = (MInterfaceDef) i.next();
            List contentList = baseIface.getContentss();
            for(Iterator j = contentList.iterator(); j.hasNext();) {
                MContained contained = (MContained)j.next();	
                if(contained instanceof MOperationDef) {
                    MOperationDef op = (MOperationDef)contained;	
                    if(isImpl) {
                        // generate code for C++ impl file
                        code.append(getAdapterOperationImpl(iface,op));
                    }
                    else { 
                        // generate code for C++ header file 
                        code.append(getAdapterOperationHeader(op));
                    }
                }
            }
            code.append("\n");
        }
        return code.toString();
    }
    
    protected String getAdapterOperationHeader(MOperationDef op) 
    {
        StringBuffer code = new StringBuffer();
        code.append(Text.TAB).append("virtual").append("\n");
        code.append(Text.TAB).append(getLanguageType(op)).append("\n");
        code.append(Text.TAB).append(op.getIdentifier()).append("(");
        code.append(getOperationParams(op));
        code.append(")\n");
        code.append(Text.tab(2)).append(getOperationExcepts(op)).append(";\n");
        return code.toString();
    }
    
    protected String getAdapterOperationImpl(MInterfaceDef iface, MOperationDef op)
    {
        StringBuffer code = new StringBuffer();
        code.append(getLanguageType(op)).append("\n");
        code.append(iface.getIdentifier());
        code.append("Adapter::").append(op.getIdentifier()).append("(");
        code.append(getOperationParams(op)).append(")\n");
        code.append(Text.TAB).append(getOperationExcepts(op)).append("\n");
        code.append("{\n");
        code.append(Text.TAB).append("if(!validConnection())\n");
        code.append(Text.tab(2)).append("throw ::Components::InvalidConnection();\n\n");
        code.append(getOperationDelegation(op,"facet_")).append("\n\n");
        code.append(getOperationResult(op)).append("\n");
        code.append("}\n");
        return code.toString();
    }

    
    protected String getBaseInterfaceAttributes(boolean isImpl, MInterfaceDef iface, List baseInterfaceList)
    {
        StringBuffer code = new StringBuffer();
        for(Iterator i = baseInterfaceList.iterator(); i.hasNext();) {
            MInterfaceDef baseIface = (MInterfaceDef) i.next();
            List contentList = baseIface.getContentss();
            for(Iterator j = contentList.iterator(); j.hasNext();) {
                MContained contained = (MContained)j.next();	
                if(contained instanceof MAttributeDef) {
                    MAttributeDef attr = (MAttributeDef)contained;
                    if(isImpl) {
                        // generate code for C++ impl file
                        code.append(getAdapterAttributeImpl(iface,attr));
                    }
                    else {
                        // generate code for C++ header file 
	                    code.append(getAdapterAttributeHeader(attr));
                    }
                }
            }
            code.append("\n");
        }
        return code.toString();
    }
    
    protected String getAdapterAttributeHeader(MAttributeDef attr) 
    {
        StringBuffer code = new StringBuffer();
        code.append(Text.TAB).append("virtual\n");
        code.append(Text.TAB).append("const ").append(getLanguageType(attr));
        code.append(" ").append(attr.getIdentifier()).append("() const\n");
        code.append(Text.tab(2)).append("throw(::Components::CCMException);\n\n");

        code.append(Text.TAB).append("virtual\n");
        code.append(Text.TAB).append("void ").append(attr.getIdentifier()).append("(const ");
        code.append(getLanguageType(attr)).append(" value)\n");
        code.append(Text.tab(2)).append("throw(::Components::CCMException);\n\n");
        return code.toString();
    }
    
    protected String getAdapterAttributeImpl(MInterfaceDef iface, MAttributeDef attr) 
    {
        StringBuffer code = new StringBuffer();
        code.append("const ").append(getLanguageType(attr)).append("\n");
        code.append(iface.getIdentifier()).append("Adapter::").append(attr.getIdentifier());
        code.append("() const\n");
        code.append(Text.TAB).append("throw(::Components::CCMException)\n");
        code.append("{\n");
        code.append(Text.TAB).append("if(!validConnection())\n");
        code.append(Text.tab(2)).append("throw ::Components::InvalidConnection();\n");
        code.append(Text.TAB).append("return facet_->").append(attr.getIdentifier());
        code.append("();\n");       
        code.append("}\n\n");
        
        code.append("void\n");
        code.append(iface.getIdentifier()).append("Adapter::").append(attr.getIdentifier());
        code.append("(const ").append(getLanguageType(attr)).append(" value)\n");
        code.append(Text.TAB).append("throw(::Components::CCMException)\n");
        code.append("{\n");
        code.append(Text.TAB).append("if(!validConnection())\n");
        code.append(Text.tab(2)).append("throw ::Components::InvalidConnection();\n");
        code.append(Text.TAB).append("facet_->").append(attr.getIdentifier());
        code.append("(value);\n");       
        code.append("}\n\n");
        return code.toString();
    }
    
    /**
     * Write generated code to an output file.
     * 
     * @param template
     *            the template object to get the generated code structure from ;
     *            variable values should come from the node handler object.
     */
    protected void writeOutput(Template template)
    {
        logger.fine("enter writeOutput()");
        List out_paths = getOutputFiles();
        String out_string = template.substituteVariables(output_variables);
        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");
        String implDirectory = ConfigurationLocator.getInstance().get("ccmtools.dir.impl");

        try 
        {
            OutputDirectories = new HashSet<String>();
            Iterator path_iterator = out_paths.iterator();
            for(int i = 0; i < out_strings.length; i++) 
            {               
                // try to prittify generated code (eliminate empty lines etc).
                String generated_code = SourceFileHelper.prettifySourceCode(out_strings[i]);

                // out_path = [directory, filename]
                List out_path = (List) path_iterator.next();

                // from the getOutputFiles function we know each entry in the
                // output file list has exactly two parts ... the dirname and
                // the filename.
                String file_dir = (String) out_path.get(0);
                String file_name = (String) out_path.get(1);

                // don't add blank output files. this lets us discard parts of
                // the templates that we don't want to output (see the component
                // section of the getOutputFiles function)
                if(file_name.equals(""))
                    continue;

                File outFile = new File(output_dir + File.separator + file_dir, file_name);
                if((file_dir == implDirectory) && outFile.isFile()) 
                {
                    if(outFile.getName().endsWith("_entry.h")) 
                    {
                        // *_entry.h files must be overwritten by every generator
                        // call because they are part of the component logic
                        writeFinalizedFile(file_dir, file_name, generated_code);
                    }
                    else if(!isCodeEqualWithFile(generated_code, outFile)) 
                    {
                        uiDriver.printMessage("WARNING: " + outFile + " already exists!");
                        file_name += ".new";
                        outFile = new File(output_dir + File.separator + file_dir, file_name);
                    }
                }
                if(isCodeEqualWithFile(generated_code, outFile)) 
                {
                    uiDriver.printMessage("Skipping " + outFile);
                }
                else 
                {
                    writeFinalizedFile(file_dir, file_name, generated_code);
                }
            }
            
            Confix.writeConfix2Files(uiDriver, OutputDirectories);
        }
        catch(Exception e) 
        {
            uiDriver.printError("!!!Error " + e.getMessage());
        }
        logger.fine("leave writeOutput()");
    }

    
    /**
     * Overide writeFinalizedFile method to collect all output directories as input
     * for the Confix file generation.
     * 
     */
    protected void writeFinalizedFile(String directory, String file, String output) 
        throws IOException
    {
        OutputDirectories.add(output_dir + File.separator + directory);        
        super.writeFinalizedFile(directory, file, output);
    }

    
    /**
     * Create a list of lists of pathname components for output files needed by
     * the current node type.
     * 
     * @return a list of List objects containing file names for all output files
     *         to be generated for the current node.
     */
    protected List getOutputFiles()
    {
        logger.fine("enter getOutputFiles()");
        String node_name = ((MContained) currentNode).getIdentifier();
        List files = new ArrayList();
        List f = null;
        String implDirectory = ConfigurationLocator.getInstance().get("ccmtools.dir.impl");
        
        if((currentNode instanceof MComponentDef)
                || (currentNode instanceof MHomeDef)) {
            String base_name = node_name;

            // we put home files in the dir with the component files to convince
            // confix to work with us. beware the evil voodoo that results when
            // home and component files are in separate directories !

            if(currentNode instanceof MHomeDef) {
                base_name = ((MHomeDef) currentNode).getComponent().getIdentifier();
            }
            String cxxGenNamespace = getCxxGenOutputDirectory(base_name);
            String cxxNamespace = getCxxOutputDirectory(base_name);
            
            f = new ArrayList();
            f.add(cxxGenNamespace);
            f.add(node_name + "_gen.h");
            files.add(f);
            f = new ArrayList();
            f.add(cxxGenNamespace);
            f.add(node_name + "_gen.cc");
            files.add(f);

            f = new ArrayList();
            f.add(cxxGenNamespace + "_share");
            f.add(node_name + "_share.h");
            files.add(f);

            if(currentNode instanceof MHomeDef) 
            {
                MHomeDef home = (MHomeDef)currentNode;    
                f = new ArrayList();
                f.add(implDirectory);
                f.add(getLocalCxxIncludeName(home, Text.MANGLING_SEPARATOR) + "_entry.h");
                files.add(f);
            }

            if((flags & FLAG_APPLICATION_FILES) != 0) {
                f = new ArrayList();
                f.add(implDirectory);
                f.add(node_name + "_impl.h");
                files.add(f);
                f = new ArrayList();
                f.add(implDirectory);
                f.add(node_name + "_impl.cc");
                files.add(f);
            }
            else {
                f = new ArrayList();
                f.add(implDirectory);
                f.add("");
                files.add(f);
                f = new ArrayList();
                f.add(implDirectory);
                f.add("");
                files.add(f);
            }
        }
        else if((currentNode instanceof MStructDef)
                || (currentNode instanceof MUnionDef)
                || (currentNode instanceof MAliasDef)
                || (currentNode instanceof MEnumDef)
                || (currentNode instanceof MExceptionDef)) {
            f = new ArrayList();
            f.add(getCxxOutputDirectory(""));
            f.add(node_name + ".h");
            files.add(f);
        }
        else if ((currentNode instanceof MInterfaceDef)) {
            // Interface part
            f = new ArrayList();
            f.add(getCxxOutputDirectory(""));
            f.add(node_name + ".h");
            files.add(f);
            // Adapter part (header file)
            f = new ArrayList();
            f.add(getCxxGenOutputDirectory(""));
            f.add(node_name + "Adapter.h");
            files.add(f);
            // Adapter part (impl file)
            f = new ArrayList();
            f.add(getCxxGenOutputDirectory(""));
            f.add(node_name + "Adapter.cc");
            files.add(f);
        }
        else if((currentNode instanceof MProvidesDef)) {
            if((flags & FLAG_APPLICATION_FILES) != 0) {
                MComponentDef component = ((MProvidesDef) currentNode).getComponent();
                f = new ArrayList();
                f.add(implDirectory);
                f.add(component.getIdentifier() + "_" + node_name + "_impl.h");
                files.add(f);
                f = new ArrayList();
                f.add(implDirectory);
                f.add(component.getIdentifier() + "_" + node_name + "_impl.cc");
                files.add(f);
            }
            else {
                f = new ArrayList();
                f.add(implDirectory);
                f.add("");
                files.add(f);
                f = new ArrayList();
                f.add(implDirectory);
                f.add("");
                files.add(f);
            }
        }
        else {
            f = new ArrayList();
            f.add("");
            f.add("");
            files.add(f);
        }
        logger.fine("leave getOutputFiles()");
        return files;
    }

    protected String getCxxOutputDirectory(String component)
    {
        logger.fine("begin");
        
        List modules = new ArrayList();
        modules.addAll(cxxNamespace);
        modules.addAll(namespaceStack);
        
        String generatorPrefix = ConfigurationLocator.getInstance().get("ccmtools.dir.gen");
        
        logger.fine("end");
        return generatorPrefix + join("_", modules);
    }

    protected String getCxxGenOutputDirectory(String component)
    {
        logger.fine("begin");

        List modules = new ArrayList();
        modules.addAll(cxxGenNamespace);
        modules.addAll(namespaceStack);
        
        String generatorPrefix = ConfigurationLocator.getInstance().get("ccmtools.dir.gen");
        
        logger.fine("end");
        return generatorPrefix + join("_", modules);
    }
    
    
    //====================================================================
    // Simple %(tag)s helper methods
    //====================================================================


    
    
    //====================================================================
    // MOperationDef %(tag)s helper methods
    //====================================================================
       
    public String getOperationDelegation(MOperationDef op, String target)
    {
        logger.finer("enter getOperationDelegation()");
        StringBuffer code = new StringBuffer();
        String langType = getLanguageType(op);
        code.append(Text.TAB);
        if(!langType.equals("void")) {
            code.append(langType);
            code.append(" result = ");
        }
        code.append(target).append("->");
        code.append(op.getIdentifier()).append("(");
        List parameterList = new ArrayList();
        for(Iterator params = op.getParameters().iterator();params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            parameterList.add(p.getIdentifier());
        }
        code.append(Text.join(",", parameterList)).append(");");
        logger.finer("leave getOperationDelegation()");
        return code.toString();
    }    
    
    public String getOperationResult(MOperationDef op)
    {
        logger.finer("enter getOperationResult()");
        StringBuffer code = new StringBuffer();
        String langType = getLanguageType(op);
        if(!langType.equals("void")) {
            code.append(Text.TAB).append("return result;");
        }
        logger.finer("leave getOperationResult()");
        return code.toString();
    }
    
    public String getOperationReturn(MOperationDef op)
    {
        logger.finer("enter getOperationReturn()");
        String code;
        String langType = getLanguageType(op);
        if(!langType.equals("void")) {
            code = "return ";
        }
        else {	
            code = "";
        }
        logger.finer("leave getOperationReturn()");
        return code;
    }
}