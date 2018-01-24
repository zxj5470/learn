"""
回归
"""
import numpy as np
from keras.models import Sequential
from keras.layers import Dense
import matplotlib.pyplot as plt

np.random.seed(1337)  # for reproducibility
num = 500
X = np.linspace(-1, 1, num)
np.random.shuffle(X)  # random data
Y = 0.5 * X + 2 + np.random.normal(0, 0.05, (num,))

# plt.scatter(X, Y)
# plt.show()
per = int(0.8 * num)
X_train, Y_train = X[:per], Y[:per]  # 前160个用于训练
X_test, Y_test = X[per:], Y[per:]  # 后面的用于测试

# 建造神经网络模型
model = Sequential()
model.add(Dense(units=1, input_dim=1))  # 添加一个Dense层
model.add(Dense(units=1))  # 再次添加时默认输入为上一层的输出
model.add(Dense(units=1))  # 再次添加时默认输入为上一层的输出
model.add(Dense(units=1))  # 再次添加时默认输入为上一层的输出
# model.add(Dense(output_dim=1))  # 再次添加时默认输入为上一层的输出

# 损失函数
model.compile(loss='mse', optimizer='sgd')  # optimizer 乱序

# 训练
print("Training…………")
for step in range(301):
    # 返回cost，每100步输出cost
    cost = model.train_on_batch(X_train, Y_train)
    if step % 100 == 0:
        print("train cost:", cost)

# 测试
print("Testing…………")
# 评价，基于test
cost = model.evaluate(X_test, Y_test, batch_size=40)
print("test cost:", cost)

dense: Dense = model.layers[0]
W, b = dense.get_weights()
print("Weight= ", W, "\n biases= ", b)

# plot
Y_pred = model.predict(X_test)
plt.scatter(X_test, Y_test)
plt.plot(X_test, Y_pred)
plt.show()
