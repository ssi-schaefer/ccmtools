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
import mof_xmi_parser.model.MStructuralFeature_multiplicity;


/**
 * StructuralFeature implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
abstract class StructuralFeatureImp extends TypedElementImp implements MofStructuralFeature
{
    StructuralFeatureImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    private MofScopeKind scope_;
    private MofVisibilityKind visibility_;
    private String isChangeable_;
    private MofMultiplicityType multiplicity_;


    /// implements {@link MofFeature#getScope}
    public MofScopeKind getScope() throws IllegalArgumentException
    {
        if( scope_==null )
        {
            String text = getXmiScope();
            if( text!=null )
            {
                scope_ = MofScopeKind.create(text);
            }
            else
            {
                scope_ = MofScopeKind.INSTANCE;
            }
        }
        return scope_;
    }

    abstract String getXmiScope();


    /// implements {@link MofFeature#getVisibility}
    public MofVisibilityKind getVisibility() throws IllegalArgumentException
    {
        if( visibility_==null )
        {
            String text = getXmiVisibility();
            if( text!=null )
            {
                visibility_ = MofVisibilityKind.create(text);
            }
            else
            {
                throw new IllegalArgumentException("no visibility");
            }
        }
        return visibility_;
    }

    abstract String getXmiVisibility();


    /// implements {@link MofStructuralFeature#isChangeable}
    public boolean isChangeable()
    {
        if( isChangeable_==null )
        {
            isChangeable_ = getXmiIsChangeable();
            if( isChangeable_==null )
            {
                isChangeable_ = "false";
            }
        }
        return isChangeable_.equalsIgnoreCase("true");
    }

    abstract String getXmiIsChangeable();

    
    /// implements {@link MofStructuralFeature#getMultiplicity}
    public MofMultiplicityType getMultiplicity() throws NumberFormatException
    {
        if( multiplicity_==null )
        {
            Vector children = xmi_.findChildren(MStructuralFeature_multiplicity.xmlName__);
            if( children.size()>=1 )
            {
                MStructuralFeature_multiplicity p = (MStructuralFeature_multiplicity)children.get(0);
                multiplicity_ = new MofMultiplicityType(p);
            }
            else
            {
                multiplicity_ = new MofMultiplicityType("1");
            }
        }
        return multiplicity_;
    }

}
