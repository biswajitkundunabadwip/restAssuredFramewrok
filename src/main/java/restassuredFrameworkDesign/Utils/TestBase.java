package restassuredFrameworkDesign.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class TestBase {

	private FileInputStream fis;
	public Properties prop;
	private RequestSpecification requestSpecification;

	@BeforeMethod
	public void init() throws FileNotFoundException {
		fis = new FileInputStream(ConfigPath.PROPERTIES_FILE);
		prop = new Properties();
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Formed url is : "+ prop.getProperty("baseURI"));
		setRequestSpecification(new RequestSpecBuilder().setBaseUri(prop.getProperty("baseURI"))
				.build());
	}

	@AfterMethod
	public void tearDown() {
		
	}

	public RequestSpecification getRequestSpecification() {
		return requestSpecification;
	}

	public void setRequestSpecification(RequestSpecification requestSpecification) {
		this.requestSpecification = requestSpecification;
	}


}
