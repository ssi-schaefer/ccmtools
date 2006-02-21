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
  protected final String TEXT_2 = "()" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_3 = "_ContextImpl.get_connections_";
  protected final String TEXT_4 = "()\");" + NL + "        return component.get_connections_";
  protected final String TEXT_5 = "();" + NL + "    }";
  protected final String TEXT_6 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getComponent().generateCcmIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}
