/*  MOF reader
 *
 *  2004 by Research & Development, Salomon Automation <www.salomon.at>
 *
 *  Robert Lechner  <robert.lechner@salomon.at>
 *
 *
 *  $Id$
 *
 */

package mof_reader;


import java.util.Vector;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.model.MAssociationEnd_multiplicity;
import mof_xmi_parser.model.MCollectionType_multiplicity;
import mof_xmi_parser.model.MParameter_multiplicity;
import mof_xmi_parser.model.MStructuralFeature_multiplicity;
import mof_xmi_parser.MXMI_field;


/**
 * MultiplicityType
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public class MofMultiplicityType
{
    protected int lower_;
    protected int upper_;
    protected boolean isOrdered_;
    protected boolean isUnique_;


    public int getLower() { return lower_; }
    public int getUpper() { return upper_; }
    public boolean isOrdered() { return isOrdered_; }
    public boolean isUnique() { return isUnique_; }


    MofMultiplicityType( MAssociationEnd_multiplicity m ) throws NumberFormatException
    {
        convertContainer(m);
    }


    MofMultiplicityType( MCollectionType_multiplicity m ) throws NumberFormatException
    {
        convertContainer(m);
    }


    MofMultiplicityType( MParameter_multiplicity m ) throws NumberFormatException
    {
        convertContainer(m);
    }


    MofMultiplicityType( MStructuralFeature_multiplicity m ) throws NumberFormatException
    {
        convertContainer(m);
    }


    protected void convertContainer( DTD_Container container ) throws NumberFormatException
    {
        Vector ch = container.findChildren(MXMI_field.xmlName__);
        int s = ch.size();
        if(s<1)
        {
            throw new NumberFormatException("no multiplicity");
        }
        if( s==1 )
        {
            MXMI_field field = (MXMI_field)ch.get(0);
            convertBoth(makeText(field));
        }
        else
        {
            MXMI_field field1 = (MXMI_field)ch.get(0);
            MXMI_field field2 = (MXMI_field)ch.get(1);
            lower_ = convertOne(makeText(field1));
            upper_ = convertOne(makeText(field2));
        }
        if( upper_<0 )
        {
            lower_ = 0;
        }
    }


    private String makeText( MXMI_field field ) throws NumberFormatException
    {
        if( field.size()!=1 )
        {
            throw new NumberFormatException("unknown multiplicity field format");
        }
        return field.get(0).toString();
    }


    public MofMultiplicityType( String multiplicity ) throws NumberFormatException
    {
        convertBoth(multiplicity);
    }


    protected void convertBoth( String multiplicity ) throws NumberFormatException
    {
        int index = multiplicity.indexOf("..");
        if( index<0 )
        {
            lower_ = upper_ = convertOne(multiplicity);
        }
        else
        {
            lower_ = convertOne(multiplicity.substring(0,index));
            upper_ = convertOne(multiplicity.substring(index+2));
        }
        if( upper_<0 )
        {
            lower_ = 0;
        }
    }


    protected static int convertOne( String multiplicity ) throws NumberFormatException
    {
        multiplicity = multiplicity.trim();
        if( multiplicity=="*" )
        {
            return -1;
        }
        return Integer.parseInt(multiplicity);
    }

}
