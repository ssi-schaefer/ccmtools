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
import java.util.Set;
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
import ccmtools.parser.idl.metamodel.ComponentIDL.MUsesDef;
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
        if (data_type.equals("AssemblyImplVariable"))
        {
            return variable_AssemblyInnerComponentVariable()
                    + variable_AssemblySingleToMultipleCookieVariable();
        }
        if (data_type.equals("AssemblyInnerComponentVariableCreation"))
        {
            return variable_AssemblyInnerComponentVariableCreation();
        }
        if (data_type.equals("AssemblyInnerComponentInclude"))
        {
            return variable_AssemblyInnerHomeInclude() + variable_AssemblyInnerComponentInclude();
        }
        if (data_type.equals("AssemblyCcmActivate"))
        {
            return variable_AssemblyCcmActivate();
        }
        if (data_type.equals("AssemblyCcmRemove"))
        {
            return variable_AssemblyCcmRemove();
        }
        if (data_type.equals("AssemblyImplBase"))
        {
            return "\n" + TAB + ", virtual public ::Components::ComponentDelegator";
        }
        if (data_type.equals("AssemblyImplPrototype"))
        {
            final String VV = TAB + "virtual ";
            final String O = "::Components::Object::SmartPtr";
            final String C = "::Components::Cookie";
            final String N = "::Components::FeatureName";
            StringBuilder c = new StringBuilder();
            c.append(VV + O + " provide(const std::string& name);\n");
            c.append(VV + C + " connect(const " + N + "& name, " + O + " f);\n");
            c.append(VV + "void disconnect(const " + N + "& name, " + C + " const& ck);\n");
            return c.toString();
        }
        if (data_type.equals("AssemblyImplDefinition"))
        {
            return variable_AssemblyImplDefinition();
        }
        return super.data_MComponentDef(data_type, data_value);
    }

    protected String variable_AssemblyInnerComponentVariable()
    {
        StringBuilder code = new StringBuilder();
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            MComponentDef comp_def = map.get(key);
            code.append(TAB + "::Components::CCMObject::SmartPtr " + key + "_;\n");
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
                code_creation.append(TAB).append(key).append("_ = ");
                code_creation.append(keyless_var).append("->create_component();\n");
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
        for (AssemblyElement e : currentAssembly.getElements())
        {
            if (e instanceof Constant)
            {
                Constant c = (Constant) e;
                Port target = c.getTarget();
                String target_comp = target.getComponent();
                String value = c.getValue().toString();
                StringBuilder code = new StringBuilder();
                String target_type = getLocalCxxName(map.get(target_comp), "::") + "*";
                String target_var = createLocalVar(local_var_map, target_type, code_homes);
                code.append(TAB + target_var + " = dynamic_cast< " + target_type + ">(");
                code.append(target_comp + "_.ptr());\n");
                code.append(TAB + "assert(" + target_var + ");\n");
                code.append(TAB + target_var + "->");
                code.append(target.getConnector());
                code.append("(");
                code.append(value);
                code.append(");\n");
                code_creation.append(code);
            }
        }
        StringBuilder result = new StringBuilder();
        result.append(code_homes);
        result.append(code_creation);
        return result.toString();
    }

    protected String variable_AssemblyInnerComponentInclude()
    {
        if (currentAssembly == null)
            return "";
        HashSet<String> include_set = new HashSet<String>();
        StringBuilder code = new StringBuilder();
        for (AssemblyElement e : currentAssembly.getElements())
        {
            if (e instanceof Constant)
            {
                Constant c = (Constant) e;
                generateAssemblyInnerComponentInclude(c.getTarget(), include_set, code);
            }
            else if (e instanceof Attribute)
            {
                Attribute a = (Attribute) e;
                generateAssemblyInnerComponentInclude(a.getTarget(), include_set, code);
            }
        }
        return code.toString();
    }

    private void generateAssemblyInnerComponentInclude( Port target, Set<String> include_set,
            StringBuilder code )
    {
        String target_comp = target.getComponent();
        MComponentDef target_def = getAssemblyLocalComponents().get(target_comp);
        String inc_name = getLocalCxxIncludeName(target_def);
        if (!include_set.contains(inc_name))
        {
            code.append("#include <" + inc_name + "_gen.h>\n");
            include_set.add(inc_name);
        }
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

    protected String variable_AssemblySingleToMultipleCookieVariable()
    {
        if (currentAssembly == null)
            return "";
        MComponentDef comp_def = (MComponentDef) currentNode;
        StringBuilder code = new StringBuilder();
        for (AssemblyElement e : currentAssembly.getElements())
        {
            if (e instanceof Connection)
            {
                Connection c = (Connection) e;
                Port source = c.getFacet();
                if (source.getComponent() == null)
                {
                    Port target = c.getReceptacle();
                    String target_comp = target.getComponent();
                    if (target_comp != null)
                    {
                        String source_name = source.getConnector();
                        MUsesDef outer_receptacle = getReceptacle(comp_def, source_name);
                        MComponentDef inner_comp = getAssemblyLocalComponents().get(target_comp);
                        String target_name = target.getConnector();
                        MUsesDef inner_receptacle = getReceptacle(inner_comp, target_name);
                        boolean outer_multiple = outer_receptacle.isMultiple();
                        boolean inner_multiple = inner_receptacle.isMultiple();
                        if (outer_multiple != inner_multiple)
                        {
                            code.append(TAB).append("::Components::Cookie ");
                            code.append(source_name).append("_;\n");
                        }
                    }
                }
            }
        }
        return code.toString();
    }

    protected String variable_AssemblyImplDefinition()
    {
        if (currentAssembly == null)
            return "";
        MComponentDef comp_def = (MComponentDef) currentNode;
        StringBuilder provide = new StringBuilder();
        StringBuilder connect = new StringBuilder();
        StringBuilder disconnect = new StringBuilder();
        HashSet<String> outer_facets = new HashSet<String>();
        for (AssemblyElement e : currentAssembly.getElements())
        {
            if (e instanceof Connection)
            {
                Connection c = (Connection) e;
                Port source = c.getFacet();
                Port target = c.getReceptacle();
                if (target.getComponent() == null)
                {
                    // connect to an outer facet
                    String target_name = target.getConnector();
                    outer_facets.add(target_name);
                    generateProvideImpl(target_name, source, provide);
                }
                if (source.getComponent() == null)
                {
                    // connect from an outer receptacle
                    String source_name = source.getConnector();
                    generateConnectionImpl(source_name, target, connect, disconnect);
                }
            }
        }
        //
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
        //
        StringBuilder c = new StringBuilder();
        String CLS = comp_def.getIdentifier() + "_impl::";
        final String O = "::Components::Object::SmartPtr";
        final String C = "::Components::Cookie";
        final String N = "::Components::FeatureName";
        c.append(O + "\n" + CLS + "provide(const std::string& name)\n{\n");
        c.append(provide);
        c.append(TAB + "throw ::Components::InvalidName();\n");
        c.append("}\n\n");
        c.append(C + "\n" + CLS + "connect(const " + N + "& name, " + O + " f)\n{\n");
        c.append(connect);
        c.append(TAB + "throw ::Components::InvalidName();\n");
        c.append("}\n\n");
        c.append("void\n" + CLS + "disconnect(const " + N + "& name, " + C + " const& ck)\n{\n");
        c.append(disconnect);
        c.append(TAB + "throw ::Components::InvalidName();\n");
        c.append("}\n\n");
        return c.toString();
    }

    // connect to an outer facet
    private void generateProvideImpl( String target_name, Port source, StringBuilder code )
    {
        code.append(TAB + "if(name==\"" + target_name + "\")\n");
        code.append(TAB + "{\n");
        if (source.getComponent() == null)
        {
            // special use case: facet/receptacle loop
            code.append(TAB2 + "return ::Components::Object::SmartPtr();\n");
        }
        else
        {
            // connect from an inner facet
            code.append(TAB2 + "return " + source.getComponent());
            code.append("_->provide_facet(\"" + source.getConnector() + "\");\n");
        }
        code.append(TAB + "}\n");
    }

    // connect from an outer receptacle
    private void generateConnectionImpl( String source_name, Port target,
            StringBuilder connect_code, StringBuilder disconnect_code )
    {
        connect_code.append(TAB + "if(name==\"" + source_name + "\")\n");
        connect_code.append(TAB + "{\n");
        disconnect_code.append(TAB + "if(name==\"" + source_name + "\")\n");
        disconnect_code.append(TAB + "{\n");
        String target_comp = target.getComponent();
        String target_name = target.getConnector();
        MComponentDef comp_def = (MComponentDef) currentNode;
        if (target_comp == null)
        {
            // special use case: facet/receptacle loop
            String real_type = comp_def.getIdentifier() + "_" + target_name + "_impl";
            String impl_code = TAB2 + real_type + "* impl = dynamic_cast<" + real_type + "*>(get_"
                    + target_name + "());\n";
            connect_code.append(impl_code);
            connect_code.append(TAB2 + "impl->target = f;\n");
            connect_code.append(TAB2 + "return ::Components::Cookie();\n");
            disconnect_code.append(impl_code);
            disconnect_code.append(TAB2 + "impl->target.forget();\n");
        }
        else
        {
            // connect to an inner receptacle
            MUsesDef outer_receptacle = getReceptacle(comp_def, source_name);
            MComponentDef inner_comp = getAssemblyLocalComponents().get(target_comp);
            MUsesDef inner_receptacle = getReceptacle(inner_comp, target_name);
            boolean outer_multiple = outer_receptacle.isMultiple();
            boolean inner_multiple = inner_receptacle.isMultiple();
            if (outer_multiple == inner_multiple)
            {
                // both receptacles are single or multiple
                connect_code.append(TAB2 + "return " + target_comp + "_->connect(\"");
                connect_code.append(target_name + "\", f);\n");
                disconnect_code.append(TAB2 + target_comp + "_->disconnect(\"");
                disconnect_code.append(target_name + "\", ck);\n");
            }
            else
            {
                if (outer_multiple)
                {
                    throw new RuntimeException("cannot connect outer multiple receptacle "
                            + source_name + " with inner single receptacle " + target_name);
                }
                // connect outer single receptacle with inner multiple receptacle
                connect_code.append(TAB2 + source_name + "_ = ");
                connect_code.append(target_comp + "_->connect(\"");
                connect_code.append(target_name + "\", f);\n");
                connect_code.append(TAB2 + "return " + source_name + "_;\n");
                disconnect_code.append(TAB2 + target_comp + "_->disconnect(\"");
                disconnect_code.append(target_name + "\", " + source_name + "_);\n");
            }
        }
        connect_code.append(TAB + "}\n");
        disconnect_code.append(TAB2 + "return;\n");
        disconnect_code.append(TAB + "}\n");
    }

    private static MUsesDef getReceptacle( MComponentDef comp_def, String name )
    {
        for (Object o : comp_def.getReceptacles())
        {
            MUsesDef u = (MUsesDef) o;
            if (u.getIdentifier().equals(name))
                return u;
        }
        throw new RuntimeException("cannot find receptacle: " + name);
    }

    protected String variable_AssemblyCcmActivate()
    {
        if (currentAssembly == null)
            return "";
        StringBuilder activation_code = new StringBuilder();
        for (AssemblyElement e : currentAssembly.getElements())
        {
            if (e instanceof Connection)
            {
                Connection c = (Connection) e;
                Port source = c.getFacet();
                Port target = c.getReceptacle();
                String source_comp = source.getComponent();
                String target_comp = target.getComponent();
                if (source_comp != null && target_comp != null)
                {
                    activation_code.append(TAB2);
                    activation_code.append(target_comp);
                    activation_code.append("_->connect(\"");
                    activation_code.append(target.getConnector());
                    activation_code.append("\", ");
                    activation_code.append(source_comp);
                    activation_code.append("_->provide_facet(\"");
                    activation_code.append(source.getConnector());
                    activation_code.append("\"));\n");
                }
            }
            else if (e instanceof Attribute)
            {
                Attribute a = (Attribute) e;
                activation_code.append(generateCopyAttribute(a, true));
            }
        }
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            activation_code.append(TAB2 + key + "_->configuration_complete();\n");
        }
        return activation_code.toString();
    }

    private StringBuilder generateCopyAttribute( Attribute a, boolean create_block )
    {
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        Port target = a.getTarget();
        String target_comp = target.getComponent();
        String source = a.getSource();
        StringBuilder code = new StringBuilder();
        String target_type = getLocalCxxName(map.get(target_comp), "::") + "*";
        String HEAD;
        if (create_block)
        {
            code.append(TAB2 + "{\n");
            HEAD = TAB3;
        }
        else
        {
            HEAD = TAB2;
        }
        code.append(HEAD + target_type + " target = dynamic_cast< " + target_type + ">(");
        code.append(target_comp + "_.ptr());\n");
        code.append(HEAD + "assert(target);\n");
        code.append(HEAD + "target->");
        code.append(target.getConnector());
        code.append("(this->");
        code.append(source);
        code.append("_);\n");
        if (create_block)
        {
            code.append(TAB2 + "}\n");
        }
        return code;
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
            return TAB + "::Components::Object::SmartPtr target;\n";
        }
        return super.data_MProvidesDef(dataType, dataValue);
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
        //
        MInterfaceDef iface = provides.getProvides();
        String iface_type = getLocalCxxName(iface, Text.SCOPE_SEPARATOR);
        code.append(TAB + iface_type + "* f_a_c_e_t = dynamic_cast< ");
        code.append(iface_type + "*>(this->target.ptr());\n");
        code.append(TAB + "if(!f_a_c_e_t)\n");
        code.append(TAB2 + "throw ::Components::CCMException(::Components::SYSTEM_ERROR);\n");
        //
        code.append(TAB);
        if (!return_type.equals("void"))
            code.append("return ");
        code.append("f_a_c_e_t->").append(op.getIdentifier()).append("(");
        code.append(getOperationParamNames(op));
        code.append(");\n");
        //
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
                    if (empty)
                    {
                        result.append(TAB + "if(ccm_activate_ok)\n" + TAB + "{\n");
                        result.append(generateCopyAttribute(a, false));
                        empty = false;
                    }
                    else
                    {
                        throw new RuntimeException("attribute \"" + source + "\"");
                    }
                }
            }
        }
        if (!empty)
            result.append(TAB + "}\n");
        return result.toString();
    }
}
