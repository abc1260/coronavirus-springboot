package com.aikiinc.coronavirus.data;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoronaVirusRemoteDataTest {
	private static Logger LOG = LoggerFactory.getLogger(CoronaVirusRemoteDataTest.class);
	private static CoronaVirusRemoteData coronaVirusRemoteData;

	@BeforeClass
	public static void setUp() {
		try {
			coronaVirusRemoteData = new CoronaVirusRemoteData(CoronaVirusRemoteData.SOURCE_URL_PREFIX);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void process() {
		try {
			coronaVirusRemoteData.process();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

}
