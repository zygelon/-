#include <iostream>
#include <omp.h>
#include <conio.h>
#include <intrin.h>
#include <iomanip>
#include <chrono>
#include <stdlib.h>
#include <ctime>
#include <cmath>
#include <chrono>
#pragma omp
#include <array>
#define Num 2000

using namespace std;

void SetZero(unique_ptr<array<array<int, Num>, Num> >& arr) {
	for (int i = 0; i < Num; i++)
		for (int j = 0; j < Num; j++)
			(*arr)[i][j] = rand() % 8;
}

int main() {
	auto a = make_unique<array<array<int, Num>, Num> >();
	auto b = make_unique<array<array<int, Num>, Num> >();
	auto c = make_unique<array<array<int, Num>, Num> >();
	SetZero(a);

	SetZero(b);
	clock_t start_time = clock();
	//int i, j, g;
	//int i=0;
	//		

//}

	int i;
	#pragma omp parallel for
	for (i = 0; i < 2000; i++)
	{
		int sum = 0;
		for (int g = 0; g < Num; g++) {
			for (int j = 0; j < Num; j++)
				sum += (*a)[i][j] * (*b)[j][g];
			(*c)[i][g] = sum;
		}
	}
	std::cout << "N = " << Num << "; durdudr = " << int(clock() - start_time) << "\n";
	system("pause");
	return 0;
}