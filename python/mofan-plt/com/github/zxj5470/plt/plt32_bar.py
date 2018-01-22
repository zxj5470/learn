import matplotlib.pyplot as plt
import numpy as np

n = 12
X = np.arange(n)
Y1 = (1 - X / float(n)) * np.random.uniform(0.5, 1.0, n)
Y2 = (1 - X / float(n)) * np.random.uniform(0.5, 1.0, n)

plt.bar(X, +Y1, facecolor='#9999ff', edgecolor='white')
plt.bar(X, -Y2, facecolor='#ff9999', edgecolor='white')

# zip -> X，Y1分别传入x和y
for x, y in zip(X, Y1):
    # ha :horizontal alignment 横对齐
    plt.text(x, y + 0.05, "%0.2f" % y, ha='center', va='bottom')

for x, y in zip(X, Y2):
    # ha :horizontal alignment 横对齐
    plt.text(x, -y - 0.05, r"$-%0.2f$" % y, ha='center', va='top')

plt.show()
