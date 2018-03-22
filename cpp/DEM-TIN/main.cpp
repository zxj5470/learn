#include <iostream>
#include <cmath>

using namespace std;

typedef struct ZPoint {
	double x;
	double y;
	double h;
};
static ZPoint O = {0.0, 0.0};

typedef struct ZCircle {
	double x;
	double y;
	double R;
};

inline double dist(ZPoint &p1, ZPoint &p2) {
	return sqrt(pow((p1.x - p2.x), 2) + pow(p1.y - p2.y, 2));
}

ZCircle circumcircle(ZPoint &A, ZPoint &B, ZPoint &C) {
	double a = dist(B, C);
	double b = dist(A, C);
	double c = dist(A, B);
	double p = (a + b + c) / 2;
	double s = sqrt(p * (p - a) * (p - b) * (p - c));
	double R = a * b * c / (4.0 * s);
	double x1 = A.x, x2 = B.x, x3 = C.x;
	double y1 = A.y, y2 = B.y, y3 = C.y;
	/*
	 * |x1 y1 1|
	 * |x2 y2 1|
	 * |x3 y3 1|
	 */
	double OA = dist(A, O), OB = dist(B, O), OC = dist(C, O);
	//use Matrix to calculate
	double det = 2 * (x1 * y2 + x2 * y3 + x3 * y1 - x1 * y3 - x2 * y1 - x3 * y2);
	double x = (OA * y2 + OB * y3 + OC * y1 - OB * y1 - OC * y2 - OA * y3) / det;
	double y = (OC * x2 + OB * x1 + OA * x3 - OA * x2 - OB * x3 - OC * x1) / det;
	return ZCircle{x, y, R};
}


bool sameLine(ZPoint &p1, ZPoint &p2, ZPoint &p3) {
	return false;
}

int main() {
	ZPoint a = {2, 0}, b = {1, sqrt(3)}, c = {0, 0};
	if (!sameLine(a, b, c)) {
		auto cir = circumcircle(a, b, c);
		printf("%f,%f,%f", cir.x, cir.y, cir.R);
	}
	return 0;
}