package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class OperationDefCatchStatementConverterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized OperationDefCatchStatementConverterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    OperationDefCatchStatementConverterFromCorbaTemplate result = new OperationDefCatchStatementConverterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\tcatch(";
  protected final String TEXT_2 = " e)" + NL + "\t{" + NL + "\t    throw ";
  protected final String TEXT_3 = "(e);" + NL + "\t}" + NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     OperationDef op = (OperationDef) argument;  
    
for(Iterator i = op.getException().iterator(); i.hasNext(); )
{
    ExceptionDef ex = (ExceptionDef)i.next();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(ex.generateJavaMapping());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(ex.generateCorbaConverterType());
    stringBuffer.append(TEXT_3);
    
}

    return stringBuffer.toString();
  }
}
