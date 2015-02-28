import numpy as np

def bin_mat(n):
	st = []
	while(n!=0):
		rem = n%2
		st.append(rem)
		n = n/2
	for i in range(4-len(st)): st.append(0)
	st.reverse()
	return np.array(st)
if (__name__ == "__main__"):
	max_det = 0;
	N = 16
	for i in range(N):
		for j in range(N):
			for k in range(N):
				for l in range(N):
					mat = np.matrix([bin_mat(i), bin_mat(j), bin_mat(k), bin_mat(l)])
					
					det = np.linalg.det(mat)
					if (max_det < det):
						max_det = det
						print(mat)
						print("=> %d\n"%det)
	print(max_det)
