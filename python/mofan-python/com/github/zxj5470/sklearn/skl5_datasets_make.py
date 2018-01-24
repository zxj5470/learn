"""
数据集
"""
from sklearn import datasets
import matplotlib.pyplot as plt

# 创造数据
X, y = datasets.make_regression(n_samples=100, n_features=1, n_targets=1, noise=233)
plt.scatter(X, y)
plt.show()
