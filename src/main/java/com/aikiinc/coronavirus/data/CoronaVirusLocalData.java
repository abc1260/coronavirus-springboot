package com.aikiinc.coronavirus.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoronaVirusLocalData {
	private Logger log = LoggerFactory.getLogger(CoronaVirusLocalData.class);
	private static final String CORONAVIRUS_DATA = "coronavirus.data";
	private URL localUrl = CoronaVirusLocalData.class.getClassLoader().getResource(CORONAVIRUS_DATA);

	Optional<Iterable<CSVRecord>> readLocaData() throws Exception {
		log.info("Read local data from: " + localUrl);
		Optional<Iterable<CSVRecord>> records = Optional.empty();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(localUrl.openStream()));

			// Get the iterable records
			Iterable<CSVRecord> trecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines().parse(br);
			records = Optional.of(trecords);
		} catch (IOException e) {
			log.warn("Local data was NOT read from: " + localUrl);
			throw new Exception(e);
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
