package ccmtools.parser.idl3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import antlr.TokenStreamException;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainedImpl;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MUnionDef;
import ccmtools.metamodel.BaseIDL.MUnionFieldDef;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.metamodel.ComponentIDL.MComponentDef;
import ccmtools.metamodel.ComponentIDL.MHomeDef;
import ccmtools.metamodel.ComponentIDL.MSupportsDef;
import ccmtools.metamodel.ComponentIDL.MSupportsDefImpl;

public class ParserHelper
{
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
    
    
    private Idl3SymbolTable symbolTable;
    
    private String originalFile; // the original file being parsed.
    private String sourceFile;   // the source of the current section of code.
    
    private List errorList = new ArrayList();
    private List warningList = new ArrayList();
    
    /** Java standard logger object */
    protected Logger logger;
    
    
    public ParserHelper()
    {
        logger = Logger.getLogger("ccm.parser.idl3");
        logger.fine("");               
    		symbolTable = new Idl3SymbolTable();
    }
    
    
    public Idl3SymbolTable getSymbolTable()
    {
		logger.fine("");
    		return symbolTable;
    }
    
    
    /**
     * Find out which file the parser manager started with.
     *
     * @return the name of the original file given to parse.
     */
    public String getOriginalFile() 
    { 
    		logger.fine("");
    		return originalFile; 
    	}

    /**
     * Set the name of the file that the parser manager started with.
     *
     * @param f the name of the original file to be parsed.
     */
    public void setOriginalFile(String f)
    {
    		logger.fine("");
    		logger.finer("filename = " + f);
        if (originalFile == null) 
        {
            File file = new File(f);
            try 
            { 
            		originalFile = file.getCanonicalPath(); 
            	}
            catch (IOException e) 
            { 
            		originalFile = f; 
            	}
        }
    }
    
    
    /**
     * Find out which file the parser is currently handling.
     *
     * @return the name of the file that originated the current code section.
     */
    public String getSourceFile() 
    { 
    		logger.fine("");
    		return sourceFile; 
    	}

    /**
     * Set the name of the file that the parser is currently handling.
     *
     * @param f the name of the file that provided the current code section.
     */
    public void setSourceFile(String f)
    {
    		logger.fine("");
    		logger.finer("filename = " + f);
        File file = new File(f);
        try 
        { 
        		sourceFile = file.getCanonicalPath(); 
        	}
        catch (IOException e) 
        { 
        		sourceFile = f; 
        	}
    }
    
    
    public List getErrorList()
    {
		logger.fine("");
    		return errorList;
    }
    
    public List getWarningList()
    {
		logger.fine("");
    		return warningList;
    }
    
    /**
     * Return the absolute filename if, this file has been included, thus, no code 
     * should be generated. In the other case return an empty string.
     */
    public String getIncludedSourceFile()
    {
    		logger.fine("");	
    		if(getSourceFile().equals(getOriginalFile()))
    		{
    			return "";
    		}
    		else
    		{
    			return getSourceFile();
    		}    
    }
    
    
    /*
     * Translate the given object scope id into a CORBA compatible repository
     * identifier.
     */
    public String createRepositoryId(String id)
    {
    		logger.fine("");	
    		logger.finer("id = " + id);
    		String scope = getSymbolTable().currentScopeAsString();
        String replaced = scope.replaceAll("::", "/");

        if(scope.equals("")) 
        {
            return "IDL:"+ replaced + id + ":1.0";
        } 
        else 
        {
            return "IDL:"+ replaced + "/" + id + ":1.0";
        }
    }
    
    
    /*
     * This function is a debug wrapper for the symbol table function of the
     * same name. It essentially just calls the symbolTable function, but if
     * needed first it prints stuff.
     */
    public MContained lookupName(String name)
    {
    		logger.fine("");
        logger.finer("name = " + name);
        MContained result = null;

        if (name.indexOf("::") > 0)
		{
			result = getSymbolTable().lookupScopedName(name);
		}
		else
		{
			String look = name;
			if (look.startsWith("::"))
			{
				look = look.substring(2, look.length());
			}
			result = getSymbolTable().lookupNameInCurrentScope(look);
		}
		return result;
    }
    
    
    /*
     * Set the base(s) of this value def according to the name given.
     */
    public void addValueBase(MValueDef val, String name)
    {
    		logger.fine("");
    		logger.finer("name = " + name);
        MValueDef inherited = (MValueDef) lookupName(name);
        if((inherited != null) && (inherited.getContentss() != null)) 
        {
            if(inherited.isAbstract()) 
            	{
            		val.addAbstractBase(inherited);
            	}
            else 
            {
            		val.setBase(inherited);
            }
        }
    }

    /*
     * Set the supports information for the given value using the given name.
     */
    public void addValueSupports(MValueDef val, String name)
        throws TokenStreamException
    {
    		logger.fine("");
    		logger.finer("name = " + name);
        String id = val.getIdentifier();
        MContained support = lookupName(name);

        if (support != null) 
        {
            val.setInterfaceDef((MInterfaceDef) support);
        } 
        else 
        {
            throw new TokenStreamException("value '"+id+"' can't inherit from undefined value '"+name+"'");
        }
    }

    
    /*
     * Get the IDL type for a given identifier.
     */
    public MIDLType getIDLType(String id)
        throws TokenStreamException
    {
    		logger.fine("");
    		logger.finer("id = " + id);
        MContained contained = new MContainedImpl();
        contained = verifyNameExists(id, contained);

        if (contained instanceof MIDLType)
            return (MIDLType) contained;

        if (contained instanceof MTyped)
            return ((MTyped) contained).getIdlType();

        throw new TokenStreamException("cannot find IDL type for '"+
            contained.getIdentifier()+"'");
    }

    
    
    //------------------------------------------------------------------------
    // Parser and Model Validation Methods    
    //------------------------------------------------------------------------
    
    
    /*
     * Find out if a given string is an IDL keyword.
     */
    public boolean checkKeyword(String id)
	{
    		logger.fine("");
    		logger.finer("id = " + id);
    		for (int i = 0; i < keywords.length; i++)
		{
			if (keywords[i].equalsIgnoreCase(id))
			{
				return false;
			}
		}
		return true;
	}
    

    /*
	 * Find out if the given string could be a Long object. If so, return the
	 * Long object, and if not throw a semantic exception.
	 */
    public Long checkLong(String expr)
        throws TokenStreamException
    {
    		logger.fine("");
    		logger.fine("expr = " + expr);
        Long result = null;
        try 
        {
            result = new Long(expr);
            return result;
        } 
        catch (Exception e) 
        {
            throw new TokenStreamException("can't evaluate '"+expr+"' as an integer");
        }
    }

	
    /*
     * Check to make sure the given string is a positive (1 or greater) valid
     * long integer. Throw an exception if not.
     */
    public void checkPositive(String bound)
        throws TokenStreamException
    {
    		logger.fine("");
    		logger.finer("bound = " + bound);
    		try 
    		{
            Long limit = new Long(bound);
            if (limit.longValue() < 1) 
            { 
            		throw new RuntimeException(); 
            	}
        } 
    		catch (RuntimeException e) 
    		{
            throw new TokenStreamException("invalid positive value "+bound);
        }
    }

    /*
     * Find out if the given string could be a Float object. If so, return the *
     * Float object, and if not throw a semantic exception.
     */
    public Float checkFloat(String expr)
        throws TokenStreamException
    {
    		logger.fine("");
    		logger.finer("expr = " + expr);
    		Float result = null;
        try 
        {
            result = new Float(expr);
            return result;
        } 
        catch (Exception e) 
        {
            throw new TokenStreamException("can't evaluate '"+expr+"' as a float");
        }
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
    public MContained verifyNameEmpty(String id, MContained query)
        throws TokenStreamException
    {
    		logger.fine("");
    		logger.finer("id = " + id);
    		
    		MContained lookup = lookupName(id);

        if (lookup == null) 
        		return query;

        Class qtype = query.getClass().getInterfaces()[0];

        if (!qtype.isInstance(lookup)) 
        		return query;

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
        throw new TokenStreamException("identifier '"+id+"' was used more than once");
    }

    /*
     * Verify that the given name exists and is an instance of the correct
     * class. Throw an exception if not.
     */
    public MContained verifyNameExists(String id, MContained query)
        throws TokenStreamException
    {
    		logger.fine("");
    		logger.finer("id = " + id);
        MContained lookup = lookupName(id);
        try 
        {
            Class qtype = query.getClass().getInterfaces()[0];
            if(qtype.isInstance(lookup)) 
            		return lookup;
            throw new RuntimeException();
        } 
        catch (RuntimeException e) 
        {
            throw new TokenStreamException("identifier '"+id+"' is undefined or of the wrong type");
        }
    }

    
    /*
     * Add the contents to the given container. Check to ensure each item is
     * defined in the current file being parsed.
     */
    public void checkAddContents(MContainer container, List contents)
        throws TokenStreamException
    {
    		logger.fine("");
        if (container == null)
            throw new TokenStreamException("can't add contents ("+contents+") to a null container");

        Iterator it = contents.iterator();
        while (it.hasNext()) 
        {
            MContained item = (MContained) it.next();
            if (item == null)
            {
                throw new TokenStreamException("can't add a null item from '"+contents+
                    "' to container '"+container+"'");
            }
            item.setDefinedIn(container);
            container.addContents(item);
        }
    }

    /*
     * Set the base list for the given interface, after checking to make sure
     * the given bases actually exist and have the correct abstract / local
     * attributes.
     */
    public void checkSetBases(MInterfaceDef iface, List bases)
        throws TokenStreamException
    {
    		logger.fine("");
        String id = iface.getIdentifier();

        ArrayList verified = new ArrayList();
        for (Iterator it = bases.iterator(); it.hasNext(); ) 
        {
            String inherit = (String) it.next();
            MContained lookup = lookupName(inherit);

            if ((lookup == null) || (! (lookup instanceof MInterfaceDef)))
            {
                throw new TokenStreamException("interface '"+ id 
                		+"' can't inherit from undefined interface '"+inherit+"'");
            }
            
            MInterfaceDef base = (MInterfaceDef) lookup;

            if (iface.isAbstract() != base.isAbstract())
            {
                throw new TokenStreamException("interface '"+ id 
                		+"' can't inherit from '"+inherit+"' because one interface is abstract");
            }

            if ((! iface.isLocal()) && base.isLocal())
            {
                throw new TokenStreamException("interface '"+ id 
                		+"' can't inherit from '"+inherit+"' because '"+id+"' is not local");
            }

            verified.add(base);
        }
        iface.setBases(verified);
    }

    /*
     * Set the supported interface list for the given interface (actually has to
     * be a component or home, but they're both of type MInterfaceDef), after
     * checking to make sure the given supported interfaces actually exist.
     */
    public void checkSetSupports(MInterfaceDef iface, List supports)
        throws TokenStreamException
    {
    		logger.fine("");
        List slist = new ArrayList();
        for (Iterator it = supports.iterator(); it.hasNext(); )
        {
            String name = (String) it.next();
            MContained lookup = lookupName(name);
            if ((lookup == null) || (! (lookup instanceof MInterfaceDef))) 
            {
                throw new TokenStreamException("interface '" + iface.getIdentifier() 
                		+ "' can't support undefined interface '"+name+"'");
            } 
            else 
            {
                MSupportsDef support = new MSupportsDefImpl();
                support.setIdentifier("support_"+name);
                support.setSupports((MInterfaceDef) lookup);
                support.setDefinedIn(iface);
                if (iface instanceof MComponentDef) 
                {
                    support.setComponent((MComponentDef) iface);
                } 
                else if(iface instanceof MHomeDef) 
                {
                    support.setHome((MHomeDef) iface);
                }
                slist.add(support);
            }
        }

        if (iface instanceof MComponentDef)
        {
            ((MComponentDef) iface).setSupportss(slist);
        }
        else if (iface instanceof MHomeDef)
        {
            ((MHomeDef) iface).setSupportss(slist);
        }
        else 
        	{
        		throw new RuntimeException(iface.getIdentifier() + " must be a component or home instance");
        	}
    }

    /*
     * Examine the given list of objects for repeated identifiers, and set them
     * to the member list appropriate for the type of the given box.
     */
    public void checkSetMembers(MContained box, List members)
        throws TokenStreamException
    {
    		logger.fine("");
        MExceptionDef exception = null;
        MStructDef struct = null;
        MUnionDef union = null;

        if (box instanceof MExceptionDef) 
        	{
        		exception = (MExceptionDef) box;
        	}
        else if(box instanceof MStructDef)
        	{
        		struct = (MStructDef) box;
        	}
        else if(box instanceof MUnionDef) 
        	{
        		union = (MUnionDef) box;
        	}

        if ((struct == null) && (exception == null) && (union == null))
        {
            throw new RuntimeException(box+" must be an exception, union, or struct");
        }
        
        if ((members.size() == 0) && (exception == null))
        {
            throw new TokenStreamException("container '"+box.getIdentifier()+"' has no members");
        }
        // check if there are fields with same identifier.

        String outID = "";
        String inID = "";
        Iterator o = members.iterator();
        Iterator i = members.iterator();
        try 
        {
            if (union != null) 
            {
                MUnionFieldDef out, in;
                while (o.hasNext()) 
                {
                    out = (MUnionFieldDef) o.next(); outID = out.getIdentifier();
                    while (i.hasNext()) 
                    {
                        in = (MUnionFieldDef) i.next(); inID = in.getIdentifier();
                        if (outID.equals(inID) && ! out.equals(in))
                            throw new RuntimeException();
                    }
                }
            } 
            else 
            {
                MFieldDef out, in;
                while (o.hasNext()) 
                {
                    out = (MFieldDef) o.next(); outID = out.getIdentifier();
                    while (i.hasNext()) 
                    {
                        in = (MFieldDef) i.next(); inID = in.getIdentifier();
                        if (outID.equals(inID) && ! out.equals(in))
                            throw new RuntimeException();
                    }
                }
            }
        } 
        catch (RuntimeException e) 
        {
            throw new TokenStreamException("repeated identifier '" 
            		+ outID + "' in '"+box.getIdentifier()+"'");
        }

        // add the members to the given box.
        Iterator it = members.iterator();
        if (union != null) 
        {
            while(it.hasNext())
            {
            		((MUnionFieldDef) it.next()).setUnion(union);
            }
            union.setUnionMembers(members);
        } 
        else 
        {
            while (it.hasNext()) 
            {
                MFieldDef f = (MFieldDef) it.next();
                if (struct != null) f.setStructure(struct);
                else f.setException(exception);
            }
            if (struct != null) 
            	{
            		struct.setMembers(members);
            	}
            else 
            	{
            		exception.setMembers(members);
            	}
        }
    }

    
    /*
     * Set the parameter list for the given operation.
     */
    public void checkSetParameters(MOperationDef op, List params)
    {
    		logger.fine("");
        for (Iterator p = params.iterator(); p.hasNext(); )
        {
            ((MParameterDef) p.next()).setOperation(op);
        }
        op.setParameters(params);
    }

    
    /*
     * Set the exception list for the given operation. Check that the given
     * exceptions have been defined somewhere.
     */
    public void checkSetExceptions(MOperationDef op, List excepts)
        throws TokenStreamException
    {
    		logger.fine("");
    		op.setExceptionDefs(new HashSet(excepts));
    }
}
