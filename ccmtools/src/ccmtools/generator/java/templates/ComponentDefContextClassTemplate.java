package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ComponentDefContextClassTemplate
{
  protected static String nl;
  public static synchronized ComponentDefContextClassTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ComponentDefContextClassTemplate result = new ComponentDefContextClassTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "            " + NL + "import Components.ccm.local.HomeExecutorBase;" + NL + "import Components.ccm.local.IllegalState;       " + NL + "import Components.ccm.local.NoConnection;" + NL + "            " + NL + "import java.util.logging.Logger;" + NL + "import ccm.local.ServiceLocator;" + NL + "                     " + NL + "public class ";
  protected final String TEXT_4 = "_ContextImpl " + NL + "    implements ";
  protected final String TEXT_5 = "_Context" + NL + "{" + NL + "    private Logger logger = ServiceLocator.instance().getLogger();" + NL + "" + NL + "    private ";
  protected final String TEXT_6 = " component;" + NL + "    " + NL + "    public ";
  protected final String TEXT_7 = "_ContextImpl(";
  protected final String TEXT_8 = " component)" + NL + "    {" + NL + "        logger.fine(\"component = \" + component);" + NL + "        this.component = component;" + NL + "    }" + NL + "    " + NL + "    /** Receptacle access methods */";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "      " + NL + "    " + NL + "    " + NL + "    /** CCMContext methods */" + NL + "\t    " + NL + "    public HomeExecutorBase get_CCM_home()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        throw new RuntimeException(\"Not implemented!\");" + NL + "    }" + NL + "" + NL + "" + NL + "    /** SessionContext methods */" + NL + "    " + NL + "    public Object get_CCM_object()" + NL + "        throws IllegalState" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        throw new RuntimeException(\"Not implemented!\");" + NL + "    }" + NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ComponentDef component = (ComponentDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(component.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(component.generateJavaNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(component.generateAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_8);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_9);
    stringBuffer.append(uses.generateContextGetConnectionMethodImplementation());
    
}

    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}
