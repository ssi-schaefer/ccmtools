/* DTD parser
 *
 * 2003 by Robert Lechner (rlechner@gmx.at)
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

package ccmtools.dtd2java;

import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;
import java.util.HashSet;


/**
 * The parse tree of a DTD-file.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdFile
{
    // element names; instances of java.lang.String
    private Vector elementNames_ = new Vector();

    // the elements; instances of DtdElement
    private HashMap elements_ = new HashMap();

    // attribute lists; instances of DtdAttributes
    private HashMap attributeLists_ = new HashMap();

    // entities; instances of DtdEntity
    private HashMap entities_ = new HashMap();

    // notations; instances of DtdNotation
    private HashMap notations_ = new HashMap();


    /**
     * Returns a hash-map with instances of DtdElement.
     */
    public HashMap getAllElements()
    {
        return elements_;
    }


    /**
     * Returns a hash-map with instances of DtdAttributes.
     */
    public HashMap getAllAttributeLists()
    {
        return attributeLists_;
    }


    /**
     * Returns a hash-map with instances of DtdEntity.
     */
    public HashMap getAllEntities()
    {
        return entities_;
    }


    /**
     * Returns a hash-map with instances of DtdNotation.
     */
    public HashMap getAllNotations()
    {
        return notations_;
    }


    /**
     * Adds a new element.
     */
    public void addElement( DtdElement element )
    {
        String name = element.getName();
        elementNames_.add(name);
        elements_.put(name, element);
    }


    /**
     * Returns an element (or null).
     */
    public DtdElement getElementByName( String name )
    {
        return (DtdElement)elements_.get(name);
    }


    /**
     * Returns the names of all elements, which are no sub-expressions of other elements.
     */
    public Vector getRootNames()
    {
        HashSet children = new HashSet();
        int index, size=elementNames_.size();
        for( index=0; index<size; index++ )
        {
            String name = (String)elementNames_.get(index);
            DtdElement element = (DtdElement)elements_.get(name);
            element.getContent().collectElementNames(children);
        }
        Vector result = new Vector();
        for( index=0; index<size; index++ )
        {
            String name = (String)elementNames_.get(index);
            if( !children.contains(name) )
            {
                result.add(name);
            }
        }
        return result;
    }


    /**
     * Adds a new attribute list or merges two lists.
     */
    public void addAttributes( DtdAttributes attributes )
    {
        String name = attributes.getName();
        if( attributeLists_.containsKey(name) )
        {
            ((DtdAttributes)attributeLists_.get(name)).merge(attributes);
        }
        else
        {
            attributeLists_.put(name, attributes);
        }
    }


    /**
     * Returns an attribute list (or null).
     */
    public DtdAttributes getAttributeList( String name )
    {
        return (DtdAttributes)attributeLists_.get(name);
    }


    /**
     * Adds a new entity.
     */
    public void addEntity( DtdEntity entity )
    {
        entities_.put(entity.getName(), entity);
    }


    /**
     * Returns an entity (or null).
     */
    public DtdEntity getEntity( String name )
    {
        return (DtdEntity)entities_.get(name);
    }


    /**
     * Adds a new notation.
     */
    public void addNotation( DtdNotation notation )
    {
        notations_.put(notation.getName(), notation);
    }


    /**
     * Returns a notation (or null).
     */
    public DtdNotation getNotation( String name )
    {
        return (DtdNotation)notations_.get(name);
    }


    /**
     * Creates a new DTD-file.
     */
    public void write( java.io.File file ) throws java.io.IOException
    {
        java.io.FileWriter w = new java.io.FileWriter(file);
        write(w);
        w.close();
    }


    /**
     * Writes the DTD-code.
     */
    public void write( java.io.Writer w ) throws java.io.IOException
    {
        w.write("<!-- automatically generated -->\n\n");
        Iterator it1 = entities_.keySet().iterator();
        while( it1.hasNext() )
        {
            DtdEntity entity = (DtdEntity)entities_.get((String)it1.next());
            entity.write(w);
        }
        Iterator it2 = notations_.keySet().iterator();
        while( it2.hasNext() )
        {
            DtdNotation notation = (DtdNotation)notations_.get((String)it2.next());
            notation.write(w);
        }
        int size = elementNames_.size();
        for( int index=0; index<size; index++ )
        {
            String name = (String)elementNames_.get(index);
            DtdElement element = (DtdElement)elements_.get(name);
            element.write(w);
            DtdAttributes attributes = (DtdAttributes)attributeLists_.get(name);
            if( attributes!=null )
            {
                attributes.write(w);
            }
        }
    }


    /**
     * Returns a XML-string-expression.
     */
    public static String makeString( String value )
    {
        if( value.indexOf('\"')<0 )
        {
            return "\""+value+"\"";
        }
        return "\'"+value+"\'";
    }
}
