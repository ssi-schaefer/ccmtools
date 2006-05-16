package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class HomeDefAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized HomeDefAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    HomeDefAdapterToCorbaTemplate result = new HomeDefAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import java.util.logging.Logger;" + NL + "" + NL + "import Components.ccm.local.CCMException;" + NL + "import Components.ccm.local.CCMObject;" + NL + "import Components.ccm.local.CreateFailure;" + NL + "import Components.ccm.local.RemoveFailure;" + NL + "" + NL + "import ccm.local.ServiceLocator;" + NL + "           " + NL + "public class ";
  protected final String TEXT_4 = "AdapterToCorba" + NL + "    implements ";
  protected final String TEXT_5 = NL + "{" + NL + "    private Logger logger = ServiceLocator.instance().getLogger();" + NL + "    " + NL + "    public static final String HOME_DEFAULT_NAME = \"";
  protected final String TEXT_6 = ":1.0\";" + NL + "" + NL + "    /** CORBA reference to the remote home */" + NL + "    private String homeName;" + NL + "    " + NL + "    public ";
  protected final String TEXT_7 = "AdapterToCorba()" + NL + "        throws CCMException" + NL + "    {" + NL + "        this(HOME_DEFAULT_NAME);" + NL + "        logger.fine(\"\");" + NL + "    }" + NL + "    " + NL + "    public ";
  protected final String TEXT_8 = "AdapterToCorba(String homeName)" + NL + "        throws CCMException" + NL + "    {" + NL + "        logger.fine(\"homeName = \" + homeName);" + NL + "        this.homeName = homeName;" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Home implicit operations */" + NL + "        " + NL + "    public ";
  protected final String TEXT_9 = " create()" + NL + "        throws CreateFailure" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {" + NL + "        \tComponents.CCMHome ccmHome = " + NL + "        \t    ccm.local.ServiceLocator.instance().findCCMHome(homeName);" + NL + "        \t";
  protected final String TEXT_10 = "  remoteHome = " + NL + "        \t    ";
  protected final String TEXT_11 = "Helper.narrow(ccmHome);" + NL + "            return new ";
  protected final String TEXT_12 = "AdapterToCorba(remoteHome.create());        " + NL + "        }" + NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "            throw new CreateFailure();" + NL + "        }" + NL + "    } " + NL + "    " + NL + "    // This operation is defined in ccm.local.Components.KeylessCCMHomeOperations" + NL + "    public CCMObject create_component()" + NL + "        throws CCMException, CreateFailure" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        return (CCMObject)create();" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Home explicit operations */" + NL + "        " + NL + "    // This operation is defined in ccm.local.Components.CCMHome" + NL + "    public void remove_component(CCMObject component)" + NL + "        throws CCMException, RemoveFailure" + NL + "    {" + NL + "        logger.fine(\"component = \" + component);" + NL + "        if(component == null)" + NL + "        {" + NL + "            throw new RemoveFailure(\"Can't remove component because its reference is null!\");" + NL + "        }" + NL + "        else" + NL + "        {" + NL + "            component.remove();" + NL + "        } " + NL + "    }" + NL + "}";

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
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(home.getComponent().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(home.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(home.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(home.getComponent().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_12);
    return stringBuffer.toString();
  }
}
