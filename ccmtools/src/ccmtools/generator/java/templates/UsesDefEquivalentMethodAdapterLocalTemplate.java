package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefEquivalentMethodAdapterLocalTemplate
{
  protected static String nl;
  public static synchronized UsesDefEquivalentMethodAdapterLocalTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefEquivalentMethodAdapterLocalTemplate result = new UsesDefEquivalentMethodAdapterLocalTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public void connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " localObj)" + NL + "        throws AlreadyConnected, " + NL + "               InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"obj = \" + localObj);" + NL + "        if(";
  protected final String TEXT_4 = "Receptacle != null)" + NL + "        {" + NL + "            throw new AlreadyConnected();" + NL + "        }\t" + NL + "        else" + NL + "        {";
  protected final String TEXT_5 = NL + "            ";
  protected final String TEXT_6 = "Receptacle = localObj;" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_7 = " disconnect_";
  protected final String TEXT_8 = "()" + NL + "        throws NoConnection" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        if(";
  protected final String TEXT_9 = "Receptacle == null)" + NL + "        {" + NL + "            throw new NoConnection();" + NL + "        }" + NL + "        else" + NL + "        {";
  protected final String TEXT_10 = NL + "            ";
  protected final String TEXT_11 = " f = ";
  protected final String TEXT_12 = "Receptacle;";
  protected final String TEXT_13 = NL + "            ";
  protected final String TEXT_14 = "Receptacle = null;" + NL + "            return f;" + NL + "        }" + NL + "    }" + NL + "        " + NL + "    public ";
  protected final String TEXT_15 = " get_connection_";
  protected final String TEXT_16 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        return ";
  protected final String TEXT_17 = "Receptacle;" + NL + "    }";
  protected final String TEXT_18 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(TEXT_18);
    return stringBuffer.toString();
  }
}
