/*  MOF reader
 *
 *  2004 by Research & Development, Salomon Automation <www.salomon.at>
 *
 *  Robert Lechner  <robert.lechner@salomon.at>
 *
 *
 *  $Id$
 *
 */

package mof_reader;

import mof_xmi_parser.DTD_Root;
import mof_xmi_parser.DTD_Container;
import java.io.File;


/**
 * Reads the XMI file and creates a MOF model.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public class Main
{
    /**
     * Reads the XMI file and creates a MOF model.
     *
     * @param args[0]  XMI-filename
     */
    public static void main( String[] args )
    {
        if( args.length!=1 || args[0]=="--help" )
        {
            System.out.println("mof_reader.Main  XMI-filename");
            return;
        }
        try
        {
            Model model = Main.readXmi(new File(args[0]));
            if( model==null )
            {
                System.out.println("model==null");
            }
            else
            {
                System.out.println("model ok");
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }


    /**
     * Reads the XMI file and returns the MOF model (or null).
     *
     * @param xmiFile  the XMI file
     *
     * @throws Exception  see {@link mof_xmi_parser.DTD_Root#parse} and {@link Model#Model}
     */
    public static Model readXmi( File xmiFile ) throws Exception
    {
        DTD_Container root = DTD_Root.parse(xmiFile, new XmiFactory());
        if( root==null )
        {
            return null;
        }
        return new Model(root);
    }
}
