import glstudy.glObject.glPoint;
import glstudy.glUtil.bufferedCanvas;
import glstudy.glUtil.eventedFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.util.Vector;

public class WeightMapCanvas extends bufferedCanvas{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Map map;
	
	int idx;
	int height;
	int width;
	
	/**
	 * weight canvas static constant
	 */
	
	static int MAP_SCALE = 1;
	static int MAP_PADDING = 5;
	static int MAP_PADDING_BOTTOM = 100;
	static int MAP_FONT_SIZE = 10;
	static int MAP_FONT_MARGIN = 5;
	
	public WeightMapCanvas(int width, int height, int weightLength, int idx){
		super();
		map = new Map(width, height, weightLength);
		this.idx = idx;
		this.height = MAP_SCALE * height + MAP_PADDING * 2 + MAP_PADDING_BOTTOM;
		this.width = MAP_SCALE * width + MAP_PADDING * 2;
		this.width = this.width < 120 ? 120 : this.width;
		this.setPreferredSize(new Dimension(this.width, this.height));
	}

	public WeightMapCanvas(Map m, int idx){
		super();
		map = m;
		this.idx = idx;
		this.height = MAP_SCALE * m.height + MAP_PADDING * 2 + MAP_PADDING_BOTTOM;
		this.width = MAP_SCALE * m.width + MAP_PADDING * 2;
		this.width = this.width < 120 ? 120 : this.width;
		this.setPreferredSize(new Dimension(this.width, this.height));
	}

	@Override
	public void set(Graphics g){
		g.setColor(Color.lightGray);
		g.drawRect(0,0,width-1,height-1);
		g.setColor(Color.black);
		g.translate(MAP_PADDING, MAP_PADDING);
		g.setFont(g.getFont().deriveFont(MAP_FONT_SIZE * 1.0f));
	}
	
	
	
	@Override
	public void draw(Graphics g){
		for(Unit u : map.getUnitVector()){
			int color = Color.HSBtoRGB((float)u.getValue(idx), 1, 1 - (float)(Math.pow(u.getValue(idx), 10)));
			g.setColor(new Color(color));
			g.fillRect((int)u.x * MAP_SCALE, (int)u.y * MAP_SCALE, MAP_SCALE, MAP_SCALE);
			//drawBorder(u, g);
		}
		//drawSample(g);
	}
	public void drawBorder(Unit u, Graphics g){
		g.setColor(g.getColor().darker());
		g.drawRect((int)u.x * MAP_SCALE, (int)u.y * MAP_SCALE, MAP_SCALE, MAP_SCALE);
	}
	public void drawSample(Graphics g){
		g.setColor(Color.black);
		for(Unit s : map.getSampleVector()){
			Unit u = map.getBMU(s);
			g.drawRect((int)u.x * MAP_SCALE, (int)u.y * MAP_SCALE, MAP_SCALE, MAP_SCALE);
			g.drawString(map.getSampleVector().indexOf(s) + "",
					(int)u.x * MAP_SCALE + MAP_SCALE + MAP_FONT_MARGIN, (int)u.y * MAP_SCALE + MAP_FONT_SIZE);
		}
	}
	
	public void drawStatus(Graphics g){
		int row = 0;
		int height = this.height - (MAP_PADDING_BOTTOM + MAP_PADDING - MAP_FONT_SIZE);
		g.setColor(Color.black);
		g.drawString(map.getClass().getName()+"@"+map.hashCode()+" : "+idx,
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
		g.drawString("[width = " + map.width + "]",
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
		g.drawString("[height = " + map.height + "]",
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
		g.drawString("[weightLength = " + map.weightLength + "]",
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
		g.drawString("[SampleSize = " + map.getSampleVector().size() + "]",
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
		g.drawString("[learningRate = " + map.learningRate + "]",
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
		g.drawString("[threshold = " + map.threshold + "]",
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
		g.drawString("[range = " + map.range + "]",
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
		g.drawString("[iteration = " + map.iteration + "]",
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
		g.drawString("[progress = " + (int)((map.iteration - map.counter) * 100.0 / map.iteration)+ "%]",
				MAP_PADDING, height + MAP_FONT_SIZE * row++);
	}
	
	@Override
	public void unset(Graphics g){
		g.translate(-MAP_PADDING, -MAP_PADDING);
		drawStatus(g);
	}
	
	public static void main(String [] args){
		Map m = new Map(30, 30, 3);
		WeightMapCanvas mc = new WeightMapCanvas(m, 0);
		Panel p = new Panel();
		eventedFrame ef = new eventedFrame("Self Organizing Maps");
		Vector<Unit> sample = new Vector<Unit>();
		
		for(int i = 0; i < 100; i++){
			Unit s = new Unit(new glPoint(0,0), m.weightLength, true);
			sample.add(s);
		}
		m.setSampleVector(sample);
		p.add(mc);
		ef.add(p);
		ef.pack();
		ef.setVisible(true);
	}
}
