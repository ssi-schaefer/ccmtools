#! /bin/sh

OUTPUT=test-`echo ${IDL} | sed 's,/,-,g'`.output
${JAVA} Main ${top_srcdir}/test/idl/${IDL}.idl 2>&1 > ${OUTPUT} \
    && cat ${OUTPUT} \
     | sed -e '1,/^Defined symbols:$/d' \
     | sed -e '/IDL 3 Symbol Table/,$d' \
     | grep -q "${EXPECTED}" \
    && rm ${OUTPUT}

