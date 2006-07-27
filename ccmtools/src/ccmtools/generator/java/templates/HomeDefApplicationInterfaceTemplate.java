package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class HomeDefApplicationInterfaceTemplate
{
  protected static String nl;
  public static synchronized HomeDefApplicationInterfaceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    HomeDefApplicationInterfaceTemplate result = new HomeDefApplicationInterfaceTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "                 " + NL + "public interface ";
  protected final String TEXT_4 = " " + NL + "    extends ";
  protected final String TEXT_5 = "Explicit, ";
  protected final String TEXT_6 = "Implicit" + NL + "{" + NL + "}";
  protected final String TEXT_7 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     HomeDef home = (HomeDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(home.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(home.generateJavaNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(home.generateCcmIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(home.generateCcmIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(home.generateCcmIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
