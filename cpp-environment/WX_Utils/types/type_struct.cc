// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#include "type_struct.h"

#include "struct.h"

#include <WX/Utils/error_macros.h>

#include <algorithm>
#include <functional>
#include <cassert>

namespace WX {
namespace Utils {

LTA_MEMDEF(StructType, 2, "$Id$");

using namespace std;

struct MembersEqual : 
   public std::binary_function<StructType::StructMember, StructType::StructMember, bool> {
   bool operator()(
      const StructType::StructMember& l,
      const StructType::StructMember& r)
   const
   {
      return l.first == r.first;
   }
};

static inline
void
is_unique(
   const StructType::StructMembers& members)
{
   StructType::StructMembers::const_iterator end = members.end();
   if (members.size() == 0)
      return;
   for (StructType::StructMembers::const_iterator i = members.begin();
        i != end-1;
        ++i) {
      if (std::find_if(i+1, end, std::bind1st(MembersEqual(), *i)) != end) {
         THROW_ERROR_MSG(StructType::DuplicateMembers, "Duplicate struct member \""<<i->first<<'"');
      }
   }
}

static const SmartPtr<Type> __wx_empty_type__;
static
const SmartPtr<Type>&
find_member_type(
   const StructType::StructMembers& mems,
   const string& name)
{
   StructType::StructMembers::const_iterator pos =
      find_if(mems.begin(), mems.end(), std::bind1st(MembersEqual(), make_pair(name, SmartPtr<Type>())));
   if (pos == mems.end())
      return __wx_empty_type__;
   return pos->second;
}

// static const std::string sv_typestr("struct");
static const StructType st_anonymous;
static const StructValue sv_default;

StructType::StructType(
   const std::string& name,
   const StructMembers& members)
: name_(name),
  members_(members)
{
   assert(name_.size());
   typestr_ = "struct ";
   typestr_ += name;
   is_unique(members);

   default_ = SmartPtr<StructValue>(new StructValue(this));
   defaultP_ = SmartPtr<Value>(default_.ptr());
}

StructType::StructType()
{
   default_ = SmartPtr<StructValue>(new StructValue(this));
   defaultP_ = SmartPtr<Value>(default_.ptr());
}

StructType::~StructType() {}

const StructType*
StructType::anonymous()
{
   return &st_anonymous;
}

void
StructType::make_default(
   Struct& s)
const
{
   s = Struct();
   for (StructMembers::const_iterator i = this->members().begin();
        i != this->members().end();
        ++i)
      s.setP(i->first, SmartPtr<Value>(i->second.ptr()->default_value()->deep_copy()));
}

StructValue*
StructType::narrow(
   Value* v)
const
{
   if (v->type() != this)
      return NULL;
   StructValue* ret = dynamic_cast<StructValue*>(v);
   assert(ret); // paranoia
   return ret;
}

const StructValue*
StructType::const_narrow(
   const Value* v)
const
{
   return narrow(const_cast<Value*>(v));
}

StructValue*
StructType::create()
const
{
   return new StructValue(this);
}

bool
StructType::can_assign(
   const Value& v,
   Error* e)
const
{
   return this->is_anonymous() || this->const_narrow(&v);
}

void
StructType::assign(
   Value* self,
   const Value* that)
const
{
   if (that == self)
      return;

   StructValue* sself = this->narrow(self);
   assert(sself);

   if (!this->can_assign(*that)) {
      THROW_ERROR_MSG(Type::IncompatibleAssignment,
                      "Cannot assign "<<that->type()->typestr()<<" to "<<this->typestr());
   }

   // if self is an anonymous struct, anything can be assigned to
   // self. can_assign() has checked type compatibility anyway, so all
   // we need is a struct type - no need to narrow().
   const StructValue* sthat = dynamic_cast<const StructValue*>(that);
   assert(sthat);

   sself->value_ = sthat->value_;
}

const Value*
StructType::default_value()
const
{
   return default_.cptr();
}

const SmartPtr<Value>&
StructType::default_valueP()
const
{
   return defaultP_;
}

int
StructType::compare(
   const Value* self,
   const Value* that)
const
{
   // self must have the exact StructType (i.e., this - we have been
   // called by self, after all)
   const StructValue* sself = this->const_narrow(self);
   assert(sself);

   // that must have at least struct type.
   const StructValue* sthat = dynamic_cast<const StructValue*>(that);
   if (!sthat) {
      THROW_ERROR_MSG(Type::IncompatibleComparison,
                      "Cannot compare "<<this->typestr()<<" with "<<that->type()->typestr());
   }

   const StructType* that_type = dynamic_cast<const StructType*>(sthat->type());
   assert(that_type);

   // two structs are comparable if they have the same exact type, or
   // if at least one of them is an anonymous struct.
   if (!(this == that_type || this->is_anonymous() || that_type->is_anonymous())) {
      THROW_ERROR_MSG(Type::IncompatibleComparison,
                      "Cannot compare "<<this->typestr()<<" with "<<that_type->typestr());
   }

   // finally, go compare them. comparison is done using
   // Struct::compare().

   return sself->value_.compare(sthat->value_);
}

Value*
StructType::shallow_copy(
   const Value* self)
const
{
   const StructValue* sself = this->const_narrow(self);
   assert(sself);
   StructValue* ret = new StructValue(this);
   ret->value_ = sself->value_;
   return ret;
}

Value*
StructType::toplevel_copy(
   const Value* self)
const
{
   const StructValue* sself = this->const_narrow(self);
   assert(sself);
   StructValue* ret = new StructValue(this);
   sself->value_.toplevel_copy(ret->value_);
   return ret;
}

Value*
StructType::deep_copy(
   const Value* self)
const
{
   const StructValue* sself = this->const_narrow(self);
   assert(sself);
   StructValue* ret = new StructValue(this);
   sself->value_.deep_copy(ret->value_);
   return ret;
}

Value*
StructType::fit(
   Value* v)
const
{
   if (v->type() != this) {
      THROW_ERROR_MSG(Error, "Cannot fit "<<v->type()->typestr()<<" into "<<this->typestr());
   }      
   return v;
}

const Value&
StructType::get(
   const StructValue* self,
   const std::string& name)
const
{
   if (!this->is_anonymous()) {
      const SmartPtr<Type>& t = find_member_type(members_, name);
      if (!t) {
         THROW_ERROR_MSG(NoSuchMember,
                         "No member \""<<name<<"\" in "<<this->typestr());
      }
   }

   try {
      return self->value_.get(name);
   }
   catch (const Error&) {

      // get() can only fail if the struct is of anonymous type. if it
      // has "strong type", then we guarantee to return a meaningful
      // value, simply because we fill it with its default members on
      // first set(), or because we retrieve the value from our
      // default_ instance if nothing was set() before.
      assert(this->is_anonymous());

      throw;
   }
}
 
const SmartPtr<Value>&
StructType::getP(
   const StructValue* self,
   const std::string& name)
const
{
   if (!this->is_anonymous()) {
      const SmartPtr<Type>& t = find_member_type(members_, name);
      if (!t) {
         THROW_ERROR_MSG(NoSuchMember,
                         "No member \""<<name<<"\" in "<<this->typestr());
      }
   }

   const SmartPtr<Value>& ret = self->value_.getP(name);

   // if this is not an anonymous struct, we insist in having a value
   // because we filled in the default struct with its default
   // members.
   assert(this->is_anonymous() || ret);

   return ret;
}

void
StructType::set(
   StructValue* self,
   const std::string& name,
   const Value& value)
const
{
   setP(self, name, SmartPtr<Value>(value.shallow_copy()));
}

void
StructType::setP(
   StructValue* self,
   const string& n,
   const SmartPtr<Value>& v)
const
{
   assert(v);

   Value* fit_v = v.ptr();
   assert(fit_v);

   // if self is not an anonymous struct, we insist in having the
   // member defined. the given value must fit with the member type.

   if (!this->is_anonymous()) {
      const SmartPtr<Type> t = find_member_type(members_, n);
      if (!t) {
         THROW_ERROR_MSG(StructType::NoSuchMember,
                         "No member "<<n<<" in "<<this->typestr());
      }

      try {
         fit_v = t.ptr()->fit(fit_v);
      }
      catch (const Error& e) {
         THROW_ERROR_ERROR_MSG(TypeMismatch, e,
                               "Cannot set member \""<<n<<'"');
      }
   }
   
   self->value_.setP(n, SmartPtr<Value>(fit_v));
}

int
StructType::n(
   const StructValue* self)
const
{
   return self->value_.n();
}

bool
StructType::first(
   const StructValue* self,
   std::string& name,
   SmartPtr<Value>& value)
const
{
   return self->value_.first(name, value);
}

bool
StructType::next(
   const StructValue* self,
   std::string& name,
   SmartPtr<Value>& value)
const
{
   return self->value_.next(name, value);
}

} // /namespace
} // /namespace
