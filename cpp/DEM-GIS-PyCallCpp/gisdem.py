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
    "Windows": "dll"  # windows 64bit python cannot call a 32bit dll ……
}
dll = load_dll('./libgisdem' + '.' + dll_ext[os])
dll.setParam(20, 20, 6, 4)
dll.run()

source_lines = open('input.csv').readlines()
result_lines = open('output.csv').readlines()


def idw(lines):
    data = []
    for each_row in lines:
        each_rows_chars = each_row.split(",")
        row_data = []
        for each in each_rows_chars:
            row_data.append(float(each))
        data.append(row_data)

    # print(data)
    height = data.__len__()
    width = data[0].__len__()
    a = np.array(data).reshape(width, height)
    plt.imshow(a, interpolation='nearest', cmap='hot_r', origin='upper')
    plt.colorbar(shrink=0.9)


idw(source_lines)
plt.figure()
idw(result_lines)
plt.show()
