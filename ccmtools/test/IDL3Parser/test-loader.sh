#! /bin/sh

OUTPUT=test-`echo ${IDL} | sed 's,/,-,g'`.output
${JAVA} Main ${top_srcdir}/test/idl/${IDL}.idl 2>&1 > ${OUTPUT} \
    && cat ${OUTPUT} | grep -q "[s] ${EXPECTED}" && rm ${OUTPUT}

