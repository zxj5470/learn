
#include "gisdem.h"

static auto seed = time(NULL);

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

void GisDEM::calculate() {
	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w; ++j) {
//			calculateEach(i, j);
			calculateRefactor(i, j);
		}
	}
}

const char *GisDEM::sourceToFile() {
	str.clear();
	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w; ++j) {
			str += to_string(data[i][j]);
			if (j != w - 1)str += ",";
		}
		if (i != h - 1)str += "\n";
	}
	ofstream fout("./input.csv");
	fout << str;
	fout.close();
	return str.c_str();
}

void GisDEM::resultOutputFile() {
	calculate();
	ofstream fout("./data.csv");
	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w; ++j) {
			fout << double(output[i][j]);
			if (j != w - 1)fout << ",";
		}
		if (i != h - 1)fout << "\n";
	}
	fout.close();
}


/**
 * 以i，j为中心将数据分成两种：对角线数据和非对角线数据。
 * 默认已将 output[i][j] = data[i][j]  只用考虑周围数据的影响。
 * @param i
 * @param j
 * @param c 最远的可影响到的横坐标距离
 * @return
 */
void GisDEM::calculateEach(int i, int j) {
	for (int n = 1; n <= r; n++) {
		// bottom
		if (i + n < h) {
			//考虑正下方的非对角线数据
			inc(i, j, n, 0);//正下方
			for (int k = 1; k < n; k++) { // n > 1 时才会执行
				if (j + k < w) {
					inc(i, j, n, k);
				}
				if (j - k >= 0) {
					inc(i, j, n, -k);
				}
			}
		}

		// top
		if (i - n >= 0) {
			//考虑正上方的非对角线数据
			inc(i, j, -n, 0);
			for (int k = 1; k < n; k++) { // n > 1 时才会执行
				if (j + k < w) {
					inc(i, j, -n, k);
				}
				if (j - k >= 0) {
					inc(i, j, -n, -k);
				}
			}
		}

		//考虑正左方的非对角线数据
		if (j - n >= 0) { // left
			inc(i, j, 0, -n);
			for (int k = 1; k < n; k++) { // n > 1 时才会执行
				if (i + k < h) {
					inc(i, j, k, -n);
				}
				if (i - k >= 0) {
					inc(i, j, -k, -n);
				}
			}
		}

		//考虑正右方的非对角线数据
		if (j + n < w) { //right
			inc(i, j, 0, n);
			for (int k = 1; k < n; k++) { // n > 1 时才会执行
				if (i + k < h) {
					inc(i, j, k, n);
				}
				if (i - k >= 0) {
					inc(i, j, -k, n);
				}
			}
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

/**
 * 计算权值
 * @param dx
 * @param dy
 * @return
 */
inline double GisDEM::v(int dx, int dy) {
	return 0.5 * 1.0 / sqrt(dx * dx + dy * dy);
}

/**
 *
 * @param i
 * @param j
 * @param di 与i的坐标差。有正有负。
 * @param dj 与j的坐标差。有正有负。
 */
inline void GisDEM::inc(int i, int j, int di, int dj) {
	output[i][j] += data[i + di][j + dj] * v(di, dj);
}

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

/**
 * j
 * @param i
 * @param j
 * @param n 大于零表示取右侧的值，小于0表示取左侧的值。
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
 *
 * @param i
 * @param j
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

void GisDEM::set(int w, int h, int p, int r) {
	this->w = w;
	this->h = h;
	this->p = p;
	this->r = r;
}

GisDEM::GisDEM() {
	w = 50;
	h = 50;
	p = 5;
	r = 3;
}

int GisDEM::gisRandom(int zeroTo) {
	srand((unsigned)seed);
	seed = rand();
	return rand() % zeroTo;
}

/**
 * DLL
 */

void run() {
	gis.init();
	gis.sourceToFile();
	gis.resultOutputFile();
}

void setParam(int w, int h, int p, int r) {
	gis.set(w, h, p, r);
}