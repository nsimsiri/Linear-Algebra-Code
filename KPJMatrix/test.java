

class test{
	public static void main(String[] args){
		System.out.println("Hello");	
		Double[][] d = new Double[][]{{1.,2.},{3.,4.}};
		Double[][] e = new Double[][]{{1.,2.},{1.,2.}};
		Integer[][] f = new Integer[][]{{3,4,5},{5,4,3}};
		KPJMatrix m = new KPJMatrix(d);
		KPJMatrix k = new KPJMatrix(e);
		KPJMatrix fm = new KPJMatrix(f);
		KPJMatrix I = KPJMatrix.getI(3);
		// System.out.format("%s\n+\n%s\n=\n%s", m, k, m.add(k));
		// System.out.format("%s\n*\n%s\n=\n%s", m, fm, m.mul(fm));
		System.out.format("%s\n", I.mul(3));
		// System.out.format("%s\nr=%d c=%d\n",fm, fm.getRowCount(), fm.getColCount());
	}
}