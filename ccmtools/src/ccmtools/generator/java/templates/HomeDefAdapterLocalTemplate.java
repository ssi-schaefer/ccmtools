package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class HomeDefAdapterLocalTemplate
{
  protected static String nl;
  public static synchronized HomeDefAdapterLocalTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    HomeDefAdapterLocalTemplate result = new HomeDefAdapterLocalTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "   " + NL + "import java.util.logging.Logger;" + NL + "" + NL + "import Components.ccm.local.Assembly;" + NL + "import Components.ccm.local.AssemblyFactory;" + NL + "import Components.ccm.local.CCMException;" + NL + "import Components.ccm.local.CCMObject;" + NL + "import Components.ccm.local.CreateFailure;" + NL + "import Components.ccm.local.RemoveFailure;" + NL + "" + NL + "import ccm.local.ServiceLocator;" + NL + "" + NL + "public class ";
  protected final String TEXT_4 = "Adapter " + NL + "    implements ";
  protected final String TEXT_5 = NL + "{" + NL + "    private Logger logger = ServiceLocator.instance().getLogger();" + NL + "    " + NL + "    private ";
  protected final String TEXT_6 = " localInterface;" + NL + "    private AssemblyFactory assemblyFactory;" + NL + "    " + NL + "    protected ";
  protected final String TEXT_7 = "Adapter()" + NL + "    {" + NL + "        this(null, null);" + NL + "    }" + NL + "    " + NL + "    public ";
  protected final String TEXT_8 = "Adapter(";
  protected final String TEXT_9 = " localInterface)" + NL + "    {" + NL + "        this(localInterface, null);" + NL + "    }" + NL + "    " + NL + "    public ";
  protected final String TEXT_10 = "Adapter(";
  protected final String TEXT_11 = " localInterface, " + NL + "            AssemblyFactory assemblyFactory)" + NL + "    {" + NL + "        logger.fine(\"localInterface = \" + localInterface + \", \" + assemblyFactory);" + NL + "        this.localInterface = localInterface;" + NL + "        this.assemblyFactory = assemblyFactory;" + NL + "    }" + NL + "" + NL + "    " + NL + "    public ";
  protected final String TEXT_12 = " create()" + NL + "        throws CreateFailure" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {";
  protected final String TEXT_13 = NL + "            ";
  protected final String TEXT_14 = " c = (";
  protected final String TEXT_15 = ")localInterface.create();";
  protected final String TEXT_16 = NL + "            ";
  protected final String TEXT_17 = " component;" + NL + "            if(assemblyFactory != null)" + NL + "            {" + NL + "                Assembly assembly = assemblyFactory.create();" + NL + "                component = new ";
  protected final String TEXT_18 = "Adapter(c, assembly);" + NL + "                assembly.build(component);" + NL + "            }" + NL + "            else" + NL + "            {" + NL + "                component = new ";
  protected final String TEXT_19 = "Adapter(c);" + NL + "            }" + NL + "            return component;" + NL + "        }" + NL + "        catch(CCMException e)" + NL + "        {" + NL + "            throw new CreateFailure();" + NL + "        }    " + NL + "    }" + NL + "    " + NL + "    public void remove_component(CCMObject component) " + NL + "        throws CCMException, RemoveFailure" + NL + "    {" + NL + "        logger.fine(\"\");\t" + NL + "        component.remove();" + NL + "    }" + NL + "" + NL + "    public CCMObject create_component() " + NL + "        throws CCMException, CreateFailure" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        return create();" + NL + "    }" + NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     HomeDef home = (HomeDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(home.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(home.generateJavaNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(home.generateCcmIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(home.generateCcmIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(home.generateCcmIdentifier());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(home.getComponent().getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(home.getComponent().generateCcmIdentifier());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(home.getComponent().generateCcmIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(home.getComponent().getIdentifier());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(home.getComponent().getIdentifier());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(home.getComponent().getIdentifier());
    stringBuffer.append(TEXT_19);
    return stringBuffer.toString();
  }
}
