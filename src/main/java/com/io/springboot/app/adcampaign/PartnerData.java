package com.io.springboot.app.adcampaign;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity class to hold Partner Data
 * @since 06/03/17
 * @author brizy
 *
 */
@Entity
public class PartnerData implements Serializable  {
	
	private static final long serialVersionUID = 5148242499930949487L;
	
	@Id
	private String partnerId;
	private LocalDateTime expiryTime;
	private String adContent;
	
	
	public PartnerData() {
	}
	
	public PartnerData(String partnerId, LocalDateTime expiryTime, String adContent) {
		super();
		this.partnerId = partnerId;
		this.expiryTime = expiryTime;
		this.adContent = adContent;
	}
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public LocalDateTime getDuration() {
		return expiryTime;
	}
	public void setDuration(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getAdContent() {
		return adContent;
	}
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}

	@Override
	public String toString() {
		return "CampaignDataJPA [partnerId=" + partnerId + "| adContent=" + adContent + "]";
	}
	
	

}
