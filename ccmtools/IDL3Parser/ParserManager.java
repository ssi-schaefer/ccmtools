/* CCM Tools : IDL3 Parser
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

package ccmtools.IDL3Parser;

import ccmtools.Metamodel.BaseIDL.MContainer;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ParserManager {
    private long debug;

    private IDL3SymbolTable symbolTable;
    private IDL3Parser parser;
    private IDL3Lexer lexer;
    private String originalFilename;
    private List includedFiles;
    private List includePath;

    /**
     * Create a new ParserManager class instance with no debug output enabled.
     */
    public ParserManager()
    {
        symbolTable = new IDL3SymbolTable();
        includedFiles = new ArrayList();
        includePath = new ArrayList();
        this.debug = 0;
    }

    /**
     * Create a new ParserManager class instance using the given debug flags to
     * control debug output. The debug flags are passed directly on to the
     * underlying lexer and parser class instances.
     *
     * @param debug the flags to enable for debug output. Pass -1 to enable all
     *              output.
     */
    public ParserManager(long debug, List include_path)
    {
        symbolTable = new IDL3SymbolTable();
        includedFiles = new ArrayList();
        includePath = new ArrayList(include_path);
        this.debug = debug;
    }

    /**
     * Add a file to the list of files that were included during a parse. This
     * function is normally called by an IDL3Parser instance when it encounters
     * an #include directive.
     *
     * @param includedFileName the name of the included file.
     */
    public void addIncludedFile(String includedFileName)
    {
        // locate include files on the include path.

        for (Iterator p = includePath.iterator(); p.hasNext(); ) {
            File test = new File((File) p.next(), includedFileName);
            if (test.isFile()) {
                includedFiles.add(test.toString());
                return;
            }
        }

        throw new RuntimeException("Source IDL file "+includedFileName+" not found.");
    }

    /**
     * Get a list of all files included from other files during a parse.
     *
     * @return the included file list. Could be empty if the original file
     *         contained no used #include directives.
     */
    public List getIncludedFiles()
    {
	return includedFiles;
    }

    /**
     * Find out if the given file is a file included from some other file during
     * the parse process (if not, it is likely either the original file, or
     * nothing is known about the file).
     *
     * @param includedFileName the name of a file to check on.
     * @return true only when the given file was included from another file
     *         during the parse.
     */
    public boolean isIncluded(String includedFileName)
    {
        File tester = new File(includedFileName);
        for (Iterator i = includedFiles.iterator(); i.hasNext(); ) {
            File next = new File((String) i.next());
            if (tester.equals(next)) return true;
        }
	return false;
    }

    /**
     * Create a new parser (and an associated lexer) object to parse the given
     * file. Set up the lexer and parser to communicate, and clear the contents
     * of the current symbol table.
     *
     * @param filename an input IDL file to parse.
     */
    public void createParser(String filename)
    {
	try
        {
            // open a simple stream to the input
            DataInputStream input = new DataInputStream(new FileInputStream(filename));
            lexer = new IDL3Lexer(input);
            lexer.setDebug(debug);
            lexer.setFilename(filename);
            lexer.setManager(this);

            parser = new IDL3Parser(lexer);
            parser.setDebug(debug);
            parser.setFilename(filename);
            parser.setManager(this);

            originalFilename = filename;

            // clear the symbol table for the new parse, and reset the list of
            // included files.
            symbolTable.clear();
            includedFiles = new ArrayList();
        }
	catch(Exception e)
        {
            System.err.println("Parser manager exception: " + e);
        }
    }

    /**
     * Get the current symbol table being maintained by the parser manager.
     *
     * @return the current symbol table, normally for debug output. Be careful;
     *         externally manipulating the symbol table could have severe side
     *         effects.
     */
    public IDL3SymbolTable getSymbolTable()
    {
	return symbolTable;
    }

    /**
     * Find out which file the parser manager started with.
     *
     * @return the name of the original file given to parse.
     */
    public String getOriginalFilename()
    {
	return originalFilename;
    }

    /**
     * Parse a file specified earlier with a call to createParser. This function
     * actually sets the parser for a file in motion, whereas the createParser
     * function merely sets up a parser and lexer.
     *
     * @return a <code>ccm.mof.BaseIDL.MContainer</code> object that contains
     *         a metamodel corresponding to the declarations in the source IDL
     *         file.
     */
    public MContainer parseFile()
        throws RecognitionException, TokenStreamException
    {
	MContainer spec = null;
        spec = parser.specification();
        spec.setIdentifier(originalFilename);
	return spec;
    }
}
