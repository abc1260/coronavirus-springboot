package com.aikiinc.coronavirus.data;

import java.io.BufferedReader;
import java.io.IOException;

import com.aikiinc.coronavirus.data.CoronaVirusDataException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoronaVirusLocalData {
	private Logger log = LoggerFactory.getLogger(CoronaVirusLocalData.class);
	private static final String CORONAVIRUS_DATA = "coronavirus.data";
	private URL localUrl = CoronaVirusLocalData.class.getClassLoader().getResource(CORONAVIRUS_DATA);

	Optional<Iterable<CSVRecord>> readLocaData() throws CoronaVirusDataException {
		log.info("Read local data from: " + localUrl);
		Optional<Iterable<CSVRecord>> records = Optional.empty();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(localUrl.openStream()));

			// Get the iterable records
			Iterable<CSVRecord> trecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines().parse(br);
			records = Optional.of(trecords);
		} catch (Exception e) {
			log.warn("Local data was NOT read from: " + localUrl);
			throw new CoronaVirusDataException(e);
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
			}
		}

		return records;
	}

}
