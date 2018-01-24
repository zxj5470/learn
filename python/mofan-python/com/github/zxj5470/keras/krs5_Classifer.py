"""
分类
"""
import numpy as np
from keras.models import Sequential
from keras.utils import np_utils
from keras.layers import Dense, Activation
from keras.datasets import mnist
from keras.optimizers import RMSprop

Array = np.ndarray
(X_train, y_train), (X_test, y_test) = mnist.load_data()

X_train: Array = X_train
y_train: Array = y_train
X_test: Array = X_test
y_train: Array = y_train

# 特征标准化
X_train = X_train.reshape(X_train.shape[0], -1) / 255  # normalize
X_test = X_test.reshape(X_test.shape[0], -1) / 255  # normalize
y_train = np_utils.to_categorical(y_train, num_classes=10)
y_test = np_utils.to_categorical(y_test, num_classes=10)

# 构建神经网络
# X shape (60,000 28x28)
model = Sequential([
    Dense(32, input_dim=784),
    Activation('relu'),  # relu 作为激活函数
    Dense(10),  # input 32
    Activation('softmax')
])

# 定义 optimizer
rmsprop = RMSprop(lr=0.001, rho=0.9, epsilon=1e-08, decay=0.0)

# 添加 metrics
model.compile(
    optimizer=rmsprop,
    # optimizer='rmsprop',
    loss='categorical_crossentropy',
    metrics=['accuracy']
)

# train
print("Traning…………")
model.fit(X_train, y_train, epochs=2, batch_size=32)

# test
print("Testing…………")
loss, accuracy = model.evaluate(X_test, y_test)

print("test loss: ", loss)
print("test accuracy: ", accuracy)
