// $ANTLR 2.7.2: "OCL.g" -> "OclParser.java"$

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

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

public class OclParser extends antlr.LLkParser       implements OCLTokenTypes
 {

////////////////////////////////////////////////////////////////////////////

/**
 * Creates a parse tree from an OCL file.
 * @param fileName  the name of the OCL file
 * @param creator  the parsetree creator
 * @return the parse tree
 * @throws java.io.FileNotFoundException  file access error
 * @throws antlr.ANTLRException  parser error
 */
public static MFile parseFile( String fileName, OclParsetreeCreator creator )
 throws antlr.ANTLRException, java.io.FileNotFoundException
{
	java.io.BufferedInputStream stream = new java.io.BufferedInputStream(
		new java.io.FileInputStream(fileName) );
	OclLexer lexer = new OclLexer( stream );
	OclParser parser = new OclParser( lexer );
	parser.setParsetreeCreator( creator );
	return parser.oclFile();
}

private static OclParser the_parser_;

/**
 * Helper function for reporting error messages.
 */
static void throwException( String message ) throws OclParserException
{
	Token t;
	String f;
	try
	{
		t = the_parser_.LT(1);
		f = the_parser_.getFilename();
	}
	catch( Exception e )
	{
		throw new OclParserException( message );
	}
	throw new OclParserException( message, f, t.getLine(), t.getColumn() );
}

private OclParsetreeCreator creator_;

/**
 * Sets the parsetree creator.
 */
public void setParsetreeCreator( OclParsetreeCreator creator )
{
	creator_ = creator;
}

private String removeDelimitation( String value )
{
    int len = value.length();
    if( value.indexOf('\'')==0 && value.lastIndexOf('\'')==(len-1) )
    {
        if( len>2 )
        {
            return value.substring( 1, len-1 );
        }
        else
        {
            return "";
        }
    }
    else
    {
        return value;
    }
}

////////////////////////////////////////////////////////////////////////////

protected OclParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public OclParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected OclParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public OclParser(TokenStream lexer) {
  this(lexer,2);
}

public OclParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
}

	public final MFile  oclFile() throws RecognitionException, TokenStreamException {
		MFile p_file;
		
		
			if( creator_==null )
			{
				throw new OclParserException("no parsetree creator defined");
			}
			the_parser_=this;
			p_file=creator_.createFile();
			String p_name=null;
			MPackage p_pkg=null;
			MContext p_ctxt=null;
		
		
		try {      // for error handling
			{
			int _cnt5=0;
			_loop5:
			do {
				if ((LA(1)==LITERAL_package)) {
					match(LITERAL_package);
					p_name=pathName();
					if ( inputState.guessing==0 ) {
						p_pkg=creator_.createPackage(p_name);
					}
					{
					_loop4:
					do {
						if ((LA(1)==LITERAL_context)) {
							p_ctxt=constraint();
							if ( inputState.guessing==0 ) {
								creator_.add(p_pkg,p_ctxt);
							}
						}
						else {
							break _loop4;
						}
						
					} while (true);
					}
					match(LITERAL_endpackage);
					if ( inputState.guessing==0 ) {
						creator_.add(p_file,p_pkg);
					}
				}
				else {
					if ( _cnt5>=1 ) { break _loop5; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt5++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				
					the_parser_=null;
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return p_file;
	}
	
	public final String  pathName() throws RecognitionException, TokenStreamException {
		String name=null;
		
		Token  n1 = null;
		Token  n2 = null;
		
		try {      // for error handling
			n1 = LT(1);
			match(NAME);
			if ( inputState.guessing==0 ) {
				name=n1.getText();
			}
			{
			_loop98:
			do {
				if ((LA(1)==DCOLON)) {
					match(DCOLON);
					n2 = LT(1);
					match(NAME);
					if ( inputState.guessing==0 ) {
						name+=(OclConstants.PATHNAME_SEPARATOR+n2.getText());
					}
				}
				else {
					break _loop98;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return name;
	}
	
	public final MContext  constraint() throws RecognitionException, TokenStreamException {
		MContext p_ctxt=null;
		
		
			MDefinition p_def=null;
			MConstraint p_con=null;
		
		
		try {      // for error handling
			p_ctxt=contextDeclaration();
			{
			int _cnt8=0;
			_loop8:
			do {
				switch ( LA(1)) {
				case LITERAL_def:
				{
					p_def=conDefinition();
					if ( inputState.guessing==0 ) {
						creator_.add(p_ctxt,p_def);
					}
					break;
				}
				case LITERAL_pre:
				case LITERAL_post:
				case LITERAL_inv:
				{
					p_con=conConstraint();
					if ( inputState.guessing==0 ) {
						creator_.add(p_ctxt,p_con);
					}
					break;
				}
				default:
				{
					if ( _cnt8>=1 ) { break _loop8; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				}
				_cnt8++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		return p_ctxt;
	}
	
	public final MContext  contextDeclaration() throws RecognitionException, TokenStreamException {
		MContext p_ctxt=null;
		
		
		try {      // for error handling
			match(LITERAL_context);
			{
			if ((LA(1)==NAME) && (LA(2)==DCOLON)) {
				p_ctxt=operationContext();
			}
			else if ((LA(1)==NAME) && (_tokenSet_3.member(LA(2)))) {
				p_ctxt=classifierContext();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return p_ctxt;
	}
	
	public final MDefinition  conDefinition() throws RecognitionException, TokenStreamException {
		MDefinition p_def=null;
		
		Token  n1 = null;
		
			String p_name=null;
			MLetStatement p_let=null;
		
		
		try {      // for error handling
			match(LITERAL_def);
			{
			switch ( LA(1)) {
			case NAME:
			{
				n1 = LT(1);
				match(NAME);
				if ( inputState.guessing==0 ) {
					p_name=n1.getText();
				}
				break;
			}
			case COLON:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(COLON);
			if ( inputState.guessing==0 ) {
				p_def=creator_.createDefinition(p_name);
			}
			{
			_loop12:
			do {
				if ((LA(1)==LITERAL_let)) {
					p_let=letExpression();
					if ( inputState.guessing==0 ) {
						creator_.add(p_def,p_let);
					}
				}
				else {
					break _loop12;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		return p_def;
	}
	
	public final MConstraint  conConstraint() throws RecognitionException, TokenStreamException {
		MConstraint p_con=null;
		
		Token  n1 = null;
		
			String p_st=null, p_name=null;
			MConstraintExpression p_oclexpr=null;
		
		
		try {      // for error handling
			p_st=stereotype();
			{
			switch ( LA(1)) {
			case NAME:
			{
				n1 = LT(1);
				match(NAME);
				if ( inputState.guessing==0 ) {
					p_name=n1.getText();
				}
				break;
			}
			case COLON:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(COLON);
			p_oclexpr=oclExpression();
			if ( inputState.guessing==0 ) {
				p_con=creator_.createConstraint(p_st,p_name,p_oclexpr);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		return p_con;
	}
	
	public final MLetStatement  letExpression() throws RecognitionException, TokenStreamException {
		MLetStatement p_le=null;
		
		Token  n1 = null;
		Token  n2 = null;
		Token  n3 = null;
		
			MTypeSpecifier p_typeSpec=null;
			MExpression p_expr=null;
		
		
		try {      // for error handling
			match(LITERAL_let);
			n1 = LT(1);
			match(NAME);
			if ( inputState.guessing==0 ) {
				p_le=creator_.createLetStatement(n1.getText());
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				match(LPAREN);
				{
				switch ( LA(1)) {
				case NAME:
				{
					n2 = LT(1);
					match(NAME);
					match(COLON);
					p_typeSpec=typeSpecifier();
					if ( inputState.guessing==0 ) {
						creator_.add(p_le, creator_.createFormalParameter(n2.getText(),p_typeSpec));
					}
					{
					_loop36:
					do {
						if ((LA(1)==COMMA)) {
							match(COMMA);
							n3 = LT(1);
							match(NAME);
							match(COLON);
							p_typeSpec=typeSpecifier();
							if ( inputState.guessing==0 ) {
								creator_.add(p_le, creator_.createFormalParameter(n3.getText(),p_typeSpec));
							}
						}
						else {
							break _loop36;
						}
						
					} while (true);
					}
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RPAREN);
				break;
			}
			case COLON:
			case EQUAL:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				p_typeSpec=typeSpecifier();
				if ( inputState.guessing==0 ) {
					p_le.setType(p_typeSpec);
				}
				break;
			}
			case EQUAL:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(EQUAL);
			p_expr=expression();
			if ( inputState.guessing==0 ) {
				p_le.setExpression(p_expr);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		return p_le;
	}
	
	public final String  stereotype() throws RecognitionException, TokenStreamException {
		String kind=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_pre:
			{
				match(LITERAL_pre);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.STEREOTYPE_PRECONDITION;
				}
				break;
			}
			case LITERAL_post:
			{
				match(LITERAL_post);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.STEREOTYPE_POSTCONDITION;
				}
				break;
			}
			case LITERAL_inv:
			{
				match(LITERAL_inv);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.STEREOTYPE_INVARIANT;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_7);
			} else {
			  throw ex;
			}
		}
		return kind;
	}
	
	public final MConstraintExpression  oclExpression() throws RecognitionException, TokenStreamException {
		MConstraintExpression p_oclexpr;
		
		
			p_oclexpr = creator_.createConstraintExpression();
			MLetStatement p_le=null;
			MExpression p_expr=null;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_in:
			case LITERAL_let:
			{
				{
				_loop30:
				do {
					if ((LA(1)==LITERAL_let)) {
						p_le=letExpression();
						if ( inputState.guessing==0 ) {
							creator_.add(p_oclexpr,p_le);
						}
					}
					else {
						break _loop30;
					}
					
				} while (true);
				}
				match(LITERAL_in);
				break;
			}
			case NAME:
			case LPAREN:
			case STRING:
			case NUMBER:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_if:
			case LITERAL_Set:
			case LITERAL_Bag:
			case LITERAL_Sequence:
			case MINUS:
			case LITERAL_not:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			p_expr=expression();
			if ( inputState.guessing==0 ) {
				creator_.set(p_oclexpr,p_expr);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		return p_oclexpr;
	}
	
	public final MOperationContext  operationContext() throws RecognitionException, TokenStreamException {
		MOperationContext p_ctxt=null;
		
		Token  n0 = null;
		Token  n1 = null;
		Token  n2 = null;
		
			String p_opName=null;
			MTypeSpecifier p_typeSpec=null;
		
		
		try {      // for error handling
			n0 = LT(1);
			match(NAME);
			match(DCOLON);
			p_opName=operationName();
			if ( inputState.guessing==0 ) {
				p_ctxt=creator_.createOperationContext(n0.getText(),p_opName);
			}
			match(LPAREN);
			{
			switch ( LA(1)) {
			case NAME:
			{
				n1 = LT(1);
				match(NAME);
				match(COLON);
				p_typeSpec=typeSpecifier();
				if ( inputState.guessing==0 ) {
					creator_.add(p_ctxt, creator_.createFormalParameter(n1.getText(),p_typeSpec));
				}
				{
				_loop22:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						n2 = LT(1);
						match(NAME);
						match(COLON);
						p_typeSpec=typeSpecifier();
						if ( inputState.guessing==0 ) {
							creator_.add(p_ctxt, creator_.createFormalParameter(n2.getText(),p_typeSpec));
						}
					}
					else {
						break _loop22;
					}
					
				} while (true);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				p_typeSpec=typeSpecifier();
				if ( inputState.guessing==0 ) {
					p_ctxt.setReturnType(p_typeSpec);
				}
				break;
			}
			case LITERAL_def:
			case LITERAL_pre:
			case LITERAL_post:
			case LITERAL_inv:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return p_ctxt;
	}
	
	public final MClassifierContext  classifierContext() throws RecognitionException, TokenStreamException {
		MClassifierContext p_ctxt=null;
		
		Token  n1 = null;
		Token  n2 = null;
		Token  n3 = null;
		
		try {      // for error handling
			if ((LA(1)==NAME) && (LA(2)==COLON)) {
				{
				n1 = LT(1);
				match(NAME);
				match(COLON);
				n2 = LT(1);
				match(NAME);
				}
				if ( inputState.guessing==0 ) {
					p_ctxt=creator_.createClassifierContext(n1.getText(),n2.getText());
				}
			}
			else if ((LA(1)==NAME) && (_tokenSet_4.member(LA(2)))) {
				n3 = LT(1);
				match(NAME);
				if ( inputState.guessing==0 ) {
					p_ctxt=creator_.createClassifierContext(OclConstants.KEYWORD_SELF,n3.getText());
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return p_ctxt;
	}
	
	public final String  operationName() throws RecognitionException, TokenStreamException {
		String operator=null;
		
		Token  name = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NAME:
			{
				name = LT(1);
				match(NAME);
				if ( inputState.guessing==0 ) {
					operator=name.getText();
				}
				break;
			}
			case EQUAL:
			{
				match(EQUAL);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_EQUAL;
				}
				break;
			}
			case PLUS:
			{
				match(PLUS);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_PLUS;
				}
				break;
			}
			case MINUS:
			{
				match(MINUS);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_MINUS;
				}
				break;
			}
			case LT:
			{
				match(LT);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_LT;
				}
				break;
			}
			case LE:
			{
				match(LE);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_LE;
				}
				break;
			}
			case GE:
			{
				match(GE);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_GE;
				}
				break;
			}
			case GT:
			{
				match(GT);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_GT;
				}
				break;
			}
			case DIVIDE:
			{
				match(DIVIDE);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_DIVIDE;
				}
				break;
			}
			case MULT:
			{
				match(MULT);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_MULT;
				}
				break;
			}
			case NEQUAL:
			{
				match(NEQUAL);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_NEQUAL;
				}
				break;
			}
			case LITERAL_implies:
			{
				match(LITERAL_implies);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_IMPLIES;
				}
				break;
			}
			case LITERAL_not:
			{
				match(LITERAL_not);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_NOT;
				}
				break;
			}
			case LITERAL_or:
			{
				match(LITERAL_or);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_OR;
				}
				break;
			}
			case LITERAL_xor:
			{
				match(LITERAL_xor);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_XOR;
				}
				break;
			}
			case LITERAL_and:
			{
				match(LITERAL_and);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_AND;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		return operator;
	}
	
	public final MTypeSpecifier  typeSpecifier() throws RecognitionException, TokenStreamException {
		MTypeSpecifier p_typeSpec=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case NAME:
			{
				p_typeSpec=simpleTypeSpecifier();
				break;
			}
			case LITERAL_Set:
			case LITERAL_Bag:
			case LITERAL_Sequence:
			case LITERAL_Collection:
			{
				p_typeSpec=collectionType();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return p_typeSpec;
	}
	
	public final MSimpleTypeSpec  simpleTypeSpecifier() throws RecognitionException, TokenStreamException {
		MSimpleTypeSpec p_typeSpec=null;
		
		
			String p_name=null;
		
		
		try {      // for error handling
			p_name=pathName();
			if ( inputState.guessing==0 ) {
				p_typeSpec=creator_.createSimpleTypeSpec(p_name);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return p_typeSpec;
	}
	
	public final MCollectionTypeSpec  collectionType() throws RecognitionException, TokenStreamException {
		MCollectionTypeSpec p_typeSpec=null;
		
		
			String p_kind=null;
			MSimpleTypeSpec p_type=null;
		
		
		try {      // for error handling
			p_kind=collectionParameterKind();
			match(LPAREN);
			p_type=simpleTypeSpecifier();
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				p_typeSpec=creator_.createCollectionTypeSpec(p_kind,p_type);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return p_typeSpec;
	}
	
	public final String  collectionParameterKind() throws RecognitionException, TokenStreamException {
		String kind=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_Set:
			{
				match(LITERAL_Set);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.COLLECTIONKIND_SET;
				}
				break;
			}
			case LITERAL_Bag:
			{
				match(LITERAL_Bag);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.COLLECTIONKIND_BAG;
				}
				break;
			}
			case LITERAL_Sequence:
			{
				match(LITERAL_Sequence);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.COLLECTIONKIND_SEQUENCE;
				}
				break;
			}
			case LITERAL_Collection:
			{
				match(LITERAL_Collection);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.COLLECTIONKIND_COLLECTION;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		return kind;
	}
	
	public final MExpression  expression() throws RecognitionException, TokenStreamException {
		MExpression p_expr=null;
		
		
		try {      // for error handling
			p_expr=logicalExpression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final MExpression  logicalExpression() throws RecognitionException, TokenStreamException {
		MExpression p_expr=null;
		
		
			String p_op=null;
			MExpression p_e2=null;
		
		
		try {      // for error handling
			p_expr=relationalExpression();
			{
			_loop40:
			do {
				if ((_tokenSet_12.member(LA(1)))) {
					p_op=logicalOperator();
					p_e2=relationalExpression();
					if ( inputState.guessing==0 ) {
						p_expr=creator_.createOperationExpression(p_expr,p_op,p_e2);
					}
				}
				else {
					break _loop40;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final MExpression  relationalExpression() throws RecognitionException, TokenStreamException {
		MExpression p_expr=null;
		
		
			String p_op=null;
			MExpression p_e2=null;
		
		
		try {      // for error handling
			p_expr=additiveExpression();
			{
			switch ( LA(1)) {
			case EQUAL:
			case LT:
			case LE:
			case GE:
			case GT:
			case NEQUAL:
			{
				p_op=relationalOperator();
				p_e2=additiveExpression();
				if ( inputState.guessing==0 ) {
					p_expr=creator_.createOperationExpression(p_expr,p_op,p_e2);
				}
				break;
			}
			case LITERAL_endpackage:
			case LITERAL_def:
			case LITERAL_context:
			case COMMA:
			case RPAREN:
			case LITERAL_in:
			case LITERAL_let:
			case LITERAL_pre:
			case BAR:
			case RBRACK:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RCURLY:
			case DOTDOT:
			case LITERAL_post:
			case LITERAL_inv:
			case LITERAL_implies:
			case LITERAL_or:
			case LITERAL_xor:
			case LITERAL_and:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_13);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final String  logicalOperator() throws RecognitionException, TokenStreamException {
		String operator=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_and:
			{
				match(LITERAL_and);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_AND;
				}
				break;
			}
			case LITERAL_or:
			{
				match(LITERAL_or);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_OR;
				}
				break;
			}
			case LITERAL_xor:
			{
				match(LITERAL_xor);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_XOR;
				}
				break;
			}
			case LITERAL_implies:
			{
				match(LITERAL_implies);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_IMPLIES;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return operator;
	}
	
	public final MExpression  additiveExpression() throws RecognitionException, TokenStreamException {
		MExpression p_expr=null;
		
		
			String p_op=null;
			MExpression p_e2=null;
		
		
		try {      // for error handling
			p_expr=multiplicativeExpression();
			{
			_loop45:
			do {
				if ((LA(1)==PLUS||LA(1)==MINUS)) {
					p_op=addOperator();
					p_e2=multiplicativeExpression();
					if ( inputState.guessing==0 ) {
						p_expr=creator_.createOperationExpression(p_expr,p_op,p_e2);
					}
				}
				else {
					break _loop45;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final String  relationalOperator() throws RecognitionException, TokenStreamException {
		String operator=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case EQUAL:
			{
				match(EQUAL);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_EQUAL;
				}
				break;
			}
			case GT:
			{
				match(GT);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_GT;
				}
				break;
			}
			case LT:
			{
				match(LT);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_LT;
				}
				break;
			}
			case GE:
			{
				match(GE);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_GE;
				}
				break;
			}
			case LE:
			{
				match(LE);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_LE;
				}
				break;
			}
			case NEQUAL:
			{
				match(NEQUAL);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_NEQUAL;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return operator;
	}
	
	public final MExpression  multiplicativeExpression() throws RecognitionException, TokenStreamException {
		MExpression p_expr=null;
		
		
			String p_op=null;
			MExpression p_e2=null;
		
		
		try {      // for error handling
			p_expr=unaryExpression();
			{
			_loop48:
			do {
				if ((LA(1)==DIVIDE||LA(1)==MULT)) {
					p_op=multiplyOperator();
					p_e2=unaryExpression();
					if ( inputState.guessing==0 ) {
						p_expr=creator_.createOperationExpression(p_expr,p_op,p_e2);
					}
				}
				else {
					break _loop48;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_16);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final String  addOperator() throws RecognitionException, TokenStreamException {
		String operator=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			{
				match(PLUS);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_PLUS;
				}
				break;
			}
			case MINUS:
			{
				match(MINUS);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_MINUS;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return operator;
	}
	
	public final MExpression  unaryExpression() throws RecognitionException, TokenStreamException {
		MExpression p_expr=null;
		
		
			String p_op=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case MINUS:
			case LITERAL_not:
			{
				{
				p_op=unaryOperator();
				p_expr=postfixExpression();
				}
				if ( inputState.guessing==0 ) {
					p_expr=creator_.createOperationExpression(null,p_op,p_expr);
				}
				break;
			}
			case NAME:
			case LPAREN:
			case STRING:
			case NUMBER:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_if:
			case LITERAL_Set:
			case LITERAL_Bag:
			case LITERAL_Sequence:
			{
				p_expr=postfixExpression();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final String  multiplyOperator() throws RecognitionException, TokenStreamException {
		String operator=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case MULT:
			{
				match(MULT);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_MULT;
				}
				break;
			}
			case DIVIDE:
			{
				match(DIVIDE);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_DIVIDE;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return operator;
	}
	
	public final String  unaryOperator() throws RecognitionException, TokenStreamException {
		String operator=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case MINUS:
			{
				match(MINUS);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_MINUS;
				}
				break;
			}
			case LITERAL_not:
			{
				match(LITERAL_not);
				if ( inputState.guessing==0 ) {
					operator=OclConstants.OPERATOR_NOT;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		return operator;
	}
	
	public final MExpression  postfixExpression() throws RecognitionException, TokenStreamException {
		MExpression p_expr=null;
		
		
			MPropertyCall p_call=null;
		
		
		try {      // for error handling
			p_expr=primaryExpression();
			{
			_loop55:
			do {
				switch ( LA(1)) {
				case DOT:
				{
					{
					match(DOT);
					p_call=propertyCall();
					}
					if ( inputState.guessing==0 ) {
						p_expr=creator_.createPostfixExpression(p_expr,p_call,false);
					}
					break;
				}
				case RARROW:
				{
					{
					match(RARROW);
					p_call=propertyCall();
					}
					if ( inputState.guessing==0 ) {
						p_expr=creator_.createPostfixExpression(p_expr,p_call,true);
					}
					break;
				}
				default:
				{
					break _loop55;
				}
				}
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final MExpression  primaryExpression() throws RecognitionException, TokenStreamException {
		MExpression p_expr=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_Set:
			case LITERAL_Bag:
			case LITERAL_Sequence:
			{
				p_expr=literalCollection();
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				p_expr=expression();
				match(RPAREN);
				break;
			}
			case LITERAL_if:
			{
				p_expr=ifExpression();
				break;
			}
			default:
				boolean synPredMatched84 = false;
				if (((_tokenSet_19.member(LA(1))) && (_tokenSet_20.member(LA(2))))) {
					int _m84 = mark();
					synPredMatched84 = true;
					inputState.guessing++;
					try {
						{
						switch ( LA(1)) {
						case STRING:
						{
							match(STRING);
							break;
						}
						case NUMBER:
						{
							match(NUMBER);
							break;
						}
						case LITERAL_true:
						{
							match(LITERAL_true);
							break;
						}
						case LITERAL_false:
						{
							match(LITERAL_false);
							break;
						}
						case NAME:
						{
							{
							match(NAME);
							match(DCOLON);
							match(NAME);
							{
							_loop83:
							do {
								if ((LA(1)==DCOLON)) {
									match(DCOLON);
									match(NAME);
								}
								else {
									break _loop83;
								}
								
							} while (true);
							}
							}
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
					}
					catch (RecognitionException pe) {
						synPredMatched84 = false;
					}
					rewind(_m84);
					inputState.guessing--;
				}
				if ( synPredMatched84 ) {
					p_expr=literal();
				}
				else if ((LA(1)==NAME) && (_tokenSet_21.member(LA(2)))) {
					p_expr=propertyCall();
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final MPropertyCall  propertyCall() throws RecognitionException, TokenStreamException {
		MPropertyCall p_call=null;
		
		
			String p_name=null;
			boolean p_previous=false;
			MActualParameters p_ap=null;
			MPropertyCallParameters p_pcp=null;
		
		
		try {      // for error handling
			p_name=pathName();
			{
			switch ( LA(1)) {
			case ATSIGN:
			{
				timeExpression();
				if ( inputState.guessing==0 ) {
					p_previous=true;
				}
				break;
			}
			case LITERAL_endpackage:
			case LITERAL_def:
			case LITERAL_context:
			case LPAREN:
			case COMMA:
			case RPAREN:
			case LITERAL_in:
			case LITERAL_let:
			case EQUAL:
			case DOT:
			case RARROW:
			case LITERAL_pre:
			case BAR:
			case LBRACK:
			case RBRACK:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RCURLY:
			case DOTDOT:
			case LITERAL_post:
			case LITERAL_inv:
			case PLUS:
			case MINUS:
			case LT:
			case LE:
			case GE:
			case GT:
			case DIVIDE:
			case MULT:
			case NEQUAL:
			case LITERAL_implies:
			case LITERAL_or:
			case LITERAL_xor:
			case LITERAL_and:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				p_call=creator_.createPropertyCall(p_name,p_previous);
			}
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				p_ap=qualifiers();
				if ( inputState.guessing==0 ) {
					p_call.setQualifiers(p_ap);
				}
				break;
			}
			case LITERAL_endpackage:
			case LITERAL_def:
			case LITERAL_context:
			case LPAREN:
			case COMMA:
			case RPAREN:
			case LITERAL_in:
			case LITERAL_let:
			case EQUAL:
			case DOT:
			case RARROW:
			case LITERAL_pre:
			case BAR:
			case RBRACK:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RCURLY:
			case DOTDOT:
			case LITERAL_post:
			case LITERAL_inv:
			case PLUS:
			case MINUS:
			case LT:
			case LE:
			case GE:
			case GT:
			case DIVIDE:
			case MULT:
			case NEQUAL:
			case LITERAL_implies:
			case LITERAL_or:
			case LITERAL_xor:
			case LITERAL_and:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				p_pcp=propertyCallParameters();
				if ( inputState.guessing==0 ) {
					p_call.setCallParameters(p_pcp);
				}
				break;
			}
			case LITERAL_endpackage:
			case LITERAL_def:
			case LITERAL_context:
			case COMMA:
			case RPAREN:
			case LITERAL_in:
			case LITERAL_let:
			case EQUAL:
			case DOT:
			case RARROW:
			case LITERAL_pre:
			case BAR:
			case RBRACK:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RCURLY:
			case DOTDOT:
			case LITERAL_post:
			case LITERAL_inv:
			case PLUS:
			case MINUS:
			case LT:
			case LE:
			case GE:
			case GT:
			case DIVIDE:
			case MULT:
			case NEQUAL:
			case LITERAL_implies:
			case LITERAL_or:
			case LITERAL_xor:
			case LITERAL_and:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return p_call;
	}
	
	public final void timeExpression() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(ATSIGN);
			match(LITERAL_pre);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_23);
			} else {
			  throw ex;
			}
		}
	}
	
	public final MActualParameters  qualifiers() throws RecognitionException, TokenStreamException {
		MActualParameters p_ap=null;
		
		
		try {      // for error handling
			match(LBRACK);
			p_ap=actualParameterList();
			match(RBRACK);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		return p_ap;
	}
	
	public final MPropertyCallParameters  propertyCallParameters() throws RecognitionException, TokenStreamException {
		MPropertyCallParameters p_pcp=null;
		
		
			MActualParameters p_ap1=null, p_ap2=null;
			MDeclarator p_decl=null;
		
		
		try {      // for error handling
			boolean synPredMatched67 = false;
			if (((LA(1)==LPAREN) && (LA(2)==NAME))) {
				int _m67 = mark();
				synPredMatched67 = true;
				inputState.guessing++;
				try {
					{
					match(LPAREN);
					match(NAME);
					{
					_loop64:
					do {
						if ((LA(1)==COMMA)) {
							match(COMMA);
							match(NAME);
						}
						else {
							break _loop64;
						}
						
					} while (true);
					}
					{
					switch ( LA(1)) {
					case COLON:
					{
						match(COLON);
						simpleTypeSpecifier();
						break;
					}
					case SEMICOL:
					case BAR:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					{
					switch ( LA(1)) {
					case SEMICOL:
					{
						match(SEMICOL);
						match(NAME);
						match(COLON);
						typeSpecifier();
						match(EQUAL);
						expression();
						break;
					}
					case BAR:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(BAR);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched67 = false;
				}
				rewind(_m67);
				inputState.guessing--;
			}
			if ( synPredMatched67 ) {
				match(LPAREN);
				p_decl=declarator();
				{
				switch ( LA(1)) {
				case NAME:
				case LPAREN:
				case STRING:
				case NUMBER:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_if:
				case LITERAL_Set:
				case LITERAL_Bag:
				case LITERAL_Sequence:
				case MINUS:
				case LITERAL_not:
				{
					p_ap1=actualParameterList();
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RPAREN);
				if ( inputState.guessing==0 ) {
					p_pcp=creator_.createPropertyCallParameters(p_decl,p_ap1);
				}
			}
			else if ((LA(1)==LPAREN) && (_tokenSet_25.member(LA(2)))) {
				match(LPAREN);
				{
				switch ( LA(1)) {
				case NAME:
				case LPAREN:
				case STRING:
				case NUMBER:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_if:
				case LITERAL_Set:
				case LITERAL_Bag:
				case LITERAL_Sequence:
				case MINUS:
				case LITERAL_not:
				{
					p_ap2=actualParameterList();
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RPAREN);
				if ( inputState.guessing==0 ) {
					p_pcp=creator_.createPropertyCallParameters(null,p_ap2);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return p_pcp;
	}
	
	public final MDeclarator  declarator() throws RecognitionException, TokenStreamException {
		MDeclarator p_decl=null;
		
		Token  n1 = null;
		Token  n2 = null;
		Token  n3 = null;
		
			MSimpleTypeSpec p_simple=null;
			MTypeSpecifier p_type=null;
			MExpression p_expr=null;
		
		
		try {      // for error handling
			n1 = LT(1);
			match(NAME);
			if ( inputState.guessing==0 ) {
				p_decl=creator_.createDeclarator(n1.getText());
			}
			{
			_loop72:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					n2 = LT(1);
					match(NAME);
					if ( inputState.guessing==0 ) {
						creator_.add(p_decl,n2.getText());
					}
				}
				else {
					break _loop72;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				p_simple=simpleTypeSpecifier();
				if ( inputState.guessing==0 ) {
					p_decl.setSimpleType(p_simple);
				}
				break;
			}
			case SEMICOL:
			case BAR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case SEMICOL:
			{
				match(SEMICOL);
				n3 = LT(1);
				match(NAME);
				match(COLON);
				p_type=typeSpecifier();
				match(EQUAL);
				p_expr=expression();
				if ( inputState.guessing==0 ) {
					p_decl.setOptName(n3.getText()); p_decl.setOptType(p_type); p_decl.setOptExpression(p_expr);
				}
				break;
			}
			case BAR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(BAR);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return p_decl;
	}
	
	public final MActualParameters  actualParameterList() throws RecognitionException, TokenStreamException {
		MActualParameters p_ap=null;
		
		
			MExpression p_expr=null;
		
		
		try {      // for error handling
			p_expr=expression();
			if ( inputState.guessing==0 ) {
				p_ap=creator_.createActualParameters(p_expr);
			}
			{
			_loop78:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					p_expr=expression();
					if ( inputState.guessing==0 ) {
						creator_.add(p_ap,p_expr);
					}
				}
				else {
					break _loop78;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		return p_ap;
	}
	
	public final MCollectionLiteral  literalCollection() throws RecognitionException, TokenStreamException {
		MCollectionLiteral p_expr=null;
		
		
			String p_kind=null;
			MCollectionPart p_item=null;
		
		
		try {      // for error handling
			p_kind=collectionLiteralKind();
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				p_expr=creator_.createCollectionLiteral(p_kind);
			}
			{
			switch ( LA(1)) {
			case NAME:
			case LPAREN:
			case STRING:
			case NUMBER:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_if:
			case LITERAL_Set:
			case LITERAL_Bag:
			case LITERAL_Sequence:
			case MINUS:
			case LITERAL_not:
			{
				p_item=collectionItem();
				if ( inputState.guessing==0 ) {
					creator_.add(p_expr,p_item);
				}
				{
				_loop93:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						p_item=collectionItem();
						if ( inputState.guessing==0 ) {
							creator_.add(p_expr,p_item);
						}
					}
					else {
						break _loop93;
					}
					
				} while (true);
				}
				break;
			}
			case RCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RCURLY);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final MLiteralExpression  literal() throws RecognitionException, TokenStreamException {
		MLiteralExpression p_expr=null;
		
		Token  s1 = null;
		Token  n1 = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case STRING:
			{
				s1 = LT(1);
				match(STRING);
				if ( inputState.guessing==0 ) {
					p_expr=creator_.createStringLiteral(removeDelimitation(s1.getText()));
				}
				break;
			}
			case NUMBER:
			{
				n1 = LT(1);
				match(NUMBER);
				if ( inputState.guessing==0 ) {
					p_expr=creator_.createNumericLiteral(n1.getText());
				}
				break;
			}
			case LITERAL_true:
			{
				match(LITERAL_true);
				if ( inputState.guessing==0 ) {
					p_expr=creator_.createBooleanLiteral(true);
				}
				break;
			}
			case LITERAL_false:
			{
				match(LITERAL_false);
				if ( inputState.guessing==0 ) {
					p_expr=creator_.createBooleanLiteral(false);
				}
				break;
			}
			case NAME:
			{
				p_expr=enumLiteral();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return p_expr;
	}
	
	public final MIfExpression  ifExpression() throws RecognitionException, TokenStreamException {
		MIfExpression p_expr0=null;
		
		
			MExpression p_expr1=null, p_expr2=null, p_expr3=null;
		
		
		try {      // for error handling
			match(LITERAL_if);
			p_expr1=expression();
			match(LITERAL_then);
			p_expr2=expression();
			match(LITERAL_else);
			p_expr3=expression();
			match(LITERAL_endif);
			if ( inputState.guessing==0 ) {
				p_expr0=creator_.createIfExpression(p_expr1,p_expr2,p_expr3);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return p_expr0;
	}
	
	public final MEnumLiteral  enumLiteral() throws RecognitionException, TokenStreamException {
		MEnumLiteral p_enum=null;
		
		Token  n1 = null;
		Token  n2 = null;
		Token  n3 = null;
		
		try {      // for error handling
			n1 = LT(1);
			match(NAME);
			match(DCOLON);
			n2 = LT(1);
			match(NAME);
			if ( inputState.guessing==0 ) {
				p_enum=creator_.createEnumLiteral(n1.getText(),n2.getText());
			}
			{
			_loop89:
			do {
				if ((LA(1)==DCOLON)) {
					match(DCOLON);
					n3 = LT(1);
					match(NAME);
					if ( inputState.guessing==0 ) {
						creator_.add(p_enum,n3.getText());
					}
				}
				else {
					break _loop89;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return p_enum;
	}
	
	public final String  collectionLiteralKind() throws RecognitionException, TokenStreamException {
		String kind=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_Set:
			{
				match(LITERAL_Set);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.COLLECTIONKIND_SET;
				}
				break;
			}
			case LITERAL_Bag:
			{
				match(LITERAL_Bag);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.COLLECTIONKIND_BAG;
				}
				break;
			}
			case LITERAL_Sequence:
			{
				match(LITERAL_Sequence);
				if ( inputState.guessing==0 ) {
					kind=OclConstants.COLLECTIONKIND_SEQUENCE;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		return kind;
	}
	
	public final MCollectionPart  collectionItem() throws RecognitionException, TokenStreamException {
		MCollectionPart p_item=null;
		
		
			MExpression p_expr1=null, p_expr2=null;
			boolean p_range=false;
		
		
		try {      // for error handling
			p_expr1=expression();
			{
			switch ( LA(1)) {
			case DOTDOT:
			{
				match(DOTDOT);
				p_expr2=expression();
				if ( inputState.guessing==0 ) {
					p_range=true;
				}
				break;
			}
			case COMMA:
			case RCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
					if( p_range )
					{
						p_item=creator_.createCollectionRange(p_expr1,p_expr2);
					}
					else
					{
						p_item=creator_.createCollectionItem(p_expr1);
					}
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return p_item;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"package\"",
		"\"endpackage\"",
		"\"def\"",
		"NAME",
		"COLON",
		"\"context\"",
		"DCOLON",
		"LPAREN",
		"COMMA",
		"RPAREN",
		"\"in\"",
		"\"let\"",
		"EQUAL",
		"DOT",
		"RARROW",
		"ATSIGN",
		"\"pre\"",
		"SEMICOL",
		"BAR",
		"LBRACK",
		"RBRACK",
		"STRING",
		"NUMBER",
		"\"true\"",
		"\"false\"",
		"\"if\"",
		"\"then\"",
		"\"else\"",
		"\"endif\"",
		"LCURLY",
		"RCURLY",
		"DOTDOT",
		"\"post\"",
		"\"inv\"",
		"\"Set\"",
		"\"Bag\"",
		"\"Sequence\"",
		"\"Collection\"",
		"PLUS",
		"MINUS",
		"LT",
		"LE",
		"GE",
		"GT",
		"DIVIDE",
		"MULT",
		"NEQUAL",
		"\"implies\"",
		"\"not\"",
		"\"or\"",
		"\"xor\"",
		"\"and\"",
		"a comment",
		"white space",
		"POUND"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 67549861611829856L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 544L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 206159479104L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 206159478848L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 206159479392L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 206159528544L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 384L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 2048L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 206159556672L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 206165848128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 265236312672L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 65302194596872192L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 65302459833184864L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 4514320905930880L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 66692242530759264L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 66705436670292576L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 67549861600424544L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 1925185538176L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 503316608L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 67549861600818784L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 67549861609733728L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 67549861600817760L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 67549861609208416L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { 67549861600819808L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 4514320905939072L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = { 16785408L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { 8589934592L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { 17179873280L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	
	}
