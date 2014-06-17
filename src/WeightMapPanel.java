import glstudy.glObject.glPoint;
import glstudy.glUtil.eventedFrame;

import java.awt.GridLayout;
import java.awt.Panel;
import java.util.Vector;

public class WeightMapPanel extends Panel implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2964351865113526449L;	
	
	Map map;
	Vector<WeightMapCanvas> canvasArray;
	volatile Thread timer;
	
	public WeightMapPanel(Map m){
		super();
		this.setLayout(new GridLayout(2, 10));
		map = m;
		canvasArray = new Vector<WeightMapCanvas>();
		for(int i = 0; i < m.weightLength; i++){
			WeightMapCanvas wmc = new WeightMapCanvas(map, i);
			canvasArray.add(wmc);
			add(wmc);
		}
	}
	
	public void repaint(){
		super.repaint();
		for(WeightMapCanvas wmc : canvasArray)
			wmc.repaint();
	}
	
	public void run(){
		while(Thread.currentThread() == timer){
			try{
				Thread.sleep(0);
			}catch(InterruptedException e){ }
			repaint();
			if(!map.step())
				stop();
		}
	}
	
	public void start(){
		timer = new Thread(this);
		timer.start();
	}
	
	public void stop(){
		timer = null;
	}
	
	public static void main(String [] args){
		Map m = new Map(200, 200, 5);
		WeightMapPanel wmp = new WeightMapPanel(m);
		
		eventedFrame ef = new eventedFrame("Self Organizing Maps");
		Vector<Unit> sample = new Vector<Unit>();
		
		// set sample vector
		for(int i = 0; i < 30; i++){
			Unit s = new Unit(new glPoint(0,0), m.weightLength, true);
			sample.add(s);
		}
		m.setSampleVector(sample);
		ef.add(wmp);
		ef.pack();
		ef.setVisible(true);
		wmp.start();
	}
}
