/*
 * Created on Feb 5, 2007
 * 
 * R&D Salomon Automation (http://www.salomon.at)
 * 
 * Robert Lechner (robert.lechner@salomon.at)
 * 
 * $Id$
 */
package ccmtools.parser.assembly.metamodel;

import java.io.PrintStream;
import java.util.Map;

/**
 * One line of an assembly description.
 */
public abstract class AssemblyElement
{
    public abstract void prettyPrint( PrintStream out, String offset );

    protected Assembly parent_;

    /**
     * call this method after model creation
     * 
     * @param parent the assembly
     * @param components map with all known inner components
     */
    abstract void postProcessing( Assembly parent, Map<String, Component> components );
}
