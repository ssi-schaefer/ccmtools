package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class HomeDefDeploymentClientLibTemplate
{
  protected static String nl;
  public static synchronized HomeDefDeploymentClientLibTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    HomeDefDeploymentClientLibTemplate result = new HomeDefDeploymentClientLibTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "public class ";
  protected final String TEXT_4 = "ClientLibDeployment " + NL + "{" + NL + "   /** Default logger instance */" + NL + "    private static java.util.logging.Logger logger = ccmtools.local.ServiceLocator.instance().getLogger();" + NL + "    " + NL + "    " + NL + "    public static void deploy(String name)" + NL + "        throws Components.CCMException" + NL + "    {" + NL + "        logger.fine(\"name = \" + name);" + NL + "        try" + NL + "        {" + NL + "            ccmtools.corba.Components.CCMHome remoteHome = ccmtools.local.ServiceLocator.instance().findCCMHome(name);";
  protected final String TEXT_5 = NL + "            ";
  protected final String TEXT_6 = " localHome = new ";
  protected final String TEXT_7 = "AdapterToCorba(remoteHome);" + NL + "            Components.HomeFinder.instance().register_home(localHome, name);    " + NL + "        }" + NL + "        catch(ccmtools.local.ServiceLocatorException e)" + NL + "        {" + NL + "            throw new Components.CCMException(e.getMessage(), Components.CCMExceptionReason.SYSTEM_ERROR);" + NL + "        }" + NL + "    }" + NL + "" + NL + "    " + NL + "    public static void deployWithIor(String name, String ior)" + NL + "        throws Components.CCMException" + NL + "    {" + NL + "        logger.fine(\"name = \" + name + \", ior = \" + ior);" + NL + "        org.omg.CORBA.Object obj = ccmtools.local.ServiceLocator.instance().getCorbaOrb().string_to_object(ior);" + NL + "        ccmtools.corba.Components.CCMHome remoteHome = " + NL + "            ccmtools.corba.Components.CCMHomeHelper.narrow(obj);";
  protected final String TEXT_8 = NL + "        ";
  protected final String TEXT_9 = " localHome = new ";
  protected final String TEXT_10 = "AdapterToCorba(remoteHome);" + NL + "        Components.HomeFinder.instance().register_home(localHome, name);    " + NL + "    }" + NL + "" + NL + "    " + NL + "    public static void undeploy(String name)" + NL + "    {" + NL + "        logger.fine(\"name = \" + name);" + NL + "        Components.HomeFinder.instance().unregister_home(name);    " + NL + "    }" + NL + "}";
  protected final String TEXT_11 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     HomeDef home = (HomeDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(home.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(home.generateJavaNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    return stringBuffer.toString();
  }
}
