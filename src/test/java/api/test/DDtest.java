package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoint;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDtest {

	
	
	@Test (priority =1,dataProvider="Data",dataProviderClass =DataProviders.class)
	public void testCreateUser(String UserID,String UserName,String FirstName,String	LastName,String	Email,String Password,String Phone) {
		
		
//		System.out.println(UserID);
//		System.out.println(UserName);
//		System.out.println(FirstName);
//		System.out.println(LastName);
//		System.out.println(Email);
//		System.out.println(Password);
//		System.out.println(Phone);
//		System.out.println(userPayload);

		User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(UserID));
		userPayload.setUsername(UserName);
		userPayload.setFirstname(FirstName);
		userPayload.setLastname(LastName);
		userPayload.setEmail(Email);
		userPayload.setPassword(Password);
		userPayload.setPhone(Phone);
		
		Response response = UserEndpoint.CreateUser(userPayload);
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to create user: " + UserName);

	
	}
	
	@Test(priority = 2, dependsOnMethods = {"testCreateUser"},dataProvider = "UserNames",dataProviderClass = DataProviders.class)
	public void testReadUserByName(String username) throws InterruptedException { 
		System.out.println("user name is  :"+username);
		
		int retries = 5;
		int delay = 1000; // 1 second
		Response response=null;;

		for (int i = 0; i < retries; i++) {
			response = UserEndpoint.ReadUser(username);
		    if (response.getStatusCode() == 200) {
		        break;
		    }
		    Thread.sleep(delay);
		}
		
//		Response response = UserEndpoint.ReadUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to read user: " + username);

		
	}
	
	@Test (priority=2,dependsOnMethods = {"testCreateUser"},dataProvider = "UserNames",dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String UserName) {
		
		Response response = UserEndpoint.DeleteUser(UserName);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
}
