package restassuredFrameworkDesign.Utils;

import io.restassured.path.json.JsonPath;

public class Utillitys {

	public static String evaluateJsonFromString(String response,String fieldNameFromJson) {
		JsonPath jp = new JsonPath(response);
		return jp.getString(fieldNameFromJson);
	}
}
