package client.threads.ClientBuild.genericMaps;

import java.util.LinkedHashMap;
import java.util.Map;

public class GenericFriendsMap {
    

    private static GenericFriendsMap  instance;
    private static Map<String, String> genericMap = new LinkedHashMap<String, String>();

    private GenericFriendsMap (){

    }

    public static GenericFriendsMap  getInstance(){
        if(instance == null){
            instance = new GenericFriendsMap ();
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
