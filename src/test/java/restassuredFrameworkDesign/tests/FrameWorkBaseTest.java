package restassuredFrameworkDesign.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import javax.swing.text.Utilities;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.internal.annotations.TestAnnotation;

import com.sun.org.apache.xml.internal.serializer.utils.Utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import restassuredFrameworkDesign.Payloads.Payloads;
import restassuredFrameworkDesign.Utils.TestBase;
import restassuredFrameworkDesign.Utils.URI;
import restassuredFrameworkDesign.Utils.Utillitys;

public class FrameWorkBaseTest extends TestBase{
	String placeId=null;
	String address="Kolkata,Salt Lake";
	@Test
	public void addPlaceApi() {
		String response=given()
				.log()
				.all()
				.spec(getRequestSpecification())
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(Payloads.addPlace("Kolkata"))
				.when().post(URI.POST_ADD_PLACE.getURI()).then().assertThat().statusCode(200)
				.body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.52 (Ubuntu)")
				.extract().response().asString();
		
		System.out.println("Response is : "+response);
		placeId=Utillitys.evaluateJsonFromString(response, "place_id");
	}
	
	@Test(dependsOnMethods = "addPlaceApi")
	public void updateAddress() {
		String address="Kolkata,Baguiati";
		given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id ", placeId)
		.header("Content-Type", "application/json")
		.body(Payloads.updatePlace(placeId,address ))
		.when()
		.put(URI.PUT_UPDATE_PLACE.getURI())
		.then()
		.log()
		.all()
		.assertThat()
		.statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
	}
	
	
	@Test(dependsOnMethods = "updateAddress")
	public void getAddress() {
		
		String getPlaceApiResponse=given().log().all()
		.queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
	.when()
		.get(URI.GET_PLACE.getURI())
		.then()
		.log()
		.all()
		.assertThat()
		.statusCode(200)
		.body("address", equalTo(address)).extract().response().asString();
		
		System.out.println(" Updated address is : "+Utillitys.evaluateJsonFromString(getPlaceApiResponse, "address"));
		Assert.assertEquals(Utillitys.evaluateJsonFromString(getPlaceApiResponse, "address"), address);
	}
	
	@Test(dependsOnMethods = "getAddress")
	public void deleteAddress() {
		given()
		.log()
		.all()
		.spec(getRequestSpecification())
		.queryParam("key", "qaclick123")
		.pathParam(placeId, placeId)
		.body(Payloads.deletePlaceApi(placeId))
		.when()
		.delete(URI.DELETE_UPDATE_PLACE.getURI())
		.then()
		.log()
		.all()
		.assertThat()
		.statusCode(200)
		.body("status", equalTo("OK"));
		
	}
}
