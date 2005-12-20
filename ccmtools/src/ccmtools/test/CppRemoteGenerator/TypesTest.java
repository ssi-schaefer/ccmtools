/* CCM Tools : CppRemoteGenerator test cases
 * Egon Teiniker <egon.teiniker@salomon.at>
 * Copyright (C) 2002 - 2005 Salomon Automation
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

package ccmtools.test.CppRemoteGenerator;

import ccmtools.test.CcmtoolsTestCase;


public class TypesTest extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public TypesTest(String name)
    {
        super(name);
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppRemoteGenerator";
    }

    
    // ------------------------------------------------------------------------
    // Attribute test cases
    // ------------------------------------------------------------------------
    //    make -C attribute_types test
    //    make -C attribute_module_types test

    public void testAttributeTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/attribute_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testAttributeModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/attribute_module_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    // ------------------------------------------------------------------------
    // Supports test cases
    // ------------------------------------------------------------------------
    //    make -C supports_exception test
    //    make -C supports_module_exception test
    //    make -C supports_types test
    //    make -C supports_module_types test
  
    public void testSupportsException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testSupportsModuleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_module_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testSupportsTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testSupportsModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_module_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    // ------------------------------------------------------------------------
    // Facet test cases
    // ------------------------------------------------------------------------
    //    make -C facet_exception test
    //    make -C facet_module_exception test
    //    make -C facet_types test
    //    make -C facet_module_types test
    //    make -C facet_constants test

    public void testFacetException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testFacetModuleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testFacetTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testFacetModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
        
    public void testFacetConstants()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_constants test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    // ------------------------------------------------------------------------
    // Receptacle test cases
    // ------------------------------------------------------------------------
    //    make -C receptacle_exception test
    //    make -C receptacle_module_exception test
    //    make -C receptacle_types test
    //    make -C receptacle_module_types test

    public void testReceptacleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testReceptacleModuleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_module_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testReceptacleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testReceptacleModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_module_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
