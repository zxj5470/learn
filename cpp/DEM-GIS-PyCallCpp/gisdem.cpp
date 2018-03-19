#include "gisdem.h"

inline int random(int fromZeroTo) {
	return (int) random() % fromZeroTo;
}

void GisDEM::init() {
	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w; ++j) {
			data[i][j] = 0;
			output[i][j] = data[i][j];
		}
	}

	//random generation
	for (int i = 0; i < h * w / 3; i++) {
		int a = random(h);
		auto b = random(w);
		auto h = 30 + random(100);
		data[a][b] = h;
		output[a][b] = h;
	}

}

void GisDEM::calculate() {
	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w; ++j) {
			calculateEach(i, j);
		}
	}
}

const char *GisDEM::sourceToString() {
	str.clear();
	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w - 1; ++j) {
			str += to_string(data[i][j]);
			str += "\t";
		}
		str += to_string(data[i][w - i]);
		if (i != h - 1)str += "\n";
	}
	return str.c_str();
}

const char *GisDEM::resultToString() {
	calculate();
	str.clear();
	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w - 1; ++j) {
			str += to_string(output[i][j]);
			str += "\t";
		}
		str += to_string(output[i][w - i]);
		if (i != h - 1)str += "\n";
	}
	return str.c_str();
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
	int c = 1;
	for (int n = 1; n <= c; n++) {
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

		if (i - n >= 0) { // top
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

inline double GisDEM::v(int dx, int dy) {
	return 1.0 / sqrt(dx * dx + dy * dy);
}

inline void GisDEM::inc(int i, int j, int di, int dj) {
//	cout << "(" << i << "," << j << "): [" << i + di << "," << j + dj << "]\t" << data[i + di][j + dj] * v(di, dj) << "\tdata = ";
	output[i][j] += data[i + di][j + dj] * v(di, dj);
	cout << "(" << i << "," << j << ")\t" << output[i][j] << endl;
}

void GisDEM::println() {
	cout << output[4][5] << endl;
}

/**
 * DLL
 */

const char *source() {
	gis.init();
	return gis.sourceToString();
}

const char *result() {
	gis.init();
	return gis.resultToString();
}

int getWidth() { return w; }

int getHeight() { return h; }
