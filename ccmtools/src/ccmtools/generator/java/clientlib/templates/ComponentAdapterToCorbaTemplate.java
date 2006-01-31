package ccmtools.generator.java.clientlib.templates;

import java.util.Iterator;
import ccmtools.generator.java.clientlib.metamodel.*;

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
  protected final String TEXT_4 = ";" + NL + "                 " + NL + "import org.omg.CORBA.ORB;" + NL + "import org.omg.CORBA.Policy;" + NL + "import org.omg.PortableServer.ImplicitActivationPolicyValue;" + NL + "import org.omg.PortableServer.POA;" + NL + "import org.omg.PortableServer.POAHelper;" + NL + "import org.omg.PortableServer.POAManagerPackage.AdapterInactive;" + NL + "import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;" + NL + "import org.omg.PortableServer.POAPackage.InvalidPolicy;" + NL + "" + NL + "" + NL + "public class ";
  protected final String TEXT_5 = "AdapterToCorba " + NL + "    implements ";
  protected final String TEXT_6 = NL + "{" + NL + "    public static final String COMPONENT_REPOSITORY_ID = " + NL + "        \"";
  protected final String TEXT_7 = "\";" + NL + "" + NL + "    /** CORBA reference to a remote component */" + NL + "    private ";
  protected final String TEXT_8 = " remoteInterface;" + NL + "" + NL + "    /** Java references to local facet adapters */";
  protected final String TEXT_9 = "    ";
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = NL + NL + "    /** Java references to local receptacle adapters */";
  protected final String TEXT_12 = "    ";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL + NL + "    /** CORBA references */" + NL + "    private ORB orb;" + NL + "    private POA componentPoa;" + NL + "    " + NL + "    " + NL + "    public ";
  protected final String TEXT_15 = "AdapterToCorba(";
  protected final String TEXT_16 = " remoteComponent)" + NL + "        throws ccm.local.Components.CCMException" + NL + "    {    " + NL + "        this.remoteInterface = remoteComponent;" + NL + "        orb = ccm.local.ServiceLocator.instance().getCorbaOrb();" + NL + "        try" + NL + "        {" + NL + "            // Create a POA instance to handle CORBA requests for local receptacles" + NL + "            POA rootPoa = POAHelper.narrow(orb.resolve_initial_references(\"RootPOA\"));" + NL + "            rootPoa.the_POAManager().activate();\t\t\t\t\t\t\t\t\t" + NL + "            Policy[] policies = new Policy[1];" + NL + "            policies[0] = rootPoa.create_implicit_activation_policy(" + NL + "                              ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION);" + NL + "            componentPoa = rootPoa.create_POA(\"TestAdapterPOA\", rootPoa.the_POAManager(),policies );" + NL + "            componentPoa.the_POAManager().activate();" + NL + "        }" + NL + "        catch (org.omg.CORBA.ORBPackage.InvalidName e)" + NL + "        {" + NL + "            throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "        catch (AdapterInactive e)" + NL + "        {" + NL + "            throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "        catch (AdapterAlreadyExists e)" + NL + "        {" + NL + "            throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "        catch (InvalidPolicy e)" + NL + "        {" + NL + "            throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Equivalent interface methods */" + NL;
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = "    " + NL + "    ";
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = NL;
  protected final String TEXT_21 = NL;
  protected final String TEXT_22 = NL + NL + "    " + NL + "    /** CCMObject interface methods */" + NL + "    " + NL + "    public void configuration_complete()" + NL + "    {" + NL + "        if(remoteInterface != null)" + NL + "        {" + NL + "            remoteInterface.configuration_complete();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public void remove()" + NL + "    {" + NL + "        if(remoteInterface != null)" + NL + "        {" + NL + "            remoteInterface.remove();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public ccm.local.Components.HomeExecutorBase get_ccm_home()" + NL + "    {" + NL + "        // TODO" + NL + "        throw new RuntimeException(\"get_ccm_home() is not implemented!\");" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Navigation interface methods */" + NL + "    " + NL + "    public Object provide_facet(String name)" + NL + "        throws ccm.local.Components.InvalidName" + NL + "    {" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new ccm.local.Components.InvalidName();" + NL + "        }";
  protected final String TEXT_23 = "        ";
  protected final String TEXT_24 = NL;
  protected final String TEXT_25 = NL + "        else" + NL + "        {" + NL + "        \tthrow new ccm.local.Components.InvalidName();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Receptacle methods */" + NL + "    " + NL + "    public ccm.local.Components.Cookie connect(String name, Object localObject)" + NL + "        throws ccm.local.Components.InvalidName, ccm.local.Components.InvalidConnection," + NL + "               ccm.local.Components.AlreadyConnected, ccm.local.Components.ExceededConnectionLimit" + NL + "    {" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new ccm.local.Components.InvalidName();" + NL + "        }";
  protected final String TEXT_26 = "     ";
  protected final String TEXT_27 = NL;
  protected final String TEXT_28 = NL + "        else" + NL + "        {" + NL + "            throw new ccm.local.Components.InvalidName();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public void disconnect(String name, ccm.local.Components.Cookie ck)" + NL + "        throws ccm.local.Components.InvalidName, ccm.local.Components.InvalidConnection," + NL + "               ccm.local.Components.CookieRequired, ccm.local.Components.NoConnection" + NL + "    {" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new ccm.local.Components.InvalidName();" + NL + "        }";
  protected final String TEXT_29 = "     ";
  protected final String TEXT_30 = NL;
  protected final String TEXT_31 = NL + "        else" + NL + "        {" + NL + "            throw new ccm.local.Components.InvalidName();" + NL + "        }" + NL + "    }" + NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ComponentDef component = (ComponentDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(component.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(component.generateTimestamp());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(component.getJavaNamespace());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(component.getAbsoluteJavaName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(component.getRepositoryId());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(component.getAbsoluteIdlName());
    stringBuffer.append(TEXT_8);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();) 
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(provides.generateLocalFacetAdapterDeclaration());
    
}

    stringBuffer.append(TEXT_11);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(uses.generateLocalReceptacleAdapterDeclaration());
    
}

    stringBuffer.append(TEXT_14);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(component.getAbsoluteIdlName());
    stringBuffer.append(TEXT_16);
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_17);
    stringBuffer.append(attr.generateAttributeAdapterToCorba());
    
}

    stringBuffer.append(TEXT_18);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_19);
    stringBuffer.append(provides.generateProvidesEquivalentMethodImplementation());
    
}

    stringBuffer.append(TEXT_20);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(uses.generateUsesEquivalentMethodImplementation());
    
}

    stringBuffer.append(TEXT_22);
    		
for(Iterator i = component.getFacet().iterator(); i.hasNext();) 
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_23);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(provides.generateProvidesNavigationMethodImplementation());
    
}

    stringBuffer.append(TEXT_25);
    		
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_26);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(uses.generateUsesReceptacleConnectMethodImplementation());
    
}

    stringBuffer.append(TEXT_28);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(uses.generateUsesReceptacleDisconnectMethodImplementation());
    
}

    stringBuffer.append(TEXT_31);
    return stringBuffer.toString();
  }
}
