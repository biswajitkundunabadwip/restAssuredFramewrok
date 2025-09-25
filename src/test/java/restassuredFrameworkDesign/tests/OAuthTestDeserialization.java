package restassuredFrameworkDesign.tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.GetCourseResponse;

public class OAuthTestDeserialization {



	public static void main(String[] args) {
		RestAssured.baseURI="";
		
		String generateTokenResponse=given()
				.log()
				.all()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

		        .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

		        .formParams("grant_type", "client_credentials")

		        .formParams("scope", "trust")
		        .when()
		        .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		        .then()
		        .log()
		        .all()
		        .assertThat()
		        .statusCode(200).extract().response().asString();
				
				JsonPath jp = new JsonPath(generateTokenResponse);
				String access_token=jp.getString("access_token");
				
				System.out.println("the extracted access token is : "+access_token);
				
				
				
				GetCourseResponse gc = given()
				.log()
				.all()
				.queryParam("access_token", access_token)
				.when()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
				.as(GetCourseResponse.class);
				
				System.out.println(gc.getInstructor());

				int webAutomationSize = gc.getCourses().getWebAutomation().size();
				String [] courseTitles= {"Selenium Webdriver Java","Cypress","Protractor"};
				ArrayList<String> getCourseArrayList = new ArrayList<>();
				for(int i=0;i<webAutomationSize;i++) {
					System.out.println(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
//					System.out.println(gc.getCourses().getWebAutomation().get(i).getPrice());
					
					
					getCourseArrayList.add(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
					
					
				}
				List<String> expectedList = Arrays.asList(courseTitles);
				
				Assert.assertTrue(getCourseArrayList.equals(expectedList));
				
	}
	
}
