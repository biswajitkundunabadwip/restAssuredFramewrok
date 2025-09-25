package restassuredFrameworkDesign.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import restassuredFrameworkDesign.Payloads.Payloads;
import restassuredFrameworkDesign.Utils.Utillitys;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LibrarayTests {

	String bookID = null;
	String authorName = "biswajit";

	@BeforeMethod
	public void init() {
		RestAssured.baseURI = "http://216.10.245.166";
	}

	@Test
	public void addBook() {
		String bookAddResponse = given().header("Content-Type", "application/json")
				.body(Payloads.addBookApi("BK", "100", authorName)).when().post("Library/Addbook.php").then()
				.assertThat().log().all().statusCode(200).body("Msg", equalTo("successfully added")).extract()
				.response().asString();

		bookID = Utillitys.evaluateJsonFromString(bookAddResponse, "ID");

		System.out.println("book id is : " + bookID);

	}

	@Test
	public void getBook() {
		given()
		.log()
		.all()
		.header("Content-Type", "application/json")
		.queryParam("AuthorName", authorName)
		.when()
		.get("/Library/GetBook.php").then()
		.log()
		.all()
		.assertThat().statusCode(200);
	}

}
