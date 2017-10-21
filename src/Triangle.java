import processing.core.PApplet;

import java.awt.*;

public class Triangle extends Shape {
    private Point[] points;
    private int width, height;

    public Triangle() {
        width = 40;
        height = 30;
    }

    Triangle(int centerX, int centerY, int width, int height, MyColor color) {
        super(centerX, centerY, color);
        this.width = width;
        this.height = height;
        this.type = Types.TRIANGLE;
        setOtherPoints();
    }


    @Override
    public void draw(PApplet applet) {
        super.draw(applet);
        applet.triangle(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY(), points[2].getX(), points[2].getY());
    }

    @Override
    public Triangle clone() {
        return (Triangle) super.clone();
    }

    @Override
    public boolean isCollision(int x, int y) {
        sortPoints();

        if (y < points[0].getY() || y > points[2].getY()) return false;

        int m1, m2;
        int x1, x2, tmp;

        if (y < points[1].getY()) {
            m1 = ((points[1].getX() - points[0].getX()) << 16) / (points[1].getY() - points[0].getY());
            m2 = ((points[2].getX() - points[0].getX()) << 16) / (points[2].getY() - points[0].getY());
            x1 = points[0].getX() + (((y - points[0].getY()) * m1) >> 16);
            x2 = points[0].getX() + (((y - points[0].getY()) * m2) >> 16);
            if (x1 > x2) {
                tmp = x1;
                x1 = x2;
                x2 = tmp;
            }
            return x >= x1 && x <= x2;
        } else {
            m1 = ((points[1].getX() - points[2].getX()) << 16) / (points[1].getY() - points[2].getY());

            m2 = ((points[0].getX() - points[2].getX()) << 16) / (points[0].getY() - points[2].getY());

            x1 = points[2].getX() + (((y - points[2].getY()) * m1) >> 16);
            x2 = points[2].getX() + (((y - points[2].getY()) * m2) >> 16);

            if (x1 > x2) {
                tmp = x1;
                x1 = x2;
                x2 = tmp;
            }
            return x >= x1 && x <= x2;
        }

    }

    @Override
    void setOtherPoints() {
        points = new Point[3];
        points[0] = new Point(this.getCenterX() - (width / 2), this.getCenterY() - height / 3);
        points[1] = new Point(this.getCenterX(), this.getCenterY() + height * 2 / 3);
        points[2] = new Point(this.getCenterX() + (width / 2), this.getCenterY() - height / 3);
    }

    private void sortPoints() {
        Point temp;
        if (points[0].getY() > points[1].getY()) {
            temp = points[0];
            points[0] = points[1];
            points[1] = temp;
        }
        if (points[0].getY() > points[2].getY()) {
            temp = points[0];
            points[0] = points[2];
            points[2] = temp;
        }
        if (points[1].getY() > points[2].getY()) {
            temp = points[1];
            points[1] = points[2];
            points[2] = temp;
        }
    }

    @Override
    public Types getType() {
        return Types.TRIANGLE;
    }
}