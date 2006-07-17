package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ExceptionDefConstructorTemplate
{
  protected static String nl;
  public static synchronized ExceptionDefConstructorTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ExceptionDefConstructorTemplate result = new ExceptionDefConstructorTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "    public ";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = ") " + NL + "    {" + NL + "        super(REPOSITORY_ID);";
  protected final String TEXT_4 = NL + "        ";
  protected final String TEXT_5 = "(";
  protected final String TEXT_6 = ");";
  protected final String TEXT_7 = NL + "    }" + NL + "        " + NL + "    public ";
  protected final String TEXT_8 = "(String reason, ";
  protected final String TEXT_9 = ")    " + NL + "    {" + NL + "        super(REPOSITORY_ID + \" \" + reason);";
  protected final String TEXT_10 = NL + "        ";
  protected final String TEXT_11 = "(";
  protected final String TEXT_12 = ");";
  protected final String TEXT_13 = "       " + NL + "    }";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ExceptionDef exception = (ExceptionDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(exception.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(exception.generateConstructorParameterList());
    stringBuffer.append(TEXT_3);
    
for(Iterator i=exception.getFields().iterator(); i.hasNext();)
{
	FieldDef field = (FieldDef)i.next();

    stringBuffer.append(TEXT_4);
    stringBuffer.append(field.generateSetterName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(field.getIdentifier());
    stringBuffer.append(TEXT_6);
    
}

    stringBuffer.append(TEXT_7);
    stringBuffer.append(exception.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(exception.generateConstructorParameterList());
    stringBuffer.append(TEXT_9);
    
for(Iterator i=exception.getFields().iterator(); i.hasNext();)
{
	FieldDef field = (FieldDef)i.next();

    stringBuffer.append(TEXT_10);
    stringBuffer.append(field.generateSetterName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(field.getIdentifier());
    stringBuffer.append(TEXT_12);
    
}

    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
}
