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

import ccmtools.IDL3Parser.IDL3SymbolTable;
import ccmtools.IDL3Parser.ParserManager;
import ccmtools.Metamodel.BaseIDL.MContainer;

public class Main {
    public static IDL3SymbolTable symbolTable = new IDL3SymbolTable();

    public static void main(String[] args)
        throws Exception
    {
        ParserManager manager = new ParserManager();
        manager.createParser(args[0]);

        IDL3SymbolTable symbolTable = manager.getSymbolTable();

        try {
            MContainer container = manager.parseFile();
            System.out.println("Symbol table:\n" + symbolTable.toString());
            System.out.println("Container:\n"+ container.toString());
            System.out.println("Included Files:\n"+ manager.getIncludedFiles());
        } catch (Exception e) {
            System.err.println("Error parsing file "+args[0]);
            System.err.println(e);
            throw e;
        }
    }
}
