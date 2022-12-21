package profiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ProfileProcessor {
    public static String readFile(String path, Charset encoding) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), encoding);
        return String.join(System.lineSeparator(), lines);
    }

    public static JSONObject fromXmlToJson(){
        JSONObject xmlJSONObj = null;
        try {
            FileInputStream fis = null;
            String xmlContent = readFile("C:\\Users\\khaou\\M2-GL\\Evolution-Restruturation-des-logiciels\\TP5\\TP-Tracability\\Repository.log", StandardCharsets.UTF_8);
            xmlJSONObj= XML.toJSONObject(xmlContent);
        } catch (JSONException je) {
            System.out.println(je.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return xmlJSONObj;
    }
    public static void getRecords(){
        Map<String,List<String>> profiles=new HashMap<>();
        List<String> addUserList=new ArrayList<>();
        List<String> fetchUserList=new ArrayList<>();
        List<String> fetchExpensiveUserList=new ArrayList<>();
        List<String> displayUserList=new ArrayList<>();
        JSONObject xmlJSONObj=fromXmlToJson();
        JSONObject log=xmlJSONObj.getJSONObject("log");
        JSONArray arr = log.getJSONArray("record");
        for (int i = 0; i < arr.length(); i++){
            String method = arr.getJSONObject(i).getString("method");
            String message = arr.getJSONObject(i).getString("message");
            if(message.charAt(11)=='0'){
                continue;
            }else{
                if(method.equals("addProduct") || method.equals("updateProduct") || method.equals("deleteProductByID")){
                    addUserList.add(String.valueOf(message.charAt(11)));
                }else if(method.equals("fetchProductByID") || method.equals("displayAllProducts")){
                    fetchUserList.add(String.valueOf(message.charAt(11)));
                }else if(method.equals("fetchExpensiveProducts")){
                    fetchExpensiveUserList.add(String.valueOf(message.charAt(11)));
                }
            }

        }
        Map<String,Integer> writeUserProfile=countFrequencies(addUserList);
        String topWriteUserProfile=topUserProfile(writeUserProfile);
        String topWriteUser=readmessage(topWriteUserProfile);
        Map<String,Integer> readUserProfile=countFrequencies(fetchUserList);
        String topReadUserProfile=topUserProfile(readUserProfile);
        String topReadUser=readmessage(topReadUserProfile);
        Map<String,Integer> expensiveUserProfile=countFrequencies(fetchExpensiveUserList);
        String topExpensiveUserProfile=topUserProfile(expensiveUserProfile);
        String topExpensiveUser=readmessage(topExpensiveUserProfile);




        System.out.println("L'utilisateur qui a fait le plus d'opérations d'écriture :"+topWriteUser);
        System.out.println("L'utilisateur qui a fait le plus d'opérations de lecture :"+topReadUser);
        System.out.println("L'utilisateur qui a cherché le plus les produits chers :"+topExpensiveUser);


    }
    public static Map<String,Integer> countFrequencies(List<String> list){
        Set<String> set=new HashSet<>(list);
        Map<String,Integer> userProfile=new HashMap<>();
        for (String s : set){
            userProfile.put(s,Collections.frequency(list, s));
        }
        return userProfile;
    }
    public static String topUserProfile(Map<String,Integer> userProfile){
        String topUserProfile="";
        Iterator<Map.Entry<String, Integer>> i = userProfile.entrySet().iterator();
        if(userProfile.size()==1){
            topUserProfile=userProfile.entrySet().iterator().next().getKey();
        }else{
            while (i.hasNext()) {
                Map.Entry<String,Integer> next = i.next();
                i.remove();
                for (Map.Entry<String,Integer> e : userProfile.entrySet()) {
                    if(e.getValue() < next.getValue()){
                        topUserProfile=next.getKey();
                    }else {
                        topUserProfile=e.getKey();
                    }
                }
            }
        }

        return topUserProfile;
    }
    public static String readmessage(String userID){
        JSONObject xmlJSONObj=fromXmlToJson();
        JSONObject log=xmlJSONObj.getJSONObject("log");
        JSONArray arr = log.getJSONArray("record");
        String msg="";
        for (int i = 0; i < arr.length(); i++) {
            String message = arr.getJSONObject(i).getString("message");
            if(String.valueOf(message.charAt(11)).equals(userID)){
                msg=message;
                return msg;
            }
        }
        return msg;
    }
}
