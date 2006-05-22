// $ANTLR 2.7.2: "idl3new.g" -> "IDL3Parser.java"$

/* CCM Tools : IDL3 Parser
 * Edin Arnautovic <edin.arnautovic@salomon.at>
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2002, 2003 Salomon Automation
 *
 * Generated using antlr <http://antlr.org/> from source grammar file :
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ccmtools.Metamodel.BaseIDL.*;
import ccmtools.Metamodel.ComponentIDL.*;


public interface IDL3TokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int SEMI = 4;
	int LITERAL_module = 5;
	int LCURLY = 6;
	int RCURLY = 7;
	int LITERAL_abstract = 8;
	int LITERAL_local = 9;
	int LITERAL_interface = 10;
	int COLON = 11;
	int COMMA = 12;
	int SCOPEOP = 13;
	int LITERAL_valuetype = 14;
	int LITERAL_custom = 15;
	int LITERAL_truncatable = 16;
	int LITERAL_supports = 17;
	int LITERAL_public = 18;
	int LITERAL_private = 19;
	int LITERAL_factory = 20;
	int LPAREN = 21;
	int RPAREN = 22;
	int LITERAL_in = 23;
	int LITERAL_const = 24;
	int ASSIGN = 25;
	int OR = 26;
	int XOR = 27;
	int AND = 28;
	int LSHIFT = 29;
	int RSHIFT = 30;
	int PLUS = 31;
	int MINUS = 32;
	int MOD = 33;
	int STAR = 34;
	int DIV = 35;
	int TILDE = 36;
	int LITERAL_TRUE = 37;
	int LITERAL_FALSE = 38;
	int LITERAL_typedef = 39;
	int LITERAL_native = 40;
	int LITERAL_float = 41;
	int LITERAL_double = 42;
	int LITERAL_long = 43;
	int LITERAL_short = 44;
	int LITERAL_unsigned = 45;
	int LITERAL_char = 46;
	int LITERAL_wchar = 47;
	int LITERAL_boolean = 48;
	int LITERAL_octet = 49;
	int LITERAL_any = 50;
	int LITERAL_Object = 51;
	int LITERAL_struct = 52;
	int LITERAL_union = 53;
	int LITERAL_switch = 54;
	int LITERAL_case = 55;
	int LITERAL_default = 56;
	int LITERAL_enum = 57;
	int LITERAL_sequence = 58;
	int LT = 59;
	int GT = 60;
	int LITERAL_string = 61;
	int LITERAL_wstring = 62;
	int LBRACK = 63;
	int RBRACK = 64;
	int LITERAL_exception = 65;
	int LITERAL_oneway = 66;
	int LITERAL_void = 67;
	int LITERAL_out = 68;
	int LITERAL_inout = 69;
	int LITERAL_raises = 70;
	int LITERAL_context = 71;
	int LITERAL_fixed = 72;
	int LITERAL_ValueBase = 73;
	int LITERAL_import = 74;
	int LITERAL_typeid = 75;
	int LITERAL_typeprefix = 76;
	int LITERAL_readonly = 77;
	int LITERAL_attribute = 78;
	int LITERAL_getraises = 79;
	int LITERAL_setraises = 80;
	int LITERAL_component = 81;
	int LITERAL_provides = 82;
	int LITERAL_uses = 83;
	int LITERAL_multiple = 84;
	int LITERAL_emits = 85;
	int LITERAL_publishes = 86;
	int LITERAL_consumes = 87;
	int LITERAL_home = 88;
	int LITERAL_manages = 89;
	int LITERAL_primarykey = 90;
	int LITERAL_finder = 91;
	int LITERAL_eventtype = 92;
	int INT = 93;
	int OCTAL = 94;
	int HEX = 95;
	int STRING_LITERAL = 96;
	int WIDE_STRING_LITERAL = 97;
	int CHAR_LITERAL = 98;
	int WIDE_CHAR_LITERAL = 99;
	int FLOAT = 100;
	int LITERAL_d = 101;
	int LITERAL_D = 102;
	int IDENT = 103;
	int QUESTION = 104;
	int DOT = 105;
	int NOT = 106;
	int WS = 107;
	int PREPROC_DIRECTIVE = 108;
	int SL_COMMENT = 109;
	int ML_COMMENT = 110;
	int DIGIT = 111;
	int OCTDIGIT = 112;
	int HEX_LETTER = 113;
	int EXPONENT = 114;
	int IDENT_LETTER = 115;
	int ESC = 116;
	int OCT = 117;
}
