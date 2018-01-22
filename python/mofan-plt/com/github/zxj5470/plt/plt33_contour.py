import matplotlib.pyplot as plt
import numpy as np


# 等高线
def f(x, y):
    #
    return (1 - x / 2 + x ** 5 + y ** 3) * np.exp(-x ** 2 - y ** 2)


n = 256
x = np.linspace(-3, 3, n)
y = np.linspace(-3, 3, n)

X, Y = np.meshgrid(x, y)

contour_line_number = 10
# contour fill
# cmap :color map ->
plt.contourf(X, Y, f(X, Y), contour_line_number, alpha=0.75, cmap=plt.cm.hot)

C = plt.contour(X, Y, f(X, Y), contour_line_number, color='black', linewidth=0.5)

# 等高线 数字标注
plt.clabel(C, inline=True)

plt.xticks(())
plt.yticks(())
plt.show()
