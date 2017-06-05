package com.io.springboot.app.multicampaign;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class MultiCampaignService Provides methods for fetching and persisting
 * campaign data
 * 
 * @since 06/03/17
 * @author brizy
 *
 */
@Service
public class MultiCampaignService {
	
	@Autowired
	private MultiCampaignRepository mcRepo;
	private static Logger logger = Logger.getLogger("MultiCampaignService.class");
	
	
	public List<MultiCampaignData> getAllCampaignData(String partnerId){
		logger.info("Entering getAllCampaignData for MultiCampaign \n");
		List<MultiCampaignData> multiCampaignData = new ArrayList<MultiCampaignData>();
		mcRepo.findByPartnerDataPartnerId(partnerId).forEach(data->{
			multiCampaignData.add(data);
		});
		logger.info("Exiting getAllCampaignData for MultiCampaign \n");
		return multiCampaignData;
	}
	
	public void persistCampaignData(final MultiCampaignInfo mcInfo, String partnerId ){
		logger.info("Entering persistCampaignData for MultiCampaign \n");
		mcRepo.save(
				new MultiCampaignData(
						mcInfo.getAdContent(),
						LocalDateTime.now().plusSeconds(mcInfo.getDuration()),
						partnerId));
		
		logger.info("Exiting persistCampaignData for MultiCampaign \n");
		}
	
	

}
