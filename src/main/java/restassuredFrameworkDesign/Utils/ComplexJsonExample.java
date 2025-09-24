package restassuredFrameworkDesign.Utils;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import restassuredFrameworkDesign.Payloads.Payloads;

/**
 * 1. Print No of courses returned by API
 * 
 * 2.Print Purchase Amount
 * 
 * 3. Print Title of the first course
 * 
 * 4. Print All course titles and their respective Prices
 * 
 * 5. Print no of copies sold by RPA Course
 * 
 * 6. Verify if Sum of all Course prices matches with Purchase Amount
 **/

public class ComplexJsonExample {
	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payloads.coursePrice());
		//1. Print No of courses returned by API
		System.out.println("Print No of courses returned by API"+js.getInt("courses.size()"));
		
		//2.Print Purchase Amount
		System.out.println("Print Purchase Amount : "+js.getInt("dashboard.purchaseAmount"));
		
		//3.Print Title of the first course
		System.out.println("Print Title of the first course : "+js.getString("courses[0].title"));
		
		//4.Print All course titles and their respective Prices
		for(int i=0;i<js.getInt("courses.size()");i++) {
			System.out.println("course titles "+js.getString("courses["+i+"].title"));
			System.out.println("course Price "+js.getString("courses["+i+"].price"));
		}
		String courseTitle="RPA";
		//5. Print no of copies sold by RPA Course
		for(int i=0;i<js.getInt("courses.size()");i++) {
			if(js.getString("courses["+i+"].title").equals(courseTitle)) {
				System.out.println("copies "+js.getString("courses["+i+"].copies"));
				break;
			}
		}
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount

		int totalAmount=0;
		for(int i=0;i<js.getInt("courses.size()");i++) {
			int copies=0;
			int copiesAmount=0;
			copies=js.getInt("courses["+i+"].copies");
			copiesAmount=js.getInt("courses["+i+"].price");
			totalAmount=totalAmount +(copies*copiesAmount);
		}
		
		Assert.assertEquals(js.getInt("dashboard.purchaseAmount"), totalAmount);
	}
}
