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
SOURCE=$ROOT/src
mkdir $BUILD


#
#   dtd2java
#
cd $SOURCE/dtd2java
java -classpath $LIB/antlr.jar antlr.Tool dtd.g
javac -classpath $LIB/antlr.jar -d $BUILD *.java
rm DtdLexer.java DtdParser.java DTDTokenTypes.java DTDTokenTypes.txt
cd $BUILD
rm -f $LIB/dtd2java.jar
jar cf $LIB/dtd2java.jar dtd2java/*.class


#
#   uml_parser, uml2idl
#
cd $SOURCE
rm -rf uml_parser
java -classpath $LIB/antlr.jar:$LIB/dtd2java.jar dtd2java.Main dtd2java/UML.dtd uml_parser
rm dtd2java/UML.dtd.PP.dtd
javac -d $BUILD uml_parser/*.java uml_parser/uml/*.java
cd $SOURCE/uml2idl
javac -classpath $BUILD -d $BUILD *.java
cd $BUILD
rm -f $LIB/uml2idl.jar
jar cf $LIB/uml2idl.jar uml_parser/*.class uml_parser/uml/*.class uml2idl/*.class


#
#   EXIT
#
cd $ROOT
rm -rf $BUILD
