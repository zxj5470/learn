import numpy as np
from sklearn import datasets
# from sklearn.cross_validation import train_test_split,cross_val_score
# which is deprecated
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.neighbors import KNeighborsClassifier

iris = datasets.load_iris()
iris_X = iris.data
iris_y = iris.target

# 未使用交叉验证

X_train, X_test, y_train, y_test = train_test_split(iris_X, iris_y, random_state=4)
# 数据点附近的五个neighbor的平均值
knn = KNeighborsClassifier(n_neighbors=5)
knn.fit(X_train, y_train)
# y_pred = knn.predict(X_test)
print(knn.score(X_test, y_test))

# 使用交叉验证
knn = KNeighborsClassifier(n_neighbors=5)
scores = cross_val_score(knn, iris_X, iris_y, cv=5, scoring='accuracy')
print(scores)
scores: np.ndarray = scores
# 平均值
print(scores.mean())
