#ifndef DEM_GIS_PYCALLCPP_LIBRARY_H
#define DEM_GIS_PYCALLCPP_LIBRARY_H

#include <iostream>
#include <cmath>
#include <vector>
#include <fstream>
#include <random>
#include <chrono>

using namespace std;

class GisDEM {
public:
	GisDEM();

	void init();

	void set(int w, int h, int p, int r);

	const char *sourceToFile();

	void resultOutputFile();

private:
	vector<vector<double>> data;
	vector<vector<double>> output;
	int w, h, p, r;

	int gisRandom(int zeroTo);

	double v(int dx, int dy);

	void inc(int i, int j, int di, int dj);

	void calculateEach(int i, int j);

	void calculateRefactor(int i, int j);

	void calculate();

	string str;

	void calcTopAndBottom(int i, int j, int n);

	void calcLeftAndRight(int i, int j, int n);

};

static GisDEM gis;

extern "C" {
void run();
void setParam(int w, int h, int p, int r);
}
#endif