package it.unive.lisa.imp;

import org.junit.Test;

import static org.junit.Assert.fail;

public class IMPFrontendTest {

	@Test
	public void testExampleProgram() {
		try {
			IMPFrontend.processFile("example.imp", false);
		} catch (ParsingException e) {
			fail("Processing the example file thrown an exception: " + e);
		}
	}
}
