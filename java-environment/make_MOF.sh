#! /bin/sh
#
# $Id$
#

#
#   INIT
#
ROOT=$PWD
LIB=$ROOT/lib
BUILD=$ROOT/build
SOURCE=$ROOT/src


#
#   dtd2java
#
ant dtd2java.jar


#
#   mof_parser
#
cd $SOURCE
rm -rf mof_parser
java -classpath $LIB/antlr.jar:$LIB/dtd2java.jar dtd2java.Main dtd2java/MOF.dtd mof_parser
rm dtd2java/MOF.dtd.PP.dtd
tar cfz $BUILD/mof_parser.tar.gz mof_parser
rm -rf mof_parser

#
#   EXIT
#
cd $ROOT
ls -l build/mof_parser.tar.gz

