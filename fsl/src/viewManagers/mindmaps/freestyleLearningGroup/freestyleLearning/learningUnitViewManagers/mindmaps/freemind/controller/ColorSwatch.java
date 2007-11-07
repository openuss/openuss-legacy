package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;


class ColorSwatch implements Icon
{
	Color color = Color.white;
	
	public ColorSwatch()
	{
	}

	public ColorSwatch(Color color)
	{
		this.color = color;
	}
	
	public int getIconWidth() {
	    return 11;
	}

	public int getIconHeight() {
	    return 11;
	}
	
	Color getColor() {
		return color;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
	    g.setColor(Color.black);
	    g.fillRect(x, y, getIconWidth(), getIconHeight());
		g.setColor(getColor());
	    g.fillRect(x+2, y+2, getIconWidth()-4, getIconHeight()-4);
	}
}
