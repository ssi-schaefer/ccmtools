package ccmtools.JavaClientLib.templates;

import ccmtools.JavaClientLib.metamodel.*;

public class HomeExplicitDeclarationTemplate
{
  protected static String nl;
  public static synchronized HomeExplicitDeclarationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    HomeExplicitDeclarationTemplate result = new HomeExplicitDeclarationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * ";
  protected final String TEXT_3 = NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_4 = ";" + NL + "                 " + NL + "public interface ";
  protected final String TEXT_5 = "Explicit " + NL + "    extends ccm.local.Components.CCMHome" + NL + "{" + NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     HomeDef home = (HomeDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(home.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(home.generateTimestamp());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(home.getJavaNamespace());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(home.getIdentifier());
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
