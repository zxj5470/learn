# PyStr

> C++ call [pystring](https://github.com/imageworks/pystring) with OOP


## submodule
`pystring/` [imageworks/pystring](https://github.com/imageworks/pystring)
## sample
```cpp
#include "pystr.hpp"
using namespace pystr;

int main() {
	PyStr s("asdf23333");
    cout << s.capitalize()<< endl;
    cout << s.center(16)<< endl;
    cout << s.count("3")<< endl;
    cout << s.endswith("f23333")<< endl;
    cout << PyStr("23\tasdf\tasdfsadf").expandtabs(6)<< endl;
    
    cout << s.find("df")<< endl;
    cout << s.slice(2,3) << endl;
	return 0;
}
```