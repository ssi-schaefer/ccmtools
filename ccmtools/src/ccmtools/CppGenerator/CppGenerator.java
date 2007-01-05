/**
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import ccmtools.CodeGenerator.CodeGenerator;
import ccmtools.CodeGenerator.Template;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MArrayDef;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MDefinitionKind;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterDef;
import ccmtools.metamodel.BaseIDL.MParameterMode;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MTypedefDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.metamodel.ComponentIDL.MComponentDef;
import ccmtools.metamodel.ComponentIDL.MFactoryDef;
import ccmtools.metamodel.ComponentIDL.MFinderDef;
import ccmtools.metamodel.ComponentIDL.MHomeDef;
import ccmtools.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.metamodel.ComponentIDL.MSupportsDef;
import ccmtools.metamodel.ComponentIDL.MUsesDef;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.Text;

abstract public class CppGenerator extends CodeGenerator
{

    // reserved words in C++. identifiers that contain these words will be
    // mapped to new identifiers.

    private final static String[] _reserved = {
            "and", "and_eq", "asm", "auto", "bitand", "bitor", "bool", "break",
            "case", "catch", "char", "class", "compl", "const", "const_cast",
            "continue", "default", "delete", "do", "double", "dynamic_cast",
            "else", "enum", "explicit", "export", "extern", "false", "float",
            "for", "friend", "goto", "if", "inline", "int", "long", "mutable",
            "namespace", "new", "not", "not_eq", "operator", "or", "or_eq",
            "private", "protected", "public", "register", "reinterpret_cast",
            "return", "short", "signed", "sizeof", "static", "static_cast",
            "struct", "switch", "template", "this", "throw", "true", "try",
            "typedef", "typeid", "typename", "union", "unsigned", "using",
            "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq"
    };

    // c++ language types that get mapped from corba primitive kinds.

    private final static String[] _language = {
            "", "wamas::platform::utils::Value", // PK_ANY
            "bool", // PK_BOOLEAN
            "char", // PK_CHAR
            "double", // PK_DOUBLE
            "(fixed data type not implemented", // PK_FIXED
            "float", // PK_FLOAT
            "long", // PK_LONG
            "double", // PK_LONGDOUBLE
            "long", // PK_LONGLONG
            "NULL", // PK_NULL
            "::Components::Object*", // PK_OBJREF
            "unsigned char", // PK_OCTET
            "(principal data type not implemented", // PK_PRINCIPAL
            "short", // PK_SHORT
            "std::string", // PK_STRING
            "::Components::TypeCode", // PK_TYPECODE
            "unsigned long", // PK_ULONG
            "unsigned long", // PK_ULONGLONG
            "unsigned short", // PK_USHORT
            "::Components::Object*", // PK_VALUEBASE
            "void", // PK_VOID
            "wchar_t", // PK_WCHAR
            "std::wstring" // PK_WSTRING
    };

    protected final static String CPP_SEQUENCE_TYPE = "std::vector";

    protected List cxxNamespace = null;
    protected List cxxGenNamespace = null;
    protected List corbaStubsNamespace = null;
    protected List remoteNamespace = null;

    
    public CppGenerator(String sublang, UserInterfaceDriver d, File out_dir,
            String[] output_types) throws IOException
    {
        super(sublang, d, out_dir, output_types, _reserved, _language);
        logger = Logger.getLogger("ccm.generator.cpp");
        logger.fine("enter CppGenerator()");
        cxxNamespace = new ArrayList();
        logger.fine("leave CppGenerator()");
    }
    

    
    //====================================================================
    // C++ type and namespace helper methods
    //====================================================================
 
    protected List getLocalCxxGenNamespaceList(MContained node)
    {
        logger.fine("begin");
        List namespaces = new ArrayList();
        namespaces.addAll(cxxGenNamespace);            
        namespaces.addAll(getScope(node));        
        logger.fine("end");
        return namespaces;
    }

    protected String getLocalCxxGenNamespace(MContained node, String separator)
    {
        logger.fine("");
        return separator + Text.joinList(separator, getLocalCxxGenNamespaceList(node)) + separator;
    }
        
    protected String getLocalCxxGenName(MContained node, String separator)
    {
        logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append(getLocalCxxGenNamespace(node, separator));
        code.append(node.getIdentifier());
        logger.fine("end");
        return code.toString() ;
    } 

    protected String getLocalCxxGenIncludeNamespace(MContained node, String separator)
    {
        logger.fine("");
        return Text.joinList(separator, getLocalCxxGenNamespaceList(node)) + separator;
    }

    protected String getLocalCxxGenIncludeName(MContained node, String separator)
    {
        logger.fine("begin");
        StringBuilder out = new StringBuilder();
        if(getLocalCxxGenNamespaceList(node).size() > 0)
        {
            out.append(Text.joinList(separator, getLocalCxxGenNamespaceList(node))).append(separator);
        }
        out.append(node.getIdentifier());
        logger.fine("end");
        return out.toString();    
    } 
    
    protected String getLocalCxxGenIncludeName(MContained node)
    {
        logger.fine("");
        return getLocalCxxGenIncludeName(node, Text.INCLUDE_SEPARATOR);
    }

    
    
    protected List getLocalCxxNamespaceList(MContained node)
    {
        logger.fine("begin");
        List namespaces = new ArrayList();
        namespaces.addAll(cxxNamespace);
        namespaces.addAll(getScope(node));
        logger.fine("end");
        return namespaces;
    }
    
    protected String getLocalCxxNamespace(MContained node, String separator)
    {
        logger.fine("begin");
        StringBuilder out = new StringBuilder();
        out.append(separator);
        if(getLocalCxxNamespaceList(node).size() > 0)
        {
            out.append(Text.joinList(separator, getLocalCxxNamespaceList(node))).append(separator);
        }
        logger.fine("end");
        return out.toString();
    }
    
    protected String getLocalCxxName(MContained node, String separator)
    {
        logger.fine("begin");
        StringBuilder out = new StringBuilder();
        out.append(getLocalCxxNamespace(node, separator)).append(node.getIdentifier());
        logger.fine("end");
        return out.toString() ;
    } 
    
    protected String getLocalCxxIncludeNamespace(MContained node, String separator)
    {
        logger.fine("begin");
        StringBuilder out = new StringBuilder();
        if(getLocalCxxNamespaceList(node).size() > 0)
        {
            out.append(Text.joinList(separator, getLocalCxxNamespaceList(node))).append(separator);
        }
        logger.fine("end");
        return out.toString();
    }
    
    protected String getLocalCxxIncludeName(MContained node, String separator)
    {
//        logger.fine("begin");
//        StringBuilder out = new StringBuilder();
//        if(getLocalCxxNamespaceList(node).size() > 0)
//        {
//            out.append(Text.joinList(separator, getLocalCxxNamespaceList(node))).append(separator);
//        }
//        out.append(node.getIdentifier());
//        logger.fine("end");
//        return out.toString();    
        return getLocalCxxIncludeNamespace(node, separator) + node.getIdentifier();
    } 
    
    protected String getLocalCxxIncludeName(MContained node)
    {
        logger.fine("");
        return getLocalCxxIncludeName(node, Text.INCLUDE_SEPARATOR);
    }
    
       
    /**
     * Overide the CodeGenerator method to return the base types of a
     * MInterface model element.
     */
    protected String joinBaseNames(String sep)
    {
        logger.fine("joinBaseNames()");
        if(currentNode instanceof MInterfaceDef) 
        {
            MInterfaceDef node = (MInterfaceDef) currentNode;
            List names = new ArrayList();
            for(Iterator i = node.getBases().iterator(); i.hasNext();)
            {
                MInterfaceDef iface = (MInterfaceDef)i.next();
                names.add(getLocalCxxNamespace(iface, "::") + "CCM_" + iface.getIdentifier()); 
            }
            return join(sep, names);
        }
        else 
        {
            return "";
        }
    }
   
    
    /**
     * Override the CodeGenerator method to handle local C++ 
     * namespace artifacts.
     */
    protected String handleNamespace(String data_type)
    {
        logger.fine("handleNamespaces()");
        
        List cxxGenModules = new ArrayList();
        cxxGenModules.addAll(cxxGenNamespace);
        cxxGenModules.addAll(namespaceStack);
        
        List cxxModules = new ArrayList();
        cxxModules.addAll(namespaceStack);
        cxxModules.addAll(cxxNamespace);
        
        if(data_type.equals("UsingNamespace")) 
        {
            List tmp = new ArrayList();
            for(Iterator i = cxxModules.iterator(); i.hasNext();)
                tmp.add("using namespace " + i.next() + ";\n");
            return join("", tmp);
        }
        else if(data_type.equals("OpenNamespace")) 
        {
            List tmp = new ArrayList();
            for(Iterator i = cxxModules.iterator(); i.hasNext();)
                tmp.add("namespace " + i.next() + " {\n");
            return join("", tmp);
        }
        else if(data_type.equals("CloseNamespace")) 
        {
            Collections.reverse(cxxModules);
            List tmp = new ArrayList();
            for(Iterator i = cxxModules.iterator(); i.hasNext();)
                tmp.add("} // /namespace " + i.next() + "\n");
            return join("", tmp);
        }        

        if(data_type.equals("UsingGenNamespace")) 
        {
            List moduleList = new ArrayList();
            for(Iterator i = cxxGenModules.iterator(); i.hasNext();)
                moduleList.add("using namespace " + i.next() + ";\n");
            return join("", moduleList);
        }
        else if(data_type.equals("OpenGenNamespace")) 
        {
            List moduleList = new ArrayList();
            for(Iterator i = cxxGenModules.iterator(); i.hasNext();)
                moduleList.add("namespace " + i.next() + " {\n");
            return join("", moduleList);
        }
        else if(data_type.equals("CloseGenNamespace")) 
        {
            Collections.reverse(cxxGenModules);
            List moduleList = new ArrayList();
            for(Iterator i = cxxGenModules.iterator(); i.hasNext();)
                moduleList.add("} // /namespace " + i.next() + "\n");
            return join("", moduleList);
        }        
        return super.handleNamespace(data_type);
    }

       
    /**
     * Overide the CodeGenerator method to use the local C++ 
     * namespaces and identifier.
     */
    protected String getFullScopeIdentifier(MContained node)
    {
        logger.fine("getFullScopeIdentifier()");
        return getLocalCxxGenName(node, Text.SCOPE_SEPARATOR);
    }

    
    /**
     * Overide the CodeGenerator method to use the
     * local C++ namespace.
     */
    protected String getFullScopeInclude(MContained node)
    {
        logger.fine("");
        return getLocalCxxIncludeName(node);
    }
        
        
    /**
     * Implement the abstract method from CodeGenerator and uses
     * local C++ namesapce.
     */
    protected String getScopedInclude(MContained node)
    {
        logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append("#include <").append(getFullScopeInclude(node)).append(".h>\n");
        logger.fine("end");
        return code.toString();
    }

    
    protected String getBaseLanguageType(MTyped object)
    {
        logger.fine("begin");
        StringBuffer code = new StringBuffer();
        MIDLType idl_type = object.getIdlType();
        
        if(idl_type instanceof MContained) 
        {
            MContained content = (MContained) idl_type;
            code.append(getLocalCxxName(content, Text.SCOPE_SEPARATOR));
        }
        else 
        {
            code.append(super.getBaseLanguageType(object));
        }
        logger.fine("end");
        return code.toString();
    }
    
    
    /**
     * Implement the abstract CodeGenerator method to define local C++
     * types (depending on a given model element type).
     */
    public String getLanguageType(MTyped object)
    {
        logger.fine("getLanguageType()");
        MIDLType idl_type = object.getIdlType();
        String base_type = getBaseLanguageType(object);
        
        // Handle interfaces using smart pointers
        // (Any ia handled as interface
        if(idl_type instanceof MPrimitiveDef 
                && ((MPrimitiveDef) idl_type).getKind() == MPrimitiveKind.PK_ANY) 
        {
            // TODO: Use any plugin manager
            base_type = "wamas::platform::utils::SmartPtr< " + base_type + " >";
        }
        else if(idl_type instanceof MInterfaceDef)
        {
            base_type = base_type + "::SmartPtr";
        }

        // This code defines the parameter passing rules for operations:
        //   in : simple types are passed as const values
        //           complex types are passed by const ref
        //   inout : always passed by ref
        //   out : always passed by ref
        if(object instanceof MParameterDef) {
            MParameterDef param = (MParameterDef) object;
            MParameterMode direction = param.getDirection();
            String prefix = "";
            String suffix = "";
            if(direction == MParameterMode.PARAM_IN) { // in
                prefix = "const ";
                if((idl_type instanceof MTypedefDef)
                        || (idl_type instanceof MPrimitiveDef && ((MPrimitiveDef) idl_type)
                                .getKind() == MPrimitiveKind.PK_ANY)
                        || (idl_type instanceof MStringDef)
                        || (idl_type instanceof MWstringDef)
                        || (idl_type instanceof MFixedDef)
                        || (idl_type instanceof MInterfaceDef)) {
                    suffix = "&";
                }
            }
            else { // inout, out
                prefix = "";
                suffix = "&";
            }
            return prefix + base_type + suffix;
        }

        if((object instanceof MAliasDef) && (idl_type instanceof MTyped))
            return getLanguageType((MTyped) idl_type);

        // FIXME : can we implement bounded sequences in C++ ?
        if(object instanceof MSequenceDef)
            return CPP_SEQUENCE_TYPE + "< " + base_type + " > ";

        if(object instanceof MArrayDef) {
            /*
             * This code defines the IDL -> C++ mapping of arrays: long x[7] ->
             * std::vector <long> ... but no bounds checking.
             */

            String result = "std::vector< " + base_type + " > ";
            int dimension = ((MArrayDef) object).getBounds().size();

            if(dimension > 1) {
                result = "std::vector< ";
                for(int i = 1; i < dimension; i++)
                    result += "std::vector< ";
                result += base_type + ">";
                for(int i = 1; i < dimension; i++)
                    result += " >";
            }

            return result + " ";
        }
        return base_type;
    }
    
    
    
    //====================================================================
    // Code generator core methods
    //====================================================================

    /**
     * Acknowledge and process a closing node during graph traversal. If the
     * node is an MContainer type, pop the namespace (this will remove our base
     * namespace that we pushed, in theory (tm)). If the node is of the correct
     * type and defined in the original parsed file, write code for this node.
     * 
     * @param node
     *            the node that the graph traverser object just finished
     *            investigating.
     * @param scope_id
     *            the full scope identifier of the node. This identifier is a
     *            string containing the names of ancestor nodes, joined together
     *            with double colons.
     */
    public void endNode(Object node, String scope_id)
    {
        logger.fine("enter endNode()");
        super.endNode(node, scope_id);
        writeOutputIfNeeded();
        logger.fine("leave endNode()");
    }



    /**
     * Get a local value for the given variable name.
     * 
     * This function performs some common value parsing in the CCM MOF library.
     * More specific value parsing needs to be provided in the subclass for a
     * given language, in the subclass' getLocalValue function. Subclasses
     * should call this function first and then perform any subclass specific
     * value manipulation with the returned value.
     * 
     * @param variable
     *            The variable name to get a value for.
     * @return the value of the variable available from the current
     *         output_variables hash table. Could be an empty string.
     */
    protected String getLocalValue(String variable)
    {
        logger.fine("getLocalValue()");
        String value = super.getLocalValue(variable);

        // Handle simple template %(tag)s 
        if (variable.equals("CcmToolsVersion")) {
            return getCcmToolsVersion();
        }
        else if(variable.equals("CcmGeneratorTimeStamp")) {
            return getCcmGeneratorTimeStamp();
        }
        else if(variable.equals("CcmGeneratorUser")) {
            return getCcmGeneratorUser();
        }
        
        // Handle template %(tag)s depending on the current model node
        if(currentNode instanceof MHomeDef) {
            return data_MHomeDef(variable, value);
        }
        else if(currentNode instanceof MComponentDef) {
            return data_MComponentDef(variable, value);
        }
        else if(currentNode instanceof MFactoryDef) {
            return data_MFactoryDef(variable, value);
        }
        else if(currentNode instanceof MFinderDef) {
            return data_MFinderDef(variable, value);
        }
        else if(currentNode instanceof MProvidesDef) {
            return data_MProvidesDef(variable, value);
        }
        else if(currentNode instanceof MSupportsDef) {
            return data_MSupportsDef(variable, value);
        }
        else if(currentNode instanceof MUsesDef) {
            return data_MUsesDef(variable, value);
        }
        else if(currentNode instanceof MInterfaceDef) {
            return data_MInterfaceDef(variable, value);
        }
        else if(currentNode instanceof MAttributeDef) {
            return data_MAttributeDef(variable, value);
        }
        else if(currentNode instanceof MOperationDef) {
            return data_MOperationDef(variable, value);
        }
        else if(currentNode instanceof MExceptionDef) {
            return data_MExceptionDef(variable, value);
        }
        else if(currentNode instanceof MEnumDef) {
            return data_MEnumDef(variable, value);
        }
        else if(currentNode instanceof MAliasDef) {
            return data_MAliasDef(variable, value);
        }

        return value;
    }

    
    /**
     * Load an appropriate template (based on the value in the template_name
     * argument) for the given child, and fill out its variable information.
     * 
     * @param child
     *            MInterfaceDef node to gather information from.
     * @param template_name
     *            the name of the template to load for variable substitution.
     * @param attribute
     *            true if we should fill in attribute information. If false,
     *            then we will fill in operation information.
     * @return a string containing the variable-substituted template requested.
     */
    protected String fillTwoStepTemplates(MInterfaceDef child,
                                          String template_name,
                                          boolean attribute)
    {
        logger.fine("enter fillTwoStepTemplates()");
        MContained contained = (MContained) currentNode;
        // if this is a supports node, we want to actually refer to the
        // home or component that owns this supports definition.
        if(currentNode instanceof MSupportsDef) {
            MContained tmp = ((MSupportsDef) contained).getComponent();
            if(tmp == null)
                tmp = ((MSupportsDef) contained).getHome();
            contained = tmp;
        }

        // we need to recursively include all base interfaces here. we'll use a
        // stack and pop off the most recent element, add operation information
        // for that interface, then push on all the bases for that interface.
        // lather, rinse, repeat.
        Stack ifaces = new Stack();
        ifaces.push(child);

        StringBuffer code = new StringBuffer();

        while(!ifaces.empty()) {
            MInterfaceDef iface = (MInterfaceDef) ifaces.pop();
            List contents = 
                iface.getFilteredContents(
                    (attribute) ? MDefinitionKind.DK_ATTRIBUTE
                                : MDefinitionKind.DK_OPERATION,false);

            for(Iterator c = contents.iterator(); c.hasNext();) {
                Map vars = null;
                if(attribute)
                    vars = getTwoStepAttributeVariables((MAttributeDef) c.next(), contained);
                else
                    vars = getTwoStepOperationVariables((MOperationDef) c.next(), contained);

                Template template = template_manager.getRawTemplate(template_name);
                code.append(template.substituteVariables(vars));
            }

            for(Iterator i = iface.getBases().iterator(); i.hasNext();)
                ifaces.push(i.next());
        }
        logger.fine("leave fillTwoStepTemplates()");
        return code.toString();
    }


    /**
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function. This function handles attributes.
     * 
     * @param iface
     *            the interface from which we're starting the two step
     *            operation.
     * @param attr
     *            the particular interface attribute that we're filling in a
     *            template for.
     * @param contained
     *            the container in which the given interface is defined.
     * @return a map containing the keys and values needed to fill in the
     *         template for this interface.
     */
    protected Map getTwoStepAttributeVariables(MAttributeDef attr,
                                               MContained contained)
    {
        logger.fine("enter getTwoStepAttributeVariables()");
        String lang_type = getLanguageType(attr);
        Map vars = new Hashtable();

        vars.put("Object", contained.getIdentifier());
        vars.put("Identifier", attr.getIdentifier());
        vars.put("LanguageType", lang_type);
        
        logger.fine("leave getTwoStepAttributeVariables()");
        return vars;
    }

    
    /**
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function. This version of the function handles
     * operations from the given interface.
     * 
     * @param operation
     *            the particular interface operation that we're filling in a
     *            template for.
     * @param container
     *            the container in which the given interface is defined.
     * @return a map containing the keys and values needed to fill in the
     *         template for this interface.
     */
    abstract protected Map getTwoStepOperationVariables(MOperationDef op,
                                                        MContained contained);

    
    
    //====================================================================
    // Node specific template %(tag)s handling
    //====================================================================
    
    protected String data_MAliasDef(String data_type, String data_value)
    {
        logger.fine("data_MAliasDef()");
        MIDLType idl_type = ((MAliasDef) currentNode).getIdlType();

        if(data_type.equals("FirstBound")) {
            return "" + ((MArrayDef) idl_type).getBounds().get(0);
        }
        else if(data_type.equals("AllBounds")) {
            MArrayDef array = (MArrayDef) idl_type;
            String result = "";
            for(Iterator i = array.getBounds().iterator(); i.hasNext();)
                result += "[" + (Long) i.next() + "]";
            return result;
        }

        return data_value;
    }

    protected String data_MComponentDef(String data_type, String data_value)
    {
        logger.fine("data_MComponentDef()");
        MComponentDef component = (MComponentDef) currentNode;
        MHomeDef home = null;

        try 
        {
            home = (MHomeDef) component.getHomes().iterator().next();
            if(home == null)
                throw new RuntimeException();
        }
        catch(Exception e) 
        {
            throw new RuntimeException("Component '" + component.getIdentifier()
                    + "' does not have exactly one home.");
        }

        if(data_type.equals("HomeType")) 
        {
            return home.getIdentifier();
        }
        if(data_type.equals("LocalNamespace")) 
        {
          return getLocalCxxNamespace(component, Text.SCOPE_SEPARATOR);
        }
        else if(data_type.equals("LocalGenNamespace")) 
        {
            return getLocalCxxGenNamespace(component, Text.SCOPE_SEPARATOR);
        }
        else if(data_type.equals("LocalNamespaceMangled")) 
        {
            return getLocalCxxGenNamespace(component, Text.MANGLING_SEPARATOR);
        }
        else if(data_type.endsWith("Namespace")) 
        {
            return handleNamespace(data_type);
        }
        else if(data_type.equals("HomeInclude")) 
        {
            return getLocalCxxIncludeName(home);
//            String include = getFullScopeInclude(component);
//            include = include.substring(0, include.lastIndexOf(Text.INCLUDE_SEPARATOR));
//            return include + Text.INCLUDE_SEPARATOR + home.getIdentifier();
        }
        else if(data_type.equals("GenHomeInclude")) 
        {
            return getLocalCxxGenIncludeName(home);
//            String include = getFullScopeInclude(component);
//            include = include.substring(0, include.lastIndexOf(Text.INCLUDE_SEPARATOR));
//            return include + Text.INCLUDE_SEPARATOR + home.getIdentifier();
        }
        else if(data_type.equals("ComponentInclude")) 
        {
            return getLocalCxxIncludeName(component);
        }
        else if(data_type.equals("GenComponentInclude")) 
        {
            
            return getLocalCxxGenIncludeName(component);
        }          
        else if(data_type.endsWith("AbsoluteLocalHomeName")) 
        {
            return getLocalCxxIncludeName(home, Text.MANGLING_SEPARATOR);
        }
        // TODO:
        return data_MInterfaceDef(data_type, data_value);
    }

    protected String data_MEnumDef(String data_type, String data_value)
    {
        logger.fine("data_MEnumDef()");
        if(data_type.equals("Members")) {
            List b = new ArrayList();
            MEnumDef enumDef = (MEnumDef) currentNode;
            for(Iterator i = enumDef.getMembers().iterator(); i.hasNext();)
                b.add((String) i.next());
            return join(", ", b);
        }
        return data_value;
    }

    protected String data_MFactoryDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MFactoryDef()");
        MFactoryDef factory = (MFactoryDef)currentNode;
        MHomeDef home = factory.getHome();
        MComponentDef component = home.getComponent();
        
        if(dataType.equals("ComponentType")) 
        {
            dataValue = component.getIdentifier();
        }
        if(dataType.equals("GenCCMComponentType")) 
        {
            dataValue = getLocalCxxGenNamespace(component, Text.SCOPE_SEPARATOR) +
                            "CCM_" + component.getIdentifier();
        }
        if(dataType.equals("CCMComponentType")) 
        {
            dataValue = getLocalCxxNamespace(component, Text.SCOPE_SEPARATOR) +
                            "CCM_" + component.getIdentifier();
        }
        else if(dataType.equals("HomeType")) 
        {
            dataValue = home.getIdentifier();
        }
        else if(dataType.equals("GenCCMHomeType")) 
        {
            dataValue = getLocalCxxGenNamespace(home, Text.SCOPE_SEPARATOR) + 
                            "CCM_" + home.getIdentifier();
        }
        else 
        {
            dataValue = data_MOperationDef(dataType, dataValue);
        }
        logger.fine("leave data_MFactoryDef()");
        return dataValue;
    }

    protected String data_MFinderDef(String data_type, String data_value)
    {
        logger.fine("data_MFinderDef()");
        return data_MOperationDef(data_type, data_value);
    }

    protected String data_MHomeDef(String data_type, String data_value)
    {
        logger.fine("data_MHomeDef()");
        MHomeDef home = (MHomeDef) currentNode;
        MComponentDef component = home.getComponent();

        String home_id = home.getIdentifier();
        String component_id = component.getIdentifier();

        if(data_type.equals("LocalNamespace")) 
        {
            return getLocalCxxNamespace(home, Text.SCOPE_SEPARATOR);
        }
        else if(data_type.equals("LocalGenNamespace")) 
        {
            return getLocalCxxGenNamespace(home, Text.SCOPE_SEPARATOR);
        }
        else if(data_type.endsWith("Namespace")) 
        {
            return handleNamespace(data_type);
        }
        else if(data_type.equals("HomeInclude")) 
        {
            return getLocalCxxIncludeName(home);
        }
        else if(data_type.equals("GenHomeInclude")) 
        {
            return getLocalCxxGenIncludeName(home);
        }        
        else if(data_type.equals("ComponentInclude")) 
        {
            return getLocalCxxGenNamespace(component, Text.INCLUDE_SEPARATOR);
        }
        else if(data_type.equals("ComponentType")) 
        {
            return component.getIdentifier();
        }                         
        else if(data_type.equals("LocalGenComponent")) 
        {
            return getLocalCxxGenNamespace(component, Text.SCOPE_SEPARATOR);
        }        
        else if(data_type.endsWith("AbsoluteLocalHomeName")) 
        {
            return getLocalCxxIncludeName(home, Text.MANGLING_SEPARATOR);
        }
        return data_MInterfaceDef(data_type, data_value);
    }

    protected String data_MInterfaceDef(String data_type, String data_value)
    {
        logger.fine("data_MInterfaceDef()");
        if(data_type.equals("BaseType")) {
            String base = joinBaseNames(", virtual public ");
            if(base.length() > 0)
                return ", virtual public " + base;
        }
        return data_value;
    }

    protected String data_MAttributeDef(String dataType, String dataValue)
    {
        logger.fine("data_MAttributeDef()");
        MAttributeDef attr = (MAttributeDef)currentNode;
        
        if(dataType.equals("DefinedInIdentifier")) 
        {
            dataValue = attr.getDefinedIn().getIdentifier();
        }
        else if(dataType.equals("ComponentIdentifier"))
        {
            MContainer cont = ((MAttributeDef) currentNode).getDefinedIn();
            if(cont instanceof MComponentDef)
            {
                MComponentDef component = (MComponentDef) cont;
                dataValue = component.getIdentifier();
            }            
        }
        return dataValue;
    }
        
    protected String data_MOperationDef(String data_type, String data_value)
    {
        logger.fine("data_MOperationDef()");
        if(data_type.equals("MExceptionDefThrows") && data_value.endsWith(", ")) {
            return "throw (Components::CCMException, "
                    + data_value.substring(0, data_value.length() - 2) + " )";
        }
        else if(data_type.equals("OperationParameterList")) {
            MOperationDef op = (MOperationDef) currentNode;
            return getOperationParams(op);
        }
        else if(data_type.startsWith("MParameterDef")
                && data_value.endsWith(", ")) {
            return data_value.substring(0, data_value.length() - 2);
        }
        else if(data_type.equals("UsesIdentifier")) {
            MOperationDef op = (MOperationDef) currentNode;
            if(op.getDefinedIn() instanceof MUsesDef)
                return op.getDefinedIn().getIdentifier();
        }
        else if(data_type.equals("ProvidesIdentifier")) {
            MOperationDef op = (MOperationDef) currentNode;
            if(op.getDefinedIn() instanceof MProvidesDef)
                return op.getDefinedIn().getIdentifier();
        }
        return data_value;
    }

    
    protected String data_MExceptionDef(String data_type, String data_value)
    {
        logger.fine("enter data_MExceptionDef()");
	    MExceptionDef exception = (MExceptionDef)currentNode;
	    
        if(data_type.equals("ExceptionInclude")) 
        {
            StringBuffer code = new StringBuffer();
            code.append("#include <").append(getLocalCxxIncludeName(exception)).append(".h>\n");
//            code.append(getLocalCxxNamespace(exception, Text.INCLUDE_SEPARATOR));
//            code.append(exception.getIdentifier());
//            code.append(".h>\n");
            data_value = code.toString();
        }
        logger.fine("leave data_MExceptionDef()");
        return data_value;
    }
    
    
    protected String data_MProvidesDef(String data_type, String data_value)
    {
        logger.fine("data_MProvidesDef()");
        MInterfaceDef iface = ((MProvidesDef) currentNode).getProvides();
        MProvidesDef provides = (MProvidesDef) currentNode;
        
        if(data_type.equals("CCMProvidesType")) 
        {
            return getLocalCxxNamespace(iface, "::") + "CCM_" + iface.getIdentifier();
        }
        else if(data_type.equals("ProvidesType")) 
        {
            return getLocalCxxName(iface, Text.SCOPE_SEPARATOR);
        }
        else if(data_type.equals("GenProvidesType")) 
        {
            return getLocalCxxGenName(iface, Text.SCOPE_SEPARATOR);
        }        
        else if(data_type.equals("GenProvidesInclude"))
        {
            return getLocalCxxGenIncludeName(iface);
        }
        else if(data_type.startsWith("MOperation")) 
        {
            return fillTwoStepTemplates(iface, data_type, false);
        }
        else if(data_type.startsWith("MAttribute")) 
        {
            return fillTwoStepTemplates(iface, data_type, true);
        }
        else if(data_type.equals("ComponentType")) 
        {
            MComponentDef component = provides.getComponent();
            return component.getIdentifier();
        }
        else if(data_type.startsWith("FullComponentType")) 
        {
            // Return full scoped component type
            MComponentDef component = provides.getComponent();
            StringBuffer code = new StringBuffer();
            code.append(getLocalCxxGenNamespace(component, Text.SCOPE_SEPARATOR));
            code.append("::CCM_");
            code.append(component.getIdentifier());
            return code.toString();
        }
        else if(data_type.startsWith("OpenNamespace")
                || (data_type.startsWith("CloseNamespace"))) {
            // Add component Namespace to facet impl class files
            MComponentDef component = ((MProvidesDef) currentNode).getComponent();
            return handleNamespace(data_type);
        }
        return data_value;
    }

    
    protected String data_MSupportsDef(String data_type, String data_value)
    {
        logger.fine("data_MSupportsDef()");
        MInterfaceDef iface = ((MSupportsDef) currentNode).getSupports();

        if(data_type.equals("GenSupportsInclude"))
        {
            return getLocalCxxGenIncludeName(iface);
        }
        else if(data_type.equals("SupportsType")) 
        {
          //  return iface.getIdentifier();
            return getLocalCxxName(iface, Text.SCOPE_SEPARATOR);
        }
        if(data_type.equals("GenSupportsType")) 
        {
          //  return iface.getIdentifier();
            return getLocalCxxGenName(iface, Text.SCOPE_SEPARATOR);
        }
        else if(data_type.equals("CCMSupportsType")) 
        {
            return getLocalCxxNamespace(iface, Text.SCOPE_SEPARATOR) + "CCM_" + iface.getIdentifier();
        }
        else if(data_type.startsWith("MOperation")) 
        {
            return fillTwoStepTemplates(iface, data_type, false);
        }
        else if(data_type.startsWith("MAttribute")) 
        {
            return fillTwoStepTemplates(iface, data_type, true);
        }

        return data_value;
    }

    
    protected String data_MUsesDef(String data_type, String data_value)
    {
        logger.fine("data_MUsesDef()");
        MInterfaceDef iface = ((MUsesDef) currentNode).getUses();

        if(data_type.equals("CCMUsesType")) 
        {
            return getLocalCxxNamespace(iface, Text.SCOPE_SEPARATOR) + "CCM_" + iface.getIdentifier();
        }
        else if(data_type.equals("UsesType")) 
        {
            return getLocalCxxName(iface, Text.SCOPE_SEPARATOR);
        }
        else if(data_type.equals("GenUsesName")) 
        {
            return getLocalCxxGenName((MUsesDef)currentNode, Text.SCOPE_SEPARATOR);
        }
        else if(data_type.equals("UsesName")) 
        {
            return getLocalCxxName((MUsesDef)currentNode, Text.SCOPE_SEPARATOR);
        }
        else if(data_type.startsWith("MOperation")) 
        {
            return fillTwoStepTemplates(iface, data_type, false);
        }
        else if(data_type.startsWith("MAttribute")) 
        {
            return fillTwoStepTemplates(iface, data_type, true);
        }
        else if(data_type.equals("ComponentType")) 
        {
            MUsesDef uses = (MUsesDef)currentNode;
            MComponentDef component = uses.getComponent();
            return component.getIdentifier();
        }

        return data_value;
    }

    
    
    
    
    //====================================================================
    // Simple template %(tag)s helper methods
    //====================================================================
    
    public String getCcmToolsVersion()
    {
        logger.fine("enter getCcmToolsVersion()");
        StringBuffer buffer = new StringBuffer();
        buffer.append("CCM Tools version ");
        buffer.append(ccmtools.Constants.VERSION);
        logger.fine("leave getCcmToolsVersion()");
        return buffer.toString();
    }
    
    public String getCcmGeneratorTimeStamp() 
    {
        logger.fine("enter getCcmGeneratorTimeStamp()");
        StringBuffer buffer = new StringBuffer();
        Calendar now = Calendar.getInstance();
        buffer.append(now.getTime());
        logger.fine("leave getCcmGeneratorTimeStamp()");
        return buffer.toString();
    }
    
    public String getCcmGeneratorUser() 
    {
        logger.fine("enter getCcmGeneratorUser()");
        StringBuffer buffer = new StringBuffer();
        buffer.append(System.getProperty("user.name"));
        logger.fine("leave getCcmGeneratorUser()");
        return buffer.toString();
    }

    
    /**
     * Get type and name information about the parameters for the given
     * operation. This will return a comma-separated string, i.e. <type1>
     * <name1>, <type2><name2>, ... ,<typeN><nameN>for this operation's
     * parameters.
     * 
     * @param op
     *            the operation to investigate.
     * @return a comma separated string of the parameter information requested
     *         for this operation.
     */
    protected String getOperationParams(MOperationDef op)
    {
        logger.fine("getOperationParams()");
        List parameterList = new ArrayList();
        for(Iterator ps = op.getParameters().iterator(); ps.hasNext();) {
            MParameterDef p = (MParameterDef) ps.next();
            parameterList.add(getLanguageType(p) + " " + p.getIdentifier());
        }
        String indent = "\n" + Text.tab(2);
        if(parameterList.size() > 1) {
            return indent + Text.join("," + indent, parameterList);
        }
        else {
            return Text.join(", ", parameterList);
        }
    }

    
    /**
     * Get name information about the parameters for the given operation. This
     * will return a comma-separated string, i.e. <name1>, <name2>, ... ,
     * <nameN>for this operation's parameters.
     * 
     * @param op
     *            the operation to investigate.
     * @return a comma separated string of the parameter information requested
     *         for this operation.
     */
    protected String getOperationParamNames(MOperationDef op)
    {
        logger.fine("enter getOperationParamNames()");
        List ret = new ArrayList();
        for(Iterator ps = op.getParameters().iterator(); ps.hasNext();)
            ret.add(((MParameterDef) ps.next()).getIdentifier());
        logger.fine("leave getOperationParamNames()");
        return join(", ", ret);
    }

    /**
     * Get the exceptions thrown by the given operation.
     * 
     * @param op
     *            the operation to investigate.
     * @return a string containing C++ code describing the exceptions that this
     *         operation throws. If there are no exceptions, this returns an
     *         empty string. If there are exceptions, it returns something like
     *         "throw ( exception, exception, ... )".
     */
    protected String getOperationExcepts(MOperationDef op)
    {
        logger.fine("getOperationExcepts()");
        List ret = new ArrayList();
        for(Iterator es = op.getExceptionDefs().iterator(); es.hasNext();) 
        {
            MExceptionDef exception = (MExceptionDef)es.next();
            String code = getLocalCxxName(exception, Text.SCOPE_SEPARATOR);
            ret.add(code);
        }
        if(ret.size() > 0) 
        {
            String indent = "\n" + Text.tab(2);
            return "throw(::Components::CCMException," + indent	+ join("," + indent, ret) + " )";
        }
        else 
        {
            return "throw(::Components::CCMException)";
        }
    }
}

