/* CCM Tools : Testsuite
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

import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.IDL3Parser.ParserManager;
import ccmtools.CCDGenerator.BasicCCDCreator;

import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.Document;
import org.jdom.JDOMException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

public class Main
{
    public static void main(String args[])
    {
        Document ccdDomDocument = null;
        MContainer container = null;
        ParserManager manager = new ParserManager(-1);

        try {
            container = manager.parseFile(args[0]);
        } catch (Exception e) {
            System.err.println("Error parsing file "+args[0]);
            System.exit(1);
        }

        Collection contents = container.getContentss();
        Document defaultDocument = null;
        for(Iterator i = contents.iterator(); i.hasNext(); ) {
            MContained contained = (MContained)i.next();
            if (contained instanceof MComponentDef) {
                SAXBuilder builder = new SAXBuilder();

                // command line should offer URIs or file names
                try {
                    defaultDocument = builder.build(args[1]);

                    // If there are no well-formedness errors, then no exception
                    // is thrown
                    System.out.println(args[1] + " is well-formed.");
                } catch (JDOMException e) {
                    // indicates a well-formedness error
                    System.out.println(args[1] + " is not well-formed.");
                    System.out.println(e.getMessage());
                }

                BasicCCDCreator ccdCreator = new BasicCCDCreator(defaultDocument);

                ccdDomDocument = ccdCreator.createCCDDOM((MComponentDef)contained);

                // achtung achtung!!, this is only for testing. it is assumed
                // that only one component exists in each idl file.
                System.out.println("Model: " + container.toString()+ "\n");
                XMLOutputter outputter = new XMLOutputter();

                try {
                    File outputFile = new File(contained.getIdentifier()+".ccd");
                    FileWriter out = new FileWriter(outputFile);

                    outputter.setNewlines(true);
                    outputter.setIndent(true);
                    outputter.output(ccdDomDocument, out);
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
    }
}

