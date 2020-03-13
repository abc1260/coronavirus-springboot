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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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

	public void saveRemoteDataLocally(List<CoronaVirus> coronaVirusList) throws CoronaVirusDataException {
		try {
			// 1
			Path lpath = Paths.get(localUrl.getPath());

			// 2
			StringBuilder bld = new StringBuilder();
			bld.append("Province/State,Country/Region,Last Update,Confirmed,Deaths,Recovered,Latitude,Longitude\n");
			for (CoronaVirus record : coronaVirusList) {
				bld.append(record.getProvince() == null ? "" : record.getProvince());
				bld.append(",");
				bld.append(record.getRegion() == null ? "" : record.getRegion());
				bld.append(",");
				bld.append(record.getLastUpdate() == null ? "" : record.getLastUpdate());
				bld.append(",");
				bld.append(record.getConfirmed() == null ? "" : record.getConfirmed());
				bld.append(",");
				bld.append(record.getDeaths() == null ? "" : record.getDeaths());
				bld.append(",");
				bld.append(record.getRecovered() == null ? "" : record.getRecovered());
				bld.append(",");
				bld.append(record.getLatitude() == null || record.getLatitude().isEmpty() ? "" : record.getLatitude());
				bld.append(",");
				bld.append(record.getLongitude() == null ? "" : record.getLongitude());
				bld.append("\n");
			}

			// 3
			Files.write(lpath, bld.toString().getBytes(), StandardOpenOption.CREATE);
			log.info("\tSaved data to local path: " + localUrl);
		} catch (Exception e) {
			String msg = "Error saving remote data locally: " + e.getMessage();
			log.warn("\t" + msg);
			throw new CoronaVirusDataException(e);
		}
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
