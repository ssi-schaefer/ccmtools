/* UML to IDL/OCL converter
 *
 * 2004 by Robert Lechner (rlechner@gmx.at)
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

package uml2idl;

import uml_parser.*;
import uml_parser.uml.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Reads an UML-file and creates the IDL and OCL files.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public class Main
{
    /**
     * Main file header for IDL files.
     */
    static final String IDL_HEADER =
    "/*\n\n"+
    "This file was automatically generated by 'uml2idl'.\n"+
    "'uml2idl' is part of the \"CCM tools\" project (http://ccmtools.sf.net).\n\n"+
    "DO NOT EDIT!\n\n*/\n\n\n";


    /**
     * Main file header for OCL files.
     */
    static final String OCL_HEADER =
    "--\n"+
    "--  This file was automatically generated by 'uml2idl'.\n"+
    "--  'uml2idl' is part of the \"CCM tools\" project (http://ccmtools.sf.net).\n"+
    "--\n"+
    "--  DO NOT EDIT!\n"+
    "--\n\n\n";


    /**
     * indent spaces
     */
    static final String SPACES = "    ";


    /**
     * IDL and OCL path seperator
     */
    static final String PATH_SEPERATOR = "::";


    private static int counter_;

    /**
     * Creates an unique ID. This ID may not be written to a XML file!
     */
    static String makeId()
    {
        String id = ".\"'"+counter_;
        counter_++;
        return id;
    }


    /**
     * Reduces the path name of an IDL element.
     *
     * @param name  the full path name of the element
     * @param container  the full path name of the element's parent
     * @return the reduced path name (only valid inside the container)
     */
    static String reducePathname( String name, String container )
    {
        int index = name.indexOf(PATH_SEPERATOR);
        if( index<0 )
        {
            return name;
        }
        int i2 = container.indexOf(PATH_SEPERATOR);
        if( i2<0 )
        {
            if( name.substring(0,index).equals(container) )
            {
                index += PATH_SEPERATOR.length();
                return name.substring(index);
            }
            return name;
        }
        if( index!=i2 )
        {
            return name;
        }
        if( name.substring(0,index).equals(container.substring(0,index)) )
        {
            index += PATH_SEPERATOR.length();
            return reducePathname( name.substring(index), container.substring(index) );
        }
        return name;
    }


    private static HashMap infos_ = new HashMap();

    /**
     * only for debugging
     */
    static void logChild( String info, Object child )
    {
        String value = child.getClass().getName();
        HashMap values = (HashMap)infos_.get(info);
        if( values==null )
        {
            values = new HashMap();
            values.put(value, new Integer(1));
            infos_.put(info,values);
        }
        else
        {
            Integer counter = (Integer)values.get(value);
            if( counter==null )
            {
                values.put(value, new Integer(1));
            }
            else
            {
                int oldCount = counter.intValue();
                values.put(value, new Integer(oldCount+1));
            }
        }
    }

    private static void printChildren()
    {
        Iterator it1 = infos_.keySet().iterator();
        while( it1.hasNext() )
        {
            System.out.println("---");
            String info = (String)it1.next();
            HashMap values = (HashMap)infos_.get(info);
            Iterator it2 = values.keySet().iterator();
            while( it2.hasNext() )
            {
                String value = (String)it2.next();
                Integer counter = (Integer)values.get(value);
                int count = counter.intValue();
                System.out.println(info+" : "+count+"* "+value);
            }
        }
    }
    
    
    static String makeModelElementComments( DTD_Container element, String prefix )
    {
        StringBuffer code = new StringBuffer();
        Vector vc1 = element.findChildren(MModelElement_comment.xmlName__);
        for( int i1=0; i1<vc1.size(); i1++ )
        {
            MModelElement_comment mec = (MModelElement_comment)vc1.get(i1);
            Vector vc2 = mec.findChildren(MComment.xmlName__);
            for( int i2=0; i2<vc2.size(); i2++ )
            {
                MComment comment = (MComment)vc2.get(i2);
                Vector vc3 = comment.findChildren(UmlModelElementName.xmlName__);
                for( int i3=0; i3<vc3.size(); i3++ )
                {
                    UmlModelElementName men = (UmlModelElementName)vc3.get(i3);
                    code.append(prefix);
                    code.append("/* ");
                    code.append(men.getName());
                    code.append(" */\n");
                }
            }
        }
        return code.toString();
    }
    

    /**
     * Returns the name of a model element (or null).
     */
    static String makeModelElementName( DTD_Container element )
    {
        Vector names = element.findChildren(UmlModelElementName.xmlName__);
        for( int index=0; index<names.size(); index++ )
        {
            String name = ((UmlModelElementName)names.get(index)).getName();
            if( name!=null )
            {
                return name;
            }
        }
        return null;
    }


    /**
     * Returns the 'tagged values' of a model element.
     */
    HashMap makeModelElementTaggedValues( DTD_Container element )
    {
        HashMap result = new HashMap();
        Vector children = element.findChildren(UmlModelElementTaggedValue.xmlName__);
        for( int i=0; i<children.size(); i++ )
        {
            ((UmlModelElementTaggedValue)children.get(i)).addValues(result);
        }
        return result;
    }


    /**
     * Checks if the model element has a special stereotype.
     */
    boolean isModelElementStereotype( DTD_Container element, String name )
    {
        //System.out.println("searching for stereotype "+name);
        Vector children = element.findChildren(UmlModelElementStereotype.xmlName__);
        for( int i=0; i<children.size(); i++ )
        {
            if( ((UmlModelElementStereotype)children.get(i)).isStereotype(name, this) )
            {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if the stereotype is of a special kind.
     *
     * @param id    XML-ID of the stereotype
     * @param name  special kind
     */
    boolean isStereotype( String id, String name )
    {
        //System.out.println("stereotype: id=="+id+"  check for "+name);
        Object o = workers_.get(id);
        if( o!=null )
        {
            if( o instanceof UmlModelElementStereotype )
            {
                return ((UmlModelElementStereotype)o).isStereotype(name, this);
            }
            if( o instanceof UmlStereotype )
            {
                return ((UmlStereotype)o).isStereotype(name, this);
            }
        }
        return false;
    }


    /**
     * Reads an UML-file and creates an IDL-file.
     *
     * @param argv :<br>
                argv[0] = name of the UML-file <br>
                argv[1] = prefix of the IDL- and OCL-files <br>
                argv[2]=='DUMP' .. optional; show containment
     */
    public static void main( String[] argv )
    {
        try
        {
            if( argv.length<2 )
            {
                System.out.println("java uml2idl.Main UML_filename IDL_OCL_prefix [DUMP]");
            }
            else
            {
                System.out.println("reading  "+argv[0]);
                DTD_Container root = DTD_Root.parse(new File(argv[0]), new Factory());
                if( root==null )
                {
                    System.out.println("DTD_Root.parse == null");
                }
                else if( !(root instanceof MXMI) )
                {
                    System.out.println("wrong root class: "+root.getClass().getName());
                }
                else
                {
                    /*FileWriter debugWriter = new FileWriter(argv[0]+".DEBUG");
                    debugWriter.write(root.xmlCode());
                    debugWriter.close();*/
                    //
                    String idlFileName = argv[1]+".idl";
                    String oclFileName = argv[1]+".ocl";
                    System.out.println("writing "+idlFileName+" and "+oclFileName);
                    (new Main()).run( (MXMI)root, idlFileName, oclFileName );
                    if( argv.length>=3 && argv[2].equals("DUMP") )
                    {
                        printChildren();
                    }
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
            System.exit(1);
        }
    }


    // the IDL file
    private FileWriter idlWriter_;

    // the OCL file
    private FileWriter oclWriter_;

    // all children of MXMI_content which implements Worker
    private Vector root_elements_ = new Vector();

    /**
     * All implementations of Worker; the key is the XML-ID.
     */
    HashMap workers_ = new HashMap();


    /**
     * Creates the IDL and OCL files.
     */
    public void run( MXMI root, String idlFileName, String oclFileName ) throws IOException
    {
        idlWriter_ = new FileWriter(idlFileName);
        idlWriter_.write(IDL_HEADER);
        if( root.timestamp_!=null )
        {
            idlWriter_.write("/*  timestamp: ");
            idlWriter_.write(root.timestamp_);
            idlWriter_.write("\n*/\n\n\n");
        }
        //
        /*  process header and content => fill workers_ and root_elements_
        */
        int index, size=root.size();
        for( index=0; index<size; index++ )
        {
            Object obj = root.get(index);
            if( obj instanceof MXMI_header )
            {
                processHeader( (MXMI_header)obj );
            }
            else if( obj instanceof MXMI_content )
            {
                processContent( (MXMI_content)obj );
            }
        }
        //
        /*  process all associations => create missing attributes
        */
        Iterator it1 = workers_.keySet().iterator();
        while( it1.hasNext() )
        {
            String key = (String)it1.next();
            Object worker = workers_.get(key);
            if( worker instanceof UmlAssociation )
            {
                ((UmlAssociation)worker).createAttributes(this);
            }
        }
        //
        /*  connect parents and children
        */
        size = root_elements_.size();
        for( index=0; index<size; index++ )
        {
            ((Worker)root_elements_.get(index)).makeConnections(this, null);
        }
        //
        /*  calculate the order and sort root_elements_
        */
        int number = 1;
        for( index=0; index<size; index++ )
        {
            number = ((Worker)root_elements_.get(index)).createDependencyOrder(number, this);
        }
        sort( root_elements_ );
        //
        /*  write the IDL code
        */
        for( index=0; index<size; index++ )
        {
            idlWriter_.write( ((Worker)root_elements_.get(index)).getIdlCode(this, "") );
        }
        idlWriter_.close();
        //
        /*  write the OCL code
        */
        oclWriter_ = new FileWriter(oclFileName);
        oclWriter_.write(OCL_HEADER);
        for( index=0; index<size; index++ )
        {
            oclWriter_.write( ((Worker)root_elements_.get(index)).getOclCode(this) );
        }
        oclWriter_.close();
    }


    /**
     * Sorts an array of implementations of {@link Worker} by calling {@link Worker#getDependencyNumber()}.
     */
    static void sort( Vector workers )
    {
        int size = workers.size();
        if( size<2 )
        {
            return;
        }
        boolean change;
        do
        {
            change = false;
            Object o1=workers.get(0);
            int i2=1, n1=((Worker)o1).getDependencyNumber();
            do
            {
                Object o2 = workers.get(i2);
                int n2 = ((Worker)o2).getDependencyNumber();
                if( n1>n2 )
                {
                    workers.set(i2-1, o2);
                    workers.set(i2, o1);
                    change = true;
                }
                else
                {
                    o1 = o2;
                    n1 = n2;
                }
                i2++;
            }
            while( i2<size );
        }
        while(change);
    }


    private void processHeader( MXMI_header header ) throws IOException
    {
        int size = header.size();
        for( int index=0; index<size; index++ )
        {
            Object obj = header.get(index);
            if( obj instanceof MXMI_documentation )
            {
                idlWriter_.write("/*\n");
                printNode(obj, "");
                idlWriter_.write("*/\n\n\n");
            }
        }
    }


    private void printNode( Object obj, String prefix ) throws IOException
    {
        if( obj instanceof DTD_Container )
        {
            DTD_Container c = (DTD_Container)obj;
            idlWriter_.write(prefix);
            idlWriter_.write(c.xmlName());
            idlWriter_.write("\n");
            prefix = prefix+"  ";
            int size = c.size();
            for( int index=0; index<size; index++ )
            {
                printNode( c.get(index), prefix );
            }
        }
        else
        {
            idlWriter_.write(prefix);
            idlWriter_.write(obj.toString());
            idlWriter_.write("\n");
        }
    }


    private void processContent( MXMI_content content ) throws IOException
    {
        int size = content.size();
        for( int index=0; index<size; index++ )
        {
            Object obj = content.get(index);
            if( obj instanceof Worker )
            {
                root_elements_.add(obj);
                ((Worker)obj).collectWorkers(workers_);
            }
            else if( obj instanceof DTD_Container )
            {
                idlWriter_.write("/* XML-content: "+((DTD_Container)obj).xmlName()+"\n*/\n\n\n");
            }
            else
            {
                idlWriter_.write("/* Java-content: "+obj.getClass().getName()+"\n*/\n\n\n");
            }
        }
    }
}
