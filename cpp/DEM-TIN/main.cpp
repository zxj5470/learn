#include <iostream>
#include <cmath>

using namespace std;
/**
 * @var x, y coordinate of the circle
 * @field value the elevation for the point
 */
typedef struct ZPoint {
	double x;
	double y;
	double value; // elevation
};

// Original point
static ZPoint O = {0.0, 0.0, 0.0};

/**
 * @var x, y : coordinate of the center of this circle
 * @var R : radius of the circle
 */
typedef struct ZCircle {
	double x;
	double y;
	double R;
};

class DEMTools {

private:
	/**
	 * @param p1,p2 : Two Points
	 * @return distance of two points
	 */
	inline double dist(ZPoint &p1, ZPoint &p2) {
		return dist(p1.x, p1.y, p2.x, p2.y);
	}

	inline double dist(double x1, double y1, double x2, double y2) {
		return sqrt(distSquare(x1, y1, x2, y2));
	}

	inline double distSquare(ZPoint &p1, ZPoint &p2) {
		return distSquare(p1.x, p1.y, p2.x, p2.y);
	}

	inline double distSquare(double x1, double y1, double x2, double y2) {
		return pow(x1 - x2, 2) + pow(y1 - y2, 2);
	}

public:
	/**
	 * Three points being same line equals to judge whether AB // BC
	 *
	 * (B.x-A.x) = k1 (C.x-A.x)
	 * (B.y-A.y) = k2 (C.y-A.y)
	 * @param A,B,C : Three Points
	 * @return if k1 equals to k2
	 */
	bool sameLine(ZPoint &A, ZPoint &B, ZPoint &C) {
		return (B.x - A.x) * (C.y - A.y) == (B.y - A.y) * (C.x - A.x);
	}

	/**
	 * make sure ABC is not in the same line.
	 * So call <code>sameLine(a,b,c)</code>  before
	 * @param A,B,C : Three Points
	 * @return ZCircle {x,y,R}
	 */
	inline ZCircle circumcircle(ZPoint &A, ZPoint &B, ZPoint &C) {
		double a = dist(B, C);
		double b = dist(A, C);
		double c = dist(A, B);

		double p = (a + b + c) / 2;
		double s = sqrt(p * (p - a) * (p - b) * (p - c));
		double R = a * b * c / (4.0 * s);

		double x1 = A.x, x2 = B.x, x3 = C.x;
		double y1 = A.y, y2 = B.y, y3 = C.y;

		double A_2 = distSquare(A, O), B_2 = distSquare(B, O), C_2 = distSquare(C, O);

		//calculate by using Matrix
		double det = 2 * (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y1));
		double x = (A_2 * (y2 - y3) + B_2 * (y3 - y1) + C_2 * (y1 - y2)) / det;
		double y = -(A_2 * (x2 - x3) + B_2 * (x3 - x1) + C_2 * (x1 - x2)) / det;

		return ZCircle{x, y, R};
	}

	bool isInCircle(ZPoint &p, ZCircle &c) {
		return dist(p.x, p.y, c.x, c.y) < c.R;
	}
};

int main() {
	DEMTools tool;
	ZPoint a = {0, 0}, b = {2, 0}, c = {1, -1};

	if (!tool.sameLine(a, b, c)) {
		auto cir = tool.circumcircle(a, b, c);
		cout << cir.x << "," << cir.y;
	}
	return 0;
}
