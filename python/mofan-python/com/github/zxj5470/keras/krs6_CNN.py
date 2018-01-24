"""
分类
"""
import numpy as np
from keras.datasets import mnist
from keras.utils import np_utils
from keras.models import Sequential
from keras.layers import Dense, Activation, Convolution2D, MaxPooling2D, Flatten
from keras.optimizers import Adam

np.random.seed(1337)  # for reproducibility

Array = np.ndarray
(X_train, y_train), (X_test, y_test) = mnist.load_data()

X_train: Array = X_train
y_train: Array = y_train
X_test: Array = X_test
y_train: Array = y_train

# 特征标准化
# 长宽28
X_train = X_train.reshape(-1, 1, 28, 28) / 255  # normalize
X_test = X_test.reshape(-1, 1, 28, 28) / 255  # normalize
y_train = np_utils.to_categorical(y_train, num_classes=10)
y_test = np_utils.to_categorical(y_test, num_classes=10)

# 构建神经网络
# X shape (60,000 28x28)
model = Sequential()
model.add(Convolution2D(
    batch_input_shape=(None, 1, 28, 28),
    filters=32,
    kernel_size=5,
    strides=1,
    padding='same',  # Padding method
    data_format='channels_first')
)
model.add(Activation('relu'))

# Pooling 池化
model.add(MaxPooling2D(
    pool_size=(2, 2),
    strides=(2, 2),
    padding='same',  # padding method
))

# Convolution2D 2
model.add(Convolution2D(64, 5, strides=1, padding='same', data_format='channels_first'))
model.add(Activation('relu'))

# Pooling 2
model.add(MaxPooling2D(pool_size=(2, 2), padding='same'))

# Fully connected
model.add(Flatten())
model.add(Dense(1024))
model.add(Activation('relu'))

# Fully connected 2
model.add(Dense(10))  # 照片分类十个
model.add(Activation('softmax'))

adam = Adam(lr=1e-4)
model.compile(optimizer=adam,
              loss='categorical_crossentropy',
              metrics=['accuracy'])
# train
print("Traning…………")
model.fit(X_train, y_train, epochs=2, batch_size=32)

# test
print("Testing…………")
loss, accuracy = model.evaluate(X_test, y_test)

print("test loss: ", loss)
print("test accuracy: ", accuracy)
