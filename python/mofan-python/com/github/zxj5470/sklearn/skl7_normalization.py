"""
normalization: scale
"""
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from sklearn.datasets.samples_generator import make_classification
from sklearn.svm import SVC
import numpy as np
import matplotlib.pyplot as plt

"""
@:param n_samples: 样本数
@:param n_features: 属性
@:param n_redundant: 样本数
@:param n_redundant: 样本数
@:param random_state: 随机产生，但每次产生的相同
@:param n_clusters_per_class: 随机产生，但每次产生的相同
@:param scale: scale参数
"""
X, y = make_classification(n_samples=300, n_features=2,
                           n_redundant=0, n_informative=2,
                           random_state=22, n_clusters_per_class=1,
                           scale=100)

# plt.scatter(X[:, 0], X[:, 1], c=y)
# plt.show()

X = preprocessing.scale(X)
# feature_range 压缩到的范围 默认 0,1
# X = preprocessing.minmax_scale(X, feature_range=(-1, 1))

# 分割。30%测试，70%训练
train_X, test_X, train_y, test_y = train_test_split(X, y, test_size=0.3)

clf = SVC()
clf.fit(train_X, train_y)

print(clf.score(test_X, test_y))
# 未使用scale 0.5
# 使用后 0.9以上
