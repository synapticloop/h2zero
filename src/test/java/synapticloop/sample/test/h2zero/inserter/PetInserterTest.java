package synapticloop.sample.test.h2zero.inserter;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import synapticloop.h2zero.base.exception.H2ZeroFinderException;
import synapticloop.h2zero.base.exception.H2ZeroPrimaryKeyException;
import synapticloop.sample.h2zero.finder.PetFinder;
//import synapticloop.sample.h2zero.inserter.PetInserter;
import synapticloop.sample.h2zero.model.Pet;
import synapticloop.sample.test.h2zero.utils.Helper;

public class PetInserterTest {
	private static final String DEFAULT_PET_NAME = "Fido";
	public static final long DEFAULT_ID_PET = 1000;
	

	@Before
	public void setup() {
		
	}
/*
	@Test
	public void testInsertWith() throws UnsupportedEncodingException, SQLException, H2ZeroPrimaryKeyException {
		int numRows = PetInserter.insert(null, DEFAULT_PET_NAME, 1, 1.0f, new Date(System.currentTimeMillis()), new ByteArrayInputStream("this is a test".getBytes()));
		assertEquals(1, numRows);
		List<Pet> pets = PetFinder.findAll();
		for (Pet pet : pets) {
			pet.delete();
		}
	}

	@Test
	public void testInsertSilent() throws H2ZeroFinderException, IOException, NoSuchAlgorithmException, SQLException {
		InputStream imageInputStream = PetInserterTest.class.getResourceAsStream("/fido.jpg");
		String md5Hash = Helper.getMD5Hash(imageInputStream);
		imageInputStream.close();

		imageInputStream = PetInserterTest.class.getResourceAsStream("/fido.jpg");
		int numRows = PetInserter.insertSilent(DEFAULT_ID_PET, DEFAULT_PET_NAME, 1, 1.0f, new Date(System.currentTimeMillis()), imageInputStream);
		assertEquals(1, numRows);
		imageInputStream.close();

		Pet pet = PetFinder.findByPrimaryKey(DEFAULT_ID_PET);
		assertEquals(DEFAULT_PET_NAME, pet.getNmPet());
		assertEquals(new Integer(1), pet.getNumAge());
		assertEquals(new Float(1.0), pet.getFltWeight());
		Blob imgPhoto = pet.getImgPhoto();
		InputStream binaryStream = imgPhoto.getBinaryStream();
		String md5HashConfirm = Helper.getMD5Hash(binaryStream);
		assertEquals(md5Hash, md5HashConfirm);
	}*/
}
