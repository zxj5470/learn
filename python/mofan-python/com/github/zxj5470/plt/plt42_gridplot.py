import numpy as np
import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec

# m1
plt.figure()
ax1 = plt.subplot2grid((3, 3), (0, 0), colspan=3, rowspan=1)
ax1.plot([1, 2], [1, 2])
ax1.set_title('ax1_title')  # 对应 <- plt.xlim()

ax2 = plt.subplot2grid((3, 3), (1, 0), colspan=2)
ax3 = plt.subplot2grid((3, 3), (1, 2), rowspan=2)
ax4 = plt.subplot2grid((3, 3), (2, 0), colspan=1, rowspan=1)
ax5 = plt.subplot2grid((3, 3), (2, 1), colspan=1, rowspan=1)

# m2 使用 gridSpace
# 这种方法太鬼畜了。。。还是算了
plt.figure()
gs = gridspec.GridSpec(3, 3)
ax1 = plt.subplot(gs[0, :])
ax2 = plt.subplot(gs[1, :2])
ax3 = plt.subplot(gs[1:, 2])
ax4 = plt.subplot(gs[-1, 0])
ax5 = plt.subplot(gs[-1, -2])

# m3
f, ((ax11, ax12), (ax21, ax22)) = plt.subplots(2, 2, sharex=True, sharey=False)
ax11.scatter([1, 2], [2, 2])
plt.tight_layout()  # Automatically adjust subplot
plt.show()
