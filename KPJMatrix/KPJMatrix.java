import java.util.*;
import java.io.*;

class KPJMatrix{
	private ArrayList<ArrayList<Double>> A;

	public KPJMatrix(Double[][] arr){
		this.A = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < arr.length; i++){
			ArrayList<Double> row = new ArrayList<Double>();
			for (int j = 0; j < arr[i].length; j++){
				row.add(arr[i][j]);
			}
			this.A.add(row);
		}
	}

	public KPJMatrix(ArrayList<ArrayList<Double>> arr){
		this.A = new ArrayList<ArrayList<Double>>();
		for (ArrayList<Double> arrlist : arr){
			ArrayList<Double> new_arr = new ArrayList<Double>();
			for (Double elm : arrlist){
				new_arr.add(elm);
			}
			this.A.add(new_arr);
		}
	}

	public KPJMatrix(Integer[][] arr){
		this.A = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < arr.length; i++){
			ArrayList<Double> row = new ArrayList<Double>();
			for (int j = 0; j<arr[i].length;j++){	
				row.add(new Double((double)arr[i][j]));
			}
			this.A.add(row);
		}
	}

	public List<KPJMatrix> lu(){
		// 0 -> L, 1 -> U
		KPJMatrix m = this;
		if (m.isSquare()){
			KPJMatrix U = new KPJMatrix(m.getA());
			KPJMatrix L = KPJMatrix.getI(m.getRowCount());
			int top_row_c = 1;
			for(int i = 1; i < this.getColCount(); i++){
				for (int j = top_row_c+1; j <= this.getRowCount(); j++){
					KPJMatrix Lj = KPJMatrix.getI(L.getRowCount());
					Lj.set(j,top_row_c, -(U.get(j,i)/U.get(top_row_c, i)));
					U = Lj.mul(U);
					L = Lj.mul(L);
				}
				top_row_c++;
			}
			List<KPJMatrix> res_list = new ArrayList<KPJMatrix>();
			res_list.add(L);
			res_list.add(U);
			return res_list;
		} else {
			throw new IllegalArgumentException("Matrix not square");
		}
	}

	public List<KPJMatrix> lu_upper(){
		if (this.isSquare()){
			KPJMatrix U = new KPJMatrix(this.getA());
			KPJMatrix L = KPJMatrix.getI(this.getRowCount());
			int bot_row_c = this.getRowCount();
			for (int i = this.getColCount(); i > 0; i--){
				for (int j = bot_row_c-1; j>0; j--){
					KPJMatrix Lj = KPJMatrix.getI(L.getRowCount());
					Lj.set(j, bot_row_c, -(U.get(j,i)/U.get(bot_row_c, i)));
					U = Lj.mul(U);
					L = Lj.mul(L);
				}
				bot_row_c--;
			}
			List<KPJMatrix> res_list = new ArrayList<KPJMatrix>();
			res_list.add(L);
			res_list.add(U);
			return res_list;
		} else {
			throw new IllegalArgumentException("Matrix not square");
		}

	}

	public static KPJMatrix getI(int N){
		Double[][] dArr = new Double[N][N];
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				if (i==j) dArr[i][j]=new Double(1);
				else dArr[i][j] = new Double(0);
			}
		}
		return new KPJMatrix(dArr);
	}

	public double get(int i, int j){
		if (i > 0 && j > 0 && i <= this.getRowCount() && j <= this.getColCount()){
			return this.A.get(i-1).get(j-1);
		} else {
			throw new IllegalArgumentException("bad index");
		}
	}

	public void set(int i, int j, int value){this.set(i, j, new Double(value));}
	public void set(int i, int j, Double value){
		if (i > 0 && j > 0 && i <= this.getRowCount() && j <= this.getColCount()){
			this.A.get(i-1).set(j-1, value);
		} else {
			throw new IllegalArgumentException("bad index");
		}	
	}

	public KPJMatrix mul(KPJMatrix m){
		// this x m
		ArrayList<ArrayList<Double>> B = new ArrayList<ArrayList<Double>>();
		if (m.getRowCount()==this.getColCount()){
			for (int i = 1; i <= this.getRowCount(); i++){
				ArrayList<Double> new_row = new ArrayList<Double>();
				for (int l = 1; l <= m.getColCount(); l++){
					double sum = 0;
					for (int j = 1; j <= this.getColCount(); j++){
						sum+=(this.get(i,j)*m.get(j,l));
					}
					new_row.add(new Double(sum));
				}
				B.add(new_row);
			} 
			return new KPJMatrix(B);
		}
		throw new IllegalArgumentException("not ixm m*j");
	}

	public KPJMatrix mul(int N){
		ArrayList<ArrayList<Double>> B = new ArrayList<ArrayList<Double>>();
		for(int i = 1; i <= this.getColCount(); i ++){
			ArrayList<Double> row = new ArrayList<Double>();
			for (int j = 1; j <= this.getRowCount(); j++){
				row.add(N*this.get(i,j));
			}
			B.add(row);
		}
		return new KPJMatrix(B);
	}

	public KPJMatrix add(KPJMatrix m){
		ArrayList<ArrayList<Double>> B = new ArrayList<ArrayList<Double>>();
		if (m.getRowCount()==this.getRowCount() && m.getColCount()==this.getColCount()){
			for (int i = 1; i <= this.getRowCount(); i++){
				ArrayList<Double> new_row = new ArrayList<Double>();
				for (int j = 1; j <= this.getColCount(); j++){
					new_row.add(m.get(i,j) + this.get(i,j));
				}
				B.add(new_row);
			} 
		}
		return new KPJMatrix(B);
	}

	@Override
	public String toString(){
		String s = "[ ";
		int i = 0;
		for (ArrayList<Double> row : this.A){
			if (i!=0) s+="\n  ";
			i++;
			for (Double elm : row) s+=String.format("%f ", elm);
		}
		return s+"]";
	} 
	public boolean isSquare() {return this.getRowCount()==this.getColCount();}
	public boolean isEmpty() {return this.A.isEmpty();};
	public ArrayList<ArrayList<Double>> getA() {return this.A;}
	public int getRowCount() {return this.A.size();}
	public int getColCount() {
		if (!this.isEmpty()) return this.A.get(0).size();	
		return 0;
	}

	public static void main(String[] args) throws IOException{
		Scanner sc = new Scanner(System.in);
		ArrayList<ArrayList<Double>> input_list = new ArrayList<ArrayList<Double>>();
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if (!line.contains("//") && !line.isEmpty()){
				String[] s = line.split(" ");
				ArrayList<Double> row = new ArrayList<Double>();
				for (int i=0; i < s.length; i++) {
					row.add(Double.parseDouble(s[i]));
				}

				input_list.add(row);
			}
		}
		KPJMatrix m = new KPJMatrix(input_list);
		System.out.format("Your matrix=\n\n%s \n row= %d col= %d\n", m, m.getRowCount(), m.getColCount());	
		List<KPJMatrix> LU = m.lu();
		System.out.format("\nL=\n\n%s\n\nU=\n\n%s\n", LU.get(0), LU.get(1));
	}
}