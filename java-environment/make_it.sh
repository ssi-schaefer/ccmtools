#! /bin/sh
#
# $Id$
#

#
#   INIT
#
ROOT=$PWD
LIB=$ROOT/lib
BUILD=$ROOT/tmp
mkdir $BUILD


#
#   dtd2java
#
cd $ROOT/dtd2java
java -classpath $LIB/antlr.jar antlr.Tool dtd.g
javac -classpath $LIB/antlr.jar -d $BUILD *.java
rm DtdLexer.java DtdParser.java DTDTokenTypes.java DTDTokenTypes.txt
cd $BUILD
rm -f $LIB/dtd2java.jar
jar cf $LIB/dtd2java.jar ccmtools/dtd2java/*.class


#
#   uml_parser, uml2idl
#
cd $ROOT
java -classpath $LIB/antlr.jar:$LIB/dtd2java.jar ccmtools.dtd2java.Main dtd2java/UML.dtd ccmtools.uml_parser
rm dtd2java/UML.dtd.PP.dtd
javac -d $BUILD ccmtools/uml_parser/*.java ccmtools/uml_parser/uml/*.java
cd $ROOT
rm -rf ccmtools/uml_parser
rmdir ccmtools
cd uml2idl
javac -classpath $BUILD -d $BUILD *.java
cd $BUILD
rm -f $LIB/uml2idl.jar
jar cf $LIB/uml2idl.jar ccmtools/uml_parser/*.class ccmtools/uml_parser/uml/*.class ccmtools/uml2idl/*.class


#
#   EXIT
#
cd $ROOT
rm -rf $BUILD
