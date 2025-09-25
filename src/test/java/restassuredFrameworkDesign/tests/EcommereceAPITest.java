package restassuredFrameworkDesign.tests;

import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import request.ecommerce.pojo.LoginRequest;
import request.ecommerce.pojo.LoginResponse;
import request.ecommerce.pojo.OrderDetails;
import request.ecommerce.pojo.OrderRequest;
import restassuredFrameworkDesign.Utils.Utillitys;

import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;

public class EcommereceAPITest {

	public static void main(String[] args) {
		RequestSpecification reqSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest= new LoginRequest();
		loginRequest.setUserEmail("postman@gmail.com");
		loginRequest.setUserPassword("Hello123@");
		
		
		LoginResponse loginResponse = given()
		.log()
		.all()
		.spec(reqSpecification)
		.body(loginRequest)
		.when()
		.post("/api/ecom/auth/login")
		.then()
		.extract()
		.response()
		.as(LoginResponse.class);
		
		System.out.println(loginResponse.getToken());
		
		//Add product
		RequestSpecification addProductReqSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addHeader("Authorization", loginResponse.getToken()).build();
		
		
		
		
		String productId=given()
		.log()
		.all()
		.spec(addProductReqSpecification)
		.formParam("productName", "Armani")
		.formParam("productAddedBy", "6274b798e26b7e1a10ea0660")
		.formParam("productCategory", "fashion")
		.formParam("productSubCategory", "shirts")
		.formParam("productPrice", "11500")
		.formParam("productDescription", "Armani Originals1")
		.formParam("productFor", "women")
		.multiPart("productImage",new File("C:\\Users\\biswa\\OneDrive\\Desktop\\armani.png"))
		.when()
		.post("/api/ecom/product/add-product")
		.then()
		.log()
		.all()
		.assertThat()
		.statusCode(201).extract().response().asString();
		
		String productIdFromApiResponse=Utillitys.evaluateJsonFromString(productId, "productId");
		
		/*
		 * Create order
		 */
		RequestSpecification createOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
		.addHeader("Authorization", loginResponse.getToken()).build();
		
		OrderDetails orderDetails= new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productIdFromApiResponse);
		
		ArrayList<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		orderDetailsList.add(orderDetails);
		
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrders(orderDetailsList);
		
		String responseCreateOrder=given()
		.log()
		.all()
		.spec(createOrder)
		.body(orderRequest)
		.when()
		.post("/api/ecom/order/create-order")
		.then()
		.log()
		.all()
		.assertThat()
		.statusCode(201)
		.extract().response().asString();
		
	}
}
