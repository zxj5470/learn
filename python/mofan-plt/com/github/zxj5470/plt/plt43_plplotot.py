import numpy as np
import matplotlib.pyplot as plt

fig = plt.figure()
x = [1, 2, 3, 4, 5, 6, 7]
y = [1, 3, 4, 2, 5, 8, 6]

l, b, w, h = 0.1, 0.1, 0.8, 0.8
ax1 = fig.add_axes([l, b, w, h])
ax1.plot(x, y, 'r')
ax1.set_xlabel('x')
ax1.set_ylabel('y')
ax1.set_title('title')

l, b, w, h = 0.2, 0.6, 0.25, 0.25
ax2 = fig.add_axes([l, b, w, h])
ax2.plot(y, x, 'r')
ax2.set_xlabel('x')
ax2.set_ylabel('y')
ax2.set_title('title inside 1')

plt.axes([.6, .2, .25, .25])
plt.plot(y[::-1],x,'g') # green
plt.xlabel('x')
plt.ylabel('y')
plt.title('title inside 2')

plt.show()
