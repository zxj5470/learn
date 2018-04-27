# Delaunay 三角网

## 相关说明

运行效果
![](image/gif01.gif)

效果图
![](image/pic01.png)
## 运行环境

- Java 1.6 及以上 (已测试过的运行环境包括 Java 1.6, 1.7, 1.8, 10)
- kotlin-stdlib.jar 

命令
双击 `run.bat` 打开即可。
```bash
java -jar delaunay.jar
```

## 数据结构

- ArraySet（用于三角形，确保三角形三个点不相同）
   - 添加时进行查重，若重复则不添加。`(Set)`
   - 可按照下标获取。`(Array)`
   - 若使用Java内置的HashSet实际上仍是使用HashMap。无法做到下标取值。
- Graph


## Kotlin

### 语言特性

- 运算符重载(用于各种计算等)
- 函数扩展(方便调用)
- 高阶函数(可迭代/集合类型的map,filter,forEach)。简化代码过程。

## 中英对照表

| 英文 | 中文 | 备注 |
|:---|:---:|:---|
|	bisector	|	平分 	|		|
|	circumscribedCenter	|	外接圆圆心	|		|
|	cross	|	叉乘	|		|
|	determinant	|	行列式	|	计算行列式的值	|
|	dimension	|	维数 	| 向量的size或行列式的长宽 |
|	distanceEuclidean	|	欧氏距离		|   我们习惯的距离公式 |
|	minus	|	减法	|	`Pnt`之间的 `-`	|
|	perpendicular bisector	|	垂直平分线	|		|
|	plus	|	加法	|	`Pnt`之间的 `+`	|
|	relation	|	是	|		|
