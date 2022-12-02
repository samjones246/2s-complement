package com.samjo.examplemod;

import graphics.Color;
import graphics.Graphics;
import graphics.Shape;
import main.Entity;

public class MapHighlightEntity extends Entity {
    private double width;
    private double height;
    private double lineThickness;
    private Color color;

    public MapHighlightEntity(
            double x,
            double y,
            double width,
            double height,
            double lineThickness,
            Color color
    ) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.lineThickness = lineThickness;
        this.color = color;
        this.setDepth(Integer.MIN_VALUE);
        persistent = true;
    }

    @Override
    public void render() {
        super.render();
        Graphics.setColor(color.elements);
        Graphics.setAlpha(1.0);
        double x1 = x;
        double x2 = x+width;
        double y1 = y;
        double y2 = y+height;
        Shape.drawLine(x1, y1, x2, y1, lineThickness);
        Shape.drawLine(x1, y2, x2, y2, lineThickness);
        Shape.drawLine(x1, y1, x1, y2, lineThickness);
        Shape.drawLine(x2, y1, x2, y2, lineThickness);
    }
}