#include <iostream>
#include <string>
#include "gisdem.h"

using namespace std;

/**
 * 需求：输入随机的高程值，对周围范围内内容进行考虑距离反比进行计算值
 */
int main() {
	gis.init();
	gis.sourceToFile();
	gis.resultOutputFile();
	return 0;
}