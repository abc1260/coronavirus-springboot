package com.aikiinc.coronavirus.data;

import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
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

	@Test
	public void readRemoteData() {
		try {
			coronaVirusRemoteData.process();
			
			Optional<Iterable<CSVRecord>> records = coronaVirusRemoteData.readRemoteData();
			if (records.isPresent()) {
				Assert.assertTrue(records.get().iterator().hasNext());
				
				try {
					records.get().forEach(e -> System.out.println(e));
				} catch (Exception e) {
				}
			} else
				Assert.fail("No CSVRecord was read");
		} catch (CoronaVirusDataException e) {
			Assert.fail(e.getMessage());
		}
	}

}
