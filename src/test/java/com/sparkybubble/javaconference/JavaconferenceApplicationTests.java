package com.sparkybubble.javaconference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static com.sparkybubble.javaconference.JavaconferenceApplication.readFile;
import static com.sparkybubble.javaconference.JavaconferenceApplication.sortTalk;

@SpringBootTest
class JavaconferenceApplicationTests {

	@Autowired
	JavaconferenceApplication conferenceApp;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(conferenceApp);
	}

	@Test
	@DisplayName("Test: readFile()")
	void testReadFile(){
		/*Create instance of the readFile method. */
		HashMap<String, Integer> file = readFile();
		/*Make sure the file is not empty and is correct size. */
		Assertions.assertTrue(!file.isEmpty());
		Assertions.assertTrue(file.size()==17);

		/*Add to the hashMap and verify it changes accordingly. */
		file.put("TestAdd", 50);
		Assertions.assertTrue(file.size() != 17);
		Assertions.assertTrue(file.size()==18);

		/*Make another hashMap and set equal to the first hashMap. */
		HashMap<String, Integer> newFile = file;
		Assertions.assertNotNull(file);
		Assertions.assertNotNull(newFile);

		/*Make sure that both references contain same content. */
		Assertions.assertEquals(file, newFile);



	}

	@Test
	@DisplayName("Test: sortTalk()")
	void testSortTalk(){
		HashMap<String, Integer> file = readFile();
		HashMap<String, Integer> file2 = readFile();

		/*Before it is sorted. */
		Assertions.assertEquals(file, file2);

		sortTalk(file2);

		/*After sort method is called. Makes sure file2 is sorted. */
		Assertions.assertNotEquals(file, file2);

		/*Verify that any duplicates are taken out. */
		Assertions.assertEquals(7,file2.size());

		/*Sort the first file*/
		sortTalk(file);

		/*Verify that both file are now the same. */
		Assertions.assertEquals(file, file2);
	}


}
