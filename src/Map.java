import glstudy.glObject.glPoint;

import java.util.Vector;


public class Map{
	private Vector<Unit> unitArray;
	private Vector<Unit> sample;
	double threshold = 0.05;
	double learningRate = 0.3;
	double range = 30;
	int iteration = 200;
	int weightLength;
	int width;
	int height;
	
	int counter = iteration;

	public Map(int width, int height, int weightLength){
		unitArray = new Vector<Unit>();
		this.weightLength = weightLength;
		this.width = width;
		this.height = height;

		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				unitArray.add(new Unit(new glPoint(i, j), weightLength, true));
	}

	public void adjustWeight(Unit t, double range){
		for(Unit u : unitArray){
			double activate = learningRate * Math.exp(-u.getSqrDist(t) / (range * range));
			if(activate >= threshold * learningRate){
				for(int i = 0; i < weightLength; i++){
					double error = t.getValue(i) - u.getValue(i);
					u.addValue(i, error * activate * learningRate);
				}
			}
		}
	}

	@Override
	public String toString(){
		return super.toString() +
				"[width=" + width + "]" +
				"[heght=" + height + "]" +
				"[weightLength=" + weightLength + "]" +
				"[threshold=" + threshold + "]" +
				"[learningRate=" + learningRate + "]" +
				"[range=" + range + "]";
	}
	/**
	 * 유닛 t에 대하여 맵에서 BMU를 추출한다.
	 * @param t
	 * @return
	 */
	public Unit getBMU(Unit t){
		Unit bmu = unitArray.get(0);
		double err = bmu.getSqrError(t);
		for(Unit u : unitArray){
			double ue = u.getSqrError(t); 
			if(ue < err){
				bmu = u;
				err = ue;
			}
		}
		return bmu;
	}
	
	public boolean step(){
		if(counter == 0)
			return false;
		for(Unit s : sample){
			Unit bmu = getBMU(s);
			s.set(bmu);
			adjustWeight(s, range * (counter * 1.0 / iteration));
		}
		counter--;
		return true;
	}
	
	public boolean isLearned(){
		return counter == 0 ? true : false;
	}
	
	public void setSampleVector(Vector<Unit> sample){
		this.sample = sample;
	}
	public Vector<Unit> getSampleVector(){
		return sample;
	}
	public Vector<Unit> getUnitVector(){
		return unitArray;
	}
}