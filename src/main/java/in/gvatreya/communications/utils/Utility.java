package in.gvatreya.communications.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utility {

    public static String printObject(Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }
}
