#!../../../../venv/bin/python
import matplotlib.pyplot as plt
import numpy as np

x = np.linspace(-3, 3, 50)
y = 2 * x + 1

plt.figure(num=1, figsize=(8, 5))
plt.plot()

# 设置坐标中心
ax = plt.gca()
ax.spines['right'].set_color('none')
ax.spines['top'].set_color('none')

ax.xaxis.set_ticks_position('bottom')
ax.yaxis.set_ticks_position('left')

ax.spines['bottom'].set_position(('data', 0))
ax.spines['left'].set_position(('data', 0))

x0 = 1
y0 = 0.1 * x0 + 1

# scatter 展示单个点
plt.plot(x, y, linewidth=10,zorder=1)
plt.scatter(x0, y0, s=50, color='g')
plt.plot([x0, x0], [y0, 0], 'k--', lw=2.5)  # k 为黑色

# 添加文字标注
# 名字，x,y
plt.annotate(r'$0.1x+1=%s$' % y0,
             xy=(x0, y0), xycoords='data', xytext=(+30, -10),
             textcoords='offset points',
             arrowprops=dict(arrowstyle='->', connectionstyle='arc3,rad=.2'))

# x , y , 内容
plt.text(-3.7, 3, r'$This\ is\ the\ some\ text\ \mu\ \sigma_i \ \alpha_t $',
         fontdict={'size': 16, 'color': 'r'})

for label in ax.get_xticklabels() + ax.get_yticklabels():
    label.set_fontsize(10)
    label.set_bbox(dict(facecolor='white', edgecolor='None', alpha=0.7))

plt.show()
