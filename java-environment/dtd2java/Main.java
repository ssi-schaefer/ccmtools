/* DTD code generator
 *
 * 2003 by Robert Lechner (rlechner@gmx.at)
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

package ccmtools.dtd2java;


/**
 * Reads a DTD-file and calls the code generator.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class Main
{
    /**
     * Reads a DTD-file and calls the code generator.
     *
     * @param argv  argv[0] = name of the DTD-file (see {@link DtdParser#parseFile}) <br>
                    argv[1] = the Java-name of the destination package (see {@link DtdGenerator#run})
     */
    public static void main( String[] argv )
    {
        try
        {
            if( argv.length<2 )
            {
                System.out.println("java ccmtools.dtd2java.Main DTD_filename Java_package");
            }
            else
            {
                DtdFile f = DtdParser.parseFile(argv[0]);
                DtdGenerator g = new DtdGenerator();
                g.run(f, argv[1]);
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
