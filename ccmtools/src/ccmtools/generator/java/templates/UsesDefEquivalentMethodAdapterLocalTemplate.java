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
  protected final String TEXT_3 = " localObj)" + NL + "        throws Components.AlreadyConnected, " + NL + "               Components.InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"obj = \" + localObj);" + NL + "        if(delegator!=null)" + NL + "        {" + NL + "        \ttry" + NL + "        \t{" + NL + "        \t\tdelegator.connect(\"";
  protected final String TEXT_4 = "\", localObj);" + NL + "        \t}" + NL + "        \tcatch(Components.InvalidName e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.InvalidConnection(e.getMessage());" + NL + "        \t}" + NL + "        \tcatch(Components.ExceededConnectionLimit e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.AlreadyConnected(e.getMessage());" + NL + "        \t}" + NL + "        }" + NL + "        else" + NL + "        {" + NL + "            if(";
  protected final String TEXT_5 = "Receptacle != null)" + NL + "            {" + NL + "                throw new Components.AlreadyConnected();" + NL + "            }\t" + NL + "        }";
  protected final String TEXT_6 = NL + "        ";
  protected final String TEXT_7 = "Receptacle = localObj;" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_8 = " disconnect_";
  protected final String TEXT_9 = "()" + NL + "        throws Components.NoConnection" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        if(delegator!=null)" + NL + "        {" + NL + "        \ttry" + NL + "        \t{" + NL + "        \t\tdelegator.disconnect(\"";
  protected final String TEXT_10 = "\", null);" + NL + "        \t}" + NL + "        \tcatch(Components.InvalidName e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.NoConnection(e.getMessage());" + NL + "        \t}" + NL + "        \tcatch(Components.InvalidConnection e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.NoConnection(e.getMessage());" + NL + "        \t}" + NL + "        \tcatch(Components.CookieRequired e)" + NL + "        \t{" + NL + "        \t\tthrow new Components.NoConnection(e.getMessage());" + NL + "        \t}" + NL + "        }" + NL + "        else" + NL + "        {" + NL + "            if(";
  protected final String TEXT_11 = "Receptacle == null)" + NL + "            {" + NL + "                throw new Components.NoConnection();" + NL + "            }" + NL + "        }";
  protected final String TEXT_12 = NL + "        ";
  protected final String TEXT_13 = " f = ";
  protected final String TEXT_14 = "Receptacle;";
  protected final String TEXT_15 = NL + "        ";
  protected final String TEXT_16 = "Receptacle = null;" + NL + "        return f;" + NL + "    }" + NL + "        " + NL + "    public ";
  protected final String TEXT_17 = " get_connection_";
  protected final String TEXT_18 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        return ";
  protected final String TEXT_19 = "Receptacle;" + NL + "    }";
  protected final String TEXT_20 = NL;

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
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(TEXT_20);
    return stringBuffer.toString();
  }
}
