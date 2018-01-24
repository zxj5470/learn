"""
normalization: scale
"""
from sklearn import preprocessing
import numpy as np

a = np.array([[10,2.7,3.6],
              [-100,5,-2],
              [120,20,40]],dtype=np.float64)

print(a)
print(preprocessing.scale(a))