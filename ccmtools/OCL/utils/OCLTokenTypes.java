// $ANTLR 2.7.2: "OCL.g" -> "OclLexer.java"$

/*
 * Copyright (c) 2001 Alexander V. Konstantinou (akonstan@acm.org)
 *
 * Permission to use, copy, modify, distribute and sell this software
 * and its documentation for any purpose is hereby granted without fee,
 * provided that the above copyright notice appear in all copies and
 * that both that copyright notice and this permission notice appear
 * in supporting documentation.  Alexander V. Konstantinou makes no
 * representations about the suitability of this software for any
 * purpose.  It is provided "as is" without express or implied warranty.
 *
 * ANTLR Object Constraint Language (OCL) grammar.  This grammar complies
 * with the UML 1.4 OCL grammar (version 0.1c)
 *
 * Id: ocl.g,v 1.1 2001/05/25 21:32:02 akonstan
 *
 * The latest version can be obtained at:
 *
 *         http://www.cs.columbia.edu/~akonstan/ocl/
 *
 * References:
 *
 *   - http://www.antlr.org/
 *   - http://www.omg.org/uml/
 *   - http://www.klasse.nl/ocl/
 */

 ////////////////////////////////////////////////////////////////////////////

/* CCM Tools : OCL parser
 * Robert Lechner <rlechner@sbox.tugraz.at>
 * copyright (c) 2003, 2004 Salomon Automation
 *
 * $Id$
 *
 *
 * History:
 *
 * - Version 1.0: original release (Alexander V. Konstantinou)
 * - Version 1.1: check for UML 1.5 compliance (Robert Lechner)
 * - Version 1.2: Java Code (Aug. 2003, Robert Lechner)
 *
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation Inc,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package ccmtools.OCL.utils;

import oclmetamodel.*;

public interface OCLTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int LITERAL_package = 4;
	int LITERAL_endpackage = 5;
	int LITERAL_def = 6;
	int NAME = 7;
	int COLON = 8;
	int LITERAL_context = 9;
	int DCOLON = 10;
	int LPAREN = 11;
	int COMMA = 12;
	int RPAREN = 13;
	int LITERAL_in = 14;
	int LITERAL_let = 15;
	int EQUAL = 16;
	int DOT = 17;
	int RARROW = 18;
	int ATSIGN = 19;
	int LITERAL_pre = 20;
	int SEMICOL = 21;
	int BAR = 22;
	int LBRACK = 23;
	int RBRACK = 24;
	int STRING = 25;
	int NUMBER = 26;
	int LITERAL_true = 27;
	int LITERAL_false = 28;
	int LITERAL_if = 29;
	int LITERAL_then = 30;
	int LITERAL_else = 31;
	int LITERAL_endif = 32;
	int LCURLY = 33;
	int RCURLY = 34;
	int DOTDOT = 35;
	int LITERAL_post = 36;
	int LITERAL_inv = 37;
	int LITERAL_Set = 38;
	int LITERAL_Bag = 39;
	int LITERAL_Sequence = 40;
	int LITERAL_Collection = 41;
	int PLUS = 42;
	int MINUS = 43;
	int LT = 44;
	int LE = 45;
	int GE = 46;
	int GT = 47;
	int DIVIDE = 48;
	int MULT = 49;
	int NEQUAL = 50;
	int LITERAL_implies = 51;
	int LITERAL_not = 52;
	int LITERAL_or = 53;
	int LITERAL_xor = 54;
	int LITERAL_and = 55;
	int SL_COMMENT = 56;
	int WS = 57;
	int POUND = 58;
}
