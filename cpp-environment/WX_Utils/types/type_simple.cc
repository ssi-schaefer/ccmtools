// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#include "type_simple.h"

#include "value_simple.h"
#include "value_enum.h"

#include <WX/Utils/error_macros.h>

#include <stdio.h>
#include <cassert>

namespace WX {
namespace Utils {

struct TypeSingletons {
   TypeSingletons() {}
   const IntType it_singleton;
   const StringType st_singleton;
   const BooleanType bt_singleton;
   const DoubleType dt_singleton;
};

static TypeSingletons* my_tsingletons = NULL;

static inline
const TypeSingletons*
init_tsingletons()
{
   if (!my_tsingletons)
      my_tsingletons = new TypeSingletons;
   return my_tsingletons;
}

struct ValueSingletons {
   
   ValueSingletons()
   {
      def_intvalueP = SmartPtr<Value>(new IntValue);
      def_stringvalueP = SmartPtr<Value>(new StringValue);
      def_booleanvalueP = SmartPtr<Value>(new BooleanValue);
      def_doublevalueP = SmartPtr<Value>(new DoubleValue);
   }

   SmartPtr<Value> def_intvalueP;
   SmartPtr<Value> def_stringvalueP;
   SmartPtr<Value> def_booleanvalueP;
   SmartPtr<Value> def_doublevalueP;

   const IntValue def_intvalue;
   const StringValue def_stringvalue;
   const BooleanValue def_booleanvalue;
   const DoubleValue def_doublevalue;
};

static ValueSingletons* my_vsingletons = NULL;

static inline
const ValueSingletons*
init_vsingletons()
{
   if (!my_vsingletons)
      my_vsingletons = new ValueSingletons;
   return my_vsingletons;
}

Init_Globals::Init_Globals()
{
   init_vsingletons();
   init_tsingletons();
}

// human readable type descriptions
static const std::string iv_typestr("integer");
static const std::string sv_typestr("string");
static const std::string bv_typestr("boolean");
static const std::string dv_typestr("double");

static inline int compare_double(double l, double r) {
   if (l < r) return -1;
   if (l > r) return 1;
   return 0;
}

static inline int compare_int(int l, int r) {
   if (l < r) return -1;
   if (l > r) return 1;
   return 0;
}

// --------------------------------------------------------------------
LTA_MEMDEF(IntType, 2, "$Id$");

IntValue*
IntType::narrow(Value* v)
const
{
   if (v->type() == this)
      return dynamic_cast<IntValue*>(v);
   return NULL;
}

const IntValue*
IntType::const_narrow(
   const Value* v)
const
{
   if (v->type() == this)
      return dynamic_cast<const IntValue*>(v);
   return NULL;
}

const IntType*
IntType::instance()
{
   return &init_tsingletons()->it_singleton;
}

IntValue*
IntType::create()
const {
   return new IntValue(this);
}

bool
IntType::can_assign(const Value& v, Error* e) const {
   if (dynamic_cast<const IntValue*>(&v))
      return true;
   if (dynamic_cast<const EnumerationValue*>(&v))
      return true;
   if (e) {
      COMPOSE_ERROR_MSG(Error, err, v.typestr()<<" "
                        "does not match "<<typestr());
      *e = err;
   }
   return false;
}

void
IntType::assign(
   Value* self,
   const Value* that)
const
{
   if (self == that) return;
   IntValue* iself = this->narrow(self);
   assert(iself);

   // we consider an IntValue which has a different IntType to be
   // assignable to self (iow, we do not use const_narrow(), but
   // rather dynamic_cast()). this is mainly for historical reasons
   // where there were different IntTypes with different formatting
   // parameters around.
   const IntValue* ithat = dynamic_cast<const IntValue*>(that);
   if (ithat) {
      iself->set_value(ithat->value());
      return;
   }

   // any instance of EnumerationType can be assigned to an IntValue.
   const EnumerationValue* ethat = dynamic_cast<const EnumerationValue*>(that);
   if (ethat) {
      iself->set_value(ethat->get_as_int());
      return;
   }

   THROW_ERROR_MSG(Type::IncompatibleAssignment,
                   "Cannot assign "<<that->type()->typestr()<<" to "<<this->typestr());
}

const std::string&
IntType::typestr()
const
{
   return iv_typestr;
}

int
IntType::compare(
   const Value* self,
   const Value* that)
const
{
   if (self == that)
      return 0;

   const IntValue* iself = this->const_narrow(self);
   assert(iself);

   const IntValue* ithat = dynamic_cast<const IntValue*>(that);
   if (ithat)
      return compare_int(iself->value(), ithat->value());

   const DoubleValue* dthat = dynamic_cast<const DoubleValue*>(that);
   if (dthat)
      return compare_double(iself->value(), dthat->value());

   const EnumerationValue* ethat = dynamic_cast<const EnumerationValue*>(that);
   if (ethat)
      return compare_int(iself->value(), ethat->get_as_int());

   THROW_ERROR_MSG(Type::IncompatibleComparison,
                   "Cannot compare "<<this->typestr()<<" with "<<that->type()->typestr());
}

Value*
IntType::shallow_copy(
   const Value* self)
const
{
   const IntValue* iself = dynamic_cast<const IntValue*>(self);
   assert(iself);
   return new IntValue(iself->value());
}

Value*
IntType::toplevel_copy(
   const Value* self)
const
{
   const IntValue* iself = dynamic_cast<const IntValue*>(self);
   assert(iself);
   return new IntValue(iself->value());
}

Value*
IntType::deep_copy(
   const Value* self)
const
{
   const IntValue* iself = dynamic_cast<const IntValue*>(self);
   assert(iself);
   return new IntValue(iself->value());
}

Value*
IntType::fit(
   Value* v)
const
{
   IntValue* iv = dynamic_cast<IntValue*>(v);
   if (iv)
      return iv;
   const EnumerationValue* ev = dynamic_cast<const EnumerationValue*>(v);
   if (ev)
      return new IntValue(ev->get_as_int());
   THROW_ERROR_MSG(Error, "Cannot convert "<<v->type()->typestr()<<" to "<<this->typestr());
}

const Value*
IntType::default_value()
const
{
   return &init_vsingletons()->def_intvalue;
}

const SmartPtr<Value>&
IntType::default_valueP()
const
{
   return init_vsingletons()->def_intvalueP;
}

void
IntType::convert_from(
   Value* self,
   const Value* that)
const
{
   IntValue* iself = dynamic_cast<IntValue*>(self);
   assert(iself);

   const IntValue* ithat = dynamic_cast<const IntValue*>(that);
   if (ithat) {
      iself->set_value(ithat->value());
      return;
   }
   const StringValue* sthat = dynamic_cast<const StringValue*>(that);
   if (sthat) {
      const char* str = sthat->value().c_str();
      int len = strlen(str);
      char* end;
      int i = strtol(str, &end, 10);
      if (end != str+len) {
         THROW_ERROR_MSG(Error, 
                         "Could not read integer value. "<<
                         end-str<<" characters of "<<len<<" were consumed, "
                         "buffer was \""<<str<<"\".");
      }
      iself->set_value(i);
      return;
   }
   const EnumerationValue* ethat = dynamic_cast<const EnumerationValue*>(that);
   if (ethat) {
      iself->set_value(ethat->get_as_int());
      return;
   }
   // hey, this is a quick hack. so don't complain about that
   // assertion.
   assert(0);
}

// --------------------------------------------------------------------
LTA_MEMDEF(DoubleType, 4, "$Id$");

DoubleValue*
DoubleType::narrow(Value* v)
const
{
   if (v->type() == this)
      return dynamic_cast<DoubleValue*>(v);
   return NULL;
}

const DoubleValue*
DoubleType::const_narrow(
   const Value* v)
const
{
   if (v->type() == this)
      return dynamic_cast<const DoubleValue*>(v);
   return NULL;
}

const DoubleType*
DoubleType::instance()
{
   return &init_tsingletons()->dt_singleton;
}

DoubleValue*
DoubleType::create()
const
{
   return new DoubleValue(this);
}

bool
DoubleType::can_assign(
   const Value& v,
   Error* e) const
{
   if (dynamic_cast<const DoubleValue*>(&v))
      return true;
   if (e) {
      COMPOSE_ERROR_MSG(Error, err, v.typestr()<<" "
                        "does not match "<<typestr());
      *e = err;
   }
   return false;
}

void
DoubleType::assign(
   Value* self,
   const Value* that)
const
{
   if (self == that) return;
   DoubleValue* dself = this->narrow(self);
   assert(dself);

   // we consider a DoubleValue which has a different DoubleType to be
   // assignable to self (iow, we do not use const_narrow(), but
   // rather dynamic_cast()). this is mainly for historical reasons
   // where there were different DoubleTypes with different formatting
   // parameters around.
   const DoubleValue* dthat = dynamic_cast<const DoubleValue*>(that);
   if (dthat) {
      dself->set_value(dthat->value());
      return;
   }
   // same rational for not using IntType::instance()->const_narrow()
   // at this point.
   const IntValue* ithat = dynamic_cast<const IntValue*>(ithat);
   if (ithat) {
      dself->set_value(ithat->value());
      return;
   }

   THROW_ERROR_MSG(Type::IncompatibleAssignment,
                   "Cannot assign "<<that->type()->typestr()<<" to double");
}

const std::string&
DoubleType::typestr()
const
{
   return dv_typestr;
}

int
DoubleType::compare(
   const Value* self,
   const Value* that)
const
{
   if (self == that)
      return 0;

   const DoubleValue* dself = this->const_narrow(self);
   assert(dself);

   const IntValue* ithat = dynamic_cast<const IntValue*>(that);
   if (ithat)
      return compare_double(dself->value(), ithat->value());

   const DoubleValue* dthat = dynamic_cast<const DoubleValue*>(that);
   if (dthat)
      return compare_double(dself->value(), dthat->value());

   THROW_ERROR_MSG(Type::IncompatibleComparison,
                   "Cannot compare "<<this->typestr()<<" with "<<that->type()->typestr());
}

Value*
DoubleType::shallow_copy(
   const Value* self)
const
{
   const DoubleValue* dself = dynamic_cast<const DoubleValue*>(self);
   assert(dself);
   return new DoubleValue(dself->value());
}

Value*
DoubleType::toplevel_copy(
   const Value* self)
const
{
   const DoubleValue* dself = dynamic_cast<const DoubleValue*>(self);
   assert(dself);
   return new DoubleValue(dself->value());
}

Value*
DoubleType::deep_copy(
   const Value* self)
const
{
   const DoubleValue* dself = dynamic_cast<const DoubleValue*>(self);
   assert(dself);
   return new DoubleValue(dself->value());
}

Value*
DoubleType::fit(
   Value* v)
const
{
   DoubleValue* dv = dynamic_cast<DoubleValue*>(v);
   if (dv)
      return v;
   THROW_ERROR_MSG(Error, "Cannot convert "<<v->type()->typestr()<<" to "<<this->typestr());
}

const Value*
DoubleType::default_value()
const
{
   return &init_vsingletons()->def_doublevalue;
}

const SmartPtr<Value>&
DoubleType::default_valueP()
const
{
   return init_vsingletons()->def_doublevalueP;
}

// --------------------------------------------------------------------
LTA_MEMDEF(StringType, 2, "$Id$");

StringValue*
StringType::narrow(
   Value* v)
const
{
   if (v->type() == this)
      return dynamic_cast<StringValue*>(v);
   return NULL;
}

const StringValue*
StringType::const_narrow(
   const Value* v)
const
{
   if (v->type() == this)
      return dynamic_cast<const StringValue*>(v);
   return NULL;
}

const StringType*
StringType::instance()
{
   return &init_tsingletons()->st_singleton;
}

StringValue*
StringType::create()
const
{
   return new StringValue(this);
}

bool
StringType::can_assign(
   const Value& v,
   Error* e)
const
{
   if (dynamic_cast<const StringValue*>(&v))
      return true;
   if (e) {
      COMPOSE_ERROR_MSG(Error, err, v.typestr()<<" "
                        "does not match "<<typestr());
      *e = err;
   }
   return false;
}

void
StringType::assign(
   Value* self,
   const Value* that)
const
{
   if (self == that) return;
   StringValue* sself = this->narrow(self);
   assert(sself);

   // we consider a StringValue which has a different StringType to be
   // assignable to self (iow, we do not use const_narrow(), but
   // rather dynamic_cast()). this is mainly for historical reasons
   // where there were different StringTypes with different formatting
   // parameters around.
   const StringValue* sthat = dynamic_cast<const StringValue*>(that);
   if (sthat) {
      sself->set_value(sthat->value());
      return;
   }
   const EnumerationValue* ethat = dynamic_cast<const EnumerationValue*>(that);
   if (ethat) {
      sself->set_value(ethat->get_as_string());
      return;
   }
   THROW_ERROR_MSG(Type::IncompatibleAssignment,
                   "Cannot assign "<<that->type()->typestr()<<" to string");
}

const std::string&
StringType::typestr()
const
{
   return sv_typestr;
}

int
StringType::compare(
   const Value* self,
   const Value* that)
const
{
   if (self == that)
      return 0;

   const StringValue* sself = this->const_narrow(self);
   assert(sself);

   const StringValue* sthat = dynamic_cast<const StringValue*>(that);
   if (sthat)
      return sself->value().compare(sthat->value());

   THROW_ERROR_MSG(Type::IncompatibleComparison,
                   "Cannot compare "<<this->typestr()<<" with "<<that->type()->typestr());
}

Value*
StringType::shallow_copy(
   const Value* self)
const
{
   const StringValue* sself = dynamic_cast<const StringValue*>(self);
   assert(sself);
   return new StringValue(sself->value());
}

Value*
StringType::toplevel_copy(
   const Value* self)
const
{
   const StringValue* sself = dynamic_cast<const StringValue*>(self);
   assert(sself);
   return new StringValue(sself->value());
}

Value*
StringType::deep_copy(
   const Value* self)
const
{
   const StringValue* sself = dynamic_cast<const StringValue*>(self);
   assert(sself);
   return new StringValue(sself->value());
}

Value*
StringType::fit(
   Value* v)
const
{
   StringValue* sv = dynamic_cast<StringValue*>(v);
   if (sv)
      return v;
   const EnumerationValue* ev = dynamic_cast<const EnumerationValue*>(v);
   if (ev)
      return new StringValue(ev->get_as_string());
   THROW_ERROR_MSG(Error, "Cannot convert "<<v->type()->typestr()<<" to "<<this->typestr());
}

const Value*
StringType::default_value()
const
{
   return &init_vsingletons()->def_stringvalue;
}

const SmartPtr<Value>&
StringType::default_valueP()
const
{
   return init_vsingletons()->def_stringvalueP;
}

void
StringType::convert_from(
   Value* self,
   const Value* that)
const
{
   StringValue* sself = dynamic_cast<StringValue*>(self);
   assert(sself);
   
   const StringValue* sthat = dynamic_cast<const StringValue*>(that);
   if (sthat) {
      sself->set_value(sthat->value());
      return;
   }
   const IntValue* ithat = dynamic_cast<const IntValue*>(that);
   if (ithat) {
      int value = ithat->value();
      char buf[64];
      std::sprintf(buf, "%d", value);
      sself->set_value(buf);
      return;
   }
   const EnumerationValue* ethat = dynamic_cast<const EnumerationValue*>(that);
   if (ethat) {
      sself->set_value(ethat->get_as_string());
      return;
   }
   // hey, this is a quick hack. so don't complain about that
   // assertion.
   assert(0);
}

// --------------------------------------------------------------------
LTA_MEMDEF(BooleanType, 2, "$Id$");

BooleanValue*
BooleanType::narrow(Value* v)
const
{
   if (v->type() == this)
      return dynamic_cast<BooleanValue*>(v);
   return NULL;
}

const BooleanValue*
BooleanType::const_narrow(
   const Value* v)
const
{
   if (v->type() == this)
      return dynamic_cast<const BooleanValue*>(v);
   return NULL;
}

const BooleanType*
BooleanType::instance()
{
   return &init_tsingletons()->bt_singleton;
}

BooleanValue*
BooleanType::create()
const
{
   return new BooleanValue(this);
}

bool
BooleanType::can_assign(
   const Value& v,
   Error* e)
const
{
   if (dynamic_cast<const BooleanValue*>(&v))
      return true;
   if (e) {
      COMPOSE_ERROR_MSG(Error, err, v.typestr()<<" "
                        "does not match "<<typestr());
      *e = err;
   }
   return false;
}

void
BooleanType::assign(
   Value* self,
   const Value* that)
const
{
   if (self == that)
      return;

   BooleanValue* bself = dynamic_cast<BooleanValue*>(self);
   assert(bself);

   // we consider a BooleanValue which has a different BooleanType to
   // be assignable to self (iow, we do not use const_narrow(), but
   // rather dynamic_cast()). this is mainly for historical reasons
   // where there were different BooleanTypes with different
   // formatting parameters around.
   const BooleanValue* bthat = dynamic_cast<const BooleanValue*>(that);
   if (bthat) {
      bself->set_value(bthat->value());
      return;
   }

   THROW_ERROR_MSG(Type::IncompatibleAssignment,
                   "Cannot assign "<<that->type()->typestr()<<" to "<<this->typestr());
}

const std::string&
BooleanType::typestr()
const
{
   return bv_typestr;
}

int
BooleanType::compare(
   const Value* self,
   const Value* that)
const
{
   if (self == that) 
      return 0;
   
   const BooleanValue* bself = this->const_narrow(self);
   assert(bself);

   const BooleanValue* bthat = dynamic_cast<const BooleanValue*>(that);
   if (bthat)
      return bself->value() - bthat->value();

   THROW_ERROR_MSG(Type::IncompatibleComparison,
                   "Cannot compare "<<this->typestr()<<" with "<<that->type()->typestr());
}

Value*
BooleanType::shallow_copy(
   const Value* self)
const
{
   const BooleanValue* bself = dynamic_cast<const BooleanValue*>(self);
   assert(bself);
   return new BooleanValue(bself->value());
}

Value*
BooleanType::toplevel_copy(
   const Value* self)
const
{
   const BooleanValue* bself = dynamic_cast<const BooleanValue*>(self);
   assert(bself);
   return new BooleanValue(bself->value());
}

Value*
BooleanType::deep_copy(
   const Value* self)
const
{
   const BooleanValue* bself = dynamic_cast<const BooleanValue*>(self);
   assert(bself);
   return new BooleanValue(bself->value());
}

Value*
BooleanType::fit(
   Value* v)
const
{
   BooleanValue* bv = dynamic_cast<BooleanValue*>(v);
   if (bv)
      return v;
   THROW_ERROR_MSG(Error, "Cannot convert "<<v->type()->typestr()<<" to "<<this->typestr());
}

const Value*
BooleanType::default_value()
const
{
   return &init_vsingletons()->def_booleanvalue;
}

const SmartPtr<Value>&
BooleanType::default_valueP()
const
{
   return init_vsingletons()->def_booleanvalueP;
}

} // /namespace
} // /namespace
