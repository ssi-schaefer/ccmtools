#! /bin/sh

idldir=${top_srcdir}/../test/idl/Calculator

${top_srcdir}/test/CppGenerator/test-loader.sh Calculator \
  "-I${idldir}" \
  "${idldir}/Display.idl ${idldir}/IntegerCalculator.idl ${idldir}/Calculator.idl"

