#include <iostream>

#include "InvokePython.h"

using namespace std;

namespace CCM_Test {
namespace Python {

PyObject *
invoke_python
  ( string module, string function, PyObject *pyArgs )
{
  char *function_name = strndup ( function.c_str (  ), 1024 );
  PyObject *pyName, *pyModule, *pyDict, *pyFunction, *pyResult = NULL;

  // this code is from the Python documentation on "Extending and Embedding
  // Python" : http://www.python.org/doc/current/ext/pure-embedding.html

  pyName = PyString_FromString ( module.c_str (  ) );
  pyModule = PyImport_Import ( pyName );
  if ( pyModule != NULL ) {
    // pyDict and pyFunction are borrowed references, don't need to decref them
    pyDict = PyModule_GetDict ( pyModule );
    pyFunction = PyDict_GetItemString ( pyDict, function_name );
    if ( pyFunction && PyCallable_Check ( pyFunction ) ) {
      pyResult = PyObject_CallObject ( pyFunction, pyArgs );
      if ( pyResult == NULL ) {
        PyErr_Print (  );
        cerr << " ** Call to python function \"" << function << "\" failed" << endl;
      }
    } else {
      PyErr_Print (  );
      cerr << " ** Cannot find \"" << function << "\" in \"" << module << "\" module" << endl;
    }
    Py_DECREF ( pyModule );
  } else {
    PyErr_Print (  );
    cerr << " ** Failed to load python module \"" << module << "\"" << endl;
  }
  Py_DECREF ( pyName );

  return pyResult;
}

} // /namespace Python
} // /namespace CCM_Test

