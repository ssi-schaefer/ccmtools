/* CCM Tools : Python Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2002, 2003 Salomon Automation
 *
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

package ccmtools.PythonGenerator;

import ccmtools.CodeGenerator.CodeGenerator;
import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MFixedDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MParameterMode;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.BaseIDL.MTypedefDef;
import ccmtools.Metamodel.BaseIDL.MUnionDef;
import ccmtools.Metamodel.BaseIDL.MUnionFieldDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MFactoryDef;
import ccmtools.Metamodel.ComponentIDL.MFinderDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MSupportsDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

abstract public class CppGenerator
    extends CodeGenerator
{
    // reserved words in C++. identifiers that contain these words will be
    // mapped to new identifiers.

    private final static String[] local_reserved_words =
    {
        "abs", "and", "apply", "as", "basestring", "bool", "buffer", "break",
        "callable", "chr", "class", "classmethod", "cmp", "coerce", "compile",
        "complex", "copyright", "credits", "continue", "del", "delattr", "dict",
        "dir", "divmod", "elif", "else", "enumerate", "eval", "except",
        "execfile", "exit", "file", "filter", "finally", "float", "for", "from",
        "getattr", "globals", "hash", "help", "hex", "id", "if", "import",
        "input", "int", "intern", "isinstance", "issubclass", "iter", "len",
        "license", "list", "locals", "long", "map", "max", "min", "module",
        "not", "object", "oct", "open", "or", "ord", "pow", "property", "quit",
        "raise", "range", "raw_input", "reduce", "reload", "repr", "return",
        "round", "self", "setattr", "slice", "staticmethod", "str", "sum",
        "super", "tuple", "try", "type", "unichr", "unicode", "vars", "xrange",
        "zip"
    };

    // python language types that get mapped from corba primitive kinds.

    private final static String[] local_language_map =
    {
        "",
        "", // PK_ANY
        "", // PK_BOOLEAN
        "", // PK_CHAR
        "", // PK_DOUBLE
        "", // PK_FIXED
        "", // PK_FLOAT
        "", // PK_LONG
        "", // PK_LONGDOUBLE
        "", // PK_LONGLONG
        "", // PK_NULL
        "", // PK_OBJREF
        "", // PK_OCTET
        "", // PK_PRINCIPAL
        "", // PK_SHORT
        "", // PK_STRING
        "", // PK_TYPECODE
        "", // PK_ULONG
        "", // PK_ULONGLONG
        "", // PK_USHORT
        "", // PK_VALUEBASE
        "", // PK_VOID
        "", // PK_WCHAR
        ""  // PK_WSTRING
    };

    /**************************************************************************/

    public PythonGenerator(String sublang, Driver d, File out_dir,
                           String[] output_types,
                           File[] env_files, String[] env_templates)
        throws IOException
    {
        super(sublang, d, out_dir, output_types, local_reserved_words,
              env_files, env_templates, local_language_map);
    }

    /**
     * Acknowledge and process a closing node during graph traversal. If the
     * node is an MContainer type, pop the namespace (this will remove our
     * CCM_Local that we pushed, in theory (tm)). If the node is of the correct
     * type and defined in the original parsed file, write code for this node.
     *
     * @param node the node that the graph traverser object just finished
     *        investigating.
     * @param scope_id the full scope identifier of the node. This identifier is
     *        a string containing the names of ancestor nodes, joined together
     *        with double colons.
     */
    public void endNode(Object node, String scope_id)
    {
        super.endNode(node, scope_id);

        if ((node instanceof MContainer) &&
            (((MContainer) node).getDefinedIn() == null))
            namespace.pop();

        writeOutputIfNeeded();
    }

    /**************************************************************************/

    /**
     * Join the bases of the current node using the given string as a separator.
     * The current node should be an instance of MInterfaceDef.
     *
     * @param sep the separator to use between bases.
     * @return a string containing the names of base interfaces, separated by
     *         sep.
     */
    protected String joinBaseNames(String sep)
    {
        if (! (current_node instanceof MInterfaceDef)) return "";
        MInterfaceDef node = (MInterfaceDef) current_node;
        ArrayList names = new ArrayList();
        for (Iterator i = node.getBases().iterator(); i.hasNext(); )
            names.add("CCM_" + ((MInterfaceDef) i.next()).getIdentifier());
        return join(sep, names);
    }

    /**
     * Get a local value for the given variable name.
     *
     * This function performs some common value parsing in the CCM MOF library.
     * More specific value parsing needs to be provided in the subclass for a
     * given language, in the subclass' getLocalValue function. Subclasses
     * should call this function first and then perform any subclass specific
     * value manipulation with the returned value.
     *
     * @param variable The variable name to get a value for.
     * @return the value of the variable available from the current
     *         output_variables hash table. Could be an empty string.
     */
    protected String getLocalValue(String variable)
    {
        String value = super.getLocalValue(variable);

        if (current_node instanceof MHomeDef) {
            return data_MHomeDef(variable, value);
        } else if (current_node instanceof MComponentDef) {
            return data_MComponentDef(variable, value);
        } else if (current_node instanceof MFactoryDef) {
            return data_MFactoryDef(variable, value);
        } else if (current_node instanceof MFinderDef) {
            return data_MFinderDef(variable, value);

        } else if (current_node instanceof MProvidesDef) {
            return data_MProvidesDef(variable, value);
        } else if (current_node instanceof MSupportsDef) {
            return data_MSupportsDef(variable, value);
        } else if (current_node instanceof MUsesDef) {
            return data_MUsesDef(variable, value);

        } else if (current_node instanceof MInterfaceDef) {
            return data_MInterfaceDef(variable, value);
        } else if (current_node instanceof MOperationDef) {
            return data_MOperationDef(variable, value);
        } else if (current_node instanceof MEnumDef) {
            return data_MEnumDef(variable, value);
        } else if (current_node instanceof MExceptionDef) {
            return data_MExceptionDef(variable, value);
        } else if (current_node instanceof MAliasDef) {
            return data_MAliasDef(variable, value);
        } else if (current_node instanceof MStructDef) {
            return data_MStructDef(variable, value);
        } else if (current_node instanceof MUnionDef) {
            return data_MUnionDef(variable, value);
        }

        return value;
    }

    /**
     * Return the Python language type for the given object. This returns an
     * empty string since Python is not strongly typed.
     *
     * @param object the node object to use for type finding.
     */
    protected String getLanguageType(MTyped object) { return ""; }

    /**************************************************************************/

    protected String data_MAliasDef(String data_type, String data_value)
    {
        MIDLType idl_type = ((MAliasDef) current_node).getIdlType();

        if (data_type.equals("FirstBound")) {
            return "" + ((MArrayDef) idl_type).getBounds().get(0);
        } else if (data_type.equals("AllBounds")) {
            MArrayDef array = (MArrayDef) idl_type;
            String result = "";
            for (Iterator i = array.getBounds().iterator(); i.hasNext(); )
                result += "[" + (Long) i.next() + "]";
            return result;
        } else if (data_type.equals("ExternInclude")) {
            if (idl_type instanceof MContained) {
                MContained node = (MContained) idl_type;
                if (node.getSourceFile().equals(""))
                    return getScopedInclude(node);
            }
        } else if (data_type.endsWith("Namespace")) {
            return handleNamespace(data_type, "");
        }

        return data_value;
    }

    protected String data_MComponentDef(String data_type, String data_value)
    {
        if (data_type.endsWith("Namespace")) {
            MComponentDef component = (MComponentDef) current_node;
            return handleNamespace(data_type, component.getIdentifier());
        } else if (data_type.equals("BaseTypes")) {
            String base = joinBaseNames(", public ");
            if (base.length() > 0) return ", public " + base;
        } else if (data_type.equals("ExternInclude")) {
            return collectExternIncludes();
        }
        return data_value;
    }

    protected String data_MEnumDef(String data_type, String data_value)
    {
        if (data_type.equals("Members")) {
            List b = new ArrayList();
            MEnumDef enum = (MEnumDef) current_node;
            for (Iterator i = enum.getMembers().iterator(); i.hasNext(); )
                b.add((String) i.next());
            return join(", ", b);
        }
        return data_value;
    }

    protected String data_MExceptionDef(String data_type, String data_value)
    {
        if (data_type.endsWith("Namespace")) {
            return handleNamespace(data_type, "");
        }
        return data_value;
    }

    protected String data_MFactoryDef(String data_type, String data_value)
    {
        if (data_type.startsWith("MParameterDef") && data_value.endsWith(", "))
            return data_value.substring(0, data_value.length() - 2);
        return data_value;
    }

    protected String data_MFinderDef(String data_type, String data_value)
    {
        if (data_type.startsWith("MParameterDef") && data_value.endsWith(", "))
            return data_value.substring(0, data_value.length() - 2);
        return data_value;
    }

    protected String data_MHomeDef(String data_type, String data_value)
    {
        if (data_type.endsWith("Namespace")) {
            MComponentDef comp = ((MHomeDef) current_node).getComponent();
            return handleNamespace(data_type, comp.getIdentifier());
        }
        return data_MComponentDef(data_type, data_value);
    }

    protected String data_MInterfaceDef(String data_type, String data_value)
    {
        if (data_type.equals("BaseTypes")) {
            String base = joinBaseNames(", public ");
            if (base.length() > 0) return ": public " + base;
        } else if (data_type.equals("ExternInclude")) {
            return collectExternIncludes();
        }
        return data_value;
    }

    protected String data_MOperationDef(String data_type, String data_value)
    {
        if (data_type.equals("MExceptionDefThrows") &&
            data_value.endsWith(", ")) {
            return "throw ( " +
                data_value.substring(0, data_value.length() - 2) + " )";
        } else if (data_type.startsWith("MParameterDef") &&
                   data_value.endsWith(", ")) {
            return data_value.substring(0, data_value.length() - 2);
        } else if (data_type.equals("UsesIdentifier")) {
            MOperationDef op = (MOperationDef) current_node;
            if (op.getDefinedIn() instanceof MUsesDef)
                return op.getDefinedIn().getIdentifier();
        } else if (data_type.equals("ProvidesIdentifier")) {
            MOperationDef op = (MOperationDef) current_node;
            if (op.getDefinedIn() instanceof MProvidesDef)
                return op.getDefinedIn().getIdentifier();
        }
        return data_value;
    }

    protected String data_MProvidesDef(String data_type, String data_value)
    {
        MProvidesDef provides = (MProvidesDef) current_node;
        MInterfaceDef iface = provides.getProvides();

        if (data_type.startsWith("MOperation")) {
            return fillTwoStepTemplates(iface, data_type);
        } else if (data_type.equals("IncludeNamespace")) {
            List scope = getScope(iface);
            scope.add(0, namespace.get(0));
            return join("/", scope);
        }
        return data_value;
    }

    protected String data_MStructDef(String data_type, String data_value)
    {
        MStructDef struct = (MStructDef) current_node;

        if (data_type.equals("ExternInclude")) {
            List lines = new ArrayList();
            for (Iterator i = struct.getMembers().iterator(); i.hasNext(); ) {
                MIDLType idl_type = ((MFieldDef) i.next()).getIdlType();
                if (idl_type instanceof MContained) {
                    MContained node = (MContained) idl_type;
                    if (node.getSourceFile().equals(""))
                        lines.add(getScopedInclude(node));
                }
            }
            return join("\n", lines);
        }

        return data_value;
    }

    protected String data_MSupportsDef(String data_type, String data_value)
    {
        MSupportsDef supports = (MSupportsDef) current_node;
        MInterfaceDef iface = supports.getSupports();

        if (data_type.startsWith("MOperation")) {
            return fillTwoStepTemplates(iface, data_type);
        } else if (data_type.equals("IncludeNamespace")) {
            List scope = getScope(iface);
            scope.add(0, namespace.get(0));
            return join("/", scope);
        }
        return data_value;
    }

    protected String data_MUnionDef(String data_type, String data_value)
    {
        MUnionDef union = (MUnionDef) current_node;

        if (data_type.equals("ExternInclude")) {
            List lines = new ArrayList();
            for (Iterator i = union.getUnionMembers().iterator(); i.hasNext(); ) {
                MIDLType idl_type = ((MUnionFieldDef) i.next()).getIdlType();
                if (idl_type instanceof MContained) {
                    MContained node = (MContained) idl_type;
                    if (node.getSourceFile().equals(""))
                        lines.add(getScopedInclude(node));
                }
            }
            return join("\n", lines);
        }

        return data_value;
    }

    protected String data_MUsesDef(String data_type, String data_value)
    {
        MUsesDef uses = (MUsesDef) current_node;
        MInterfaceDef iface = uses.getUses();
        if (data_type.equals("IncludeNamespace")) {
            List scope = getScope(iface);
            scope.add(0, namespace.get(0));
            return join("/", scope);
        }
        return data_value;
    }

    /**************************************************************************/

    /**
     * Get type and name information about the parameters for the given
     * operation. This will return a comma-separated string, i.e. <type1>
     * <name1>, <type2> <name2>, ... , <typeN> <nameN> for this operation's
     * parameters.
     *
     * @param op the operation to investigate.
     * @return a comma separated string of the parameter information requested
     *         for this operation.
     */
    protected String getOperationParams(MOperationDef op)
    {
        List ret = new ArrayList();
        for (Iterator ps = op.getParameters().iterator(); ps.hasNext(); ) {
            MParameterDef p = (MParameterDef) ps.next();
            ret.add(getLanguageType(p) + " " + p.getIdentifier());
        }
        return join(", ", ret);
    }

    /**
     * Get name information about the parameters for the given operation. This
     * will return a comma-separated string, i.e. <name1>, <name2>, ... ,
     * <nameN> for this operation's parameters.
     *
     * @param op the operation to investigate.
     * @return a comma separated string of the parameter information requested
     *         for this operation.
     */
    protected String getOperationParamNames(MOperationDef op)
    {
        List ret = new ArrayList();
        for (Iterator ps = op.getParameters().iterator(); ps.hasNext(); )
            ret.add(((MParameterDef) ps.next()).getIdentifier());
        return join(", ", ret);
    }

    /**
     * Get the exceptions thrown by the given operation.
     *
     * @param op the operation to investigate.
     * @return a string containing C++ code describing the exceptions that this
     *         operation throws. If there are no exceptions, this returns an
     *         empty string. If there are exceptions, it returns something like
     *         "throw ( exception, exception, ... )".
     */
    protected String getOperationExcepts(MOperationDef op)
    {
        List ret = new ArrayList();
        for (Iterator es = op.getExceptionDefs().iterator(); es.hasNext(); )
            ret.add(((MExceptionDef) es.next()).getIdentifier());
        return (ret.size() > 0) ? "throw ( " + join(", ", ret) + " )" : "";
    }

    /**
     * Load an appropriate template (based on the value in the template_name
     * argument) for the given child, and fill out its variable information.
     *
     * @param child MInterfaceDef node to gather information from.
     * @param template_name the name of the template to load for variable
     *        substitution.
     * @return a string containing the variable-substituted template requested.
     */
    protected String fillTwoStepTemplates(MInterfaceDef child,
                                          String template_name)
    {
        StringBuffer ret = new StringBuffer("");

        for (Iterator ops = child.getContentss().iterator(); ops.hasNext(); ) {
            MContained object = (MContained) ops.next();

            if (! (object instanceof MOperationDef)) continue;

            MOperationDef op = (MOperationDef) object;
            MContained cont  = (MContained) current_node;

            // if this is a supports node, we want to actually refer to the home
            // or component that owns this supports definition.

            if (current_node instanceof MSupportsDef) {
                MContained tmp = ((MSupportsDef) cont).getComponent();
                if (tmp == null) tmp = ((MSupportsDef) cont).getHome();
                cont = tmp;
            }

            Template template = template_manager.getRawTemplate(template_name);
            Map vars = getTwoStepVariables(child, op, cont);
            ret.append(template.substituteVariables(vars));
        }

        return ret.toString();
    }

    /**
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function.
     *
     * @param iface the interface from which we're starting the two step
     *        operation.
     * @param operation the particular interface operation that we're filling in
     *        a template for.
     * @param container the container in which the given interface is defined.
     * @return a map containing the keys and values needed to fill in the
     *         template for this interface.
     */
    abstract protected Map getTwoStepVariables(MInterfaceDef iface,
                                               MOperationDef operation,
                                               MContained container);
}

