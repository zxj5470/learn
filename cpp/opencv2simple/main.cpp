#include <iostream>
#include <opencv2\highgui\highgui.hpp>
#include <opencv2\core\core.hpp>
#include <opencv2\imgproc\imgproc.hpp>

using namespace std;
using namespace cv;

int main()
{
    Mat imag, result;
    imag = imread("2345.jpg",0);    //将读入的彩色图像直接以灰度图像读入
    namedWindow("原图", 1);
    imshow("原图", imag);
    result = imag.clone();
    //进行二值化处理，选择30，200.0为阈值
    threshold(imag, result, 30, 200.0, CV_THRESH_BINARY);
    namedWindow("二值化图像");
    imshow("二值化图像", result);
    waitKey();
    return 0;
}