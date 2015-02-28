#include <iostream>
#include <cstdio>
#include <vector>
using namespace std;

void info(int** a, int size){
	for (int i = 0; i < size; i++){
		printf("%d\n", *(a+i));
	}
}

int main(){
	int[2][2] a = {{1,2},{3,4}};
	info(a, sizeof(a));
}