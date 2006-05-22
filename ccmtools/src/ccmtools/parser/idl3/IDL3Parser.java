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

public class IDL3Parser extends antlr.LLkParser       implements IDL3TokenTypes
 {

    // debug levels for the parser are allowed to use the lower 24 bits of a
    // long int.
    private final static long DEBUG_FORWARD_DECL = 0x000001;
    private final static long DEBUG_INTERFACE    = 0x000002;
    private final static long DEBUG_COMPONENT    = 0x000004;
    private final static long DEBUG_EVENT        = 0x000008;
    private final static long DEBUG_EXCEPTION    = 0x000010;
    private final static long DEBUG_OPERATION    = 0x000020;
    private final static long DEBUG_PARAMETER    = 0x000040;
    private final static long DEBUG_IDL_TYPE     = 0x000080;
    private final static long DEBUG_TYPEDEF      = 0x000100;
    private final static long DEBUG_NAMESPACE    = 0x000200;
    private final static long DEBUG_PARSER_TOKEN = 0x000400;
    private final static long DEBUG_SYMBOL_TABLE = 0x000800;
    private final static long DEBUG_FILE         = 0x001000;
    private final static long DEBUG_VALUE        = 0x002000;
    private final static long DEBUG_CONSTANT     = 0x004000;
    private final static long DEBUG_INHERITANCE  = 0x008000;
    private final static long DEBUG_PRIMARY_KEY  = 0x010000;
    private final static long DEBUG_CONTAINER    = 0x020000;

    // padding for future debug level manipulation ...
    private final static long DEBUG_UNUSED_F     = 0x040000;
    private final static long DEBUG_UNUSED_G     = 0x080000;
    private final static long DEBUG_UNUSED_H     = 0x100000;
    private final static long DEBUG_UNUSED_I     = 0x200000;
    private final static long DEBUG_UNUSED_J     = 0x400000;
    private final static long DEBUG_UNUSED_K     = 0x800000;

    private final static String[] keywords =
    {
        "abstract", "any", "attribute", "boolean", "case", "char", "component",
        "const", "consumes", "context", "custom", "default", "double",
        "exception", "emits", "enum", "eventtype", "factory", "FALSE", "finder",
        "fixed", "float", "getraises", "home", "import", "in", "inout",
        "interface", "local", "long", "module", "multiple", "native", "Object",
        "octet", "oneway", "out", "primarykey", "private", "provides", "public",
        "publishes", "raises", "readonly", "setraises", "sequence", "short",
        "string", "struct", "supports", "switch", "TRUE", "truncatable",
        "typedef", "typeid", "typeprefix", "unsigned", "union", "uses",
        "ValueBase", "valuetype", "void", "wchar", "wstring",
    };

    private long debug = 0;

    private IDL3SymbolTable symbolTable;
    private Map scopeTable;
    private MContainer specification = null;
    private ParserManager manager;

    /*
     *  Set up the current class instance for a parse, by specifying the parser
     *  manager object that this parser belongs to. This loads the parser
     *  manager's symbol table into this parser instance.
     */
    public void setManager(ParserManager m)
    {
        manager = m;
        symbolTable = manager.getSymbolTable();
        scopeTable = new Hashtable();
    }

    /*
     * Set the debug level for this parser class instance.
     */
    public void setDebug(long d) { debug = d; }

    /*
     * Translate the given object scope id into a CORBA compatible repository
     * identifier.
     */
    private String createRepositoryId(String id)
    {
        String scope = symbolTable.currentScopeAsString();
        String replaced = scope.replaceAll("::", "/");

        if(scope.equals("")) {
            return "IDL:"+ replaced + id + ":1.0";
        } else {
            return "IDL:"+ replaced + "/" + id + ":1.0";
        }
    }

    /*
     * Find out if a given string is an IDL keyword.
     */
    private boolean checkKeyword(String id)
    {
        for (int i = 0; i < keywords.length; i++)
            if (keywords[i].equalsIgnoreCase(id)) return false;
        return true;
    }

    /*
     * Find out if the given string could be a Long object. If so, return the
     * Long object, and if not throw a semantic exception.
     */
    private Long checkLong(String expr)
        throws TokenStreamException
    {
        Long result = null;
        try {
            result = new Long(expr);
            return result;
        } catch (Exception e) {
            throw new TokenStreamException(
                "can't evaluate '"+expr+"' as an integer");
        }
    }

    /*
     * Check to make sure the given string is a positive (1 or greater) valid
     * long integer. Throw an exception if not.
     */
    private void checkPositive(String bound)
        throws TokenStreamException
    {
        try {
            Long limit = new Long(bound);
            if (limit.longValue() < 1) { throw new RuntimeException(); }
        } catch (RuntimeException e) {
            throw new TokenStreamException("invalid positive value "+bound);
        }
    }

    /*
     * Find out if the given string could be a Float object. If so, return the *
     * Float object, and if not throw a semantic exception.
     */
    private Float checkFloat(String expr)
        throws TokenStreamException
    {
        Float result = null;
        try {
            result = new Float(expr);
            return result;
        } catch (Exception e) {
            throw new TokenStreamException(
                "can't evaluate '"+expr+"' as a float");
        }
    }

    /*
     * Get the currently active source file. Returns an empty string if the
     * current source file is the same as the original source file.
     */
    private String getSourceFile()
    {
        String source = manager.getSourceFile();
        if (source.equals(manager.getOriginalFile())) source = "";
        if ((debug & DEBUG_FILE) != 0)
            System.out.println("[f] setting source file to '"+source+"'");
        return source;
    }

    /*
     * See if the given identifier points to an object that's already in the
     * symbol table. If the identifier is found, and points to an object of the
     * same type as the given query object, then check to see if the looked up
     * object is a forward declaration (which means it has a null definition
     * body in some sense, usually meaning that there are no members associated
     * with the definition). If the looked up object is not a forward
     * declaration, throw a semantic error.
     */
    private MContained verifyNameEmpty(String id, MContained query)
        throws TokenStreamException
    {
        MContained lookup =
            lookupName(id, DEBUG_TYPEDEF | DEBUG_INTERFACE);

        if (lookup == null) return query;

        Class qtype = query.getClass().getInterfaces()[0];

        if (! qtype.isInstance(lookup)) return query;

        if (((lookup instanceof MStructDef) &&
                (((MStructDef) lookup).getMembers().size() == 0)) ||
            ((lookup instanceof MUnionDef) &&
                (((MUnionDef) lookup).getUnionMembers().size() == 0)) ||
            ((lookup instanceof MEnumDef) &&
                (((MEnumDef) lookup).getMembers().size() == 0)) ||
            ((lookup instanceof MExceptionDef) &&
                (((MExceptionDef) lookup).getMembers().size() == 0)) ||
            ((lookup instanceof MComponentDef) &&
                ((((MComponentDef) lookup).getContentss().size() == 0) &&
                 (((MComponentDef) lookup).getEmitss().size() == 0) &&
                 (((MComponentDef) lookup).getPublishess().size() == 0) &&
                 (((MComponentDef) lookup).getConsumess().size() == 0) &&
                 (((MComponentDef) lookup).getFacets().size() == 0) &&
                 (((MComponentDef) lookup).getReceptacles().size() == 0))) ||
            ((lookup instanceof MHomeDef) &&
                ((((MHomeDef) lookup).getContentss().size() == 0) &&
                 (((MHomeDef) lookup).getFactories().size() == 0) &&
                 (((MHomeDef) lookup).getFinders().size() == 0))) ||
            ((lookup instanceof MInterfaceDef) &&
                (((MInterfaceDef) lookup).getContentss().size() == 0)) ||
            (lookup instanceof MModuleDef)) {
            return lookup;
        }

        throw new TokenStreamException(
            "identifier '"+id+"' was used more than once");
    }

    /*
     * Verify that the given name exists and is an instance of the correct
     * class. Throw an exception if not.
     */
    private MContained verifyNameExists(String id, MContained query)
        throws TokenStreamException
    {
        MContained lookup =
            lookupName(id, DEBUG_IDL_TYPE | DEBUG_TYPEDEF);

        try {
            Class qtype = query.getClass().getInterfaces()[0];
            if (qtype.isInstance(lookup)) return lookup;
            throw new RuntimeException();
        } catch (RuntimeException e) {
            throw new TokenStreamException("identifier '"+id+"' is undefined or of the wrong type");
        }
    }

    /*
     * Get the IDL type for a given identifier.
     */
    private MIDLType getIDLType(String id)
        throws TokenStreamException
    {
        MContained contained = new MContainedImpl();
        contained = verifyNameExists(id, contained);

        if (contained instanceof MIDLType)
            return (MIDLType) contained;

        if (contained instanceof MTyped)
            return ((MTyped) contained).getIdlType();

        throw new TokenStreamException("cannot find IDL type for '"+
            contained.getIdentifier()+"'");
    }

    /*
     * Add the contents to the given container. Check to ensure each item is
     * defined in the current file being parsed.
     */
    private void checkAddContents(MContainer container, List contents)
        throws TokenStreamException
    {
        if (container == null)
            throw new TokenStreamException(
                "can't add contents ("+contents+") to a null container");

        Iterator it = contents.iterator();
        while (it.hasNext()) {
            MContained item = (MContained) it.next();

            if (item == null)
                throw new TokenStreamException(
                    "can't add a null item from '"+contents+
                    "' to container '"+container+"'");

            item.setDefinedIn(container);
            container.addContents(item);

            if ((debug & DEBUG_CONTAINER) != 0)
                System.out.println(
                    "[C] adding "+item.getIdentifier()+" to container "+
                    container.getIdentifier());
        }
    }

    /*
     * Set the base list for the given interface, after checking to make sure
     * the given bases actually exist and have the correct abstract / local
     * attributes.
     */
    private void checkSetBases(MInterfaceDef iface, List bases)
        throws TokenStreamException
    {
        String id = iface.getIdentifier();

        ArrayList verified = new ArrayList();
        for (Iterator it = bases.iterator(); it.hasNext(); ) {
            String inherit = (String) it.next();
            MContained lookup = lookupName(inherit, DEBUG_INTERFACE | DEBUG_INHERITANCE);

            if ((lookup == null) || (! (lookup instanceof MInterfaceDef)))
                throw new TokenStreamException("interface '"+id+"' can't inherit from undefined interface '"+inherit+"'");

            MInterfaceDef base = (MInterfaceDef) lookup;

            if (iface.isAbstract() != base.isAbstract())
                throw new TokenStreamException("interface '"+id+"' can't inherit from '"+inherit+"' because one interface is abstract");

            if ((! iface.isLocal()) && base.isLocal())
                throw new TokenStreamException("interface '"+id+"' can't inherit from '"+inherit+"' because '"+id+"' is not local");

            if ((debug & DEBUG_INTERFACE) != 0)
                System.out.println("[f] adding base '"+inherit+"' to interface '"+id+"'");

            verified.add(base);
        }

        iface.setBases(verified);
    }

    /*
     * Set the supported interface list for the given interface (actually has to
     * be a component or home, but they're both of type MInterfaceDef), after
     * checking to make sure the given supported interfaces actually exist.
     */
    private void checkSetSupports(MInterfaceDef iface, List supports)
        throws TokenStreamException
    {
        List slist = new ArrayList();
        for (Iterator it = supports.iterator(); it.hasNext(); ) {
            String name = (String) it.next();
            MContained lookup = lookupName(name, DEBUG_INTERFACE | DEBUG_INHERITANCE);
            if ((lookup == null) || (! (lookup instanceof MInterfaceDef))) {
                throw new TokenStreamException("interface '"+iface.getIdentifier()+"' can't support undefined interface '"+name+"'");
            } else {
                MSupportsDef support = new MSupportsDefImpl();
                support.setIdentifier("support_"+name);
                support.setSupports((MInterfaceDef) lookup);
                support.setDefinedIn(iface);
                if (iface instanceof MComponentDef) {
                    support.setComponent((MComponentDef) iface);
                } else if (iface instanceof MHomeDef) {
                    support.setHome((MHomeDef) iface);
                }
                slist.add(support);
            }
        }

        if (iface instanceof MComponentDef)
            ((MComponentDef) iface).setSupportss(slist);
        else if (iface instanceof MHomeDef)
            ((MHomeDef) iface).setSupportss(slist);
        else throw new RuntimeException(iface.getIdentifier()+" must be a component or home instance");
    }

    /*
     * Examine the given list of objects for repeated identifiers, and set them
     * to the member list appropriate for the type of the given box.
     */
    private void checkSetMembers(MContained box, List members)
        throws TokenStreamException
    {
        MExceptionDef exception = null;
        MStructDef struct = null;
        MUnionDef union = null;

        if (box instanceof MExceptionDef) exception = (MExceptionDef) box;
        else if (box instanceof MStructDef) struct = (MStructDef) box;
        else if (box instanceof MUnionDef) union = (MUnionDef) box;

        if ((struct == null) && (exception == null) && (union == null))
            throw new RuntimeException(box+" must be an exception, union, or struct");

        if ((members.size() == 0) && (exception == null))
            throw new TokenStreamException("container '"+box.getIdentifier()+"' has no members");

        // check if there are fields with same identifier.

        String outID = "";
        String inID = "";
        Iterator o = members.iterator();
        Iterator i = members.iterator();
        try {
            if (union != null) {
                MUnionFieldDef out, in;
                while (o.hasNext()) {
                    out = (MUnionFieldDef) o.next(); outID = out.getIdentifier();
                    while (i.hasNext()) {
                        in = (MUnionFieldDef) i.next(); inID = in.getIdentifier();
                        if (outID.equals(inID) && ! out.equals(in))
                            throw new RuntimeException();
                    }
                }
            } else {
                MFieldDef out, in;
                while (o.hasNext()) {
                    out = (MFieldDef) o.next(); outID = out.getIdentifier();
                    while (i.hasNext()) {
                        in = (MFieldDef) i.next(); inID = in.getIdentifier();
                        if (outID.equals(inID) && ! out.equals(in))
                            throw new RuntimeException();
                    }
                }
            }
        } catch (RuntimeException e) {
            throw new TokenStreamException("repeated identifier '"+outID+"' in '"+box.getIdentifier()+"'");
        }

        // add the members to the given box.
        Iterator it = members.iterator();
        if (union != null) {
            while (it.hasNext()) ((MUnionFieldDef) it.next()).setUnion(union);
            union.setUnionMembers(members);
        } else {
            while (it.hasNext()) {
                MFieldDef f = (MFieldDef) it.next();
                if (struct != null) f.setStructure(struct);
                else f.setException(exception);
            }
            if (struct != null) struct.setMembers(members);
            else exception.setMembers(members);
        }
    }

    /*
     * Set the parameter list for the given operation.
     */
    private void checkSetParameters(MOperationDef op, List params)
    {
        for (Iterator p = params.iterator(); p.hasNext(); )
            ((MParameterDef) p.next()).setOperation(op);
        op.setParameters(params);
    }

    /*
     * Set the exception list for the given operation. Check that the given
     * exceptions have been defined somewhere.
     */
    private void checkSetExceptions(MOperationDef op, List excepts)
        throws TokenStreamException
    {
        op.setExceptionDefs(new HashSet(excepts));
    }

    /*
     * Set the base(s) of this value def according to the name given.
     */
    private void addValueBase(MValueDef val, String name)
    {
        MValueDef inherited = (MValueDef)
            lookupName(name, DEBUG_VALUE | DEBUG_INHERITANCE);

        if ((inherited != null) && (inherited.getContentss() != null)) {
            if (inherited.isAbstract()) val.addAbstractBase(inherited);
            else val.setBase(inherited);
        }
    }

    /*
     * Set the supports information for the given value using the given name.
     */
    private void addValueSupports(MValueDef val, String name)
        throws TokenStreamException
    {
        String id = val.getIdentifier();
        MContained support =
            lookupName(name, DEBUG_VALUE | DEBUG_INTERFACE);

        if (support != null) {
            val.setInterfaceDef((MInterfaceDef) support);
            if ((debug & (DEBUG_INTERFACE | DEBUG_INHERITANCE)) != 0)
                System.out.println("[v] added support '"+name+"' to value '"+id+"'");
        } else {
            throw new TokenStreamException("value '"+id+"' can't inherit from undefined value '"+name+"'");
        }
    }

    /*
     * This function is a debug wrapper for the symbol table function of the
     * same name. It essentially just calls the symbolTable function, but if
     * needed first it prints stuff.
     */
    private MContained lookupName(String name, long level)
    {
        MContained result = null;

        if ((debug & level) != 0) {
            if ((debug & DEBUG_SYMBOL_TABLE) != 0)
                System.out.println(symbolTable.toString());
            System.out.println("[L] looking up '" + name + "'");
        }

        if (name.indexOf("::") > 0) {
            result = this.symbolTable.lookupScopedName(name);
        } else {
            String look = new String(name);
            if (look.startsWith("::"))
                look = look.substring(2, look.length());
            result = this.symbolTable.lookupNameInCurrentScope(look);
        }

        if ((debug & level) != 0) {
            if (result == null) System.out.println("[L] '"+name+"' not found");
            else System.out.println("[L] '"+name+"' ("+result+") found");
        }

        return result;
    }

protected IDL3Parser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public IDL3Parser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected IDL3Parser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public IDL3Parser(TokenStream lexer) {
  this(lexer,2);
}

public IDL3Parser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
}

/******************************************************************************/
	public final MContainer  specification() throws RecognitionException, TokenStreamException {
		MContainer container = null;
		
		
		container = new MContainerImpl();
		String imported = null;
		List decls = null;
		List definitions = new ArrayList();
		specification = container;
		
		
		try {      // for error handling
			{
			_loop3:
			do {
				if ((LA(1)==LITERAL_import)) {
					import_dcl();
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
			{
			int _cnt5=0;
			_loop5:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					decls=definition();
					if ( inputState.guessing==0 ) {
						definitions.addAll(decls);
					}
				}
				else {
					if ( _cnt5>=1 ) { break _loop5; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt5++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				
				checkAddContents(container, definitions);
				
				if ((debug & DEBUG_FILE) != 0)
				System.out.println("[f] input file "+
				manager.getOriginalFile()+" parsed");
				
				if ((debug & DEBUG_SYMBOL_TABLE) != 0)
				System.out.println(symbolTable.toString());
				
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
		return container;
	}
	
	public final void import_dcl() throws RecognitionException, TokenStreamException {
		
		String scope = null;
		
		try {      // for error handling
			match(LITERAL_import);
			scope=imported_scope();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				scopeTable.put(scope, scope);
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
	}
	
	public final List  definition() throws RecognitionException, TokenStreamException {
		List definitions = null;
		
		definitions = new ArrayList(); MContained holder = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_typedef:
			case LITERAL_native:
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			{
				definitions=type_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_const:
			{
				holder=const_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					definitions.add(holder);
				}
				break;
			}
			case LITERAL_exception:
			{
				holder=except_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					definitions.add(holder);
				}
				break;
			}
			case LITERAL_module:
			{
				holder=module();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					definitions.add(holder);
				}
				break;
			}
			case LITERAL_typeid:
			{
				type_id_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_typeprefix:
			{
				type_prefix_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_component:
			{
				holder=component();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					definitions.add(holder);
				}
				break;
			}
			case LITERAL_home:
			{
				holder=home_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					definitions.add(holder);
				}
				break;
			}
			default:
				boolean synPredMatched8 = false;
				if ((((LA(1) >= LITERAL_abstract && LA(1) <= LITERAL_interface)) && (LA(2)==LITERAL_interface||LA(2)==IDENT))) {
					int _m8 = mark();
					synPredMatched8 = true;
					inputState.guessing++;
					try {
						{
						iface();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched8 = false;
					}
					rewind(_m8);
					inputState.guessing--;
				}
				if ( synPredMatched8 ) {
					holder=iface();
					match(SEMI);
					if ( inputState.guessing==0 ) {
						definitions.add(holder);
					}
				}
				else {
					boolean synPredMatched10 = false;
					if (((LA(1)==LITERAL_abstract||LA(1)==LITERAL_valuetype||LA(1)==LITERAL_custom) && (LA(2)==LITERAL_valuetype||LA(2)==IDENT))) {
						int _m10 = mark();
						synPredMatched10 = true;
						inputState.guessing++;
						try {
							{
							value();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched10 = false;
						}
						rewind(_m10);
						inputState.guessing--;
					}
					if ( synPredMatched10 ) {
						holder=value();
						match(SEMI);
						if ( inputState.guessing==0 ) {
							definitions.add(holder);
						}
					}
					else if ((LA(1)==LITERAL_abstract||LA(1)==LITERAL_custom||LA(1)==LITERAL_eventtype) && (LA(2)==LITERAL_eventtype||LA(2)==IDENT)) {
						holder=event();
						match(SEMI);
						if ( inputState.guessing==0 ) {
							definitions.add(holder);
						}
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					consume();
					consumeUntil(_tokenSet_3);
				} else {
				  throw ex;
				}
			}
			return definitions;
		}
		
	public final List  type_dcl() throws RecognitionException, TokenStreamException {
		List decls = null;
		
		
		decls = new ArrayList();
		MIDLType type = null;
		String id = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_typedef:
			{
				match(LITERAL_typedef);
				decls=type_declarator();
				break;
			}
			case LITERAL_enum:
			{
				type=enum_type();
				if ( inputState.guessing==0 ) {
					decls.add(type);
				}
				break;
			}
			case LITERAL_native:
			{
				match(LITERAL_native);
				id=simple_declarator();
				break;
			}
			default:
				boolean synPredMatched109 = false;
				if (((LA(1)==LITERAL_struct) && (LA(2)==IDENT))) {
					int _m109 = mark();
					synPredMatched109 = true;
					inputState.guessing++;
					try {
						{
						struct_type();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched109 = false;
					}
					rewind(_m109);
					inputState.guessing--;
				}
				if ( synPredMatched109 ) {
					type=struct_type();
					if ( inputState.guessing==0 ) {
						decls.add(type);
					}
				}
				else {
					boolean synPredMatched111 = false;
					if (((LA(1)==LITERAL_union) && (LA(2)==IDENT))) {
						int _m111 = mark();
						synPredMatched111 = true;
						inputState.guessing++;
						try {
							{
							union_type();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched111 = false;
						}
						rewind(_m111);
						inputState.guessing--;
					}
					if ( synPredMatched111 ) {
						type=union_type();
						if ( inputState.guessing==0 ) {
							decls.add(type);
						}
					}
					else {
						boolean synPredMatched113 = false;
						if (((LA(1)==LITERAL_struct||LA(1)==LITERAL_union) && (LA(2)==IDENT))) {
							int _m113 = mark();
							synPredMatched113 = true;
							inputState.guessing++;
							try {
								{
								constr_forward_decl();
								match(SEMI);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched113 = false;
							}
							rewind(_m113);
							inputState.guessing--;
						}
						if ( synPredMatched113 ) {
							type=constr_forward_decl();
							if ( inputState.guessing==0 ) {
								decls.add(type);
							}
						}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}}
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
				return decls;
			}
			
	public final MConstantDef  const_dcl() throws RecognitionException, TokenStreamException {
		MConstantDef constant = null;
		
		
		constant = new MConstantDefImpl();
		String id = null;
		MIDLType type = null;
		String expr = null;
		
		
		try {      // for error handling
			match(LITERAL_const);
			type=const_type();
			if ( inputState.guessing==0 ) {
				constant.setIdlType(type);
			}
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				constant = (MConstantDef) verifyNameEmpty(id, constant);
				constant.setSourceFile(getSourceFile());
				constant.setIdentifier(id);
				symbolTable.add(id, constant);
				
			}
			match(ASSIGN);
			expr=const_exp();
			if ( inputState.guessing==0 ) {
				
				if (type instanceof MPrimitiveDef) {
				MPrimitiveDef ptype = (MPrimitiveDef) type;
				if (ptype.getKind() == MPrimitiveKind.PK_OCTET) {
				constant.setConstValue(new Integer(expr));
				} 
				else if (ptype.getKind() == MPrimitiveKind.PK_BOOLEAN) {
				constant.setConstValue(new Boolean(expr));
				} 
				else if (ptype.getKind() == MPrimitiveKind.PK_SHORT) {
				constant.setConstValue(new Integer(expr));
				} 
				else if(ptype.getKind() == MPrimitiveKind.PK_USHORT) {
				constant.setConstValue(new Integer(expr));
				} 
				else if (ptype.getKind() == MPrimitiveKind.PK_LONG) {
				constant.setConstValue(new Long(expr));
				} 
				else if (ptype.getKind() == MPrimitiveKind.PK_ULONG) {
				constant.setConstValue(new Long(expr));
				} 
				else if (ptype.getKind() == MPrimitiveKind.PK_LONGLONG) {
				constant.setConstValue(new Long(expr));
				} 
				else if (ptype.getKind() == MPrimitiveKind.PK_ULONGLONG) {
				constant.setConstValue(new Long(expr));
				} 
				else if (ptype.getKind() == MPrimitiveKind.PK_CHAR) {
				constant.setConstValue(expr);
				}
				else if (ptype.getKind() == MPrimitiveKind.PK_FLOAT) {
				constant.setConstValue(new Float(expr));
				}
				else if (ptype.getKind() == MPrimitiveKind.PK_DOUBLE) {
				constant.setConstValue(new Double(expr));
				}
				} 
				else if (type instanceof MStringDef) {
				constant.setConstValue(expr);
				} 
				else if (type instanceof MWstringDef) {
				constant.setConstValue(expr);
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
		return constant;
	}
	
	public final MExceptionDef  except_dcl() throws RecognitionException, TokenStreamException {
		MExceptionDef except = null;
		
		
		except = new MExceptionDefImpl();
		String id = null;
		List members = new ArrayList();
		List decls = null;
		boolean repeatedID = false;
		
		
		try {      // for error handling
			match(LITERAL_exception);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				except = (MExceptionDef) verifyNameEmpty(id, except);
				except.setSourceFile(getSourceFile());
				except.setIdentifier(id);
				symbolTable.add(id, except);
				
			}
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(id);
			}
			{
			_loop196:
			do {
				if ((_tokenSet_5.member(LA(1)))) {
					decls=member();
					if ( inputState.guessing==0 ) {
						members.addAll(decls);
					}
				}
				else {
					break _loop196;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				checkSetMembers(except, members);
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.popScope();
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
		return except;
	}
	
	public final MInterfaceDef  iface() throws RecognitionException, TokenStreamException {
		MInterfaceDef iface = null;
		
		
		try {      // for error handling
			iface=forward_dcl();
			{
			switch ( LA(1)) {
			case LCURLY:
			case COLON:
			{
				interface_dcl(iface);
				break;
			}
			case SEMI:
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
		return iface;
	}
	
	public final MModuleDef  module() throws RecognitionException, TokenStreamException {
		MModuleDef mod = null;
		
		
		mod = new MModuleDefImpl();
		String id;
		List decls = null;
		List definitions = new ArrayList();
		
		
		try {      // for error handling
			match(LITERAL_module);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				mod = (MModuleDef) verifyNameEmpty(id, mod);
				mod.setSourceFile(getSourceFile());
				mod.setIdentifier(id);
				symbolTable.add(id, mod);
				
			}
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(id);
			}
			{
			_loop13:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					decls=definition();
					if ( inputState.guessing==0 ) {
						definitions.addAll(decls);
					}
				}
				else {
					break _loop13;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				checkAddContents(mod, definitions);
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.popScope();
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
		return mod;
	}
	
	public final MContained  value() throws RecognitionException, TokenStreamException {
		MContained val = null;
		
		
		try {      // for error handling
			boolean synPredMatched35 = false;
			if (((LA(1)==LITERAL_abstract) && (LA(2)==LITERAL_valuetype))) {
				int _m35 = mark();
				synPredMatched35 = true;
				inputState.guessing++;
				try {
					{
					value_abs_dcl();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched35 = false;
				}
				rewind(_m35);
				inputState.guessing--;
			}
			if ( synPredMatched35 ) {
				val=value_abs_dcl();
			}
			else {
				boolean synPredMatched37 = false;
				if (((LA(1)==LITERAL_valuetype||LA(1)==LITERAL_custom) && (LA(2)==LITERAL_valuetype||LA(2)==IDENT))) {
					int _m37 = mark();
					synPredMatched37 = true;
					inputState.guessing++;
					try {
						{
						value_dcl();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched37 = false;
					}
					rewind(_m37);
					inputState.guessing--;
				}
				if ( synPredMatched37 ) {
					val=value_dcl();
				}
				else {
					boolean synPredMatched39 = false;
					if (((LA(1)==LITERAL_valuetype) && (LA(2)==IDENT))) {
						int _m39 = mark();
						synPredMatched39 = true;
						inputState.guessing++;
						try {
							{
							value_box_dcl();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched39 = false;
						}
						rewind(_m39);
						inputState.guessing--;
					}
					if ( synPredMatched39 ) {
						val=value_box_dcl();
					}
					else if ((LA(1)==LITERAL_abstract||LA(1)==LITERAL_valuetype) && (LA(2)==LITERAL_valuetype||LA(2)==IDENT)) {
						val=value_forward_dcl();
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}
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
				return val;
			}
			
	public final void type_id_dcl() throws RecognitionException, TokenStreamException {
		
		String scope = null; String id = null;
		
		try {      // for error handling
			match(LITERAL_typeid);
			scope=scoped_name();
			id=string_literal();
			if ( inputState.guessing==0 ) {
				scopeTable.put(id, scope);
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
	}
	
	public final void type_prefix_dcl() throws RecognitionException, TokenStreamException {
		
		String scope = null; String id = null;
		
		try {      // for error handling
			match(LITERAL_typeprefix);
			scope=scoped_name();
			id=string_literal();
			if ( inputState.guessing==0 ) {
				scopeTable.put(id, scope);
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
	}
	
	public final MEventDef  event() throws RecognitionException, TokenStreamException {
		MEventDef event = null;
		
		
		try {      // for error handling
			boolean synPredMatched283 = false;
			if (((LA(1)==LITERAL_custom||LA(1)==LITERAL_eventtype) && (LA(2)==LITERAL_eventtype||LA(2)==IDENT))) {
				int _m283 = mark();
				synPredMatched283 = true;
				inputState.guessing++;
				try {
					{
					event_dcl();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched283 = false;
				}
				rewind(_m283);
				inputState.guessing--;
			}
			if ( synPredMatched283 ) {
				event=event_dcl();
			}
			else {
				boolean synPredMatched285 = false;
				if (((LA(1)==LITERAL_abstract) && (LA(2)==LITERAL_eventtype))) {
					int _m285 = mark();
					synPredMatched285 = true;
					inputState.guessing++;
					try {
						{
						event_abs_dcl();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched285 = false;
					}
					rewind(_m285);
					inputState.guessing--;
				}
				if ( synPredMatched285 ) {
					event=event_abs_dcl();
				}
				else if ((LA(1)==LITERAL_abstract||LA(1)==LITERAL_eventtype) && (LA(2)==LITERAL_eventtype||LA(2)==IDENT)) {
					event=event_forward_dcl();
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
			return event;
		}
		
	public final MComponentDef  component() throws RecognitionException, TokenStreamException {
		MComponentDef component = null;
		
		
		try {      // for error handling
			boolean synPredMatched243 = false;
			if (((LA(1)==LITERAL_component) && (LA(2)==IDENT))) {
				int _m243 = mark();
				synPredMatched243 = true;
				inputState.guessing++;
				try {
					{
					component_dcl();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched243 = false;
				}
				rewind(_m243);
				inputState.guessing--;
			}
			if ( synPredMatched243 ) {
				component=component_dcl();
			}
			else if ((LA(1)==LITERAL_component) && (LA(2)==IDENT)) {
				component=component_forward_dcl();
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
		return component;
	}
	
	public final MHomeDef  home_dcl() throws RecognitionException, TokenStreamException {
		MHomeDef home = null;
		
		
		try {      // for error handling
			home=home_header();
			home_body(home);
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
		return home;
	}
	
	public final String  identifier() throws RecognitionException, TokenStreamException {
		String identifier = null;
		
		Token  i = null;
		
		try {      // for error handling
			i = LT(1);
			match(IDENT);
			if ( inputState.guessing==0 ) {
				identifier = i.getText();
			}
			if (!( checkKeyword(identifier) ))
			  throw new SemanticException(" checkKeyword(identifier) ");
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
		return identifier;
	}
	
	public final MInterfaceDef  forward_dcl() throws RecognitionException, TokenStreamException {
		MInterfaceDef iface = null;
		
		iface = new MInterfaceDefImpl(); String id = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_abstract:
			{
				match(LITERAL_abstract);
				if ( inputState.guessing==0 ) {
					iface.setAbstract(true);
				}
				break;
			}
			case LITERAL_local:
			{
				match(LITERAL_local);
				if ( inputState.guessing==0 ) {
					iface.setLocal(true);
				}
				break;
			}
			case LITERAL_interface:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_interface);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				iface = (MInterfaceDef) verifyNameEmpty(id, iface);
				iface.setSourceFile(getSourceFile());
				iface.setIdentifier(id);
				iface.setRepositoryId(createRepositoryId(id));
				symbolTable.add(id, iface);
				
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
		return iface;
	}
	
	public final void interface_dcl(
		MInterfaceDef iface
	) throws RecognitionException, TokenStreamException {
		
		List exports = new ArrayList();
		
		try {      // for error handling
			interface_header(iface);
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(iface.getIdentifier());
			}
			exports=interface_body();
			if ( inputState.guessing==0 ) {
				checkAddContents(iface, exports);
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.popScope();
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
	}
	
	public final void interface_header(
		MInterfaceDef iface
	) throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case COLON:
			{
				interface_inheritance_spec(iface);
				break;
			}
			case LCURLY:
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
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
	}
	
	public final List  interface_body() throws RecognitionException, TokenStreamException {
		List exports = null;
		
		exports = new ArrayList(); List decls = null;
		
		try {      // for error handling
			{
			_loop23:
			do {
				if ((_tokenSet_9.member(LA(1)))) {
					decls=export();
					if ( inputState.guessing==0 ) {
						exports.addAll(decls);
					}
				}
				else {
					break _loop23;
				}
				
			} while (true);
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
		return exports;
	}
	
	public final void interface_inheritance_spec(
		MInterfaceDef iface
	) throws RecognitionException, TokenStreamException {
		
		List inherits = new ArrayList(); String name = null;
		
		try {      // for error handling
			match(COLON);
			name=interface_name();
			if ( inputState.guessing==0 ) {
				inherits.add(name);
			}
			{
			_loop27:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					name=interface_name();
					if ( inputState.guessing==0 ) {
						inherits.add(name);
					}
				}
				else {
					break _loop27;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				checkSetBases(iface, inherits);
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
	}
	
	public final List  export() throws RecognitionException, TokenStreamException {
		List exports = null;
		
		exports = new ArrayList(); MContained holder = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_typedef:
			case LITERAL_native:
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			{
				exports=type_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_const:
			{
				holder=const_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					exports.add(holder);
				}
				break;
			}
			case LITERAL_exception:
			{
				holder=except_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					exports.add(holder);
				}
				break;
			}
			case LITERAL_readonly:
			case LITERAL_attribute:
			{
				exports=attr_dcl();
				match(SEMI);
				break;
			}
			case SCOPEOP:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_long:
			case LITERAL_short:
			case LITERAL_unsigned:
			case LITERAL_char:
			case LITERAL_wchar:
			case LITERAL_boolean:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_Object:
			case LITERAL_string:
			case LITERAL_wstring:
			case LITERAL_oneway:
			case LITERAL_void:
			case LITERAL_ValueBase:
			case IDENT:
			{
				holder=op_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					exports.add(holder);
				}
				break;
			}
			case LITERAL_typeid:
			{
				type_id_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_typeprefix:
			{
				type_prefix_dcl();
				match(SEMI);
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
				consumeUntil(_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		return exports;
	}
	
	public final List  attr_dcl() throws RecognitionException, TokenStreamException {
		List attributes = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_readonly:
			{
				attributes=readonly_attr_spec();
				break;
			}
			case LITERAL_attribute:
			{
				attributes=attr_spec();
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
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return attributes;
	}
	
	public final MOperationDef  op_dcl() throws RecognitionException, TokenStreamException {
		MOperationDef operation = null;
		
		
		operation = new MOperationDefImpl();
		boolean oneway = false;
		String context = null;
		List params = null;
		List exceptions = null;
		String id = null;
		MIDLType type = null;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_oneway:
			{
				oneway=op_attribute();
				break;
			}
			case SCOPEOP:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_long:
			case LITERAL_short:
			case LITERAL_unsigned:
			case LITERAL_char:
			case LITERAL_wchar:
			case LITERAL_boolean:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_Object:
			case LITERAL_string:
			case LITERAL_wstring:
			case LITERAL_void:
			case LITERAL_ValueBase:
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			type=op_type_spec();
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				operation = (MOperationDef) verifyNameEmpty(id, operation);
				operation.setSourceFile(getSourceFile());
				operation.setIdentifier(id);
				operation.setIdlType(type);
				operation.setOneway(oneway);
				symbolTable.add(id, operation);
				
			}
			params=parameter_dcls(id);
			if ( inputState.guessing==0 ) {
				checkSetParameters(operation, params);
			}
			{
			switch ( LA(1)) {
			case LITERAL_raises:
			{
				exceptions=raises_expr();
				if ( inputState.guessing==0 ) {
					operation.setExceptionDefs(new HashSet(exceptions));
				}
				break;
			}
			case SEMI:
			case LITERAL_context:
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
			case LITERAL_context:
			{
				context=context_expr();
				if ( inputState.guessing==0 ) {
					operation.setContexts(context);
				}
				break;
			}
			case SEMI:
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
		return operation;
	}
	
	public final String  interface_name() throws RecognitionException, TokenStreamException {
		String name = null;
		
		
		try {      // for error handling
			name=scoped_name();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		return name;
	}
	
	public final String  scoped_name() throws RecognitionException, TokenStreamException {
		String name = null;
		
		Token  s = null;
		Token  c = null;
		String id = null; String global = new String("");
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case SCOPEOP:
			{
				s = LT(1);
				match(SCOPEOP);
				if ( inputState.guessing==0 ) {
					global = s.getText();
				}
				break;
			}
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			id=identifier();
			if ( inputState.guessing==0 ) {
				name = global + id;
			}
			{
			_loop32:
			do {
				if ((LA(1)==SCOPEOP)) {
					c = LT(1);
					match(SCOPEOP);
					id=identifier();
					if ( inputState.guessing==0 ) {
						name += c.getText() + id;
					}
				}
				else {
					break _loop32;
				}
				
			} while (true);
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
		return name;
	}
	
	public final MValueDef  value_abs_dcl() throws RecognitionException, TokenStreamException {
		MValueDef val = null;
		
		
		val = new MValueDefImpl();
		String id = null;
		List decls = null;
		List exports = new ArrayList();
		
		
		try {      // for error handling
			match(LITERAL_abstract);
			if ( inputState.guessing==0 ) {
				val.setAbstract(true);
			}
			match(LITERAL_valuetype);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				val = (MValueDef) verifyNameEmpty(id, val);
				val.setSourceFile(getSourceFile());
				val.setIdentifier(id);
				symbolTable.add(id, val);
				
			}
			value_inheritance_spec(val);
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(id);
			}
			{
			_loop45:
			do {
				if ((_tokenSet_9.member(LA(1)))) {
					decls=export();
					if ( inputState.guessing==0 ) {
						exports.addAll(decls);
					}
				}
				else {
					break _loop45;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				checkAddContents(val, exports);
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.popScope();
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
		return val;
	}
	
	public final MValueDef  value_dcl() throws RecognitionException, TokenStreamException {
		MValueDef val = null;
		
		List elements = new ArrayList(); List decls = null;
		
		try {      // for error handling
			val=value_header();
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(val.getIdentifier());
			}
			{
			_loop48:
			do {
				if ((_tokenSet_14.member(LA(1)))) {
					decls=value_element();
					if ( inputState.guessing==0 ) {
						elements.addAll(decls);
					}
				}
				else {
					break _loop48;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				checkAddContents(val, elements);
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.popScope();
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
		return val;
	}
	
	public final MValueBoxDef  value_box_dcl() throws RecognitionException, TokenStreamException {
		MValueBoxDef valbox = null;
		
		
		valbox = new MValueBoxDefImpl();
		String id = null;
		MIDLType type = null;
		
		
		try {      // for error handling
			match(LITERAL_valuetype);
			id=identifier();
			type=type_spec();
			if ( inputState.guessing==0 ) {
				
				valbox = (MValueBoxDef) verifyNameEmpty(id, valbox);
				valbox.setSourceFile(getSourceFile());
				valbox.setIdentifier(id);
				valbox.setIdlType(type);
				
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
		return valbox;
	}
	
	public final MValueDef  value_forward_dcl() throws RecognitionException, TokenStreamException {
		MValueDef val = null;
		
		val = new MValueDefImpl(); String id = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_abstract:
			{
				match(LITERAL_abstract);
				if ( inputState.guessing==0 ) {
					val.setAbstract(true);
				}
				break;
			}
			case LITERAL_valuetype:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_valuetype);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				val = (MValueDef) verifyNameEmpty(id, val);
				val.setSourceFile(getSourceFile());
				val.setIdentifier(id);
				symbolTable.add(id, val);
				
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
		return val;
	}
	
	public final MIDLType  type_spec() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_long:
			case LITERAL_short:
			case LITERAL_unsigned:
			case LITERAL_char:
			case LITERAL_wchar:
			case LITERAL_boolean:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_Object:
			case LITERAL_sequence:
			case LITERAL_string:
			case LITERAL_wstring:
			case LITERAL_fixed:
			case LITERAL_ValueBase:
			case IDENT:
			{
				type=simple_type_spec();
				break;
			}
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			{
				type=constr_type_spec();
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
				consumeUntil(_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		return type;
	}
	
	public final void value_inheritance_spec(
		MValueDef val
	) throws RecognitionException, TokenStreamException {
		
		String name = null; MValueDef inherited = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				{
				switch ( LA(1)) {
				case LITERAL_truncatable:
				{
					match(LITERAL_truncatable);
					if ( inputState.guessing==0 ) {
						val.setTruncatable(true);
					}
					break;
				}
				case SCOPEOP:
				case IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				name=value_name();
				if ( inputState.guessing==0 ) {
					addValueBase(val, name);
				}
				{
				_loop55:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						name=value_name();
						if ( inputState.guessing==0 ) {
							addValueBase(val, name);
						}
					}
					else {
						break _loop55;
					}
					
				} while (true);
				}
				break;
			}
			case LCURLY:
			case LITERAL_supports:
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
			case LITERAL_supports:
			{
				match(LITERAL_supports);
				name=interface_name();
				if ( inputState.guessing==0 ) {
					addValueSupports(val, name);
				}
				{
				_loop58:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						name=interface_name();
						if ( inputState.guessing==0 ) {
							addValueSupports(val, name);
						}
					}
					else {
						break _loop58;
					}
					
				} while (true);
				}
				break;
			}
			case LCURLY:
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
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
	}
	
	public final MValueDef  value_header() throws RecognitionException, TokenStreamException {
		MValueDef val = null;
		
		val = new MValueDefImpl(); String id = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_custom:
			{
				match(LITERAL_custom);
				if ( inputState.guessing==0 ) {
					val.setCustom(true);
				}
				break;
			}
			case LITERAL_valuetype:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_valuetype);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				val = (MValueDef) verifyNameEmpty(id, val);
				val.setSourceFile(getSourceFile());
				val.setIdentifier(id);
				symbolTable.add(id, val);
				
			}
			value_inheritance_spec(val);
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
		return val;
	}
	
	public final List  value_element() throws RecognitionException, TokenStreamException {
		List elements = null;
		
		elements = new ArrayList(); MContained holder = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case LITERAL_const:
			case LITERAL_typedef:
			case LITERAL_native:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_long:
			case LITERAL_short:
			case LITERAL_unsigned:
			case LITERAL_char:
			case LITERAL_wchar:
			case LITERAL_boolean:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_Object:
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			case LITERAL_string:
			case LITERAL_wstring:
			case LITERAL_exception:
			case LITERAL_oneway:
			case LITERAL_void:
			case LITERAL_ValueBase:
			case LITERAL_typeid:
			case LITERAL_typeprefix:
			case LITERAL_readonly:
			case LITERAL_attribute:
			case IDENT:
			{
				elements=export();
				break;
			}
			case LITERAL_public:
			case LITERAL_private:
			{
				elements=state_member();
				break;
			}
			case LITERAL_factory:
			{
				holder=init_dcl();
				if ( inputState.guessing==0 ) {
					elements.add(holder);
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
				consumeUntil(_tokenSet_16);
			} else {
			  throw ex;
			}
		}
		return elements;
	}
	
	public final String  value_name() throws RecognitionException, TokenStreamException {
		String name = null;
		
		
		try {      // for error handling
			name=scoped_name();
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
		return name;
	}
	
	public final List  state_member() throws RecognitionException, TokenStreamException {
		List members = null;
		
		
		members = new ArrayList();
		List decls = null;
		MIDLType type = null;
		boolean isPublic = false;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_public:
			{
				match(LITERAL_public);
				if ( inputState.guessing==0 ) {
					isPublic = true;
				}
				break;
			}
			case LITERAL_private:
			{
				match(LITERAL_private);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			type=type_spec();
			decls=declarators();
			if ( inputState.guessing==0 ) {
				
				for (Iterator it = decls.iterator(); it.hasNext(); ) {
				DeclaratorHelper declarator = (DeclaratorHelper) it.next();
				String id = declarator.getDeclarator();
				
				if(declarator.isArray()) {
				MArrayDef array = declarator.getArray();
				array.setIdlType(type);
				type = (MIDLType) array;
				}
				
				MValueMemberDef member = new MValueMemberDefImpl();
				member.setIdlType(type);
				member.setIdentifier(id);
				member.setPublicMember(isPublic);
				members.add(member);
				}
				
			}
			match(SEMI);
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
		return members;
	}
	
	public final MFactoryDef  init_dcl() throws RecognitionException, TokenStreamException {
		MFactoryDef factory = null;
		
		
		factory = new MFactoryDefImpl();
		String id;
		List params = null;
		List excepts = null;
		
		
		try {      // for error handling
			match(LITERAL_factory);
			id=identifier();
			if ( inputState.guessing==0 ) {
				factory.setIdentifier(id); symbolTable.add(id, factory);
			}
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LITERAL_in:
			{
				params=init_param_decls();
				if ( inputState.guessing==0 ) {
					checkSetParameters((MOperationDef) factory, params);
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
			case LITERAL_raises:
			{
				excepts=raises_expr();
				if ( inputState.guessing==0 ) {
					checkSetExceptions((MOperationDef) factory, excepts);
				}
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(SEMI);
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
		return factory;
	}
	
	public final List  declarators() throws RecognitionException, TokenStreamException {
		List declarations = null;
		
		declarations = new ArrayList(); DeclaratorHelper helper;
		
		try {      // for error handling
			helper=declarator();
			if ( inputState.guessing==0 ) {
				declarations.add(helper);
			}
			{
			_loop126:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					helper=declarator();
					if ( inputState.guessing==0 ) {
						declarations.add(helper);
					}
				}
				else {
					break _loop126;
				}
				
			} while (true);
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
		return declarations;
	}
	
	public final List  init_param_decls() throws RecognitionException, TokenStreamException {
		List params = null;
		
		params = new ArrayList(); MParameterDef parameter;
		
		try {      // for error handling
			parameter=init_param_decl();
			if ( inputState.guessing==0 ) {
				params.add(parameter);
			}
			{
			_loop68:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					parameter=init_param_decl();
					if ( inputState.guessing==0 ) {
						params.add(parameter);
					}
				}
				else {
					break _loop68;
				}
				
			} while (true);
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
		return params;
	}
	
	public final List  raises_expr() throws RecognitionException, TokenStreamException {
		List exceptions = null;
		
		exceptions = new ArrayList(); String name = null;
		
		try {      // for error handling
			match(LITERAL_raises);
			match(LPAREN);
			name=scoped_name();
			if ( inputState.guessing==0 ) {
				
				MExceptionDef exception = new MExceptionDefImpl();
				exception = (MExceptionDef) verifyNameExists(name, exception);
				exceptions.add(exception);
				
			}
			{
			_loop211:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					name=scoped_name();
					if ( inputState.guessing==0 ) {
						
						MExceptionDef exception = new MExceptionDefImpl();
						exception = (MExceptionDef) verifyNameExists(name, exception);
						exceptions.add(exception);
						
					}
				}
				else {
					break _loop211;
				}
				
			} while (true);
			}
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return exceptions;
	}
	
	public final MParameterDef  init_param_decl() throws RecognitionException, TokenStreamException {
		MParameterDef param = null;
		
		
		param = new MParameterDefImpl();
		MIDLType type = null;
		String name = null;
		
		
		try {      // for error handling
			init_param_attribute();
			if ( inputState.guessing==0 ) {
				param.setDirection(MParameterMode.PARAM_IN);
			}
			type=param_type_spec();
			if ( inputState.guessing==0 ) {
				param.setIdlType(type);
			}
			name=simple_declarator();
			if ( inputState.guessing==0 ) {
				param.setIdentifier(name);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_20);
			} else {
			  throw ex;
			}
		}
		return param;
	}
	
	public final void init_param_attribute() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_in);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_21);
			} else {
			  throw ex;
			}
		}
	}
	
	public final MIDLType  param_type_spec() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		String name = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_long:
			case LITERAL_short:
			case LITERAL_unsigned:
			case LITERAL_char:
			case LITERAL_wchar:
			case LITERAL_boolean:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_Object:
			case LITERAL_ValueBase:
			{
				type=base_type_spec();
				break;
			}
			case LITERAL_string:
			{
				type=string_type();
				break;
			}
			case LITERAL_wstring:
			{
				type=wide_string_type();
				break;
			}
			case SCOPEOP:
			case IDENT:
			{
				name=scoped_name();
				if ( inputState.guessing==0 ) {
					type = getIDLType(name);
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
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return type;
	}
	
	public final String  simple_declarator() throws RecognitionException, TokenStreamException {
		String name = null;
		
		
		try {      // for error handling
			name=identifier();
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
		return name;
	}
	
	public final MIDLType  const_type() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		String name = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_char:
			{
				type=char_type();
				break;
			}
			case LITERAL_wchar:
			{
				type=wide_char_type();
				break;
			}
			case LITERAL_boolean:
			{
				type=boolean_type();
				break;
			}
			case LITERAL_string:
			{
				type=string_type();
				break;
			}
			case LITERAL_wstring:
			{
				type=wide_string_type();
				break;
			}
			case LITERAL_fixed:
			{
				type=fixed_pt_const_type();
				break;
			}
			case SCOPEOP:
			case IDENT:
			{
				name=scoped_name();
				if ( inputState.guessing==0 ) {
					type = getIDLType(name);
				}
				break;
			}
			case LITERAL_octet:
			{
				type=octet_type();
				break;
			}
			default:
				boolean synPredMatched74 = false;
				if ((((LA(1) >= LITERAL_long && LA(1) <= LITERAL_unsigned)) && (LA(2)==LITERAL_long||LA(2)==LITERAL_short||LA(2)==IDENT))) {
					int _m74 = mark();
					synPredMatched74 = true;
					inputState.guessing++;
					try {
						{
						integer_type();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched74 = false;
					}
					rewind(_m74);
					inputState.guessing--;
				}
				if ( synPredMatched74 ) {
					type=integer_type();
				}
				else {
					boolean synPredMatched76 = false;
					if ((((LA(1) >= LITERAL_float && LA(1) <= LITERAL_long)) && (LA(2)==LITERAL_double||LA(2)==IDENT))) {
						int _m76 = mark();
						synPredMatched76 = true;
						inputState.guessing++;
						try {
							{
							floating_pt_type();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched76 = false;
						}
						rewind(_m76);
						inputState.guessing--;
					}
					if ( synPredMatched76 ) {
						type=floating_pt_type();
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
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
			return type;
		}
		
	public final String  const_exp() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		
		try {      // for error handling
			expr=or_expr();
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
		return expr;
	}
	
	public final MIDLType  integer_type() throws RecognitionException, TokenStreamException {
		MIDLType number = null;
		
		number = new MPrimitiveDefImpl();
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_long:
			case LITERAL_short:
			{
				number=signed_int();
				break;
			}
			case LITERAL_unsigned:
			{
				number=unsigned_int();
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
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return number;
	}
	
	public final MIDLType  char_type() throws RecognitionException, TokenStreamException {
		MIDLType ch = null;
		
		ch = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_char);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) ch).setKind(MPrimitiveKind.PK_CHAR);
			}
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
		return ch;
	}
	
	public final MIDLType  wide_char_type() throws RecognitionException, TokenStreamException {
		MIDLType wch = null;
		
		wch = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_wchar);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) wch).setKind(MPrimitiveKind.PK_WCHAR);
			}
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
		return wch;
	}
	
	public final MIDLType  boolean_type() throws RecognitionException, TokenStreamException {
		MIDLType bool = null;
		
		bool = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_boolean);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) bool).setKind(MPrimitiveKind.PK_BOOLEAN);
			}
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
		return bool;
	}
	
	public final MIDLType  floating_pt_type() throws RecognitionException, TokenStreamException {
		MIDLType number = null;
		
		number = new MPrimitiveDefImpl();
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_float:
			{
				match(LITERAL_float);
				if ( inputState.guessing==0 ) {
					((MPrimitiveDef) number).setKind(MPrimitiveKind.PK_FLOAT);
				}
				break;
			}
			case LITERAL_double:
			{
				match(LITERAL_double);
				if ( inputState.guessing==0 ) {
					((MPrimitiveDef) number).setKind(MPrimitiveKind.PK_DOUBLE);
				}
				break;
			}
			case LITERAL_long:
			{
				match(LITERAL_long);
				match(LITERAL_double);
				if ( inputState.guessing==0 ) {
					((MPrimitiveDef) number).setKind(MPrimitiveKind.PK_LONGDOUBLE);
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
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		return number;
	}
	
	public final MIDLType  string_type() throws RecognitionException, TokenStreamException {
		MIDLType str = null;
		
		str = new MStringDefImpl(); String bound = null;
		
		try {      // for error handling
			match(LITERAL_string);
			{
			switch ( LA(1)) {
			case LT:
			{
				match(LT);
				bound=positive_int_const();
				match(GT);
				if ( inputState.guessing==0 ) {
					((MStringDef) str).setBound(new Long(bound));
				}
				break;
			}
			case SEMI:
			case COMMA:
			case GT:
			case IDENT:
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
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		return str;
	}
	
	public final MIDLType  wide_string_type() throws RecognitionException, TokenStreamException {
		MIDLType wstr = null;
		
		wstr = new MWstringDefImpl(); String bound = null;
		
		try {      // for error handling
			match(LITERAL_wstring);
			{
			switch ( LA(1)) {
			case LT:
			{
				match(LT);
				bound=positive_int_const();
				match(GT);
				if ( inputState.guessing==0 ) {
					((MWstringDef) wstr).setBound(new Long(bound));
				}
				break;
			}
			case SEMI:
			case COMMA:
			case GT:
			case IDENT:
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
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		return wstr;
	}
	
	public final MIDLType  fixed_pt_const_type() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		type = new MFixedDefImpl();
		
		try {      // for error handling
			match(LITERAL_fixed);
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
		return type;
	}
	
	public final MIDLType  octet_type() throws RecognitionException, TokenStreamException {
		MIDLType octet = null;
		
		octet = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_octet);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) octet).setKind(MPrimitiveKind.PK_OCTET);
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
		return octet;
	}
	
	public final String  or_expr() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		String right = null;
		
		try {      // for error handling
			expr=xor_expr();
			{
			switch ( LA(1)) {
			case OR:
			{
				match(OR);
				right=or_expr();
				if ( inputState.guessing==0 ) {
					
					Long a = checkLong(expr); Long b = checkLong(right);
					long result = a.longValue() | b.longValue();
					expr = "" + result;
					
				}
				break;
			}
			case SEMI:
			case COLON:
			case COMMA:
			case RPAREN:
			case GT:
			case RBRACK:
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
				consumeUntil(_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final String  xor_expr() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		String right = null;
		
		try {      // for error handling
			expr=and_expr();
			{
			switch ( LA(1)) {
			case XOR:
			{
				match(XOR);
				right=xor_expr();
				if ( inputState.guessing==0 ) {
					
					Long a = checkLong(expr); Long b = checkLong(right);
					long result = a.longValue() ^ b.longValue();
					expr = "" + result;
					
				}
				break;
			}
			case SEMI:
			case COLON:
			case COMMA:
			case RPAREN:
			case OR:
			case GT:
			case RBRACK:
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
				consumeUntil(_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final String  and_expr() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		String right = null;
		
		try {      // for error handling
			expr=shift_expr();
			{
			switch ( LA(1)) {
			case AND:
			{
				match(AND);
				right=and_expr();
				if ( inputState.guessing==0 ) {
					
					Long a = checkLong(expr); Long b = checkLong(right);
					long result = a.longValue() & b.longValue();
					expr = "" + result;
					
				}
				break;
			}
			case SEMI:
			case COLON:
			case COMMA:
			case RPAREN:
			case OR:
			case XOR:
			case GT:
			case RBRACK:
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
				consumeUntil(_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final String  shift_expr() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		String right = null; boolean goLeft = false;
		
		try {      // for error handling
			expr=add_expr();
			{
			switch ( LA(1)) {
			case LSHIFT:
			case RSHIFT:
			{
				{
				switch ( LA(1)) {
				case LSHIFT:
				{
					match(LSHIFT);
					if ( inputState.guessing==0 ) {
						goLeft = true;
					}
					break;
				}
				case RSHIFT:
				{
					match(RSHIFT);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				right=shift_expr();
				if ( inputState.guessing==0 ) {
					
					Long a = checkLong(expr); Long b = checkLong(right);
					long result = a.longValue() >> b.longValue();
					if (goLeft) { result = a.longValue() << b.longValue(); }
					expr = "" + result;
					
				}
				break;
			}
			case SEMI:
			case COLON:
			case COMMA:
			case RPAREN:
			case OR:
			case XOR:
			case AND:
			case GT:
			case RBRACK:
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
				consumeUntil(_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final String  add_expr() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		String right = null; boolean add = false;
		
		try {      // for error handling
			expr=mult_expr();
			{
			switch ( LA(1)) {
			case PLUS:
			case MINUS:
			{
				{
				switch ( LA(1)) {
				case PLUS:
				{
					match(PLUS);
					if ( inputState.guessing==0 ) {
						add = true;
					}
					break;
				}
				case MINUS:
				{
					match(MINUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				right=add_expr();
				if ( inputState.guessing==0 ) {
					
					Number a, b, result;
					// try to evaluate the expression as an integer first, if that
					// fails (because the numbers are floats) then try using float
					// math instead.
					try {
					a = new Long(expr); b = new Long(right);
					result = new Long(a.longValue() - b.longValue());
					if (add) { result = new Long(a.longValue() + b.longValue()); }
					} catch (NumberFormatException e) {
					a = checkFloat(expr); b = checkFloat(right);
					result = new Float(a.floatValue() - b.floatValue());
					if (add) { result = new Float(a.floatValue() + b.floatValue()); }
					}
					expr = result.toString();
					
				}
				break;
			}
			case SEMI:
			case COLON:
			case COMMA:
			case RPAREN:
			case OR:
			case XOR:
			case AND:
			case LSHIFT:
			case RSHIFT:
			case GT:
			case RBRACK:
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
				consumeUntil(_tokenSet_30);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final String  mult_expr() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		String right = null; int op = 0;
		
		try {      // for error handling
			expr=unary_expr();
			{
			switch ( LA(1)) {
			case MOD:
			case STAR:
			case DIV:
			{
				{
				switch ( LA(1)) {
				case MOD:
				{
					match(MOD);
					break;
				}
				case STAR:
				{
					match(STAR);
					if ( inputState.guessing==0 ) {
						op = 1;
					}
					break;
				}
				case DIV:
				{
					match(DIV);
					if ( inputState.guessing==0 ) {
						op = 2;
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				right=mult_expr();
				if ( inputState.guessing==0 ) {
					
					if (op == 0) {
					Long a = checkLong(expr); Long b = checkLong(right);
					long result = a.longValue() % b.longValue();
					expr = "" + result;
					} else {
					// try to evaluate the expression as an integer first. if
					// that fails, try using float math instead.
					try {
					Long a = new Long(expr); Long b = new Long(right);
					long result = a.longValue() * b.longValue();
					if (op == 2) { result = a.longValue() / b.longValue(); }
					expr = "" + result;
					} catch (NumberFormatException e) {
					Float a = checkFloat(expr); Float b = checkFloat(right);
					float result = a.floatValue() * b.floatValue();
					if (op == 2) { result = a.floatValue() / b.floatValue(); }
					expr = "" + result;
					}
					}
					
				}
				break;
			}
			case SEMI:
			case COLON:
			case COMMA:
			case RPAREN:
			case OR:
			case XOR:
			case AND:
			case LSHIFT:
			case RSHIFT:
			case PLUS:
			case MINUS:
			case GT:
			case RBRACK:
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
				consumeUntil(_tokenSet_31);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final String  unary_expr() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		String op = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			case MINUS:
			case TILDE:
			{
				op=unary_operator();
				expr=primary_expr();
				if ( inputState.guessing==0 ) {
					
					if (op.equals("~")) {
					Long a = checkLong(expr);
					long result = ~ a.longValue();
					expr = "" + result;
					} else {
					expr = op + expr;
					}
					
				}
				break;
			}
			case SCOPEOP:
			case LPAREN:
			case LITERAL_TRUE:
			case LITERAL_FALSE:
			case INT:
			case OCTAL:
			case HEX:
			case STRING_LITERAL:
			case WIDE_STRING_LITERAL:
			case CHAR_LITERAL:
			case WIDE_CHAR_LITERAL:
			case FLOAT:
			case IDENT:
			{
				expr=primary_expr();
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
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final String  unary_operator() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		Token  m = null;
		Token  p = null;
		Token  t = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case MINUS:
			{
				m = LT(1);
				match(MINUS);
				if ( inputState.guessing==0 ) {
					expr = m.getText();
				}
				break;
			}
			case PLUS:
			{
				p = LT(1);
				match(PLUS);
				if ( inputState.guessing==0 ) {
					expr = p.getText();
				}
				break;
			}
			case TILDE:
			{
				t = LT(1);
				match(TILDE);
				if ( inputState.guessing==0 ) {
					expr = t.getText();
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
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final String  primary_expr() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case IDENT:
			{
				expr=scoped_name();
				if ( inputState.guessing==0 ) {
					
					MConstantDef constant = new MConstantDefImpl();
					constant = (MConstantDef) verifyNameExists(expr, constant);
					expr = constant.getConstValue().toString();
					
				}
				break;
			}
			case LITERAL_TRUE:
			case LITERAL_FALSE:
			case INT:
			case OCTAL:
			case HEX:
			case STRING_LITERAL:
			case WIDE_STRING_LITERAL:
			case CHAR_LITERAL:
			case WIDE_CHAR_LITERAL:
			case FLOAT:
			{
				expr=literal();
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				expr=const_exp();
				match(RPAREN);
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
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final String  literal() throws RecognitionException, TokenStreamException {
		String literal = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case INT:
			case OCTAL:
			case HEX:
			{
				literal=integer_literal();
				break;
			}
			case STRING_LITERAL:
			{
				literal=string_literal();
				break;
			}
			case WIDE_STRING_LITERAL:
			{
				literal=wide_string_literal();
				break;
			}
			case CHAR_LITERAL:
			{
				literal=character_literal();
				break;
			}
			case WIDE_CHAR_LITERAL:
			{
				literal=wide_character_literal();
				break;
			}
			case LITERAL_TRUE:
			case LITERAL_FALSE:
			{
				literal=boolean_literal();
				break;
			}
			default:
				boolean synPredMatched102 = false;
				if (((LA(1)==FLOAT) && (LA(2)==LITERAL_d||LA(2)==LITERAL_D))) {
					int _m102 = mark();
					synPredMatched102 = true;
					inputState.guessing++;
					try {
						{
						fixed_pt_literal();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched102 = false;
					}
					rewind(_m102);
					inputState.guessing--;
				}
				if ( synPredMatched102 ) {
					literal=fixed_pt_literal();
				}
				else {
					boolean synPredMatched104 = false;
					if (((LA(1)==FLOAT) && (_tokenSet_32.member(LA(2))))) {
						int _m104 = mark();
						synPredMatched104 = true;
						inputState.guessing++;
						try {
							{
							floating_pt_literal();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched104 = false;
						}
						rewind(_m104);
						inputState.guessing--;
					}
					if ( synPredMatched104 ) {
						literal=floating_pt_literal();
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					consume();
					consumeUntil(_tokenSet_32);
				} else {
				  throw ex;
				}
			}
			return literal;
		}
		
	public final String  integer_literal() throws RecognitionException, TokenStreamException {
		String literal = null;
		
		Token  i = null;
		Token  o = null;
		Token  h = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case INT:
			{
				i = LT(1);
				match(INT);
				if ( inputState.guessing==0 ) {
					literal = i.getText();
				}
				break;
			}
			case OCTAL:
			{
				o = LT(1);
				match(OCTAL);
				if ( inputState.guessing==0 ) {
					
					Integer value = new Integer(Integer.parseInt(o.getText(), 8));
					literal = value.toString();
					
				}
				break;
			}
			case HEX:
			{
				h = LT(1);
				match(HEX);
				if ( inputState.guessing==0 ) {
					
					Integer value = new Integer(
					Integer.parseInt(o.getText().substring(2), 16));
					literal = value.toString();
					
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
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		return literal;
	}
	
	public final String  string_literal() throws RecognitionException, TokenStreamException {
		String literal = null;
		
		Token  s = null;
		literal = new String();
		
		try {      // for error handling
			{
			int _cnt299=0;
			_loop299:
			do {
				if ((LA(1)==STRING_LITERAL)) {
					s = LT(1);
					match(STRING_LITERAL);
					if ( inputState.guessing==0 ) {
						literal += s.getText();
					}
				}
				else {
					if ( _cnt299>=1 ) { break _loop299; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt299++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		return literal;
	}
	
	public final String  wide_string_literal() throws RecognitionException, TokenStreamException {
		String literal = null;
		
		Token  ws = null;
		literal = new String();
		
		try {      // for error handling
			{
			int _cnt302=0;
			_loop302:
			do {
				if ((LA(1)==WIDE_STRING_LITERAL)) {
					ws = LT(1);
					match(WIDE_STRING_LITERAL);
					if ( inputState.guessing==0 ) {
						literal += ws.getText();
					}
				}
				else {
					if ( _cnt302>=1 ) { break _loop302; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt302++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		return literal;
	}
	
	public final String  character_literal() throws RecognitionException, TokenStreamException {
		String literal = null;
		
		Token  c = null;
		
		try {      // for error handling
			c = LT(1);
			match(CHAR_LITERAL);
			if ( inputState.guessing==0 ) {
				literal = c.getText();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		return literal;
	}
	
	public final String  wide_character_literal() throws RecognitionException, TokenStreamException {
		String literal = null;
		
		Token  wc = null;
		
		try {      // for error handling
			wc = LT(1);
			match(WIDE_CHAR_LITERAL);
			if ( inputState.guessing==0 ) {
				literal = wc.getText();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		return literal;
	}
	
	public final String  fixed_pt_literal() throws RecognitionException, TokenStreamException {
		String literal = null;
		
		Token  f = null;
		Token  F = null;
		
		try {      // for error handling
			boolean synPredMatched308 = false;
			if (((LA(1)==FLOAT) && (LA(2)==LITERAL_d))) {
				int _m308 = mark();
				synPredMatched308 = true;
				inputState.guessing++;
				try {
					{
					match(FLOAT);
					match(LITERAL_d);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched308 = false;
				}
				rewind(_m308);
				inputState.guessing--;
			}
			if ( synPredMatched308 ) {
				f = LT(1);
				match(FLOAT);
				if ( inputState.guessing==0 ) {
					literal = f.getText();
				}
				match(LITERAL_d);
			}
			else {
				boolean synPredMatched310 = false;
				if (((LA(1)==FLOAT) && (LA(2)==LITERAL_D))) {
					int _m310 = mark();
					synPredMatched310 = true;
					inputState.guessing++;
					try {
						{
						match(FLOAT);
						match(LITERAL_D);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched310 = false;
					}
					rewind(_m310);
					inputState.guessing--;
				}
				if ( synPredMatched310 ) {
					F = LT(1);
					match(FLOAT);
					if ( inputState.guessing==0 ) {
						literal = F.getText();
					}
					match(LITERAL_D);
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
					consumeUntil(_tokenSet_32);
				} else {
				  throw ex;
				}
			}
			return literal;
		}
		
	public final String  floating_pt_literal() throws RecognitionException, TokenStreamException {
		String literal = null;
		
		Token  f = null;
		
		try {      // for error handling
			f = LT(1);
			match(FLOAT);
			if ( inputState.guessing==0 ) {
				literal = f.getText();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		return literal;
	}
	
	public final String  boolean_literal() throws RecognitionException, TokenStreamException {
		String bool = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_TRUE:
			{
				match(LITERAL_TRUE);
				if ( inputState.guessing==0 ) {
					bool = new String("TRUE");
				}
				break;
			}
			case LITERAL_FALSE:
			{
				match(LITERAL_FALSE);
				if ( inputState.guessing==0 ) {
					bool = new String("FALSE");
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
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		return bool;
	}
	
	public final String  positive_int_const() throws RecognitionException, TokenStreamException {
		String expr = null;
		
		
		try {      // for error handling
			expr=const_exp();
			if ( inputState.guessing==0 ) {
				checkPositive(expr);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final List  type_declarator() throws RecognitionException, TokenStreamException {
		List declarations = null;
		
		
		declarations = new ArrayList();
		MIDLType type = null;
		List decls = null;
		
		
		try {      // for error handling
			type=type_spec();
			decls=declarators();
			if ( inputState.guessing==0 ) {
				
				for (Iterator it = decls.iterator(); it.hasNext(); ) {
				DeclaratorHelper declarator = (DeclaratorHelper) it.next();
				String id = declarator.getDeclarator();
				
				if (declarator.isArray()) {
				MArrayDef array = declarator.getArray();
				array.setIdlType(type);
				type = (MIDLType) array;
				}
				
				MAliasDef alias = new MAliasDefImpl();
				alias.setIdlType(type);
				alias.setIdentifier(id);
				alias.setSourceFile(getSourceFile());
				declarations.add(alias);
				
				symbolTable.add(id, alias);
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
		return declarations;
	}
	
	public final MIDLType  struct_type() throws RecognitionException, TokenStreamException {
		MIDLType struct = null;
		
		
		struct = new MStructDefImpl();
		String id = null;
		List members = null;
		
		
		try {      // for error handling
			match(LITERAL_struct);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				struct = (MStructDef) verifyNameEmpty(id, (MStructDef) struct);
				((MStructDef) struct).setSourceFile(getSourceFile());
				((MStructDef) struct).setIdentifier(id);
				symbolTable.add(id, (MStructDef) struct);
				
			}
			match(LCURLY);
			members=member_list();
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				checkSetMembers((MStructDef) struct, members);
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
		return struct;
	}
	
	public final MIDLType  union_type() throws RecognitionException, TokenStreamException {
		MIDLType union = null;
		
		
		union = new MUnionDefImpl();
		String id = null;
		MIDLType type = null;
		List members = null;
		
		
		try {      // for error handling
			match(LITERAL_union);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				union = (MUnionDef) verifyNameEmpty(id, (MUnionDef) union);
				((MUnionDef) union).setSourceFile(getSourceFile());
				((MUnionDef) union).setIdentifier(id);
				symbolTable.add(id, (MUnionDef) union);
				
			}
			match(LITERAL_switch);
			match(LPAREN);
			type=switch_type_spec();
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				((MUnionDef) union).setDiscriminatorType(type);
			}
			match(LCURLY);
			members=switch_body(type);
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				checkSetMembers((MUnionDef) union, members);
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
		return union;
	}
	
	public final MIDLType  enum_type() throws RecognitionException, TokenStreamException {
		MIDLType enumDef = null;
		
		
		enumDef = new MEnumDefImpl();
		List members = new ArrayList();
		String name = null;
		String id = null;
		
		
		try {      // for error handling
			match(LITERAL_enum);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				enumDef = (MEnumDef) verifyNameEmpty(id, (MEnumDef) enumDef);
				((MEnumDef) enumDef).setSourceFile(getSourceFile());
				((MEnumDef) enumDef).setIdentifier(id);
				symbolTable.add(id, (MEnumDef) enumDef);
				
			}
			match(LCURLY);
			name=enumerator();
			if ( inputState.guessing==0 ) {
				if (name != null) members.add(name); name = null;
			}
			{
			_loop181:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					name=enumerator();
					if ( inputState.guessing==0 ) {
						if (name != null) members.add(name); name = null;
					}
				}
				else {
					break _loop181;
				}
				
			} while (true);
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				
				if (members.size() == 0)
				throw new TokenStreamException("enum '"+id+"' is empty");
				
				Object[] membs = members.toArray();
				for (int i = 0; i < membs.length; i++) {
				String m = membs[i].toString();
				for (int j = i+1; j < membs.length; j++)
				if (m.equals(membs[j].toString()))
				throw new TokenStreamException("repeated member '"+m+"' in enum '"+id+"'");
				}
				
				((MEnumDef) enumDef).setMembers(members);
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_35);
			} else {
			  throw ex;
			}
		}
		return enumDef;
	}
	
	public final MIDLType  constr_forward_decl() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		String id = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_struct:
			{
				match(LITERAL_struct);
				id=identifier();
				if ( inputState.guessing==0 ) {
					
					MStructDef struct = new MStructDefImpl();
					struct = (MStructDef) verifyNameEmpty(id, struct);
					struct.setSourceFile(getSourceFile());
					symbolTable.add(id, struct);
					type = (MIDLType) struct;
					
				}
				break;
			}
			case LITERAL_union:
			{
				match(LITERAL_union);
				id=identifier();
				if ( inputState.guessing==0 ) {
					
					MUnionDef union = new MUnionDefImpl();
					union = (MUnionDef) verifyNameEmpty(id, union);
					union.setSourceFile(getSourceFile());
					symbolTable.add(id, union);
					type = (MIDLType) union;
					
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
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return type;
	}
	
	public final MIDLType  simple_type_spec() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		String name = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_long:
			case LITERAL_short:
			case LITERAL_unsigned:
			case LITERAL_char:
			case LITERAL_wchar:
			case LITERAL_boolean:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_Object:
			case LITERAL_ValueBase:
			{
				type=base_type_spec();
				break;
			}
			case LITERAL_sequence:
			case LITERAL_string:
			case LITERAL_wstring:
			case LITERAL_fixed:
			{
				type=template_type_spec();
				break;
			}
			case SCOPEOP:
			case IDENT:
			{
				name=scoped_name();
				if ( inputState.guessing==0 ) {
					type = getIDLType(name);
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
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		return type;
	}
	
	public final MIDLType  constr_type_spec() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_struct:
			{
				type=struct_type();
				break;
			}
			case LITERAL_union:
			{
				type=union_type();
				break;
			}
			case LITERAL_enum:
			{
				type=enum_type();
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
				consumeUntil(_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		return type;
	}
	
	public final MIDLType  base_type_spec() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_char:
			{
				type=char_type();
				break;
			}
			case LITERAL_wchar:
			{
				type=wide_char_type();
				break;
			}
			case LITERAL_boolean:
			{
				type=boolean_type();
				break;
			}
			case LITERAL_octet:
			{
				type=octet_type();
				break;
			}
			case LITERAL_any:
			{
				type=any_type();
				break;
			}
			case LITERAL_Object:
			{
				type=object_type();
				break;
			}
			case LITERAL_ValueBase:
			{
				type=value_base_type();
				break;
			}
			default:
				boolean synPredMatched119 = false;
				if ((((LA(1) >= LITERAL_float && LA(1) <= LITERAL_long)) && (_tokenSet_36.member(LA(2))))) {
					int _m119 = mark();
					synPredMatched119 = true;
					inputState.guessing++;
					try {
						{
						floating_pt_type();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched119 = false;
					}
					rewind(_m119);
					inputState.guessing--;
				}
				if ( synPredMatched119 ) {
					type=floating_pt_type();
				}
				else {
					boolean synPredMatched121 = false;
					if ((((LA(1) >= LITERAL_long && LA(1) <= LITERAL_unsigned)) && (_tokenSet_37.member(LA(2))))) {
						int _m121 = mark();
						synPredMatched121 = true;
						inputState.guessing++;
						try {
							{
							integer_type();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched121 = false;
						}
						rewind(_m121);
						inputState.guessing--;
					}
					if ( synPredMatched121 ) {
						type=integer_type();
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
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
			return type;
		}
		
	public final MIDLType  template_type_spec() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_sequence:
			{
				type=sequence_type();
				break;
			}
			case LITERAL_string:
			{
				type=string_type();
				break;
			}
			case LITERAL_wstring:
			{
				type=wide_string_type();
				break;
			}
			case LITERAL_fixed:
			{
				type=fixed_pt_type();
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
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		return type;
	}
	
	public final MIDLType  any_type() throws RecognitionException, TokenStreamException {
		MIDLType any = null;
		
		any = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_any);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) any).setKind(MPrimitiveKind.PK_ANY);
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
		return any;
	}
	
	public final MIDLType  object_type() throws RecognitionException, TokenStreamException {
		MIDLType object = null;
		
		object = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_Object);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) object).setKind(MPrimitiveKind.PK_OBJREF);
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
		return object;
	}
	
	public final MIDLType  value_base_type() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		type = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_ValueBase);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) type).setKind(MPrimitiveKind.PK_VALUEBASE);
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
		return type;
	}
	
	public final MIDLType  sequence_type() throws RecognitionException, TokenStreamException {
		MIDLType sequence = null;
		
		
		sequence = new MSequenceDefImpl();
		String bound = null;
		MIDLType type = null;
		
		
		try {      // for error handling
			match(LITERAL_sequence);
			match(LT);
			type=simple_type_spec();
			if ( inputState.guessing==0 ) {
				((MSequenceDef) sequence).setIdlType(type);
			}
			{
			switch ( LA(1)) {
			case COMMA:
			{
				match(COMMA);
				bound=positive_int_const();
				if ( inputState.guessing==0 ) {
					((MSequenceDef) sequence).setBound(new Long(bound));
				}
				break;
			}
			case GT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(GT);
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
		return sequence;
	}
	
	public final MIDLType  fixed_pt_type() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		
		type = new MFixedDefImpl();
		String digits = null;
		String scale = null;
		
		
		try {      // for error handling
			match(LITERAL_fixed);
			match(LT);
			scale=positive_int_const();
			if ( inputState.guessing==0 ) {
				((MFixedDef) type).setScale(Short.parseShort(scale));
			}
			match(COMMA);
			digits=positive_int_const();
			if ( inputState.guessing==0 ) {
				((MFixedDef) type).setDigits(Integer.parseInt(digits));
			}
			match(GT);
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
		return type;
	}
	
	public final DeclaratorHelper  declarator() throws RecognitionException, TokenStreamException {
		DeclaratorHelper helper = null;
		
		helper = new DeclaratorHelper(); String name;
		
		try {      // for error handling
			boolean synPredMatched129 = false;
			if (((LA(1)==IDENT) && (LA(2)==SEMI||LA(2)==COMMA))) {
				int _m129 = mark();
				synPredMatched129 = true;
				inputState.guessing++;
				try {
					{
					simple_declarator();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched129 = false;
				}
				rewind(_m129);
				inputState.guessing--;
			}
			if ( synPredMatched129 ) {
				name=simple_declarator();
				if ( inputState.guessing==0 ) {
					helper.setDeclarator(name);
				}
			}
			else {
				boolean synPredMatched131 = false;
				if (((LA(1)==IDENT) && (LA(2)==LBRACK))) {
					int _m131 = mark();
					synPredMatched131 = true;
					inputState.guessing++;
					try {
						{
						complex_declarator();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched131 = false;
					}
					rewind(_m131);
					inputState.guessing--;
				}
				if ( synPredMatched131 ) {
					helper=complex_declarator();
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
					consumeUntil(_tokenSet_38);
				} else {
				  throw ex;
				}
			}
			return helper;
		}
		
	public final DeclaratorHelper  complex_declarator() throws RecognitionException, TokenStreamException {
		DeclaratorHelper helper = null;
		
		
		try {      // for error handling
			helper=array_declarator();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_38);
			} else {
			  throw ex;
			}
		}
		return helper;
	}
	
	public final DeclaratorHelper  array_declarator() throws RecognitionException, TokenStreamException {
		DeclaratorHelper helper = null;
		
		
		helper = new DeclaratorHelper();
		String name;
		String size;
		MArrayDef array = new MArrayDefImpl();
		
		
		try {      // for error handling
			name=identifier();
			if ( inputState.guessing==0 ) {
				helper.setDeclarator(name);
			}
			{
			int _cnt191=0;
			_loop191:
			do {
				if ((LA(1)==LBRACK)) {
					size=fixed_array_size();
					if ( inputState.guessing==0 ) {
						array.addBound(new Long(size));
					}
				}
				else {
					if ( _cnt191>=1 ) { break _loop191; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt191++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				helper.setArray(array);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_38);
			} else {
			  throw ex;
			}
		}
		return helper;
	}
	
	public final MPrimitiveDef  signed_int() throws RecognitionException, TokenStreamException {
		MPrimitiveDef number = null;
		
		
		try {      // for error handling
			boolean synPredMatched140 = false;
			if (((LA(1)==LITERAL_short))) {
				int _m140 = mark();
				synPredMatched140 = true;
				inputState.guessing++;
				try {
					{
					signed_short_int();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched140 = false;
				}
				rewind(_m140);
				inputState.guessing--;
			}
			if ( synPredMatched140 ) {
				number=signed_short_int();
			}
			else {
				boolean synPredMatched142 = false;
				if (((LA(1)==LITERAL_long) && (_tokenSet_25.member(LA(2))))) {
					int _m142 = mark();
					synPredMatched142 = true;
					inputState.guessing++;
					try {
						{
						signed_long_int();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched142 = false;
					}
					rewind(_m142);
					inputState.guessing--;
				}
				if ( synPredMatched142 ) {
					number=signed_long_int();
				}
				else {
					boolean synPredMatched144 = false;
					if (((LA(1)==LITERAL_long) && (LA(2)==LITERAL_long))) {
						int _m144 = mark();
						synPredMatched144 = true;
						inputState.guessing++;
						try {
							{
							signed_longlong_int();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched144 = false;
						}
						rewind(_m144);
						inputState.guessing--;
					}
					if ( synPredMatched144 ) {
						number=signed_longlong_int();
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}
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
				return number;
			}
			
	public final MPrimitiveDef  unsigned_int() throws RecognitionException, TokenStreamException {
		MPrimitiveDef number = null;
		
		
		try {      // for error handling
			boolean synPredMatched150 = false;
			if (((LA(1)==LITERAL_unsigned) && (LA(2)==LITERAL_short))) {
				int _m150 = mark();
				synPredMatched150 = true;
				inputState.guessing++;
				try {
					{
					unsigned_short_int();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched150 = false;
				}
				rewind(_m150);
				inputState.guessing--;
			}
			if ( synPredMatched150 ) {
				number=unsigned_short_int();
			}
			else {
				boolean synPredMatched152 = false;
				if (((LA(1)==LITERAL_unsigned) && (LA(2)==LITERAL_long))) {
					int _m152 = mark();
					synPredMatched152 = true;
					inputState.guessing++;
					try {
						{
						unsigned_long_int();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched152 = false;
					}
					rewind(_m152);
					inputState.guessing--;
				}
				if ( synPredMatched152 ) {
					number=unsigned_long_int();
				}
				else {
					boolean synPredMatched154 = false;
					if (((LA(1)==LITERAL_unsigned) && (LA(2)==LITERAL_long))) {
						int _m154 = mark();
						synPredMatched154 = true;
						inputState.guessing++;
						try {
							{
							unsigned_longlong_int();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched154 = false;
						}
						rewind(_m154);
						inputState.guessing--;
					}
					if ( synPredMatched154 ) {
						number=unsigned_longlong_int();
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}
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
				return number;
			}
			
	public final MPrimitiveDef  signed_short_int() throws RecognitionException, TokenStreamException {
		MPrimitiveDef number = null;
		
		number = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_short);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) number).setKind(MPrimitiveKind.PK_SHORT);
			}
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
		return number;
	}
	
	public final MPrimitiveDef  signed_long_int() throws RecognitionException, TokenStreamException {
		MPrimitiveDef number = null;
		
		number = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_long);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) number).setKind(MPrimitiveKind.PK_LONG);
			}
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
		return number;
	}
	
	public final MPrimitiveDef  signed_longlong_int() throws RecognitionException, TokenStreamException {
		MPrimitiveDef number = null;
		
		number = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_long);
			match(LITERAL_long);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) number).setKind(MPrimitiveKind.PK_LONGLONG);
			}
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
		return number;
	}
	
	public final MPrimitiveDef  unsigned_short_int() throws RecognitionException, TokenStreamException {
		MPrimitiveDef number = null;
		
		number = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_unsigned);
			match(LITERAL_short);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) number).setKind(MPrimitiveKind.PK_USHORT);
			}
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
		return number;
	}
	
	public final MPrimitiveDef  unsigned_long_int() throws RecognitionException, TokenStreamException {
		MPrimitiveDef number = null;
		
		number = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_unsigned);
			match(LITERAL_long);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) number).setKind(MPrimitiveKind.PK_ULONG);
			}
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
		return number;
	}
	
	public final MPrimitiveDef  unsigned_longlong_int() throws RecognitionException, TokenStreamException {
		MPrimitiveDef number = null;
		
		number = new MPrimitiveDefImpl();
		
		try {      // for error handling
			match(LITERAL_unsigned);
			match(LITERAL_long);
			match(LITERAL_long);
			if ( inputState.guessing==0 ) {
				((MPrimitiveDef) number).setKind(MPrimitiveKind.PK_ULONGLONG);
			}
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
		return number;
	}
	
	public final List  member_list() throws RecognitionException, TokenStreamException {
		List members = null;
		
		members = new ArrayList(); List decls = null;
		
		try {      // for error handling
			{
			int _cnt167=0;
			_loop167:
			do {
				if ((_tokenSet_5.member(LA(1)))) {
					decls=member();
					if ( inputState.guessing==0 ) {
						members.addAll(decls);
					}
				}
				else {
					if ( _cnt167>=1 ) { break _loop167; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt167++;
			} while (true);
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
		return members;
	}
	
	public final List  member() throws RecognitionException, TokenStreamException {
		List members = null;
		
		
		members = new ArrayList();
		List decls = null;
		MIDLType type = null;
		
		
		try {      // for error handling
			type=type_spec();
			decls=declarators();
			if ( inputState.guessing==0 ) {
				
				for (Iterator it = decls.iterator(); it.hasNext(); ) {
				DeclaratorHelper declarator = (DeclaratorHelper) it.next();
				String id = declarator.getDeclarator();
				
				if (declarator.isArray()) {
				MArrayDef array = declarator.getArray();
				array.setIdlType(type);
				type = (MIDLType) array;
				}
				
				MFieldDef field = new MFieldDefImpl();
				field.setIdentifier(id);
				field.setIdlType(type);
				members.add(field);
				}
				
			}
			match(SEMI);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_39);
			} else {
			  throw ex;
			}
		}
		return members;
	}
	
	public final MIDLType  switch_type_spec() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		String name = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_long:
			case LITERAL_short:
			case LITERAL_unsigned:
			{
				type=integer_type();
				break;
			}
			case LITERAL_char:
			{
				type=char_type();
				break;
			}
			case LITERAL_wchar:
			{
				type=wide_char_type();
				break;
			}
			case LITERAL_boolean:
			{
				type=boolean_type();
				break;
			}
			case LITERAL_enum:
			{
				type=enum_type();
				break;
			}
			case SCOPEOP:
			case IDENT:
			{
				name=scoped_name();
				if ( inputState.guessing==0 ) {
					type = getIDLType(name);
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
		return type;
	}
	
	public final List  switch_body(
		MIDLType switchType
	) throws RecognitionException, TokenStreamException {
		List members = null;
		
		members = new ArrayList(); List decls = null;
		
		try {      // for error handling
			{
			int _cnt173=0;
			_loop173:
			do {
				if ((LA(1)==LITERAL_case||LA(1)==LITERAL_default)) {
					decls=case_dcl(switchType);
					if ( inputState.guessing==0 ) {
						members.addAll(decls);
					}
				}
				else {
					if ( _cnt173>=1 ) { break _loop173; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt173++;
			} while (true);
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
		return members;
	}
	
	public final List  case_dcl(
		MIDLType switchType
	) throws RecognitionException, TokenStreamException {
		List members = null;
		
		String label = null; List labels = null;
		
		try {      // for error handling
			{
			int _cnt176=0;
			_loop176:
			do {
				if ((LA(1)==LITERAL_case||LA(1)==LITERAL_default)) {
					label=case_label();
					if ( inputState.guessing==0 ) {
						
						if (label.equals("")) {
						// a zero length string indicates the 'default' label
						labels.add(label);
						} else if (switchType instanceof MPrimitiveDef) {
						MPrimitiveDef ptype = (MPrimitiveDef) switchType;
						if (ptype.getKind() == MPrimitiveKind.PK_BOOLEAN) {
						labels.add(new Boolean(label));
						} else {
						if (label.charAt(0) == '-') {
						if ((ptype.getKind() == MPrimitiveKind.PK_SHORT) ||
						(ptype.getKind() == MPrimitiveKind.PK_LONG)  ||
						(ptype.getKind() == MPrimitiveKind.PK_LONGLONG)) {
						labels.add(new Long(label));
						} else if (ptype.getKind() == MPrimitiveKind.PK_USHORT) {
						labels.add(new Long(Long.parseLong(label) + (1<<16)));
						} else if (ptype.getKind() == MPrimitiveKind.PK_ULONG) {
						labels.add(new Long(Long.parseLong(label) + (1<<32)));
						} else if (ptype.getKind() == MPrimitiveKind.PK_ULONGLONG) {
						labels.add(new Long(Long.parseLong(label) + (1<<64)));
						} else {
						throw new TokenStreamException("invalid primitive type for union label '" + label + "'");
						}
						} else {
						labels.add(new Long(label));
						}
						labels.add(new Short(label));
						}
						} else if ((switchType instanceof MStringDef) ||
						(switchType instanceof MWstringDef)) {
						if (label.length() == 1) {
						labels.add(new String(label));
						} else {
						throw new TokenStreamException("union label '" + label + "' has more than one character");
						}
						} else if (switchType instanceof MEnumDef) {
						MEnumDef enumDef = (MEnumDef) switchType;
						if (enumDef.getMembers().contains(label)) {
						labels.add(label);
						} else {
						throw new TokenStreamException("union label '" + label + "' is not a valid member of enum " + enumDef.getIdentifier());
						}
						}
						
					}
				}
				else {
					if ( _cnt176>=1 ) { break _loop176; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt176++;
			} while (true);
			}
			members=element_spec(labels);
			match(SEMI);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return members;
	}
	
	public final String  case_label() throws RecognitionException, TokenStreamException {
		String label = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_case:
			{
				match(LITERAL_case);
				label=const_exp();
				match(COLON);
				break;
			}
			case LITERAL_default:
			{
				match(LITERAL_default);
				if ( inputState.guessing==0 ) {
					label = new String("");
				}
				match(COLON);
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
				consumeUntil(_tokenSet_41);
			} else {
			  throw ex;
			}
		}
		return label;
	}
	
	public final List  element_spec(
		List labels
	) throws RecognitionException, TokenStreamException {
		List fields = null;
		
		
		fields = new ArrayList();
		DeclaratorHelper helper = null;
		MIDLType type = null;
		
		
		try {      // for error handling
			type=type_spec();
			helper=declarator();
			if ( inputState.guessing==0 ) {
				
				String id = helper.getDeclarator();
				
				if (helper.isArray()) {
				MArrayDef array = helper.getArray();
				array.setIdlType(type);
				type = array;
				}
				
				for (Iterator l = labels.iterator(); l.hasNext(); ) {
				Object case_label = l.next();
				
				for (Iterator f = fields.iterator(); f.hasNext(); ) {
				MUnionFieldDef field = (MUnionFieldDef) f.next();
				Object field_label = field.getLabel();
				if (case_label.equals(field_label)) {
				if (case_label.toString().equals("")) {
				throw new TokenStreamException("unions cannot have multiple 'default' labels");
				} else {
				throw new TokenStreamException("case label '"+case_label+ "' was used more than once in one union");
				}
				}
				}
				
				MUnionFieldDef field = new MUnionFieldDefImpl();
				field.setIdentifier(id);
				field.setIdlType(type);
				field.setLabel(case_label);
				fields.add(field);
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
		return fields;
	}
	
	public final String  enumerator() throws RecognitionException, TokenStreamException {
		String name = null;
		
		
		try {      // for error handling
			name=identifier();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_42);
			} else {
			  throw ex;
			}
		}
		return name;
	}
	
	public final String  fixed_array_size() throws RecognitionException, TokenStreamException {
		String size = null;
		
		
		try {      // for error handling
			match(LBRACK);
			size=positive_int_const();
			match(RBRACK);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_43);
			} else {
			  throw ex;
			}
		}
		return size;
	}
	
	public final List  readonly_attr_spec() throws RecognitionException, TokenStreamException {
		List attributes = null;
		
		
		attributes = new ArrayList();
		String id = null;
		MIDLType type = null;
		List decls = null;
		
		
		try {      // for error handling
			match(LITERAL_readonly);
			match(LITERAL_attribute);
			type=param_type_spec();
			decls=readonly_attr_declarator();
			if ( inputState.guessing==0 ) {
				
				for (Iterator it = decls.iterator(); it.hasNext(); ) {
				MAttributeDef attr = (MAttributeDef) it.next();
				attr.setIdlType(type);
				attributes.add(attr);
				symbolTable.add(id, attr);
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
		return attributes;
	}
	
	public final List  attr_spec() throws RecognitionException, TokenStreamException {
		List attributes = null;
		
		
		attributes = new ArrayList();
		MIDLType type = null;
		List decls = null;
		
		
		try {      // for error handling
			match(LITERAL_attribute);
			type=param_type_spec();
			decls=attr_declarator();
			if ( inputState.guessing==0 ) {
				
				for (Iterator it = decls.iterator(); it.hasNext(); ) {
				MAttributeDef attr = (MAttributeDef) it.next();
				attr.setIdlType(type);
				attr.setReadonly(false);
				attributes.add(attr);
				symbolTable.add(attr.getIdentifier(), attr);
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
		return attributes;
	}
	
	public final boolean  op_attribute() throws RecognitionException, TokenStreamException {
		boolean oneway = false;
		
		
		try {      // for error handling
			match(LITERAL_oneway);
			if ( inputState.guessing==0 ) {
				oneway = true;
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_44);
			} else {
			  throw ex;
			}
		}
		return oneway;
	}
	
	public final MIDLType  op_type_spec() throws RecognitionException, TokenStreamException {
		MIDLType type = null;
		
		type = new MPrimitiveDefImpl();
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_long:
			case LITERAL_short:
			case LITERAL_unsigned:
			case LITERAL_char:
			case LITERAL_wchar:
			case LITERAL_boolean:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_Object:
			case LITERAL_string:
			case LITERAL_wstring:
			case LITERAL_ValueBase:
			case IDENT:
			{
				type=param_type_spec();
				break;
			}
			case LITERAL_void:
			{
				match(LITERAL_void);
				if ( inputState.guessing==0 ) {
					((MPrimitiveDef) type).setKind(MPrimitiveKind.PK_VOID);
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
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return type;
	}
	
	public final List  parameter_dcls(
		String scope
	) throws RecognitionException, TokenStreamException {
		List params = null;
		
		
		params = new ArrayList();
		MParameterDef param = null;
		
		
		try {      // for error handling
			match(LPAREN);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(scope);
			}
			{
			switch ( LA(1)) {
			case LITERAL_in:
			case LITERAL_out:
			case LITERAL_inout:
			{
				param=param_dcl();
				if ( inputState.guessing==0 ) {
					params.add(param);
				}
				{
				_loop206:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						param=param_dcl();
						if ( inputState.guessing==0 ) {
							params.add(param);
						}
					}
					else {
						break _loop206;
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
			if ( inputState.guessing==0 ) {
				symbolTable.popScope();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_45);
			} else {
			  throw ex;
			}
		}
		return params;
	}
	
	public final String  context_expr() throws RecognitionException, TokenStreamException {
		String context = null;
		
		String id = null;
		
		try {      // for error handling
			match(LITERAL_context);
			match(LPAREN);
			id=string_literal();
			if ( inputState.guessing==0 ) {
				context = id;
			}
			{
			_loop214:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					id=string_literal();
					if ( inputState.guessing==0 ) {
						context += "," + id;
					}
				}
				else {
					break _loop214;
				}
				
			} while (true);
			}
			match(RPAREN);
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
		return context;
	}
	
	public final MParameterDef  param_dcl() throws RecognitionException, TokenStreamException {
		MParameterDef param = null;
		
		
		param = new MParameterDefImpl();
		MIDLType type = null;
		String id;
		
		
		try {      // for error handling
			param_attribute(param);
			type=param_type_spec();
			if ( inputState.guessing==0 ) {
				param.setIdlType(type);
			}
			id=simple_declarator();
			if ( inputState.guessing==0 ) {
				param.setIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_20);
			} else {
			  throw ex;
			}
		}
		return param;
	}
	
	public final void param_attribute(
		MParameterDef param
	) throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_in:
			{
				match(LITERAL_in);
				if ( inputState.guessing==0 ) {
					param.setDirection(MParameterMode.PARAM_IN);
				}
				break;
			}
			case LITERAL_out:
			{
				match(LITERAL_out);
				if ( inputState.guessing==0 ) {
					param.setDirection(MParameterMode.PARAM_OUT);
				}
				break;
			}
			case LITERAL_inout:
			{
				match(LITERAL_inout);
				if ( inputState.guessing==0 ) {
					param.setDirection(MParameterMode.PARAM_INOUT);
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
				consumeUntil(_tokenSet_21);
			} else {
			  throw ex;
			}
		}
	}
	
	public final String  imported_scope() throws RecognitionException, TokenStreamException {
		String scope = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case IDENT:
			{
				scope=scoped_name();
				break;
			}
			case STRING_LITERAL:
			{
				scope=string_literal();
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
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return scope;
	}
	
	public final List  readonly_attr_declarator() throws RecognitionException, TokenStreamException {
		List attributes = null;
		
		
		attributes = new ArrayList();
		String id = null;
		List excepts = null;
		MAttributeDef attr = new MAttributeDefImpl();
		
		
		try {      // for error handling
			id=simple_declarator();
			if ( inputState.guessing==0 ) {
				
				attr = (MAttributeDef) verifyNameEmpty(id, attr);
				attr.setSourceFile(getSourceFile());
				attr.setIdentifier(id);
				attr.setReadonly(true);
				attributes.add(attr);
				
			}
			{
			switch ( LA(1)) {
			case LITERAL_raises:
			{
				excepts=raises_expr();
				if ( inputState.guessing==0 ) {
					attr.setGetExceptions(new HashSet(excepts));
				}
				break;
			}
			case SEMI:
			case COMMA:
			{
				{
				_loop228:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						id=simple_declarator();
						if ( inputState.guessing==0 ) {
							
							attr = (MAttributeDef) verifyNameEmpty(id, attr);
							attr.setSourceFile(getSourceFile());
							attr.setIdentifier(id);
							attr.setReadonly(true);
							attributes.add(attr);
							
						}
					}
					else {
						break _loop228;
					}
					
				} while (true);
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
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return attributes;
	}
	
	public final List  attr_declarator() throws RecognitionException, TokenStreamException {
		List attributes = null;
		
		
		attributes = new ArrayList();
		String id = null;
		MAttributeDef attr = new MAttributeDefImpl();
		
		
		try {      // for error handling
			id=simple_declarator();
			if ( inputState.guessing==0 ) {
				
				attr = (MAttributeDef) verifyNameEmpty(id, attr);
				attr.setSourceFile(getSourceFile());
				attr.setIdentifier(id);
				attr.setReadonly(false);
				attributes.add(attr);
				
			}
			{
			switch ( LA(1)) {
			case LITERAL_getraises:
			case LITERAL_setraises:
			{
				attr_raises_expr(attr);
				break;
			}
			case SEMI:
			case COMMA:
			{
				{
				_loop233:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						id=simple_declarator();
						if ( inputState.guessing==0 ) {
							
							attr = (MAttributeDef) verifyNameEmpty(id, attr);
							attr.setSourceFile(getSourceFile());
							attr.setIdentifier(id);
							attr.setReadonly(false);
							attributes.add(attr);
							
						}
					}
					else {
						break _loop233;
					}
					
				} while (true);
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
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return attributes;
	}
	
	public final void attr_raises_expr(
		MAttributeDef attr
	) throws RecognitionException, TokenStreamException {
		
		List gets = null; List sets = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_getraises:
			{
				gets=get_excep_expr();
				if ( inputState.guessing==0 ) {
					attr.setGetExceptions(new HashSet(gets));
				}
				{
				switch ( LA(1)) {
				case LITERAL_setraises:
				{
					sets=set_excep_expr();
					if ( inputState.guessing==0 ) {
						attr.setSetExceptions(new HashSet(sets));
					}
					break;
				}
				case SEMI:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case LITERAL_setraises:
			{
				sets=set_excep_expr();
				if ( inputState.guessing==0 ) {
					attr.setSetExceptions(new HashSet(sets));
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
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
	}
	
	public final List  get_excep_expr() throws RecognitionException, TokenStreamException {
		List exceptions = null;
		
		
		try {      // for error handling
			match(LITERAL_getraises);
			exceptions=exception_list();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_46);
			} else {
			  throw ex;
			}
		}
		return exceptions;
	}
	
	public final List  set_excep_expr() throws RecognitionException, TokenStreamException {
		List exceptions = null;
		
		
		try {      // for error handling
			match(LITERAL_setraises);
			exceptions=exception_list();
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
		return exceptions;
	}
	
	public final List  exception_list() throws RecognitionException, TokenStreamException {
		List exceptions = null;
		
		exceptions = new ArrayList(); String name = null;
		
		try {      // for error handling
			match(LPAREN);
			name=scoped_name();
			if ( inputState.guessing==0 ) {
				
				MExceptionDef except = new MExceptionDefImpl();
				except = (MExceptionDef) verifyNameExists(name, except);
				exceptions.add(except);
				
			}
			{
			_loop240:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					name=scoped_name();
					if ( inputState.guessing==0 ) {
						
						MExceptionDef except = new MExceptionDefImpl();
						except = (MExceptionDef) verifyNameExists(name, except);
						exceptions.add(except);
						
					}
				}
				else {
					break _loop240;
				}
				
			} while (true);
			}
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_46);
			} else {
			  throw ex;
			}
		}
		return exceptions;
	}
	
	public final MComponentDef  component_dcl() throws RecognitionException, TokenStreamException {
		MComponentDef component = null;
		
		component = new MComponentDefImpl(); List decls =  null;
		
		try {      // for error handling
			component=component_header();
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(component.getIdentifier());
			}
			decls=component_body();
			if ( inputState.guessing==0 ) {
				
				for (Iterator it = decls.iterator(); it.hasNext(); ) {
				MContained element = (MContained) it.next();
				element.setDefinedIn(component);
				element.setSourceFile(getSourceFile());
				
				if ((debug & DEBUG_COMPONENT) != 0)
				System.out.print(
				"[c] adding '"+element.getIdentifier()+
				"' to component '"+component.getIdentifier()+"'");
				
				if (element instanceof MEmitsDef) {
				((MEmitsDef) element).setComponent(component);
				component.addEmits((MEmitsDef) element);
				if ((debug & (DEBUG_COMPONENT + DEBUG_EVENT)) != 0)
				System.out.println(" as emits");
				} else if (element instanceof MPublishesDef) {
				((MPublishesDef) element).setComponent(component);
				component.addPublishes((MPublishesDef) element);
				if ((debug & (DEBUG_COMPONENT + DEBUG_EVENT)) != 0)
				System.out.println(" as publishes");
				} else if (element instanceof MConsumesDef) {
				((MConsumesDef) element).setComponent(component);
				component.addConsumes((MConsumesDef) element);
				if ((debug & (DEBUG_COMPONENT + DEBUG_EVENT)) != 0)
				System.out.println(" as consumes");
				} else if (element instanceof MProvidesDef) {
				((MProvidesDef) element).setComponent(component);
				component.addFacet((MProvidesDef) element);
				if ((debug & (DEBUG_COMPONENT + DEBUG_INTERFACE)) != 0)
				System.out.println(" as facet");
				} else if (element instanceof MUsesDef) {
				((MUsesDef) element).setComponent(component);
				component.addReceptacle((MUsesDef) element);
				if ((debug & (DEBUG_COMPONENT + DEBUG_INTERFACE)) != 0)
				System.out.println(" as receptacle");
				} else if (element instanceof MAttributeDef) {
				((MAttributeDef) element).setDefinedIn(component);
				component.addContents((MAttributeDef) element);
				if ((debug & DEBUG_COMPONENT) != 0)
				System.out.println(" as attribute");
				} else {
				throw new TokenStreamException(
				"can't add element '"+element.getIdentifier()+
				"' to component '"+component.getIdentifier()+"'");
				}
				}
				
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.popScope();
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
		return component;
	}
	
	public final MComponentDef  component_forward_dcl() throws RecognitionException, TokenStreamException {
		MComponentDef component = null;
		
		component = new MComponentDefImpl(); String id = null;
		
		try {      // for error handling
			match(LITERAL_component);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				component = (MComponentDef) verifyNameEmpty(id, component);
				component.setSourceFile(getSourceFile());
				component.setRepositoryId(createRepositoryId(id));
				component.setIdentifier(id);
				symbolTable.add(id, component);
				
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
		return component;
	}
	
	public final MComponentDef  component_header() throws RecognitionException, TokenStreamException {
		MComponentDef component = null;
		
		component = new MComponentDefImpl(); String id; List base; List sups;
		
		try {      // for error handling
			match(LITERAL_component);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				component = (MComponentDef) verifyNameEmpty(id, component);
				component.setSourceFile(getSourceFile());
				component.setRepositoryId(createRepositoryId(id));
				component.setIdentifier(id);
				symbolTable.add(id, component);
				
			}
			{
			switch ( LA(1)) {
			case COLON:
			{
				base=component_inheritance_spec();
				if ( inputState.guessing==0 ) {
					checkSetBases(component, base);
				}
				break;
			}
			case LCURLY:
			case LITERAL_supports:
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
			case LITERAL_supports:
			{
				sups=supported_interface_spec();
				if ( inputState.guessing==0 ) {
					checkSetSupports(component, sups);
				}
				break;
			}
			case LCURLY:
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
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		return component;
	}
	
	public final List  component_body() throws RecognitionException, TokenStreamException {
		List exports = null;
		
		exports = new ArrayList(); List decls = null;
		
		try {      // for error handling
			{
			_loop255:
			do {
				if ((_tokenSet_47.member(LA(1)))) {
					decls=component_export();
					if ( inputState.guessing==0 ) {
						exports.addAll(decls);
					}
				}
				else {
					break _loop255;
				}
				
			} while (true);
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
		return exports;
	}
	
	public final List  component_inheritance_spec() throws RecognitionException, TokenStreamException {
		List names = null;
		
		names = new ArrayList(); String name = null;
		
		try {      // for error handling
			match(COLON);
			name=scoped_name();
			if ( inputState.guessing==0 ) {
				names.add(name);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		return names;
	}
	
	public final List  supported_interface_spec() throws RecognitionException, TokenStreamException {
		List supports = null;
		
		supports = new ArrayList(); String name = null;
		
		try {      // for error handling
			match(LITERAL_supports);
			name=scoped_name();
			if ( inputState.guessing==0 ) {
				supports.add(name);
			}
			{
			_loop251:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					name=scoped_name();
					if ( inputState.guessing==0 ) {
						supports.add(name);
					}
				}
				else {
					break _loop251;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_49);
			} else {
			  throw ex;
			}
		}
		return supports;
	}
	
	public final List  component_export() throws RecognitionException, TokenStreamException {
		List exports = null;
		
		exports = new ArrayList(); MContained holder = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_provides:
			{
				holder=provides_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					exports.add(holder);
				}
				break;
			}
			case LITERAL_uses:
			{
				holder=uses_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					exports.add(holder);
				}
				break;
			}
			case LITERAL_emits:
			{
				holder=emits_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					exports.add(holder);
				}
				break;
			}
			case LITERAL_publishes:
			{
				holder=publishes_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					exports.add(holder);
				}
				break;
			}
			case LITERAL_consumes:
			{
				holder=consumes_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					exports.add(holder);
				}
				break;
			}
			case LITERAL_readonly:
			case LITERAL_attribute:
			{
				exports=attr_dcl();
				match(SEMI);
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
				consumeUntil(_tokenSet_50);
			} else {
			  throw ex;
			}
		}
		return exports;
	}
	
	public final MProvidesDef  provides_dcl() throws RecognitionException, TokenStreamException {
		MProvidesDef provides = null;
		
		
		provides = new MProvidesDefImpl();
		String id = null;
		MInterfaceDef iface = null;
		
		
		try {      // for error handling
			match(LITERAL_provides);
			iface=interface_type();
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				provides = (MProvidesDef) verifyNameEmpty(id, provides);
				provides.setSourceFile(getSourceFile());
				provides.setIdentifier(id);
				provides.setProvides(iface);
				symbolTable.add(id, provides);
				
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
		return provides;
	}
	
	public final MUsesDef  uses_dcl() throws RecognitionException, TokenStreamException {
		MUsesDef uses = null;
		
		
		uses = new MUsesDefImpl();
		String id = null;
		MInterfaceDef iface = null;
		
		
		try {      // for error handling
			match(LITERAL_uses);
			{
			switch ( LA(1)) {
			case LITERAL_multiple:
			{
				match(LITERAL_multiple);
				if ( inputState.guessing==0 ) {
					uses.setMultiple(true);
				}
				break;
			}
			case SCOPEOP:
			case LITERAL_Object:
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			iface=interface_type();
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				uses = (MUsesDef) verifyNameEmpty(id, uses);
				uses.setSourceFile(getSourceFile());
				uses.setIdentifier(id);
				uses.setUses(iface);
				symbolTable.add(id, uses);
				
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
		return uses;
	}
	
	public final MEmitsDef  emits_dcl() throws RecognitionException, TokenStreamException {
		MEmitsDef emits = null;
		
		
		emits = new MEmitsDefImpl();
		String id = null;
		String name = null;
		MEventDef event = new MEventDefImpl();
		
		
		try {      // for error handling
			match(LITERAL_emits);
			name=scoped_name();
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				emits = (MEmitsDef) verifyNameEmpty(id, emits);
				event = (MEventDef) verifyNameExists(name, event);
				emits.setType(event);
				emits.setSourceFile(getSourceFile());
				emits.setIdentifier(id);
				symbolTable.add(id, emits);
				
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
		return emits;
	}
	
	public final MPublishesDef  publishes_dcl() throws RecognitionException, TokenStreamException {
		MPublishesDef publishes = null;
		
		
		publishes = new MPublishesDefImpl();
		String id = null;
		String name = null;
		MEventDef event = null;
		
		
		try {      // for error handling
			match(LITERAL_publishes);
			name=scoped_name();
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				publishes = (MPublishesDef) verifyNameEmpty(id, publishes);
				event = (MEventDef) verifyNameExists(name, event);
				publishes.setType(event);
				publishes.setSourceFile(getSourceFile());
				publishes.setIdentifier(id);
				symbolTable.add(id, publishes);
				
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
		return publishes;
	}
	
	public final MConsumesDef  consumes_dcl() throws RecognitionException, TokenStreamException {
		MConsumesDef consumes = null;
		
		
		consumes = new MConsumesDefImpl();
		String id = null;
		String name = null;
		MEventDef event = null;
		
		
		try {      // for error handling
			match(LITERAL_consumes);
			name=scoped_name();
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				consumes = (MConsumesDef) verifyNameEmpty(id, consumes);
				event = (MEventDef) verifyNameExists(name, event);
				consumes.setType(event);
				consumes.setSourceFile(getSourceFile());
				consumes.setIdentifier(id);
				symbolTable.add(id, consumes);
				
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
		return consumes;
	}
	
	public final MInterfaceDef  interface_type() throws RecognitionException, TokenStreamException {
		MInterfaceDef iface = null;
		
		iface = new MInterfaceDefImpl(); String name = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case IDENT:
			{
				name=scoped_name();
				if ( inputState.guessing==0 ) {
					iface = (MInterfaceDef) verifyNameExists(name, iface);
				}
				break;
			}
			case LITERAL_Object:
			{
				match(LITERAL_Object);
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
		return iface;
	}
	
	public final MHomeDef  home_header() throws RecognitionException, TokenStreamException {
		MHomeDef home = null;
		
		
		home = new MHomeDefImpl();
		String id = null;
		String name;
		MComponentDef component = new MComponentDefImpl();
		MHomeDef b = null;
		List sups = null;
		MValueDef key = null;
		
		
		try {      // for error handling
			match(LITERAL_home);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				home = (MHomeDef) verifyNameEmpty(id, home);
				home.setRepositoryId(createRepositoryId(id));
				home.setSourceFile(getSourceFile());
				home.setIdentifier(id);
				symbolTable.add(id, home);
				
			}
			{
			switch ( LA(1)) {
			case COLON:
			{
				b=home_inheritance_spec();
				if ( inputState.guessing==0 ) {
					if (b != null) { home.addBase(b); }
				}
				break;
			}
			case LITERAL_supports:
			case LITERAL_manages:
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
			case LITERAL_supports:
			{
				sups=supported_interface_spec();
				if ( inputState.guessing==0 ) {
					checkSetSupports(home, sups);
				}
				break;
			}
			case LITERAL_manages:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_manages);
			name=scoped_name();
			if ( inputState.guessing==0 ) {
				
				component = (MComponentDef) verifyNameExists(name, component);
				home.setComponent(component);
				component.addHome(home);
				
			}
			{
			switch ( LA(1)) {
			case LITERAL_primarykey:
			{
				key=primary_key_spec();
				if ( inputState.guessing==0 ) {
					home.setPrimary_Key(key);
				}
				break;
			}
			case LCURLY:
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
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		return home;
	}
	
	public final void home_body(
		MHomeDef home
	) throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(home.getIdentifier());
			}
			{
			_loop273:
			do {
				if ((_tokenSet_51.member(LA(1)))) {
					home_export(home);
				}
				else {
					break _loop273;
				}
				
			} while (true);
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.popScope();
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
	}
	
	public final MHomeDef  home_inheritance_spec() throws RecognitionException, TokenStreamException {
		MHomeDef base = null;
		
		String name = null;
		
		try {      // for error handling
			match(COLON);
			name=scoped_name();
			if ( inputState.guessing==0 ) {
				
				MContained lookup = lookupName(name, DEBUG_COMPONENT);
				if ((lookup != null) && (lookup instanceof MHomeDef))
				{ base = (MHomeDef) lookup; }
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_52);
			} else {
			  throw ex;
			}
		}
		return base;
	}
	
	public final MValueDef  primary_key_spec() throws RecognitionException, TokenStreamException {
		MValueDef key = null;
		
		key = new MValueDefImpl(); String name = null;
		
		try {      // for error handling
			match(LITERAL_primarykey);
			name=scoped_name();
			if ( inputState.guessing==0 ) {
				key = (MValueDef) verifyNameExists(name, key);
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
		return key;
	}
	
	public final void home_export(
		MHomeDef home
	) throws RecognitionException, TokenStreamException {
		
		
		List exports = null;
		MFactoryDef factory = null;
		MFinderDef finder = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case LITERAL_const:
			case LITERAL_typedef:
			case LITERAL_native:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_long:
			case LITERAL_short:
			case LITERAL_unsigned:
			case LITERAL_char:
			case LITERAL_wchar:
			case LITERAL_boolean:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_Object:
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			case LITERAL_string:
			case LITERAL_wstring:
			case LITERAL_exception:
			case LITERAL_oneway:
			case LITERAL_void:
			case LITERAL_ValueBase:
			case LITERAL_typeid:
			case LITERAL_typeprefix:
			case LITERAL_readonly:
			case LITERAL_attribute:
			case IDENT:
			{
				exports=export();
				if ( inputState.guessing==0 ) {
					checkAddContents(home, exports);
				}
				break;
			}
			case LITERAL_factory:
			{
				factory=factory_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					
					factory.setHome(home);
					home.addFactory(factory);
					
				}
				break;
			}
			case LITERAL_finder:
			{
				finder=finder_dcl();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					
					finder.setHome(home);
					home.addFinder(finder);
					
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
				consumeUntil(_tokenSet_53);
			} else {
			  throw ex;
			}
		}
	}
	
	public final MFactoryDef  factory_dcl() throws RecognitionException, TokenStreamException {
		MFactoryDef factory = null;
		
		
		factory = new MFactoryDefImpl();
		String id = null;
		List params = null;
		List excepts = null;
		
		
		try {      // for error handling
			match(LITERAL_factory);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				factory = (MFactoryDef) verifyNameEmpty(id, factory);
				factory.setSourceFile(getSourceFile());
				factory.setIdentifier(id);
				symbolTable.add(id, factory);
				
			}
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LITERAL_in:
			{
				params=init_param_decls();
				if ( inputState.guessing==0 ) {
					checkSetParameters((MOperationDef) factory, params);
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
			case LITERAL_raises:
			{
				excepts=raises_expr();
				if ( inputState.guessing==0 ) {
					checkSetExceptions((MOperationDef) factory, excepts);
				}
				break;
			}
			case SEMI:
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
		return factory;
	}
	
	public final MFinderDef  finder_dcl() throws RecognitionException, TokenStreamException {
		MFinderDef finder = null;
		
		
		finder = new MFinderDefImpl();
		String id = null;
		List params = null;
		List excepts = null;
		
		
		try {      // for error handling
			match(LITERAL_finder);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				finder = (MFinderDef) verifyNameEmpty(id, finder);
				finder.setSourceFile(getSourceFile());
				finder.setIdentifier(id);
				symbolTable.add(id, finder);
				
			}
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LITERAL_in:
			{
				params=init_param_decls();
				if ( inputState.guessing==0 ) {
					checkSetParameters((MOperationDef) finder, params);
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
			case LITERAL_raises:
			{
				excepts=raises_expr();
				if ( inputState.guessing==0 ) {
					checkSetExceptions((MOperationDef) finder, excepts);
				}
				break;
			}
			case SEMI:
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
		return finder;
	}
	
	public final MEventDef  event_dcl() throws RecognitionException, TokenStreamException {
		MEventDef event = null;
		
		
		event = new MEventDefImpl();
		List elements = new ArrayList();
		List decls = null;
		
		
		try {      // for error handling
			event=event_header();
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(event.getIdentifier());
			}
			{
			_loop293:
			do {
				if ((_tokenSet_14.member(LA(1)))) {
					decls=value_element();
					if ( inputState.guessing==0 ) {
						
						for (Iterator i = decls.iterator(); i.hasNext(); ) {
						((MContained) i.next()).setDefinedIn(event);
						}
						elements.addAll(decls);
						
					}
				}
				else {
					break _loop293;
				}
				
			} while (true);
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				
				event.setContentss(elements);
				symbolTable.popScope();
				
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
		return event;
	}
	
	public final MEventDef  event_abs_dcl() throws RecognitionException, TokenStreamException {
		MEventDef event = null;
		
		
		event = new MEventDefImpl();
		String id = null;
		List exports = new ArrayList();
		List decls = null;
		
		
		try {      // for error handling
			match(LITERAL_abstract);
			if ( inputState.guessing==0 ) {
				event.setAbstract(true);
			}
			match(LITERAL_eventtype);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				event = (MEventDef) verifyNameEmpty(id, event);
				event.setSourceFile(getSourceFile());
				event.setIdentifier(id);
				symbolTable.add(id, event);
				
			}
			value_inheritance_spec((MValueDef) event);
			match(LCURLY);
			if ( inputState.guessing==0 ) {
				symbolTable.pushScope(id);
			}
			{
			_loop290:
			do {
				if ((_tokenSet_9.member(LA(1)))) {
					decls=export();
					if ( inputState.guessing==0 ) {
						exports.addAll(decls);
					}
				}
				else {
					break _loop290;
				}
				
			} while (true);
			}
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				
				for (Iterator i = exports.iterator(); i.hasNext(); ) {
				((MContained) i.next()).setDefinedIn(event);
				}
				symbolTable.popScope();
				
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
		return event;
	}
	
	public final MEventDef  event_forward_dcl() throws RecognitionException, TokenStreamException {
		MEventDef event = null;
		
		event = new MEventDefImpl(); String id = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_abstract:
			{
				match(LITERAL_abstract);
				if ( inputState.guessing==0 ) {
					event.setAbstract(true);
				}
				break;
			}
			case LITERAL_eventtype:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_eventtype);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				event = (MEventDef) verifyNameEmpty(id, event);
				event.setSourceFile(getSourceFile());
				event.setIdentifier(id);
				symbolTable.add(id, event);
				event.setContentss(null);
				
				if ((debug & (DEBUG_EVENT | DEBUG_FORWARD_DECL)) != 0) {
				System.out.println("[d] event forward declaration for " + id);
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
		return event;
	}
	
	public final MEventDef  event_header() throws RecognitionException, TokenStreamException {
		MEventDef event = null;
		
		event = new MEventDefImpl(); String id = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_custom:
			{
				match(LITERAL_custom);
				if ( inputState.guessing==0 ) {
					event.setCustom(true);
				}
				break;
			}
			case LITERAL_eventtype:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_eventtype);
			id=identifier();
			if ( inputState.guessing==0 ) {
				
				event = (MEventDef) verifyNameEmpty(id, event);
				event.setSourceFile(getSourceFile());
				event.setIdentifier(id);
				symbolTable.add(id, event);
				
			}
			value_inheritance_spec((MValueDef) event);
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
		return event;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		";",
		"\"module\"",
		"{",
		"}",
		"\"abstract\"",
		"\"local\"",
		"\"interface\"",
		":",
		",",
		"::",
		"\"valuetype\"",
		"\"custom\"",
		"\"truncatable\"",
		"\"supports\"",
		"\"public\"",
		"\"private\"",
		"\"factory\"",
		"(",
		")",
		"\"in\"",
		"\"const\"",
		"=",
		"|",
		"^",
		"&",
		"<<",
		">>",
		"+",
		"-",
		"%",
		"*",
		"/",
		"~",
		"\"TRUE\"",
		"\"FALSE\"",
		"\"typedef\"",
		"\"native\"",
		"\"float\"",
		"\"double\"",
		"\"long\"",
		"\"short\"",
		"\"unsigned\"",
		"\"char\"",
		"\"wchar\"",
		"\"boolean\"",
		"\"octet\"",
		"\"any\"",
		"\"Object\"",
		"\"struct\"",
		"\"union\"",
		"\"switch\"",
		"\"case\"",
		"\"default\"",
		"\"enum\"",
		"\"sequence\"",
		"<",
		">",
		"\"string\"",
		"\"wstring\"",
		"[",
		"]",
		"\"exception\"",
		"\"oneway\"",
		"\"void\"",
		"\"out\"",
		"\"inout\"",
		"\"raises\"",
		"\"context\"",
		"\"fixed\"",
		"\"ValueBase\"",
		"\"import\"",
		"\"typeid\"",
		"\"typeprefix\"",
		"\"readonly\"",
		"\"attribute\"",
		"\"getraises\"",
		"\"setraises\"",
		"\"component\"",
		"\"provides\"",
		"\"uses\"",
		"\"multiple\"",
		"\"emits\"",
		"\"publishes\"",
		"\"consumes\"",
		"\"home\"",
		"\"manages\"",
		"\"primarykey\"",
		"\"finder\"",
		"\"eventtype\"",
		"an integer value",
		"OCTAL",
		"a hexadecimal value",
		"a string literal",
		"a string literal",
		"a character literal",
		"a character literal",
		"a floating point value",
		"\"d\"",
		"\"D\"",
		"an identifier",
		"?",
		".",
		"!",
		"white space",
		"a preprocessor directive",
		"a comment",
		"a comment",
		"a digit",
		"an octal digit",
		"a hexadecimal letter",
		"an exponent",
		"an identifier character",
		"an escape sequence",
		"an octal value"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 157627636242237216L, 285349890L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 157627636242237216L, 285350914L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 157627636242237346L, 285349890L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 16L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 7367886791354884096L, 549755814656L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { -684549273691211568L, 554151543617L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 2128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 64L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 7079658064487391232L, 549755845134L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 7079658064489226368L, 549890062862L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 4160L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 1152921573263546448L, 554151444481L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 7079658064489226240L, 549755845134L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 16L, 549755813888L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 7079658064489226368L, 549755845134L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 135232L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 4194304L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 16L, 128L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 4198400L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 6922030428245204992L, 549755814400L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 0L, 549755813888L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 4198416L, 98368L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { 1152921504611047440L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 1152921504611045392L, 549755813888L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = { 1152921504606851088L, 549755813888L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { 1152921504678156304L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { 1152921504812374032L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = { 1152921505080809488L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = { 1152921506691422224L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = { 1152921513133873168L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = { 1152921573263415312L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = { 412318965760L, 686657896448L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { 1152921504606851072L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = { 4194320L, 549755813888L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = { 1152925902653362192L, 549755813888L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = { 1152947892885917712L, 549755813888L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = { 4112L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = { 7367886791354884224L, 549755814656L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = { 108086391056892032L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = { 7475973182411776000L, 549755814656L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = { 4224L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = { -9223372036854771696L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = { 6922030428245204992L, 549755814408L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	private static final long[] mk_tokenSet_45() {
		long[] data = { 16L, 192L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
	private static final long[] mk_tokenSet_46() {
		long[] data = { 16L, 65536L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
	private static final long[] mk_tokenSet_47() {
		long[] data = { 0L, 15491072L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
	private static final long[] mk_tokenSet_48() {
		long[] data = { 131136L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_48 = new BitSet(mk_tokenSet_48());
	private static final long[] mk_tokenSet_49() {
		long[] data = { 64L, 33554432L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_49 = new BitSet(mk_tokenSet_49());
	private static final long[] mk_tokenSet_50() {
		long[] data = { 128L, 15491072L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_50 = new BitSet(mk_tokenSet_50());
	private static final long[] mk_tokenSet_51() {
		long[] data = { 7079658064488439808L, 549890062862L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_51 = new BitSet(mk_tokenSet_51());
	private static final long[] mk_tokenSet_52() {
		long[] data = { 131072L, 33554432L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_52 = new BitSet(mk_tokenSet_52());
	private static final long[] mk_tokenSet_53() {
		long[] data = { 7079658064488439936L, 549890062862L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_53 = new BitSet(mk_tokenSet_53());
	
	}
