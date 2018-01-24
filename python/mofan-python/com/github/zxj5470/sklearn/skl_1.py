import numpy as np
from sklearn import datasets
# from sklearn.cross_validation import train_test_split
# which is deprecated
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier


def correct_percentage(predict: np.ndarray, real: np.ndarray):
    _count = 0
    for i, value in enumerate(predict):
        if value == real[i]:
            _count += 1
    return str(_count / predict.size * 100.) + "%"


iris = datasets.load_iris()
iris_X = iris.data
iris_y = iris.target

# 属性 两个样本。四种属性
# print(iris_X[:2, :])

# 分类。0 1 2 三种
# print(iris_y)

# 设置训练集和测试集（切割）
# train_test_split 并且会将数据进行随机分开
X_train, X_test, Y_train, Y_test = train_test_split(iris_X, iris_y, test_size=0.3)

# print(Y_train)

# 使用KNN进行预测
knn = KNeighborsClassifier()

# 放入数据并训练
knn.fit(X_train, Y_train)

# 预测
predict_x = knn.predict(X_test)
print(predict_x)
# 原始数据对照（上下比对）
print(Y_test)
print(correct_percentage(predict_x, Y_test))
'''
结果随机……但是总会有不同
[0 0 2 2 0 0 2 1 0 1 2 0 1 1 2 1 2 2 0 1 0 0 2 1 1 1 1 0 1 1 0 1 2 2 2 1 2
 0 2 0 1 1 2 1 1]
 
[0 0 2 2 0 0 2 1 0 1 2 0 1 1 2 1 2 2 0 1 0 0 2 1 1 2 1 0 1 1 0 1 2 2 2 1 2
 0 1 0 1 1 2 1 1]
 
97.77777777777777%
'''
