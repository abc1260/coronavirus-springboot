package com.aikiinc.coronavirus.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CoronaVirusRemoteData {
	private Logger log = LoggerFactory.getLogger(CoronaVirusRemoteData.class);
	public final static String SOURCE_URL_PREFIX = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";
	private String sourceUrlPrefix;
	private URL siteUrl;
	private String dateLoaded;

	public CoronaVirusRemoteData(String sourceUrlPrefix) throws CoronaVirusDataException {
		this.sourceUrlPrefix = sourceUrlPrefix;
		log.debug(this.sourceUrlPrefix);
	}

	public void process() throws CoronaVirusDataException {
		try {
			setRemoteConnection();
		} catch (CoronaVirusDataException e) {
		}
	}

	void setRemoteConnection() throws CoronaVirusDataException {
		// String sdate =
		// LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		String sdate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		log.debug("Load coronavirus data for date: " + sdate);

		String sourceUrl = SOURCE_URL_PREFIX + sdate + ".csv";
		try {
			this.siteUrl = new URL(sourceUrl);

			siteUrl.getContent();

			this.dateLoaded = sdate;

			log.debug("\tLoading data from: " + siteUrl);
		} catch (Exception e) {
			log.warn("\tCould not load data from: " + sourceUrl);
			log.warn("\tException: " + e.getMessage());
		}
	}

	Optional<Iterable<CSVRecord>> readRemoteData() throws CoronaVirusDataException {
		log.info("Read remote data from:  " + siteUrl);
		Optional<Iterable<CSVRecord>> records = Optional.empty();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(siteUrl.openStream()));

			// Get the iterable records
			Iterable<CSVRecord> trecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines().parse(br);
			records = Optional.of(trecords);
		} catch (IOException e) {
			log.warn("Remote data was NOT read from: " + siteUrl);
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
