package restassuredFrameworkDesign.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import com.sun.org.apache.xml.internal.serializer.utils.Utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import restassuredFrameworkDesign.Payloads.Payloads;
import restassuredFrameworkDesign.Utils.Utillitys;

public class BaseTestStaticJsonPath {
	public static void main(String[] args) throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\main\\java\\restassuredFrameworkDesign\\Utils\\addPlace.json"))))
				.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.52 (Ubuntu)")
				.extract().response().asString();
		
		System.out.println("Response is : "+response);
		
		JsonPath jp = new JsonPath(response);
		String placeId=jp.getString("place_id");
		
		System.out.println("Place id is : "+placeId);
		String address="Kolkata,Baguiati";
		given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id ", placeId)
		.header("Content-Type", "application/json")
		.body(Payloads.updatePlace(placeId,address ))
		.when()
		.put("/maps/api/place/update/json")
		.then()
		.log()
		.all()
		.assertThat()
		.statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		String getPlaceApiResponse=given().log().all()
		.queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when()
		.get("/maps/api/place/get/json\r\n")
		.then()
		.log()
		.all()
		.assertThat()
		.statusCode(200)
		.body("address", equalTo(address)).extract().response().asString();
		
		
//		JsonPath jp1 = new JsonPath(getPlaceApiResponse);
		System.out.println(" Updated address is : "+Utillitys.evaluateJsonFromString(getPlaceApiResponse, "address"));
		Assert.assertEquals(Utillitys.evaluateJsonFromString(getPlaceApiResponse, "address"), address);
	}
}
