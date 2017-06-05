package com.io.springboot.app.adcampaign;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class AdCampaignService Provides methods for fetching and persisting
 * campaign data
 * 
 * @since 06/03/17
 * @author brizy
 *
 */
@Service
public class AdCampaignService {

	@Autowired
	private AdCampaignRepository acRepo;
	private static Logger logger = Logger.getLogger("AdCampaignService.class");

	/**
	 * Method to fetch all campaign data
	 * @return List<String>
	 * 
	 */
	public List<String> getAllCampaignData() {
		
		logger.info("Entering getAllCampaignData \n ");
		
		List<String> campaignData = new ArrayList<String>();
		acRepo.findAll().forEach(data -> {
			campaignData.add(data.toString());
		});
		logger.info("Exiting getAllCampaignData \n ");
		return campaignData;
	}

	/**
	 * Method to fetch campaign data for the given partnerId
	 * @return PartnerData
	 * 
	 */

	public PartnerData getCampaignData(final String partnerId) throws NoSuchElementException {
		logger.info("Fetching getCampaignData \n ");
		return acRepo.findOne(partnerId);
	}

	/**
	 * Method to save campaign data for the given partnerId
	 * @param CampaignData
	 * 
	 */
	public void persistCampaignData(final CampaignData data) {
		logger.info("Entering persistCampaignData  \n ");
		acRepo.save(new PartnerData(data.getPartnerId(), LocalDateTime.now().plusSeconds(data.getDuration()),
				data.getAdContent()));
		logger.info("Exiting persistCampaignData  \n ");
	}

}
