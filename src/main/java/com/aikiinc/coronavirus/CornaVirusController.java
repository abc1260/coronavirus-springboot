package com.aikiinc.coronavirus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aikiinc.coronavirus.service.CoronaVirusProcessing;
import com.aikiinc.coronavirus.service.CoronaVirusService;
import com.aikiinc.model.CoronaVirus;
import com.aikiinc.model.HelloMessage;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class CornaVirusController implements CoronaVirusService {
	private Logger log = LoggerFactory.getLogger(CornaVirusController.class);

	@Autowired
	private CoronaVirusProcessing coronaVirusProcessing;

	public CornaVirusController(CoronaVirusProcessing process) {
		this.coronaVirusProcessing = process;

		if (coronaVirusProcessing != null) {
			log.info("Calling darocessing processing");
			coronaVirusProcessing.process();
		}
	}

	@GetMapping(value = "/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message","Hello message");
		mv.setViewName("index.html");
		
		return mv;
	}

	@GetMapping(value = "/dateLoaded")
	public  @ResponseBody String getDateLoaded() {
		return coronaVirusProcessing.getDateLoaded();
	}

	@GetMapping(value = "/coronaVirusRegionsMap")
	public Map<String, List<CoronaVirus>> getCoronaVirusRegionsMap() {
		return coronaVirusProcessing.getCoronaVirusRegionsMap();
	}

	@GetMapping(value = "/regions")
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