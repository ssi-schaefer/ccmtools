/*** 
 * CCM Tools : Constants
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2003 Salomon Automation
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
 ***/

package ccmtools;

public class Constants 
{
    public static final String PACKAGE  = "ccmtools";
    public static final String VERSION  = "0.8.0";
    public static final String CPP_PATH = "cpp";

    public static final String  CCMTOOLS_VERSION_TEXT = "CCM Tools version " + Constants.VERSION;

    public static final String  CCMTOOLS_COPYRIGHT_TEXT = 
        "Copyright (C) 2002 - 2006 Salomon Automation \n"   +
        "The CCM Tools library is distributed under the \n" +
        "terms of the GNU Lesser General Public License.";

    public static final String  VERSION_TEXT = CCMTOOLS_VERSION_TEXT + "\n" + CCMTOOLS_COPYRIGHT_TEXT;
    
    public static final String USAGE_TEXT = 
       "\nUsage: ccmtools GENERATOR [Options] FILES\n\n"
	   + "Options:\n"
       + "  -a, --application             Generate skeletons for business logic \n"
       + "  -h, --help                    Display this help\n"
       + "  -Ipath                        Add path to the preprocessor include path\n"
       + "  -o DIR, --output=DIR          Base output in DIR (default .)\n"
       + "  -V, --version                 Display CCM Tools version information\n"
       + "  --generator-mask=<flags>      Mask for generator debug output\n"
       + "  --parser-mask=<flags>         Mask for parser debug output\n"
	   + "  --noexit                      Don't exit Java VM with error status\n\n"

       + "Available GENERATOR types:\n"
       + "  c++local        Generate local C++ components \n"
       + "  c++local-test   Generate local C++ test client\n"
	   + "  c++dbc          Generate local C++ components with DbC (experimental)\n"
	   + "  c++remote       Generate remote C++ components (for Mico ORB)";

    public static final String[] GENERATOR_TYPES = 
    {
            "c++local", "c++local-test", "c++dbc", "c++remote",
            "c++remote-test", "idl3", "idl3mirror", "idl2"
    };
}

