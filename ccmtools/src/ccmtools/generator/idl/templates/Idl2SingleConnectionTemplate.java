package ccmtools.generator.idl.templates;

import ccmtools.generator.idl.metamodel.*;

public class Idl2SingleConnectionTemplate
{
  protected static String nl;
  public static synchronized Idl2SingleConnectionTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    Idl2SingleConnectionTemplate result = new Idl2SingleConnectionTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "    void connect_";
  protected final String TEXT_3 = "(in ";
  protected final String TEXT_4 = " conxn)";
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = "        raises(Components::AlreadyConnected, Components::InvalidConnection);" + NL;
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = "    ";
  protected final String TEXT_9 = " disconnect_";
  protected final String TEXT_10 = "()";
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = "        raises(Components::NoConnection);" + NL;
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = "    ";
  protected final String TEXT_15 = " get_connection_";
  protected final String TEXT_16 = "();";
  protected final String TEXT_17 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ReceptacleDef model = (ReceptacleDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(model.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(model.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(model.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    return stringBuffer.toString();
  }
}
