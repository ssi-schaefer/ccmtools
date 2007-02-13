@echo off

if "%JAVA_HOME%" == "" goto JavaHomeError

if "%CCMTOOLS_HOME%" == "" goto CcmtoolsHomeError

set CLASSPATH=%CCMTOOLS_HOME%\lib\antlr.jar;%CCMTOOLS_HOME%\lib\java-cup-11a.jar;%CCMTOOLS_HOME%\lib\commons-cli-1.0.jar;%CCMTOOLS_HOME%\lib\jdom.jar;%CCMTOOLS_HOME%\lib\ccmtools.jar;%CCMTOOLS_HOME%\lib\ccm-runtime.jar;%CLASSPATH% 
%JAVA_HOME%\bin\java -cp %CLASSPATH% -Dccmtools.home=%CCMTOOLS_HOME% -Djava.util.logging.config.file=%CCMTOOLS_HOME%\etc\logging.properties ccmtools.parser.idl.metamodel.Main %*

goto end

:JavaHomeError
	echo !!!ERROR: Environment variable JAVA_HOME not found!
	goto end
	
:CcmtoolsHomeError
	echo !!!ERROR: Environment variable CCMTOOLS_HOME not found!
	
:end
	