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
import ccmtools.parser.assembly.metamodel.Component;
import ccmtools.parser.assembly.metamodel.Model;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.BaseIDL.MContained;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MHomeDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.ui.UserInterfaceDriver;

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
        if (data_type.equals("AssemblyInnerComponentVariableDestruction"))
        {
            return variable_AssemblyInnerComponentVariableDestruction();
        }
        if (data_type.equals("AssemblyInnerComponentInclude"))
        {
            return variable_AssemblyInnerComponentInclude();
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
            code.append(TAB + cpp_type + "* " + key + "_;\n");
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

    protected String variable_AssemblyInnerComponentVariableCreation()
    {
        StringBuffer code = new StringBuffer();
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            MComponentDef comp_def = map.get(key);
            // TODO
            code.append(TAB + key + "_ = 0; // TODO\n");
        }
        return code.toString();
    }

    protected String variable_AssemblyInnerComponentVariableDestruction()
    {
        StringBuffer code = new StringBuffer();
        Map<String, MComponentDef> map = getAssemblyLocalComponents();
        for (String key : map.keySet())
        {
            code.append(TAB + "delete " + key + "_;\n");
        }
        return code.toString();
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
                code.append("#include <" + inc_name + ".h>\n");
                include_set.add(inc_name);
            }
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
}
