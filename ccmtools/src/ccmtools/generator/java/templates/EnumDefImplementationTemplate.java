package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class EnumDefImplementationTemplate
{
  protected static String nl;
  public static synchronized EnumDefImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    EnumDefImplementationTemplate result = new EnumDefImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + " " + NL + "public enum ";
  protected final String TEXT_4 = " " + NL + "{";
  protected final String TEXT_5 = NL + "    ";
  protected final String TEXT_6 = NL + "}";
  protected final String TEXT_7 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     EnumDef enumeration = (EnumDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(enumeration.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(enumeration.generateJavaNamespace() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(enumeration.getIdentifier() );
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(enumeration.generateMemberList());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
