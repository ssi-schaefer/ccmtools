cd ../..
javac ccmtools/dtd2java/*.java
jar cf /tmp/dtd2java.jar ccmtools/dtd2java/*.class
rm ccmtools/dtd2java/*.class
