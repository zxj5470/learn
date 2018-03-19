#ifndef DEM_GIS_PYCALLCPP_LIBRARY_H
#define DEM_GIS_PYCALLCPP_LIBRARY_H

#include <iostream>
#include <cmath>
using namespace std;

const int w = 6;
const int h = 6;

class GisDEM {
public:
	void init();
	const char *sourceToString();
	const char *resultToString();
	void println();

private:
	double data[h][w]{0.0};
	double output[h][w]{0.0};
	double v(int dx,int dy);
	void inc(int i,int j,int di,int dj);
	void calculateEach(int i, int j);
	void calculate();
	string str;
};

static GisDEM gis{};

extern "C" {
const char *source();
const char *result();
int getHeight();
int getWidth();
}

#endif