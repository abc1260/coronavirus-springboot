package com.aikiinc.coronavirus.data;

import com.aikiinc.coronavirus.data.CoronaVirusDataException;
import com.aikiinc.coronavirus.utility.CoronaVirusUtil;
import com.aikiinc.model.CoronaVirus;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoronaVirusLocalData {
	private Logger log = LoggerFactory.getLogger(CoronaVirusLocalData.class);
	private static final String CORONAVIRUS_DATA = "coronavirus.data";
	private URL localUrl = CoronaVirusLocalData.class.getClassLoader().getResource(CORONAVIRUS_DATA);
	private Optional<Iterable<CSVRecord>> records = Optional.empty();
	private List<CoronaVirus> coronaVirusList;

	private CoronaVirusLocalData() {
	}

	/**
	 * Create a CoronaVirusLocalData instance and process loading the data
	 * 
	 * @return
	 * @throws CoronaVirusDataException
	 */
	public static final CoronaVirusLocalData getInstance() throws CoronaVirusDataException {
		CoronaVirusLocalData coronaVirusLocalData = new CoronaVirusLocalData();
		coronaVirusLocalData.process();

		return coronaVirusLocalData;
	}

	private void process() throws CoronaVirusDataException {
		readData();
		extractData();
		CoronaVirusUtil.closeBuffer();
	}

	private void readData() throws CoronaVirusDataException {
		log.info("Read local data from: " + localUrl);
		records = CoronaVirusUtil.readData(localUrl);
	}

	private void extractData() {
		coronaVirusList = CoronaVirusUtil.extractData(records);
	}

	public List<CoronaVirus> getCoronaVirusList() {
		return coronaVirusList;
	}

}
