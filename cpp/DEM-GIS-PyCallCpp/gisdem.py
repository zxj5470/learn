import ctypes
import platform
import matplotlib.pyplot as plt
import numpy as np

load_dll = ctypes.cdll.LoadLibrary
os = platform.system()
# lib_ext = {
#     "Darwin": "a",  # Mac OS
#     "Linux": "a",
#     "Windows": "lib"
# }
dll_ext = {
    "Darwin": "dylib",  # Mac OS
    "Linux": "so",
    "Windows": "dll"
}
dll = load_dll('./libgisdem' + '.' + dll_ext[os])
dll.source.restype = ctypes.c_char_p
dll.result.restype = ctypes.c_char_p

width = dll.getWidth()
height = dll.getHeight()

source_lines = (str(dll.source().decode('ascii')).split("\n"))
result_lines = (str(dll.result().decode('ascii')).split("\n"))


def show(lines):
    data = []
    for each_row in lines:
        each_rows_chars = each_row.split("\t")
        row_data = []
        for each in each_rows_chars:
            row_data.append(float(each))
        data += row_data

    print(data)
    a = np.array(data).reshape(width, height)
    plt.imshow(a, interpolation='nearest', cmap='hot_r', origin='upper')
    plt.colorbar(shrink=0.9)


show(source_lines)
plt.figure()
show(result_lines)
plt.show()
