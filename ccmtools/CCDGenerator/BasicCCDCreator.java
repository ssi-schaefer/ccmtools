/* CCM Tools : CORBA Component Descriptor (CCD) Generator
 * Edin Arnautovic <edin.arnautovic@salomon.at>
 * copyright (c) 2002, 2003 Salomon Automation
 *
 * $Id$
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package ccmtools.CCDGenerator;

import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.DocType;

import java.util.Collection;
import java.util.List;
import java.util.Iterator;

public class BasicCCDCreator {
    private Document ccdDOM = null;

    public BasicCCDCreator()
    {
	DocType docType = new DocType("corbacomponent","corbacomponent.dtd");
	ccdDOM = new Document();
	ccdDOM.setDocType(docType);
    	Element corbacomponent = new Element("corbacomponent");
	ccdDOM.setRootElement(corbacomponent);
    }

    public BasicCCDCreator(Document defaultDocument)
    {
	ccdDOM = (Document) defaultDocument.clone();
    }

    public Document createCCDDOM(MComponentDef component)
    {
	/*
	  <!ELEMENT componentrepid EMPTY >
	  <!ATTLIST componentrepid
	  repid CDATA #IMPLIED >
	*/
	Element componentrepid = new Element("componentrepid");
	componentrepid.setAttribute("repid", component.getRepositoryId());
	ccdDOM.getRootElement().addContent(componentrepid);

	Collection componentHomes = component.getHomes();
	if(componentHomes != null) {
            /*
              <!ELEMENT homerepid EMPTY >
              <!ATTLIST homerepid
              repid CDATA #IMPLIED >
            */
            for(Iterator i = componentHomes.iterator(); i.hasNext(); ) {
                MHomeDef homeDef = (MHomeDef)i.next();
                Element homerepid = new Element("homerepid");
                homerepid.setAttribute("repid",homeDef.getRepositoryId());
                ccdDOM.getRootElement().addContent(homerepid);
                handleHome(homeDef);
            }
        }

	handleComponent(component);

	return ccdDOM;
    }

    /*
     * this method should create componentfeatures element and add it to the
     * ccd.root
     */
    private void  handleComponent(MComponentDef component)
    {
	/*
	  <!ELEMENT componentfeatures
	  ( inheritscomponent?
	  , supportsinterface*
	  , ports
	  , operationpolicies?
	  , extension* ) >

	  <!ATTLIST componentfeatures
	  name CDATA #REQUIRED
	  repid CDATA #REQUIRED >
	*/
	Element componentfeatures = new Element("componentfeatures");
	componentfeatures.setAttribute("name", component.getIdentifier());
	componentfeatures.setAttribute("repid", component.getRepositoryId());

	/*
	  <!ELEMENT inheritscomponent EMPTY>
	  <!ATTLIST inheritscomponent
	  repid CDATA #REQUIRED>
	*/
	Collection componentBases = component.getBases();
	if(componentBases != null) {
            if (!(componentBases.isEmpty())) {
                Element inheritscomponent = new Element("inheritscomponent");

                //component should have only one base
                Iterator it = componentBases.iterator();
                MComponentDef baseComponent = (MComponentDef) it.next();
                inheritscomponent.setAttribute("repid", baseComponent.getRepositoryId()); 

                //the base component should be also defined in CCD file
                handleComponent(baseComponent);
                componentfeatures.addContent(inheritscomponent);
            }
        }

	/*
	  <!ELEMENT supportsinterface
	  ( operationpolicies?
	  , extension* ) >
	  <!ATTLIST supportsinterface
	  repid CDATA #REQUIRED >
	*/
	Collection supportsinterfaceCollection = component.getSupportss();
	if(supportsinterfaceCollection != null)
	    {
		Element supportsinterface = null;
		MInterfaceDef supportsInterfaceDef = null;
		for(Iterator i = supportsinterfaceCollection.iterator(); i.hasNext();)
		    {
			supportsinterface = new Element("supportsinterface");
			supportsInterfaceDef = (MInterfaceDef)i.next();
			supportsinterface.setAttribute("repid", supportsInterfaceDef.getRepositoryId());
			handleInterface(supportsInterfaceDef);
			componentfeatures.addContent(supportsinterface);
		    }
	    }

	/*
	  <!ELEMENT ports
	  ( uses
	  | provides
	  | emits
	  | publishes
	  | consumes
	  )* >
	*/
	Element ports = new Element("ports");

	/*
	  <!ELEMENT uses ( extension* ) >
	  <!ATTLIST uses
	  usesname CDATA #REQUIRED
	  repid CDATA #REQUIRED >
	*/
	Collection usesCollection = component.getReceptacles();
	if(usesCollection != null) {
            Element uses = null;
            MUsesDef usesDef = null;
            for(Iterator i = usesCollection.iterator(); i.hasNext(); ) {
                usesDef = (MUsesDef)i.next();
                uses = new Element("uses");
                uses.setAttribute("usesname", usesDef.getIdentifier());
                uses.setAttribute("repid", usesDef.getUses().getRepositoryId());
                handleInterface(usesDef.getUses());
                ports.addContent(uses);
            }
        }

	/*
	  <!ELEMENT provides
	  ( operationpolicies?
	  , extension* ) >
	  <!ATTLIST provides
	  providesname CDATA #REQUIRED
	  repid CDATA #REQUIRED
	  facettag CDATA #REQUIRED >
	*/
	Collection providesCollection = component.getFacets();
	if(providesCollection != null) {
            Element provides = null;
            MProvidesDef providesDef = null;
            for(Iterator i = providesCollection.iterator(); i.hasNext(); ) {
                providesDef = (MProvidesDef)i.next();
                provides = new Element("provides");
                provides.setAttribute("providesname", providesDef.getIdentifier());
                provides.setAttribute("repid", providesDef.getProvides().getRepositoryId());
                handleInterface(providesDef.getProvides());
                ports.addContent(provides);
            }
        }

	componentfeatures.addContent(ports);

	ccdDOM.getRootElement().addContent(componentfeatures);
    }

    private void handleInterface(MInterfaceDef interf)
    {
	/*
	  <!ELEMENT interface
	  ( inheritsinterface*
	  , operationpolicies? ) >
	  <!ATTLIST interface
	  name CDATA #REQUIRED
	  repid CDATA #REQUIRED >
	*/
	Element interfaceElement = new Element("interface");
	interfaceElement.setAttribute("name", interf.getIdentifier());
	interfaceElement.setAttribute("repid", interf.getRepositoryId());

	/*
	  <!ELEMENT operationpolicies
	  ( operation+ ) >
	*/
	Collection contentss = interf.getContentss();
	if(contentss != null) {
            Element operationpolicies = new Element("operationpolicies");
            boolean hasOperations = false;
            for(Iterator i = contentss.iterator(); i.hasNext(); ) {
                MContained contained = (MContained)i.next();

                if (contained instanceof MOperationDef) {
                    hasOperations = true;
                    /*
                      !ELEMENT operation
                      ( transaction?
                      , requiredrights? ) >
                      <!ATTLIST operation
                      name CDATA #REQUIRED >
                    */
                    Element operation = new Element("operation");
                    operation.setAttribute("name", contained.getIdentifier());
                    operationpolicies.addContent(operation);
                }
            }
            if(hasOperations)
                interfaceElement.addContent(operationpolicies);
        }

	ccdDOM.getRootElement().addContent(interfaceElement);
    }

    private void handleHome(MHomeDef homeDef)
    {
	/*
	  <!ELEMENT homefeatures
	  ( inheritshome?
	  , operationpolicies?
	  , extension* ) >
	*/
	Element homefeatures = new Element("homefeatures");
	homefeatures.setAttribute("name", homeDef.getIdentifier());
	homefeatures.setAttribute("repid", homeDef.getRepositoryId());
	ccdDOM.getRootElement().addContent(homefeatures);

	/*
	  <!ELEMENT operationpolicies
	  ( operation+ ) >
	*/
	Element operationpolicies = new Element("operationpolicies");
	boolean hasOperations = false;
	Collection contentss = homeDef.getContentss();
	if(contentss != null) {
            for(Iterator i = contentss.iterator(); i.hasNext(); ) {
                MContained contained = (MContained)i.next();

                if (contained instanceof MOperationDef) {
                    hasOperations = true;
                    /*
                      !ELEMENT operation
                      ( transaction?
                      , requiredrights? ) >
                      <!ATTLIST operation
                      name CDATA #REQUIRED >
                    */
                    Element operation = new Element("operation");
                    operation.setAttribute("name", contained.getIdentifier());
                    operationpolicies.addContent(operation);
                }
            }
        }

	Collection finders = homeDef.getFinders();
	if(finders != null) {
            for(Iterator i = finders.iterator(); i.hasNext(); ) {
                MContained contained = (MContained)i.next();
                if (contained instanceof MOperationDef) {
                    hasOperations = true;
                    /*
                      !ELEMENT operation
                      ( transaction?
                      , requiredrights? ) >
                      <!ATTLIST operation
                      name CDATA #REQUIRED >
                    */
                    Element operation = new Element("operation");
                    operation.setAttribute("name", contained.getIdentifier());
                    operationpolicies.addContent(operation);
                }
            }
        }

	Collection factories = homeDef.getFactories();
	if(factories != null) {
            for(Iterator i = factories.iterator(); i.hasNext(); ) {
                MContained contained = (MContained)i.next();
                if (contained instanceof MOperationDef) {
                    hasOperations = true;
                    /*
                      !ELEMENT operation
                      ( transaction?
                      , requiredrights? ) >
                      <!ATTLIST operation
                      name CDATA #REQUIRED >
                    */
                    Element operation = new Element("operation");
                    operation.setAttribute("name", contained.getIdentifier());
                    operationpolicies.addContent(operation);
                }
            }
        }
	if(hasOperations)
	    homefeatures.addContent(operationpolicies);
    }
}

