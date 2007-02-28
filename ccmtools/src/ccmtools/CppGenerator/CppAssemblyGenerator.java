/*
 * Created on Feb 20, 2007
 * 
 * R&D Salomon Automation (http://www.salomon.at)
 * 
 * Robert Lechner (robert.lechner@salomon.at)
 * 
 * $Id$
 */
package ccmtools.CppGenerator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import ccmtools.CcmtoolsException;
import ccmtools.parser.assembly.metamodel.Assembly;
import ccmtools.parser.assembly.metamodel.AssemblyElement;
import ccmtools.parser.assembly.metamodel.Attribute;
import ccmtools.parser.assembly.metamodel.Component;
import ccmtools.parser.assembly.metamodel.Connection;
import ccmtools.parser.assembly.metamodel.Constant;
import ccmtools.parser.assembly.metamodel.Model;
import ccmtools.parser.assembly.metamodel.Port;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.BaseIDL.MAttributeDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MContained;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MOperationDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MHomeDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.Text;

/**
 * local C++ assembly generator
 */
public class CppAssemblyGenerator extends CppLocalGenerator
{
    /**
     * creates the local C++ assembly generator
     * 
     * @param uiDriver user interface driver
     * @param out_dir the directory that will be the root of the output source tree
     * @param assemblies the assembly model
     */
    public CppAssemblyGenerator( UserInterfaceDriver uiDriver, File outDir, Model assemblies )
            throws IOException, CcmtoolsException
    {
        super(uiDriver, outDir);
        logger = Logger.getLogger("ccm.generator.cpp.assembly");
        this.assemblies = assemblies;
        setFlag(FLAG_APPLICATION_FILES);
    }

    /**
     * the assembly model
     */
    protected Model assemblies;

    /**
     * the assembly description of the current component (or null)
     */
    protected Assembly currentAssembly;

    /**
     * Create a list of lists of pathname components for output files needed by the current node
     * type. Only the implementation files of MComponentDef, MHomeDef and MProvidesDef will be
     * written (and only if the component is implemented by an assembly).
     * 
     * @return a list of List objects containing file names for all output files to be generated for
     *         the current node.
     */
    protected List getOutputFiles()
    {
        logger.fine("enter getOutputFiles(), searching for assembly");
        currentAssembly = null;
        if (currentNode instanceof MComponentDef)
        {
            MComponentDef def = (MComponentDef) currentNode;
            currentAssembly = getAssemblyDescription(def);
        }
        else if (currentNode instanceof MHomeDef)
        {
            MHomeDef def = (MHomeDef) currentNode;
            currentAssembly = getAssemblyDescription(def.getComponent());
        }
        else if (currentNode instanceof MProvidesDef)
        {
            MProvidesDef def = (MProvidesDef) currentNode;
            currentAssembly = getAssemblyDescription(def.getComponent());
        }
        logger.fine("leave getOutputFiles(), searching for assembly");
        List files = super.getOutputFiles();
        if (currentAssembly != null)
            keep_only_impls(files);
        else
            kill_all_files(files);
        return files;
    }

    protected static void keep_only_impls( List files )
    {
        for (Object o : files)
        {
            List out_path = (List) o;
            String x = out_path.get(1).toString();
            if (!x.endsWith(IMPL_SUFFIX_H) && !x.endsWith(IMPL_SUFFIX_CC))
                out_path.set(1, "");
        }
    }

    protected static void kill_all_files( List files )
    {
        for (Object o : files)
        {
            List out_path = (List) o;
            out_path.set(1, "");
        }
    }

    protected Assembly getAssemblyDescription( MComponentDef def )
    {
        return assemblies.getAssembly(getQualifiedCcmName(def));
    }

    protected static String getQualifiedCcmName( MContained node )
    {
        return CcmModelHelper.getAbsoluteName(node, Model.IDL_SCOPE);
    }

    protected static final String TAB = "    ";

    protected static final String TAB2 = TAB + TAB;

    protected static final String TAB3 = TAB2 + TAB;

    protected String data_MComponentDef( String data_type, String data_value )
    {
        if (data_type.equals("AssemblyInnerComponentVariable"))
        {
            return variable_AssemblyInnerComponentVariable();
        }
        if (data_type.equals("AssemblyInnerComponentVariableCreation"))
        {
            return variable_AssemblyInnerComponentVariableCreation();
        }
        if (data_type.equals("AssemblyInnerComponentInclude"))
        {
            return variable_AssemblyInnerComponentInclude();
        }
        if (data_type.equals("AssemblyInnerHomeInclude"))
        {
            return variable_AssemblyInnerHomeInclude();
        }
        if (data_type.equals("AssemblyCcmActivate"))
        {
            return variable_AssemblyCcmActivate();
        }
        if (data_type.equals("AssemblyCcmRemove"))
        {
            return variable_AssemblyCcmRemove();
        }
        return super.data_MComponentDef(data_type, data_value);
    }

    protected String variable_AssemblyInnerComponentVariable()
    {
        StringBuffer code = new StringBuffer();
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            MComponentDef comp_def = map.get(key);
            String cpp_type = getLocalCxxName(comp_def, "::");
            code.append(TAB + cpp_type + "::SmartPtr " + key + "_;\n");
            for (Object o : comp_def.getFacets())
            {
                MProvidesDef p = (MProvidesDef) o;
                if (p.getIdentifier().equals(key))
                {
                    throw new RuntimeException("element \"" + key
                            + "\": name conflict between IDL and assembly");
                }
            }
        }
        return code.toString();
    }

    private static int local_var_counter_;

    private static String createLocalVar( Map<String, String> var_map, String var_type,
            StringBuilder code )
    {
        String local_var = var_map.get(var_type);
        if (local_var == null)
        {
            local_var = "var" + local_var_counter_;
            ++local_var_counter_;
            var_map.put(var_type, local_var);
            code.append(TAB);
            code.append(var_type);
            code.append(" ");
            code.append(local_var);
            code.append(";\n");
        }
        return local_var;
    }

    protected String variable_AssemblyInnerComponentVariableCreation()
    {
        if (currentAssembly == null)
            return "";
        StringBuilder code_homes = new StringBuilder();
        StringBuilder code_creation = new StringBuilder();
        HashMap<String, String> local_var_map = new HashMap<String, String>();
        boolean have_finder = false;
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            MComponentDef comp_def = map.get(key);
            String comp_alias = currentAssembly.getComponents().get(key).getAlias();
            if (comp_alias != null)
            {
                // calling home-finder
                final String finder_type = "::Components::HomeFinder*";
                String finder_var = createLocalVar(local_var_map, finder_type, code_homes);
                if (!have_finder)
                {
                    code_creation.append(TAB).append(finder_var);
                    code_creation.append(" = ::Components::HomeFinder::Instance();\n");
                    code_creation.append(TAB).append("assert(");
                    code_creation.append(finder_var).append(");\n");
                    have_finder = true;
                }
                final String home_type = "::Components::CCMHome::SmartPtr";
                String home_var = createLocalVar(local_var_map, home_type, code_homes);
                code_creation.append(TAB).append(home_var);
                code_creation.append(" = ").append(finder_var);
                code_creation.append("->find_home_by_name(\"");
                code_creation.append(comp_alias).append("\");\n");
                final String keyless_type = "::Components::KeylessCCMHome*";
                String keyless_var = createLocalVar(local_var_map, keyless_type, code_homes);
                code_creation.append(TAB).append(keyless_var);
                code_creation.append(" = dynamic_cast< ").append(keyless_type);
                code_creation.append(">(").append(home_var).append(".ptr());\n");
                code_creation.append(TAB).append("assert(");
                code_creation.append(keyless_var).append(");\n");
                final String obj_type = "::wamas::platform::utils::SmartPtr< ::Components::CCMObject>";
                String obj_var = createLocalVar(local_var_map, obj_type, code_homes);
                code_creation.append(TAB).append(obj_var);
                code_creation.append(" = ").append(keyless_var).append("->create_component();\n");
                String cpp_type = getLocalCxxName(comp_def, "::");
                code_creation.append(TAB).append(key);
                code_creation.append("_.eat(dynamic_cast< ").append(cpp_type);
                code_creation.append("*>(").append(obj_var).append(".ptr()));\n");
            }
            else
            {
                // using first home
                MHomeDef home = getHome(comp_def);
                String home_type = getLocalCxxName(home, "::");
                String home_var = createLocalVar(local_var_map, home_type, code_homes);
                code_creation.append(TAB);
                code_creation.append(key);
                code_creation.append("_ = ");
                code_creation.append(home_var);
                code_creation.append(".create();\n");
            }
            code_creation.append(TAB).append("assert(");
            code_creation.append(key).append("_);\n");
        }
        StringBuilder result = new StringBuilder();
        result.append(code_homes);
        result.append(code_creation);
        for (AssemblyElement e : currentAssembly.getElements())
        {
            if (e instanceof Constant)
            {
                Constant c = (Constant) e;
                Port target = c.getTarget();
                String value = c.getValue().toString();
                StringBuilder code = new StringBuilder();
                code.append(TAB);
                code.append(target.getComponent());
                code.append("_->");
                code.append(target.getConnector());
                code.append("(");
                code.append(value);
                code.append(");\n");
                result.append(code);
            }
        }
        return result.toString();
    }

    protected String variable_AssemblyInnerComponentInclude()
    {
        HashSet<String> include_set = new HashSet<String>();
        StringBuffer code = new StringBuffer();
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            MComponentDef comp_def = map.get(key);
            String inc_name = getLocalCxxIncludeName(comp_def);
            if (!include_set.contains(inc_name))
            {
                code.append("#include <" + inc_name + "_gen.h>\n");
                include_set.add(inc_name);
            }
        }
        return code.toString();
    }

    protected String variable_AssemblyInnerHomeInclude()
    {
        if (currentAssembly == null)
            return "";
        HashSet<String> include_set = new HashSet<String>();
        StringBuffer code = new StringBuffer();
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            String comp_alias = currentAssembly.getComponents().get(key).getAlias();
            if (comp_alias == null)
            {
                MComponentDef comp_def = map.get(key);
                MHomeDef home = getHome(comp_def);
                String inc_name = getLocalCxxIncludeName(home);
                if (!include_set.contains(inc_name))
                {
                    code.append("#include <" + inc_name + "_gen.h>\n");
                    include_set.add(inc_name);
                }
            }
        }
        return code.toString();
    }

    protected String variable_AssemblyCcmActivate()
    {
        if (currentAssembly == null)
            return "";
        MComponentDef comp_def = (MComponentDef) currentNode;
        StringBuilder activation_code = new StringBuilder();
        HashSet<String> outer_facets = new HashSet<String>();
        for (AssemblyElement e : currentAssembly.getElements())
        {
            if (e instanceof Connection)
            {
                Connection c = (Connection) e;
                StringBuilder code = new StringBuilder();
                String code_tail;
                Port target = c.getReceptacle();
                String target_name = target.getConnector();
                if (target.getComponent() == null)
                {
                    // connect to an outer facet
                    code.append(TAB2 + "if(" + target_name + "_) {\n");
                    String real_type = comp_def.getIdentifier() + "_" + target_name + "_impl";
                    code.append(TAB3 + real_type + "* facet = dynamic_cast<" + real_type + "*>("
                            + target_name + "_);\n");
                    code.append(TAB3 + "facet->target = ");
                    code_tail = ";\n" + TAB2 + "}\n";
                    outer_facets.add(target_name);
                }
                else
                {
                    // connect to the receptacle of an inner component
                    code.append(TAB2);
                    code.append(target.getComponent());
                    code.append("_->connect_");
                    code.append(target_name);
                    code.append("(");
                    code_tail = ");\n";
                }
                Port source = c.getFacet();
                code.append(getFacetValue(source));
                code.append(code_tail);
                activation_code.append(code);
            }
            else if (e instanceof Attribute)
            {
                Attribute a = (Attribute) e;
                Port target = a.getTarget();
                String source = a.getSource();
                StringBuilder code = new StringBuilder();
                code.append(TAB2);
                code.append(target.getComponent());
                code.append("_->");
                code.append(target.getConnector());
                code.append("(this->");
                code.append(source);
                code.append("_);\n");
                activation_code.append(code);
            }
        }
        for (Object o : comp_def.getFacets())
        {
            MProvidesDef p = (MProvidesDef) o;
            String name = p.getIdentifier();
            if (!outer_facets.contains(name))
            {
                throw new RuntimeException("facet " + name
                        + " is not connected to an inner component");
            }
        }
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            activation_code.append(TAB2 + key + "_->configuration_complete();\n");
        }
        return activation_code.toString();
    }

    protected String variable_AssemblyCcmRemove()
    {
        StringBuilder code = new StringBuilder();
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            code.append(TAB2 + key + "_->remove();\n");
        }
        return code.toString();
    }

    private Map<String, Map<String, MComponentDef>> assembly_local_components_ = new HashMap<String, Map<String, MComponentDef>>();

    protected Map<String, MComponentDef> getAssemblyLocalComponents()
    {
        if (currentAssembly != null)
        {
            String assembly_key = currentAssembly.getGlobalName();
            Map<String, MComponentDef> map = assembly_local_components_.get(assembly_key);
            if (map == null)
            {
                map = new HashMap<String, MComponentDef>();
                assembly_local_components_.put(assembly_key, map);
                Map<String, Component> local_comps = currentAssembly.getComponents();
                for (String key : local_comps.keySet())
                {
                    Component comp_decl = local_comps.get(key);
                    MComponentDef comp_def = comp_decl.getCcmName().getCcmComponent();
                    if (comp_def == null)
                    {
                        throw new RuntimeException("cannot find component " + key + " of type "
                                + comp_decl.getCcmName());
                    }
                    map.put(key, comp_def);
                }
            }
            return map;
        }
        else
        {
            return new HashMap<String, MComponentDef>();
        }
    }

    protected void writeSourceFile( String implDirectory, String generated_code, String file_dir,
            String file_name ) throws IOException
    {
        File outFile = new File(output_dir + File.separator + file_dir, file_name);
        if (isCodeEqualWithFile(generated_code, outFile))
        {
            uiDriver.printMessage("Skipping " + outFile);
        }
        else
        {
            writeFinalizedFile(file_dir, file_name, generated_code);
        }
    }

    protected static MHomeDef getHome( MComponentDef comp )
    {
        MHomeDef result = null;
        for (Object o : comp.getHomes())
        {
            if (result == null)
                result = (MHomeDef) o;
        }
        if (result == null)
            throw new RuntimeException("component \"" + comp.getIdentifier() + "\" has no home");
        return result;
    }

    protected String data_MProvidesDef( String dataType, String dataValue )
    {
        if (dataType.equals("AssemblyTargetVariable"))
        {
            return variable_AssemblyTargetVariable();
        }
        if (dataType.equals("AssemblyGetFacetCode"))
        {
            return variable_AssemblyGetFacetCode();
        }
        return super.data_MProvidesDef(dataType, dataValue);
    }

    protected String variable_AssemblyTargetVariable()
    {
        MProvidesDef provides = (MProvidesDef) currentNode;
        MInterfaceDef iface = provides.getProvides();
        String iface_type = getLocalCxxName(iface, Text.SCOPE_SEPARATOR);
        return TAB + iface_type + "::SmartPtr target;\n";
    }

    protected String variable_AssemblyGetFacetCode()
    {
        if (currentAssembly == null)
            return "";
        MProvidesDef provides = (MProvidesDef) currentNode;
        StringBuilder result = new StringBuilder();
        result.append(TAB).append("if(ccm_activate_ok) {\n");
        result.append(TAB2).append("facet->target = ");
        result.append(getAssemblyInitFacetTargetValue(provides)).append(";\n");
        result.append(TAB).append("}\n");
        return result.toString();
    }

    private String getAssemblyInitFacetTargetValue( MProvidesDef provides )
    {
        String name = provides.getIdentifier();
        for (AssemblyElement e : currentAssembly.getElements())
        {
            if (e instanceof Connection)
            {
                Connection c = (Connection) e;
                Port target = c.getReceptacle();
                if (target.getComponent() == null && target.getConnector().equals(name))
                {
                    Port source = c.getFacet();
                    return getFacetValue(source);
                }
            }
        }
        throw new RuntimeException("facet " + name + " is not connected to an inner component");
    }

    private static String getFacetValue( Port source )
    {
        StringBuilder code = new StringBuilder();
        if (source.getComponent() == null)
        {
            // connect from an outer receptacle
            code.append("ctx->get_connection_");
        }
        else
        {
            // connect from the facet of an inner component
            code.append(source.getComponent());
            code.append("_->provide_");
        }
        code.append(source.getConnector());
        code.append("()");
        return code.toString();
    }

    protected String generateOperationImpl( MProvidesDef provides, MOperationDef op )
    {
        StringBuilder code = new StringBuilder();
        String return_type = getLanguageType(op);
        code.append(return_type).append("\n");
        code.append(provides.getComponent().getIdentifier()).append("_").append(
            provides.getIdentifier());
        code.append("_impl::").append(op.getIdentifier()).append("(");
        code.append(getOperationParams(op)).append(")\n");
        code.append("    ").append(getOperationExcepts(op)).append("\n");
        code.append("{\n");
        code.append(TAB);
        if (!return_type.equals("void"))
            code.append("return ");
        code.append("this->target->").append(op.getIdentifier()).append("(");
        code.append(getOperationParamNames(op));
        code.append(");\n");
        code.append("}\n\n");
        return code.toString();
    }

    protected String data_MAttributeDef( String dataType, String dataValue )
    {
        if (dataType.equals("AssemblyAttributeSetterCode"))
        {
            return variable_AssemblyAttributeSetterCode();
        }
        return super.data_MAttributeDef(dataType, dataValue);
    }

    protected String variable_AssemblyAttributeSetterCode()
    {
        if (currentAssembly == null)
            return "";
        MAttributeDef attr = (MAttributeDef) currentNode;
        String source = attr.getIdentifier();
        StringBuilder result = new StringBuilder();
        boolean empty = true;
        for (AssemblyElement e : currentAssembly.getElements())
        {
            if (e instanceof Attribute)
            {
                Attribute a = (Attribute) e;
                if (a.getSource().equals(source))
                {
                    Port target = a.getTarget();
                    StringBuilder code = new StringBuilder();
                    code.append(TAB2);
                    code.append(target.getComponent());
                    code.append("_->");
                    code.append(target.getConnector());
                    code.append("(this->");
                    code.append(source);
                    code.append("_);\n");
                    if (empty)
                    {
                        result.append(TAB + "if(ccm_activate_ok) {\n");
                        empty = false;
                    }
                    result.append(code);
                }
            }
        }
        if (!empty)
            result.append(TAB + "}\n");
        return result.toString();
    }
}
