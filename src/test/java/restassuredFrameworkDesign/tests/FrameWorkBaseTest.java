package restassuredFrameworkDesign.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

import restassuredFrameworkDesign.Payloads.Payloads;
import restassuredFrameworkDesign.Utils.TestBase;
import restassuredFrameworkDesign.Utils.URI;
import restassuredFrameworkDesign.Utils.Utillitys;

public class FrameWorkBaseTest extends TestBase{
	String placeId=null;
	String address="Kolkata,Salt Lake";
	
	@Test(groups = {"placeAPI",""})
	public void addPlaceApi() {
		String response=given()
				.log()
				.all()
				.spec(getRequestSpecification())
				.queryParam("key", "qaclick123")
				.header("Content-Type",prop.get("Content-Type"))
				.body(Payloads.addPlace("Kolkata"))
				.when().post(URI.POST_ADD_PLACE.getURI()).then().assertThat().statusCode(200)
				.body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.52 (Ubuntu)")
				.extract().response().asString();
		
		System.out.println("Response is : "+response);
		placeId=Utillitys.evaluateJsonFromString(response, "place_id");
	}
	
	@Test(dependsOnMethods = "addPlaceApi",groups = {"placeAPI"})
	public void updateAddress() {
		given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id ", placeId)
		.header("Content-Type", "application/json")
		.spec(getRequestSpecification())
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
	
	
	@Test(dependsOnMethods = "updateAddress",groups = {"placeAPI"})
	public void getAddress() {
		
		String getPlaceApiResponse=given().log().all()
		.queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.spec(getRequestSpecification())
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
	
	@Test(dependsOnMethods = "getAddress",groups = {"placeAPI"})
	public void deleteAddress() {
		given()
		.log()
		.all()
		.spec(getRequestSpecification())
		.queryParam("key", "qaclick123")
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
