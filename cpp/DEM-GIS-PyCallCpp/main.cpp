#include <iostream>
#include <string>
#include "gisdem.h"

using namespace std;

/**
 * 需求：输入随机的高程值，对周围范围内内容进行考虑距离反比进行计算值
 * @return
 */
int main() {
	int height = getHeight();
	int width = getWidth();
	printf("%d,%d\n", height, width);
	gis.init();
	cout << gis.sourceToString() << endl << endl;
	cout << gis.resultToString() << endl;
	gis.println();
	return 0;
}