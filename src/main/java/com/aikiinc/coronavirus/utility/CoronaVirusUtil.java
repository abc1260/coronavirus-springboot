package com.aikiinc.coronavirus.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.aikiinc.coronavirus.data.CoronaVirusDataException;
import com.aikiinc.model.CoronaVirus;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Set;
import java.util.TreeSet;

public class CoronaVirusUtil {
	private static Logger LOG = LoggerFactory.getLogger(CoronaVirusUtil.class);
	private static Set<String> regionKeys = new TreeSet<String>();
	private static BufferedReader br = null;

	public static Optional<Iterable<CSVRecord>> readData(URL dataUrl) throws CoronaVirusDataException {
		Optional<Iterable<CSVRecord>> records = Optional.empty();

		try {
			br = new BufferedReader(new InputStreamReader(dataUrl.openStream()));

			// Get the iterable records
			Iterable<CSVRecord> trecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines().parse(br);
			records = Optional.of(trecords);
		} catch (Exception e) {
			LOG.warn("Data was NOT read from: " + dataUrl);

			throw new CoronaVirusDataException(e);
		}

		return records;
	}

	public static void closeBuffer() {
		try {
			if (br != null)
				br.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Extract the coronavirus data:<br/>
	 * 1. Create list of CoronaVirusData - coronaVirusDataList<br/>
	 * 2. Create keys of retrieved regions - country
	 * 
	 * @param records
	 */
	public static List<CoronaVirus> extractData(Optional<Iterable<CSVRecord>> records) {
		List<CoronaVirus> coronaVirusDataList = new ArrayList<CoronaVirus>();
		regionKeys = new TreeSet<String>();

		if (records.isPresent()) {
			int j = 1;
			for (CSVRecord record : records.get()) {
				CoronaVirus data = new CoronaVirus();

				try {
					data.setProvince(record.get("Province/State"));
				} catch (Exception e) {
					data.setProvince(",");
					LOG.warn(e.getMessage());
				}
				try {
					data.setRegion(record.get("Country/Region").trim());
				} catch (Exception e) {
					data.setRegion(",");
					LOG.warn(e.getMessage());
				}
				try {
					data.setLastUpdate(record.get("Last Update"));
				} catch (Exception e) {
					data.setLastUpdate(",");
					LOG.warn(e.getMessage());
				}
				try {
					data.setConfirmed(record.get("Confirmed"));
				} catch (Exception e) {
					data.setConfirmed(",");
					LOG.warn(e.getMessage());
				}
				try {
					data.setDeaths(record.get("Deaths"));
				} catch (Exception e) {
					data.setDeaths(",");
					LOG.warn(e.getMessage());
				}
				try {
					data.setRecovered(record.get("Recovered"));
				} catch (Exception e) {
					data.setRecovered(",");
					LOG.warn(e.getMessage());
				}
				try {
					data.setLatitude(record.get("Latitude"));
				} catch (Exception e) {
					data.setLatitude(",");
					LOG.warn(e.getMessage());
				}
				try {
					data.setLongitude(record.get("Longitude"));
				} catch (Exception e) {
					data.setLongitude("");
					LOG.warn(e.getMessage());
				}

				// LOG.debug(j++ + ": " + data.toString());

				if (data.getRegion() != null && data.getRegion().length() > 1)
					coronaVirusDataList.add(data);

				// Get all reported regions (countries) as key
				if (data.getProvince() != null && !data.getProvince().isEmpty() && data.getRegion().length() > 1)
					regionKeys.add(data.getRegion());
			}
		}

		return coronaVirusDataList;
	}

	public static Set<String> getRegionKeys() {
		return regionKeys;
	}

	public static Optional<String> getReportedDate(String reportedDate) {
		Optional<String> optdate = Optional.empty();

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss");
			LocalDate ldate = LocalDate.parse(reportedDate, formatter);
			String sdate = ldate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

			optdate = Optional.ofNullable(sdate);
		} catch (Exception e) {
		}

		return optdate;
	}

}
