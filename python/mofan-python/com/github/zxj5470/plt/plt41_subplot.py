import numpy as np
import matplotlib.pyplot as plt

# 四分图

plt.subplot(2,1,1)
plt.plot([0, 1], [0, 1])

# 因为第一行一个图占了三个位置。。所以第二行的开始从4开始
plt.subplot(2,3,4)
plt.plot([0, 1], [0,2])

plt.subplot(235)
plt.plot([0, 1], [0,3])

plt.subplot(236)
plt.plot([0, 1], [0,4])

plt.show()
