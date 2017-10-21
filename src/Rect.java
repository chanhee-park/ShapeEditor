import processing.core.PApplet;

import java.awt.*;

class Rect extends Shape {
    private int width;
    private int height;
    private Point start, end;

    public Rect() {
        super();
        this.width =40;
        this.height = 40;
    }

    Rect(int centerX, int centerY, int width, int height, MyColor color) {
        super(centerX, centerY, color);
        this.width = width;
        this.height = height;
        this.type = Types.RECT;
        setOtherPoints();
    }


    @Override
    public void draw(PApplet applet) {
        super.draw(applet);
        applet.rect(start.getX(), start.getY(), width, height);
    }

    @Override
    public Rect clone() {
        return (Rect) super.clone();
    }

    @Override
    public boolean isCollision(int x, int y) {
        return (x >= start.getX()) && (x < end.getX()) && (y >= start.getY()) && (y <= end.getY());
    }

    @Override
    void setOtherPoints() {
        start = new Point();
        end = new Point();
        start.setX(this.getCenterX() - (width / 2));
        end.setX(this.getCenterX() + (width / 2));
        start.setY(this.getCenterY() - (height / 2));
        end.setY(this.getCenterY() + (height / 2));
    }

    @Override
    public Types getType() {
        return Types.RECT;
    }
}
