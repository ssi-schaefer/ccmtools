package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class InterfaceDefAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized InterfaceDefAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    InterfaceDefAdapterFromCorbaTemplate result = new InterfaceDefAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + " " + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "                 " + NL + "import java.util.logging.Logger;" + NL + "import ccm.local.ServiceLocator;" + NL + "" + NL + "import org.omg.CORBA.*;" + NL + "" + NL + "" + NL + "/**" + NL + " * Interface adapter from CORBA to Java." + NL + " */" + NL + "public class ";
  protected final String TEXT_4 = "AdapterFromCorba" + NL + "    extends ";
  protected final String TEXT_5 = "POA" + NL + "{" + NL + "    private Logger logger = ServiceLocator.instance().getLogger();" + NL + "    " + NL + "    /** Java reference to the local interface */" + NL + "    private ";
  protected final String TEXT_6 = " localInterface;" + NL + "\t" + NL + "    public ";
  protected final String TEXT_7 = "AdapterFromCorba(";
  protected final String TEXT_8 = " receptacle)" + NL + "    {" + NL + "        logger.fine(\"receptacle = \" + receptacle + \")\");" + NL + "        this.localInterface = receptacle;" + NL + "    }    \t" + NL;
  protected final String TEXT_9 = "    ";
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = "    ";
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = "    ";
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = "    ";
  protected final String TEXT_16 = NL;
  protected final String TEXT_17 = NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     InterfaceDef iface = (InterfaceDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append( iface.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append( iface.generateJavaRemoteNamespace() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(iface.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(iface.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(iface.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(iface.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(iface.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_8);
     
for(Iterator i=iface.getBaseInterfaces().iterator(); i.hasNext();)
{
	InterfaceDef baseIface = (InterfaceDef)i.next();
	for(Iterator j=baseIface.getAttributes().iterator(); j.hasNext(); )
	{
        AttributeDef attr = (AttributeDef)j.next();

    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(attr.generateAdapterFromCorba());
    
    }
} 
 
for(Iterator i=iface.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(attr.generateAdapterFromCorba());
    
}
               
for(Iterator i=iface.getBaseInterfaces().iterator(); i.hasNext();)
{
	InterfaceDef baseIface = (InterfaceDef)i.next();
	for(Iterator j=baseIface.getOperation().iterator(); j.hasNext(); )
	{
        OperationDef op = (OperationDef)j.next();

    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(op.generateAdapterFromCorba());
    
    }
}

for(Iterator i=iface.getOperation().iterator(); i.hasNext();)
{
    OperationDef op = (OperationDef)i.next();

    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(op.generateAdapterFromCorba());
    
}

    stringBuffer.append(TEXT_17);
    return stringBuffer.toString();
  }
}
