package ccmtools.generator.idl.templates;

import ccmtools.generator.idl.metamodel.*;

public class Idl2ComponentDefTemplate
{
  protected static String nl;
  public static synchronized Idl2ComponentDefTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    Idl2ComponentDefTemplate result = new Idl2ComponentDefTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = NL + "#include <ccmtools/corba/Components/ccmtools.idl>" + NL;
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = NL + " ";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "interface ";
  protected final String TEXT_11 = " : Components::CCMObject";
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = "{";
  protected final String TEXT_16 = NL;
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = NL;
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "};" + NL;
  protected final String TEXT_21 = NL;
  protected final String TEXT_22 = NL;
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ComponentDef model = (ComponentDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(model.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(model.generateIncludeGuardOpen());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(model.generateIdl2IncludeStatements());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(model.generateModulesOpen() );
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(model.generateIdl2SupportedInterfaces());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(model.generateBaseComponent());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(model.generateAttributes());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(model.generateIdl2ProvidedInterfaces());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(model.generateIdl2UsedInterfaces());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(model.generateIdl2ModulesClose() );
    stringBuffer.append(TEXT_22);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(model.generateIncludeGuardClose());
    stringBuffer.append(TEXT_24);
    return stringBuffer.toString();
  }
}
