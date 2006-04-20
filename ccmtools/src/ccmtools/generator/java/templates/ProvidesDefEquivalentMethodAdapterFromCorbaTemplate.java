package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class ProvidesDefEquivalentMethodAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized ProvidesDefEquivalentMethodAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ProvidesDefEquivalentMethodAdapterFromCorbaTemplate result = new ProvidesDefEquivalentMethodAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " provide_";
  protected final String TEXT_3 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {" + NL + "            if(";
  protected final String TEXT_4 = "Facet == null)" + NL + "            {";
  protected final String TEXT_5 = NL + "                ";
  protected final String TEXT_6 = " localAdapter = " + NL + "                    localComponent.provide_";
  protected final String TEXT_7 = "();" + NL + "                Servant servant = new ";
  protected final String TEXT_8 = "AdapterFromCorba(localAdapter);" + NL + "                org.omg.CORBA.Object obj = " + NL + "                    container.getCorbaObjectFromServant(servant);";
  protected final String TEXT_9 = NL + "                ";
  protected final String TEXT_10 = "Facet = ";
  protected final String TEXT_11 = "Helper.narrow(obj);" + NL + "            }" + NL + "        }" + NL + "        catch(CCMException e)" + NL + "        {" + NL + "            e.printStackTrace();" + NL + "        }" + NL + "        return ";
  protected final String TEXT_12 = "Facet;" + NL + "    }";
  protected final String TEXT_13 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ProvidesDef provides = (ProvidesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(provides.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(provides.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(provides.getInterface().getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(provides.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
}
