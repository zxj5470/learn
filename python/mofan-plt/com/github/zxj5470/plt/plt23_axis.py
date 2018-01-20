#!../../../../venv/bin/python
import matplotlib.pyplot as plt
import numpy as np

x = np.linspace(-3, 3, 50)
y1 = 2 * x + 1
y2 = x ** 2
plt.figure()
# 画在同一个figure中

plt.xlim((-1, 2))
plt.xlabel("I am x")

plt.ylim((-2, 3))
plt.ylabel("I am y")

new_ticks = np.linspace(-1, 2, 5)
plt.xticks(new_ticks)
plt.yticks([-2, 1.22], ['$That\'s\ good$', r'$\alpha$'])

# gca = 'get current axis'
# 设置坐标轴位置
ax = plt.gca()
ax.spines['right'].set_color('none')
ax.spines['top'].set_color('none')
ax.xaxis.set_ticks_position('bottom')
ax.yaxis.set_ticks_position('left')
ax.spines['bottom'].set_position(('data', -1))
ax.spines['left'].set_position(('data', 0))

# 图像返回值
line1, = plt.plot(x, y1, label='down', color='red', linewidth=1.0, linestyle='--')  # 虚线为`--`
line2, = plt.plot(x, y2, label='up')

# 图例部分
plt.legend(handles=[line1, line2], labels=['aaa', 'bbb'], loc='best')

plt.show()
