package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class StructDefConstructorTemplate
{
  protected static String nl;
  public static synchronized StructDefConstructorTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    StructDefConstructorTemplate result = new StructDefConstructorTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "    public ";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = ") " + NL + "    {";
  protected final String TEXT_4 = NL + "        ";
  protected final String TEXT_5 = "(";
  protected final String TEXT_6 = ");";
  protected final String TEXT_7 = NL + "    }";
  protected final String TEXT_8 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     StructDef struct = (StructDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(struct.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(struct.generateConstructorParameterList());
    stringBuffer.append(TEXT_3);
    
for(Iterator i=struct.getFields().iterator(); i.hasNext();)
{
	FieldDef field = (FieldDef)i.next();

    stringBuffer.append(TEXT_4);
    stringBuffer.append(field.generateSetterName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(field.getIdentifier());
    stringBuffer.append(TEXT_6);
    
}

    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
