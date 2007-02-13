// JFlex lexer definition for ccmtools assembly descriptions

package ccmtools.parser.assembly;

import java_cup.runtime.*;

%%

%class AssemblyLexer
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

    String current_input_filename = "(filename not set)";

    public void error(String message)
    {
        StringBuilder out = new StringBuilder();
        out.append(current_input_filename);
        out.append(" line ");
        out.append(yyline);
        out.append(": ");
        out.append(message);
        throw new RuntimeException(out.toString());
    }

    StringBuffer string = new StringBuffer();
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" {CommentContent} "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}
CommentContent = ( [^*] | \*+ [^*/] )*

PreprocessorLine = "#" {InputCharacter}* {LineTerminator}

Digit = [0-9]
Alpha = [a-zA-Z_]
Identifier = {Alpha} ({Alpha}|{Digit})*

QualifiedName = {GlobalName} | {ScopedName}
GlobalName = "::" {Identifier}
ScopedName = "::"? {Identifier} "::" {Identifier} ("::" {Identifier})*

OctDigit        = [0-7]
StringCharacter = [^\r\n\"\\]

Number = {DecInteger} | {HexInteger} | {Double}
DecInteger = ("+" | "-")? {Digit}+
HexInteger = ("0x" | "0X") [0-9a-fA-F]+
Double = ("+" | "-")? ({Double1} | {Double2} | {Double3} | {Double4})
Double1 = {Digit}+ "." {Digit}*
Double2 = {Digit}* "." {Digit}+
Double3 = {Digit}+ ("e" | "E") {DecInteger}
Double4 = ({Double1} | {Double2}) ("e" | "E") {DecInteger}

%state STRING

%%

<YYINITIAL>
{
    /* reserved words */
    "assembly"      { return symbol(sym.ASSEMBLY); }
    "attribute"     { return symbol(sym.ATTRIBUTE); }
    "component"     { return symbol(sym.COMPONENT); }
    "connect"       { return symbol(sym.CONNECT); }
    "constant"      { return symbol(sym.CONSTANT); }
    "implements"    { return symbol(sym.IMPLEMENTS); }
    "module"        { return symbol(sym.MODULE); }
    "this"          { return symbol(sym.THIS); }
    "to"            { return symbol(sym.TO); }

    /* operators and separators */
    ";"             { return symbol(sym.SEMICOLON); }
    "."             { return symbol(sym.DOT); }
    "{"             { return symbol(sym.LBRACE); }
    "}"             { return symbol(sym.RBRACE); }
    "="             { return symbol(sym.EQUAL); }

    /* string literal */
    "\""            { yybegin(STRING); string.setLength(0); }

    /* comments */
    {Comment}           { /* ignore */ }

    /* whitespace */
    {WhiteSpace}        { /* ignore */ }

    /* preprocessor line */
    {PreprocessorLine}  { /* ignore */ }

    /* numbers */
    {Number}            { return symbol(sym.NUMBER, yytext()); }

    /* qualified names */
    {QualifiedName}     { return symbol(sym.QN, yytext()); }

    /* identifers, after reserved words!!! */
    {Identifier}        { return symbol(sym.NAME, yytext()); }

    /* all other chars report an error */
    [^\n\r]             { error("Illegal character '" + yytext() + "'"); }
}

<STRING>
{
  "\""                           { yybegin(YYINITIAL);
                                   return symbol(sym.STRING, string.toString()); }

  {StringCharacter}+             { string.append( yytext() ); }

  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                                           string.append( val ); }

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""
                                                              +yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated string at end of line"); }
}
