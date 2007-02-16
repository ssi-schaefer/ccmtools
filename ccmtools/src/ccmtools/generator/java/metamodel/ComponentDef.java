package ccmtools.generator.java.metamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import ccmtools.generator.java.templates.ComponentDefAdapterFromCorbaTemplate;
import ccmtools.generator.java.templates.ComponentDefAdapterLocalTemplate;
import ccmtools.generator.java.templates.ComponentDefAdapterToCorbaTemplate;
import ccmtools.generator.java.templates.ComponentDefApplicationClassTemplate;
import ccmtools.generator.java.templates.ComponentDefApplicationInterfaceTemplate;
import ccmtools.generator.java.templates.ComponentDefAssemblyClassTemplate;
import ccmtools.generator.java.templates.ComponentDefContextClassTemplate;
import ccmtools.generator.java.templates.ComponentDefContextInterfaceTemplate;
import ccmtools.generator.java.templates.ComponentDefInterfaceTemplate;
import ccmtools.parser.assembly.metamodel.Assembly;
import ccmtools.parser.assembly.metamodel.AssemblyElement;
import ccmtools.parser.assembly.metamodel.Attribute;
import ccmtools.parser.assembly.metamodel.Component;
import ccmtools.parser.assembly.metamodel.Connection;
import ccmtools.parser.assembly.metamodel.Constant;
import ccmtools.parser.assembly.metamodel.Model;
import ccmtools.parser.assembly.metamodel.Port;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MHomeDef;
import ccmtools.utils.SourceFile;
import ccmtools.utils.Text;

public class ComponentDef extends ModelElement implements JavaLocalInterfaceGeneratorElement,
        JavaLocalAdapterGeneratorElement, JavaClientLibGeneratorElement,
        JavaCorbaAdapterGeneratorElement, JavaApplicationGeneratorElement
{
    private List<AttributeDef> attributes = new ArrayList<AttributeDef>();

    private List<ProvidesDef> facet = new ArrayList<ProvidesDef>();

    private List<UsesDef> receptacle = new ArrayList<UsesDef>();

    private List<SupportsDef> supports = new ArrayList<SupportsDef>();

    private JavaApplicationGeneratorElement home;

    public ComponentDef( String identifier, List<String> namespace )
    {
        super(identifier, namespace);
    }

    public List<AttributeDef> getAttributes()
    {
        return attributes;
    }

    public List<ProvidesDef> getFacet()
    {
        return facet;
    }

    public List<UsesDef> getReceptacle()
    {
        return receptacle;
    }

    public List<SupportsDef> getSupports()
    {
        return supports;
    }

    public JavaApplicationGeneratorElement getHome()
    {
        return home;
    }

    public void setHome( JavaApplicationGeneratorElement home )
    {
        this.home = home;
    }

    public Set<String> getJavaImportStatements()
    {
        Set<String> importStatements = new TreeSet<String>();
        // Each component class refers to its context object
        importStatements.add(generateAbsoluteJavaCcmName() + "_Context");
        for (AttributeDef a : getAttributes())
        {
            importStatements.addAll(a.getType().getJavaImportStatements());
        }
        for (SupportsDef s : getSupports())
        {
            importStatements.addAll(s.getJavaImportStatements());
        }
        importStatements.add(generateAbsoluteJavaName());
        return importStatements;
    }

    /***********************************************************************************************
     * Local Interface Generator Methods
     **********************************************************************************************/
    public String generateJavaImportStatements()
    {
        return generateJavaImportStatements(getJavaImportStatements());
    }

    public String generateJavaImportStatements( String namespace )
    {
        return generateJavaImportStatements(namespace, getJavaImportStatements());
    }

    public String generateInterface()
    {
        return new ComponentDefInterfaceTemplate().generate(this);
    }

    public String generateSupportsDeclarations()
    {
        List<String> supportsList = new ArrayList<String>();
        for (SupportsDef s : getSupports())
        {
            supportsList.add(s.getInterface().getIdentifier());
        }
        if (supportsList.size() > 0)
        {
            return "," + NL + TAB + Text.joinList("," + NL + TAB, supportsList);
        }
        else
        {
            return ""; // no supported interfaces
        }
    }

    public String generateSupportsCcmDeclarations()
    {
        List<String> supportsList = new ArrayList<String>();
        for (SupportsDef s : getSupports())
        {
            supportsList.add(s.getInterface().generateCcmIdentifier());
        }
        if (supportsList.size() > 0)
        {
            return "," + NL + TAB + Text.joinList("," + NL + TAB, supportsList);
        }
        else
        {
            return ""; // no supported interfaces
        }
    }

    // Generate SourceFile objects --------------------------------------------
    public List<SourceFile> generateLocalInterfaceSourceFiles()
    {
        List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
        String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
        SourceFile iface = new SourceFile(localPackageName, getIdentifier() + ".java",
                generateInterface());
        sourceFileList.add(iface);
        return sourceFileList;
    }

    /***********************************************************************************************
     * Local Adapter Generator Methods
     **********************************************************************************************/
    public String generateApplicationInterface()
    {
        return new ComponentDefApplicationInterfaceTemplate().generate(this);
    }

    public String generateContextInterface()
    {
        return new ComponentDefContextInterfaceTemplate().generate(this);
    }

    public String generateContextClass()
    {
        return new ComponentDefContextClassTemplate().generate(this);
    }

    public String generateAdapterLocal()
    {
        return new ComponentDefAdapterLocalTemplate().generate(this);
    }

    // Generate SourceFile objects --------------------------------------------
    public List<SourceFile> generateLocalAdapterSourceFiles()
    {
        List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
        String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
        SourceFile applicationInterface = new SourceFile(localPackageName, generateCcmIdentifier()
                + ".java", generateApplicationInterface());
        sourceFileList.add(applicationInterface);
        SourceFile contextInterface = new SourceFile(localPackageName, generateCcmIdentifier()
                + "_Context.java", generateContextInterface());
        sourceFileList.add(contextInterface);
        SourceFile contextClass = new SourceFile(localPackageName, generateCcmIdentifier()
                + "_ContextImpl.java", generateContextClass());
        sourceFileList.add(contextClass);
        SourceFile adapterLocal = new SourceFile(localPackageName,
                getIdentifier() + "Adapter.java", generateAdapterLocal());
        sourceFileList.add(adapterLocal);
        return sourceFileList;
    }

    /***********************************************************************************************
     * Application Generator Methods
     **********************************************************************************************/
    public String generateApplicationClass()
    {
        return new ComponentDefApplicationClassTemplate().generate(this);
    }

    public String generateAssemblyClass()
    {
        return new ComponentDefAssemblyClassTemplate().generate(this);
    }

    // Generate SourceFile objects --------------------------------------------
    public List<SourceFile> generateApplicationSourceFiles()
    {
        List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
        String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
        SourceFile applicationClass = new SourceFile(localPackageName, getIdentifier()
                + "Impl.java", generateApplicationClass());
        sourceFileList.add(applicationClass);
        return sourceFileList;
    }

    private Assembly assembly_;

    public List<SourceFile> generateAssemblySourceFiles( Model assemblies )
    {
        assembly_ = getAssemblyDescription(assemblies);
        List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
        if (assembly_ != null)
        {
            String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
            SourceFile applicationClass = new SourceFile(localPackageName, getIdentifier()
                    + "Impl.java", generateAssemblyClass());
            sourceFileList.add(applicationClass);
        }
        return sourceFileList;
    }

    private String getQualifiedCcmName()
    {
        return Text.joinList(Model.IDL_SCOPE, getJavaNamespaceList()) + Model.IDL_SCOPE
                + getIdentifier();
    }

    /**
     * searches for the assembly description
     * 
     * @param assemblies the assembly model
     * @return the assembly description (or null)
     */
    Assembly getAssemblyDescription( Model assemblies )
    {
        return assemblies.getAssembly(getQualifiedCcmName());
    }

    private HashMap<String, MComponentDef> assembly_local_components_;

    public Map<String, MComponentDef> getAssemblyLocalComponents()
    {
        if (assembly_local_components_ == null)
        {
            assembly_local_components_ = new HashMap<String, MComponentDef>();
            Map<String, Component> local_comps = assembly_.getComponents();
            for (String key : local_comps.keySet())
            {
                Component comp_decl = local_comps.get(key);
                MComponentDef comp_def = comp_decl.getCcmName().getCcmComponent();
                if (comp_def == null)
                {
                    throw new RuntimeException("cannot find component " + key + " of type "
                            + comp_decl.getCcmName());
                }
                assembly_local_components_.put(key, comp_def);
            }
        }
        return assembly_local_components_;
    }

    public Iterator getAssemblyAttributeDeclarations()
    {
        ArrayList<String> list = new ArrayList<String>();
        for (String key : getAssemblyLocalComponents().keySet())
        {
            MComponentDef comp_def = assembly_local_components_.get(key);
            String java_type = CcmModelHelper.getAbsoluteName(comp_def, ".");
            String code = TAB + "private " + java_type + " " + key + "_;";
            list.add(code);
        }
        return list.iterator();
    }

    public Iterator getAssemblyAttributeInitialisation()
    {
        ArrayList<String> list = new ArrayList<String>();
        for (String key : getAssemblyLocalComponents().keySet())
        {
            MComponentDef comp_def = assembly_local_components_.get(key);
            List homes = comp_def.getHomes();
            if (homes.size() > 0)
            {
                // using first home
                MHomeDef home = (MHomeDef) homes.get(0);
                String hn = CcmModelHelper.getAbsoluteName(home, ".");
                String code = TAB3 + key + "_ = ((" + hn + ")" + hn
                        + "Deployment.create()).create();";
                list.add(code);
            }
            else
            {
                // no home
                String cn = CcmModelHelper.getAbsoluteName(comp_def, ".");
                String code = TAB3 + key + "_ = new " + cn + "Adapter(new " + cn + "Impl());";
                list.add(code);
            }
        }
        return list.iterator();
    }

    public Iterator getAssemblyAttributeSetup()
    {
        ArrayList<String> list = new ArrayList<String>();
        for (AssemblyElement e : assembly_.getElements())
        {
            if (e instanceof Connection)
            {
                Connection c = (Connection) e;
                Port target = c.getReceptacle();
                if (target.getComponent() != null)
                {
                    StringBuffer code = new StringBuffer();
                    code.append(TAB3);
                    code.append(target.getComponent());
                    code.append("_.connect_");
                    code.append(target.getConnector());
                    code.append("(");
                    Port source = c.getFacet();
                    if (source.getComponent() == null)
                    {
                        // connect an outer receptacle to the receptacle of an inner component
                        code.append("ctx.get_connection_");
                    }
                    else
                    {
                        // connect facet and receptacle if inner components
                        code.append(source.getComponent());
                        code.append("_.provide_");
                    }
                    code.append(source.getConnector());
                    code.append("());");
                    list.add(code.toString());
                }
            }
            else if (e instanceof Attribute)
            {
                Attribute a = (Attribute) e;
                Port target = a.getTarget();
                String source = a.getSource();
                StringBuffer code = new StringBuffer();
                code.append(TAB3);
                code.append(target.getComponent());
                code.append("_.");
                code.append(target.getConnector());
                code.append("(this.");
                code.append(source);
                code.append("_);");
                list.add(code.toString());
            }
            else if (e instanceof Constant)
            {
                Constant c = (Constant) e;
                Port target = c.getTarget();
                String value = c.getValue().toString();
                StringBuffer code = new StringBuffer();
                code.append(TAB3);
                code.append(target.getComponent());
                code.append("_.");
                code.append(target.getConnector());
                code.append("(");
                code.append(value);
                code.append(");");
                list.add(code.toString());
            }
        }
        return list.iterator();
    }

    public String getInnerFacet( ProvidesDef facet )
    {
        String name = facet.getIdentifier();
        for (AssemblyElement e : assembly_.getElements())
        {
            if (e instanceof Connection)
            {
                Connection c = (Connection) e;
                Port target = c.getReceptacle();
                if (target.getComponent() == null && target.getConnector().equals(name))
                {
                    Port source = c.getFacet();
                    if (source.getComponent() == null)
                    {
                        // special case: connect an outer facet to an outer receptacle
                        return "ctx.get_connection_" + source.getConnector() + "()";
                    }
                    return source.getComponent() + "_.provide_" + source.getConnector() + "()";
                }
            }
        }
        throw new RuntimeException("facet \"" + name + "\" is not connected");
    }

    /***********************************************************************************************
     * Client Library Generator Methods
     **********************************************************************************************/
    public String generateAdapterToCorba()
    {
        return new ComponentDefAdapterToCorbaTemplate().generate(this);
    }

    // Generate SourceFile objects --------------------------------------------
    public List<SourceFile> generateClientLibSourceFiles()
    {
        List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
        String localPackageName = Text.joinList(File.separator, getJavaNamespaceList());
        SourceFile adapterToCorba = new SourceFile(localPackageName, getIdentifier()
                + "AdapterToCorba.java", generateAdapterToCorba());
        sourceFileList.add(adapterToCorba);
        return sourceFileList;
    }

    /***********************************************************************************************
     * CORBA Adapter Generator Methods
     **********************************************************************************************/
    public String generateAdapterFromCorba()
    {
        return new ComponentDefAdapterFromCorbaTemplate().generate(this);
    }

    // Generate SourceFile objects --------------------------------------------
    public List<SourceFile> generateCorbaAdapterSourceFiles()
    {
        List<SourceFile> sourceFileList = new ArrayList<SourceFile>();
        String remotePackageName = Text.joinList(File.separator, getJavaRemoteNamespaceList());
        SourceFile adapterToCorba = new SourceFile(remotePackageName, getIdentifier()
                + "AdapterFromCorba.java", generateAdapterFromCorba());
        sourceFileList.add(adapterToCorba);
        return sourceFileList;
    }
}