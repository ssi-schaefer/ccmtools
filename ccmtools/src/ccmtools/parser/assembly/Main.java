/*
 * Created on Feb 5, 2007
 * 
 * R&D Salomon Automation (http://www.salomon.at)
 * 
 * Robert Lechner (robert.lechner@salomon.at)
 * 
 * $Id$
 */
package ccmtools.parser.assembly;

import java.io.FileReader;
import java.util.List;
import ccmtools.CcmtoolsException;
import ccmtools.parser.assembly.metamodel.Model;

public final class Main
{
    public static void main( String[] args )
    {
        if (args.length != 1)
        {
            Main m = new Main();
            System.err.println(m.getClass().getName() + " assembly-description-file");
            System.exit(1);
        }
        else
            try
            {
                Model m = Main.parse(args[0]);
                m.prettyPrint(System.out);
            }
            catch (Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
    }

    public static Model parse( String assembly_description_file ) throws Exception
    {
        FileReader reader = new FileReader(assembly_description_file);
        AssemblyLexer lexer = new AssemblyLexer(reader);
        lexer.current_input_filename = assembly_description_file;
        AssemblyParser parser = new AssemblyParser(lexer);
        parser.current_input_filename = assembly_description_file;
        java_cup.runtime.Symbol root = parser.parse();
        if (root == null || root.value == null)
            throw new NullPointerException("parsing failed");
        if (!(root.value instanceof Model))
            throw new IllegalStateException("invalid root element: "
                    + root.value.getClass().getName());
        Model result = (Model) root.value;
        result.postProcessing();
        return result;
    }

    public static Model parse( List<String> files ) throws Exception
    {
        Model result = new Model();
        for (String f : files)
        {
            try
            {
                Model m = parse(f);
                result.merge(m);
            }
            catch (Exception e)
            {
                throw new CcmtoolsException("problem with: " + f, e);
            }
        }
        return result;
    }
}
