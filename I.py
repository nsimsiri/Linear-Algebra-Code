import numpy as np

def gen_I(N):
	m_arr = []
	for i in range(N):
		in_arr = []
		for j in range(N):
			in_arr.append(0)
		m_arr.append(in_arr)
		m_arr[i][i] = 1
	return np.matrix(m_arr)

if (__name__=="__main__"):
	print(np.linalg.det(-1*gen_I(2)))