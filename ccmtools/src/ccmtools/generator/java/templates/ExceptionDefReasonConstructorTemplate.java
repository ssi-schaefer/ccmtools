package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class ExceptionDefReasonConstructorTemplate
{
  protected static String nl;
  public static synchronized ExceptionDefReasonConstructorTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ExceptionDefReasonConstructorTemplate result = new ExceptionDefReasonConstructorTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "    public ";
  protected final String TEXT_2 = "(String reason)    " + NL + "    {" + NL + "        super(REPOSITORY_ID + \" \" + reason);" + NL + "    }";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ExceptionDef exception = (ExceptionDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(exception.getIdentifier());
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
