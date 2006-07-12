package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class HomeDefDeploymentCorbaComponentTemplate
{
  protected static String nl;
  public static synchronized HomeDefDeploymentCorbaComponentTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    HomeDefDeploymentCorbaComponentTemplate result = new HomeDefDeploymentCorbaComponentTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import java.util.logging.Logger;" + NL + "" + NL + "import Components.ccm.local.CCMException;" + NL + "import Components.ccm.local.CCMExceptionReason;" + NL + "import ccm.local.ServiceLocator;" + NL + "import ccm.remote.CCMSessionContainer;" + NL + "" + NL + "import org.omg.PortableServer.POA;" + NL + "import org.omg.CosNaming.NamingContextExt;" + NL + "import org.omg.CosNaming.NamingContextExtHelper;" + NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL + "                                  " + NL + "public class ";
  protected final String TEXT_6 = "Deployment " + NL + "{" + NL + "    /** Default logger instance */" + NL + "    private static Logger logger = ServiceLocator.instance().getLogger();" + NL + "        " + NL + "    /** CCM session container */" + NL + "    private static CCMSessionContainer container = new CCMSessionContainer();" + NL + "    " + NL + "    " + NL + "    public static ";
  protected final String TEXT_7 = " deploy()" + NL + "        throws CCMException    " + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {";
  protected final String TEXT_8 = NL + "            ";
  protected final String TEXT_9 = " localHome = " + NL + "                (";
  protected final String TEXT_10 = ")";
  protected final String TEXT_11 = "Deployment.create();" + NL + "            container.load(new ";
  protected final String TEXT_12 = "AdapterFromCorba(container, localHome));" + NL + "            return ";
  protected final String TEXT_13 = "Helper.narrow(container.getCorbaHome());" + NL + "        }" + NL + "        catch(Exception e)" + NL + "        {" + NL + "            e.printStackTrace();" + NL + "            throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);" + NL + "        }" + NL + "    }" + NL + "    " + NL + "" + NL + "    public static void deploy(String name)" + NL + "        throws CCMException" + NL + "    {" + NL + "        logger.fine(\"name = \" + name);" + NL + "        deploy();" + NL + "        container.registerCorbaHome(name);" + NL + "    }" + NL + "" + NL + "" + NL + "    public static String deployToIor()" + NL + "        throws CCMException" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        deploy();" + NL + "        return container.getCorbaHomeIor();" + NL + "    }" + NL + "    " + NL + "    " + NL + "    public static void undeploy(String name)" + NL + "        throws CCMException" + NL + "    {" + NL + "        logger.fine(\"name = \" + name);" + NL + "        container.unregisterCorbaHome(name);" + NL + "    }" + NL + "}";
  protected final String TEXT_14 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     HomeDef home = (HomeDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(home.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(home.generateJavaRemoteNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(home.generateJavaImportStatements(home.generateJavaRemoteNamespace()));
    stringBuffer.append(TEXT_5);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(home.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(home.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(home.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    return stringBuffer.toString();
  }
}
