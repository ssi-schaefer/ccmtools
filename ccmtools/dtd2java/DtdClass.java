/* DTD code generator
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

package ccmtools.dtd2java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.HashSet;


/**
 * The Java-Class of a DTD-element.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version December 2003
 */
public class DtdClass
{
    // special content "#PCDATA"
    static final String PCDATA = "#PCDATA";

    // special content "EMPTY"
    static final String EMPTY = "EMPTY";

    // special content "ANY"
    static final String ANY = "ANY";

    // fakes "#PCDATA"
    static final DtdClass FAKE_PCDATA = new DtdClass(PCDATA);

    // fakes "EMPTY"
    static final DtdClass FAKE_EMPTY = new DtdClass(EMPTY);

    // fakes "ANY"
    static final DtdClass FAKE_ANY = new DtdClass(ANY);


    // creates a fake
    private DtdClass( String fake )
    {
        dtdName_ = dtdCode_ = fake;
        package_ = "java.lang";
        name_ = "String";
        isFake_ = true;
    }


    // Java class name
    private String name_;

    // Java package name.
    private String package_;

    // Java source file
    private File source_;

    /**
     * children; instances of {@link dtd2java.DtdClass}
     */
    private Vector children_  = new Vector();


    // XML-name
    private String dtdName_;

    // DTD-code of the element
    private String dtdCode_;

    // DTD-code of the attributes
    private String dtdAttributes_;

    // the attribute list
    private DtdAttributes attributes_;

    // is (possibly) the root element
    private boolean isRoot_;

    // is a fake
    private boolean isFake_;

    // the Java-names of the children
    private Vector childrenJavaNames_;


    /**
     * Mark as root element.
     */
    public void setRoot()
    {
        isRoot_ = true;
    }


    /**
     * Returns the name of the Java class.
     */
    public String getJavaName()
    {
        return name_;
    }


    /**
     * Creates a Java class.
     *
     * @param dtdName  DTD-name of the element
     * @param javaPackage  the Java-name of the base package
     * @param element  the DTD-element
     * @param attributes  the attribute list
     */
    public DtdClass( String dtdName, String javaPackage, DtdElement element,
                     DtdAttributes attributes )
    {
        dtdName_ = dtdName;
        dtdCode_ = element.text();
        if( attributes!=null )
        {
            dtdAttributes_ = attributes.text();
            attributes_ = attributes;
        }
        String name = makeName(dtdName,false);
	    int index = name.lastIndexOf(":");
	    if( index<0 )
	    {
	        package_ = javaPackage;
	        name_ = name;
	    }
	    else
	    {
	        package_ = javaPackage + "." + name.substring(0,index).replace(':', '.').toLowerCase();
	        name_ = name.substring(index+1);
	    }
	    name_ = "M"+name_;
	    File directory = new File(package_.replace('.', File.separatorChar));
        if( !directory.isDirectory() )
        {
            directory.mkdirs();
        }
        source_ = new File(directory, name_+".java");
    }


    // creates a valid Java identifier
	static String makeName( String dtdName, boolean convertColon )
	{
	    char[] buffer = dtdName.toCharArray();
	    for( int index=0; index<buffer.length; index++ )
	    {
	        char c = buffer[index];
	        if( c==':' )
	        {
	            if( convertColon )
	            {
	                buffer[index] = '_';
	            }
	        }
	        else if( !(c>='a' && c<='z') && !(c>='A' && c<='Z') && !(c>='0' && c<='9') )
	        {
	            buffer[index] = '_';
	        }
	    }
	    return new String(buffer);
	}


    /**
     * Creates all children.
     */
	public void parseContent( DtdContent content, DtdGenerator generator )
	{
		if( content instanceof DtdName )
		{
		    children_.add(generator.createClass( ((DtdName)content).getName() ));
		}
		else if( content instanceof DtdConstant )
		{
		    children_.add(generator.createClass( ((DtdConstant)content).getName() ));
		}
		else
		{
   		    Vector buffer = new Vector();
  		    content.expand(buffer);
    		int size = buffer.size();
    		for( int index=0; index<size; index++ )
    		{
    		    DtdContent con = (DtdContent)buffer.get(index);
    		    children_.add(createChild(con, generator));
    		}
		}
	}


    // creates one child
	private DtdClass createChild( DtdContent content, DtdGenerator generator )
	{
		if( content instanceof DtdName )
		{
		    return generator.createClass( ((DtdName)content).getName() );
		}
		if( content instanceof DtdConstant )
		{
		    return generator.createClass( ((DtdConstant)content).getName() );
		}
		return null; // this should never happen
	}


    private boolean isEmpty_, isAny_;

    /**
     * Creates the Java source-code.
     *
     * @param basePackage  the Java-name of the base package
     */
	public String createCode( String basePackage ) throws IOException
	{
	    isEmpty_ = isAny_ = false;
        if( children_.size()==1 )
        {
            DtdClass c = (DtdClass)children_.get(0);
            if( c.dtdCode_.equals(EMPTY) )
            {
                isEmpty_=true;
            }
            else if( c.dtdCode_.equals(ANY) )
            {
                isAny_=true;
            }
        }
	    FileWriter writer = new FileWriter(source_);
	    writer.write(DtdGenerator.MAIN_HEADER);
	    writer.write(
	        "package "+package_+";\n\n"+
            "import org.xml.sax.*;\n\n"+
	        "/**\nDTD-element <b> "+dtdName_+
	        " </b>. <br>Element declaration:\n<pre>\n"+
	        dtdCode_.replace('<',' ').replace('>',' ')+"\n");
	    if( dtdAttributes_==null )
	    {
	        writer.write("</pre>\n\n");
	    }
	    else
	    {
	        writer.write("\n"+dtdAttributes_.replace('<',' ').replace('>',' ')+"\n</pre>\n\n");
	    }
	    int size, index;
	    if( !isEmpty_ && !isAny_ )
	    {
	        boolean printHeader = true;
    	    size = children_.size();
    	    for( index=0; index<size; index++ )
    	    {
    	        DtdClass c = (DtdClass)children_.get(index);
    	        if( !c.isFake_ )
    	        {
    	            if( printHeader )
    	            {
    	                writer.write("List of children:\n<ul>\n");
    	                printHeader = false;
    	            }
    	            writer.write("<li>{@link "+c.package_+"."+c.name_+"}</li>\n");
    	        }
    	    }
    	    if( !printHeader )
    	    {
    	        writer.write("</ul>\n");
    	    }
	    }
	    writer.write("*/\n"+
	                 "public class "+name_+" extends "+basePackage+"."+(isRoot_?"DTD_Root":"DTD_Container")+"\n{\n");
	    if( attributes_!=null )
	    {
    	    size = attributes_.size();
    	    for( index=0; index<size; index++ )
    	    {
    	        createAttribute( attributes_.get(index), writer );
    	    }
	    }
	    writer.write("  public "+name_+"( Attributes attrs )\n"+
	                 "  {\n"+
	                 "    super();\n");
	    if( attributes_!=null )
	    {
	        writer.write("    if(attrs!=null) {\n"+
	                     "      int length = attrs.getLength();\n"+
	                     "      for( int index=0; index<length; index++ ) {\n"+
	                     "        String name = attrs.getQName(index);\n"+
	                     "        String value = attrs.getValue(index);\n");
    	    size = attributes_.size();
    	    for( index=0; index<size; index++ )
    	    {
    	        DtdAttribute attr = attributes_.get(index);
    	        writer.write("        ");
    	        if( index>0 )  writer.write("else ");
    	        writer.write("if( name.equals(\""+attr.getName()+"\") )  "+attr.javaName_+"=value;\n");
    	    }
    	    writer.write("      }\n"+
    	                 "    }\n");
	    }
        writer.write("  }\n\n"+
                     "  /** the XML-name of this element */\n"+
                     "  public static final String xmlName__ = \""+dtdName_+"\";\n\n"+
	                 "  public String xmlName()\n"+
	                 "  {return xmlName__;}\n\n"+
                     "  public String xmlCode()\n"+
                     "  {\n"+
                     "    String code = \"<"+dtdName_+"\";\n");
        if( attributes_!=null )
        {
    	    size = attributes_.size();
    	    for( index=0; index<size; index++ )
    	    {
    	        DtdAttribute attr = attributes_.get(index);
    	        writer.write("    if("+attr.javaName_+"!=null)  code += \" "+
    	                     attr.getName()+"=\"+makeString("+attr.javaName_+");\n");
    	    }
        }
        writer.write("    code += \" >\";\n"+
                     "    code += makeContent();\n"+
                     "    code += \"</"+dtdName_+">\\n\";\n"+
                     "    return code;\n"+
                     "  }\n\n"+
                     "}\n");
	    writer.close();
	    return "    if(qName.equals(\""+dtdName_+"\"))  return new "+package_+"."+name_+"(attrs);\n";
	}


	private void createAttribute( DtdAttribute attr, FileWriter writer ) throws IOException
	{
	    String name = attr.getName();
	    writer.write("  /** Attribute <b> "+name+" </b>. <br>DTD-code:\n  <pre>\n  "+attr.text()+"\n  </pre>\n  */\n");
        attr.javaName_ = makeName(name,true)+"_";
	    writer.write("  public String "+attr.javaName_);
	    String init = attr.getDefaultValue();
	    if( init!=null )
	    {
	        writer.write(" = \""+init+"\"");
	    }
	    writer.write(";\n\n");
	}


    /**
     * Returns the code for the main dot-file.
     * Call this method only after a call to {@link #createCode}.
     */
	public String getDotCode()
	{
	    String myName = "\""+dtdName_+"\"";
	    String code = myName+" [shape=ellipse";
	    if( isRoot_ )
	    {
	        code += ", peripheries=2";
	    }
	    code += "];\n";
	    if( isAny_ )
	    {
	        code += myName+" -> ANY;\n";
	    }
	    else if( !isEmpty_ )
	    {
    	    int size = children_.size();
    	    for( int index=0; index<size; index++ )
    	    {
    	        DtdClass c = (DtdClass)children_.get(index);
    	        if( !c.isFake_ )
    	        {
    	            code += myName+" -> \""+c.dtdName_+"\";\n";
    	        }
    	    }
    	    return code;
	    }
	    return code;
	}


	private boolean dot2init_, dot2finished_;

    /**
     * Prepares a call to {@link #getDotCode2}.
     */
	public void initDotData()
	{
	    dot2finished_ = false;
	    dot2init_ = true;
	    int size = children_.size();
	    for( int index=0; index<size; index++ )
	    {
	        DtdClass c = (DtdClass)children_.get(index);
	        if( !c.dot2init_ || c.dot2finished_ )
	        {
	            c.initDotData();
	        }
	    }
	}


    /**
     * Returns the code for the special dot-files.
     * Call this method only after a call to {@link #createCode} and {@link #initDotData}.
     */
	public String getDotCode2()
	{
	    dot2finished_ = true;
	    String code = getDotCode();
	    int size = children_.size();
	    for( int index=0; index<size; index++ )
	    {
	        DtdClass c = (DtdClass)children_.get(index);
	        if( !c.isFake_ && !c.dot2finished_ )
	        {
	            code += c.getDotCode2();
	        }
	    }
	    return code;
	}
}
