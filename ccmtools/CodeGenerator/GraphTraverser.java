/* CCM Tools : Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
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

package ccmtools.CodeGenerator;

import ccmtools.Metamodel.BaseIDL.MContained;

import java.util.Set;

public interface GraphTraverser
{
    /**
     * Traverse the subgraph starting at the given node.
     *
     * @param node a node to use for starting traversal.
     */
    void traverseGraph(MContained node);

    /**
     * Get the node handler objects for this traverser.
     *
     * @return a set of NodeHandler objects currently used by this traverser.
     */
    public Set getHandlers();

    /**
     * Add a node handler object for this traverser.
     *
     * @param h a NodeHandler object to assign to this traverser.
     */
    public void addHandler(NodeHandler h);

    /**
     * Remove a node handler object from this traverser.
     *
     * @param h a NodeHandler object to remove from this traverser. This
     *        function does nothing if h is not currently a handler.
     */
    public void removeHandler(NodeHandler h);
}

