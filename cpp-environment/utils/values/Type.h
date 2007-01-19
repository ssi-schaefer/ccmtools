#ifndef WX__Utils__Type_H
#define WX__Utils__Type_H

#include <wamas/platform/utils/smartptr.h>
#include <wamas/platform/utils/error.h>
#include <string>

namespace wamas {
namespace platform {
namespace utils {

class Value;
typedef SmartPtr<Value> SmartValue;

class Builder;
typedef SmartPtr<Builder> SmartBuilder;


/// for all kind of type problems
class TypeError : public Error {};


/**
@brief abstract base for all value types
*/
class Type : virtual public wamas::platform::utils::RefCounted
{
public:
    virtual ~Type() {}

    /**
    @brief returns the type name
    */
    virtual const std::string& typestr() const = 0;

    /**
    @brief returns the default value of this type
    @note You may not change that value!
    */
    virtual SmartValue default_value() const = 0;

    /**
    @brief returns a deep copy of the default value of this type
    */
    virtual SmartBuilder create() const = 0;

    /**
    @brief returns a value with this type
    @throw TypeError if the value is not compatible
    */
    virtual SmartValue fit(const SmartValue& value) const = 0;

protected:
    /// @internal helper for 'fit'
    template<typename VALUE>
    SmartValue generic_fit(const SmartValue& value) const
    {
        const Value* pv = value.cptr();
        if(!pv)
            return value;

        if(dynamic_cast<const VALUE*>(pv))
            return value;

        return SmartValue(new VALUE(*pv));
    }
};


typedef SmartPtr<Type> SmartType;


/**
@internal typed smart pointer for types
*/
template<class T>
class TypedSmartType : public SmartPtr<T>
{
public:
    TypedSmartType()
    {}

    TypedSmartType(T* pv)
    : SmartPtr<T>(pv)
    {}

    TypedSmartType(Type* pv)
    : SmartPtr<T>(narrow(pv))
    {}

    TypedSmartType(const SmartType& sv)
    : SmartPtr<T>(narrow(sv.ptr()))
    {}

private:
    static T* narrow(Type* pv)
    {
        return dynamic_cast<T*>(pv);
    }
};


} // /namespace
} // /namespace
} // /namespace

#endif
