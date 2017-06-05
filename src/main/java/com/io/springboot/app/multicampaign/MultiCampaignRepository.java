package com.io.springboot.app.multicampaign;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


/**
 * Interface MultiCampaignRepository extends CrudRepository
 * @since 06/03/17
 * @author brizy
 */

public interface MultiCampaignRepository extends CrudRepository<MultiCampaignData, String> {
	
	List<MultiCampaignData> findByPartnerDataPartnerId(String partnerId);
		
}
