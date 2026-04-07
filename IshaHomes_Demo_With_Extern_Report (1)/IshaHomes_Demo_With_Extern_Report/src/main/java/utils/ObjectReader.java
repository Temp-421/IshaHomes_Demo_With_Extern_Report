package utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ObjectReader
{
    private  static Properties properties;
    static{
        try
        {
            FileInputStream fis=new FileInputStream("C:\\Users\\2478588\\OneDrive - Cognizant\\Desktop\\Interim_Project\\IshaHomes_Demo\\src\\main\\resources\\ObjectRepository");
            properties=new Properties();
            properties.load(fis);
        }
        catch (IOException e){
            throw new RuntimeException("Could not load Object.properties : " + e.getMessage());
        }
    }
    public  static String get(String key)
    {
        String value=properties.getProperty(key);
        if(value==null)
        {
            throw new RuntimeException("Locator not found for key : "+key);
        }
        return value;
    }
}
