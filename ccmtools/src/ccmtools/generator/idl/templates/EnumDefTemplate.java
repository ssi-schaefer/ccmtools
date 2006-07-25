package ccmtools.generator.idl.templates;

import ccmtools.generator.idl.metamodel.*;

public class EnumDefTemplate
{
  protected static String nl;
  public static synchronized EnumDefTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    EnumDefTemplate result = new EnumDefTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "enum ";
  protected final String TEXT_3 = " ";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = "{";
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = "};";
  protected final String TEXT_9 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     EnumDef model = (EnumDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(model.getIdentifier() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(model.indent());
    stringBuffer.append(model.generateMemberList());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}
