package lt.itacademy.utils;

import java.util.Date;
import java.util.Random;

public class RandomUtils {

    public static String generateTimeStamp() {
        Date now = new Date();
        String timeStamp = "" + now.getTime();
        return timeStamp;
    }

    public static String getRandomName(){
        Random random = new Random();
        int nameLength = random.nextInt(10) + 4;
        String name = "";
        for (int i = 0; i < nameLength; i++){
            name = name + (char) (random.nextInt(26) + 97);
        }
        return name;
    }
}
