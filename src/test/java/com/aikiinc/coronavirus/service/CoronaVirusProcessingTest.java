package com.aikiinc.coronavirus.service;

import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aikiinc.model.CoronaVirus;

public class CoronaVirusProcessingTest {
	private Logger LOG = LoggerFactory.getLogger(CoronaVirusProcessingTest.class);
	private static CoronaVirusProcessing coronaVirusProcessing;

	@BeforeClass
	public static void setUp() {
		coronaVirusProcessing = new CoronaVirusProcessing();
		coronaVirusProcessing.process();
	}

	@Test
	public void getCoronaVirusList() {
		Assert.assertTrue(coronaVirusProcessing.getCoronaVirusList().size() > 0);
		// coronaVirusProcessing.getCoronaVirusList().forEach(e ->
		// LOG.debug(e.toString()));
	}

	@Test
	public void getDateLoaded() {
		String reporteddate = coronaVirusProcessing.getDateLoaded();
		Assert.assertNotNull(reporteddate);
		// LOG.debug(reporteddate);
	}

	@Test
	public void getCoronaVirusRegionsMap() {
		Set<String> showRegions = coronaVirusProcessing.getRegionKeys();
		Assert.assertTrue(showRegions.size() > 0);

		showRegions.stream().forEach(r -> {
//			LOG.debug("Region: " + r);
			List<CoronaVirus> list = coronaVirusProcessing.getCoronaVirusByRegion(r);
			Assert.assertTrue(list.size() > 0);
//			if (list != null)
//				list.stream().forEach(e -> LOG.debug("\t" + e.toString()));
		});
	}

	@Test
	public void getRegionKeys() {
		Set<String> showRegions = coronaVirusProcessing.getRegionKeys();
		Assert.assertTrue(showRegions.size() > 0);
		// showRegions.forEach(e -> LOG.debug(e.toString()));
	}

	@Test
	public void getCoronaVirusByRegion() {
		String r = "Mainland China";
		List<CoronaVirus> list = coronaVirusProcessing.getCoronaVirusByRegion(r);
		Assert.assertTrue(list.size() > 0);
//		if (list != null)
//			list.stream().forEach(e -> System.out.println(e));
	}

}
