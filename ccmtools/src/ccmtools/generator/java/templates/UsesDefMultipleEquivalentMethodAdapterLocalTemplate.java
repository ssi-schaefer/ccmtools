package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefMultipleEquivalentMethodAdapterLocalTemplate
{
  protected static String nl;
  public static synchronized UsesDefMultipleEquivalentMethodAdapterLocalTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefMultipleEquivalentMethodAdapterLocalTemplate result = new UsesDefMultipleEquivalentMethodAdapterLocalTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public Components.Cookie connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " obj)" + NL + "        throws Components.ExceededConnectionLimit, " + NL + "               Components.InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"Object reference = \" + obj);" + NL + "        if(obj == null)" + NL + "        {" + NL + "            throw new Components.InvalidConnection();" + NL + "        }" + NL + "        Components.Cookie ck;" + NL + "        if(delegator!=null)" + NL + "        {" + NL + "        \ttry" + NL + "        \t{" + NL + "        \t\tck = delegator.connect(\"";
  protected final String TEXT_4 = "\", obj);" + NL + "        \t}" + NL + "        \tcatch(Components.InvalidName e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.InvalidConnection(e.getMessage());" + NL + "        \t}" + NL + "        \tcatch(Components.AlreadyConnected e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.ExceededConnectionLimit(e.getMessage());" + NL + "        \t}" + NL + "        }" + NL + "        else" + NL + "        {" + NL + "            ck = new Components.CookieImpl();            " + NL + "        }";
  protected final String TEXT_5 = NL + "        ";
  protected final String TEXT_6 = "ReceptacleMap.put(ck, obj);" + NL + "        return ck;" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_7 = " disconnect_";
  protected final String TEXT_8 = "(Components.Cookie ck)" + NL + "        throws Components.InvalidConnection" + NL + "    {" + NL + "\t    logger.fine(\"Cookie = \" + ck);        " + NL + "        if(!";
  protected final String TEXT_9 = "ReceptacleMap.containsKey(ck))" + NL + "        {" + NL + "            throw new Components.InvalidConnection();" + NL + "        }" + NL + "        if(delegator!=null)" + NL + "        {" + NL + "        \ttry" + NL + "        \t{" + NL + "        \t\tdelegator.disconnect(\"";
  protected final String TEXT_10 = "\", ck);" + NL + "        \t}" + NL + "        \tcatch(Components.InvalidName e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.InvalidConnection(e.getMessage());" + NL + "        \t}" + NL + "        \tcatch(Components.CookieRequired e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.InvalidConnection(e.getMessage());" + NL + "        \t}" + NL + "        \tcatch(Components.NoConnection e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.InvalidConnection(e.getMessage());" + NL + "        \t}" + NL + "        }";
  protected final String TEXT_11 = NL + "        ";
  protected final String TEXT_12 = " f = ";
  protected final String TEXT_13 = "ReceptacleMap.get(ck);";
  protected final String TEXT_14 = NL + "        ";
  protected final String TEXT_15 = "ReceptacleMap.remove(ck);" + NL + "        return f;" + NL + "    }" + NL + "        " + NL + "    public java.util.Map<Components.Cookie, ";
  protected final String TEXT_16 = "> get_connections_";
  protected final String TEXT_17 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        // return a copy of the receptacle map" + NL + "        return new java.util.HashMap<Components.Cookie, ";
  protected final String TEXT_18 = ">(";
  protected final String TEXT_19 = "ReceptacleMap);" + NL + "    }";
  protected final String TEXT_20 = NL;

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
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(iface.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(iface.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(iface.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(TEXT_20);
    return stringBuffer.toString();
  }
}
