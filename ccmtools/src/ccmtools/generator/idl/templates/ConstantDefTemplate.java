package ccmtools.generator.idl.templates;

import ccmtools.generator.idl.metamodel.*;

public class ConstantDefTemplate
{
  protected static String nl;
  public static synchronized ConstantDefTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ConstantDefTemplate result = new ConstantDefTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = NL + " ";
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = "const ";
  protected final String TEXT_9 = " ";
  protected final String TEXT_10 = " = ";
  protected final String TEXT_11 = "; " + NL;
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ConstantDef model = (ConstantDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(model.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(model.generateIncludeGuardOpen());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(model.generateModulesOpen() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(model.getType().generateIdlMapping());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(model.generateConstantValue());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(model.generateModulesClose() );
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(model.generateIncludeGuardClose());
    stringBuffer.append(TEXT_15);
    return stringBuffer.toString();
  }
}
