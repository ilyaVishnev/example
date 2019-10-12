package ru;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MyTriangle {
    
    public static File out = new File("out.txt");
    public static String text = "";
    public static Boolean maxAlreadyExist = false;

    public static void main(String[] args) {
        if (args.length != 2) {
            text = "the line of command is not correct";
        } else {
            String fileIn = args[0];
            String fileOut = args[1];
            if (fileOut.endsWith(".txt") && fileIn.endsWith(".txt")) {
                findMaxSquare(fileIn, fileOut);
                out = new File(fileOut);
            } else {
                text = "file extensions are not correct";
            }
        }
        try (FileWriter writer = new FileWriter(out)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void findMaxSquare(String fileIn, String fileOut) {
        int[] max = {0, 0, 0, 0, 0, 0};
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileIn), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int[] triangle = Arrays.asList(line.split(" ")).stream().mapToInt(Integer::parseInt).toArray();
                    if (triangle.length == 6) {
                        max = compareSqueres(triangle, max);
                    } else {
                        text += "some of the lines didn't contained a sufficient number of coordinates\n";
                    }
                } catch (Exception ex) {
                    text += "some lines didn't contain numbers\n";
                }
            }
            if (!maxAlreadyExist) {
                String result = Arrays.toString(max);
                text = text + result.substring(1, result.length() - 1).replace(",", "");
            }
        } catch (IOException ex) {
            text = "the name of the incoming file is incorrect";
        }
    }

    public static int[] compareSqueres(int[] triangle, int[] max) {
        double square = getSquere(triangle);
        double maxSquare = getSquere(max);
        if (square > maxSquare) {
            maxAlreadyExist = false;
            return triangle;
        } else if (square == maxSquare) {
            maxAlreadyExist = true;
        }
        return max;
    }

    public static double getSquere(int[] triangle) {
        double d = 0;
        double h = 0;
        double a = Math.pow(Math.pow(Math.abs(triangle[0] - triangle[2]), 2) + Math.pow(Math.abs(triangle[1] - triangle[3]), 2), 0.5);
        double b = Math.pow(Math.pow(Math.abs(triangle[3] - triangle[5]), 2) + Math.pow(Math.abs(triangle[2] - triangle[4]), 2), 0.5);
        double c = Math.pow(Math.pow(Math.abs(triangle[4] - triangle[0]), 2) + Math.pow(Math.abs(triangle[5] - triangle[1]), 2), 0.5);
        if (a == b) {
            d = c;
            h = Math.pow(Math.pow(a, 2) - Math.pow(d, 2) / 4, 0.5);
        }
        if (b == c) {
            d = a;
            h = Math.pow(Math.pow(b, 2) - Math.pow(d, 2) / 4, 0.5);
        }
        if (a == c) {
            d = b;
            h = Math.pow(Math.pow(c, 2) - Math.pow(d, 2) / 4, 0.5);
        }
        return 0.5 * d * h;
    }
}
