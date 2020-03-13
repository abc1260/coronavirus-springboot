package com.aikiinc.coronavirus;

import org.springframework.web.bind.annotation.RestController;

import com.aikiinc.coronavirus.service.CoronaVirusProcessing;
import com.aikiinc.coronavirus.service.CoronaVirusService;
import com.aikiinc.model.CoronaVirus;
import com.aikiinc.model.HelloMessage;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CornaVirusController implements CoronaVirusService {
	// @Autowired
	private static CoronaVirusProcessing coronaVirusProcessing;

	public CornaVirusController() {
		System.out.println("aaaaaaaaaa");
		System.out.println("coronaVirusProcessing: " + coronaVirusProcessing);

		if (coronaVirusProcessing == null) {
			coronaVirusProcessing = new CoronaVirusProcessing();
			coronaVirusProcessing.process();
		}

//		if (coronaVirusProcessing != null)
//			coronaVirusProcessing.process();
//		else {
//			coronaVirusProcessing = new CoronaVirusProcessing();
//			coronaVirusProcessing.process();
//		}
		System.out.println("coronaVirusProcessing: " + coronaVirusProcessing);

		System.out.println("aaaaaaaaaa");
	}

	// @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/")
	public HelloMessage index() {
		return new HelloMessage();
	}

	@GetMapping(value = "/dateLoaded")
	public String getDateLoaded() {
		return coronaVirusProcessing.getDateLoaded();
	}

	@GetMapping(value = "/coronaVirusRegionsMap")
	public Map<String, List<CoronaVirus>> getCoronaVirusRegionsMap() {
		return coronaVirusProcessing.getCoronaVirusRegionsMap();
	}

	@GetMapping(value = "/regionKeys")
	public Set<String> getRegionKeys() {
		return coronaVirusProcessing.getRegionKeys();
	}

	@GetMapping(value = "/coronaVirusList")
	public List<CoronaVirus> getCoronaVirusList() {
		return coronaVirusProcessing.getCoronaVirusList();
	}

	@GetMapping(value = "/coronaVirusByRegion")
	public List<CoronaVirus> getCoronaVirusByRegion(@RequestParam("region") String region) {
		return coronaVirusProcessing.getCoronaVirusByRegion(region);
	}

}