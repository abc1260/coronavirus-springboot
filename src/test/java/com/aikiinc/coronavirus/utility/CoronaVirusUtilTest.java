package com.aikiinc.coronavirus.utility;

import java.net.URL;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.aikiinc.coronavirus.data.CoronaVirusDataException;

class CoronaVirusUtilTest {

	@Test
	void readData() {
		String CORONAVIRUS_DATA = "coronavirus.data";
		URL dataUrl = CoronaVirusUtilTest.class.getClassLoader().getResource(CORONAVIRUS_DATA);

		try {
			Optional<Iterable<CSVRecord>> records = CoronaVirusUtil.readData(dataUrl);
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
