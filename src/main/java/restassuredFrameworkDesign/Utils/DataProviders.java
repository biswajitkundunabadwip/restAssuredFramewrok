package restassuredFrameworkDesign.Utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "getAddPlaceData")
	public Object[][] getAddPlaceData() {
		
		return new Object[][] {
			{1,2},
			{3,4}
		};
	}
	
	@DataProvider(name = "getUpdatePlaceData")
	public Object[][] getUpdatePlaceData() {
		
		return new Object[][] {
			{1,2},
			{3,4}
		};
	}
	
}
