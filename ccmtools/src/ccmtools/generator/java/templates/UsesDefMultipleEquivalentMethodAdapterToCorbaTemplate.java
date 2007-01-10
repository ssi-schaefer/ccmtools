package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate result = new UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public Cookie connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " localObject)" + NL + "        throws ExceededConnectionLimit, InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"Object reference = \" + localObject);" + NL + "        try" + NL + "        {" + NL + "            if(localObject == null)" + NL + "            {" + NL + "                throw new InvalidConnection();" + NL + "            }" + NL + "            else" + NL + "            {" + NL + "            \t    Servant servant = new ";
  protected final String TEXT_4 = "AdapterFromCorba(localObject);" + NL + "                org.omg.CORBA.Object remoteObject =  componentPoa.servant_to_reference(servant);" + NL + "                ccmtools.corba.Components.Cookie rck = " + NL + "                    remoteInterface.connect_";
  protected final String TEXT_5 = "(";
  protected final String TEXT_6 = "Helper.narrow(remoteObject));" + NL + "                Cookie ck = new CookieImpl(rck.CookieValue);" + NL + "                portReceptacleMap.put(ck, localObject);" + NL + "                return ck;" + NL + "            }" + NL + "        }" + NL + "        catch (ServantNotActive e)" + NL + "        {" + NL + "            throw new InvalidConnection();" + NL + "        }" + NL + "        catch (WrongPolicy e)" + NL + "        {" + NL + "            throw new InvalidConnection();" + NL + "        }" + NL + "        catch(ccmtools.corba.Components.ExceededConnectionLimit e)" + NL + "        {" + NL + "            throw new ExceededConnectionLimit();" + NL + "        }" + NL + "        catch(ccmtools.corba.Components.InvalidConnection e)" + NL + "        {" + NL + "            throw new InvalidConnection();" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_7 = " disconnect_";
  protected final String TEXT_8 = "(Cookie ck)" + NL + "        throws InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"Cookie = \" + ck);" + NL + "        try" + NL + "        {" + NL + "            if(ck == null || !";
  protected final String TEXT_9 = "ReceptacleMap.containsKey(ck))" + NL + "            {" + NL + "                throw new InvalidConnection();" + NL + "            }" + NL + "            else" + NL + "            {" + NL + "                ccmtools.corba.Components.Cookie rck = " + NL + "                    new ccmtools.corba.Components.CookieImpl(ck.getCookieValue());" + NL + "                remoteInterface.disconnect_port(rck);\t";
  protected final String TEXT_10 = NL + "                ";
  protected final String TEXT_11 = " obj = ";
  protected final String TEXT_12 = "ReceptacleMap.get(ck);";
  protected final String TEXT_13 = NL + "                ";
  protected final String TEXT_14 = "ReceptacleMap.remove(ck);\t" + NL + "                return obj;" + NL + "            }" + NL + "        }" + NL + "        catch(ccmtools.corba.Components.InvalidConnection e)" + NL + "        {" + NL + "            e.printStackTrace();" + NL + "            throw new InvalidConnection();" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public Map<Cookie, ";
  protected final String TEXT_15 = "> get_connections_";
  protected final String TEXT_16 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        // return a copy of the receptacle map" + NL + "        return new java.util.HashMap<Cookie, ";
  protected final String TEXT_17 = ">(";
  protected final String TEXT_18 = "ReceptacleMap);" + NL + "    }";
  protected final String TEXT_19 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
UsesDef uses = (UsesDef) argument;  
InterfaceDef iface = uses.getInterface();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(iface.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(iface.generateAbsoluteJavaRemoteName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(iface.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(iface.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(iface.generateAbsoluteJavaName());
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
    stringBuffer.append(iface.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    return stringBuffer.toString();
  }
}
