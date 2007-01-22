// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#ifndef wx_utils_code_debug_h
#define wx_utils_code_debug_h

#include <sstream>
#include <string>

#include "DebugWriterMgr.h"

namespace wamas {
namespace platform {
namespace utils {

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

   /** Have debugging messages with \c level printed out */
   void add_level(const std::string& level);

   void parse_levels(const std::string& levelstr);

   /** Check whether we output debugging messages with \c level */
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

      
#ifdef WXDEBUG
#  define WRITE_MSG_TO_DEBUGWRITER(msg,facility,level)\
   {\
      std::ostringstream os;\
      os << msg << std::ends;\
      wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().write(__FILE__,__LINE__,facility,\
                          level,os.str());\
   }

  // Trace level
#  define TRACE(msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(""))\
      WRITE_MSG_TO_DEBUGWRITER(msg, "",wamas::platform::utils::DebugWriter::Trace)\
   }

#  define TRACENL(msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(""))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n',"",wamas::platform::utils::DebugWriter::Trace)\
   }

#  define LTRACE(facility,msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(#facility))\
         WRITE_MSG_TO_DEBUGWRITER(msg,#facility,wamas::platform::utils::DebugWriter::Trace)\
   }

#  define LTRACENL(facility,msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(#facility))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n',#facility,wamas::platform::utils::DebugWriter::Trace)\
   }

  // Debug level
#  define DEBUG(msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(""))\
      WRITE_MSG_TO_DEBUGWRITER(msg, "",wamas::platform::utils::DebugWriter::Debug)\
   }

#  define DEBUGNL(msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(""))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n',"",wamas::platform::utils::DebugWriter::Debug)\
   }

#  define LDEBUG(facility,msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(#facility))\
         WRITE_MSG_TO_DEBUGWRITER(msg,#facility,wamas::platform::utils::DebugWriter::Debug)\
   }

#  define LDEBUGNL(facility,msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(#facility))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n',#facility,wamas::platform::utils::DebugWriter::Debug)\
   }

  // Notify level
#  define NOTIFY(msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(""))\
      WRITE_MSG_TO_DEBUGWRITER(msg, "",wamas::platform::utils::DebugWriter::Notify)\
   }

#  define NOTIFYNL(msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(""))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n',"",wamas::platform::utils::DebugWriter::Notify)\
   }

#  define LNOTIFY(facility,msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(#facility))\
         WRITE_MSG_TO_DEBUGWRITER(msg,#facility,wamas::platform::utils::DebugWriter::Notify)\
   }

#  define LNOTIFYNL(facility,msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(#facility))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n',#facility,wamas::platform::utils::DebugWriter::Notify)\
   }

  // Alert level
#  define ALERT(msg)\
   {\
      if (wamas::platform::utils::EmergWriterMgr::instance().getDebugWriter().check(""))\
      WRITE_MSG_TO_DEBUGWRITER(msg, "",wamas::platform::utils::DebugWriter::Alert)\
   }

#  define ALERTNL(msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(""))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n',"",wamas::platform::utils::DebugWriter::Alert)\
   }

#  define LALERT(facility,msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(#facility))\
         WRITE_MSG_TO_DEBUGWRITER(msg,#facility,wamas::platform::utils::DebugWriter::Alert)\
   }

#  define LALERTNL(facility,msg)\
   {\
      if (wamas::platform::utils::DebugWriterMgr::instance().getDebugWriter().check(#facility))\
         WRITE_MSG_TO_DEBUGWRITER(msg<<'\n',#facility,wamas::platform::utils::DebugWriter::Alert)\
   }

#else
#  define TRACE(msg)
#  define TRACENL(msg)
#  define LTRACE(facility,msg)
#  define LTRACENL(facility,msg)
#  define DEBUG(msg)
#  define DEBUGNL(msg)
#  define LDEBUG(facility,msg)
#  define LDEBUGNL(facility,msg)
#  define NOTIFY(msg)
#  define NOTIFYNL(msg)
#  define LNOTIFY(facility,msg)
#  define LNOTIFYNL(facility,msg)
#  define ALERT(msg)
#  define ALERTNL(msg)
#  define LALERT(facility,msg)
#  define LALERTNL(facility,msg)
#endif

} // /namespace
} // /namespace
} // /namespace

#endif
