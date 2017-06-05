package com.io.springboot.app.multicampaign;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.io.springboot.app.adcampaign.PartnerData;

/**
 * Entity class for MultiCampaignData
 * @since 06/03/16
 * @author brizy
 *
 */
@Entity
public class MultiCampaignData implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5148242499930949487L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long campaignId;
	private String adContent;
	private LocalDateTime expiryTime;
	
	
	@ManyToOne
	private PartnerData partnerData;
	
	
	public MultiCampaignData() {
	}
	
	public MultiCampaignData(final String adContent, final LocalDateTime expiryTime,
			 final String partnerId) {
		super();
		this.adContent = adContent;
		this.expiryTime = expiryTime;
		this.partnerData = new PartnerData(partnerId, LocalDateTime.now( ), "");
	}
	
	
	
	public long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}

	public String getAdContent() {
		return adContent;
	}
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	public LocalDateTime getDuration() {
		return expiryTime;
	}
	public void setDuration(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}
	

	public PartnerData getPartnerData() {
		return partnerData;
	}

	public void setPartnerData(PartnerData partnerData) {
		this.partnerData = partnerData;
	}

	@Override
	public String toString() {
		return "MultiCampaignData [adContent=" + adContent + "| partnerData="
				+ partnerData.getPartnerId() + "]";
	}
	

	
	

}
