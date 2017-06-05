package com.io.springboot.app.multicampaign;

import java.io.Serializable;

/**
 * MultiCampaignInfo
 * 
 * @author brizy
 * @since 06/03/17
 */
public class MultiCampaignInfo implements Serializable {

	private static final long serialVersionUID = 7671320102269395951L;
	private String adContent;
	private int duration;

	public MultiCampaignInfo() {
	}

	public MultiCampaignInfo(String adContent, int duration) {
		super();
		this.adContent = adContent;
		this.duration = duration;

	}

	public String getAdContent() {
		return adContent;
	}

	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
