#!../../../../venv/bin/python
import matplotlib.pyplot as plt
import numpy as np

x = np.linspace(-3, 3, 50)
y1 = 2 * x + 1
y2 = x ** 2
plt.figure()

plt.plot(x, y2)
plt.plot(x, y1, color='red', linewidth=1.0, linestyle='--')  # 虚线为`--`

plt.xlim((-1,2))
plt.xlabel("I am x")

plt.ylim((-2,3))
plt.ylabel("I am y")

new_ticks=np.linspace(-1,2,5)
plt.xticks(new_ticks)
plt.yticks([-2,1.22],['$That\'s\ good$',r'$\alpha$'])

plt.show()
