import processing.core.PApplet;

import java.awt.*;

public class Triangle extends Shape {
    private Point[] points;
    private int width, height;

    Triangle(int centerX, int centerY, int width, int height, Color color) {
        super(centerX, centerY, color);
        this.width = width;
        this.height = height;
        setOtherPoints();
    }

    @Override
    public void draw(PApplet applet) {
        super.draw(applet);
        applet.triangle(points[0].x, points[0].y, points[1].x, points[1].y, points[2].x, points[2].y);
    }

    @Override
    public Triangle clone() {
        return (Triangle) super.clone();
    }

    @Override
    public boolean isCollision(int x, int y) {
        sortPoints();

        if (y < points[0].y || y > points[2].y) return false;

        int m1, m2;
        int x1, x2, tmp;

        if (y < points[1].y) {
            m1 = ((points[1].x - points[0].x) << 16) / (points[1].y - points[0].y);

            m2 = ((points[2].x - points[0].x) << 16) / (points[2].y - points[0].y);

            x1 = points[0].x + (((y - points[0].y) * m1) >> 16);
            x2 = points[0].x + (((y - points[0].y) * m2) >> 16);

            if (x1 > x2) {
                tmp = x1;
                x1 = x2;
                x2 = tmp;
            }
            return x >= x1 && x <= x2;
        } else {
            m1 = ((points[1].x - points[2].x) << 16) / (points[1].y - points[2].y);

            m2 = ((points[0].x - points[2].x) << 16) / (points[0].y - points[2].y);

            x1 = points[2].x + (((y - points[2].y) * m1) >> 16);
            x2 = points[2].x + (((y - points[2].y) * m2) >> 16);

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
        if (points[0].y > points[1].y) {
            temp = points[0];
            points[0] = points[1];
            points[1] = temp;
        }
        if (points[0].y > points[2].y) {
            temp = points[0];
            points[0] = points[2];
            points[2] = temp;
        }
        if (points[1].y > points[2].y) {
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