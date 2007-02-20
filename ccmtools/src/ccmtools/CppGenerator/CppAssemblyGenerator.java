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
import java.util.List;
import java.util.logging.Logger;
import ccmtools.CcmtoolsException;
import ccmtools.parser.assembly.metamodel.Assembly;
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
    public CppAssemblyGenerator( UserInterfaceDriver uiDriver, File outDir, Model assemblies )
            throws IOException, CcmtoolsException
    {
        super(uiDriver, outDir);
        logger = Logger.getLogger("ccm.generator.cpp.assembly");
        this.assemblies = assemblies;
        setFlag(FLAG_APPLICATION_FILES);
    }

    protected Model assemblies;

    protected Assembly currentAssembly;

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
}
