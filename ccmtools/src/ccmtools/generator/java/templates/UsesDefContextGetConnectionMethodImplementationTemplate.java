package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefContextGetConnectionMethodImplementationTemplate
{
  protected static String nl;
  public static synchronized UsesDefContextGetConnectionMethodImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefContextGetConnectionMethodImplementationTemplate result = new UsesDefContextGetConnectionMethodImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " get_connection_";
  protected final String TEXT_3 = "()" + NL + "        throws ccm.local.Components.NoConnection" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_4 = "_ContextImpl.get_connection_";
  protected final String TEXT_5 = "()\");" + NL + "        return component.get_connection_";
  protected final String TEXT_6 = "();" + NL + "    }";
  protected final String TEXT_7 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getComponent().generateCcmIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
