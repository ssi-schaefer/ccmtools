#! /bin/sh

libdir=$PWD/../../lib
export CLASSPATH=$libdir/java-cup-11a.jar:$libdir/assembly.jar
java ccmtools.parser.assembly.Main $*
