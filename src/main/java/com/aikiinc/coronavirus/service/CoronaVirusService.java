package com.aikiinc.coronavirus.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aikiinc.coronavirus.data.CoronaVirusDataException;
import com.aikiinc.model.CoronaVirus;

public interface CoronaVirusService {
	public String getDateLoaded();

	public Map<String, List<CoronaVirus>> getCoronaVirusRegionsMap();

	public Set<String> getRegionKeys();

	public List<CoronaVirus> getCoronaVirusList() throws CoronaVirusDataException;

	public List<CoronaVirus> getCoronaVirusByRegion(String region);
}
