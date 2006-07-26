/* CCM Tools : User Interface Library
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2002, 2003 Salomon Automation
 *
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

package ccmtools.UI;


import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


// This class (and the Driver interface) handle both user messages and logging.
// TODO: Separate UI output from logging. In the end, this class should only handle
//       user outputs.
//       All logging should be done via Java's Logging API

public class ConsoleDriver
    implements Driver
{
    private long mask;
    private String indent;
    private PrintStream out;
    private PrintStream err;

    /**
     * A Driver interface implementation that writes node handler events to the
     * controlling console.
     *
     * @param m An output mask that filters the type of output messages logged
     *          to standard out.
     */
    public ConsoleDriver(long m)
        throws FileNotFoundException
    {
        mask = m;
        out = System.out;
        err = System.err;
        indent = "";
    }

    /**
     * A Driver interface implementation that writes node handler events to the
     * controlling console, or to a file if specified.
     *
     * @param m An output mask that filters the type of output messages logged
     *          to the output file.
     * @param o An output file handle to write normal output on.
     */
    public ConsoleDriver(long m, String o)
        throws FileNotFoundException
    {
        mask = m;
        out = new PrintStream(new FileOutputStream(o));
        err = System.err;
        indent = "";
    }

    /**
     * A Driver interface implementation that writes node handler events to the
     * controlling console, or to a file if specified. The output mask defaults
     * to writing file output messages only.
     *
     * @param o An output file handle to write normal output on.
     */
    public ConsoleDriver(String o)
        throws FileNotFoundException
    {
        mask = M_OUTPUT_FILE;
        out = new PrintStream(new FileOutputStream(o));
        err = System.err;
        indent = "";
    }

    
    public void nodeStart(Object node, String scope_id)
    {
        indent += "  ";
        logSimpleLine(scope_id, M_NODE_TRACE, '+');
    }

    public void nodeEnd(Object node, String scope_id)
    {
        logSimpleLine(scope_id, M_NODE_TRACE, '-');
        indent = indent.substring(2);
    }

    public void nodeData(Object node, String name, Object value)
    {
        indent += "  ";
        logSimpleLine(name+" -> "+formatData(value), M_NODE_DATA, '@');
        indent = indent.substring(2);
    }

    public void templateContents(String template)
    {
        logSimpleLine(template, M_TEMPLATE);
    }

    public void outputVariables(Map variables)
    {
        logSimpleLine("output variables :"+formatData(variables),
                      M_OUTPUT_VARIABLES);
    }

    public void currentVariables(Set variables)
    {
        logSimpleLine("current variables :"+formatData(variables),
                      M_CURRENT_VARIABLES);
    }

    
    // Methods for user output
    
    public void println(String str)
    {
        out.println(str);
    }
    
    /**
     * Print out a string message to the console.
     * Note that this method is used for regular user output and not
     * for logging.
     * 
     * @param msg String that shoul be printed out.
     */
    public void printMessage(String msg)
    {
        out.println("> " + msg);
    }

    public void printError(String msg)
    {
        err.println("!!!! CCM Tools Error: " + msg);
    }
    
    
    // Logging helper methods --------------------------------------------------

    
    private void logSimpleLine(Object data, long m)
    {
        if ((mask & m) != 0) 
            out.println(formatPrefix(' ') + data);
    }

    private void logSimpleLine(Object data, long m, char pre)
    {
        if ((mask & m) != 0) 
            out.println(formatPrefix(pre) + data);
    }

    private String formatPrefix(char pre)
    {
        if ((mask & M_PREFIX) != 0) 
            return formatIndent(pre);
        else 
            return "";
    }

    private String formatIndent(char pre)
    {
        if ((mask & M_PREFIX_INDENT) != 0) 
            return indent + pre + " ";
        else 
            return pre + " ";
    }

    private String formatData(Object data)
    {
        if (data == null) return "(null)";

        String pre = formatPrefix(' ');
        StringBuffer ret = new StringBuffer();

        if (data instanceof Map) { // hash table format
            Map map = (Map) data;

            ret.append("\n");

            SortedSet items = new TreeSet(map.keySet());
            for (Iterator i = items.iterator(); i.hasNext(); ) {
                Object key = i.next();
                Object val = map.get(key);
                ret.append(pre+"{ "+key+" : "+abbreviateString(val)+" }\n");
            }
        } else if (data instanceof Collection) { // list format
            ret.append("\n");

            for (Iterator i = ((Collection) data).iterator(); i.hasNext(); )
                ret.append(pre+"[ "+i.next()+" ]\n");
        } else { // other formats
            ret.append(abbreviateString(data));
        }

        return ret.toString();
    }

    private String abbreviateString(Object str)
    {
        if (str == null) return "(null)";

        String tmp = str.toString();

        int loc = tmp.indexOf('[');
        if (loc >= 0) tmp = tmp.substring(0, loc);
        if (tmp.indexOf('\n') < 0) return tmp;

        String[] parts = ((String) tmp).split("\n");
        StringBuffer acc = new StringBuffer();

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].matches("[A-Za-z]")) {
                acc.append(parts[i].trim() + " | ");
            }
        }

        int l = acc.length();
        acc = acc.delete(Math.max(0, l - 3), Math.max(0, l - 1));
        String ret = acc.toString().replaceAll(" *", " ");

        return ret.substring(0, Math.max(0, ret.length() - 100));
    }
}
