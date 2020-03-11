package com.aikiinc.coronavirus.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aikiinc.coronavirus.data.CoronaVirusDataException;
import java.net.URL;

public class CoronaVirusUtil {
	private static Logger LOG = LoggerFactory.getLogger(CoronaVirusUtil.class);

	public static Optional<Iterable<CSVRecord>> readData(URL dataUrl) throws CoronaVirusDataException {
		Optional<Iterable<CSVRecord>> records = Optional.empty();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(dataUrl.openStream()));

			// Get the iterable records
			Iterable<CSVRecord> trecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines().parse(br);
			records = Optional.of(trecords);
		} catch (Exception e) {
			LOG.warn("Data was NOT read from: " + dataUrl);
			
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
