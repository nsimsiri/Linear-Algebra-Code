I = eye(1000);
A = rand(1000);
B = triu(A);

ma = [0]
ma2 = [0]
for i = 1:1000
	t = time();
	k = inv(A);
	_t = time()-t;
	ma = [ma _t];
	t = time();
	j = B\I;
	_t2 = time()-t;
	ma2 = [ma2 _t2];

endfor

disp([1,2;3,4])

