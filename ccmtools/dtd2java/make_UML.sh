cd ../..
java -cp /tmp/dtd2java.jar:$CLASSPATH ccmtools.dtd2java.Main ccmtools/dtd2java/UML.dtd ccmtools.uml_parser
rm ccmtools/dtd2java/UML.dtd.PP.dtd
javac ccmtools/uml_parser/*.java ccmtools/uml_parser/uml/*.java
jar -cf /tmp/uml_parser.jar ccmtools/uml_parser/*.class ccmtools/uml_parser/uml/*.class
rm -rf ccmtools/uml_parser
