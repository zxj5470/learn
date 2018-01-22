import numpy as np

arr = np.array([[1, 23, 4]], dtype=np.int32)

print(arr)
print('number of dim: ', arr.ndim)
print('shape: ', arr.shape)  # (1,3) 一行三列

print(arr.dtype)

# 生成 3x4 的 0 矩阵
zrs = np.zeros((3, 4), dtype=np.int32)
a = np.arange(12)
print(zrs)
print(a.reshape((3, 4)))

# line space.生成五个点。四个间隔。
a = np.linspace(1, 10, 6).reshape(2, 3)
print(a)
