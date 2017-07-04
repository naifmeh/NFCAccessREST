package testUnits;

import static org.junit.Assert.fail;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.example.test.MyResource;

import junit.framework.Assert;

public class RestTestUnit {

	@Test
	public void testGetAllUsers() {
		Response reponse = (new MyResource()).getAllUsers();
		Assert.assertEquals(true,reponse.hasEntity());
		return;
	}

}
