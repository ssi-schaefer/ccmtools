 // -*- mode: C++; c-basic-offset: 3 -*-
//
// $Id$
//
#ifndef wx_utils_types_type_simple_h
#define wx_utils_types_type_simple_h

#include "type.h"
#include "value_simple.h"

#include <WX/Utils/linkassert.h>

#include <string>


namespace WX {
namespace Utils {

class Value;
class IntValue;
class DoubleValue;
class StringValue;
class BooleanValue;

/**

   \brief Integer type

   \ingroup utils_types

 */
class IntType : public Type {
public:
   /** Boilerplate */
   //@{
   IntType(int i=0, bool withSign=false) : width_(i), withSign_(withSign) {}

   IntValue* narrow(Value*) const;
   const IntValue* const_narrow(const Value*) const;

   /** A singleton. One needs only one integer type instance; we
       provide it for convenience. */
   static const IntType* instance();
   //@}

   /** Type interface */
   //@{
   virtual IntValue* create() const;
   virtual bool can_assign(const Value&, Error* =NULL) const;
   virtual void assign(Value* self, const Value* that) const;
   virtual const std::string& typestr() const;
   virtual int compare(const Value* self, const Value* that) const;
   virtual Value* shallow_copy(const Value* self) const;
   virtual Value* toplevel_copy(const Value* self) const;
   virtual Value* deep_copy(const Value* self) const;
   virtual Value* fit(Value*) const;

   /** The default value: an IntValue instance with value zero (0) */
   virtual const Value* default_value() const;
   /** Same as default_value(), but returns a smart pointer to the
       default value instance */
   virtual const SmartPtr<Value>& default_valueP() const;

   void convert_from(Value* self, const Value* that) const;
   //@}

   /** Deprecated interface */
   //@{
   /** \deprecated formatting is not responsibility of the type. */
   int width() const { return width_; }
   /** \deprecated formatting is not responsibility of the type. */
   bool withSign() const { return withSign_; }
   //@}

private:
  int   width_;
  bool  withSign_;
public:
  LTA_MEMDECL(2);
};
LTA_STATDEF(IntType, 2);

/**

   \brief Floating point type (double)

   \ingroup utils_types

 */
class DoubleType : public Type {
public:
   /** Boilerplate */
   //@{
   DoubleType(int aftdot=0, int totlength=0,bool withSign=false) 
        : aftdot_(aftdot), totlength_(totlength), withSign_(withSign) {}

   DoubleValue* narrow(Value*) const;
   const DoubleValue* const_narrow(const Value*) const;

   /** Singleton */
   static const DoubleType* instance();
   //@}

   /** Type interface */
   //@{
   virtual DoubleValue* create() const;
   virtual bool can_assign(const Value&, Error* =NULL) const;
   virtual void assign(Value* self, const Value* that) const;
   virtual const std::string& typestr() const;
   virtual int compare(const Value* self, const Value* that) const;
   virtual Value* shallow_copy(const Value* self) const;
   virtual Value* toplevel_copy(const Value* self) const;
   virtual Value* deep_copy(const Value* self) const;
   virtual Value* fit(Value*) const;

   /** The default value: an DoubleValue instance with value being zero
       (0.0) */
   virtual const Value* default_value() const;
   /** Same as default_value(), but returns a smart pointer to the
       default value instance */
   virtual const SmartPtr<Value>& default_valueP() const;
   //@}

   /** Deprecated interface */
   //@{
   /** \deprecated formatting is not the responsibility of the
       type. */
   int aftdot() const { return aftdot_; }
   /** \deprecated formatting is not the responsibility of the
       type. */
   int totlength() const { return totlength_; }
   /** \deprecated formatting is not the responsibility of the
       type. */
   bool withSign() const { return withSign_; }
   //@}

private:
  int   aftdot_;
  int   totlength_;
  bool  withSign_;

public:
  LTA_MEMDECL(4);
};
LTA_STATDEF(DoubleType, 4);

/**

   \brief String type

   \ingroup utils_types

 */
class StringType : public Type {
public:
   /** Boilerplate */
   //@{
   StringType(int w=0) : width_(w) {}

   StringValue* narrow(Value*) const;
   const StringValue* const_narrow(const Value*) const;

   /** Singleton */
   static const StringType* instance();
   //@}

   /** Type interface. */
   //@{
   virtual StringValue* create() const;
   virtual bool can_assign(const Value&, Error* =NULL) const;
   virtual void assign(Value* self, const Value* that) const;
   virtual const std::string& typestr() const;
   virtual int compare(const Value* self, const Value* that) const;
   virtual Value* shallow_copy(const Value* self) const;
   virtual Value* toplevel_copy(const Value* self) const;
   virtual Value* deep_copy(const Value* self) const;
   virtual Value* fit(Value*) const;

   /** The default value: a StringValue instance with value being the
       empty string */
   virtual const Value* default_value() const;
   /** Same as default_value(), but returns a smart pointer to the
       default value instance */
   virtual const SmartPtr<Value>& default_valueP() const;

   virtual void convert_from(Value* self, const Value* that) const;
   //@}

   /** Deprecated interface */
   //@{
   /** \deprecated formatting is not the responsibility of the type */
   int width() const { return width_; }
   //@}

private:
   int width_;
public:
  LTA_MEMDECL(2);
};
LTA_STATDEF(StringType, 2);

/**

   \brief Boolean type

   \ingroup utils_types

 */
class BooleanType : public Type {
public:
   /** Boilerplate */
   //@{
   BooleanValue* narrow(Value*) const;
   const BooleanValue* const_narrow(const Value*) const;

   /** Singleton */
   static const BooleanType* instance();
   //@}

   /** Type interface */
   //@{
   virtual BooleanValue* create() const;
   virtual bool can_assign(const Value&, Error* =NULL) const;
   virtual void assign(Value* self, const Value* that) const;
   virtual const std::string& typestr() const;
   virtual int compare(const Value* self, const Value* that) const;
   virtual Value* shallow_copy(const Value* self) const;
   virtual Value* toplevel_copy(const Value* self) const;
   virtual Value* deep_copy(const Value* self) const;
   virtual Value* fit(Value*) const;

   /** The default value: a BooleanValue instance with value being
       false */
   virtual const Value* default_value() const;
   /** Same as default_value(), but returns a smart pointer to the
       default value instance */
   virtual const SmartPtr<Value>& default_valueP() const;
   //@}
   
public:
  LTA_MEMDECL(2);
};
LTA_STATDEF(BooleanType, 2);

class Init_Globals {
public:
   Init_Globals();
};
static Init_Globals type_simple_init_globals;

} // /namespace
} // /namespace

#endif
