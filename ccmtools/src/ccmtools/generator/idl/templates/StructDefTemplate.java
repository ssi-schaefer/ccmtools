package ccmtools.generator.idl.templates;

import ccmtools.generator.idl.metamodel.*;

public class StructDefTemplate
{
  protected static String nl;
  public static synchronized StructDefTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    StructDefTemplate result = new StructDefTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "struct ";
  protected final String TEXT_3 = " ";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = "{";
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = "};";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     StructDef model = (StructDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(model.getIdentifier() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(model.generateFieldList());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
