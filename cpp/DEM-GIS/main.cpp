#include <iostream>
#include <fstream>
#include <vector>

using namespace std;
#define MAX 1000

void split(const string source, const string &delimiter, vector<string> &ret) {
	size_t sourceLength = source.length();
	size_t currentOffset = 0;
	size_t nextIndex = source.find(delimiter);
	if (nextIndex == source.npos)return;
	do {
		ret.push_back(source.substr(currentOffset, nextIndex - currentOffset + 1));
		currentOffset = nextIndex + delimiter.length();
		nextIndex = source.find(delimiter, currentOffset);
	} while (nextIndex != source.npos);
	ret.push_back(source.substr(currentOffset, sourceLength - currentOffset + 1));
}

void handle(vector<vector<int>> &data) {
	bool bools[MAX][MAX];
	size_t rows = data.size();
	size_t cols = data[0].size();
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
			bools[i][j] = true;
		}
	}
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
			if (bools[i][j]) {
				int c = 1;
				int value = data[i][j];
				while (i + c < rows && j + c < cols && data[i + c][j + c] == value) {
					bool flag = true;
					for (int k = 1; k <= c; k++) {
						if (data[i + c - k][j + c] != value || data[i + c][j + c - k] != value) {
							flag = false;
							break;
						}
					}
					if (flag) c++;
					else break;
				}
				for (int m = i; m < i + c; m++) {
					for (int n = j; n < j + c; n++) {
						bools[m][n] = false;
					}
				}
				printf("(%d,%d,%d,%d)\n", i + 1, j + 1, value, c);
			}
		}
	}
}

int main() {
	ifstream fin("../data.csv");
	string s;
	vector<vector<int>> data;
	while (fin >> s) {
		vector<string> temp;
		vector<int> ints;
		split(s, ",", temp);
		std::for_each(temp.begin(), temp.end(),
		              [&ints](string it) {
			              ints.push_back(atoi(it.c_str()));
		              });
		data.push_back(ints);
	}
	handle(data);
	return 0;
}