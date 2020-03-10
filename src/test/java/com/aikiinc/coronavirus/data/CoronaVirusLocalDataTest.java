package com.aikiinc.coronavirus.data;

import java.util.Optional;
import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoronaVirusLocalDataTest {
	private static Logger LOG = LoggerFactory.getLogger(CoronaVirusLocalDataTest.class);

	@Test
	public void readLocaData() {
		try {
			Optional<Iterable<CSVRecord>> records = new CoronaVirusLocalData().readLocaData();
			if (records.isPresent()) {
				Assert.assertTrue(records.get().iterator().hasNext());
				
//				try {
//					records.get().forEach(e -> System.out.println(e));
//				} catch (Exception e) {
//				}
			} else
				Assert.fail("No CSVRecord was read");
		} catch (CoronaVirusDataException e) {
			Assert.fail(e.getMessage());
		}
	}

}
