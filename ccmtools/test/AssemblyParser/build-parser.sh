#! /bin/sh

thisdir=$(pwd)
root="$thisdir/../.."
libdir="$root/lib"

cd $root
ant jar.AssemblyMain || exit 1
export CLASSPATH=$libdir/java-cup-11a.jar:$libdir/assembly.jar

cd $thisdir

