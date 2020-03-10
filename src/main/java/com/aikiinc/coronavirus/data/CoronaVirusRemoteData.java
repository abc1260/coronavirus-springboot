package com.aikiinc.coronavirus.data;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoronaVirusRemoteData {
	private Logger log = LoggerFactory.getLogger(CoronaVirusRemoteData.class);
	public final static String SOURCE_URL_PREFIX = "https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data/csse_covid_19_daily_reports/";
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
		//String sdate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
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

}
