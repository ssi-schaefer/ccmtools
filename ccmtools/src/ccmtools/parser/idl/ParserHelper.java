package ccmtools.parser.idl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MAliasDefImpl;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MConstantDefImpl;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MContainerImpl;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MEnumDefImpl;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MFieldDefImpl;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MFixedDefImpl;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDefImpl;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MSequenceDefImpl;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStringDefImpl;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MStructDefImpl;
import ccmtools.metamodel.BaseIDL.MTypedefDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.metamodel.BaseIDL.MWstringDefImpl;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.CcmtoolsProperties;


public class ParserHelper
{
    private static ParserHelper instance = null;
    
    private Logger logger;
    
    private int currentSourceLine;    
    private String currentSourceFile;
    private String mainSourceFile;
    
    private IdentifierTable typeIdTable = new IdentifierTable();
    private IdentifierTable forwardDclTable = new IdentifierTable();     
    private Scope scope = new Scope();
    
    public static ParserHelper getInstance()
    {
        if(instance == null)
        {
            instance = new ParserHelper();
        }
        return instance;
    }
    
    private ParserHelper()
    {
        logger = Logger.getLogger("ccmtools.parser.idl");
        //!!!!!!!!!!
//        logger.setLevel(Level.FINE);
//        Handler handler = new ConsoleHandler();
//        handler.setLevel(Level.ALL);
//        handler.setFormatter(new ccm.local.MinimalFormatter());
//        logger.addHandler(handler);
//        ccm.local.ServiceLocator.instance().setLogger(logger);
        //!!!!!!!!
        init();        
    }
    
    
    public void init()
    {
        System.out.println("init ParserHelper");
        
        typeIdTable.clear();
        forwardDclTable.clear();
        scope.clear();
        
        currentSourceLine = 0;
        currentSourceFile = "";
        mainSourceFile = "";
    }
    
    
    public Logger getLogger()
    {
        return logger;
    }
    
    
    public int getCurrentSourceLine()
    {
        return currentSourceLine;
    }
    private void setCurrentSourceLine(int value)
    {
        currentSourceLine = value;
    }
    public void incrementCurrentSourceLine()
    {
        currentSourceLine++;
    }
    
    
    public String getCurrentSourceFile()
    {
        return currentSourceFile;
    }
    private void setCurrentSourceFile(String value)
    {
        currentSourceFile = value;
    }
    
    public String getMajorSourceFile()
    {
        return mainSourceFile;
    }
    public void setMainSourceFile(String value)
    {
        mainSourceFile = value;
    }

    
    public void registerTypeId(String name)
    {
        String absoluteId = scope + name;
        logger.fine("registerType: " + absoluteId);
        if(!(typeIdTable.register(new Identifier(absoluteId))))
        {      
            throw new RuntimeException(getCurrentSourceFile() + " line " + getCurrentSourceLine() + " : "
                    + "Re-defined type identifier '" + name + "'");            
        }
    }

    public void registerForwardDclId(String name)
    {
        String absoluteId = scope + name;
        logger.fine("registerForwardDcl: " + absoluteId);
        forwardDclTable.register(new Identifier(absoluteId));
    }

    
    public Scope getScope()
    {
        return scope;
    }
    
    
    /*************************************************************************
     * Parser Utility Methods
     *************************************************************************/

    /* 1 */
    public MContainer parseSpecification(List definitions)
    {
        getLogger().fine("1: specification");
        MContainer container = new MContainerImpl();
        container.setContentss(definitions);
        return container;
    }
    
    
    /* 2 */
    public List parseDefinitions(Object definition)
    {
        getLogger().fine("2: definition");
        List l = new ArrayList();
        l.add(definition);
        return l;
    }
    
    public List parseDefinitions(Object definition, List definitions)
    {
        getLogger().fine("2: definition definitions");
        if(definition != null)
        {
            definitions.add(definition);
        }
        else
        {
            // processed some T_INCLUDE or T_PRAGMA lines
        }
        return definitions;
    }
    
    
    /* 27 */
    public MConstantDef parseConstDcl(MIDLType constType, String identifier, Object constExpr)
    {
        getLogger().fine("27: T_EQUAL const_exp = " + constExpr);
        MConstantDef constant = new MConstantDefImpl();
        constant.setIdentifier(identifier);
        constant.setSourceFile(ParserHelper.getInstance().getCurrentSourceFile());
        if(constType instanceof MPrimitiveDef)
        {
            MPrimitiveDef primitive = (MPrimitiveDef)constType;
            constant.setIdlType(constType);
            if(primitive.getKind() == MPrimitiveKind.PK_SHORT 
                || primitive.getKind() == MPrimitiveKind.PK_USHORT)
            {
                short i = (short)(((Integer)constExpr).intValue());
                constant.setConstValue(new Short(i));
            }
            else if(primitive.getKind() == MPrimitiveKind.PK_LONG 
                || primitive.getKind() == MPrimitiveKind.PK_ULONG)
            {
                constant.setConstValue((Integer)constExpr);             
            }
            else if(primitive.getKind() == MPrimitiveKind.PK_LONGLONG 
                || primitive.getKind() == MPrimitiveKind.PK_ULONGLONG)
            {
                long l = (((Integer)constExpr).intValue());
                constant.setConstValue(new Long(l));        
            }
            else if(primitive.getKind() == MPrimitiveKind.PK_FLOAT)
            {
                float f = (float)((Double)constExpr).doubleValue();
                constant.setConstValue(new Float(f));               
            }               
            else if(primitive.getKind() == MPrimitiveKind.PK_DOUBLE)
            {
                constant.setConstValue((Double)constExpr);              
            }               
            else if(primitive.getKind() == MPrimitiveKind.PK_LONGDOUBLE)
            {
                constant.setConstValue((Double)constExpr);              
            }               
            else if(primitive.getKind() == MPrimitiveKind.PK_CHAR
                || primitive.getKind() == MPrimitiveKind.PK_WCHAR)
            {
                constant.setConstValue((Character)constExpr);               
            }   
            else if(primitive.getKind() == MPrimitiveKind.PK_OCTET)
            {
                constant.setConstValue((Integer)constExpr);             
            }   
            else if(primitive.getKind() == MPrimitiveKind.PK_BOOLEAN)
            {
                constant.setConstValue((Boolean)constExpr);             
            }                                   
        }
        else if(constType instanceof MStringDef)
        {            
            MStringDef type = (MStringDef)constType;
            String value = (String)constExpr;
            if(type.getBound() != null && type.getBound().longValue() < value.length())
            {
                throw new RuntimeException("String literal " + identifier + " is out of bound (max. "
                        + type.getBound() + " characters)!");
            }
            constant.setIdlType(type);
            constant.setConstValue(value);
        }
        else if(constType instanceof MWstringDef)
        {
            MWstringDef type = (MWstringDef)constType;
            String value = (String)constExpr;
            if(type.getBound() != null && type.getBound().longValue() < value.length())
            {
                throw new RuntimeException("Wide string literal " + identifier + " is out of bound (max. "
                        + type.getBound() + " characters)!");
            }
            constant.setIdlType(type);
            constant.setConstValue(value);          
        }
        return constant;
    }
    
    
    /* 36 */
    public Object parseUnaryExprMinusSign(Object primaryExpr)
    {
        Object result = null;
        getLogger().fine("36: T_MINUS_SIGN primary_expr = " + primaryExpr);
        if(primaryExpr instanceof Integer)
        {
            result = new Integer(-((Integer)primaryExpr).intValue());
        }
        else if(primaryExpr instanceof Double)
        {
            result = new Double(-((Double)primaryExpr).doubleValue());
        }
        return result;
    }
    

    /* 41 */
    public Integer parsePositiveIntConst(Object constExp)
    {
        getLogger().fine("41: const_exp = " + constExp);        
        if(constExp instanceof Integer)
        {
            return (Integer)constExp;
        }
        else
        {
            throw new RuntimeException(constExp + " is not an integer constant!");
        }
    }
    
    
    /* 42 */
    public MTypedefDef parseTypeDcl(MIDLType type, List declarators)
    {
        getLogger().fine("42: T_TYPEDEF type_spec declarators = " + type + " " + declarators);
        MAliasDef alias = new MAliasDefImpl();
        alias.setIdlType(type);
        String identifier = null;
        if(declarators != null)
        {
            identifier = (String)declarators.get(0);
        }
        alias.setIdentifier(identifier);
        return alias;
    }
    
    
    /* 49 */
    public List parseDeclarators(String declarator)
    {
        getLogger().fine("49: declarator  = " + declarator);
        List l = new ArrayList();
        l.add(declarator);
        return l;
    }

    public List parseDeclarators(String declarator, List declarators)
    {
        getLogger().fine("49: declarator T_COMMA declarators = " + declarator + " " + declarators);
        declarators.add(declarator);
        return declarators;
    }
    
    
    /* 51 */
    public String parseSimpleDeclarator(String identifier)
    {
        getLogger().fine("51: T_IDENTIFIER = " + identifier);
//        helper.registerTypeId(id);
        return identifier;
    }
    
    
    
    /* 53 */
    public MPrimitiveDef parseFloatType()
    {
        getLogger().fine("53: T_FLOAT");         
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_FLOAT);
        return s;
    }

    public MPrimitiveDef parseDoubleType()
    {
        getLogger().fine("53: T_DOUBLE");            
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_DOUBLE);
        return s;
    }

    public MPrimitiveDef parseLongDoubleType()
    {
        getLogger().fine("53: T_LONG T_DOUBLE");         
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_LONGDOUBLE);
        return s;
    }

    
    /* 56 */
    public MPrimitiveDef parseSignedShortType()
    {
        getLogger().fine("56: T_SHORT");         
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_SHORT);
        return s;
    }

    
    /* 57 */
    public MPrimitiveDef parseSignedLongType()
    {
        getLogger().fine("57: T_LONG");          
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_LONG);
        return s;
    }

    
    /* 58 */
    public MPrimitiveDef parseSignedLongLongType()
    {
        getLogger().fine("58: T_LONG T_LONG");           
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_LONGLONG);
        return s;  
    }
    
    
    /* 60 */
    public MPrimitiveDef parseUnsignedShortType()
    {        
        getLogger().fine("60: T_UNSIGNED T_SHORT");          
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_USHORT);
        return s;  
    }
    
    
    /* 61 */
    public MPrimitiveDef parseUnsignedLongType()
    {
        getLogger().fine("61: T_UNSIGNED T_LONG");           
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_ULONG);
        return s;
    }
    
    
    /* 62 */
    public MPrimitiveDef parseUnsignedLongLongType()
    {
        getLogger().fine("62: T_UNSIGNED T_LONG T_LONG");            
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_ULONGLONG);
        return s;
    }
    
    
    /* 63 */
    public MPrimitiveDef parseCharType()
    {
        getLogger().fine("63: T_CHAR");          
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_CHAR);
        return s; 
    }
    
    
    /* 64 */
    public MPrimitiveDef parseWideCharType()
    {
        getLogger().fine("64: T_WCHAR");         
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_WCHAR);
        return s; 
    }

    
    /* 65 */
    public MPrimitiveDef parseBooleanType()
    {
        getLogger().fine("65: T_BOOLEAN");           
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_BOOLEAN);
        return s; 
    }
    
    
    /* 66 */
    public MPrimitiveDef parseOctetType()
    {
        getLogger().fine("66: T_OCTET");         
        MPrimitiveDef s = new MPrimitiveDefImpl();
        s.setKind(MPrimitiveKind.PK_OCTET);
        return s;  
    }
    
    
    /* 67 */
    public MPrimitiveDef parseAnyType()
    {
        getLogger().fine("67: T_ANY");           
        MPrimitiveDef t = new MPrimitiveDefImpl();
        t.setKind(MPrimitiveKind.PK_ANY);
        return t; 
    }
    
    
    /* 68 */
    public MPrimitiveDef parseObjectType()
    {
        getLogger().fine("68: T_OBJECT");            
        MPrimitiveDef t = new MPrimitiveDefImpl();
        t.setKind(MPrimitiveKind.PK_OBJREF);
        return t; 
    }
    

    /* 69 */
    public MStructDef parseStructType(String identifier, List memberList)
    {
        getLogger().fine("69: T_LEFT_CURLY_BRACKET member_list T_RIGHT_CURLY_BRACKET");  
        MStructDef t = new MStructDefImpl();
        t.setIdentifier(identifier);
        Collections.reverse(memberList);
        t.setMembers(memberList);
        return t;
    }
  
    
    /* 70 */
    public List parseMemberList(MFieldDef member)
    {
        getLogger().fine("70: member = " + member);
        List l = new ArrayList();
        l.add(member);
        return l;
    }
    
    public List parseMemberList(MFieldDef member, List memberList)
    {
        getLogger().fine("70: member member_list = " + member + " " + memberList);
        memberList.add(member);
        return memberList;
    }
      
    
    /* 71 */
    public MFieldDef parseMember(MIDLType typeSpec, List declarators)
    {
        getLogger().fine("71: type_spec declarators = " + typeSpec + " " + declarators);
        MFieldDef m = new MFieldDefImpl();
        m.setIdlType(typeSpec);
        if(declarators.size() > 0)
        {
            String id = (String)declarators.get(0);  
            m.setIdentifier(id);
        }
        else
        {
            throw new RuntimeException("No declarators defined for member type " + typeSpec);
        }
        return m;
    }
    
    
    
    /* 78 */
    public MEnumDef parseEnumType(String identifier, List enumerators)
    {
        getLogger().fine("78: enumerators = " + enumerators);  
        MEnumDef enumeration = new MEnumDefImpl();
        enumeration.setIdentifier(identifier);
        enumeration.setSourceFile(ParserHelper.getInstance().getCurrentSourceFile());
        if(enumerators.size() > 0)
        {
            Collections.reverse(enumerators);
            enumeration.setMembers(enumerators);
        }
        else
        {
            throw new RuntimeException("Enumeration " + identifier + " has no members!");
        }           
        return enumeration;
    }
    
    public List parseEnumerator(String enumerator)
    {
        List l = new ArrayList();
        l.add(enumerator);
        return l;
    }
    
    public List parseEnumerators(String enumerator, List enumerators)
    {
        enumerators.add(enumerator);
        return enumerators;
    }
    
    
    /* 80 */
    public MSequenceDef parseSequenceType(MIDLType simpleType)
    {
        getLogger().fine("89: sequence < simple_type_spec > = " + simpleType);  
        MSequenceDef seq = new MSequenceDefImpl();
        seq.setIdlType(simpleType);
        return seq;
    }
    
    public MSequenceDef parseSequenceType(MIDLType simpleType, Integer bound)
    {
        getLogger().fine("89: sequence < simple_type_spec, positive_int_const> = " + simpleType 
                + ", " + bound);  
        MSequenceDef seq = new MSequenceDefImpl();
        seq.setIdlType(simpleType);
        seq.setBound(new Long(bound.longValue()));
        return seq;
    }

    
    
    /* 81 */
    public MStringDef parseStringType()
    {
        getLogger().fine("81: T_STRING");            
        MStringDef s = new MStringDefImpl();            
        return s;         
    }
    
    public MStringDef parseBoundedStringType(Integer bound)
    {
        getLogger().fine("81: T_STRING positive_int_const = " + bound);          
        MStringDef s = new MStringDefImpl();            
        s.setBound(new Long(bound.intValue()));
        return s;     
    }
    
    
    /* 82 */
    public MWstringDef parseWideStringType()
    {
        getLogger().fine("82: T_STRING");            
        MWstringDef s = new MWstringDefImpl();          
        return s;         
    }
    
    public MWstringDef parseBoundedWideStringType(Integer bound)
    {
        getLogger().fine("82: T_WSTRING positive_int_const = " + bound);         
        MWstringDef s = new MWstringDefImpl();          
        s.setBound(new Long(bound.intValue()));
        return s;                 
    }
    
    
    /* 94?? */
    public String parseStringLiteral(String s)
    {
        getLogger().fine("94??: T_STRING_LITERAL = " + s); 
        return s;
    }
    
    public String parseStringConcatenation(String s1, String s2)
    {
        getLogger().fine("94??: T_STRING_LITERAL T_string_literal = " + s1 + " " + s2); 
        return s1+s2;    
    }
        
    public String parseWideStringLiteral(String s)
    {
        getLogger().fine("94??: T_WSTRING_LITERAL = " + s); 
        return s;
    }
    
    public String parseWideStringConcatenation(String s1, String s2)
    {
        getLogger().fine("94??:  T_WSTRING_LITERAL T_wstring_literal = " + s1 + " " + s2); 
        return s1+s2;    
    }
    
    
    /* 96 */
    public MFixedDef parseFixedType(Integer digits, Integer scale)
    {
        getLogger().fine("96: positive_int_const, T_INTEGER_LITERAL = " + digits + ", " + scale);            
        MFixedDef t = new MFixedDefImpl();
        t.setDigits(digits.intValue());
        t.setScale(scale.shortValue());
        return t;              
    }
    
    
    /* 98 */
    public MPrimitiveDef parseValueBaseType()
    {
        getLogger().fine("98: T_VALUEBASE");           
        MPrimitiveDef t = new MPrimitiveDefImpl();
        t.setKind(MPrimitiveKind.PK_VALUEBASE);
        return t; 
    }
    
    
    
    
    
    
    
    /*************************************************************************
     * Scanner Utility Methods
     *************************************************************************/
        
    // # linenumber filename flags
    // # 1 "/home/eteinik/sandbox/workspace-development/TelegramGenerator/examples/simple_test/example1.tgen"
    public void handlePreprocessorLine(String line)
    {
        line = line.substring(0, line.lastIndexOf('\n'));
//        System.out.println("CPP: " + line);

        String[] elements = line.split(" ");
        if (elements[0].equals("#"))
        {
            if (elements.length >= 3)
            {
                if(elements[2].startsWith("\"<"))
                {
                    // e.g. <build-in> or <command line>
                }
                else
                {
                    setCurrentSourceLine(Integer.parseInt(elements[1]));
                    String fileName = elements[2];
                    setCurrentSourceFile(fileName.substring(fileName.indexOf('\"')+1, fileName.lastIndexOf('\"')));
                }
            }
        }
    }
 
    
    // #pragma 
    public void handlePragmaLine(String line)
    {
        line = line.substring(0, line.lastIndexOf('\n'));
        // TODO
        System.out.println("CPP: " + line);
    }

    
    /*
     * Factory methods 
     */
    
    public Double createFloat(String in)
    {
        return Double.parseDouble(in);
    }
    
    public BigInteger createFixed(String in)
    {
        return null;
    }
    
    public Integer createInteger(String in)
    {
        return Integer.parseInt(in);
    }
    
    public Integer createOctet(String in)
    {
        return Integer.parseInt(in,8);
    }
    
    public Integer createHex(String in)
    {
        int start = (in.toUpperCase()).indexOf("0X") + 2; 
        return Integer.parseInt(in.substring(start),16);
    }
    
    public Character createChar(String in)
    {
        // in = "'c'";
        return new Character(in.charAt(in.indexOf('\'')+1));
    }
    
    public Character createWChar(String in)
    {
        // in = "L'c'";
        return new Character(in.charAt(in.indexOf('\'')+1));
    }
    
    public String createString(String in)
    {
        return in.substring(in.indexOf('"')+1, in.lastIndexOf('"'));
    }
    
    public String createWString(String in)
    {
        return in.substring(in.indexOf('"')+1, in.lastIndexOf('"'));
    }

    public MContainer loadCcmModel(UserInterfaceDriver uiDriver, String idlSource)
        throws CcmtoolsException
    {
        try
        {
            uiDriver.printMessage("parse");
            ParserHelper.getInstance().init();
            IdlScanner scanner = new IdlScanner(new StringReader(idlSource));
            IdlParser parser = new IdlParser(scanner);
            MContainer ccmModel = (MContainer) parser.parse().value;
            uiDriver.printMessage("done");
            return ccmModel;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new CcmtoolsException(e.getMessage());
        }
    }


    public MContainer loadCcmModel(UserInterfaceDriver uiDriver, String idlFileName, List<String> includePaths)
        throws CcmtoolsException
    {    
        try
        {
            File idlFile = new File(idlFileName);
            String tmpFileName = idlFile.getName() + ".tmp";
            File tmpIdlFile = new File(tmpFileName);
            useCpp(uiDriver, idlFile.getAbsolutePath(), includePaths, tmpFileName);

            uiDriver.printMessage("parse " + tmpFileName);
            ParserHelper.getInstance().init();
            ParserHelper.getInstance().setMainSourceFile(idlFile.getAbsolutePath());
            IdlScanner scanner = new IdlScanner(new FileReader(tmpIdlFile));
            IdlParser parser = new IdlParser(scanner);                    
            MContainer ccmModel = (MContainer)parser.parse().value;    
            uiDriver.printMessage("done");
            return ccmModel;
        }
        catch (Exception e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }
    
    private void useCpp(UserInterfaceDriver uiDriver, String sourceFileName, List<String> includes, String tmpFileName)
        throws CcmtoolsException
    {
        File tmpFile = new File(System.getProperty("user.dir"), tmpFileName);
        try
        {
            // Run a C preprocessor on the input file, in a separate process.
            StringBuffer cmd = new StringBuffer();
            if (CcmtoolsProperties.Instance().get("ccmtools.cpp").length() != 0)
            {
                cmd.append(CcmtoolsProperties.Instance().get("ccmtools.cpp"));
            }
            else
            {
                cmd.append(Constants.CPP_PATH);
            }
            cmd.append(" ");
            for (String includePath : includes)
            {
                cmd.append("-I").append(includePath).append(" ");
            }
            cmd.append(sourceFileName);
            uiDriver.printMessage(cmd.toString()); // print cpp command line

            Process preproc = Runtime.getRuntime().exec(cmd.toString());
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(preproc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(preproc.getErrorStream()));

            // Read the output and any errors from the command
            String s;
            StringBuffer code = new StringBuffer();
            while ((s = stdInput.readLine()) != null)
            {
                code.append(s).append("\n");
            }
            while ((s = stdError.readLine()) != null)
            {
                uiDriver.printMessage(s);
            }

            // Wait for the process to complete and evaluate the return
            // value of the attempted command
            preproc.waitFor();
            if (preproc.exitValue() != 0)
            {
                throw new RuntimeException("Preprocessor Error: Please verify your include paths or file names ("
                        + sourceFileName + ")!!");
            }
            else
            {
                FileWriter writer = new FileWriter(tmpFile);
                writer.write(code.toString(), 0, code.toString().length());
                writer.close();
            }
//          tmpFile.deleteOnExit();
        }
        catch (Exception e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }

}
