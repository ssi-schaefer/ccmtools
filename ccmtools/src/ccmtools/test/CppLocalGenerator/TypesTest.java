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

package ccmtools.test.CppLocalGenerator;

import ccmtools.test.CcmtoolsTestCase;

/*******************************************************************************
 * This test case is derived from CcmtoolsTestCase class and implements tests
 * for idl3, idl3mirror, c++local, c++local-test code generators. These tests
 * check the code generation process, and the runtime behavior of the generated
 * code (using Confix' _check_*.cc files).
 ******************************************************************************/
public class TypesTest extends CcmtoolsTestCase
{

    private String ccmtoolsDir;
    private String testDir;

    public TypesTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public void testVersionOption()
    {
        runCcmtoolsGenerate("--version");
    }

    public void testHelpOption()
    {
        runCcmtoolsGenerate("--help");
    }

    
    // ------------------------------------------------------------------------
    // Attribute test cases
    // ------------------------------------------------------------------------

    public void testAttributeTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/attribute_types test");
        }
        catch(Exception e) {
            fail();
        }
    }

    
    // ------------------------------------------------------------------------
    // Supports test cases
    // ------------------------------------------------------------------------

    public void testSupportsAttribute()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_attribute test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testSupportsException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testSupportsInheritance()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_inheritance test");
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

    
    // ------------------------------------------------------------------------
    // Facet test cases
    // ------------------------------------------------------------------------

    public void testFacetAttribute()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_attribute test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testFacetException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testFacetInheritance()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_inheritance test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testFacetRename()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_rename test");
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

    public void testFacetModuleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_exception test");
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

    
    // ------------------------------------------------------------------------
    // Receptacle test cases
    // ------------------------------------------------------------------------

    public void testReceptacleAttribute()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_attribute test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleInheritance()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_inheritance test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleMultiple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_multiple test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleNotConnected()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_not_connected test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleObject()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_object test");
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

    
    // ------------------------------------------------------------------------
    // Home test cases
    // ------------------------------------------------------------------------
    
    public void testHomeException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/home_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testHomeTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/home_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    // ------------------------------------------------------------------------
    // Include test cases
    // ------------------------------------------------------------------------
    
    public void testIncludeNested()
    {
        try {
            executeCommandLine("make -C " + testDir + "/include_nested test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    // ------------------------------------------------------------------------
    // Module test cases
    // ------------------------------------------------------------------------

    public void testModuleMixed()
    {
        try {
            executeCommandLine("make -C " + testDir + "/module_mixed test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testModuleNested()
    {
        try {
            executeCommandLine("make -C " + testDir + "/module_nested test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testModuleReopen()
    {
        try {
            executeCommandLine("make -C " + testDir + "/module_reopen test");
        }
        catch(Exception e) {
            fail();
        }
    }

    
    // ------------------------------------------------------------------------
    // Assembly test cases
    // ------------------------------------------------------------------------
    
    public void testAssemblyNested()
    {
        try {
            executeCommandLine("make -C " + testDir + "/assembly_nested test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    // ------------------------------------------------------------------------
    // Any test cases
    // ------------------------------------------------------------------------
    public void testAnyPlugin()
    {
        try {
            executeCommandLine("make -C " + testDir + "/any_plugin test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
}