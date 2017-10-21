import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ShapeTypeAdapter extends TypeAdapter<Shape> {


    @Override
    public void write(JsonWriter writer, Shape shape) throws IOException {
        writer.beginObject();
        writer.name("type").value(shape.getClass().getName());
        writer.name("x").value(shape.getCenterX());
        writer.name("y").value(shape.getCenterY());
        MyColor color = shape.getColor();
        writer.name("color").value(color.toString());
        writer.endObject();
    }

    @Override
    public Shape read(JsonReader reader) throws IOException {
        reader.beginObject();

        Shape shape = null;
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "type":
                    String type = reader.nextString();
                    switch (type) {
                        case "Rect":
                            shape = new Rect();
                            break;
                        case "Circle":
                            shape = new Circle();
                            break;
                        case "Triangle":
                            shape = new Triangle();
                            break;
                    }
                    break;
                case "x":
                    if (shape != null) {
                        shape.setCenterX(reader.nextInt());
                    }
                    break;
                case "y":
                    if (shape != null) {
                        shape.setCenterY(reader.nextInt());
                    }
                    break;
                case "color":
                    if (shape != null) {
                        int[] rgb = new int[3];
                        String[] strings = reader.nextString().split(",");
                        int i = 0;
                        for(String s :strings){
                            rgb[i]=Integer.parseInt(s);//Exception in this line
                            i++;
                        }
                        MyColor color = new MyColor(rgb[0], rgb[1], rgb[2]);
                        shape.setColor(color);
                        System.out.println(color.toString());
                    }
                    break;
            }
        }
        reader.endObject();
        return shape;
    }
}
