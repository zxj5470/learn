import numpy as np

a = np.array([10, 20, 30, 40])
b = np.arange(4)

print(a)
print(b)

c = a - b
print(c)

c = a + b
print(c)

c = b ** 2
print(c)

c = 10 * np.sin(a * np.pi / 180.)
print(c)

# for each if `it` < 3
print(b < 3)

a = np.array([[1, 1],
              [0, 1]])
b = np.arange(4).reshape(2, 2)
print(a)
print(b)
# 矩阵乘法
# np.dot(a, b)   # 或是：
c = a.dot(b)
print(c)
# 普通乘法。对应位置相乘
print(a * b)

# 传入的是shape
a = np.random.random((2, 4))
print(a)
print(a.sum(axis=1))  # 第一行的求和

