package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefMultipleContextGetConnectionMethodImplementationTemplate
{
  protected static String nl;
  public static synchronized UsesDefMultipleContextGetConnectionMethodImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefMultipleContextGetConnectionMethodImplementationTemplate result = new UsesDefMultipleContextGetConnectionMethodImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public java.util.Map get_connections_";
  protected final String TEXT_2 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        return component.get_connections_";
  protected final String TEXT_3 = "();" + NL + "    }";
  protected final String TEXT_4 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
