// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#ifndef wx_utils_code_debug_h
#define wx_utils_code_debug_h

#ifdef HAVE_CONFIG_H
# include <config.h>
#endif

// instruct Confix to add the autoconf check AC_CXX_HAVE_SSTREAM (from
// the Autoconf macro archive which is delivered with Confix; see
// http://www.gnu.org/software/ac-archive/htmldoc/ac_cxx_have_sstream.html)
// to the generated configure.in in the toplevel directory.

// we know from the documentation of this M4 macro (from the Autoconf
// macro archive) that it defines HAVE_SSTREAM if <sstream> is
// available.

// CONFIX:CONFIGURE_IN(lines=['AC_CXX_HAVE_SSTREAM'],
// CONFIX:             order=AC_HEADERS,
// CONFIX:             id='AC_CXX_HAVE_SSTREAM')

#ifdef HAVE_SSTREAM
# include <sstream>
#else
# include <strstream.h>
#endif 

#include <string>

#include "DebugWriterMgr.h"

namespace WX {
namespace Utils {

/**

   \brief Enable/disable/select debugging.

   \ingroup utils_code

*/
class Debug {
public:
   Debug(bool read_env=true);
   Debug(const std::string& levels, bool read_env=true);
   ~Debug();

   /** Have all debugging messages printed out */
   void set_global(bool b) { global_=b; }

   /** Check whether we output all debugging messages */
   bool get_global() { return global_; }

   /** Have debugging messages with \code level printed out */
   void add_level(const std::string& level);

   void parse_levels(const std::string& levelstr);

   /** Check whether we output debugging messages with \code level */
   bool have_level(const std::string& level);
   int n_levels();

   /** singleton */
   static Debug& instance();

private:
   class DebugImpl* impl_;
   bool global_;
};

/**

   \def LDEBUGNL(level,msg)

   \ingroup utils_code

   \brief Output a message \a msg plus newline if \a level is on.

   \a level is stringified by the C preprocessor, so you don't enclose
   it into double quotes.

   \a msg is anything an ostream can handle. For example,

   \code 
   
   int n = some_number();
   LDEBUGNL(MyLevel, "Here I have "<<n<<" pieces");

   \endcode

 */


/**

   \def LDEBUG(level,msg)

   \brief Same as LDEBUGNL, but without newline

   \ingroup utils_code

*/

/**

   \def IFDEBUG(level,cmd)

   \brief Execute \a cmd if \a level is on.

   \ingroup utils_code

*/


#ifdef WXDEBUG

#  ifdef HAVE_SSTREAM
#    define WRITE_MSG_TO_DEBUGWRITER(msg, level)\
     {\
        std::ostringstream os;\
        os << msg << std::ends;\
        WX::Utils::DebugWriterMgr::instance().getDebugWriter().write(__FILE__,__LINE__,level,os.str());\
     }
#  else
#    define WRITE_MSG_TO_DEBUGWRITER(msg, level)\
     {\
        ostrstream os;\
        os << msg << ends;\
        WX::Utils::DebugWriterMgr::instance().getDebugWriter().write(__FILE__,__LINE__,level,os.str());\
        os.freeze(false);\
     }
#  endif

#  define DEBUG(msg)\
   {\
      if (WX::Utils::DebugWriterMgr::instance().getDebugWriter().check(""))\
         WRITE_MSG_TO_DEBUGWRITER(msg, "")\
   }

#  define DEBUGNL(msg)\
   {\
      if (WX::Utils::DebugWriterMgr::instance().getDebugWriter().check(""))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n', "")\
   }

#  define LDEBUG(level,msg)\
   {\
      if (WX::Utils::DebugWriterMgr::instance().getDebugWriter().check(#level))\
         WRITE_MSG_TO_DEBUGWRITER(msg, #level)\
   }

#  define LDEBUGNL(level,msg)\
   {\
      if (WX::Utils::DebugWriterMgr::instance().getDebugWriter().check(#level))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n', #level)\
   }

/*
#  define IFDEBUG1(cmd,os,par1) {\
  if (WX::Utils::DebugWriterMgr::instance().getDebugWriter().check("")) { \
        cmd(os,par1);\
        os<<ends; \
        WX::Utils::DebugWriterMgr::instance().getDebugWriter().write(__FILE__,__LINE__,"",os.str()); \
        os.freeze(false);\
      } \
  }
#  define IFLDEBUG1(level,cmd,os,par1) {\
  if (WX::Utils::DebugWriterMgr::instance().getDebugWriter().check(#level)) { \
        cmd(os,par1);\
        os<<ends; \
        WX::Utils::DebugWriterMgr::instance().getDebugWriter().write(__FILE__,__LINE__,"",os.str()); \
        os.freeze(false);\
      } \
  }
#  define IFDEBUG2(cmd,os,par1,par2) {\
  if (WX::Utils::DebugWriterMgr::instance().getDebugWriter().check("")) { \
        cmd(os,par1,par2);\
        os<<ends; \
        WX::Utils::DebugWriterMgr::instance().getDebugWriter().write(__FILE__,__LINE__,"",os.str()); \
        os.freeze(false);\
      } \
  }
#  define IFLDEBUG2(level,cmd,os,par1,par2) {\
  if (WX::Utils::DebugWriterMgr::instance().getDebugWriter().check(#level)) { \
        cmd(os,par1,par2);\
        os<<ends; \
        WX::Utils::DebugWriterMgr::instance().getDebugWriter().write(__FILE__,__LINE__,"",os.str()); \
        os.freeze(false);\
      } \
  }
*/
#else
#  define DEBUG(msg)
#  define DEBUGNL(msg)
#  define LDEBUG(level,msg)
#  define LDEBUGNL(level,msg)
/*
#  define IFDEBUG1(cmd,os,par1)
#  define IFLDEBUG1(level,cmd,os,par1)
#  define IFDEBUG2(cmd,os,par1,par2)
#  define IFLDEBUG2(level,cmd,os,par1,par2)
*/
#endif

} // /namespace
} // /namespace

#endif
