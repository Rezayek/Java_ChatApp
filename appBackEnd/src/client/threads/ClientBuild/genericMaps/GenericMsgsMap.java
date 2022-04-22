package client.threads.ClientBuild.genericMaps;

import java.util.LinkedHashMap;
import java.util.Map;

public class GenericMsgsMap {
    

    private static GenericMsgsMap  instance;
    private static Map<String, String> genericMap = new LinkedHashMap<String, String>();

    private GenericMsgsMap (){

    }

    public static GenericMsgsMap  getInstance(){
        if(instance == null){
            instance = new GenericMsgsMap ();
        }
        return instance;
    }

    public Map<String, String> getMap(){
        return genericMap;
    }

    public void setGenericMap(Map<String, String> genericMap){
        this.genericMap = genericMap;
    }
}
