package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ExceptionDefImplementationTemplate
{
  protected static String nl;
  public static synchronized ExceptionDefImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ExceptionDefImplementationTemplate result = new ExceptionDefImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + " ";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL + " " + NL + "public class ";
  protected final String TEXT_6 = " " + NL + "    extends Components.ccm.local.UserException" + NL + "{" + NL + "    private static final String REPOSITORY_ID = \"";
  protected final String TEXT_7 = "\";" + NL + "    ";
  protected final String TEXT_8 = NL + "    ";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = NL + NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ExceptionDef exception = (ExceptionDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(exception.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(exception.generateJavaNamespace() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(exception.generateJavaImportStatements());
    stringBuffer.append(TEXT_5);
    stringBuffer.append( exception.getIdentifier() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(exception.generateRepositoryId());
    stringBuffer.append(TEXT_7);
    
for(Iterator i=exception.getFields().iterator(); i.hasNext();)
{
	FieldDef field = (FieldDef)i.next();

    stringBuffer.append(TEXT_8);
    stringBuffer.append(field.generateDeclaration());
    
}

    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(exception.generateDefaultConstructor());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(exception.generateConstructor());
    stringBuffer.append(TEXT_13);
    
for(Iterator i=exception.getFields().iterator(); i.hasNext();)
{
	FieldDef field = (FieldDef)i.next();

    stringBuffer.append(TEXT_14);
    stringBuffer.append(field.generateAccessors());
    
}

    stringBuffer.append(TEXT_15);
    return stringBuffer.toString();
  }
}
