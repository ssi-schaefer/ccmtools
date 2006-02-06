package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class CatchStatementToCorbaTemplate
{
  protected static String nl;
  public static synchronized CatchStatementToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    CatchStatementToCorbaTemplate result = new CatchStatementToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\tcatch(";
  protected final String TEXT_2 = " e)" + NL + "\t{\t    " + NL + "\t    throw e; // currently we don't convert exceptions" + NL + "\t}" + NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     OperationDef op = (OperationDef) argument;  
    
for(Iterator i = op.getException().iterator(); i.hasNext(); )
{
    ExceptionDef ex = (ExceptionDef)i.next();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(ex.getAbsoluteIdlName());
    stringBuffer.append(TEXT_2);
    
}

    return stringBuffer.toString();
  }
}
