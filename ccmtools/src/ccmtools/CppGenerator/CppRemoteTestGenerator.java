/*
 * CCM Tools : C++ Code Generator Library Egon Teiniker
 * <egon.teiniker@tugraz.at> copyright (c) 2002, 2003 Salomon Automation
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

import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.UI.Driver;
import ccmtools.utils.Debug;
import ccmtools.utils.Text;

public class CppRemoteTestGenerator 
    extends CppRemoteGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.
    private final static String[] REMOTE_TEST_OUTPUT_TEMPLATE_TYPES = {
        "MComponentDef"
    };

    
    public CppRemoteTestGenerator(Driver uiDriver, File outDir)
        throws IOException
    {
        super("CppRemoteTest", uiDriver, outDir, REMOTE_TEST_OUTPUT_TEMPLATE_TYPES);
    }


    /**
     * Write generated code to an output file.
     * 
     * @param template
     *            the template object to get the generated code structure from ;
     *            variable values should come from the node handler object.
     */
    public void writeOutput(Template template) throws IOException
    {
        Debug.println(Debug.METHODS, "writeOutput(" + template + ")");

        String generated_code = template.substituteVariables(output_variables);

        if(generated_code.trim().equals(""))
            return;

      //  String node_name = ((MContained) currentNode).getIdentifier();
        String namespace = getRemoteNamespace(((MContained) currentNode),Text.MANGLING_SEPARATOR);
        String file_dir = "test";
        String file_name = "_check_"
                            + namespace.substring(0, namespace.length()-1)
                            + ".cc";

        writeFinalizedFile(file_dir, file_name, generated_code);

        // generate an empty Makefile.py in the CCM_Test
        // directory - needed by Confix
        writeFinalizedFile(file_dir, "Makefile.py", "");
    }

    /** *********************************************************************** */

//    protected Map getTwoStepOperationVariables(MOperationDef operation,
//                                               MContained container)
//    {
//        String lang_type = getLanguageType(operation);
//        Map vars = new Hashtable();
//
//        vars.put("Object", container.getIdentifier());
//        vars.put("Identifier", operation.getIdentifier());
//        vars.put("LanguageType", lang_type);
//        vars.put("MExceptionDefThrows", getOperationExcepts(operation));
//        vars.put("MParameterDefAll", getOperationParams(operation));
//        vars.put("MParameterDefName", getOperationParamNames(operation));
//
//        if(!lang_type.equals("void"))
//            vars.put("Return", "return ");
//        else
//            vars.put("Return", "");
//
//        return vars;
//    }

//    protected String data_MComponentDef(String data_type, String data_value)
//    {
//        MComponentDef component = (MComponentDef) currentNode;
//        MHomeDef home = null;
//
//        try {
//            home = (MHomeDef) component.getHomes().iterator().next();
//            if(home == null)
//                throw new RuntimeException();
//        }
//        catch(Exception e) {
//            throw new RuntimeException("Component '"
//                    + component.getIdentifier()
//                    + "' does not have exactly one home.");
//        }
//        List HomeScope = getScope((MContained) home);
//        List ComponentScope = getScope((MContained) component);
//
//        if(data_type.equals("HomeType")) {
//            return home.getIdentifier();
//        }
//        else if(data_type.equals("IdlHomeType")) {
//            if(HomeScope.size() > 0)
//                return getCorbaStubsNamespace("::") + join("::", HomeScope)
//                        + "::" + home.getIdentifier();
//            else
//                return getCorbaStubsNamespace("::") + home.getIdentifier();
//        }
//        else if(data_type.equals("Identifier")) {
//            return component.getIdentifier();
//        }
//        else if(data_type.equals("IdlIdentifier")) {
//            if(ComponentScope.size() > 0)
//                return getCorbaStubsNamespace("::")
//                        + join("::", ComponentScope) + "::"
//                        + component.getIdentifier();
//            else
//                return getCorbaStubsNamespace("::") + component.getIdentifier();
//        }
//        else if(data_type.endsWith("AbsoluteRemoteHomeName")) {
//            return getRemoteName(home,"_","");
//        }
//        else if(data_type.endsWith("AbsoluteLocalHomeName")) {
//            return getLocalName(home,"_");
//        }
//        return super.data_MComponentDef(data_type, data_value);
//    }

//    protected String data_MProvidesDef(String data_type, String data_value)
//    {
//        MInterfaceDef iface = ((MProvidesDef) currentNode).getProvides();
//        MComponentDef component = ((MProvidesDef) currentNode).getComponent();
//        List scope = getScope((MContained) iface);
//
//        if(data_type.equals("IdlProvidesType")) {
//            if(scope.size() > 0)
//                return getCorbaStubsNamespace("::") + join("::", scope) + "::"
//                        + iface.getIdentifier();
//            else
//                return getCorbaStubsNamespace("::") + iface.getIdentifier();
//        }
//        else if(data_type.equals("ProvidesType")) {
//            return iface.getIdentifier();
//        }
//        else if(data_type.equals("ComponentType")) {
//            return component.getIdentifier();
//        }
//        return super.data_MProvidesDef(data_type, data_value);
//    }
}