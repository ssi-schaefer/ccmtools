package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class EnumDefCorbaConverterTemplate
{
  protected static String nl;
  public static synchronized EnumDefCorbaConverterTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    EnumDefCorbaConverterTemplate result = new EnumDefCorbaConverterTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + " " + NL + "package ";
  protected final String TEXT_3 = ";" + NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL + NL + "public class ";
  protected final String TEXT_6 = "CorbaConverter" + NL + "{" + NL + "    /** Convert a local Java type into a CORBA type */" + NL + "    public static ";
  protected final String TEXT_7 = " convert(";
  protected final String TEXT_8 = " in)" + NL + "       throws ccmtools.local.CorbaConverterException" + NL + "    {";
  protected final String TEXT_9 = NL + "        ";
  protected final String TEXT_10 = " out;" + NL + "        switch(in)" + NL + "        {";
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = NL + "            default:" + NL + "                out = null;" + NL + "        }" + NL + "        return out;" + NL + "    }" + NL + "" + NL + "" + NL + "    /** Convert a CORBA type into a local Java type */" + NL + "    public static ";
  protected final String TEXT_13 = " convert(";
  protected final String TEXT_14 = " in)" + NL + "       throws ccmtools.local.CorbaConverterException" + NL + "    {";
  protected final String TEXT_15 = NL + "        ";
  protected final String TEXT_16 = " out; " + NL + "        switch(in.value())" + NL + "        {";
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = NL + "            default:" + NL + "                out = null;" + NL + "        }" + NL + "        return out;" + NL + "    }" + NL + "};";
  protected final String TEXT_19 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     EnumDef enumeration = (EnumDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(enumeration.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(enumeration.generateJavaRemoteNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(enumeration.generateJavaImportStatements(enumeration.generateJavaRemoteNamespace()));
    stringBuffer.append(TEXT_5);
    stringBuffer.append(enumeration.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(enumeration.generateCorbaMapping());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(enumeration.generateJavaMapping());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(enumeration.generateCorbaMapping());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(enumeration.generateCaseConvertersToCorba());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(enumeration.generateJavaMapping());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(enumeration.generateCorbaMapping());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(enumeration.generateJavaMapping());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(enumeration.generateCaseConvertersFromCorba());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    return stringBuffer.toString();
  }
}
