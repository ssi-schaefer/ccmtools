/* CCM Tools : IDL3 Parser
 * Edin Arnautovic <edin.arnautovic@salomon.at>
 * copyright (c) 2002, 2003 Salomon Automation
 *
 * $Id$
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

/*
 * This code is taken mostly from the gnuc parser made by John Mitchell and
 * Monty Zukowski, found on www.antlr.org (Original file: CSymbolTable). The
 * TNode has been exchanged with MContained.
 */

package ccmtools.IDL3Parser;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

import ccmtools.Metamodel.BaseIDL.MContained;

public class IDL3SymbolTable {
    /* holds list of scopes */
    private Vector scopeStack;

    /* table where all defined names are mapped to parse tree nodes */
    private Hashtable symTable;

    public IDL3SymbolTable()
    {
        scopeStack = new Vector(20);
        symTable = new Hashtable(533);
    }

    /* clear the state of the symbol table. */
    public void clear()
    {
        scopeStack = new Vector(20);
        symTable = new Hashtable(533);
    }

    /* push a new scope onto the scope stack. */
    public void pushScope(String s)
    {
        scopeStack.addElement(s);
    }

    /* pop the last scope off the scope stack. */
    public void popScope()
    {
        int size = scopeStack.size();
        if(size > 0)
            scopeStack.removeElementAt(size - 1);
    }

    /* return the current scope as a string */
    public String currentScopeAsString() {
        StringBuffer buf = new StringBuffer(100);
        boolean first = true;
        Enumeration e = scopeStack.elements();

        while (e.hasMoreElements()) {
            if (first)
                first = false;
            else
                buf.append("::");
            buf.append(e.nextElement().toString());
        }

        return buf.toString();
    }

    /* given a name for a type, append it with the current scope. */
    public String addCurrentScopeToName(String name) {
        String currScope = currentScopeAsString();
        return addScopeToName(currScope, name);
    }

    /* given a name for a type, append it with the given scope. MBZ */
    public String addScopeToName(String scope, String name) {
        if(scope == null || scope.length() > 0)
            return scope + "::" + name;
        else
            return name;
    }

    /* remove one level of scope from name MBZ */
    public String removeOneLevelScope(String scopeName) {
        int index = scopeName.lastIndexOf("::");
        if (index > 0) {
            return scopeName.substring(0,index);
        }
        if (scopeName.length() > 0) {
            return "";
        }
        return null;
    }

    /* add a contained to the table with it's key as the current scope and the
       name */
    public MContained add(String name, MContained mContained) {
        return (MContained)symTable.put(addCurrentScopeToName(name),mContained);
    }

    /* lookup a fully scoped name in the symbol table */
    public MContained lookupScopedName(String scopedName) {
        return (MContained)symTable.get(scopedName);
    }

    /* lookup an unscoped name in the table by prepending the current scope. MBZ
       -- if not found, pop scopes and look again */
    public MContained lookupNameInCurrentScope(String name) {
        if (name == null)
            return null;

        String scope = currentScopeAsString();
        String scopedName;
        MContained mContained = null;

        while (mContained == null && scope != null) {
            scopedName = addScopeToName(scope, name);
            mContained = (MContained)symTable.get(scopedName);
            scope = removeOneLevelScope(scope);
        }

        return mContained;
    }

    /* convert this table to a string */
    public String toString() {
        StringBuffer buff = new StringBuffer(300);
        buff.append("=== IDL 3 Symbol Table ===\n");
        buff.append("Current scope: " + currentScopeAsString() + "\n");
        buff.append("Defined symbols:\n");
        Enumeration ke = symTable.keys();
        Enumeration ve = symTable.elements();
        while(ke.hasMoreElements()) {
            buff.append(ke.nextElement().toString()+"\n");
        }
        buff.append("=== IDL 3 Symbol Table ===\n");
        return buff.toString();
    }
};
