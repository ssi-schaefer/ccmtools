/*$Id$*/

/******************************************\
*                                          *
*   helper functions for OCL expressions   *
*                                          *
\******************************************/


#include "OclHelpers.h"
#include <cctype>

using namespace std;


namespace CCM_OCL {


/**
 * Converts a string to uppercase letters.
 */
OCL_String OCL_toUpper( const string& x )
{
    int n = x.length();
    char* buffer = new char[n];
    x.copy(buffer,n);
    for( int i=0; i<n; i++ )
    {
        buffer[i] = toupper(buffer[i]);
    }
    OCL_String result(buffer,n);
    delete[] buffer;
    return result;
}


/**
 * Converts a string to lowercase letters.
 */
OCL_String OCL_toLower( const string& x )
{
    int n = x.length();
    char* buffer = new char[n];
    x.copy(buffer,n);
    for( int i=0; i<n; i++ )
    {
        buffer[i] = tolower(buffer[i]);
    }
    OCL_String result(buffer,n);
    delete[] buffer;
    return result;
}


/**
 * Returns the number of times that 'n' fits completely within 'z'.
 */
OCL_Integer OCL_div( OCL_Integer z, OCL_Integer n )
{
    if( z<0 )
    {
        return -OCL_div(-z,n);
    }
    if( n<0 )
    {
        return -OCL_div(z,-n);
    }
    return z/n;
}


/**
 * Returns 'z' modulo 'n'.
 */
OCL_Integer OCL_mod( OCL_Integer z, OCL_Integer n )
{
    return z - OCL_div(z,n)*n;
}


/**
 * Compares two real values.
 */
OCL_Boolean OCL_equals( OCL_Real a, OCL_Real b )
{
    if( a==b )
    {
        return true;
    }
    double fa = fabs(a);
    double fb = fabs(b);
    double m = fa>=fb ? fa : fb;
    return fabs(a-b) <= m*(1E-10);  // 9 significant digits
}


} // /namespace CCM_OCL


