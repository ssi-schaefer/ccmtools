// -*- mode: C++; c-basic-offset: 3 -*-
//
// $Id$
//
#include "errorout_ostream.h"

#include "error_impl.h"
#include "errortrace.h"

namespace wamas {
namespace platform {
namespace utils {

using namespace std;

// --------------------------------------------------------------------
static inline void output_offset(ostream& o, int offset) {
   for (int i=0; i<offset; i++)
      o << ' ';
}

void output(const SmartPtr<ErrorNode>& n, ostream& o, int offset)
{
    output_offset(o, offset);

    o << n->message()
      << " (" << ( n->file().size() ? n->file(): "(unknown)")
      << ':'
      << n->line() << ":)\n";
}

void output(const ErrorTrace& t, ostream& o, int offset)
{
    for (int i=0; i<(int)t.trace().size(); i++) {
        if(i)
            o << '\n';

        output(t.trace()[i], o, offset);

        if (t.trace()[i]->children().trace().size()) {
            output(t.trace()[i]->children(), o, offset+2);
        }
    }
}

void output(const Error& e, ostream& o, int offset)
{
    SmartPtr<ErrorNode> root=e.root();

    if ( root ) {
        output(root, o, offset);
        if (root->children().trace().size())
            output(root->children(), o, offset+2);
    }
}

} // /namespace
} // /namespace
} // /namespace
