package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class HomeDefExplicitInterfaceTemplate
{
  protected static String nl;
  public static synchronized HomeDefExplicitInterfaceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    HomeDefExplicitInterfaceTemplate result = new HomeDefExplicitInterfaceTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "public interface ";
  protected final String TEXT_4 = "Explicit " + NL + "    extends Components.CCMHome" + NL + "{" + NL + "}";
  protected final String TEXT_5 = NL;

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
    return stringBuffer.toString();
  }
}
