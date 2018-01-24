from sklearn.model_selection import learning_curve
from sklearn.datasets import load_digits
from sklearn.svm import SVC
import matplotlib.pyplot as plt
import numpy as np

digits = load_digits()
X = digits.data
y = digits.target

train_sizes, train_loss, test_loss = learning_curve(
    SVC(gamma=0.001), X, y, cv=10, scoring='neg_mean_squared_error',
    train_sizes=[0.1, 0.25, 0.5, 0.75, 0.75, 1]
)
"""
参数
cv 交叉验证分成10组
Scoring 采用方差 :method mean_squared_error was renamed to neg_mean_squared_error
train_sizes 训练记录点
"""
# 平均值
train_loss_mean = -np.mean(train_loss, axis=1)
test_loss_mean = -np.mean(test_loss, axis=1)

plt.plot(train_sizes, train_loss_mean, 'o-', c='r', label='Training')
plt.plot(train_sizes, test_loss_mean, 'o-', c='g', label='Cross-Validation')
plt.xlabel("Training examples")
plt.ylabel("Loss")
plt.legend(loc="best")

plt.show()
