/* CCM Tools : Utilities
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * copyright (c) 2002, 2003, 2004 Salomon Automation
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

package ccmtools.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;

import java.util.Set;
import java.util.HashSet;


/***
 * This class collects some helper methods to handle source code as strings
 * and files.
 *
 *
 ***/
public class Code
{
    /**
     * This method removes empty lines (if more than one) and similar #include
     * statements from the generated code.
     *
     * @param code A string containing generated code that should be prettified.
     * @return A string containing a prittified version of a given source code.
     **/
    public static String prettify(String code)
    {
	StringBuffer pretty_code = new StringBuffer();
	Set include_set = new HashSet();
	int from_index = 0;
	int newline_index = 0;
	boolean isEmptyLineSuccessor = false;
	do {
	    newline_index = code.indexOf('\n',from_index);
	    String code_line = code.substring(from_index, newline_index);
	    from_index = newline_index + 1;
	    if(code_line.length() != 0) {
		isEmptyLineSuccessor = false;

		if(code_line.startsWith("#include")) {
		    if(include_set.contains(code_line)) {
			// Ignore similar #include statements 
		    }
		    else {
			include_set.add(code_line);
			pretty_code.append(code_line);
			pretty_code.append('\n');
		    }
		}
		else {
		    pretty_code.append(code_line);
		    pretty_code.append('\n');
		}
	    }
	    else {
		if(isEmptyLineSuccessor) {
		    // Ignore second empty line
		}
		else {
		    isEmptyLineSuccessor = true;
		    pretty_code.append('\n');
		}
	    }
	}while(from_index < code.length());
	return pretty_code.toString();
    }


    /**
     * This method reads a file, specified by a File object, and compares
     * the file's content with a given code string.
     *
     * @param code A string containing source code.
     * @param file A File object that points to a file which should be compare.
     * @return true if the file's content is equal with the given code string
     *         false in all other cases
     **/
    public static boolean compareWithFile(String code, File file)
    {
	boolean result; 
	try {
	    if (file.isFile()) {
		StringBuffer buffer = new StringBuffer();
		FileInputStream stream = new FileInputStream(file);
		InputStreamReader input = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(input);
		String line = null;
		while ((line = reader.readLine()) != null) {
		    buffer.append(line + "\n");
		}
		//System.out.println(">>>>" + code + "<<<<");
		//System.out.println(">>>>" + buffer + "<<<<");
		return code.equals(buffer.toString());
	    }
	}
	catch(IOException e) {
	    // System.err.println("ERROR: Can't read " + file);
	    // TODO: write a debug message to a log file
	}
	return false;
    } 
}
