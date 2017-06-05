package com.io.springboot.app.adcampaign;

import java.io.Serializable;

/**
 * CampaignData 
 * @since 06/03/17 
 * @author brizy
 *
 */
public class CampaignData implements Serializable {
	
	private static final long serialVersionUID = -4683941843977414894L;
	private String partnerId;
	private int duration;
	private String adContent;
	
	
	public CampaignData() {
	}
	
	public CampaignData(String partnerId, int duration, String adContent) {
		super();
		this.partnerId = partnerId;
		this.duration = duration;
		this.adContent = adContent;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getAdContent() {
		return adContent;
	}
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	
	

}
