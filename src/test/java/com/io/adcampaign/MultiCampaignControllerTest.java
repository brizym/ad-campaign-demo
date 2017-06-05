package com.io.adcampaign;

import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.io.springboot.app.AdCampaignDemoApplication;
import com.io.springboot.app.adcampaign.PartnerData;
import com.io.springboot.app.multicampaign.MultiCampaignController;
import com.io.springboot.app.multicampaign.MultiCampaignData;
import com.io.springboot.app.multicampaign.MultiCampaignInfo;
import com.io.springboot.app.multicampaign.MultiCampaignService;

/**
 * TestClass for MultiCampaignController
 * @author brizy
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AdCampaignDemoApplication.class)
@WebMvcTest(MultiCampaignController.class)



public class MultiCampaignControllerTest {
	
	@Autowired
	private MultiCampaignController mcController;

	@MockBean
	private MultiCampaignService mockService;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	HttpServletResponse response = new MockHttpServletResponse();

	
	@Test
	public void testGetAllCampaignData() throws Exception {
		
		List<MultiCampaignData> mcData = new ArrayList<MultiCampaignData>();
		MultiCampaignData data1 = new MultiCampaignData();
		String partnerId = "TestPartner1";
		data1.setAdContent("TestString1");
		data1.setCampaignId(13221232);
		data1.setDuration(LocalDateTime.now().plusSeconds(500));
		data1.setPartnerData(new PartnerData(partnerId, LocalDateTime.now( ), ""));
		mcData.add(data1);
		
		given(this.mockService.getAllCampaignData(partnerId)).willReturn(mcData);
		
		Assert.assertNotNull(this.mcController.getCampaignData(partnerId,response) );
		Assert.assertEquals(response.toString(), 200, response.getStatus());
	}
	
	@Test
	public void testPersistCampaignData() throws Exception{
		
		MultiCampaignInfo info = new MultiCampaignInfo();
		info.setAdContent("TestAdContent1");
		info.setDuration(100);
		this.mcController.persistCampaignData(info, "TestString1",response);
		Assert.assertEquals(response.toString(), 200, response.getStatus());
		
	}
	

}
