#ifndef KPMATRIX_H
#define KPMATRIX_H
#include <vector>
#include <iostream>

using namespace std;

class KPMatrix{
	vector< vector<double> > v;

	public:
		// KPMatrix(vector< vector<double> > v);
		KPMatrix(double[][] arr);
		~KPMatrix();	
		KPMatrix* operator+(const KPMatrix &m);
		KPMatrix* operator-(const KPMatrix &m);
		KPMatrix* operator*(const KPMatrix &m);
		// KPMatrix* operator(const KPMatrix &m);

		int getArray();

	private:
		friend std::ostream & operator<<(std::ostream & Str, KPMatrix const & v);
 };

#endif