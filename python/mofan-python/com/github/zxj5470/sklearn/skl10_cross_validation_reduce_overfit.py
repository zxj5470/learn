from sklearn.model_selection import validation_curve
from sklearn.datasets import load_digits
from sklearn.svm import SVC
import matplotlib.pyplot as plt
import numpy as np

digits = load_digits()
X = digits.data
y = digits.target

# 范围,
param_range = np.logspace(-6, -2.3, 5)
train_loss, test_loss = validation_curve(
    SVC(), X, y,
    param_name='gamma', param_range=param_range,
    cv=10, scoring='neg_mean_squared_error'
)

"""
参数
cv 交叉验证分成10组
Scoring 采用方差 :method mean_squared_error was renamed to neg_mean_squared_error
"""
# 平均值
train_loss_mean = -np.mean(train_loss, axis=1)
test_loss_mean = -np.mean(test_loss, axis=1)

plt.plot(param_range, train_loss_mean, 'o-', c='r', label='Training')
plt.plot(param_range, test_loss_mean, 'o-', c='g', label='Cross-Validation')
plt.xlabel("Training examples")
plt.ylabel("Loss")
plt.legend(loc="best")

plt.show()