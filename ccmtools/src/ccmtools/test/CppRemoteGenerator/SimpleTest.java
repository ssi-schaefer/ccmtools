/* CCM Tools : CppGenerator test cases
 * Egon Teiniker <egon.teiniker@salomon.at>
 * Copyright (C) 2002, 2003, 2004 Salomon Automation
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

/**
 * This test case is derived from CcmtoolsTestCase class and implements tests
 * for idl3, idl3mirror, c++local, c++remote and c++remote-test code generators. 
 * These tests check the code generation process, and the runtime behavior of 
 * the generated code (using Confix' _check_*.cc files).
 */
public class SimpleTest extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public SimpleTest(String name)
    {
        super(name);
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppRemoteGenerator";
    }

    
    // ------------------------------------------------------------------------
    // Attribute test cases
    // ------------------------------------------------------------------------
    //    make -C attribute_simple test
    //    make -C attribute_module_simple test
    
    public void testAttributeSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/attribute_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testAttributeModuleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/attribute_module_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }

    
    // ------------------------------------------------------------------------
    // Supports test cases
    // ------------------------------------------------------------------------
    //    make -C supports_simple test
    //    make -C supports_module_simple test
    
    public void testSupportsSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testSupportsModuleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_module_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    // ------------------------------------------------------------------------
    // Facet test cases
    // ------------------------------------------------------------------------
    //    make -C facet_simple test
    //    make -C facet_module_simple test
    
    public void testFacetSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testFacetModuleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    // ------------------------------------------------------------------------
    // Receptacle test cases
    // ------------------------------------------------------------------------
    //    make -C receptacle_simple test
    //    make -C receptacle_module_simple test

    public void testReceptacleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testReceptacleModuleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_module_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
}