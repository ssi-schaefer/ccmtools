// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#ifndef wx_utils_types_value_simple_h
#define wx_utils_types_value_simple_h

#include "value.h"

#include <WX/Utils/linkassert.h>

#include <string>


namespace WX {
namespace Utils {

class IntType;
class DoubleType;
class StringType;
class BooleanType;

/**

   \brief Integer value - instance of IntType

   \ingroup utils_types

 */
class IntValue : public Value {
public:
   /** Boilerplate */
   //@{
   IntValue(int i=0);
   IntValue(const IntType*);
   IntValue(const Value& v);
   IntValue(const IntValue& v);

   /** My own interface */
   //@{
   /** Returns the value I carry. */
   int value() const { return value_; }
   void set_value(int v) { value_ = v; }
   /** Returns a pointer to the narrowed type of value if the value
       is-a IntValue. NULL if not. */
   static IntValue* narrow(Value*);
   //@}

private:
   int value_;

public:
   LTA_MEMDECL(1);
};
LTA_STATDEF(IntValue, 1);

/**

   \brief Double precision floating point value (double) - instance of
   DoubleType

   \ingroup utils_types

   Carries a double, whatever that is on your architecture.

 */
class DoubleValue : public Value {
public:
   /** Boilerplate */
   //@{
   DoubleValue(double d=0.0);
   DoubleValue(const DoubleType*);
   DoubleValue(const Value& v);
   DoubleValue(const DoubleValue& v);
   /** 
       Assign that to me
       
       \throw Type::IncompatibleAssignment Type mismatch
   */
   DoubleValue& operator=(const Value& that) { Value::assign(that); return *this; }
   //@}

   /** Value interface */
   //@{
   /** Returns an integer <0 if this is less than v, >0 if greater,
       and ==0 if equal. */
//    virtual int compare(const Value& v) const;
   //@}


   /** My own interface */
   //@{
   /** Returns the value I carry. */
   double value() const { return value_; }
   void set_value(double d) { value_ = d; }
   /** Returns a pointer to the narrowed type of value if the value
       is-a DoubleValue. NULL if not. */
   static DoubleValue* narrow(Value*);
   //@}

private:
   double value_;
   
public:
   LTA_MEMDECL(3);
};
LTA_STATDEF(DoubleValue, 3);

/**

   \brief String value - instance of StringType

   \ingroup utils_types

 */
class StringValue : public Value {
public:
   /** Boilerplate  */
   //@{
   StringValue();
   StringValue(const std::string& s);
   StringValue(const StringType*);
   StringValue(const Value& v);
   StringValue(const StringValue& v);
   /** 
       Assign that to me
       
       \throw Type::IncompatibleAssignment Type mismatch
   */
   StringValue& operator=(const Value& that) { Value::assign(that); return *this; }
   //@}

   /** My own interface */
   //@{
   /** Returns the value I carry. */
   const std::string& value() const { return value_; }
   void set_value(const std::string& s) { value_ = s; }

   /** Returns a pointer to the narrowed type of value if the value
       is-a StringValue. NULL if not. */
   static StringValue* narrow(Value*);

   StringValue& operator+=(const StringValue&);
   //@}

private:
   std::string value_;
   
public:
   LTA_MEMDECL(2);
};
LTA_STATDEF(StringValue, 2);

StringValue operator+(const StringValue&, const StringValue&);

/**

   \brief Boolean value - instance of BooleanType

   \ingroup utils_types

 */
class BooleanValue : public Value {
public:
   /** Boilerplate */
   //@{
   /** Constructor. Initializes this with the value of b, if
       specified. If used as a default ctor, the BooleanValue's value
       is false. */
   BooleanValue(bool b=false);
   BooleanValue(const BooleanType*);
   BooleanValue(const Value& v);
   BooleanValue(const BooleanValue& v);
   /** 
       Assign that to me
       
       \throw Type::IncompatibleAssignment Type mismatch
   */
   BooleanValue& operator=(const Value& that) { Value::assign(that); return *this; }
   //@}

   /** My own interface */
   //@{
   /** Returns the value I carry. */
   bool value() const { return value_; }
   void set_value(bool b) { value_ = b; }
   /** Returns a pointer to the narrowed type of value if the value
       is-a BooleanValue. NULL if not. */
   static BooleanValue* narrow(Value*);
   //@}

private:
   bool value_;

public:
   LTA_MEMDECL(1);
};
LTA_STATDEF(BooleanValue, 1);

} // /namespace
} // /namespace

#endif
