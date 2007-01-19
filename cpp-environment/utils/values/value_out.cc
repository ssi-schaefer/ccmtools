#include "value_out.h"
#include "value_simple.h"
#include "StructValue.h"

namespace wamas {
namespace platform {
namespace utils {


static void output(std::ostream& os, const StructValue& st)
{
    StructValue::Pair p = st.all_members();
    while(p.first!=p.second)
    {
        std::string name = (p.first)->first;
        SmartPtr<Value> v = (p.first)->second;
        ++(p.first);
        os << '(';
        os << name << ':';
        output (os, v);
        os << ')' << std::endl;
    }
}


void output(std::ostream& os, const SmartPtr<Value>& v)
{
    const Value* pv = v.cptr();
    if (!pv)
    {
        os << "(null)";
        return;
    }
    //
    {
        LongBuilder i;
        if (i.can_assign(*pv)) {
            i = *pv;
            os << i.value();
            return;
        }
    }
    {
        ShortBuilder i;
        if (i.can_assign(*pv)) {
            i = *pv;
            os << i.value();
            return;
        }
    }
    {
        DoubleBuilder d;
        if (d.can_assign(*pv)) {
            d = *pv;
            int p = os.precision();
            os.precision(10);
            os << d.value();
            os.precision(p);
            return;
        }
    }
    {
        FloatBuilder d;
        if (d.can_assign(*pv)) {
            d = *pv;
            int p = os.precision();
            os.precision(10);
            os << d.value();
            os.precision(p);
            return;
        }
    }
    {
        StringBuilder s;
        if (s.can_assign(*pv)) {
            s = *pv;
            os << s.value();
            return;
        }
    }
    {
        BooleanBuilder b;
        if (b.can_assign(*pv)) {
            b = *pv;
            os << b.value();
            return;
        }
    }
    {
        const StructValue* psv = dynamic_cast<const StructValue*>(pv);
        if(psv) {
            output(os, *psv);
            os << std::endl;
            return;
        }
    }
    os << "(unknown type: " << pv->typestr() << ")";
}


} // /namespace
} // /namespace
}// /namespace
