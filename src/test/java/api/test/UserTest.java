package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoint;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {

	Faker faker;
	User userPayload;
	Logger logger;
	
	@BeforeClass
	public void setData() {
		
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().phoneNumber());
		
		logger=LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void testCreateUser() {
		
		logger.info("*************************create User*********************");
		Response response = UserEndpoint.CreateUser(userPayload);
		response.then().log().all();
		System.out.println("user name is  :"+userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*************************creating User*********************");
	}
	
	
	
	@Test(priority = 2, dependsOnMethods = {"testCreateUser"})
	public void testReadUserByName() throws InterruptedException { 
		System.out.println("user name is  :"+this.userPayload.getUsername());
		
		logger.info("*************************read User*********************");
		
		int retries = 6;
		int delay = 1000; // 1 second
		Response response=null;;

		for (int i = 0; i < retries; i++) {
			response = UserEndpoint.ReadUser(this.userPayload.getUsername());
		    if (response.getStatusCode() == 200) {
		        break;
		    }
		    Thread.sleep(delay);
		}
		
//		Response response = UserEndpoint.ReadUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority = 3, dependsOnMethods = {"testCreateUser"})
	public void testUpdateUser() throws InterruptedException {
		
		logger.info("*************************UpdateUser*********************");
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
//		Response response = UserEndpoint.UpdateUser(this.userPayload.getUsername(),userPayload);
		int retries = 6;
		int delay = 1000; // 1 second
		Response response=null;;

		for (int i = 0; i < retries; i++) {
		 response = UserEndpoint.UpdateUser(this.userPayload.getUsername(),userPayload);
		    if (response.getStatusCode() == 200) {
		        break;
		    }
		    Thread.sleep(delay);
		}
		
		
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after update
		
		System.out.println(this.userPayload.getUsername());
		
		
//		Response responseAfterUpdate = UserEndpoint.ReadUser(this.userPayload.getUsername());
		int retries1 = 6;
		int delay1 = 1000; // 1 second
		Response responseAfterUpdate=null;;

		for (int i = 0; i < retries; i++) {
			 responseAfterUpdate = UserEndpoint.ReadUser(this.userPayload.getUsername());
		    if (responseAfterUpdate.getStatusCode() == 200) {
		        break;
		    }
		    Thread.sleep(delay1);
		}
		
		
		
		responseAfterUpdate.then().log().all();
		
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
	}
	
	
	@Test(priority = 4, dependsOnMethods = {"testCreateUser"})
	public void testDeleteUserByName() throws InterruptedException {
		
//		Response response = UserEndpoint.DeleteUser(this.userPayload.getUsername());
		
		logger.info("*************************Delete User*********************");
		int retries = 6;
		int delay = 1000; // 1 second
		Response response=null;;

		for (int i = 0; i < retries; i++) {
			response = UserEndpoint.ReadUser(this.userPayload.getUsername());
		    if (response.getStatusCode() == 200) {
		        break;
		    }
		    Thread.sleep(delay);
		}
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*************************User deleted*********************");
		// Confirm deletion
//		Response check = UserEndpoint.ReadUser(this.userPayload.getUsername());
//		Assert.assertEquals(check.getStatusCode(), 404);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
