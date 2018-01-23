import numpy as np

A = np.arange(14, 2, -1).reshape((3, 4))
# 整合到一个里面

# 最小值索引/最大值索引
print(np.argmin(A))
print(np.argmax(A))

# 平均值
# 平均值
# axis=0 对于列进行 .axis=1 行计算。
print("平均值")
print(np.mean(A, axis=1))
print(np.average(A))

# 中位数
print(np.median(A))

# 累加值
# 类似于斐波那契，逐个增加的是以源array为准
print(np.cumsum(A))

# 累差
print(np.diff(A))

# 输出两个数组。第一个数组为行的集合，第二个数组为列的集合
print(np.nonzero(A))

# 排序。默认逐行排序
print(np.sort(A))

# 矩阵反向
# 矩阵转置 transpose 和 直接 .T
# print(np.transpose(A))
print(A.T.dot(A))

# clip
# 小于5的数变成5，大于9的数变成9
print(np.clip(A, 5, 9))
