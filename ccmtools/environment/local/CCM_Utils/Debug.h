#ifndef __CCM__UTILS__DEBUG__H__
#define __CCM__UTILS__DEBUG__H__

#ifdef HAVE_CONFIG_H
# include <config.h>
#endif

// CONFIX:CHECK(AC_CXX_HAVE_SSTREAM())
#ifdef HAVE_SSTREAM
# include <sstream>
#else
# include <strstream.h>
#endif

#include <cassert>
#include <string>

#include "DebugWriterManager.h"

namespace CCM_Utils {

/**
 * \brief Enable/disable/select debugging.
 */
class Debug {
public:
   /** Have all debugging messages printed out */
   static void set_global ( bool b );

   /** Check whether we output all debugging messages */
   static bool get_global (  );

   /** Have debugging messages with \code level printed out */
   static void add_level ( const std::string& level );

   /** Check whether we output debugging messages with \code level */
   static bool have_level ( const std::string& level );
};

/**
 * \def LDEBUGNL(level,msg)
 *
 * \brief Output a message \a msg plus newline if \a level is on.
 *
 * \a msg is stringified by the C preprocessor, so you don't enclose
 * it into double quotes. \a msg is anything an ostream can
 * handle. For example,
 *
 * \code
 * int n = some_number (  );
 * LDEBUGNL(MyLevel, "Here I have "<<n<<" pieces");
 * \endcode
 */


/**
 * \def LDEBUG(level,msg)
 *
 * \brief Same as LDEBUGNL, but without newline
 */

/**
 * \def IFDEBUG(level,cmd)
 *
 * \brief Execute \a cmd if \a level is on.
 */

#ifdef CCM_DEBUG

#  ifdef HAVE_SSTREAM
#    define WRITE_MSG_TO_DEBUGWRITER(msg, level)\
     {\
        std::ostringstream os;\
        os << msg << std::ends;\
        CCM_Utils::DebugWriterManager::instance().getDebugWriter().write(__FILE__,__LINE__,level,os.str());\
     }
#  else
#    define WRITE_MSG_TO_DEBUGWRITER(msg, level)\
     {\
        ostrstream os;\
        os << msg << ends;\
        CCM_Utils::DebugWriterManager::instance().getDebugWriter().write(__FILE__,__LINE__,level,os.str());\
        os.freeze(false);\
     }
#  endif

#  define DEBUG(msg)\
   {\
      if (CCM_Utils::DebugWriterManager::instance().getDebugWriter().check(""))\
         WRITE_MSG_TO_DEBUGWRITER(msg, "")\
   }

#  define DEBUGNL(msg)\
   {\
      if (CCM_Utils::DebugWriterManager::instance().getDebugWriter().check(""))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n', "")\
   }

#  define LDEBUG(level,msg)\
   {\
      if (CCM_Utils::DebugWriterManager::instance().getDebugWriter().check(#level))\
         WRITE_MSG_TO_DEBUGWRITER(msg, #level)\
   }

#  define LDEBUGNL(level,msg)\
   {\
      if (CCM_Utils::DebugWriterManager::instance().getDebugWriter().check(#level))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n', #level)\
   }

/*
#  define IFDEBUG1(cmd,os,par1) {\
  if (CCM_Utils::DebugWriterManager::instance().getDebugWriter().check("")) { \
        cmd(os,par1);\
        os<<ends; \
        CCM_Utils::DebugWriterManager::instance().getDebugWriter().write(__FILE__,__LINE__,"",os.str()); \
        os.freeze(false);\
      } \
  }
#  define IFLDEBUG1(level,cmd,os,par1) {\
  if (CCM_Utils::DebugWriterManager::instance().getDebugWriter().check(#level)) { \
        cmd(os,par1);\
        os<<ends; \
        CCM_Utils::DebugWriterManager::instance().getDebugWriter().write(__FILE__,__LINE__,"",os.str()); \
        os.freeze(false);\
      } \
  }
#  define IFDEBUG2(cmd,os,par1,par2) {\
  if (CCM_Utils::DebugWriterManager::instance().getDebugWriter().check("")) { \
        cmd(os,par1,par2);\
        os<<ends; \
        CCM_Utils::DebugWriterManager::instance().getDebugWriter().write(__FILE__,__LINE__,"",os.str()); \
        os.freeze(false);\
      } \
  }
#  define IFLDEBUG2(level,cmd,os,par1,par2) {\
  if (CCM_Utils::DebugWriterManager::instance().getDebugWriter().check(#level)) { \
        cmd(os,par1,par2);\
        os<<ends; \
        CCM_Utils::DebugWriterManager::instance().getDebugWriter().write(__FILE__,__LINE__,"",os.str()); \
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
#endif // if define CCM_DEBUG

} // /namespace CCM_Utils

#endif


