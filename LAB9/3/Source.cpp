#include <tbb/parallel_for.h>
#include "tbb/blocked_range2d.h"
#include "tbb/tick_count.h"
#include <chrono>
#include <cstdlib>
#include <cmath>
#include <fstream>
#include <iostream>
#include <vector>
#define Num 2000

using namespace tbb;

std::vector<std::vector<int> >a;
std::vector<std::vector<int> >b;
std::vector<std::vector<int> >c;

class Multiply {
public:
	void operator()(blocked_range2d<int> r) const {
		for (size_t i = r.rows().begin(); i != r.rows().end(); ++i) {
			for (size_t j = r.cols().begin(); j != r.cols().end(); ++j) {
				int sum = 0;
				for (int k = 0; k < Num; ++k) {
					sum += a[i][k] * b[k][j];
					c[i][j] = sum;
				}
			}
		}
	}
};

int main() {
	

	for (int i = 0; i < Num; ++i) {
		std::vector<int>a1(Num);
		std::vector<int>b1(Num);
		std::vector<int>c1(Num,0);
		for (int j = 0; j < Num; ++j) {
			a1[j] = rand() % 12;
			b1[j] = rand() % 13;

		}
		a.push_back(a1);
		b.push_back(b1);
		c.push_back(c1);
	}
	tick_count startTime;
	startTime = tick_count::now();
	parallel_for(blocked_range2d<int>(0, Num, 16, 0, Num, 32), Multiply());
	int ttt = (tick_count::now() - startTime).seconds() * 1000;
	std::cout << "N = " << Num << "; Duration: " << ttt << " ms";
	system("pause");
	return 0;
}