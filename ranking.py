import numpy as np
import sys
import copy

## Natcha Simsiri
## Program starts below

class IncidenceMatrix():
	def __init__(self):
		self.A = None
		self.b = None
		self.node_table = dict()
		self.inv_node_table = dict()

	def __str__(self):
		s=""
		for i in range(self._node_count()):
			s+="(%s) %s"%(i, self.inv_node_table[i])
			if (i!=self._node_count()-1): s+=", "
		s+="\n"
		s+="M=\n%s\n"%(self.A)
		s+="b=\n%s"%(self.b)
		return s

	## METHOD 1 - inv(A)b = x
	def rank_1(self, _A, _b):
		A = copy.copy(A)
		b = copy.copy(_b)
		if (np.linalg.det(_A)==0):
			## singular case, we ground a node by taking out a column.
			new_A = []
			for row in A.getA(): new_A.append(row[:len(row)-1])
			x_hat = self.rank_1(np.matrix(new_A), b)
			return np.matrix(np.concatenate((x_hat.getA(),[[0]]), axis=0))
		else:
			return A.getI()*b

	## METHOD 2 - LEAST SQUARE METHOD
	def rank_lsqr(self, _A, _b):
		## A*inv(A'*A)*A'
		A = copy.copy(_A)
		b = copy.copy(_b)
		if (np.linalg.det(A.getT()*A)!=0):
			P = (A.getT()*A).getI()*A.getT()
			return P*b
		else:
			## case where A'A is singular -> we "ground" a node, i.e. take away a column.
			new_A = []
			for row in A.getA(): new_A.append(row[:len(row)-1])
			x_hat = self.rank_lsqr(np.matrix(new_A), b)
			return np.matrix(np.concatenate((x_hat.getA(), [[0]]), axis=0))

	def rank(self):
		## ranking construction - for pretty printing
		x_h = self.rank_lsqr(self.A, self.b).getA()
		if len(x_h)==1: return []
		result=[]
		i=0
		for score in x_h:
			result.append((self.inv_node_table[i], score[0]))
			i+=1
		return result

 	## irrelevant code - input/parsing stuff here ##
	def append(self, h, g, h_score, g_score):
		new_col_count=0
		if (not (h in self.node_table)): 
			self.node_table[h] = self._node_count()
			self.inv_node_table[self._node_count()-1] = h
			new_col_count+=1
		if (not (g in self.node_table)): 
			self.node_table[g] = self._node_count()
			self.inv_node_table[self._node_count()-1]=  g
			new_col_count+=1
		score=0
		if (h_score-g_score >=0): score=h_score-g_score+500
		else: score=h_score-g_score-500
		if (self.A is None):
			self.A = np.matrix([1, -1])
			self.b = np.matrix([score])
		else:
			if (self._node_count() > self.A.shape[1]): self._add_col(new_col_count)
			self.A = np.matrix(np.concatenate((self.A.getA(),[self._construct_row(h,g)]), axis=0));
			self.b = np.matrix(np.concatenate((self.b.getA(), [[score]]), axis=0))

	def _node_count(self): return len(self.node_table)

	def _construct_row(self, h, g):
		row = [0]*self.A.shape[1] # shape = (col, row)
		for i in range(len(row)):
			if (i==self.node_table[h]): row[i] = 1
			elif (i==self.node_table[g]): row[i] = -1
		return row

	def _add_col(self, n):
		arr = self.A.getA()
		new_arr = []
		for row in arr: new_arr.append(np.concatenate((row,[0]*n),axis=0))
		self.A=np.matrix(new_arr)

	@staticmethod
	def parse_file(M,file):
		ranking_input = open(file, "r")
		str_ranking_input = ranking_input.read()
		vs_rows = str_ranking_input.split("\n")
		## 0 - host, 1 - guest, 2 - host_score, 3 - guest score
		for vs_row in vs_rows:
			row = vs_row.split(" ")
			if (len(row)==4): M.append(row[0], row[1], int(row[2]), int(row[3]))

if (__name__=="__main__"):
	# Program starts here!
	# to run, type: "python ranking.py real_input.txt" 

	M = IncidenceMatrix() ## create IncidenceMatrix class
	IncidenceMatrix.parse_file(M,sys.argv[1]) ## parses file, and appends each input to matrix
	print M
	ranking = sorted(M.rank(), key=lambda score: score[1], reverse=True)
	rc=1
	print("\n\nRANKING:\n")
	for r in ranking: 
		print "(%d) %s - %s"%(rc,str(r[0]).upper(), r[1])
		rc+=1
	## output for ranking_input file
	# 	(0) harvard, (1) brown, (2) yale, (3) cornell, (4) dartmouth, (5) princeton ## columns 
	# M=
	# [[ 1 -1  0  0  0  0]
	#  [ 0  0  1 -1  0  0]
	#  [ 0  0 -1  0  1  0]
	#  [-1  0  0  1  0  0]
	#  [ 0  1  0  0  0 -1]
	#  [ 0 -1  0  1  0  0]
	#  [ 1  0  0  0  0 -1]
	#  [ 0  0  0 -1  0  1]
	#  [ 1  0  0  0 -1  0]
	#  [ 0 -1  1  0  0  0]
	#  [ 0  0  0 -1  1  0]
	#  [ 0  1  0  0 -1  0]
	#  [ 0  0 -1  0  0  1]
	#  [-1  0  1  0  0  0]
	#  [ 0  0  0  0  1 -1]]
	# b=
	# [[ 508]
	#  [ 538]
	#  [ 507]
	#  [-517]
	#  [-511]
	#  [-526]
	#  [ 542]
	#  [ 511]
	#  [ 511]
	#  [ 503]
	#  [ 535]
	#  [-523]
	#  [-514]
	#  [-507]
	#  [ 531]]


	# RANKING:

	# (1) HARVARD - 525.0
	# (2) DARTMOUTH - 358.333333333
	# (3) YALE - 184.333333333
	# (4) PRINCETON - 0.0
	# (5) BROWN - -159.0
	# (6) CORNELL - -343.666666667



