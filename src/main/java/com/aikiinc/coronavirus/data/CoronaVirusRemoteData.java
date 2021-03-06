package com.aikiinc.coronavirus.data;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aikiinc.coronavirus.utility.CoronaVirusUtil;
import com.aikiinc.model.CoronaVirus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.csv.CSVRecord;


public class CoronaVirusRemoteData {
	private Logger log = LoggerFactory.getLogger(CoronaVirusRemoteData.class);
	public final static String SOURCE_URL_PREFIX = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";
	private String sourceUrlPrefix;
	private URL remoteUrl;
	private String dateLoaded;
	private Optional<Iterable<CSVRecord>> records = Optional.empty();
	private List<CoronaVirus> coronaVirusList = new ArrayList<CoronaVirus>();

	private CoronaVirusRemoteData() {
	}

	private CoronaVirusRemoteData(String sourceUrlPrefix) throws CoronaVirusDataException {
		this.sourceUrlPrefix = sourceUrlPrefix;
		log.debug("Source url prefix: " + this.sourceUrlPrefix);
	}

	public static final CoronaVirusRemoteData getInstance(String sourceUrlPrefix) throws CoronaVirusDataException {
		CoronaVirusRemoteData coronaVirusRemoteData = new CoronaVirusRemoteData(sourceUrlPrefix);
		coronaVirusRemoteData.process();
		coronaVirusRemoteData.saveRemoteDataLocally();

		return coronaVirusRemoteData;
	}

	private void process() throws CoronaVirusDataException {
		try {
			setRemoteConnection();
			readData();
			extractData();
			CoronaVirusUtil.closeBuffer();
		} catch (CoronaVirusDataException e) {
			log.warn("Exception: " + e.getMessage());
			throw new CoronaVirusDataException(e.getMessage());
		}
	}

	void setRemoteConnection() throws CoronaVirusDataException {
		// String sdate =
		// LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		String sdate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		log.debug("Load CoronaVirus data for date: " + sdate);

		String sourceUrl = SOURCE_URL_PREFIX + sdate + ".csv";
		try {
			this.remoteUrl = new URL(sourceUrl);

			remoteUrl.getContent();

			this.dateLoaded = sdate;

			log.debug("\tLoading data from: " + remoteUrl);
		} catch (Exception e) {
			String msg = "Could not load data from: " + sourceUrl;
			log.warn("\t" + msg);
			throw new CoronaVirusDataException(e.getMessage());
		}
	}

	private void saveRemoteDataLocally() {
		try {
			if (coronaVirusList.size() > 0) {
				CoronaVirusLocalData coronaVirusLocalData = CoronaVirusLocalData.getInstance();
				coronaVirusLocalData.saveRemoteDataLocally(coronaVirusList);
			}
		} catch (CoronaVirusDataException e) {
			log.warn("Exception: " + e.getMessage());
		}
	}

	private void readData() throws CoronaVirusDataException {
		log.info("Read Remote data from: " + remoteUrl);
		records = CoronaVirusUtil.readData(remoteUrl);
	}

	private void extractData() {
		coronaVirusList = CoronaVirusUtil.extractData(records);
	}

	public List<CoronaVirus> getCoronaVirusList() {
		return coronaVirusList;
	}

}
