import processing.core.PApplet;

import java.awt.*;
import java.awt.Point;
import java.io.Serializable;

abstract class Shape implements Cloneable, Serializable {
    private Point center;
    private Color color;
    private boolean over = false;
    private boolean clicked = false;

    enum Types {
        RECT {
            @Override
            Shape makeShape(int x, int y, Color color) {
                return new Rect(x, y, 40, 40, color);
            }
        }, CIRCLE {
            @Override
            Shape makeShape(int x, int y, Color color) {
                return new Circle(x, y, 20, color);
            }
        }, TRIANGLE {
            @Override
            Shape makeShape(int x, int y, Color color) {
                return new Triangle(x, y, 40, 30, color);
            }
        };

        abstract Shape makeShape(int x, int y, Color color);

    }

    Shape(int centerX, int centerY, Color color) {
        center = new Point(centerX, centerY);
        this.color = color;
    }

    public void draw(PApplet applet) {
        applet.stroke(153);
        if (over) {
            applet.stroke(255);
        }
        if(clicked){
            int r = (int) (color.getRed()*1.2);
            int g = (int) (color.getGreen()*1.2);
            int b = (int) (color.getBlue()*1.2);
            if(r > 255) r = 255;
            if(g > 255) g = 255;
            if(b > 255) b = 255;
            applet.fill(r, g, b);
            return;
        }
        applet.fill(color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public Shape clone() {
        Shape clone;
        try {
            clone = (Shape) super.clone();
            clone.center = (Point) this.center.clone();
            clone.setCenterX(clone.getCenterX() + 15);
            clone.setCenterY(clone.getCenterY() + 15);
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    abstract boolean isCollision(int x, int y);

    abstract void setOtherPoints();

    public abstract Types getType();

    public int getCenterX() {
        return center.x;
    }

    public int getCenterY() {
        return center.y;
    }

    public void setCenterX(int centerX) {
        center.x = centerX;
        setOtherPoints();
    }

    public void setCenterY(int centerY) {
        center.y = centerY;
        setOtherPoints();
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}

