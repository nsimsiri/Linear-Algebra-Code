import java.util.List;

class test2{
	public static void main(String[] args){
		Integer[][] a1 = new Integer[][]{
			{2,1,1},
			{2,2,4},
			{5,3,9},
		};
		Integer[][] a2 = new Integer[][]{
			{1, 1},
			{1, 2},
		};
		Integer[][] a3 = new Integer[][]{
			{1, 1, 1},
			{0, 1, 1},
			{0, 0, 1}
		};
		KPJMatrix m1 = new KPJMatrix(a1);
		KPJMatrix p1 = KPJMatrix.getI(3);

		KPJMatrix m2 = new KPJMatrix(a2);
		KPJMatrix m3 = new KPJMatrix(a3);
		List<KPJMatrix> LU = m1.lu();
		System.out.format("m=\n%s\n\nL=\n%s\n\nU=\n%s", m1, LU.get(0), LU.get(1));
	}
}