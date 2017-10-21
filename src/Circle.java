import processing.core.PApplet;

import java.awt.*;

class Circle extends Shape {
    private int radius;

    Circle(int centerX, int centerY, int radius, MyColor color) {
        super(centerX, centerY, color);
        this.radius = radius;
        this.type = Types.CIRCLE;
    }

    public Circle() {
        radius = 20;
    }

    @Override
    public void draw(PApplet applet) {
        super.draw(applet);
        int diameter = 2 * radius;
        applet.ellipse(super.getCenterX(), super.getCenterY(), diameter, diameter);
    }

    @Override
    public Circle clone() {
        return (Circle) super.clone();
    }

    @Override
    public boolean isCollision(int x, int y) {
        int a = super.getCenterX();
        int b = super.getCenterY();
        return (x - a) * (x - a) + (y - b) * (y - b) < radius * radius;
    }

    @Override
    void setOtherPoints() {
    }

    @Override
    public Types getType() {
        return Types.CIRCLE;
    }
}
