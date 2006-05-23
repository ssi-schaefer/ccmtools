/* CCM Tools : IDL3 Parser
 * Edin Arnautovic <edin.arnautovic@salomon.at>
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

package ccmtools.parser.idl3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.logging.Logger;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import ccmtools.Metamodel.BaseIDL.MContainer;

public class ParserManager 
{
	/** Java standard logger object */
	protected Logger logger;

	/** Helper class that encapsulates all utility and validation methods for the parser */
	ParserHelper helper;
	
	
    /**
     * Create a new ParserManager class instance using the given debug flags to
     * control debug output. The debug flags are passed directly on to the
     * underlying lexer and parser class instances.
     *
     * @param debug the flags to enable for debug output. Pass -1 to enable all
     *              output.
     */
    public ParserManager(String originalFile)
    {                
        logger = Logger.getLogger("ccm.parser.idl3");
        logger.fine("");       
        helper = new ParserHelper();
        helper.setOriginalFile(originalFile);
        // Note: all other files are included files and causes no code generation.
    }

    
    /**
     * Parse an IDL3 file.
     *
     * @param filename the name of a file to open and parse.
     * @return an MContainer object that contains a metamodel corresponding to
     *         the declarations in the source IDL3 file.
     */
    public MContainer parseFile(String filename)
        throws RecognitionException, TokenStreamException
    {
    		logger.fine("");
    		logger.finer("filename = " + filename);	
		MContainer spec = null;
		Idl3Parser parser = null;
		Idl3Lexer lexer = null;
		DataInputStream stream = null;
		
		try
		{
			stream = new DataInputStream(new FileInputStream(filename));

			helper.setSourceFile(filename);

			lexer = new Idl3Lexer(stream);
			lexer.setHelper(helper);
			
			parser = new Idl3Parser(lexer);
			parser.setHelper(helper);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Error creating parser: " + e.getMessage());
		}

		helper.getSymbolTable().pushFile();
		
		try
		{
			spec = parser.specification();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.fine("Exception: " + e.getMessage());
			helper.getErrorList().add(e.getMessage());
		}

		helper.getSymbolTable().popFile();

		if (helper.getErrorList().size() > 0)
		{
			StringBuffer msg = new StringBuffer("Errors during parsing:");
			for (Iterator i = helper.getErrorList().iterator(); i.hasNext();)
			{
				msg.append("\n").append(i.next());
			}
			throw new RuntimeException(msg.toString());
		}

		return spec;
	}
}
