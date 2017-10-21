public class MyColor implements Cloneable {
    private int myRed;
    private int green;
    private int blue;

    MyColor() {

    }

    MyColor(int red, int green, int blue) {
        this.myRed = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return myRed;
    }

    public void setRed(int red) {
        this.myRed = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    @Override
    public MyColor clone() {
        return new MyColor(myRed, green, blue);
    }

    @Override
    public String toString() {
        return myRed + "," + green + "," + blue;
    }
}
