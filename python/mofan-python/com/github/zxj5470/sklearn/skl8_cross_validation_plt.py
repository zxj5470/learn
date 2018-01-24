import numpy as np
from sklearn import datasets
# from sklearn.cross_validation import train_test_split,cross_val_predict
# which is deprecated
from sklearn.model_selection import cross_val_score
from sklearn.neighbors import KNeighborsClassifier
import matplotlib.pyplot as plt

iris = datasets.load_iris()
iris_X = iris.data
iris_y = iris.target

# 选择区间
k_range = range(1, 35)
k_scores = []
for k in k_range:
    knn = KNeighborsClassifier(n_neighbors=k)
    # loss 看误差
    loss = -cross_val_score(knn, iris_X, iris_y, cv=10, scoring='mean_squared_error')  # for regression
    k_scores.append(loss.mean())

    # scores = cross_val_score(knn, iris_X, iris_y, cv=10, scoring='accuracy')  # for classification
    # k_scores.append(scores.mean())

plt.plot(k_range, k_scores)
plt.xlabel("Value of K for KNN")
plt.ylabel("Accuracy")
plt.show()

# 结果发现数字过大会过拟合
