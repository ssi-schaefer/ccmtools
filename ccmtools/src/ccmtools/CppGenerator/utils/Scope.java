/*
 * CCM Tools : C++ Code Generator Library 
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

package ccmtools.CppGenerator.utils;


/**
 * This utility class contains methods to handle the scope of IDL3 model elements
 * (including namespaces and names).
 * 
 * 
 */
public class Scope
{   
   
    /**
     * Join the base names of the current node using the given string as a
     * separator. The current node should be an instance of MInterfaceDef.
     * 
     * @param sep
     *            the separator to use between base names.
     * @return a string containing the names of base interfaces, separated by
     *         sep.
     */
//    protected static String joinBaseNames(MContained currentNode, String sep)
//    {
//        if(currentNode instanceof MInterfaceDef) {
//            MInterfaceDef node = (MInterfaceDef)currentNode;
//            ArrayList names = new ArrayList();
//            for(Iterator i = node.getBases().iterator(); i.hasNext();)
//                names.add(((MInterfaceDef) i.next()).getIdentifier());
//            return Text.join(sep, names);
//        }
//        else {
//            // Only InterfaceDef and derived types (ComponentDef, HomeDef)
//            // can have base names.
//            return "";  
//        }
//    }   
    
//    public static List getScope(MContained node)
//    {
//        List scope = new ArrayList();
//        MContainer c = node.getDefinedIn();
//        while(c.getDefinedIn() != null) {
//            if(c instanceof MModuleDef) {
//                scope.add(0, c.getIdentifier());
//            }
//            c = c.getDefinedIn();
//        }
//        return scope;
//    }
//    
//    public static String getScopedNamespace(List baseNamespace, 
//                                            MContained contained, 
//                                            String separator, String local)
//    {
//        StringBuffer buffer = new StringBuffer();
//        List scope = getScope(contained);
//
//        if (local.length() > 0) {
//            scope.add("CCM_Session_" + local);
//        }
//        buffer.append(Text.join(separator, baseNamespace));
//        buffer.append(separator);
//        if (scope.size() > 0) {
//            buffer.append(Text.join(separator, scope));
//            buffer.append(separator);
//        }
//        return buffer.toString();
//    }

//    public static String getScopedName(List baseNamespace, 
//                                       MContained contained, 
//                                       String separator, 
//                                       String local)
//    {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(getScopedNamespace(baseNamespace, contained, separator, local));
//        buffer.append(contained.getIdentifier());
//        return buffer.toString();
//    }    
    
//    /**
//     * Generate the namespace for ccmDebug() methods.
//     * For model elements not derived from MContained the predefined
//     * CCM_Local::ccmDebug() methods will be used (defined in the 
//     * cpp_environment).
//     * 
//     * @param baseNamespace List of predefined namespaces (e.g. CCM_Local)
//     * @param idlType IDL type of the current model element.
//     * @return A string containing the ccmDebug() method's namespace of 
//     * the current model element.
//     */
//    public static String getDebugNamespace(List baseNamespace, 
//                                           MIDLType idlType)
//    {
//        if(idlType instanceof MAliasDef) {
//            MTyped type = (MTyped) idlType;
//            MIDLType innerIdlType = type.getIdlType();
//            if(innerIdlType instanceof MPrimitiveDef 
//                    || innerIdlType instanceof MStringDef
//                    || innerIdlType instanceof MWstringDef) {
//                return Text.join("::", baseNamespace) + "::";
//            }
//            else {
//                return getScopedNamespace(baseNamespace, 
//                                          (MContained)idlType, "::","");
//            }
//        }
//        else if(idlType instanceof MContained) {
//            return getScopedNamespace(baseNamespace, 
//                                      (MContained)idlType, "::","");
//    	}
//        else {
//            return Text.join("::", baseNamespace) + "::";
//        }
//    }
    
    
    // ------------------------------------------------------------------------
    // CppLocalGenerator
    // ------------------------------------------------------------------------
    
    
    
//    // TODO: Handle scope in terms of getScopedNamespace() 
//    public static String handleNamespace(Stack namespaceStack, 
//                                         String dataType, String local)
//    {
//        List tmp = new ArrayList();
//        List names = new ArrayList(namespaceStack);
//
//        if(!local.equals(""))
//            names.add("CCM_Session_" + local);
//
//        if(dataType.equals("Namespace")) {
//            return Text.join(Text.SCOPE_SEPARATOR, names);
//        }
//        else if(dataType.equals("IncludeNamespace")) {
//            return Text.join(Text.FILE_SEPARATOR, names);
//        }
//        else if(dataType.equals("UsingNamespace")) {
//            for(Iterator i = names.iterator(); i.hasNext();)
//                tmp.add("using namespace " + i.next() + ";\n");
//            return Text.join("", tmp);
//        }
//        else if(dataType.equals("OpenNamespace")) {
//            for(Iterator i = names.iterator(); i.hasNext();)
//                tmp.add("namespace " + i.next() + " {\n");
//            return Text.join("", tmp);
//        }
//        else if(dataType.equals("CloseNamespace")) {
//            Collections.reverse(names);
//            for(Iterator i = names.iterator(); i.hasNext();)
//                tmp.add("} // /namespace " + i.next() + "\n");
//            return Text.join("", tmp);
//        }
//        
//        else {
//            return "";
//        }
//    }    
//    
    
    
    
    
    
    // ------------------------------------------------------------------------
    // CppRemoteGenerator
    // ------------------------------------------------------------------------
       
    
//    /**
//     * Collect all defined CORBA Stub prefixes into a single string. All CORBA
//     * Stub prefixes are stored in a class attribute list called
//     * CorbaStubsNamespace which is filled in the constructor.
//     * 
//     * @param separator
//     *            A separator string that is used between two list entries
//     *            (example "::"). Example: {"CORBA_Stubs"} -> "CORBA_Stubs::"
//     */
//    public static String getCorbaStubsNamespace(List corbaStubsNamespace,
//                                            MContained contained, 
//                                            String separator)
//    {
//        List scope = getScope(contained);
//        StringBuffer buffer = new StringBuffer();
//        if(corbaStubsNamespace.size() > 0) {
//            buffer.append(Text.join(separator, corbaStubsNamespace));
//            buffer.append(separator);
//        }
//        if (scope.size() > 0) {
//            buffer.append(Text.join(separator, scope));
//            buffer.append(separator);
//        }
//        return buffer.toString();
//    }
//
//    
//    public static String getCorbaStubName(List corbaStubsNamespace,
//                                      MContained contained, 
//                                      String separator)
//    {
//        List scope = getScope(contained);
//        StringBuffer buffer = new StringBuffer();
//        if(corbaStubsNamespace.size() > 0) {
//            buffer.append(Text.join(separator, corbaStubsNamespace));
//            buffer.append(separator);
//        }
//        if (scope.size() > 0) {
//            buffer.append(Text.join(separator, scope));
//            buffer.append(separator);
//        }
//        buffer.append(contained.getIdentifier());
//        return buffer.toString();
//    }
//    
//    
//    public static String getLocalNamespace(List localNamespace,
//                                              MContained contained, 
//                                              String separator, 
//                                              String local)
//    {
//        List scope = getScope(contained);
//        if (local.length() > 0) {
//            scope.add("CCM_Session_" + local);
//        }
//        
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(Text.join(separator, localNamespace));
//        buffer.append(separator);
//        if (scope.size() > 0) {
//            buffer.append(Text.join(separator, scope));
//            buffer.append(separator);
//        }
//        return buffer.toString();
//    }
//    
//    
//    public static String getLocalName(List localNamespace,
//                                      MContained contained, 
//                                      String separator)
//    {
//        List scope = getScope(contained);
//        StringBuffer buffer = new StringBuffer();
//
//        buffer.append(Text.join(separator, localNamespace));
//        buffer.append(separator);
//        if (scope.size() > 0) {
//            buffer.append(Text.join(separator, scope));
//            buffer.append(separator);
//        }
//        buffer.append(contained.getIdentifier());
//        return buffer.toString();
//    }
//
//    
//    public static String getRemoteNamespace(Stack namespaceStack,
//                                            String separator, 
//                                            String local)
//    {
//        List names = new ArrayList(namespaceStack);
//        if (local.length() > 0) {
//            names.add("CCM_Session_" + local);
//        }
//
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(separator);
//        if (names.size() > 1) {
//            buffer.append(Text.join(separator, Text.slice(names, 0)));
//            buffer.append(separator);
//        }
//        else {
//            // no additional namespace
//        }
//        return buffer.toString();
//    }
//
//    
//    public static String getRemoteName(List baseNamespace,
//                                   MContained contained, 
//                                   String separator,
//                                   String local)
//    {
//        List scope = getScope(contained);
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(Text.join(separator, baseNamespace));
//        buffer.append(separator);
//        if(scope.size() > 0) {
//            buffer.append(Text.join(separator, scope));
//            buffer.append(separator);
//        }
//        buffer.append(contained.getIdentifier());
//        return buffer.toString();
//    }
}
