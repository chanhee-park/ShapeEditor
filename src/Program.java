import processing.core.PApplet;
import processing.event.MouseEvent;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Program extends PApplet {
    enum Mode {
        MAKE {
            @Override
            void mouseClick(int x, int y) {
                if (shapeToBeMade == null) return;
                Color color = setRandomColor();
                Shape newShape = shapeToBeMade.makeShape(x, y, color);
                shapes.add(newShape);
            }
        }, CLONE {
            @Override
            void mouseClick(int x, int y) {
                for (int i = shapes.size() - 1; i >= 0; i--) {
                    Shape shape = shapes.get(i);
                    if (shape.isCollision(x, y)) {
                        Shape newShape = shape.clone();
                        shapes.add(newShape);
                        break;
                    }
                }
            }
        }, MOVE {
            void mouseClick(int x, int y) {
                for (int i = shapes.size() - 1; i >= 0; i--) {
                    Shape shape = shapes.get(i);
                    if (shape.isCollision(x, y)) {
                        shape.setClicked(true);
                        break;
                    }
                }
            }
        };

        abstract void mouseClick(int x, int y);
    }

    private static List<Shape> shapes = new ArrayList<>();
    private static Shape.Types shapeToBeMade = null;
    private Mode mode = Mode.MAKE;
    private Shape[] buttons;
    private boolean isControlPressed = false;


    public static void main(String[] args) {
        PApplet.main("Program");
    }

    @Override
    public void settings() {
        this.size(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }

    @Override
    public void setup() {
        makeButtons();
    }

    @Override
    public void draw() {
        background(30);
        drawShapes();
        drawMode();
        drawButtons();
    }

    @Override
    public void keyPressed() {
        if (keyCode == CONTROL) {
            isControlPressed = true;
        }
        if (!isControlPressed) {
            return;
        }
        if (keyCode == 'n' || keyCode == 'N') {
            mode = Mode.MAKE;
        } else if (keyCode == 'd' || keyCode == 'D') {
            mode = Mode.CLONE;
        } else if (keyCode == 'm' || keyCode == 'M') {
            mode = Mode.MOVE;
        } else if (keyCode == 's' || keyCode == 'S') {
            try {
                saveShape();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (keyCode == 'o' || keyCode == 'O') {
            try {
                readFile();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased() {
        if (keyCode == CONTROL) {
            isControlPressed = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        for (Shape button : buttons) {
            if (mode == Mode.MAKE && button.isCollision(mouseX, mouseY)) {
                shapeToBeMade = button.getType();
                return;
            }
        }
        mode.mouseClick(mouseX, mouseY);
    }

    @Override
    public void mouseReleased() {
        for (Shape shape : shapes) {
            if (shape.isClicked()) {
                shape.setClicked(false);
                break;
            }
        }
    }

    @Override
    public void mouseMoved() {
        for (Shape shape : shapes) {
            shape.setOver(false);
        }
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.isCollision(mouseX, mouseY)) {
                shape.setOver(true);
                break;
            }
        }
    }

    @Override
    public void mouseDragged() {
        if (mode == Mode.MOVE) {
            for (Shape shape : shapes) {
                if (shape.isClicked()) {
                    shape.setCenterX(mouseX);
                    shape.setCenterY(mouseY);
                    break;
                }
            }
        }
    }

    private static Color setRandomColor() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        return new Color(r, g, b);
    }

    private void makeButtons() {
        buttons = new Shape[3];
        Rect buttonRect;
        Circle buttonCircle;
        Triangle buttonTriangle;

        int centerX = Constants.BUTTON_RECT_CENTER_X;
        int centerY = Constants.BUTTON_RECT_CENTER_Y;
        int width = Constants.BUTTON_RECT_WIDTH;
        int height = Constants.BUTTON_RECT_HEIGHT;
        Color rgb = new Color(100, 100, 100);
        buttonRect = new Rect(centerX, centerY, width, height, rgb);

        centerX = Constants.BUTTON_CIRCLE_CENTER_X;
        centerY = Constants.BUTTON_CIRCLE_CENTER_Y;
        int radius = Constants.BUTTON_CIRCLE_RADIUS;
        buttonCircle = new Circle(centerX, centerY, radius, rgb);

        centerX = Constants.BUTTON_TRIANGLE_CENTER_X;
        centerY = Constants.BUTTON_TRIANGLE_CENTER_Y;
        width = Constants.BUTTON_TRIANGLE_WIDTH;
        height = Constants.BUTTON_TRIANGLE_HEIGHT;
        buttonTriangle = new Triangle(centerX, centerY, width, height, rgb);

        buttons[0] = buttonRect;
        buttons[1] = buttonCircle;
        buttons[2] = buttonTriangle;
    }

    private void drawButtons() {
        for (Shape button : buttons) {
            button.draw(this);
            this.fill(255);
            this.textSize(15);
            this.text(button.getClass().getName(), button.getCenterX() - 20, button.getCenterY());
        }
    }

    private void drawShapes() {
        for (Shape shape : shapes) {
            shape.draw(this);
        }
    }

    private void drawMode() {
        this.fill(255);
        this.textSize(12);
        String announcement = "";
        if (mode == Mode.MAKE && shapeToBeMade == null) {
            announcement = "Click Shape type what you want to make.";
        } else if (mode == Mode.MAKE) {
            announcement = "Click where you want to place the " + shapeToBeMade.name();
        } else if (mode == Mode.CLONE) {
            announcement = "Click on the shape you want to Clone.";
        }
        this.text(announcement, 50, Constants.WINDOW_HEIGHT - 150);

        String announcement2 = "Control + n : Make a New Shape\n" + "Control + d : Clone a Shape\n" + "Control + m : Move a Shape\n" + "now : " + mode.name();
        this.text(announcement2, 50, Constants.WINDOW_HEIGHT - 100);
    }

    private void saveShape() throws IOException {
        FileOutputStream fos = new FileOutputStream("shape.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        int i = 0;
        for (Shape shape : shapes) {
            oos.writeObject(shape);
        }
        fos.close();
        oos.close();
    }

    private void readFile() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("shape.dat"));

        Object object;
        while ((object = ois.readObject()) != null) {
            if (!(object instanceof Shape)) return;
            shapes.add((Shape) object);
        }
        ois.close();
    }
}