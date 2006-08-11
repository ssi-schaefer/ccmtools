package ccmtools.generator.idl.templates;

import ccmtools.generator.idl.metamodel.*;

public class Idl2MultipleConnectionTemplate
{
  protected static String nl;
  public static synchronized Idl2MultipleConnectionTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    Idl2MultipleConnectionTemplate result = new Idl2MultipleConnectionTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "    struct ";
  protected final String TEXT_3 = "Connection";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = "    {";
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = "        ";
  protected final String TEXT_8 = " objref;";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "        ::Components::Cookie ck;";
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = "    };" + NL;
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = "    typedef sequence<";
  protected final String TEXT_15 = "Connection> ";
  protected final String TEXT_16 = "Connections;" + NL;
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = "    ::Components::Cookie connect_";
  protected final String TEXT_19 = "(in ";
  protected final String TEXT_20 = " connection)";
  protected final String TEXT_21 = NL;
  protected final String TEXT_22 = "        raises(::Components::ExceededConnectionLimit, ::Components::InvalidConnection);" + NL;
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = "    ";
  protected final String TEXT_25 = " disconnect_";
  protected final String TEXT_26 = "(in ::Components::Cookie ck)";
  protected final String TEXT_27 = NL;
  protected final String TEXT_28 = "        raises(::Components::InvalidConnection);" + NL;
  protected final String TEXT_29 = NL;
  protected final String TEXT_30 = "    ";
  protected final String TEXT_31 = "Connections get_connections_";
  protected final String TEXT_32 = "();";
  protected final String TEXT_33 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ReceptacleDef model = (ReceptacleDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(model.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(model.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_24);
    stringBuffer.append(model.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_25);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_26);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_28);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_30);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_31);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_32);
    stringBuffer.append(TEXT_33);
    return stringBuffer.toString();
  }
}
