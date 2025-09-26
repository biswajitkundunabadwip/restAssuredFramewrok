package restassuredFrameworkDesign.Utils;

import java.io.File;

public class ConfigPath {

    public static final File PROPERTIES_FILE = new File(System.getProperty("user.dir") 
            + "\\src\\test\\resources\\project-config.properties");
    
    public static final String ADD_PLACE_RESOURCE_URI="/maps/api/place/add/json";
    public static final String UPDATE_PLACE_RESOURCE_URI="/maps/api/place/update/json";
    public static final String GET_PLACE_RESOURCE_URI="/maps/api/place/get/json";
    public static final String DELETE_PACE_RESOURCE_URI="/maps/api/place/delete/json";
}
