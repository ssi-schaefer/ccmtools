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
  protected final String TEXT_2 = " eLocal)" + NL + "\t{" + NL + "\t    try" + NL + "\t    {" + NL + "\t        ";
  protected final String TEXT_3 = " eRemote =" + NL + "\t             ";
  protected final String TEXT_4 = "(eLocal);" + NL + "\t        throw eRemote;" + NL + "\t    }" + NL + "\t    catch(ccmtools.local.CorbaConverterException eConvert)" + NL + "\t    {" + NL + "\t        throw new org.omg.CORBA.BAD_OPERATION(eConvert.getMessage());" + NL + "\t    }" + NL + "\t}" + NL;
  protected final String TEXT_5 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     OperationDef op = (OperationDef) argument;  
    
for(Iterator i = op.getException().iterator(); i.hasNext(); )
{
    ExceptionDef ex = (ExceptionDef)i.next();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(ex.generateJavaMapping());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(ex.generateCorbaMapping());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(ex.generateCorbaConverterType());
    stringBuffer.append(TEXT_4);
    
}

    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
