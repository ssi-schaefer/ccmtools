cd ../..
javac -classpath /tmp/uml_parser.jar:$CLASSPATH ccmtools/uml2idl/*.java
jar -cf /tmp/uml2idl.jar ccmtools/uml2idl/*.class
rm ccmtools/uml2idl/*.class
