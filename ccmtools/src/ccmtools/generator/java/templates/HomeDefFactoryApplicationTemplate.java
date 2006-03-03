package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class HomeDefFactoryApplicationTemplate
{
  protected static String nl;
  public static synchronized HomeDefFactoryApplicationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    HomeDefFactoryApplicationTemplate result = new HomeDefFactoryApplicationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "       " + NL + "public class ";
  protected final String TEXT_4 = "Factory " + NL + "{" + NL + "    public static ccm.local.Components.HomeExecutorBase create()" + NL + "        throws ccm.local.Components.CCMException" + NL + "    {" + NL + "        return new ";
  protected final String TEXT_5 = "Impl();" + NL + "    }" + NL + "}";

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
    stringBuffer.append(home.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
