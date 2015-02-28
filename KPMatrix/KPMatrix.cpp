#include "KPMatrix.h"

KPMatrix::KPMatrix(double arr){

}

KPMatrix::~KPMatrix(){

}

KPMatrix* KPMatrix::operator+(const KPMatrix &m){
	// return new KPMatrix(m.getStub() + this->stub);
	return NULL;
}

std::ostream & operator<<(std::ostream & Str, KPMatrix const& v){
	// Str << "-> " << v.stub;
	return Str;
}

int main(){

}