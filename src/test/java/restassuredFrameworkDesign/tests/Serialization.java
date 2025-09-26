package restassuredFrameworkDesign.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import io.restassured.RestAssured;
import request.places.pojo.AddLocation;
import request.places.pojo.location;
import restassuredFrameworkDesign.Payloads.Payloads;

public class Serialization {
	public static void main(String[] args) {
		AddLocation addLocation= new AddLocation();
		ArrayList<String> myList = new ArrayList<>();
		location lc= new location();
		lc.setLat(3);
		lc.setLng(5);
		
		myList.add("shoe park");
		myList.add("shop");
		
		
		addLocation.setLocation(lc);
		addLocation.setAccuracy(50);
		addLocation.setName("BK");
		addLocation.setPhone_number("(+91) 983 893 3937");
		addLocation.setAddress("bangalore,india");
		
		addLocation.setTypes(myList);
		addLocation.setWebsite("http://google.com");
		addLocation.setLanguage("Hindi");
		
		
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(addLocation).when().post("/maps/api/place/add/json").then().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)").extract()
				.response().asString();

		System.out.println("Response is : " + response);
	}
}
