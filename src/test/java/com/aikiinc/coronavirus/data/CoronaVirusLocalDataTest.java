package com.aikiinc.coronavirus.data;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aikiinc.model.CoronaVirus;

public class CoronaVirusLocalDataTest {
	private static Logger LOG = LoggerFactory.getLogger(CoronaVirusLocalDataTest.class);

	@Test
	public void getCoronaVirusList() {
		try {
			List<CoronaVirus> coronaDataList = CoronaVirusLocalData.getInstance().getCoronaVirusList();
			Assert.assertTrue(coronaDataList.size() > 0);

			//coronaDataList.forEach(e -> LOG.debug(e.toString()));
		} catch (CoronaVirusDataException e) {
			Assert.fail(e.getMessage());
		}
	}
	
}
