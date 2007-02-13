@echo off

if "%JAVA_HOME%" == "" goto JavaHomeError

echo Run CORBA naming service on port 5050...
echo (orbd -ORBInitialPort 5050)
%JAVA_HOME%\bin\orbd -ORBInitialPort 5050
goto end


:JavaHomeError
	echo !!!ERROR: Environment variable JAVA_HOME not found!
	
:end
