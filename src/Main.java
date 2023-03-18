import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();

        // Read in the points from positions.txt
        try {
            Scanner scanner = new Scanner(new File("positions.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                double x = Double.parseDouble(values[0]);
                double y = Double.parseDouble(values[1]);
                double z = Double.parseDouble(values[2]);
                Point point = new Point(x, y, z);
                points.add(point);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Point> filteredPoints = points.stream()
                .filter(point -> point.getZ() <= 2.0)
                .toList();

        List<Point> scaledPoints = filteredPoints.stream()
                .map(point -> new Point(point.getX() * 0.5, point.getY() * 0.5, point.getZ() * 0.5))
                .toList();

        List<Point> translatedPoints = scaledPoints.stream()
                .map(point -> new Point(point.getX() - 150.0, point.getY() - 37.0, point.getZ()))
                .toList();

        try {
            FileWriter writer = new FileWriter("drawMe.txt");

            for (Point point : translatedPoints) {
                String zFormatted = String.format("%.0f", point.getZ());
                writer.write(point.getX() + ", " + point.getY() + ", " + zFormatted + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
