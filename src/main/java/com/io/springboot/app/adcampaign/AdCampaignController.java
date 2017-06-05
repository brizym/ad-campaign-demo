package com.io.springboot.app.adcampaign;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
 * Controller Class for AdCampaign
 * @since 06/03/2017
 * @author brizy
 *
 */

@RestController
public class AdCampaignController {

	@Autowired
	private AdCampaignService acService;
	
	private static Logger logger = Logger.getLogger("AdCampaignController.class");

	/**
	 * Method to fetch all adCampaigns
	 * @return List<String>
	 * 
	 */
	@RequestMapping(method=RequestMethod.GET, value = "/ad")
	public List<String> allCampaigns(final HttpServletResponse response) {
		logger.info("In allCampaigns \n");
		List<String> campaignData = acService.getAllCampaignData();
		if(CollectionUtils.isEmpty(campaignData))
			response.setStatus(HttpStatus.NO_CONTENT.value());
		else	
			response.setStatus(HttpStatus.OK.value());
		return campaignData;
	}

	/**
	 * Method to fetch active campaigns for the given partnerId A campaign is
	 * active if it has not exceeded the duration for which it was created
	 * 
	 * @param partnerId
	 * @throws IllegalArgumentException
	 *             for expired campaigns
	 * @throws NullPointerException
	 *             if no data is found for the partner Id
	 */

	@RequestMapping(method=RequestMethod.GET, value ="/ad/{partnerId}")
	public String getCampaignData(@PathVariable final String partnerId, final HttpServletResponse response) {
		PartnerData savedCampaignData = new PartnerData();

		try {
			logger.info("Entering getCampaignData \n");
			// Call service to fetch data for the partnerId
			savedCampaignData = acService.getCampaignData(partnerId);
			LocalDateTime currentDate = LocalDateTime.now();

			// Check if campaign is active
			if (ChronoUnit.SECONDS.between(currentDate, savedCampaignData.getDuration()) <= 0){
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				throw new IllegalArgumentException("Campaign Data Does Not Exist for : " + partnerId);
			} else{
				response.setStatus(HttpStatus.OK.value());
				return savedCampaignData.toString();
		}

				
		} catch (NullPointerException e) {
			throw new NullPointerException("Campaign Data Does Not Exist for : " + partnerId);
		}

	}

	/**
	 * Method to save a campaign for the given partnerId Only a single campaign
	 * can exist for a partnerId The service saves a campaign if one does not
	 * already exist.
	 * 
	 * @throws IllegalArgumentException
	 *             for existing campaigns
	 * 
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/ad")
	public void persistCampaignData(@RequestBody final CampaignData data, final HttpServletResponse response) {
		
		logger.info("Entering persistCampaignData \n");
		// Call service to check if campaign exists in db
		if (acService.getCampaignData(data.getPartnerId()) == null){
			acService.persistCampaignData(data);
			response.setStatus(HttpStatus.OK.value());
		}
			
		else
			throw new IllegalArgumentException("Campaign Data Exists for Partner: " + data.getPartnerId());

	}

	/**
	 * Exception handler for Bad Requests
	 * 
	 * @param IllegalArgumentException
	 * @param HttpServletResponse
	 *
	 */

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
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
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

}
