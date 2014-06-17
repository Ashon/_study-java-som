import glstudy.glObject.glPoint;

public class Unit extends glPoint{
	private double [] weight;

	public Unit(glPoint p, int length, boolean randomize){
		super(p);
		weight = new double [length];
		if(randomize)
			for(int i = 0; i < weight.length; i++)
				weight[i] = Math.random();
	}

	public double getSqrError(Unit u){
		if(u.getLength() != weight.length)
			return -1;
		else {
			double res = 0;
			for(int i = 0; i < weight.length; i++){
				double d = weight[i] - u.getValue(i); 
				res += d * d;
			}
			return res;
		}
	}

	public int getLength(){
		return weight.length;
	}

	public void setWeight(double [] weight){
		this.weight = weight;
	}
	public double [] getWeight(){
		return weight;
	}
	
	public void setValue(int idx, double val){
		weight[idx] = val;
	}
	public void addValue(int idx, double val){
		weight[idx] += val;
	}	
	public double getValue(int num){
		return weight[num];
	}

	public String toString(){
		String s = super.toString() + "[";
		for(int i = 0; i < weight.length; i++)
			s +=  weight[i] + (i == weight.length - 1 ? "]" : ", ");
		return s;
	}
}