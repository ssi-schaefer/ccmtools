package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ExceptionDefCorbaConverterTemplate
{
  protected static String nl;
  public static synchronized ExceptionDefCorbaConverterTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ExceptionDefCorbaConverterTemplate result = new ExceptionDefCorbaConverterTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + " " + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import ccm.local.CorbaConverterException;" + NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL + NL + "public class ";
  protected final String TEXT_6 = "CorbaConverter" + NL + "{" + NL + "    /** Convert a local Java type into a CORBA type */" + NL + "    public static ";
  protected final String TEXT_7 = " convert(";
  protected final String TEXT_8 = " in)" + NL + "       throws CorbaConverterException" + NL + "    {";
  protected final String TEXT_9 = NL + "        ";
  protected final String TEXT_10 = " out = new ";
  protected final String TEXT_11 = "();" + NL;
  protected final String TEXT_12 = NL + "        out.";
  protected final String TEXT_13 = " = ";
  protected final String TEXT_14 = "(in.";
  protected final String TEXT_15 = "());";
  protected final String TEXT_16 = NL + "        return out;" + NL + "    }" + NL + "" + NL + "" + NL + "    /** Convert a CORBA type into a local Java type */" + NL + "    public static ";
  protected final String TEXT_17 = " convert(";
  protected final String TEXT_18 = " in)" + NL + "       throws CorbaConverterException" + NL + "    {";
  protected final String TEXT_19 = NL + "        ";
  protected final String TEXT_20 = " out = new ";
  protected final String TEXT_21 = "();";
  protected final String TEXT_22 = NL + "        out.";
  protected final String TEXT_23 = "(";
  protected final String TEXT_24 = "(in.";
  protected final String TEXT_25 = "));";
  protected final String TEXT_26 = NL + "        return out;" + NL + "    }" + NL + "};";
  protected final String TEXT_27 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ExceptionDef exception = (ExceptionDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(exception.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(exception.generateJavaRemoteNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(exception.generateJavaImportStatements(exception.generateJavaRemoteNamespace()));
    stringBuffer.append(TEXT_5);
    stringBuffer.append(exception.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(exception.generateCorbaMapping());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(exception.generateJavaMapping());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(exception.generateCorbaMapping());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(exception.generateCorbaMapping());
    stringBuffer.append(TEXT_11);
    
for(Iterator i=exception.getFields().iterator(); i.hasNext();)
{
	FieldDef field = (FieldDef)i.next();	

    stringBuffer.append(TEXT_12);
    stringBuffer.append(field.getIdentifier());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(field.getType().generateCorbaConverterType());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(field.generateGetterName());
    stringBuffer.append(TEXT_15);
    
}

    stringBuffer.append(TEXT_16);
    stringBuffer.append(exception.generateJavaMapping());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(exception.generateCorbaMapping());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(exception.generateJavaMapping());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(exception.generateJavaMapping());
    stringBuffer.append(TEXT_21);
    
for(Iterator i=exception.getFields().iterator(); i.hasNext();)
{
	FieldDef field = (FieldDef)i.next();	

    stringBuffer.append(TEXT_22);
    stringBuffer.append(field.generateSetterName());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(field.getType().generateCorbaConverterType());
    stringBuffer.append(TEXT_24);
    stringBuffer.append(field.getIdentifier());
    stringBuffer.append(TEXT_25);
    
}

    stringBuffer.append(TEXT_26);
    stringBuffer.append(TEXT_27);
    return stringBuffer.toString();
  }
}
