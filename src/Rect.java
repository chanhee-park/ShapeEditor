import processing.core.PApplet;

import java.awt.*;

class Rect extends Shape {
    private int width;
    private int height;
    private Point start, end;

    Rect(int centerX, int centerY, int width, int height, Color color) {
        super(centerX, centerY, color);
        this.width = width;
        this.height = height;
        setOtherPoints();
    }

    @Override
    public void draw(PApplet applet) {
        super.draw(applet);
        applet.rect(start.x, start.y, width, height);
    }

    @Override
    public Rect clone() {
        return (Rect) super.clone();
    }

    @Override
    public boolean isCollision(int x, int y) {
        return (x >= start.x) && (x < end.x) && (y >= start.y) && (y <= end.y);
    }

    @Override
    void setOtherPoints() {
        start = new Point();
        end = new Point();
        start.x = this.getCenterX() - (width / 2);
        end.x = this.getCenterX() + (width / 2);
        start.y = this.getCenterY() - (height / 2);
        end.y = this.getCenterY() + (height / 2);
    }

    @Override
    public Types getType() {
        return Types.RECT;
    }
}
