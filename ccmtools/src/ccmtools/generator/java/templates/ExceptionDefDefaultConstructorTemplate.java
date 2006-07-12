package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ExceptionDefDefaultConstructorTemplate
{
  protected static String nl;
  public static synchronized ExceptionDefDefaultConstructorTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ExceptionDefDefaultConstructorTemplate result = new ExceptionDefDefaultConstructorTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "    public ";
  protected final String TEXT_2 = "() " + NL + "    {" + NL + "        super(REPOSITORY_ID);";
  protected final String TEXT_3 = NL + "        ";
  protected final String TEXT_4 = "(";
  protected final String TEXT_5 = ");";
  protected final String TEXT_6 = NL + "    }";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ExceptionDef exception = (ExceptionDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(exception.getIdentifier());
    stringBuffer.append(TEXT_2);
    
for(Iterator i=exception.getFields().iterator(); i.hasNext();)
{
	FieldDef field = (FieldDef)i.next();

    stringBuffer.append(TEXT_3);
    stringBuffer.append(field.generateSetterName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(field.getType().generateJavaDefaultReturnValue());
    stringBuffer.append(TEXT_5);
    
}

    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}
