/** Usercode Section */

package ccmtools.parser.idl;

import java_cup.runtime.*;

%%

/** Options and Declarations Section */

%public
%class IdlScanner
%unicode
%line
%column
%cup

%{
	private Symbol symbol(int type)
	{
		return new Symbol(type, yyline, yycolumn);
	}

	private Symbol symbol(int type, Object value)
	{
		return new Symbol(type, yyline, yycolumn, value);
	}
	
	public void error(String message)
	{
		StringBuilder out = new StringBuilder();
		out.append(ParserHelper.getInstance().getCurrentSourceFile());
		out.append(" line " + ParserHelper.getInstance().getCurrentSourceLine());
		out.append(": " + message);
		throw new RuntimeException(out.toString());
	}
%}

// Macro Declarations

LineTerminator 				= 	\r|\n|\r\n
InputCharacter 				= 	[^\r\n]
WhiteSpace 					= 	[ \t\f]
PreprocessorLine 			= 	"#" {InputCharacter}* {LineTerminator}
PragmaLine 					= 	"#pragma" {InputCharacter}* {LineTerminator}
EndOfLineComment 			= 	"//" {InputCharacter}* {LineTerminator}

Digits 						= 	[0-9]+
Oct_Digit 					= 	[0-7]
Hex_Digit 					= 	[a-fA-F0-9]
Int_Literal 					= 	[1-9][0-9]*
Oct_Literal					= 	0{Oct_Digit}*
Hex_Literal					= 	(0x|0X){Hex_Digit}*
Esc_Sequence1 				= 	"\\"[ntvbrfa\\\?\'\"]
Esc_Sequence2 				= 	"\\"{Oct_Digit}{1,3}
Esc_Sequence3 				= 	"\\"(x|X){Hex_Digit}{1,2}
Esc_Sequence4 				= 	"\\"(u|U){Hex_Digit}{1,4}
Esc_Sequence 				=  	({Esc_Sequence1}|{Esc_Sequence2}|{Esc_Sequence3})
Char 						= 	([^\n\t\"\'\\]|{Esc_Sequence})
WChar 						= 	({Char}|{Esc_Sequence4})
Char_Literal 				= 	"'"({Char}|\")"'"
WChar_Literal 				= 	"L'"({WChar}|\")"'"
String_Literal 				=	\"({Char}|"'")*\"
WString_Literal				= 	"L"\"({WChar}|"'")*\"

Float_Literal1 				=	{Digits}"."{Digits}?(e|E)("+"|"-")?{Digits}  
Float_Literal2 				=	{Digits}(e|E)("+"|"-")?{Digits}
Float_Literal3 				= 	{Digits}"."{Digits}
Float_Literal4 				= 	{Digits}"."
Float_Literal5 				= 	"."{Digits} 
Float_Literal6 				=	"."{Digits}(e|E)("+"|"-")?{Digits}  

Fixed_Literal1 				= 	{Digits}(d|D)
Fixed_Literal2 				= 	{Digits}"."(d|D)
Fixed_Literal3 				= 	"."{Digits}(d|D)
Fixed_Literal4 				= 	{Digits}"."{Digits}(d|D)

CORBA_Identifier				= 	[a-zA-Z_][a-zA-Z0-9_]*


%%

/** Lexical Rules Section */

<YYINITIAL>
{
	{WhiteSpace}			{ /* no actions*/ }		
	
	{EndOfLineComment}	{ /* no actions*/ }
						
	{LineTerminator}		{
							ParserHelper.getInstance().incrementCurrentSourceLine();
						}

	{PragmaLine}			{
							ParserHelper.getInstance().handlePragmaLine(yytext());
							return symbol(sym.T_PRAGMA, new String(yytext()));
						}
						
	{PreprocessorLine}  {
							ParserHelper.getInstance().handlePreprocessorLine(yytext());
							return symbol(sym.T_INCLUDE, new String(yytext()));
						}


	"{"					{ return symbol(sym.T_LEFT_CURLY_BRACKET); }
	"}"					{ return symbol(sym.T_RIGHT_CURLY_BRACKET); }
	"["		 			{ return symbol(sym.T_LEFT_SQUARE_BRACKET); }
	"]"					{ return symbol(sym.T_RIGHT_SQUARE_BRACKET); }
	"("					{ return symbol(sym.T_LEFT_PARANTHESIS); }
	")"					{ return symbol(sym.T_RIGHT_PARANTHESIS); }
	":"					{ return symbol(sym.T_COLON); }
	","					{ return symbol(sym.T_COMMA); }
	";"					{ return symbol(sym.T_SEMICOLON); }
	"="					{ return symbol(sym.T_EQUAL); }
	">>"					{ return symbol(sym.T_SHIFTRIGHT); }
	"<<"					{ return symbol(sym.T_SHIFTLEFT); }
	"+"					{ return symbol(sym.T_PLUS_SIGN); }
	"-"					{ return symbol(sym.T_MINUS_SIGN); }
	"*"					{ return symbol(sym.T_ASTERISK); }
	"/"					{ return symbol(sym.T_SOLIDUS); }
	"%"					{ return symbol(sym.T_PERCENT_SIGN); }
	"~"					{ return symbol(sym.T_TILDE); }
	"|"					{ return symbol(sym.T_VERTICAL_LINE); }
	"^"					{ return symbol(sym.T_CIRCUMFLEX); }
	"&"					{ return symbol(sym.T_AMPERSAND); }
	"<"					{ return symbol(sym.T_LESS_THAN_SIGN); }
	">"					{ return symbol(sym.T_GREATER_THAN_SIGN); }
	"::"					{ return symbol(sym.T_SCOPE); } 


	"import"				{ return symbol(sym.T_IMPORT); }
	"const"				{ return symbol(sym.T_CONST); }
	"typedef"			{ return symbol(sym.T_TYPEDEF); }
	"float"				{ return symbol(sym.T_FLOAT); }
	"double"				{ return symbol(sym.T_DOUBLE); }
	"char"				{ return symbol(sym.T_CHAR); }
	"wchar"				{ return symbol(sym.T_WCHAR); }
	"fixed"             	{ return symbol(sym.T_FIXED); }	
	"boolean"			{ return symbol(sym.T_BOOLEAN); }	
	"string"				{ return symbol(sym.T_STRING); }
	"wstring"			{ return symbol(sym.T_WSTRING); }
	"void"				{ return symbol(sym.T_VOID); }
	"unsigned"			{ return symbol(sym.T_UNSIGNED); }
	"long" 				{ return symbol(sym.T_LONG); }
	"short"				{ return symbol(sym.T_SHORT); }
	"FALSE"				{ return symbol(sym.T_FALSE); }
	"TRUE"				{ return symbol(sym.T_TRUE); }
	"struct"				{ return symbol(sym.T_STRUCT); }
	"union"				{ return symbol(sym.T_UNION); }
	"switch"				{ return symbol(sym.T_SWITCH); }
	"case"				{ return symbol(sym.T_CASE); }
	"default"			{ return symbol(sym.T_DEFAULT); }
	"enum"				{ return symbol(sym.T_ENUM); }
	"in"					{ return symbol(sym.T_IN); }
	"out"				{ return symbol(sym.T_OUT); }
	"interface"			{ return symbol(sym.T_INTERFACE); }
	"abstract"			{ return symbol(sym.T_ABSTRACT); }
	"valuetype"			{ return symbol(sym.T_VALUETYPE); }
	"truncatable"		{ return symbol(sym.T_TRUNCATABLE); }
	"supports"			{ return symbol(sym.T_SUPPORTS); }
	"custom"				{ return symbol(sym.T_CUSTOM); }
	"public"				{ return symbol(sym.T_PUBLIC); }
	"private"			{ return symbol(sym.T_PRIVATE); }
	"factory"			{ return symbol(sym.T_FACTORY); }
	"native"				{ return symbol(sym.T_NATIVE); }
	"ValueBase"			{ return symbol(sym.T_VALUEBASE); }
	"typeId"				{ return symbol(sym.T_TYPEID); }
	"typePrefix"			{ return symbol(sym.T_TYPEPREFIX); }
	"getRaises"			{ return symbol(sym.T_GETRAISES); }
	"setRaises"			{ return symbol(sym.T_SETRAISES); }
	"local"				{ return symbol(sym.T_LOCAL); }

	"module"				{ return symbol(sym.T_MODULE); }
	"octet"				{ return symbol(sym.T_OCTET); }
	"any"				{ return symbol(sym.T_ANY); }
	"sequence"			{ return symbol(sym.T_SEQUENCE); }
	"readonly"			{ return symbol(sym.T_READONLY); }
	"attribute"			{ return symbol(sym.T_ATTRIBUTE); }
	"exception"			{ return symbol(sym.T_EXCEPTION); }
	"oneway"				{ return symbol(sym.T_ONEWAY); }
	"inout"				{ return symbol(sym.T_INOUT); }
	"raises"				{ return symbol(sym.T_RAISES); }
	"context"			{ return symbol(sym.T_CONTEXT); }

	"Object"        		{ return symbol(sym.T_OBJECT); }

	"component"			{ return symbol(sym.T_COMPONENT); }
	"provides"			{ return symbol(sym.T_PROVIDES); }
	"uses"				{ return symbol(sym.T_USES); }
	"multiple"			{ return symbol(sym.T_MULTIPLE); }
	"emits"				{ return symbol(sym.T_EMITS); }
	"publishes"			{ return symbol(sym.T_PUBLISHES); }
	"consumes"			{ return symbol(sym.T_CONSUMES); }
	"home"				{ return symbol(sym.T_HOME); }
	"manages"			{ return symbol(sym.T_MANAGES); }
	"primaryKey"			{ return symbol(sym.T_PRIMARYKEY); }
	"finder"				{ return symbol(sym.T_FINDER); }
	"eventtype"			{ return symbol(sym.T_EVENTTYPE); }


	{CORBA_Identifier}	{ return symbol(sym.T_IDENTIFIER, yytext()); }

	{Float_Literal1}	|
	{Float_Literal2}	|
	{Float_Literal3}	|
	{Float_Literal4}	|
	{Float_Literal5}	|
	{Float_Literal6}		{ return symbol(sym.T_FLOATING_PT_LITERAL, ParserHelper.getInstance().createFloat(yytext())); }
	
	{Fixed_Literal1}	|
	{Fixed_Literal2}	|
	{Fixed_Literal3}	|
	{Fixed_Literal4}		{ return symbol(sym.T_FIXED_PT_LITERAL, ParserHelper.getInstance().createFixed(yytext())); }

	{Int_Literal}		{ return symbol(sym.T_INTEGER_LITERAL, ParserHelper.getInstance().createInteger(yytext())); }

	{Oct_Literal}		{ return symbol(sym.T_INTEGER_LITERAL, ParserHelper.getInstance().createOctet(yytext())); }

	{Hex_Literal}		{ return symbol(sym.T_INTEGER_LITERAL, ParserHelper.getInstance().createHex(yytext())); }

	{Char_Literal}		{ return symbol(sym.T_CHARACTER_LITERAL, ParserHelper.getInstance().createChar(yytext())); }
						
	{WChar_Literal}		{ return symbol(sym.T_WCHARACTER_LITERAL, ParserHelper.getInstance().createWChar(yytext())); }
						
	{String_Literal}		{ return symbol(sym.T_STRING_LITERAL, ParserHelper.getInstance().createString(yytext())); }
						
	{WString_Literal}	{ return symbol(sym.T_WSTRING_LITERAL, ParserHelper.getInstance().createWString(yytext())); }
}

[^]						{ 
							error("Illegal character '" + yytext() + "'");
						}

