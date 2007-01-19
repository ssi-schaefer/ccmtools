package ccmtools.parser.idl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import java_cup.runtime.Symbol;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MArrayDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MArrayDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MAttributeDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MAttributeDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MConstantDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MConstantDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MContained;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainerImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MEnumDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MEnumDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MExceptionDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MExceptionDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MFieldDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MFieldDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MFixedDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MFixedDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MIDLType;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MModuleDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MModuleDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MNativeDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MNativeDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MOperationDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MOperationDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MParameterDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MParameterDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MParameterMode;
import ccmtools.parser.idl.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MPrimitiveDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.parser.idl.metamodel.BaseIDL.MSequenceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MSequenceDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MStringDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MStringDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MStructDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MTypedefDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MUnionDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MUnionDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MUnionFieldDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MUnionFieldDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueBoxDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueBoxDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueMemberDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueMemberDefImpl;
import ccmtools.parser.idl.metamodel.BaseIDL.MWstringDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MWstringDefImpl;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDefImpl;
import ccmtools.parser.idl.metamodel.ComponentIDL.MFactoryDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MFactoryDefImpl;
import ccmtools.parser.idl.metamodel.ComponentIDL.MFinderDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MHomeDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MHomeDefImpl;
import ccmtools.parser.idl.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MProvidesDefImpl;
import ccmtools.parser.idl.metamodel.ComponentIDL.MUsesDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MUsesDefImpl;
import ccmtools.utils.Text;


public class ParserHelper
{
    private static ParserHelper instance = null;
    
    private Logger logger;
    
    private int currentSourceLine;    
    private String currentSourceFile;
    private String mainSourceFile;
    
    private IdentifierTable typeIdTable = new IdentifierTable();         
    private Scope scope = new Scope();
    private ModelRepository modelRepo = new ModelRepository();

    public Logger getLogger()
    {
        return logger;
    }

    public Scope getScope()
    {
        return scope;
    }
        
    public ModelRepository getModelRepository()
    {
        return modelRepo;
    }

    public void registerTypeId(String name)
    {
        String absoluteId = scope + name;
        logger.fine("registerType: " + absoluteId);
        if(!(typeIdTable.register(new ScopedName(absoluteId))))
        {      
            reportError(getCurrentSourceFile() + " line " + getCurrentSourceLine() + " : "
                    + "Re-defined type identifier '" + name + "'");            
        }
    }

    
    
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
        logger = Logger.getLogger("ccm.parser.idl");
        //!!!!!!!!!!
//        logger.setLevel(Level.FINE);
//        Handler handler = new ConsoleHandler();
//        handler.setLevel(Level.ALL);
//        handler.setFormatter(new LogFormatter());
//        logger.addHandler(handler);
        //!!!!!!!!
        init();        
    }
    
    
    public void init()
    {
        getLogger().fine("");

        typeIdTable.clear();
        getModelRepository().clear();
        scope.clear();
        
        currentSourceLine = 0;
        currentSourceFile = "";
        mainSourceFile = "";
    }
    
    
    public void reportError(String message)
    {
        reportError(message, null);
    }
    
    public void reportError(String message, Object info)
    {
        StringBuilder out = new StringBuilder();
        out.append("ERROR [IDL parser]: ");
        if(info instanceof Symbol)
        {
            out.append(getCurrentSourceFile());
            out.append(" line " + getCurrentSourceLine()).append(": ");
        }
        out.append(message);
        throw new RuntimeException(out.toString());
    }
    

    public void notSupported(String message)
    {
        notSupported(message, null);
    }
    
    public void notSupported(String message, Object info)
    {
        reportError("CCM Tools do not support " + message + "!", info);
    }
    
        
    public int getCurrentSourceLine()
    {
        return currentSourceLine;
    }
    private void setCurrentSourceLine(int value)
    {
        getLogger().fine(Integer.toString(value));
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
        getLogger().fine(value);
        currentSourceFile = value;
    }
    
    public String getMainSourceFile()
    {
        return mainSourceFile;
    }
    public void setMainSourceFile(String value)
    {
        getLogger().fine(value);
        mainSourceFile = value;
    }
    
    /**
     * Return the absolute filename if, this file has been included, thus, no code 
     * should be generated. In the other case return an empty string.
     */
    public String getIncludedSourceFile()
    {
        logger.fine("");
        if (getCurrentSourceFile().equals(getMainSourceFile()))
        {
            return "";
        }
        else
        {
            return getCurrentSourceFile();
        }
    }
    
    
    
    /***************************************************************************
     * Parser Utility Methods
     **************************************************************************/

    /* 1 */
    public MContainer parseSpecification(List definitions)
    {
        getLogger().fine("1: specification " + definitions);
        MContainer container = new MContainerImpl();
        for(Iterator i = definitions.iterator(); i.hasNext();)
        {
            // Each contained element knows its container
            MContained contained = (MContained)i.next();
            contained.setDefinedIn(container);
        }
        container.setContentss(definitions);
        return container;
    }
    
    
    /* 2 */
    public List parseDefinitions(Object definition)
    {
        getLogger().fine("2: (" + definition + ")");
        return parseDefinitions(definition, new ArrayList());
    }
        
    public List parseDefinitions(Object definition, List l)
    {
        getLogger().fine("2: (" + definition + "," + l + ")");
        if(definition != null)
        {
            l.add(definition);
            getLogger().fine("    add " + definition);
        }
        else
        {
            // processed some T_INCLUDE or T_PRAGMA lines
        }
        return l;
    }
        
    
    /* 3 */
    public void parseModuleBegin(String id)
    {
        getLogger().fine("[3] (" + id + ")");
        getScope().pushModule(id);
    }
    
    public void parseModuleEnd(String id)
    {
        getLogger().fine("[3] (" + id + ")");
        getScope().popModule();
    }
    
    public MModuleDef parseModule(String id, List definitions)
    {
        getLogger().fine("[3] (" + definitions + ")");      
        //MModuleDef module = getModelRepository().getModule(getScope());
        MModuleDef module = new MModuleDefImpl();
        module.setIdentifier(id);
        Collections.reverse(definitions);
        for(Iterator i = definitions.iterator(); i.hasNext(); )
        {
            MContained content = (MContained)i.next();
            content.setDefinedIn(module);
            module.addContents(content);
            getLogger().fine("    add = " + content);
            getLogger().fine("       source file = " + content.getSourceFile());
        }
        return module;        
    }
    
    
    /* 5 */
    public MInterfaceDef parseInterfaceDcl(MInterfaceDef iface, List body)
    {
        getLogger().fine("5: interface_header { interface_body }");
        Collections.reverse(body);
        for(Iterator i=body.iterator(); i.hasNext();)
        {      
            MContained content = (MContained)i.next();
            content.setDefinedIn(iface);
            iface.addContents(content);
        }
        String id = iface.getIdentifier();
        ScopedName identifier = new ScopedName(getScope(), id);
        registerTypeId(id);
        getModelRepository().registerIdlType(identifier, iface);
        return iface;
    }
    
    
    /* 6 */
    public MInterfaceDef parseInterfaceForwardDeclaration(String id)
    {
        getLogger().fine("6: T_INTERFACE T_IDENTIFIER = " + id);
        MInterfaceDef type = new MInterfaceDefImpl();
        type.setIdentifier(id);  
        type.setSourceFile(getIncludedSourceFile());
        ScopedName identifier = new ScopedName(getScope(), id);        
        getModelRepository().registerForwardDeclaration(identifier);
        getModelRepository().registerIdlType(identifier, type);        
        return type;
    }
    
    public MInterfaceDef parseAbstractInterfaceForwardDeclaration(String id)
    {
        getLogger().fine("6: T_ABSTRACT T_INTERFACE T_IDENTIFIER = " + id);
        MInterfaceDef iface = parseInterfaceForwardDeclaration(id);
        iface.setAbstract(true);
        return iface;
    }
    
    public MInterfaceDef parseLocalInterfaceForwardDeclaration(String id)
    {
        getLogger().fine("6: T_LOCAL T_INTERFACE T_IDENTIFIER = " + id);
        MInterfaceDef iface = parseInterfaceForwardDeclaration(id);
        iface.setLocal(true);
        return iface;
    }
    
    
    /* 7 */
    public MInterfaceDef parseInterfaceHeader(String id)
    {
        getLogger().fine("7: interface T_IDENTIFIER = " + id);
        ScopedName identifier = new ScopedName(getScope(), id);
        MInterfaceDef iface;
        if(getModelRepository().isForwardDeclaration(identifier))
        {
            MIDLType forwardDcl = getModelRepository().findIdlType(identifier); 
            iface = (MInterfaceDef)forwardDcl;
        }
        else
        {
            iface = new MInterfaceDefImpl();
            getModelRepository().registerIdlType(identifier, iface);
        }
        iface.setIdentifier(id);
        iface.setSourceFile(getIncludedSourceFile());
        return iface;
    }

    public MInterfaceDef parseInterfaceHeader(String id, List inheritanceSpec)
    {
        getLogger().fine("7: T_INTERFACE T_IDENTIFIER interface_inheritance_spec = " + id + ", " + inheritanceSpec);
        MInterfaceDef iface = parseInterfaceHeader(id);
        Collections.reverse(inheritanceSpec);
        iface.setBases(inheritanceSpec);
        return iface;
    }

    
    public MInterfaceDef parseAbstractInterfaceHeader(String id)
    {
        getLogger().fine("7: T_ABSTRACT T_INTERFACE T_IDENTIFIER = " + id);
        MInterfaceDef iface = parseInterfaceHeader(id);
        iface.setAbstract(true);
        return iface;
    }

    public MInterfaceDef parseAbstractInterfaceHeader(String id, List inheritanceSpec)
    {
        getLogger().fine("7: T_ABSTRACT T_INTERFACE T_IDENTIFIER interface_inheritance_spec = " + id + ", " + inheritanceSpec);
        MInterfaceDef iface = parseAbstractInterfaceHeader(id);
        Collections.reverse(inheritanceSpec);
        iface.setBases(inheritanceSpec);
        return iface;
    }

    public MInterfaceDef parseLocalInterfaceHeader(String id)
    {
        getLogger().fine("7:  T_LOCAL T_INTERFACE T_IDENTIFIER = " + id);
        MInterfaceDef iface = parseInterfaceHeader(id);
        iface.setLocal(true);
        return iface;
    }
    
    public MInterfaceDef parseLocalInterfaceHeader(String id, List inheritanceSpec)
    {
        getLogger().fine("7:  T_LOCAL T_INTERFACE T_IDENTIFIER interface_inheritance_spec = " + id + ", " + inheritanceSpec);
        MInterfaceDef iface = parseLocalInterfaceHeader(id);
        Collections.reverse(inheritanceSpec);
        iface.setBases(inheritanceSpec);        
        return iface;
    }
    
    
    
    /* 8 */
    public List parseInterfaceBody()
    {
        getLogger().fine("8: interface_body");
        return new ArrayList();        
    }

    public List parseInterfaceBody(List exports)
    {
        getLogger().fine("8: interface_body = " + exports);
        return exports;        
    }
    
    public List parseExports(Object export)
    {
        getLogger().fine("8: exports " + export);
        List exports = new ArrayList();
        addExport(export, exports);
        return exports;
    }
    
    public List parseExports(Object export, List exports)
    {
        getLogger().fine("8: exports " + export + ", " + exports);
        addExport(export, exports);
        return exports;
    }
    
    private void addExport(Object export, List exports)
    {
        getLogger().fine("8: exports " + export + ", " + exports);
        if(export == null)
            return;
        
        if(export instanceof List)
        {
            List exportList = (List)export;
            exports.addAll(exportList);
        }
        else
        {
            exports.add(export);
        }
    }
    
    
    /* 10 */
    public List parseInterfaceInheritanceSpec(List scopedNames)
    {
        getLogger().fine("10: T_COLON interface_names = " + scopedNames);
        List baseIFaces = new ArrayList();
        for(Iterator i = scopedNames.iterator(); i.hasNext();)
        {
            ScopedName id = (ScopedName)i.next();
            MIDLType type = getModelRepository().findIdlType(getScope(), id); 
            if(type == null)
            {
                reportError("Base interface " + id + "not found!" );
            }
            else if(!(type instanceof MInterfaceDef))
            {
                reportError("Base interface specification contains " + type + "which is not an interface!");
            }
            else
            {
                baseIFaces.add(type);
            }            
        }        
        return baseIFaces;
    }
    
    
    /* 11 */
    public List parseScopedNames(ScopedName id)
    {
        getLogger().fine("11: scoped_name = " + id);
        List l = new ArrayList();
        l.add(id);
        return l;
    }
    
    public List parseScopedNames(ScopedName id, List scopedNames)
    {
        getLogger().fine("11: scoped_name T_COMMA scoped_names = " + id + ", " + scopedNames);
        scopedNames.add(id);
        return scopedNames;
    }
    
    
    /* 12 */
    public ScopedName parseScopedName(String id)
    {
        return new ScopedName(id);
    }

    public ScopedName parseScopedName(ScopedName scopedName, String id)
    {
        return new ScopedName(scopedName + "::" + id);
    }

    
    /* 14 */
    public MValueDef parseValueForwardDeclaration(String id)
    {
        getLogger().fine("14: valuetype T_IDENTIFIER = " + id);        
        MValueDef value = new MValueDefImpl();
        value.setIdentifier(id); 
        value.setSourceFile(getIncludedSourceFile());
        ScopedName identifier = new ScopedName(getScope(), id);        
        getModelRepository().registerForwardDeclaration(identifier);
        getModelRepository().registerIdlType(identifier, value);
        return value;
    }
    
    public MValueDef parseAbstractValueForwardDeclaration(String id)
    {
        getLogger().fine("14: abstract valuetype T_IDENTIFIER = " + id); 
        MValueDef value = parseValueForwardDeclaration(id);
        value.setAbstract(true);
        return value;
    }
    
    /* 15 */
    public MValueBoxDef parseValueBoxDeclaration(String id, MIDLType idlType)    
    {
        getLogger().fine("15: T_VALUETYPE T_IDENTIFIER type_spec = " + id + ", " + idlType);
        registerTypeId(id);
        MValueBoxDef value = new MValueBoxDefImpl();
        value.setIdentifier(id);
        value.setSourceFile(getIncludedSourceFile());
        value.setIdlType(idlType);
        return value;
    }
    
    
    /* 16 */
    public MValueDef parseValueAbstractDeclaration(String id, List elementList)
    {
        getLogger().fine("16: T_ABSTRACT T_VALUETYPE T_IDENTIFIER:id = " + id);        
        ScopedName identifier = new ScopedName(getScope(), id);
        MValueDef value;
        if(getModelRepository().isForwardDeclaration(identifier))
        {
            MIDLType forwardDcl = getModelRepository().findIdlType(identifier); 
            value = (MValueDef)forwardDcl;
        }
        else
        {
            value = new MValueDefImpl();
            getModelRepository().registerIdlType(identifier, value);
        }
        setAbstractValueDefinition(value, id, elementList);
        return value;
    }
        
    public MValueDef parseValueAbstractDeclaration(String id, MValueDef value, List elementList)
    {
        getLogger().fine("16: abstract valuetype T_IDENTIFIER value_inheritance_spec { value_body:b } = " 
                + id  +", " + value + ", " + elementList);
        setAbstractValueDefinition(value, id, elementList);        
        return value;
    }
    
    private void setAbstractValueDefinition(MValueDef value, String id, List elementList)
    {
        ScopedName identifier = new ScopedName(getScope(), id);
        value.setIdentifier(id);
        value.setAbstract(true);
        Collections.reverse(elementList);
        value.setContentss(elementList);
        getModelRepository().registerIdlType(identifier, value);
    }
    
    
    
    /* 17 */
    public MValueDef parseValueDeclaration(MValueDef value, List elementList)
    {
        getLogger().fine("17: value_header { value_elements } = " + value + ", " + elementList);
        String id = value.getIdentifier();
        ScopedName identifier = new ScopedName(getScope(), id);
        registerTypeId(id);
        Collections.reverse(elementList);
        value.setContentss(elementList);
        getModelRepository().registerIdlType(identifier, value);
        return value;
    }
    
    public MValueDef parseValueDeclaration(MValueDef value)
    {
        getLogger().fine("17: value_header { } = " + value);
        String id = value.getIdentifier();
        ScopedName identifier = new ScopedName(getScope(), id);
        registerTypeId(id);
        getModelRepository().registerIdlType(identifier, value);
        return value;
    }
    
    public List parseValueElements(Object element)
    {
        getLogger().fine("17: value_element = " + element);
        List elementList = new ArrayList();
        return parseValueElements(element, elementList);
    }
    
    public List parseValueElements(Object element, List elementList)
    {
        getLogger().fine("17: value_element value_elements = " + element);
        if(element instanceof List)
        {
            List l = (List)element;
            elementList.addAll(l);
        }
        else
        {
            elementList.add(element);
        }
        return elementList;
    }
    
    
    
    
    /* 18 */
    public MValueDef parseValueHeader(String id, MValueDef inheritanceValue)
    {
        getLogger().fine("18: T_VALUETYPE T_IDENTIFIER value_inheritance_spec = " + id + ", " + inheritanceValue);
        MValueDef value = parseValueHeader(id);
        value.setBase(inheritanceValue.getBase());
        value.setAbstractBases(inheritanceValue.getAbstractBases());    
        value.setInterfaceDefs(inheritanceValue.getInterfaceDefs());
        value.setTruncatable(inheritanceValue.isTruncatable());
        return value;
    }
    
    public MValueDef parseCustomValueHeader(String id, MValueDef inheritanceValue)
    {
        getLogger().fine("18: T_CUSTOM T_VALUETYPE T_IDENTIFIER value_inheritance_spec = " + id + ", " + inheritanceValue);        
        inheritanceValue.setCustom(true);
        return inheritanceValue;
    }

    public MValueDef parseValueHeader(String id)
    {
        getLogger().fine("18: T_VALUETYPE T_IDENTIFIER = " + id);
        ScopedName identifier = new ScopedName(getScope(), id);
        MValueDef value;
        if(getModelRepository().isForwardDeclaration(identifier))
        {
            MIDLType forwardDcl = getModelRepository().findIdlType(identifier); 
            value = (MValueDef)forwardDcl;
        }
        else
        {
            value = new MValueDefImpl();
            getModelRepository().registerIdlType(identifier, value);
        }
        value.setIdentifier(id);
        return value;
    }
        
    public MValueDef parseCustomValueHeader(String id)
    {
        getLogger().fine("18: T_CUSTOM T_VALUETYPE T_IDENTIFIER = " + id);
        MValueDef value = parseValueHeader(id);
        return parseCustomValueHeader(id, value);
    }
    
    
    /* 19 */    
    public MValueDef parseValueSupportsInterfaces(List supports)
    {
        getLogger().fine("19: T_SUPPORTS interface_names = " + supports);
        MValueDef value = new MValueDefImpl();
        return parseValueSupportsInterfaces(value, supports);
    }
    
    public MValueDef parseValueSupportsInterfaces(MValueDef value, List supports)
    {
        getLogger().fine("19:  T_COLON value_inheritance_bases T_SUPPORTS interface_names = " + value + ", " + supports);
        Collections.reverse(supports);
        for(Iterator i = supports.iterator(); i.hasNext();)
        {
            ScopedName id = (ScopedName)i.next();
            MIDLType idlType = getModelRepository().findIdlType(getScope(), id);
            if(idlType instanceof MInterfaceDef)
            {
                value.addInterfaceDef((MInterfaceDef)idlType);
            }
            else
            {
                reportError("Only interface types can be supported, " + id + " is not an interface!");
            }
        }
        return value;
    }
    
    public MValueDef parseValueInheritanceBases(ScopedName id)
    {
        getLogger().fine("19: value_name = " + id);
        MValueDef value = new MValueDefImpl();
        setValueBase(value, id);
        return value;        
    }
    

    public MValueDef parseValueInheritanceBases(ScopedName id, List ids)
    {
        getLogger().fine("19: value_name T_COMMA value_names = " + id + ", " + ids);
        MValueDef value = new MValueDefImpl();
        setValueBase(value, id);
        for(Iterator i = ids.iterator(); i.hasNext();)
        {
            setValueBase(value, (ScopedName)i.next());
        }        
        return value;        
    }

    public MValueDef parseTruncatableValueInheritanceBases(ScopedName id)
    {
        getLogger().fine("19: T_TRUNCATABLE value_name = " + id);
        MValueDef value = parseValueInheritanceBases(id);
        value.setTruncatable(true);
        return value;        
    }

    public MValueDef parseTruncatableValueInheritanceBases(ScopedName id, List ids)
    {
        getLogger().fine("19: T_TRUNCATABLE value_name T_COMMA value_names = " + id + ", " + ids);
        MValueDef value = parseValueInheritanceBases(id, ids);
        value.setTruncatable(true);            
        return value;        
    }
    
    private void setValueBase(MValueDef value, ScopedName id)
    {
        MIDLType idlType = getModelRepository().findIdlType(getScope(), id);
        if(idlType instanceof MValueDef)
        {
            MValueDef base = (MValueDef)idlType;
            if(base.isAbstract())
            {
                // multiple inheritance for abstract values
                value.addAbstractBase(base);
            }
            else
            {
                // single inheritance for stateful values
                if(value.getBase() == null)
                {
                    value.setBase(base);
                }
                else
                {
                    reportError("A valuetype can only inherit from a single valuetype (e.g. " + id + ")!");
                }
            }
        }
        else
        {
            reportError("Can't inherit from a type other than valuetype (e.g. " + id + ")!");
        }
    }
    
    
    /* 22 */
    
    public List parsePublicStateMember(MIDLType idlType, List declarators)
    {
        getLogger().fine("22: public type_spec declarators ; = " + idlType + ", " + declarators);
        return setStateMember(idlType, declarators, true);
    }
    
    public List parsePrivateStateMember(MIDLType idlType, List declarators)
    {
        getLogger().fine("22: private type_spec declarators ; = " + idlType + ", " + declarators);
        return setStateMember(idlType, declarators, false);        
    }
    
    private List setStateMember(MIDLType idlType, List declarators, boolean isPublic)
    {    
        List members = new ArrayList();        
        for(Iterator i = declarators.iterator(); i.hasNext();)
        {
            Declarator d = (Declarator)i.next();        
            MValueMemberDef member = new MValueMemberDefImpl();
            member.setIdlType(idlType);            
            member.setIdentifier(d.toString()); // ?? Array Declarator siehe (42)
            member.setPublicMember(isPublic);
            members.add(member);
        }
        return members;            
    }
    
    
    /* 23 */
    public MFactoryDef parseInitDeclaration(String id)
    {
        getLogger().fine("23: factory T_IDENTIFIER ( ); = " + id);
        MFactoryDef factory = new MFactoryDefImpl();
        factory.setIdentifier(id);
        factory.setSourceFile(getIncludedSourceFile());
        return factory;
    }
    
    public MFactoryDef parseInitDeclaration(String id, List parameters)
    {
        getLogger().fine("23: factory T_IDENTIFIER ( init_param_decls ); = " + id + ", " + parameters);
        MFactoryDef factory = new MFactoryDefImpl();
        factory.setIdentifier(id);
        factory.setSourceFile(getIncludedSourceFile());
        Collections.reverse(parameters);
        factory.setParameters(parameters);
        return factory;
    }    
    
    
    /* 24 */
    public List parseInitParameterDeclarations(MParameterDef parameter)
    {
        getLogger().fine("24: init_param_decl = " + parameter);
        List parameterList = new ArrayList();
        return parseInitParameterDeclarations(parameter, parameterList);
    }

    public List parseInitParameterDeclarations(MParameterDef parameter, List parameterList)
    {
        getLogger().fine("24: init_param_decl , init_param_decls = " + parameter + ", " + parameterList);
        parameterList.add(parameter);
        return parameterList;
    }
    
    
    /* 25 */
    public MParameterDef parseInitParameterDeclaration(MIDLType idlType, Declarator declarator)
    {
        getLogger().fine("25: init_param_attribute param_type_spec simple_declarator = " + idlType + ", " + declarator);
        MParameterDef parameter = new MParameterDefImpl();
        parameter.setIdentifier(declarator.toString());
        parameter.setIdlType(idlType);
        parameter.setDirection(MParameterMode.PARAM_IN);
        return parameter;
    }
    
    
    /* 27 */
    public MConstantDef parseConstDcl(MIDLType constType, String identifier, Object constExpr)
    {
        getLogger().fine("27: T_EQUAL const_exp = " + constType + ", " + identifier + ", " + constExpr);
        MConstantDef constant = new MConstantDefImpl();
        constant.setIdentifier(identifier);
        constant.setSourceFile(getIncludedSourceFile());
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
                reportError("String literal " + identifier + " is out of bound (max. "
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
                reportError("Wide string literal " + identifier + " is out of bound (max. "
                        + type.getBound() + " characters)!");
            }
            constant.setIdlType(type);
            constant.setConstValue(value);          
        }
        else if(constType instanceof MEnumDef)
        {
            MEnumDef type = (MEnumDef)constType;
            if(constExpr instanceof ScopedName)
            {
                // check enum constExpr
                constant.setIdlType(type);
                constant.setConstValue(constExpr);
            }
            else
            {
                reportError("Unknown enum literal " + constExpr);
            }
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
    
    
    /* 38 */
    public ScopedName parsePrimaryExpr(ScopedName scopedName)
    {
        getLogger().fine("38: scoped_name = " + scopedName);
        return scopedName;
    }
    
    
    /* 41 */
    public Integer parsePositiveIntConst(Object constExp)
    {
        getLogger().fine("41: const_exp = " + constExp);     
        Integer result = null;
        if(constExp instanceof Integer)
        {
            result = (Integer)constExp;
        }
        else
        {
            reportError(constExp + " is not an integer constant!");
        }
        return result;
    }
    
    
    /* 42 */
    public MTypedefDef parseTypeDcl(MIDLType type, List declarators)
    {
        getLogger().fine("42: T_TYPEDEF type_spec declarators = " + type + " " + declarators);
        if(declarators == null)
        {
            reportError("Empty declarators list!");
        }
        
        MAliasDef alias = new MAliasDefImpl();
        Declarator declarator = (Declarator)declarators.get(0);
        ScopedName id = new ScopedName(getScope(), declarator.toString());
        alias.setIdentifier(declarator.toString());
        alias.setSourceFile(getIncludedSourceFile());
        if(declarator instanceof ArrayDeclarator)
        {
            MArrayDef array = ((ArrayDeclarator)declarator).getArray();
            array.setIdlType(type);
            alias.setIdlType(array);
            getModelRepository().registerIdlType(id, array);
        }
        else
        {
            alias.setIdlType(type);
            getModelRepository().registerIdlType(id, alias);
        }   
        
        return alias;
    }
    
    public MNativeDef parseNativeTypeDcl(Declarator declarator)
    {
        getLogger().fine("42:T_NATIVE simple_declarator = " + declarator);        
        MNativeDef type = new MNativeDefImpl();  
        type.setSourceFile(getIncludedSourceFile());
        type.setNativeType(declarator.toString());
        ScopedName id = new ScopedName(getScope(), declarator.toString());
        getModelRepository().registerIdlType(id, type);
        return type;
    }
    
    
    /* 45 */
    public MIDLType parseScopedName(ScopedName scopedName)
    {
        getLogger().fine("45: scoped_name = " + scopedName);        
        return getModelRepository().findIdlType(getScope(), scopedName);
    }
    
    
    /* 49 */
    public List parseDeclarators(Declarator declarator)
    {
        getLogger().fine("49: declarator  = " + declarator);
        List l = new ArrayList();
        l.add(declarator);
        return l;
    }

    public List parseDeclarators(Declarator declarator, List declarators)
    {
        getLogger().fine("49: declarator T_COMMA declarators = " + declarator + " " + declarators);
        declarators.add(declarator);
        return declarators;
    }
    
    
    /* 51 */
    public Declarator parseSimpleDeclarator(String identifier)
    {
        getLogger().fine("51: T_IDENTIFIER = " + identifier);
//        helper.registerTypeId(id);
        return new Declarator(identifier);
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
        MPrimitiveDef type = new MPrimitiveDefImpl();
        type.setKind(MPrimitiveKind.PK_ANY);
        return type; 
    }
    
    
    /* 68 */
    public MPrimitiveDef parseObjectType()
    {
        getLogger().fine("68: T_OBJECT");            
        MPrimitiveDef type = new MPrimitiveDefImpl();
        type.setKind(MPrimitiveKind.PK_OBJREF);
        return type; 
    }
    

    /* 69 */
    public MStructDef parseStructType(String id, List memberList)
    {
        getLogger().fine("69: T_LEFT_CURLY_BRACKET member_list T_RIGHT_CURLY_BRACKET");  
        ScopedName identifier = new ScopedName(getScope(), id);
        MStructDef type;
        if(getModelRepository().isForwardDeclaration(identifier))
        {
            MIDLType forwardDcl = getModelRepository().findIdlType(identifier); 
            type = (MStructDef)forwardDcl;
        }
        else
        {
            type = new MStructDefImpl();
            getModelRepository().registerIdlType(identifier, type);
        }
        type.setIdentifier(id);
        type.setSourceFile(getIncludedSourceFile());
        Collections.reverse(memberList);
        type.setMembers(memberList);
        return type;
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
        MFieldDef field = new MFieldDefImpl();
        field.setIdlType(typeSpec);
        if(declarators.size() > 0)
        {
            Declarator declarator = (Declarator)declarators.get(0);
            field.setIdentifier(declarator.toString());
        }
        else
        {
            reportError("No declarators defined for member type " + typeSpec);
        }
        return field;
    }
    
        
    /* 72 */
    public MUnionDef parseUnionType(String id, MIDLType discriminatorType, List switchBody)
    {
        getLogger().fine("72: union_type = " + id );
        ScopedName identifier = new ScopedName(getScope(), id);
        MUnionDef type;
        if(getModelRepository().isForwardDeclaration(identifier))
        {
            MIDLType forwardDcl = getModelRepository().findIdlType(identifier); 
            type = (MUnionDef)forwardDcl;
        }
        else
        {
            type = new MUnionDefImpl();
            getModelRepository().registerIdlType(identifier, type);
        }
        type.setIdentifier(id);
        type.setSourceFile(getIncludedSourceFile());
        type.setDiscriminatorType(discriminatorType);
        Collections.reverse(switchBody);
        type.setUnionMembers(switchBody);
        getModelRepository().registerIdlType(identifier, type);
        return type;
    }
   
    
    /* 74 */
    public List parseSwitchBody(MUnionFieldDef c)
    {
        getLogger().fine("74: case = " + c );
        List l = new ArrayList();
        l.add(c);
        return l;
    }
    
    public List parseSwitchBody(MUnionFieldDef c, List l)
    {
        getLogger().fine("74: case switch_body = " + c + ", " + l);
        l.add(c);
        return l;
    }
    
    
    /* 75 */
    public MUnionFieldDef parseCase(Object label, MUnionFieldDef element)
    {
        getLogger().fine("75: case = " + label + ", " + element);
        if(element.getLabel() == null)
        {
            element.setLabel(label);
        }
        else
        {
            List labels = new ArrayList();
            labels.add(label);
            // If there are multiple labels for the same type, a list of
            // lebels will be generated.
            if(element.getLabel() instanceof List)
            {
                labels.addAll((List)element.getLabel());
            }
            else
            {
                labels.add(element.getLabel());
            }
            element.setLabel(labels);
        }
        return element;
    }
        
    
    /* 77 */
    public MUnionFieldDef parseElementSpec(MIDLType type, Declarator declarator)
    {
        getLogger().fine("77: element_spec = " + type + ", " + declarator);  
        MUnionFieldDef field = new MUnionFieldDefImpl();
        field.setIdentifier(declarator.toString());
        field.setIdlType(type);
        //...
        return field;
    }
    
    
    
    /* 78 */
    public MEnumDef parseEnumType(String id, List enumerators)
    {
        getLogger().fine("78: enumerators = " + enumerators);  
        ScopedName identifier = new ScopedName(getScope(), id);
        MEnumDef enumeration = new MEnumDefImpl();
        enumeration.setIdentifier(id);
        enumeration.setSourceFile(getIncludedSourceFile());
        if(enumerators.size() > 0)
        {
            Collections.reverse(enumerators);
            enumeration.setMembers(enumerators);
            for(Iterator i = enumerators.iterator(); i.hasNext();)
            {
                String enumerator = (String)i.next();
                getModelRepository().registerIdlType(new ScopedName(getScope(), enumerator), enumeration); 
            }
        }
        else
        {
            reportError("Enumeration " + id + " has no members!");
        }           
        getModelRepository().registerIdlType(identifier, enumeration);
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
    
    
    /* 83 */
    public Declarator parseArrayDeclarator(String id, List fixed_array_sizes)
    {
        MArrayDef array = new MArrayDefImpl();
        Collections.reverse(fixed_array_sizes);
        array.setBounds(fixed_array_sizes);
        return new ArrayDeclarator(id,array);
    }
    
    public List parseFixedArraySizes(Integer fixedArraySize)
    {
        getLogger().fine("83: fixed_array_size = " + fixedArraySize);
        List l = new ArrayList();
        l.add(fixedArraySize);        
        return l;
    }
    
    public List parseFixedArraySizes(Integer fixedArraySize, List l)
    {
        getLogger().fine("83: fixed_array_sizes = " + fixedArraySize);
        l.add(fixedArraySize);        
        return l;
    }
    
    
    
    /* 86 */
    public MExceptionDef parseExceptionDcl(String id, List members) 
    {
        getLogger().fine("86: T_LEFT_CURLY_BRACKET members T_RIGHT_CURLY_BRACKET");  
        ScopedName identifier = new ScopedName(getScope(), id);
        MExceptionDef type = new MExceptionDefImpl();
        type.setIdentifier(id);
        type.setSourceFile(getIncludedSourceFile());
        Collections.reverse(members);
        type.setMembers(members);
        getModelRepository().registerException(identifier, type);
        return type;
    }
        
    
    /* 87 */
    public MOperationDef parseOperationWithExceptionsAndContext(Boolean isOneway, MIDLType type, String id, 
                                                    List parameters, List exceptions, List contexts)    
    {
        getLogger().fine("87: op_attribute op_type_spec T_IDENTIFIER parameter_dcls raises_expr context_expr");
        MOperationDef operation = parseOperationWithExceptions(isOneway,type,id,parameters,exceptions);
        operation.setContexts(Text.joinList(" ", contexts));              
        return operation;
    }
        
    public MOperationDef parseOperationWithContext(Boolean isOneway, MIDLType type, String id, 
                                                    List parameters, List contexts)
    {
        getLogger().fine("87: op_attribute op_type_spec T_IDENTIFIER parameter_dcls context_expr " + contexts);
        MOperationDef operation = parseOperation(isOneway,type,id,parameters);
        operation.setContexts(Text.joinList(" ", contexts));              
        return operation;    
    }
    
    public MOperationDef parseOperationWithExceptions(Boolean isOneway, MIDLType type, String id, 
                                                    List parameters, List exceptions)
    {
        getLogger().fine("87: op_attribute op_type_spec T_IDENTIFIER parameter_dcls raises_expr");
        MOperationDef operation = parseOperation(isOneway, type, id, parameters);
        Collections.reverse(exceptions);
        operation.setExceptionDefs(exceptions);
        return operation;
    }
    
    public MOperationDef parseOperation(Boolean isOneway, MIDLType type, String id, List parameters)
    {
        getLogger().fine("87: op_attribute op_type_spec T_IDENTIFIER parameter_dcls");
        MOperationDef operation = new MOperationDefImpl();
        operation.setOneway(isOneway.booleanValue());        
        operation.setIdlType(type);
        operation.setIdentifier(id);
        operation.setSourceFile(getIncludedSourceFile());
        Collections.reverse(parameters);
        operation.setParameters(parameters);
        // TODO: Semantic checks should be placed into the metamodel !!!!!!! 
        if(operation.isOneway() && !isVoidOperation(operation))
        {
            reportError("An oneway operation must not return a value (e.g. " + operation.getIdlType() + ")!");
        }
        // TODO: Semantic checks should be placed into the metamodel !!!!!!!
        return operation;
    }
    
    
    private boolean isVoidOperation(MOperationDef op)
    {
        if(op.getIdlType() instanceof MPrimitiveDef)
        {
            MPrimitiveDef type = (MPrimitiveDef)op.getIdlType();
            if(type.getKind() == MPrimitiveKind.PK_VOID)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    
    /* 89 */
    public MIDLType parseOpTypeVoid()
    {
        MPrimitiveDef type = new MPrimitiveDefImpl();
        type.setKind(MPrimitiveKind.PK_VOID);
        return type;
    }
    
    /* 90 */
    public List parseParameterDcls()
    {
        getLogger().fine("90: T_LEFT_PARANTHESIS T_RIGHT_PARANTHESIS");  
        List l = new ArrayList();
        return l;
    }
    
    public List parseParameterDcls(MParameterDef parameter)
    {
        getLogger().fine("90: param_dcl = "  + parameter);  
        List l = new ArrayList();
        l.add(parameter);
        return l;
    }
    
    public List parseParameterDcls(MParameterDef parameter, List l)
    {
        getLogger().fine("90: param_dcl T_COMMA param_dcls = " + parameter + ", " + l);
        l.add(parameter);
        return l;
    }
    
    
    /* 91 */
    public MParameterDef parseParameterDcl(MParameterMode mode, MIDLType type, Declarator id)
    {
        getLogger().fine("91: param_attribute param_type_spec simple_declarator = " + mode + ", " + type + ", " + id);  
        MParameterDef parameter = new MParameterDefImpl();
        parameter.setDirection(mode);
        parameter.setIdlType(type);
        parameter.setIdentifier(id.toString());
        return parameter;
    }
    
    
    /* 94?? */
    public List parseContextExpr(List literals)
    {
        Collections.reverse(literals);
        return literals;
    }
    
    public List parseStringLiterals(String literal)
    {
        List literals = new ArrayList();
        literals.add(literal);
        return literals;
    }
    
    public List parseStringLiterals(String literal, List literals)
    {
        literals.add(literal);
        return literals;
    }
    
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
    
    
    /* 95 */
    public MIDLType parseParameterTypeSpec(ScopedName scopedName)
    {
        getLogger().fine("95: param_type_spec scoped_name " + scopedName);        
        return getModelRepository().findIdlType(getScope(), scopedName);
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
    
    
    /* 99 A (from 2.4, not in CCM) */
    public MStructDef parseStructForwardDeclaration(String id)
    {
        getLogger().fine("99: T_STRUCT T_IDENTIFIER = " + id);            
        MStructDef type = new MStructDefImpl();
        type.setIdentifier(id);  
        ScopedName identifier = new ScopedName(getScope(), id);        
        getModelRepository().registerForwardDeclaration(identifier);
        getModelRepository().registerIdlType(identifier, type);
        return type;
    }

    public MUnionDef parseUnionForwardDeclaration(String id)
    {
        getLogger().fine("99: T_UNION T_IDENTIFIER = " + id);    
        MUnionDef type = new MUnionDefImpl();
        type.setIdentifier(id);        
        ScopedName identifier = new ScopedName(getScope(), id);
        getModelRepository().registerForwardDeclaration(identifier);
        getModelRepository().registerIdlType(identifier, type);
        return type;
    }
    
    
    /* 104 */
    public List parseReadonlyAttrSpec(MIDLType type, List declarators)
    {
        getLogger().fine("104: T_READONLY T_ATTRIBUTE param_type_spec readonly_attr_declarator = " + type + ", " + declarators);
        List attributes = new ArrayList();
        for(Iterator i=declarators.iterator(); i.hasNext();)
        {
            Declarator id = (Declarator)i.next();
            MAttributeDef attr = new MAttributeDefImpl();
            attr.setIdentifier(id.toString());
            attr.setIdlType(type);
            attr.setReadonly(true);
            if(id instanceof ReadonlyAttributeDeclarator)
            {
                ReadonlyAttributeDeclarator attrDeclarator = (ReadonlyAttributeDeclarator)id;
                Collections.reverse(attrDeclarator.getExceptions());
                attr.setGetExceptions(attrDeclarator.getExceptions());
            }
            attributes.add(attr);
        }
        return attributes;
    }
    
    
    /* 105 */
    public List parseReadonlyAttrDeclarator(Declarator simpleDeclarator, List exceptions)
    {
        getLogger().fine("105: simple_declarator:d raises_expr:r = " + simpleDeclarator + ", " + exceptions);
        List result = new ArrayList();
        result.add(new ReadonlyAttributeDeclarator(simpleDeclarator, exceptions));
        return result;        
    }   
    
    public List parseSimpleDeclarators(Declarator simpleDeclarator)
    {
        getLogger().fine("105: simple_declarator = " + simpleDeclarator);   
        List declarators = new ArrayList();
        declarators.add(simpleDeclarator);
        return declarators;
    }
        
    public List parseSimpleDeclarators(Declarator simpleDeclarator, List declarators)
    {
        getLogger().fine("105: simple_declarator T_COMMA simple_declarators = " + simpleDeclarator + ", " + declarators); 
        declarators.add(simpleDeclarator);
        return declarators;
    }
    
    
    /* 106 */
    public List parseAttrSpec(MIDLType type, List declarators)
    {
        getLogger().fine("106: T_ATTRIBUTE param_type_spec attr_declarator = " + type + ", " + declarators);
        List attributes = new ArrayList();
        for(Iterator i=declarators.iterator(); i.hasNext();)
        {
            Declarator id = (Declarator)i.next();
            MAttributeDef attr = new MAttributeDefImpl();
            attr.setIdentifier(id.toString());
            attr.setIdlType(type);
            if(id instanceof AttributeDeclarator)
            {
                AttributeDeclarator attrDeclarator = (AttributeDeclarator)id;
                Collections.reverse(attrDeclarator.getGetterExceptions());
                attr.setGetExceptions(attrDeclarator.getGetterExceptions());
                Collections.reverse(attrDeclarator.getSetterExceptions());                
                attr.setSetExceptions(attrDeclarator.getSetterExceptions());
            }
            attributes.add(attr);
        }
        return attributes;
    }
    
    /* 107 */
    public List parseAttrDeclarator(Declarator declarator, AttributeRaisesExpression raisesExpr)
    {
        getLogger().fine("107: simple_declarator attr_raises_expr = " + declarator + ", " + raisesExpr);        
        List result = new ArrayList();
        result.add(new AttributeDeclarator(declarator, raisesExpr));
        return result;
    }
    
    
    /* 108 */
    public AttributeRaisesExpression parseAttrGetRaisesExpr(List exceptions)
    {
        getLogger().fine("108: get_excep_expr = " + exceptions);
        return new AttributeRaisesExpression(exceptions, new ArrayList());
    }
    
    public AttributeRaisesExpression parseAttrSetRaisesExpr(List exceptions)
    {
        getLogger().fine("108: set_excep_expr = " + exceptions);
        return new AttributeRaisesExpression(new ArrayList(), exceptions);
    }
    
    public AttributeRaisesExpression parseAttrGetAndSetRaisesExpr(List getExceptions, List setExceptions)
    {
        getLogger().fine("108: get_excep_expr set_excep_expr = " + getExceptions + ", " + setExceptions);
        return new AttributeRaisesExpression(getExceptions, setExceptions);
    }
    
    
    /* 111 *//* 93 */
    public List parseExceptionList(List scopedNames)
    {
        getLogger().fine("111: ( scoped_names )" + scopedNames);
        List exceptions = new ArrayList();
        for(Iterator i = scopedNames.iterator(); i.hasNext();)
        {            
            ScopedName id = (ScopedName)i.next();
            MExceptionDef ex = getModelRepository().findIdlException(getScope(),id);
            if(ex == null)
            {
                reportError("Declared exception " + id + "not found!");
            }
            else
            {
                exceptions.add(ex);
            }
        }        
        return exceptions;
    }
    
    
    /* 112 */
    public MComponentDef parseComponentForwardDeclaration(String id)
    {
        getLogger().fine("112: (" + id + ")");     
        MComponentDef component = new MComponentDefImpl();
        component.setIdentifier(id);
        component.setSourceFile(getIncludedSourceFile()); 

        ScopedName identifier = new ScopedName(getScope(), id);
        getModelRepository().registerForwardDeclaration(identifier);
        getModelRepository().registerIdlType(identifier, component);
        return component;
    }
    
    
    /* 113 */
    public MComponentDef parseComponentDeclaration(MComponentDef component)
    {
        getLogger().fine("113: (" + component + ")");        
        String id = component.getIdentifier();
        ScopedName identifier = new ScopedName(getScope(), component.getIdentifier());
        registerTypeId(id);
        getModelRepository().registerIdlType(identifier, component);
        return component;
    }
    
    public MComponentDef parseComponentDeclaration(MComponentDef component, List body)
    {
        getLogger().fine("113: (" + component + ", " + body + ")");
        Collections.reverse(body);
        for(Iterator i = body.iterator(); i.hasNext();)
        {
            Object port = i.next();
            if(port instanceof MProvidesDef)
            {
                MProvidesDef facet = (MProvidesDef)port;
                facet.setDefinedIn(component);
                facet.setComponent(component);
                component.addFacet(facet);
            }
            else if(port instanceof MUsesDef)
            {
                MUsesDef receptacle = (MUsesDef)port;
                receptacle.setDefinedIn(component);
                receptacle.setComponent(component);
                component.addReceptacle(receptacle);
            }
            // TODO: handle other ports !!!!!
            else if(port instanceof List) // handle component attributes
            {                                
                List content = (List)port;
                for(Iterator j=content.iterator(); j.hasNext(); )
                {
                    MContained contained = (MContained)j.next();
                    contained.setDefinedIn(component);
                    component.addContents(contained);
                }
            }
        }        
        String id = component.getIdentifier();
        ScopedName identifier = new ScopedName(getScope(), component.getIdentifier());
        registerTypeId(id);
        getModelRepository().registerIdlType(identifier, component);
        return component;
    }
    
    
    /* 114 */
    public MComponentDef parseComponentHeader(String id)
    {
        getLogger().fine("114: (" + id + ")");
        ScopedName identifier = new ScopedName(getScope(), id);
        MComponentDef component;
        if(getModelRepository().isForwardDeclaration(identifier))
        {
            MIDLType forwardDcl = getModelRepository().findIdlType(identifier);
            component = (MComponentDef)forwardDcl;
        }
        else
        {
            component = new MComponentDefImpl();
            getModelRepository().registerIdlType(identifier, component);
        }
        component.setIdentifier(id);
        component.setSourceFile(getIncludedSourceFile());        
        return component;
    }
    
    public MComponentDef parseComponentHeader(String id, ScopedName base)
    {
        getLogger().fine("114: (" + id + "," + base + ")");
        MComponentDef component = parseComponentHeader(id);
        MIDLType type = getModelRepository().findIdlType(getScope(), base);
        if(type == null)
        {
            reportError("Base component " + base + " not found!");
        }
        else if(!(type instanceof MComponentDef))
        {
            reportError("Base component specification " + base + " isn't a component!");            
        }        
        else
        {
            component.addBase((MInterfaceDef)type);
        }
        return component;
    }
    
    
    public MComponentDef parseComponentHeader(String id, List ifaces)
    {
        getLogger().fine("114: (" + id + "," + ifaces + ")");
        MComponentDef component = parseComponentHeader(id);
        Collections.reverse(ifaces);
        component.setSupportss(ifaces);
        return component;
    }
    
    
    public MComponentDef parseComponentHeader(String id, ScopedName base, List ifaces)
    {
        getLogger().fine("114: (" + id + "," + ifaces + ")");
        MComponentDef component = parseComponentHeader(id, base);
        Collections.reverse(ifaces);
        component.setSupportss(ifaces);
        return component;
    }
    
    
    /* 115 */
    public List parseSupportedInterfaceSpec(List scopedNames)
    {
        getLogger().fine("115: supports interface_names = " + scopedNames);
        List ifaces = new ArrayList();
        for(Iterator i = scopedNames.iterator(); i.hasNext();)
        {
            ScopedName id = (ScopedName)i.next();
            MIDLType type = getModelRepository().findIdlType(getScope(), id); 
            if(type == null)
            {
                reportError("Supported interface " + id + "not found!" );
            }
            else if(!(type instanceof MInterfaceDef))
            {
                reportError("Supported interface specification contains " + type + "which is not an interface!");
            }
            else
            {
                ifaces.add(type);
            }            
        }        
        return ifaces;       
    }
    
    
    /* 117 */
    public List parseComponentBody(Object exportElement)
    {
        List body = new ArrayList();
        body.add(exportElement);
        return body;
    }
        
    public List parseComponentBody(Object exportElement, List body)
    {
        body.add(exportElement);
        return body;
    }

        
    /* 119 */
    public MProvidesDef parseProvidesDeclaration(ScopedName ifaceType, String id)
    {
        MProvidesDef provides = new MProvidesDefImpl();
        provides.setIdentifier(id);
        provides.setSourceFile(getIncludedSourceFile()); 
        MIDLType type = getModelRepository().findIdlType(getScope(), ifaceType);
        if(type == null)
        {
            reportError("Provided interface type " + ifaceType + " not found!");
        }
        else if(!(type instanceof MInterfaceDef))
        {
            reportError("Provided type " + ifaceType + " isn't an interface!");
        }
        else
        {
            MInterfaceDef iface = (MInterfaceDef)type;
            provides.setProvides(iface);            
        }        
        return provides;
    }
    
    
    /* 120 */
    public ScopedName parseInterfaceType()
    {
        return new ScopedName("Object");
    }
    
    
    /* 121 */
    public MUsesDef parseUsesDeclaration(ScopedName ifaceType, String id)
    {
        MUsesDef uses = new MUsesDefImpl();
        uses.setIdentifier(id);
        uses.setMultiple(false);
        MIDLType type = getModelRepository().findIdlType(getScope(),ifaceType);
        if(type == null)
        {
            reportError("Used interface type " + ifaceType + " not found!");
        }
        else if(!(type instanceof MInterfaceDef))
        {
            reportError("Used type " + ifaceType + " isn't an interface!");
        }
        else
        {
            MInterfaceDef iface = (MInterfaceDef)type;
            uses.setUses(iface);            
        }        
        return uses;
    }
    
    public MUsesDef parseMultipleUsesDeclaration(ScopedName ifaceType, String id)
    {
        MUsesDef uses = parseUsesDeclaration(ifaceType,id);
        uses.setMultiple(true);
        return uses;
    }
    
    
    /* 125 */
    public MHomeDef parseHomeDeclaration(MHomeDef home, List body)
    {
        getLogger().fine("125: home_header home_body = " + home + ", " + body);
        Collections.reverse(body);
        for(Iterator i = body.iterator(); i.hasNext();)
        {
            MContained content = (MContained)i.next();
            if(content instanceof MFactoryDef)
            {
                MFactoryDef factory = (MFactoryDef)content;
                factory.setHome(home);
                home.addFactory(factory);
            }
            else if(content instanceof MFinderDef)
            {
                MFinderDef finder = (MFinderDef)content;
                finder.setHome(home);
                home.addFinder(finder);
            }
            else
            {
                content.setDefinedIn(home);
                home.addContents(content);
            }
        }
        String id = home.getIdentifier();
        ScopedName identifier = new ScopedName(getScope(), id);
        registerTypeId(id);
        getModelRepository().registerIdlType(identifier, home);
        return home;
    }
    
    
    
    /* 126 */
    public MHomeDef parseHomeHeader(String id, ScopedName componentId)
    {
        getLogger().fine("126: home id manages component = " + id + ", " + componentId);
        ScopedName identifier = new ScopedName(getScope(), id);
        MHomeDef home;
        if(getModelRepository().isForwardDeclaration(identifier))
        {
            home = (MHomeDef)getModelRepository().findIdlType(identifier);
        }
        else
        {
            home = new MHomeDefImpl();
            getModelRepository().registerIdlType(identifier, home);
        }
        home.setIdentifier(id);
        home.setSourceFile(getIncludedSourceFile());
        
        MIDLType type = getModelRepository().findIdlType(getScope(), componentId);
        if(type == null)
        {
            reportError("Managed component " + componentId + " not found!");
        }
        else if(type instanceof MComponentDef)
        {
            MComponentDef component = (MComponentDef)type;
            component.addHome(home);
            home.setComponent(component);
        }
        else
        {
            reportError("Managed type " + componentId + " is not a component!");
        }
        return home;
    }
        
    public MHomeDef parseHomeHeader(String id, ScopedName componentTypeName, ScopedName baseTypeName)
    {
        getLogger().fine("126: home id manages component = " + id + ", " + componentTypeName + ", " + baseTypeName);

        MHomeDef home =  parseHomeHeader(id, componentTypeName);
        MIDLType type = getModelRepository().findIdlType(getScope(), baseTypeName);
        if(type == null)
        {
            reportError("Base home " + baseTypeName + " not found!");
        }
        else if(type instanceof MHomeDef)
        {
            home.addBase((MHomeDef)type);
        }
        else
        {
            reportError("Base type " + baseTypeName  + " is not a home!");
        }
        return home;
    }
        
    public MHomeDef parseHomeHeader(String id, ScopedName componentTypeName, List ifaces)
    {
        getLogger().fine("126: home id manages component = " + id + ", " + componentTypeName + ", " + ifaces);
        MHomeDef home =  parseHomeHeader(id, componentTypeName);
        Collections.reverse(ifaces);
        home.setSupportss(ifaces);
        return home;
    }
    
    public MHomeDef parseHomeHeader(String id, ScopedName componentTypeName, ScopedName baseTypeName, List ifaces)
    {
        getLogger().fine("126: T_IDENTIFIER home_inheritance_spec supported_interface_spec T_MANAGES scoped_name "
                            + id + ", " + componentTypeName + ", " + baseTypeName + ", " + ifaces);
        MHomeDef home = parseHomeHeader(id, componentTypeName, baseTypeName);
        Collections.reverse(ifaces);
        home.setSupportss(ifaces);        
        return home;
    }
    
    
    /* 129 */
    public List parseHomeBody()
    {
        getLogger().fine("129: parseHomeBody()"); 
        return new ArrayList();
    }
    
    
    /* 130 */
    public List parseHomeExports(Object export)
    {
        getLogger().fine("130: home_export " + export);
        List l = new ArrayList();
        if(export instanceof List)
        {
            l.addAll((List)export);
        }
        else
        {
            l.add(export);
        }
        return l;
    }
    
    public List parseHomeExports(Object export, List l)
    {
        getLogger().fine("130: home_export home_exports" + export + " " + l);
        if(export instanceof List)
        {
            l.addAll((List)export);
        }
        else
        {
            l.add(export);
        }
        return l;
    }
    

    /* 131 */    
    public MFactoryDef parseFactoryDeclaration(String id, List parameters, List exceptions)
    {
        getLogger().fine("131: factory " + id + " ( "+ parameters + " ) " + exceptions);
//        if(parameters == null) parameters = new ArrayList();
        
        MFactoryDef factory = new MFactoryDefImpl();
        factory.setIdentifier(id);
        if(parameters != null)
        {
            Collections.reverse(parameters);
            for(Iterator i = parameters.iterator(); i.hasNext();)
            {
                MParameterDef p = (MParameterDef)i.next();
                p.setOperation(factory);
                factory.addParameter(p);
            }
        }        
        if(exceptions != null)
        {
            Collections.reverse(exceptions);
            factory.setExceptionDefs(exceptions);
        }
        return factory;
    }
   
    
    /*************************************************************************
     * Scanner Utility Methods
     *************************************************************************/
        
    // # linenumber filename flags
    // # 1 "/home/eteinik/sandbox/workspace-development/TelegramGenerator/examples/simple_test/example1.tgen"
    // # 30 "/home/eteinik/sandbox/workspace-development/ccmtools/test/JavaLocalComponents/component_mirror/xxx/idl3/component/world/Test.idl"

    // #line 1 "c:\\temp\\Message.idl"
    
    public void handlePreprocessorLine(String line)
    {
        getLogger().fine("cpp(" + line + ")");
        line = line.substring(0, line.lastIndexOf('\n'));
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
        getLogger().fine("pragma(" + line + ")");
        line = line.substring(0, line.lastIndexOf('\n'));
        // TODO
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
}
