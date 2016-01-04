import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherData {
    private ArrayList<Double> data;
    private Color color;
    private String title;
    private String name;
    private String yLabel;

    public WeatherData(char c) {
        name(c);
        this.data = new ArrayList<Double>();
    }

    public WeatherData(char c, ArrayList<String> d) {
        name(c);
        this.data = new ArrayList<Double>(d.size());
        d.forEach(el -> this.data.add(Double.parseDouble(el)));
    }

    @SuppressWarnings("unused")
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public Double getData(int i) {
        return this.data.get(i);
    }

    public ArrayList<Double> getData() {
        return this.data;
    }

    public void setData(int i, String d) {
        this.data.set(i, new Double(d));
    }

    public void addData(String d) {
        this.data.add(new Double(d));
    }

    public void removeData(int i) {
        this.data.remove(i);
    }

    public int getSize() {
        return this.data.size();
    }

    public void name(char c) {
        if (c == 'u') {
            color = Color.BLACK;
            name = "Невідомо";
            title = "Невідомо";
            yLabel = "Невідомо";
        }
        if (c == 'h') {
            color = Color.GREEN;
            name = "Вологість";
            title = "Графік вологості";
            yLabel = "Вологість,%";
        }
        if (c == 't') {
            color = Color.RED;
            name = "Температура";
            title = "Графік температури";
            yLabel = "Температура,°С";
        }
        if (c == 'p') {
            color = Color.BLUE;
            name = "Тиск";
            title = "Графік тиску";
            yLabel = "Тиск, мм р.с.";
        }
    }

    public Color getColor() {
        return this.color;
    }

    public String getTitle() {
        return this.title;
    }

    public String getName() {
        return this.name;
    }

    public String getYLabel() {
        return this.yLabel;
    }

}
