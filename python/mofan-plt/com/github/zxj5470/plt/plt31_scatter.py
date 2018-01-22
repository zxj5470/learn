import matplotlib.pyplot as plt
import numpy as np

n = 1024
# 生成随机数
X = np.random.normal(0, 1, 1024)
Y = np.random.normal(0, 1, 1024)

# 颜色(玄学代码……arctan2)
T = np.arctan2(Y, X)  # for color。arctan2用于(-pi,pi)的arctan范围

# scatter 散点图
# s size
# c color
plt.scatter(X, Y, s=75, c=T, alpha=0.5)

plt.xlim((-1.5, 1.5))
plt.ylim((-1.5, 1.5))

plt.xticks(())  # 隐藏坐标轴
plt.yticks(())  # 隐藏坐标轴
plt.show()
