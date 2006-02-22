package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefEquivalentMethodAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized UsesDefEquivalentMethodAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefEquivalentMethodAdapterToCorbaTemplate result = new UsesDefEquivalentMethodAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public void connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " localObject)" + NL + "        throws ccm.local.Components.AlreadyConnected, ccm.local.Components.InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"localInterface = \" + localObject);" + NL + "    \t// Create CORBA objects and connect them to the remote component" + NL + "\t\ttry" + NL + "\t\t{" + NL + "\t\t\tif(";
  protected final String TEXT_4 = " == null) " + NL + "\t\t\t{" + NL + "\t\t\t\t";
  protected final String TEXT_5 = " = localObject;" + NL + "\t\t\t\torg.omg.CORBA.Object remoteObject = " + NL + "\t\t\t\t\tcomponentPoa.servant_to_reference(" + NL + "\t\t\t\t\t    new ";
  protected final String TEXT_6 = "AdapterFromCorba(localObject));" + NL + "\t\t\t\tremoteInterface.connect_";
  protected final String TEXT_7 = "(";
  protected final String TEXT_8 = "Helper.narrow(remoteObject));" + NL + "\t\t\t}" + NL + "\t\t\telse" + NL + "\t\t\t{" + NL + "\t\t\t\tthrow new ccm.local.Components.AlreadyConnected();" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\tcatch (org.omg.PortableServer.POAPackage.ServantNotActive e)" + NL + "\t\t{" + NL + "\t\t\tthrow new ccm.local.Components.InvalidConnection();" + NL + "\t\t}" + NL + "\t\tcatch (org.omg.PortableServer.POAPackage.WrongPolicy e)" + NL + "\t\t{" + NL + "\t\t\tthrow new ccm.local.Components.InvalidConnection();" + NL + "\t\t}" + NL + "\t\tcatch (Components.AlreadyConnected e)" + NL + "\t\t{" + NL + "\t\t\tthrow new ccm.local.Components.AlreadyConnected();" + NL + "\t\t}" + NL + "\t\tcatch (Components.InvalidConnection e)" + NL + "\t\t{" + NL + "\t\t\tthrow new ccm.local.Components.InvalidConnection();" + NL + "\t\t}" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_9 = " disconnect_";
  protected final String TEXT_10 = "()" + NL + "        throws ccm.local.Components.NoConnection" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "   \t\ttry" + NL + "\t\t{" + NL + "\t\t\tif(";
  protected final String TEXT_11 = " != null)" + NL + "\t\t\t{" + NL + "\t\t\t\tremoteInterface.disconnect_";
  protected final String TEXT_12 = "();" + NL + "\t\t\t\treturn ";
  protected final String TEXT_13 = ";" + NL + "\t\t\t}" + NL + "\t\t\telse " + NL + "\t\t\t{" + NL + "\t\t\t\tthrow new ccm.local.Components.NoConnection();" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\tcatch (java.lang.Exception e)" + NL + "\t\t{" + NL + "\t\t\tthrow new ccm.local.Components.NoConnection();" + NL + "\t\t}" + NL + "    " + NL + "    }" + NL + "       " + NL + "    public ";
  protected final String TEXT_14 = " get_connection_";
  protected final String TEXT_15 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        return ";
  protected final String TEXT_16 = ";" + NL + "    }";
  protected final String TEXT_17 = NL + "    ";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
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
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaRemoteName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    return stringBuffer.toString();
  }
}
