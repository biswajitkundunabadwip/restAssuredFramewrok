package restassuredFrameworkDesign.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;

public class Utillitys {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static String evaluateJsonFromString(String response,String fieldNameFromJson) {
		JsonPath jp = new JsonPath(response);
		return jp.getString(fieldNameFromJson);
	}
	
    /**
     * Reads a JSON file, updates the value of a specific field, 
     * and returns the updated JSON as a formatted String.
     * <p>
     * This method is useful when you want to dynamically change test data
     * at runtime in your RestAssured framework without modifying the original file permanently.
     * </p>
     *
     * @param filePath  the path to the JSON file (e.g., "src/test/resources/book.json")
     * @param fieldName the name of the JSON field to update (e.g., "author", "isbn", "aisle")
     * @param newValue  the new value to set for the given field
     * @return a String containing the updated JSON with pretty-print formatting
     *
     * @throws RuntimeException if the file cannot be read, parsed, 
     *                          or if the JSON structure is invalid
     *
     * <p><b>Example Usage:</b></p>
     * <pre>{@code
     * String updatedJson = JsonUtilsFile.updateJsonFromFile(
     *         "src/test/resources/book.json", "aisle", "999");
     * 
     * given()
     *     .header("Content-Type", "application/json")
     *     .body(updatedJson)
     * .when()
     *     .post("/addBook")
     * .then()
     *     .statusCode(200);
     * }</pre>
     */
    public static String updateJsonFromFile(String filePath, String fieldName, String newValue) {
        try {
            // Read JSON file into a String
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            // Convert JSON string to Map
            Map<String, Object> map = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});

            // Update field
            map.put(fieldName, newValue);

            // Convert back to JSON string
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        } catch (Exception e) {
            throw new RuntimeException("Error updating JSON file", e);
        }
    }

}
