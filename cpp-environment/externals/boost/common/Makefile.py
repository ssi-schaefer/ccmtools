# $Id$

PROVIDE_SYMBOL('BOOST_COMMON')
REQUIRE_SYMBOL('BOOST_ENTRY', REQUIRED)

for f in ['boost/array_traits.hpp',
          'boost/call_traits.hpp',
          'boost/cast.hpp',
          'boost/compose.hpp',
          'boost/compressed_pair.hpp',
          'boost/concept_archetype.hpp',
          'boost/concept_check.hpp',
          'boost/config.hpp',
          'boost/counting_iterator.hpp',
          'boost/cstdint.hpp',
          'boost/cstdlib.hpp',
          'boost/functional.hpp',
          'boost/function_output_iterator.hpp',
          'boost/half_open_range.hpp',
          'boost/integer.hpp',
          'boost/integer_traits.hpp',
          'boost/iterator_adaptors.hpp',
          'boost/iterator.hpp',
          'boost/lexical_cast.hpp',
          'boost/limits.hpp',
          'boost/min_rand.hpp',
          'boost/nondet_random.hpp',
          'boost/operators.hpp',
          'boost/progress.hpp',
          'boost/property_map.hpp',
          'boost/random.hpp',
          'boost/rational.hpp',
          'boost/smart_ptr.hpp',
          'boost/shared_ptr.hpp',
          'boost/static_assert.hpp',
          'boost/stdint.h',
          'boost/timer.hpp',
          'boost/type.hpp',
          'boost/type_traits.hpp',
          'boost/detail/atomic_count.hpp',
          'boost/utility.hpp'
          ]:
    PROVIDE_H(f)

ACINCLUDE_M4(
    lines=['AC_DEFUN([CONFIX_BOOST_COMMON], [AC_REQUIRE([CONFIX_BOOST])])'],
    propagate_only=1)

CONFIGURE_IN(
    lines=['CONFIX_BOOST_COMMON'],
    order=AC_LIBRARIES,
    propagate_only=1)

EXTERNAL_LIBRARY2(inc='@BOOST_INC@')
