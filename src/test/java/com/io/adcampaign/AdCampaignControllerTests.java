package com.io.adcampaign;

import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Arrays;
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
import com.io.springboot.app.adcampaign.AdCampaignController;
import com.io.springboot.app.adcampaign.AdCampaignService;
import com.io.springboot.app.adcampaign.CampaignData;
import com.io.springboot.app.adcampaign.PartnerData;

/**
 * TestClass for AdCampaignController
 * @author brizy
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AdCampaignDemoApplication.class)
@WebMvcTest(AdCampaignController.class)
public class AdCampaignControllerTests {

	@Autowired
	private AdCampaignController adCampaignController;

	@MockBean
	private AdCampaignService mockAdService;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	HttpServletResponse response = new MockHttpServletResponse();


	@Test
	public void testGetAllCampaignData() throws Exception {

		List<String> allCampaignData = Arrays.asList(
				new String("CampaignDataJPA [partnerId=TestString1| adContent=Testadcontent1]"),
				new String("CampaignDataJPA [partnerId=TestString2| adContent=Testadcontent2]"),
				new String("CampaignDataJPA [partnerId=TestString3| adContent=Testadcontent3]"),
				new String("CampaignDataJPA [partnerId=TestString3| adContent=Testadcontent4]"));

		given(this.mockAdService.getAllCampaignData()).willReturn(allCampaignData);

		Assert.assertEquals(this.adCampaignController.allCampaigns(response), allCampaignData);
		Assert.assertEquals(response.toString(), 200, response.getStatus());
		
	}
	
	@Test
	public void testGetCampaignData() throws Exception {
		
		PartnerData savedCampaignData = new PartnerData("TestPartner1", 
				LocalDateTime.now().plusSeconds(500),"TestAdContent");
		String partnerId = "TestPartner1";
		given(this.mockAdService.getCampaignData(partnerId)).willReturn(savedCampaignData);
		Assert.assertNotNull(this.adCampaignController.getCampaignData(partnerId, response));
		Assert.assertEquals(response.toString(), 200, response.getStatus());
	}
	
	@Test
	public void testGetCampaignDataException() throws Exception {
		
		PartnerData savedCampaignData = new PartnerData("TestPartner1", 
				LocalDateTime.now().minusSeconds(20),"TestAdContent");
		String partnerId = "TestPartner1";
		given(this.mockAdService.getCampaignData(partnerId)).willReturn(savedCampaignData);
		exception.expect(IllegalArgumentException.class);
		this.adCampaignController.getCampaignData(partnerId, response);
		
	}
	
	@Test
	public void testGetCampaignDataNoData() throws Exception {
		
		String partnerId = "TestPartner1";
		given(this.mockAdService.getCampaignData(partnerId)).willReturn(null);
		exception.expect(NullPointerException.class);
		this.adCampaignController.getCampaignData(partnerId, response);
		
	}
	
	@Test 
	public void testPersistData() throws Exception{
		String partnerId = "TestPartner1";
		CampaignData data = new CampaignData();
		given(this.mockAdService.getCampaignData(partnerId)).willReturn(null);
		this.adCampaignController.persistCampaignData(data, response);
		Assert.assertEquals(response.toString(), 200, response.getStatus());
	}
	
	@Test 
	public void testPersistDataExisting() throws Exception{
		String partnerId = "TestPartner1";
		CampaignData data = new CampaignData("TestPartner1", 200, "TestAdContent1");
		PartnerData savedCampaignData = new PartnerData("TestPartner1", 
				LocalDateTime.now().plusSeconds(20),"TestAdContent");
		given(this.mockAdService.getCampaignData(partnerId)).willReturn(savedCampaignData);
		exception.expect(IllegalArgumentException.class);
		this.adCampaignController.persistCampaignData(data, response);
		
	}
	
}
