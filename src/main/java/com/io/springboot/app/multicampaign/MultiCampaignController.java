package com.io.springboot.app.multicampaign;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Class for MultiCampaign
 * @since 06/03/2017
 * @author brizy
 *
 */
@RestController
public class MultiCampaignController {

	@Autowired
	private MultiCampaignService mcService;
	
	private static Logger logger = Logger.getLogger("MultiCampaignController.class");
	
	/**
	 * Method to fetch adCampaigns for a partnerId
	 * The method returns only active campaigns
	 * @return List<String>
	 * @throws NullPointerException
	 * 
	 */

	@RequestMapping(method=RequestMethod.GET, value ="/multiad/{partnerId}")
	public List<String> getCampaignData(@PathVariable final String partnerId, final HttpServletResponse response) {
		
		List<MultiCampaignData> multiCampaignData = new ArrayList<MultiCampaignData>();
		List<String> activeCampaigns = new ArrayList<String>();
		try {
            logger.info("Entering getCampaignData  for MultiCampaign \n ");
			multiCampaignData = mcService.getAllCampaignData(partnerId);
			LocalDateTime currentDate = LocalDateTime.now();
			multiCampaignData.forEach(data -> {
				if (ChronoUnit.SECONDS.between(currentDate, data.getDuration()) >= 0)
					activeCampaigns.add(data.toString());
			});
			
			if(CollectionUtils.isEmpty(activeCampaigns))
				response.setStatus(HttpStatus.NO_CONTENT.value());
			else	
				response.setStatus(HttpStatus.OK.value());
			logger.info("Exiting getCampaignData  for MultiCampaign \n "); 
			return activeCampaigns;

		} catch (NullPointerException e) {
			throw new NullPointerException("Campaign Data Does Not Exist for : " + partnerId);
		}

	}
	
	/**
	 * Method to save campaigns for the given partnerId
	 * 
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/multiad/{partnerId}")
	public void persistCampaignData(@RequestBody final MultiCampaignInfo data, @PathVariable final String partnerId,
			final HttpServletResponse response) {
		logger.info("Entering persistCampaignData  for MultiCampaign \n ");
		mcService.persistCampaignData(data, partnerId);
		response.setStatus(HttpStatus.OK.value());
		logger.info("Exiting persistCampaignData  for MultiCampaign \n ");

	}
	
	/**
	 * Exception handler for Null Pointers
	 * 
	 * @param NullPointerException
	 * @param HttpServletResponse
	 * 
	 */
	@ExceptionHandler
	void handleNullPointerException(NullPointerException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

}
