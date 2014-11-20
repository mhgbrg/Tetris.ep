package se.chalmers.matsho.Tetris;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class PieceTile extends RectangularTile {
	private static final Color STROKE_COLOR = new Color(0, 0, 0);
	private static final float STROKE_THICKNESS = (float) 2.0;
	private static final Stroke STROKE = new BasicStroke(STROKE_THICKNESS);

	public PieceTile(Color color) {
		super(color);
	}
	
	@Override
	public void draw(final Graphics g, final int x, final int y,
			final Dimension d) {
		super.draw(g, x, y, d);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(STROKE);
		g2.setColor(STROKE_COLOR);
		g2.drawRect(x, y, d.width, d.height);
	}
}
