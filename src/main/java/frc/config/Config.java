package frc.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This class stores its configuration information in 1 file:
 * <p>
 * Deploy time config file, located in /home/lvuser/deploy/. Robot programs
 * don't have permission to write to this directory.
 * </p>
 */
public class Config {

    private static Map<String, String> map;

    /**
     * The name of the deploy time main config file in home/lvuser/deploy (e.g.
     * "omega2020.txt")
     */
    private static String deployConfigFileName;

    public static Map<String, String> getMap() {
        return map;
    }

    /** initialize the main map from the configuration files */
    public static void start() {
        map = new HashMap<String, String>();
        try {
            deployConfigFileName = "config.txt";
            // load deploy config file
            String directory = "./src/main/deploy";
            System.out.println("reading from deploy config file " + deployConfigFileName);
            loadFromFile(new File(directory, deployConfigFileName));
            System.out.println(map);
        } catch (FileNotFoundException e) {
            System.out.println("UNABLE TO FIND FILE");
            System.out.println(e.getMessage());
        }
    }

    /** load config values from the given file into the map */
    public static void loadFromFile(File f) throws FileNotFoundException {
        Scanner in = new Scanner(f);

        // add configs to map
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();

            if (line.length() > 0 && line.charAt(0) != '#') {
                String[] splitted = line.split("=", 2);
                if (splitted.length == 2)
                    map.put(splitted[0].trim(), splitted[1].trim());
            }
        }
        in.close();
    }

    /**
     * Get the boolean config value corresponding to the key passed in.
     * 
     * @return The corresponding boolean value, or false if the key was invalid
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(map.get(key));
    }

    /**
     * Get the double config value corresponding to the key passed in.
     * 
     * @return The corresponding double value, or 0.0 if the key was invalid
     */
    public static double getDouble(String key) {
        return Double.parseDouble(map.get(key));
    }

    /**
     * Get the int config value corresponding to the key passed in.
     * 
     * @return The corresponding integer value, or -1 if the key was not
     *         found/invalid
     */
    public static int getInt(String key) {
        return Integer.parseInt(map.get(key));
    }

    /**
     * Get the string value corresponding to the key passed in.
     * 
     * @return The corresponding string value, or the empty string if the key was
     *         not found/invalid
     */
    public static String getString(String key) {
        return map.get(key);
    }
}
