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

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.model.MAssociationEnd_multiplicity;
import mof_xmi_parser.model.MCollectionType_multiplicity;
import mof_xmi_parser.model.MParameter_multiplicity;
import mof_xmi_parser.model.MStructuralFeature_multiplicity;


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


    MofMultiplicityType( MAssociationEnd_multiplicity m )
    {
        // TODO
        throw new RuntimeException("not implemented");
    }


    MofMultiplicityType( MCollectionType_multiplicity m )
    {
        // TODO
        throw new RuntimeException("not implemented");
    }


    MofMultiplicityType( MParameter_multiplicity m )
    {
        // TODO
        throw new RuntimeException("not implemented");
    }


    MofMultiplicityType( MStructuralFeature_multiplicity m )
    {
        // TODO
        throw new RuntimeException("not implemented");
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
