package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefEquivalentMethodAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized UsesDefEquivalentMethodAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefEquivalentMethodAdapterFromCorbaTemplate result = new UsesDefEquivalentMethodAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public void connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " obj)" + NL + "        throws Components.AlreadyConnected, Components.InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {";
  protected final String TEXT_4 = NL + "            ";
  protected final String TEXT_5 = "Receptacle = obj;";
  protected final String TEXT_6 = NL + "            ";
  protected final String TEXT_7 = " localInterfaceAdapter = " + NL + "                new ";
  protected final String TEXT_8 = "AdapterToCorba(obj);" + NL + "            localInterface.connect_";
  protected final String TEXT_9 = "(localInterfaceAdapter);" + NL + "        }" + NL + "        catch(AlreadyConnected e)" + NL + "        {" + NL + "            e.printStackTrace();" + NL + "            throw new Components.AlreadyConnected(e.getMessage());" + NL + "        }" + NL + "        catch(InvalidConnection e)" + NL + "        {" + NL + "            e.printStackTrace();" + NL + "            throw new Components.InvalidConnection(e.getMessage());" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_10 = " disconnect_";
  protected final String TEXT_11 = "()" + NL + "        throws Components.NoConnection" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {" + NL + "            localInterface.disconnect_";
  protected final String TEXT_12 = "();";
  protected final String TEXT_13 = NL + "            ";
  protected final String TEXT_14 = " result = ";
  protected final String TEXT_15 = "Receptacle;";
  protected final String TEXT_16 = NL + "            ";
  protected final String TEXT_17 = "Receptacle = null; " + NL + "            return result;" + NL + "        }" + NL + "        catch(NoConnection e)" + NL + "        {" + NL + "            e.printStackTrace();" + NL + "            throw new Components.NoConnection(e.getMessage());" + NL + "        }" + NL + "    }" + NL + "     " + NL + "    public ";
  protected final String TEXT_18 = " get_connection_";
  protected final String TEXT_19 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        return ";
  protected final String TEXT_20 = "Receptacle;" + NL + "    }";
  protected final String TEXT_21 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(uses.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(uses.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(uses.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(TEXT_21);
    return stringBuffer.toString();
  }
}
