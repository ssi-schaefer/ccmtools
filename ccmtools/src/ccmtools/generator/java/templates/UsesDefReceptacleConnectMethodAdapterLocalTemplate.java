package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefReceptacleConnectMethodAdapterLocalTemplate
{
  protected static String nl;
  public static synchronized UsesDefReceptacleConnectMethodAdapterLocalTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefReceptacleConnectMethodAdapterLocalTemplate result = new UsesDefReceptacleConnectMethodAdapterLocalTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "        else if(name.equals(\"";
  protected final String TEXT_2 = "\"))" + NL + "        {";
  protected final String TEXT_3 = NL + "            return connect_";
  protected final String TEXT_4 = "((";
  protected final String TEXT_5 = ") obj);";
  protected final String TEXT_6 = NL + "            connect_";
  protected final String TEXT_7 = "((";
  protected final String TEXT_8 = ") obj);" + NL + "            return new Components.CookieImpl();";
  protected final String TEXT_9 = NL + "        }";
  protected final String TEXT_10 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
     if(uses.isMultiple()) { 
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_5);
     } else { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_8);
     } 
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}
