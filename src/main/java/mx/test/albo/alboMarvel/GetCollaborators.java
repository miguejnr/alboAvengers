package mx.test.albo.alboMarvel;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;

public class GetCollaborators {
	
	MongoClient mongoClient = MongoClients.create("mongodb+srv://alboMarvel:albo09871234@marvelcluster.pdwn2.mongodb.net/sample_analytics?retryWrites=true&w=majority");
	MongoDatabase database = mongoClient.getDatabase("colaborators");
	
	public String info(String name){
		MongoCollection<Document> collaborator = database.getCollection(name);
		Document doc = collaborator.find(new Document()).projection(Projections.include("last_sync")).first();
		String strdate = doc.getString("last_sync");
		UpdateInfo upd = new UpdateInfo();
		
		if(upd.isUpdated(strdate)) {
			Document res = collaborator.find(new Document()).projection(Projections.excludeId()).first();
			return res.toJson();
		}
		else {
			upd.UpdateCollaboratorsData();
			Document res = collaborator.find(new Document()).projection(Projections.excludeId()).first();
			return res.toJson();
		}
	}
}

