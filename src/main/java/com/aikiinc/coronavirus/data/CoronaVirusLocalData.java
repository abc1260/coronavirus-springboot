package com.aikiinc.coronavirus.data;

import com.aikiinc.coronavirus.data.CoronaVirusDataException;
import com.aikiinc.coronavirus.utility.CoronaVirusUtil;

import java.net.URL;
import java.util.Optional;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoronaVirusLocalData {
	private Logger log = LoggerFactory.getLogger(CoronaVirusLocalData.class);
	private static final String CORONAVIRUS_DATA = "coronavirus.data";
	private URL localUrl = CoronaVirusLocalData.class.getClassLoader().getResource(CORONAVIRUS_DATA);

	public Optional<Iterable<CSVRecord>> readData() throws CoronaVirusDataException {
		log.info("Read local data from: " + localUrl);

		return CoronaVirusUtil.readData(localUrl);
	}

}
