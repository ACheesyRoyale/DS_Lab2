package be.uantwerpen.fti.ds.lab2.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// https://crunchify.com/java-how-to-parse-jsonobject-and-jsonarrays/
public class JSONHelper {
    public static List<String> getAllValuesForGivenKey(String jsonString, String key) {
        try {
            List<String> values = new ArrayList<>();
            // Parse the JSON string into a JSONArray
            JSONArray jsonArray = new JSONArray(jsonString);

            // Iterate over each JSON object in the array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Retrieve the value associated with the specified key
                String value = jsonObject.optString(key, "Key not found");
                values.add(value);
            }
            return values;
        }
        catch (Exception e) {
            // Handle JSON parsing errors
            throw new RuntimeException();
        }
    }
}
