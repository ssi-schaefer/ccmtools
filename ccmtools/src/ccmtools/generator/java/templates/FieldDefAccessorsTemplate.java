package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class FieldDefAccessorsTemplate
{
  protected static String nl;
  public static synchronized FieldDefAccessorsTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    FieldDefAccessorsTemplate result = new FieldDefAccessorsTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = "()" + NL + "    {" + NL + "        return this.";
  protected final String TEXT_4 = ";" + NL + "    }     " + NL + "" + NL + "    public void ";
  protected final String TEXT_5 = "(";
  protected final String TEXT_6 = " value)" + NL + "    {" + NL + "        this.";
  protected final String TEXT_7 = " = value;" + NL + "    }";
  protected final String TEXT_8 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     FieldDef field = (FieldDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(field.getType().generateJavaMapping());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(field.generateGetterName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(field.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(field.generateSetterName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(field.getType().generateJavaMapping());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(field.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
