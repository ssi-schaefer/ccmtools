package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class ProvidesDefGetMethodAssemblyImplementationTemplate
{
  protected static String nl;
  public static synchronized ProvidesDefGetMethodAssemblyImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ProvidesDefGetMethodAssemblyImplementationTemplate result = new ProvidesDefGetMethodAssemblyImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " get_";
  protected final String TEXT_3 = "()" + NL + "    {" + NL + "        return new ";
  protected final String TEXT_4 = ".";
  protected final String TEXT_5 = "Impl(this, ";
  protected final String TEXT_6 = ");" + NL + "    }";
  protected final String TEXT_7 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    ProvidesDef provides = (ProvidesDef) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(provides.getInterface().generateAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(provides.generateJavaNamespace());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(provides.getComponent().getIdentifier());
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(provides.getAssemblyFacet());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
