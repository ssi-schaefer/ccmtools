package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate result = new UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public Components.Cookie connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " obj)" + NL + "        throws Components.ExceededConnectionLimit, Components.InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"Object reference = \" + obj);" + NL + "        if(obj == null)" + NL + "        {" + NL + "            throw new Components.InvalidConnection(\"Given object reference is null!\");" + NL + "        }" + NL + "        else" + NL + "        {" + NL + "            try" + NL + "            {";
  protected final String TEXT_4 = NL + "                ";
  protected final String TEXT_5 = " localInterfaceAdapter =" + NL + "                    new world.ccm.local.IFaceAdapterToCorba(obj);" + NL + "                Cookie lck = localInterface.connect_";
  protected final String TEXT_6 = "(localInterfaceAdapter);" + NL + "                Components.Cookie ck = new Components.CookieImpl(lck.getCookieValue());";
  protected final String TEXT_7 = NL + "                ";
  protected final String TEXT_8 = "ReceptacleMap.put(ck, obj);" + NL + "                return ck;" + NL + "            }" + NL + "            catch(ExceededConnectionLimit e)" + NL + "            {" + NL + "                e.printStackTrace();" + NL + "                throw new Components.ExceededConnectionLimit(e.getMessage());" + NL + "            }" + NL + "            catch(InvalidConnection e)" + NL + "            {" + NL + "                e.printStackTrace();" + NL + "                throw new Components.InvalidConnection(e.getMessage());" + NL + "            }" + NL + "        }    " + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_9 = " disconnect_";
  protected final String TEXT_10 = "(Components.Cookie ck)" + NL + "        throws Components.InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"Cookie = \" + ck);" + NL + "" + NL + "        if(ck == null || !portReceptacleMap.containsKey(ck))" + NL + "        {" + NL + "            throw new Components.InvalidConnection();" + NL + "        }" + NL + "        else" + NL + "        {" + NL + "            try" + NL + "            { " + NL + "                Cookie lck = new CookieImpl(ck.CookieValue);" + NL + "                localInterface.disconnect_port(lck);";
  protected final String TEXT_11 = NL + "                ";
  protected final String TEXT_12 = " f = ";
  protected final String TEXT_13 = "ReceptacleMap.get(ck);";
  protected final String TEXT_14 = NL + "                ";
  protected final String TEXT_15 = "ReceptacleMap.remove(ck);" + NL + "                return f;" + NL + "            }" + NL + "            catch(InvalidConnection e)" + NL + "            {" + NL + "                e.printStackTrace();" + NL + "                throw new Components.InvalidConnection(e.getMessage());" + NL + "            }" + NL + "        }    " + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_16 = "Package.";
  protected final String TEXT_17 = "Connection[] get_connections_";
  protected final String TEXT_18 = "()" + NL + "    {" + NL + "        logger.fine(\"\");";
  protected final String TEXT_19 = NL + "        ";
  protected final String TEXT_20 = "Package.";
  protected final String TEXT_21 = "Connection[] connections = " + NL + "            new ";
  protected final String TEXT_22 = "Package.";
  protected final String TEXT_23 = "Connection[portReceptacleMap.size()];" + NL + "        for(Iterator i = ";
  protected final String TEXT_24 = "ReceptacleMap.keySet().iterator(); i.hasNext();)" + NL + "        {" + NL + "            Components.Cookie ck = (Components.Cookie)i.next();";
  protected final String TEXT_25 = NL + "            ";
  protected final String TEXT_26 = " obj = ";
  protected final String TEXT_27 = "ReceptacleMap.get(ck);";
  protected final String TEXT_28 = NL + "            ";
  protected final String TEXT_29 = "Package.";
  protected final String TEXT_30 = "Connection c = new";
  protected final String TEXT_31 = NL + "                ";
  protected final String TEXT_32 = "Package.";
  protected final String TEXT_33 = "Connection(obj, ck);" + NL + "        }       " + NL + "        return connections;    " + NL + "    }";
  protected final String TEXT_34 = NL + "     ";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     
UsesDef uses = (UsesDef) argument;  
InterfaceDef iface = uses.getInterface();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(iface.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(iface.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(iface.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(iface.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(uses.getComponent().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(uses.getComponent().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_21);
    stringBuffer.append(uses.getComponent().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_24);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(iface.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_26);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_27);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(uses.getComponent().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_30);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(uses.getComponent().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_32);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_33);
    stringBuffer.append(TEXT_34);
    return stringBuffer.toString();
  }
}
