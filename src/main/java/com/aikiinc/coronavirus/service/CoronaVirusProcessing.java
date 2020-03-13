package com.aikiinc.coronavirus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aikiinc.coronavirus.data.CoronaVirusDataException;
import com.aikiinc.coronavirus.data.CoronaVirusLocalData;
import com.aikiinc.coronavirus.data.CoronaVirusRemoteData;
import com.aikiinc.coronavirus.utility.CoronaVirusUtil;
import com.aikiinc.model.CoronaVirus;

@Service
public class CoronaVirusProcessing implements CoronaVirusService {
	private Logger log = LoggerFactory.getLogger(CoronaVirusProcessing.class);
	private List<CoronaVirus> coronaDataList = new ArrayList<CoronaVirus>();
	private String dateLoaded = "99/99/9999";
	private Map<String, List<CoronaVirus>> coronaVirusDataRegionsMap = new TreeMap<String, List<CoronaVirus>>();

	public void process() {
		try {
			coronaDataList = CoronaVirusRemoteData.getInstance(CoronaVirusRemoteData.SOURCE_URL_PREFIX)
					.getCoronaVirusList();

			log.debug("Loaded " + coronaDataList.size() + " CoronaVirus remote records.");
		} catch (CoronaVirusDataException e) {
			log.warn("Error loading remote data: " + e.getMessage());
			try {
				coronaDataList = CoronaVirusLocalData.getInstance().getCoronaVirusList();

				log.debug("Loaded " + coronaDataList.size() + " CoronaVirus local records.");
			} catch (CoronaVirusDataException e1) {
				log.warn("Error loading local data: " + e.getMessage());
			}
		}

		if (coronaDataList.size() > 0) {
			getReportedDateLoaded();

			cacheCoronaVirusDataByRegion();
		}
	}

	void getReportedDateLoaded() {
		if (coronaDataList.size() > 0) {
			CoronaVirus coronaVirus = coronaDataList.get(0);
			Optional<String> sdate = CoronaVirusUtil.getReportedDate(coronaVirus.getLastUpdate());
			if (sdate.isPresent())
				dateLoaded = sdate.get();
		}
	}

	void cacheCoronaVirusDataByRegion() {
		for (CoronaVirus rec : coronaDataList) {
			String region = rec.getRegion().trim();
			if (region != null && !region.isEmpty()) {
				List<CoronaVirus> tlist = coronaVirusDataRegionsMap.get(region);
				if (tlist == null) {
					// Create new list, add entry
					tlist = new ArrayList<CoronaVirus>();
					tlist.add(rec);
				} else {
					// Update list, add entry
					tlist.add(rec);
				}

				coronaVirusDataRegionsMap.put(region, tlist);
			}
		}
	}

	@Override
	public String getDateLoaded() {
		return dateLoaded;
	}

	@Override
	public Map<String, List<CoronaVirus>> getCoronaVirusRegionsMap() {
		return coronaVirusDataRegionsMap;
	}

	@Override
	public Set<String> getRegionKeys() {
		return coronaVirusDataRegionsMap.keySet();
	}

	@Override
	public List<CoronaVirus> getCoronaVirusList() {
		return coronaDataList;
	}

	@Override
	public List<CoronaVirus> getCoronaVirusByRegion(String region) {
		List<CoronaVirus> coronaDataList = new ArrayList<CoronaVirus>();

		if (region != null)
			coronaDataList = coronaVirusDataRegionsMap.get(region);

		return coronaDataList;
	}

}
