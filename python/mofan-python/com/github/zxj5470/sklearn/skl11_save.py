from sklearn import svm, datasets
import pickle

clf = svm.SVC()
iris = datasets.load_iris()
X, y = iris.data, iris.target
clf.fit(X, y)

# Save
# f=open('save/clf.pickle', 'wb')
# pickle.dump(clf, f)

# Restore
with open('save/clf.pickle', 'rb') as f:
    clf2 = pickle.load(f)
    clf2: svm.classes.SVC = clf2
    print(type(clf2))
    print(clf2.predict(X[0:1]))

# joblib
from sklearn.externals import joblib

# Save
# joblib.dump(clf, 'save/clf.pkl')

# Restore
clf3 = joblib.load('save/clf.pkl')
clf3: svm.classes.SVC = clf3

print(clf3.predict(X[0:1]))
