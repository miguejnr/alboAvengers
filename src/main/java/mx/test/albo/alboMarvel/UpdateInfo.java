package mx.test.albo.alboMarvel;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import us.monoid.web.Resty;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


public class UpdateInfo {
	
	MongoClient mongoClient = MongoClients.create("mongodb+srv://alboMarvel:albo09871234@marvelcluster.pdwn2.mongodb.net/sample_analytics?retryWrites=true&w=majority");
	String publicKey = "5fd1b78a26a921f1efc193efbe4a99cb";
    String privateKey = "d974763326ea7c02a19ecb4ad2a1571e7b682252";
    long timeStamp = System.currentTimeMillis();
    int limit = 20;
    String stringToHash = timeStamp + privateKey + publicKey;
    String hash = DigestUtils.md5Hex(stringToHash);
    Date today = new Date();  
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String dateUpd = dateFormat.format(today);

	
	public boolean isUpdated(String strdate) {
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date;
		try {
			date = format.parse(strdate);
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			
		    if(fmt.format(date).equals(fmt.format(today))){
		    	return true;
		    }
		    else {
		    	return false;
		    }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public void updateCharactersData() {
		
        //CHARACTERS CAP AMERICA
		MongoDatabase database = mongoClient.getDatabase("characters");
        Hashtable<String, String> charactersUpdated = new Hashtable<String, String>();
        String url = String.format("http://gateway.marvel.com/v1/public/characters/1009220/comics?ts=%d&apikey=%s&hash=%s&limit=%d", timeStamp, publicKey, hash, limit);
        String output;
		try {
			output = new Resty().text(url).toString();
			JSONParser parser = new JSONParser();
			try {
				JSONObject json = (JSONObject) parser.parse(output);
				JSONObject data = (JSONObject) json.get("data");
				JSONArray res = (JSONArray) data.get("results");
				Iterator i = res.iterator();
				while(i.hasNext()) {
					JSONObject singleres = (JSONObject) i.next();
					String title = (String)singleres.get("title");
					JSONObject characters = (JSONObject) singleres.get("characters");
					JSONArray items = (JSONArray) characters.get("items");
					Iterator c = items.iterator();
					while(c.hasNext()) {
						JSONObject charac =(JSONObject) c.next();
						String name = (String)charac.get("name");
						if(charactersUpdated.get(name)==null) {
							charactersUpdated.put(name, "\"" + title + "\"" );							
						}
						else {
							charactersUpdated.put(name, charactersUpdated.get(name) + ",\"" + title + "\"");							
						}
					}
				}
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MongoCollection<Document> character = database.getCollection("capamerica");
        String charactersUpd = "[";
        Set<String> keys = charactersUpdated.keySet();
        for(String key: keys){
            charactersUpd += "{character:\"" + key + "\", Comics:[" + charactersUpdated.get(key) + "]},";
        }
        
        charactersUpd += "]";
		character.updateOne(eq("_id", new ObjectId("603eaad778736122db1bd9b1")), combine(set("last_sync",dateUpd), set("characters",charactersUpd)));
		
		
		//IRONMAN CHARACTERS UPDATE
		
		Hashtable<String, String> charactersUpdated2 = new Hashtable<String, String>();
        String url2 = String.format("http://gateway.marvel.com/v1/public/characters/1009368/comics?ts=%d&apikey=%s&hash=%s&limit=%d", timeStamp, publicKey, hash, limit);
        String output2;
		try {
			output2 = new Resty().text(url2).toString();
			JSONParser parser = new JSONParser();
			try {
				JSONObject json2 = (JSONObject) parser.parse(output2);
				JSONObject data2 = (JSONObject) json2.get("data");
				JSONArray res2 = (JSONArray) data2.get("results");
				Iterator i = res2.iterator();
				while(i.hasNext()) {
					JSONObject singleres2 = (JSONObject) i.next();
					String title2 = (String)singleres2.get("title");
					JSONObject characters2 = (JSONObject) singleres2.get("characters");
					JSONArray items2 = (JSONArray) characters2.get("items");
					Iterator c = items2.iterator();
					while(c.hasNext()) {
						JSONObject charac2 =(JSONObject) c.next();
						String name2 = (String)charac2.get("name");
						if(charactersUpdated2.get(name2)==null) {
							charactersUpdated2.put(name2, "\"" + title2 + "\"" );							
						}
						else {
							charactersUpdated2.put(name2, charactersUpdated2.get(name2) + ",\"" + title2 + "\"");							
						}
					}
				}
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MongoCollection<Document> character2 = database.getCollection("ironman");
        String charactersUpd2 = "[";
        Set<String> keys2 = charactersUpdated2.keySet();
        for(String key: keys2){
            charactersUpd2 += "{character:\"" + key + "\", Comics:[" + charactersUpdated2.get(key) + "]},";
        }
        
        charactersUpd2 += "]";
		character2.updateOne(eq("_id", new ObjectId("603eabb378736122db1bd9b2")), combine(set("last_sync",dateUpd), set("characters",charactersUpd2)));
		
		
	}
	
	public void UpdateCollaboratorsData() {
		
		MongoDatabase database = mongoClient.getDatabase("colaborators");

        //COLLABORATORS CAP AMERICA
        
        Hashtable<String, String> editorsUp = new Hashtable<String, String>();
        Hashtable<String, String> writersUp = new Hashtable<String, String>();
        Hashtable<String, String> coloristsUp = new Hashtable<String, String>();

        String url = String.format("http://gateway.marvel.com/v1/public/characters/1009220/comics?ts=%d&apikey=%s&hash=%s&limit=%d", timeStamp, publicKey, hash, limit);
        String output;
		try {
			output = new Resty().text(url).toString();
			JSONParser parser = new JSONParser();
			try {
				JSONObject json = (JSONObject) parser.parse(output);
				JSONObject data = (JSONObject) json.get("data");
				JSONArray res = (JSONArray) data.get("results");
				Iterator i = res.iterator();
				while(i.hasNext()) {
					JSONObject singleres = (JSONObject) i.next();
					JSONObject creators = (JSONObject) singleres.get("creators");
					JSONArray items = (JSONArray) creators.get("items");
					Iterator c = items.iterator();
					while(c.hasNext()) {
						JSONObject creat =(JSONObject) c.next();
						String name = (String)creat.get("name");
						String role = (String)creat.get("role");
						if(role.equals("editor")) {
							editorsUp.put(name, "");
						}
						else if(role.equals("writer")) {
							writersUp.put(name, "");
						}
						else if(role.equals("colorist")) {
							coloristsUp.put(name, "");
						}
					}
				}
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MongoCollection<Document> collaborator = database.getCollection("capamerica");
		
        String editors = "[";
        String writers = "[";
        String colorists = "[";

        Set<String> editorskeys = editorsUp.keySet();
        for(String key: editorskeys){
            editors += "\"" + key + "\",";
        }
        editors += "]";
        
        Set<String> writerskeys = writersUp.keySet();
        for(String key: writerskeys){
            writers += "\"" + key + "\",";
        }
        writers += "]";
        
        Set<String> coloristskeys = coloristsUp.keySet();
        for(String key: coloristskeys){
            colorists += "\"" + key + "\",";
        }
        colorists += "]";
        
        System.out.print(editors);
        System.out.print(writers);
        System.out.print(colorists);

        
		collaborator.updateOne(eq("_id", new ObjectId("603eaa0078736122db1bd9af")), combine(set("last_sync",dateUpd), set("editors",editors), set("writers",writers), set("colorists",colorists)));
		
		
		//COLLABORATORS IRONMAN UPDATE
		
        
        Hashtable<String, String> editorsUp2 = new Hashtable<String, String>();
        Hashtable<String, String> writersUp2 = new Hashtable<String, String>();
        Hashtable<String, String> coloristsUp2 = new Hashtable<String, String>();

        String url2 = String.format("http://gateway.marvel.com/v1/public/characters/1009368/comics?ts=%d&apikey=%s&hash=%s&limit=%d", timeStamp, publicKey, hash, limit);
        String output2;
		try {
			output2 = new Resty().text(url2).toString();
			JSONParser parser2 = new JSONParser();
			try {
				JSONObject json2 = (JSONObject) parser2.parse(output2);
				JSONObject data2 = (JSONObject) json2.get("data");
				JSONArray res2 = (JSONArray) data2.get("results");
				Iterator i = res2.iterator();
				while(i.hasNext()) {
					JSONObject singleres = (JSONObject) i.next();
					JSONObject creators = (JSONObject) singleres.get("creators");
					JSONArray items = (JSONArray) creators.get("items");
					Iterator c = items.iterator();
					while(c.hasNext()) {
						JSONObject creat =(JSONObject) c.next();
						String name = (String)creat.get("name");
						String role = (String)creat.get("role");
						if(role.equals("editor")) {
							editorsUp2.put(name, "");
						}
						else if(role.equals("writer")) {
							writersUp2.put(name, "");
						}
						else if(role.equals("colorist")) {
							coloristsUp2.put(name, "");
						}
					}
				}
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MongoCollection<Document> collaborator2 = database.getCollection("ironman");
		
        String editors2 = "[";
        String writers2 = "[";
        String colorists2 = "[";

        Set<String> editorskeys2 = editorsUp2.keySet();
        for(String key: editorskeys2){
            editors2 += "\"" + key + "\",";
        }
        editors2 += "]";
        
        Set<String> writerskeys2 = writersUp2.keySet();
        for(String key: writerskeys2){
            writers2 += "\"" + key + "\",";
        }
        writers2 += "]";
        
        Set<String> coloristskeys2 = coloristsUp2.keySet();
        for(String key: coloristskeys2){
            colorists2 += "\"" + key + "\",";
        }
        colorists2 += "]";
  
		collaborator2.updateOne(eq("_id", new ObjectId("603eaa6978736122db1bd9b0")), combine(set("last_sync",dateUpd), set("editors",editors2), set("writers",writers2), set("colorists",colorists2)));
		
	}

}
