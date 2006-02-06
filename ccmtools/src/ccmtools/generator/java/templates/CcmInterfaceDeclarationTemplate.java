package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class CcmInterfaceDeclarationTemplate
{
  protected static String nl;
  public static synchronized CcmInterfaceDeclarationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    CcmInterfaceDeclarationTemplate result = new CcmInterfaceDeclarationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "                 " + NL + "import ccm.local.Components.*;" + NL + " " + NL + "public interface ";
  protected final String TEXT_4 = " " + NL + "    extends ";
  protected final String TEXT_5 = NL + "{" + NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     InterfaceDef iface = (InterfaceDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append( iface.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append( iface.getJavaNamespace() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(iface.getCcmIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(iface.getAbsoluteJavaName());
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
