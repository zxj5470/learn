import re
import numpy as np
import matplotlib.pyplot as plt
import itertools


class Elem:
    def __init__(self, source: str, lineNumber: int):
        _s = source.split(',')
        self.begin = int(_s[0]) - 1
        self.end = int(_s[1])
        self.len = self.end - self.begin
        self.value = int(_s[2])
        self.lineNumber = lineNumber

    def __str__(self):
        return 'Line:{3},begin:{0},end:{1},value:{2}'.format(self.begin, self.end, self.value, self.lineNumber + 1)


f = open('data.txt')
rowData = []
lines = f.readlines()
rows = 0
cols = 0
for index, each in enumerate(lines):
    if index == 0:
        s = each.split(',')
        rows = int(s[0])
        cols = int(s[1])
        continue
    s = re.split(r'[()]', each)
    s = list(filter(lambda x: len(x) > 1, s))
    listOfData = list(map(lambda x: Elem(x, index), s))

    for el in listOfData:
        rowData.append([el.value] * el.len)
        # 矢量数据栅格化
# 将全部数据合并 再reshape
data = list(itertools.chain.from_iterable(rowData))
data = np.array(data).reshape(rows, cols)
plt.imshow(data)
print(data)
# 右侧条为图像的 90%
plt.colorbar(shrink=0.5)
plt.plot(linestyle="-.", color="gray", linewidth="0.5")
ax = plt.gca()
plt.grid()
# ax.set_xticks(np.arange(-.5, cols, 1))
# ax.set_yticks(np.arange(-.5, rows, 1))
ax.set_xticklabels(np.arange(0, cols, 1))
ax.set_yticklabels(np.arange(0, rows, 1))
ax.spines['bottom'].set_position(('data', -0.5))
plt.show()
