#include <iostream>
#include <python2.7/Python.h>

using namespace std;

int main() {
    Py_Initialize();
    PyRun_SimpleString("import sys");
    if (PyRun_SimpleString("execfile('../a.py')") == NULL) {
        return -1;
    }
    Py_Finalize();
    return 0;
}