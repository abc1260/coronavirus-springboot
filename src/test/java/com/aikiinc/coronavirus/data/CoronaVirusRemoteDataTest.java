package com.aikiinc.coronavirus.data;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aikiinc.model.CoronaVirus;

public class CoronaVirusRemoteDataTest {
	private static Logger LOG = LoggerFactory.getLogger(CoronaVirusRemoteDataTest.class);

	@Test
	public void getCoronaVirusList() {
		try {
			CoronaVirusRemoteData coronaVirusRemoteData = CoronaVirusRemoteData
					.getInstance(CoronaVirusRemoteData.SOURCE_URL_PREFIX);
			
			List<CoronaVirus> coronaDataList = coronaVirusRemoteData.getCoronaVirusList();
			Assert.assertTrue(coronaDataList.size() > 0);

			//coronaDataList.forEach(e -> LOG.debug(e.toString()));
		} catch (CoronaVirusDataException e) {
			Assert.fail(e.getMessage());
		}
	}

}
