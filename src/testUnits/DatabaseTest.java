package testUnits;

import javax.json.JsonObject;

import org.junit.Assert;
import org.junit.Test;

import com.example.db.AccessHandler;

public class DatabaseTest{

	@Test
	public void testGetjsonDb() {
		AccessHandler access = new AccessHandler();
		JsonObject jObj = access.getjsonDb();
		System.out.println(jObj.toString());
		Assert.assertEquals("{\"users\":[{\"uid\":\"0c30e399\",\"name\":\"Mehanna\",\"lastName\":\"Naif\",\"rank\":0},{\"uid\":\"7c2fe399\",\"name\":\"naif\",\"lastName\":\"mehonna\",\"rank\":3}]}",
				jObj.toString());
		return;
	}

}
