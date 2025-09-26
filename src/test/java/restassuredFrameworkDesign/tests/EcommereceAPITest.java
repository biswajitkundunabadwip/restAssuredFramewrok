package restassuredFrameworkDesign.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.ArrayList;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import request.ecommerce.pojo.LoginRequest;
import request.ecommerce.pojo.OrderDetails;
import request.ecommerce.pojo.OrderRequest;
import response.ecommerce.pojo.LoginResponse;
import restassuredFrameworkDesign.Utils.Utillitys;

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
		
		// delete  product
		
		RequestSpecification deleteOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
		.addHeader("Authorization", loginResponse.getToken()).addPathParam("productID", Utillitys.evaluateJsonFromString(responseCreateOrder, "productOrderId[0]")).build();
		
		given()
		.log()
		.all()
		.when()
		.spec(deleteOrder)
		.delete("/api/ecom/product/delete-product/{productID}")
		.then()
		.log()
		.all()
		.assertThat()
		.statusCode(200)
		.body("message", equalTo("Product Deleted Successfully"));
		
	}
}
