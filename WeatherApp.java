import javax.swing.*;
import java.awt.*;
import org.json.JSONObject;

public class WeatherApp {
    private JFrame frame;
    private JTextField cityField;
    private JLabel temperatureLabel;
    private JLabel humidityLabel;
    private JLabel rainLabel;

    public WeatherApp() {
        frame = new JFrame("Weather Forecast");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        cityField = new JTextField();
        JButton fetchButton = new JButton("Get Weather");
        fetchButton.addActionListener(e -> fetchWeather());

        temperatureLabel = new JLabel("Temperature: ");
        humidityLabel = new JLabel("Humidity: ");
        rainLabel = new JLabel("Raining Chances: ");

        frame.add(new JLabel("Enter City: "));
        frame.add(cityField);
        frame.add(fetchButton);
        frame.add(temperatureLabel);
        frame.add(humidityLabel);
        frame.add(rainLabel);

        frame.setVisible(true);
    }

    private void fetchWeather() {
        String city = cityField.getText();
        JSONObject weatherData = WeatherService.getWeather(city);
        if (weatherData != null) {
            JSONObject main = weatherData.getJSONObject("main");
            JSONObject weather = weatherData.getJSONArray("weather").getJSONObject(0);

            temperatureLabel.setText("Temperature: " + main.getDouble("temp") + "°C");
            humidityLabel.setText("Humidity: " + main.getInt("humidity") + "%");
            rainLabel.setText("Raining Chances: " + weather.getString("description"));
        } else {
            temperatureLabel.setText("Temperature: ");
            humidityLabel.setText("Humidity: ");
            rainLabel.setText("Raining Chances: ");
            JOptionPane.showMessageDialog(frame, "City not found");
        }
    }

    public static void main(String[] args) {
        new WeatherApp();
    }
}
