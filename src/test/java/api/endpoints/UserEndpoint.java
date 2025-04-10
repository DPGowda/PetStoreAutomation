package api.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import api.payload.User;


//UserEndPints. java
// Created for perform Create, Read, Update, Delete requests the user API.

public class UserEndpoint {

	public static Response CreateUser(User payload){
		
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when().post(Routes.post_url);
		
		return response;
		
	}
	
	public static Response ReadUser(String username){
			
			Response response = given()
				.pathParam("username",username)
				.accept(ContentType.JSON)
			.when().get(Routes.get_url);
			
			return response;
	}
	
	public static Response UpdateUser(String username,User payload){
			
			Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("username", username)
				.body(payload)
			.when().put(Routes.put_url);
			
			return response;
		
	}
	
	public static Response DeleteUser(String username){
		
			Response response = given()
				.pathParam("username", username)
			.when().delete(Routes.delete_url);
			
			return response;
	}
}
