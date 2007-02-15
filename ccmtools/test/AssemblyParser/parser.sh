#! /bin/sh

thisdir=$(pwd)
root="$thisdir/../.."
libdir="$root/lib"

export CLASSPATH=$libdir/java-cup-11a.jar:$libdir/assembly.jar
java ccmtools.parser.assembly.Main "$@" || exit 1

