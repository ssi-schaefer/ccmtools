// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#include "type.h"

#include "value.h"
#include "struct.h"

#include <WX/Utils/error_macros.h>

#include <cassert>

namespace WX {
namespace Utils {

static SmartPtr<Value> empty_value_p;

LTA_MEMDEF(Type, 6, "$Id$");

Type::~Type()
{
   delete metadata_;
}

void
Type::add_metadataP(
   const std::string& key,
   const SmartPtr<Value>& v)
{
   if (!metadata_)
      metadata_ = new Struct;
   metadata_->setP(key, v);
}

bool
Type::get_metadataP(
   const std::string& key,
   SmartPtr<Value>& v)
const
{
   if (!metadata_)
      return false;
   return metadata_->getP(key, v);
}

const SmartPtr<Value>&
Type::get_metadataP(
   const std::string& key)
const
{
   if (!metadata_)
      return empty_value_p;
   return metadata_->getP(key);
}

void
Type::convert_from(
   Value* self,
   const Value* that)
const
{
   assert(self->type()==this);
   THROW_ERROR_MSG(IncompatibleConversion, "Cannot convert "<<that->typestr()<<" to "<<this->typestr());
}

}// /namespace
}// /namespace
