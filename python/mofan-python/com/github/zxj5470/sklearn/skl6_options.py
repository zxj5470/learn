"""
数据集
"""
from sklearn import datasets
from sklearn.linear_model import LinearRegression

load_data = datasets.load_boston()
data_X = load_data.data
data_y = load_data.target

model = LinearRegression()
model.fit(data_X, data_y)

# 每个参数
print(model.coef_)
# 与y轴交点
print(model.intercept_)

# 取出参数
# {'copy_X': True,
# 'fit_intercept': True,
# 'n_jobs': 1,      几个核
# 'normalize': False}
print(model.get_params())

# 对模型进行打分
print(model.score(data_X, data_y))  # R^2 coefficient of determination
