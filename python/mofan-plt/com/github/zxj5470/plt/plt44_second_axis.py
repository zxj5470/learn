import numpy as np
import matplotlib.pyplot as plt

# 两个坐标轴

x = np.arange(0, 10, 0.1)
y1 = 0.05 * x ** 2
y2 = 4 * x + 0.4
fig, ax1 = plt.subplots()

# 借助镜面
ax2 = ax1.twinx()
ax1.plot(x, y1, 'g-')
ax2.plot(x, y2, 'b--')

ax1.set_xlabel('X')
ax1.set_ylabel('Y1',color='g')
ax2.set_ylabel('Y2',color='b')
plt.show()
