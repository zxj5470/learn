
<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->
<!-- code_chunk_output -->

* [IDW](#idw)
	* [运行环境](#运行环境)
	* [运行](#运行)
	* [效果图](#效果图)
	* [调用说明：](#调用说明)
	* [C++ 代码说明](#c-代码说明)
		* [使用vector，随机生成数据。gisRandom函数生成随机数(保证不伪随机)](#使用vector随机生成数据-gisrandom函数生成随机数保证不伪随机)
		* [计算](#计算)
		* [计算每个单元格](#计算每个单元格)
		* [计算四方向](#计算四方向)
		* [插值](#插值)
		* [IDW](#idw-1)

<!-- /code_chunk_output -->


# IDW

## 运行环境
- C++ (64位dll)
- Python 3.4及以上 (64位) + numpy + matplotlib

## 运行
```
python gisdem.py
# python3 gisdem.py
```

## 效果图
结果为随机生成的

![](./01.png)

将`gisdem.py` 中的 dll.setParam 参数修改为30,30,5,5 得到结果如下：
![](./02.png)

## 调用说明：

在python中调用动态链接库的 setParam 函数 和 run 函数。相当于运行C++中的生成数据和进行计算。
用于展示数据（类似于热力图形式）

`setParam函数签名setParam(int w, int h, int p, int r)`

- `w` 数据宽度。对应数据文件每一行的数据量
- `h` 对应数据文件的数据行数。
- `p` 随机生成的点密度。相当于每p个点随机生成一个数值
- `r` 影响搜索半径


```python
import ctypes
load_dll = ctypes.cdll.LoadLibrary

dll = load_dll('./libgisdem.dll')
dll.setParam(50, 50, 6, 4)
dll.run()
```
随后使用matplotlib进行可视化结果。

## C++ 代码说明
`dll`中内建`GisDEM`类型的对象`gis`。

```cpp
void run() {
	gis.init();
	//将随机原始数据输出到input.csv
	gis.sourceToFile();
	//将计算结果输出到output.csv
	//在resultOutputFile中调用calculate函数
	gis.resultOutputFile();
}
```


### 使用vector，随机生成数据。gisRandom函数生成随机数(保证不伪随机)
```cpp
void GisDEM::init() {
	for (int i = 0; i < h; i++) {
		vector<double> rowData;
		vector<double> rowOutput;
		for (int j = 0; j < w; ++j) {
			rowData.push_back(0.0);
			rowOutput.push_back(0.0);
		}
		data.push_back(rowData);
		output.push_back(rowOutput);
	}

	//random generation
	for (int i = 0; i < h * w / (p * p); i++) {
		int a = gisRandom(h);
		auto b = gisRandom(w);
		auto h = 30 + gisRandom(100);
		data[a][b] = h;
		output[a][b] = h;
	}
}
```

### 计算
```cpp
void GisDEM::calculate() {
	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w; ++j) {
			calculateRefactor(i, j);
		}
	}
}
```

### 计算每个单元格
`r` 在类中定义的搜索半径，在setParam中设置。默认为3。
搜索时范围的 `曼哈顿距离`最大为 2r。(即`[i+r][j+r]`)。

搜索上下代码和搜索左右代码基本相似可以进行整合
n为迭代变量。不断增加外圈来计算权值。

- 先考虑上下左右四个方向。（分情况）
	- `r == 1` 只考虑正方向（即九宫格只需要考虑2468位置）
	- `r >= 2` 时除了考虑正方向还有考虑`非对角线非正方向`的数据。
		- 以一个正方向的点为起点开始 左右或上下获取点 （例如 正上方 的点则向左右 各展开 `r-1` 个单位 ）

- 再考虑对角线方向（较为简单）

```cpp
void GisDEM::calculateRefactor(int i, int j) {
	for (int n = 1; n <= r; n++) {
		//考虑正下方的非对角线数据
		if (i + n < h) {
			calcTopAndBottom(i, j, n);
		}
		//考虑正上方的非对角线数据
		if (i - n >= 0) {
			calcTopAndBottom(i, j, -n);
		}

		//考虑正左方的非对角线数据
		if (j - n >= 0) { // left
			calcLeftAndRight(i, j, -n);
		}

		//考虑正右方的非对角线数据
		if (j + n < w) { //right
			calcLeftAndRight(i, j, n);
		}

		//左下右下
		if (i + n < h && j - n >= 0) {
			inc(i, j, n, -n);
		}
		if (i + n < h && j + n < w) {
			inc(i, j, n, n);
		}

		//左上右上
		if (i - n >= 0 && j - n >= 0) {
			inc(i, j, -n, -n);
		}
		if (i - n >= 0 && j + n < w) {
			inc(i, j, -n, n);
		}
	}
}
```

### 计算四方向
每个函数调用两次
```cpp
/**
 * @param n 大于零表示取右侧的值，小于0表示取左侧的值。
 */
void GisDEM::calcLeftAndRight(int i, int j, int n) {
	inc(i, j, 0, n);
	for (int k = 1; k < abs(n); k++) { // n > 1 时才会执行
		if (i + k < h) {
			inc(i, j, k, n);
		}
		if (i - k >= 0) {
			inc(i, j, -k, n);
		}
	}
}
/**
 * @param n 大于零表示取下侧的值，小于零表示取左侧的值 
 */
void GisDEM::calcTopAndBottom(int i, int j, int n) {
	inc(i, j, n, 0);
	for (int k = 1; k < abs(n); k++) { // n > 1 时才会执行
		if (j + k < w) {
			inc(i, j, n, k);
		}
		if (j - k >= 0) {
			inc(i, j, n, -k);
		}
	}
}
```

### 插值
(即周围点对[i,j]点的"贡献"/"相关程度")
```cpp
inline void GisDEM::inc(int i, int j, int di, int dj) {
	output[i][j] += data[i + di][j + dj] * v(di, dj);
}
```

### IDW
```cpp
double GisDEM::v(int dx, int dy) {
	return 0.5 * 1.0 / sqrt(dx * dx + dy * dy);
}
```