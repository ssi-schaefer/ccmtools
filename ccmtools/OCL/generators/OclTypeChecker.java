/* CCM Tools : OCL generators
 * Robert Lechner <rlechner@sbox.tugraz.at>
 * copyright (c) 2003 Salomon Automation
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

package ccmtools.OCL.generators;

import oclmetamodel.*;
import ccmtools.Metamodel.BaseIDL.*;
import ccmtools.Metamodel.ComponentIDL.*;


/**
 * Calculates the type of OCL expressions.
 *
 * @author Robert Lechner
 * @version 0.1
 */
public interface OclTypeChecker
{
    /**
     * Calculates and sets the type of an expression.
     *
     * @param expr  the expression
     * @param conCode  no change
     * @return the type of the expression (or null)
     */
    public OclType makeType( MExpression expr, ConstraintCode conCode );


    /**
     * Returns the class name of the local adpter.
     *
     * @param theClass  home, component or interface
     * @return class name or null
     */
    public String getLocalAdapterName( MContainer theClass );
}
