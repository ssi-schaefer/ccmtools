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

package dtd2java;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


/**
 * Removes comments and expands entity calls.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version $Date$
 */
public class DtdPreprocessor
{
    // the processed code
    private String code_ = "";


    /**
     * Reads a DTD-file and stores the processed code.
     */
    public void read( String fileName ) throws java.io.IOException
    {
        File file = new File(fileName);
        int length = (int)file.length();
        char[] buffer = new char[length];
        FileReader r = new FileReader(file);
        r.read(buffer);
        r.close();
        for( int index=0; index<length; ++index )
        {
            if( buffer[index]=='`' )
            {
                buffer[index] = '\'';
            }
            else if( buffer[index]=='­' )   // character 173
            {
                buffer[index] = '-';
            }
        }
        code_ = new String(buffer);
        code_ = process1(code_,0);
        code_ = replace(code_, "''", "\"");
        code_ = process2(code_);
    }


    // removes comments
    private String process1( String text, int startIndex )
    {
        int index1 = text.indexOf("<!--",startIndex);
        if(index1>=startIndex)
        {
            int index2 = text.indexOf("-->",index1+4);
            if(index2>index1)
            {
                if(index1>startIndex)
                {
                    return text.substring(startIndex,index1)+process1(text, index2+3);
                }
                return process1(text, index2+3);
            }
        }
        if( startIndex==0 )
        {
            return text;
        }
        return text.substring(startIndex);
    }


    // expands entity calls
    private String process2( String text )
    {
        int index1 = text.indexOf("<!ENTITY");
        if(index1>=0)
        {
            int index2 = text.indexOf(">",index1+8);
            if(index2>index1)
            {
                String prefix = text.substring(0,index2+1);
                String buffer = text.substring(index1+8,index2).trim();
                text = text.substring(index2+1);
                if(buffer.charAt(0)=='%')
                {
                    buffer = buffer.substring(1).trim();
                }
                String name;
                index1 = buffer.indexOf("SYSTEM");
                if( index1>0 )
                {
                    name = buffer.substring(0,index1).trim();
                    buffer = buffer.substring(index1+6).trim();
                }
                else
                {
                    index1 = 1;
                    while(!Character.isWhitespace(buffer.charAt(index1))) index1++;
                    name = buffer.substring(0,index1);
                    buffer = buffer.substring(index1+1).trim();
                }
                index2 = buffer.lastIndexOf(buffer.charAt(0));
                buffer = buffer.substring(1,index2);
                text = replace(text, "%"+name+";", buffer);
                text = replace(text, "&"+name+";", buffer);
                return prefix+process2(text);
            }
        }
        return text;
    }


    private String replace( String theText, String oldText, String newText )
    {
        int index1 = theText.indexOf(oldText);
        if(index1<0)
        {
            return theText;
        }
        int length = oldText.length();
        if(index1==0)
        {
            return newText + replace(theText.substring(length), oldText, newText);
        }
        return theText.substring(0,index1) + newText +
                replace(theText.substring(index1+length), oldText, newText);
    }


    /**
     * Creates a new DTD-file.
     */
    public void write( String fileName ) throws java.io.IOException
    {
        FileWriter w = new FileWriter(fileName);
        w.write(code_);
        w.close();
    }
}
