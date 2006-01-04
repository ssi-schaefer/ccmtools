package ccmtools.JavaClientLib.templates;

import ccmtools.JavaClientLib.metamodel.*;

public class ComponentAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized ComponentAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ComponentAdapterToCorbaTemplate result = new ComponentAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * ";
  protected final String TEXT_3 = NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_4 = ";" + NL + "                 " + NL + "public class ";
  protected final String TEXT_5 = "AdapterToCorba " + NL + "    implements ";
  protected final String TEXT_6 = NL + "{" + NL + "    /** CORBA reference to a remote component */" + NL + "    private ";
  protected final String TEXT_7 = " remoteComponent;" + NL + "" + NL + "    public ";
  protected final String TEXT_8 = "AdapterToCorba(";
  protected final String TEXT_9 = " remoteComponent)" + NL + "        throws ccm.local.Components.CCMException" + NL + "    {    " + NL + "        // TODO" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** CCMObject methods */" + NL + "    " + NL + "    public void configuration_complete()" + NL + "    {" + NL + "        if(remoteComponent != null)" + NL + "        {" + NL + "            remoteComponent.configuration_complete();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public void remove()" + NL + "    {" + NL + "        if(remoteComponent != null)" + NL + "        {" + NL + "            remoteComponent.remove();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public ccm.local.Components.HomeExecutorBase get_ccm_home()" + NL + "    {" + NL + "        // TODO" + NL + "        throw new RuntimeException(\"get_ccm_home() is not implemented!\");" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Navigation methods */" + NL + "    " + NL + "    public Object provide_facet(String name)" + NL + "        throws ccm.local.Components.InvalidName" + NL + "    {" + NL + "        // TODO" + NL + "        throw new RuntimeException(\"provide_facet() is not implemented!\");" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Receptacle methods */" + NL + "    " + NL + "    public ccm.local.Components.Cookie connect(String name, Object localObject)" + NL + "        throws ccm.local.Components.InvalidName, ccm.local.Components.InvalidConnection," + NL + "               ccm.local.Components.AlreadyConnected, ccm.local.Components.ExceededConnectionLimit" + NL + "    {" + NL + "         // TODO" + NL + "        throw new RuntimeException(\"provide_facet() is not implemented!\");   " + NL + "    }" + NL + "    " + NL + "    public void disconnect(String name, ccm.local.Components.Cookie ck)" + NL + "        throws ccm.local.Components.InvalidName, ccm.local.Components.InvalidConnection," + NL + "               ccm.local.Components.CookieRequired, ccm.local.Components.NoConnection" + NL + "    {" + NL + "        // TODO" + NL + "        throw new RuntimeException(\"provide_facet() is not implemented!\");   " + NL + "    }" + NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ComponentDef component = (ComponentDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(component.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(component.generateTimestamp());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(component.generateJavaNamespace());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(component.generateJavaName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(component.generateIdlName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(component.generateIdlName());
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}
