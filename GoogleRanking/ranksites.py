import numpy as np
from fractions import Fraction
import sys
import math

def rank(A):
	# print A
	S, lam = np.linalg.eig(A) 
	lam = lam.getA()
	L = []
	rankT = dict()
	i = 0
	for row in lam:
		i+=1
		## we get the eigenvector where its eigenvalue = 1, since 
		## we know that this column will not decay one we diangonalize to the k.`
		L.append(row[0].real) 
		rankT[row[0].real] = i

	# sort to pretty print. 
	M = sorted(L, reverse=True)
	print "\nRanking:"
	for i in range(len(M)):
		print "%d: x%s = %f"%(i+1, rankT[M[i]], M[i])

if (__name__=="__main__"):
	file_name = sys.argv[1]
	f = open(file_name, 'r')
	lines = f.read().split('\n')
	A = []
	for line in lines:
		if ("#" not in line):
			row =[]
			for frac in line.split(" "):
				if (len(frac)!=0): row.append(float(Fraction(frac)))
			if len(row)>0: A.append(row)
	rank(np.matrix(A))
