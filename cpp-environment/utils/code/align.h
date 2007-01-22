// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#ifndef wx_utils_code_align_h
#define wx_utils_code_align_h

namespace wamas {
namespace platform {
namespace utils {

// FIXME: is this ALIGNMENT calculation correct? >>>
struct _al {
   void* p ;
   char c ;
} ;
static const size_t ALIGNMENT = sizeof(_al) - sizeof(void*);

} // /namespace
} // /namespace
} // /namespace

#endif
