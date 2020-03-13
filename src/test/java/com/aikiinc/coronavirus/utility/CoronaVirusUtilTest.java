package com.aikiinc.coronavirus.utility;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aikiinc.coronavirus.data.CoronaVirusDataException;
import com.aikiinc.model.CoronaVirus;

public class CoronaVirusUtilTest {
	private static Logger LOG = LoggerFactory.getLogger(CoronaVirusUtilTest.class);
	private static URL dataUrl;

	@BeforeClass
	public static void setUp() {
		String CORONAVIRUS_DATA = "coronavirus.data";
		dataUrl = CoronaVirusUtilTest.class.getClassLoader().getResource(CORONAVIRUS_DATA);

		LOG.info(dataUrl.toString());
	}

	@Test
	public void readData() {
		try {
			Optional<Iterable<CSVRecord>> records = CoronaVirusUtil.readData(dataUrl);
			if (records.isPresent()) {
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

	@Test
	public void extractData() {
		try {
			Optional<Iterable<CSVRecord>> records = CoronaVirusUtil.readData(dataUrl);
			if (records.isPresent()) {
				List<CoronaVirus> coronaDataList = CoronaVirusUtil.extractData(records);
				Assert.assertTrue(coronaDataList.size() > 0);

				// coronaDataList.forEach(e -> System.out.println(e));
			} else
				Assert.fail("No CSVRecord was read");
		} catch (CoronaVirusDataException e) {
			Assert.fail(e.getMessage());
		} finally {
			CoronaVirusUtil.closeBuffer();
		}
	}

	@Test
	public void getReportedDate() {
		Optional<String> sdate = CoronaVirusUtil.getReportedDate("2020-03-07T11:13:04");
		if (sdate.isPresent()) {
			//LOG.debug("sdate: " + sdate.get());
		} else
			Assert.fail("Date is invalid");
	}

}
