package ccmtools.generator.idl.templates;

import ccmtools.generator.idl.metamodel.*;

public class TypedefDefTemplate
{
  protected static String nl;
  public static synchronized TypedefDefTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    TypedefDefTemplate result = new TypedefDefTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "typedef ";
  protected final String TEXT_3 = " ";
  protected final String TEXT_4 = "; ";
  protected final String TEXT_5 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     TypedefDef model = (TypedefDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(model.indent());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(model.getAlias().generateIdlMapping());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(model.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
