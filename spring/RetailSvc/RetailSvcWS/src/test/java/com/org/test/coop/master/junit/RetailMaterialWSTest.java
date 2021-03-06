package com.org.test.coop.master.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.coop.bs.config.DozerConfig;
import com.org.coop.bs.util.AdminSvcCommonUtil;
import com.org.coop.canonical.beans.UIModel;
import com.org.coop.retail.bs.config.RetailDozerConfig;
import com.org.coop.retail.servicehelper.RetailBranchSetupServiceHelperImpl;
import com.org.coop.society.data.admin.repositories.BranchMasterRepository;
import com.org.coop.society.data.transaction.config.DataAppConfig;
import com.org.test.coop.junit.JunitTestUtil;
import com.org.test.coop.society.data.transaction.config.TestDataAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = "com.org.test.coop")
@EnableAutoConfiguration(exclude = { DataAppConfig.class, DozerConfig.class})
@ContextHierarchy({
	  @ContextConfiguration(classes={TestDataAppConfig.class, RetailDozerConfig.class})
})
@WebAppConfiguration
public class RetailMaterialWSTest {
	private static final Logger logger = Logger.getLogger(RetailMaterialWSTest.class);
	
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;
	
	private String addMaterialGroupJson = null;
	private String addAnotherMaterialGroupJson = null;
	private String addAnotherMaterialGroup1Json = null;
	private String addDuplicateMaterialGroupJson = null;
	private String addAnotherMaterialGroupForAnotherBranchJson = null;
	private String addMaterialGroupForAnotherBranchJson = null;

	private String addMaterialJson = null;
	private String addAnotherMaterialJson1 = null;
	private String addAnotherMaterialJson2 = null;
	
	private String addMaterialForAnotherBranchJson = null;
	private String addAnotherMaterialForAnotherBranchJson = null;
	
	private String addAnotherMaterialForFreightChargeJson = null;
	
	private ObjectMapper om = null;
	
	@Autowired
	private RetailBranchSetupServiceHelperImpl branchSetupServiceImpl;
	
	@Autowired
	private BranchMasterRepository branchMasterRepository;
	
	@Autowired
	private AdminSvcCommonUtil adminSvcCommonUtil;
	
	@Before
	public void runBefore() throws Exception {
		try {
			this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			om = new ObjectMapper();
			om.setSerializationInclusion(Include.NON_NULL);
			om.setDateFormat(df);
			addMaterialGroupJson = JunitTestUtil.getFileContent("inputJson/retail/branch/addMaterialGroup.json");
			addAnotherMaterialGroupJson = JunitTestUtil.getFileContent("inputJson/retail/branch/addAnotherMaterialGroup.json");
			addAnotherMaterialGroup1Json = JunitTestUtil.getFileContent("inputJson/retail/branch/addAnotherMaterialGroup1.json");
			addDuplicateMaterialGroupJson = JunitTestUtil.getFileContent("inputJson/retail/branch/addDuplicateMaterialGroup.json");
			
			addMaterialGroupForAnotherBranchJson = JunitTestUtil.getFileContent("inputJson/retail/branch/addMaterialGroupForAnotherBranch.json");
			addAnotherMaterialGroupForAnotherBranchJson = JunitTestUtil.getFileContent("inputJson/retail/branch/addAnotherMaterialGroupForAnotherBranch.json");
			
			addMaterialJson = JunitTestUtil.getFileContent("inputJson/retail/branch/addMaterial.json");
			addAnotherMaterialJson1 = JunitTestUtil.getFileContent("inputJson/retail/branch/addAnotherMaterial1.json");
			addAnotherMaterialJson2 = JunitTestUtil.getFileContent("inputJson/retail/branch/addAnotherMaterial2.json");
			
			addMaterialForAnotherBranchJson = JunitTestUtil.getFileContent("inputJson/retail/branch/addMaterialForAnotherBranch.json");
			addAnotherMaterialForAnotherBranchJson = JunitTestUtil.getFileContent("inputJson/retail/branch/addAnotherMaterialForAnotherBranch.json");
			
			addAnotherMaterialForFreightChargeJson = JunitTestUtil.getFileContent("inputJson/retail/branch/addAnotherMaterialForFreightCharge.json");
		} catch (Exception e) {
			logger.error("Error while initializing: ", e);
			throw e;
		}
	}
	@Test
	public void testMaterials() {
		addMaterialGroup();
		addAnotherMaterialGroup();
		addAnotherMaterialGroup1();
		addDuplicateMaterialGroup();
		getMaterialGroup();
		getAllMaterialGroups();
		addMaterial();
		addAnotherMaterial1();
		addAnotherMaterial2();
		addAnotherMaterialForFreightCharges();
		getMaterial();
		getAllMaterials();
		
//		addMaterialGroupForAnotherBranch();
//		addAnotherMaterialGroupForAnotherBranch();
//		
//		addMaterialForAnotherBranch();
//		addAnotherMaterialForAnotherBranch();
	}

	private void addMaterialGroup() {
		try {
			assertNotNull(addMaterialGroupJson);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterialGroup")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addMaterialGroupJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addMaterialGroup.json");
			
			assertNull(uiModel.getErrorMsg());
			assertEquals("Lubricant".toUpperCase(), uiModel.getBranchBean().getMaterialGroups().get(0).getGroupName());
			assertEquals("Lubricant (Engine Oil)", uiModel.getBranchBean().getMaterialGroups().get(0).getGroupDescription());
			assertEquals(1, uiModel.getBranchBean().getMaterialGroups().get(0).getMaterialGrpId());
			assertEquals(1, uiModel.getBranchBean().getMaterialGroups().get(0).getBranchId());
			assertEquals("ashish", uiModel.getBranchBean().getMaterialGroups().get(0).getCreateUser());
		} catch(Exception e) {
			logger.error("Error while creating material group", e);
			Assert.fail("Error while creating material group");
		}
	}
	
	private void addMaterialGroupForAnotherBranch() {
		try {
			assertNotNull(addMaterialGroupForAnotherBranchJson);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterialGroup")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addMaterialGroupForAnotherBranchJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addMaterialGroupForAnotherBranch.json");
			
			assertNull(uiModel.getErrorMsg());
		} catch(Exception e) {
			logger.error("Error while creating material group", e);
			Assert.fail("Error while creating material group");
		}
	}
	
	private void addAnotherMaterialGroupForAnotherBranch() {
		try {
			assertNotNull(addAnotherMaterialGroupForAnotherBranchJson);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterialGroup")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addAnotherMaterialGroupForAnotherBranchJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addMaterialGroupForAnotherBranch.json");
			
			assertNull(uiModel.getErrorMsg());
		} catch(Exception e) {
			logger.error("Error while creating material group", e);
			Assert.fail("Error while creating material group");
		}
	}
	
	private void addAnotherMaterialGroup() {
		try {
			assertNotNull(addAnotherMaterialGroupJson);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterialGroup")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addAnotherMaterialGroupJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addAnotherMaterialGroup.json");
			
			assertNull(uiModel.getErrorMsg());
			assertEquals("PartsAndMaterials".toUpperCase(), uiModel.getBranchBean().getMaterialGroups().get(0).getGroupName());
			assertEquals("Parts and Materials", uiModel.getBranchBean().getMaterialGroups().get(0).getGroupDescription());
			assertEquals(2, uiModel.getBranchBean().getMaterialGroups().get(0).getMaterialGrpId());
			assertEquals(1, uiModel.getBranchBean().getMaterialGroups().get(0).getBranchId());
			assertEquals("ashish", uiModel.getBranchBean().getMaterialGroups().get(0).getCreateUser());
		} catch(Exception e) {
			logger.error("Error while creating material group", e);
			Assert.fail("Error while creating material group");
		}
	}
	
	private void addAnotherMaterialGroup1() {
		try {
			assertNotNull(addAnotherMaterialGroup1Json);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterialGroup")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addAnotherMaterialGroup1Json)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addAnotherMaterialGroup1.json");
			
			assertNull(uiModel.getErrorMsg());
			assertEquals("Pestisides".toUpperCase(), uiModel.getBranchBean().getMaterialGroups().get(0).getGroupName());
			assertEquals("Pestisides", uiModel.getBranchBean().getMaterialGroups().get(0).getGroupDescription());
			assertEquals(3, uiModel.getBranchBean().getMaterialGroups().get(0).getMaterialGrpId());
			assertEquals(1, uiModel.getBranchBean().getMaterialGroups().get(0).getBranchId());
			assertEquals("ashish", uiModel.getBranchBean().getMaterialGroups().get(0).getCreateUser());
		} catch(Exception e) {
			logger.error("Error while creating material group", e);
			Assert.fail("Error while creating material group");
		}
	}
	
	private void addDuplicateMaterialGroup() {
		try {
			assertNotNull(addDuplicateMaterialGroupJson);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterialGroup")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addDuplicateMaterialGroupJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addDuplicateMaterialGroup.json");
			
			assertNotNull(uiModel.getErrorMsg());
		} catch(Exception e) {
			logger.error("Error while creating material group", e);
			Assert.fail("Error while creating material group");
		}
	}
	
	private void getMaterialGroup() {
		try {
			MvcResult result = this.mockMvc.perform(get("/rest/getMaterialGroup?materialGroupId=1")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
				
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/getMaterialGroup.json");
			
			assertNull(uiModel.getErrorMsg());
			assertEquals("Lubricant".toUpperCase(), uiModel.getBranchBean().getMaterialGroups().get(0).getGroupName());
			assertEquals("Lubricant (Engine Oil)", uiModel.getBranchBean().getMaterialGroups().get(0).getGroupDescription());
			assertEquals(1, uiModel.getBranchBean().getMaterialGroups().get(0).getMaterialGrpId());
			assertEquals("ashish", uiModel.getBranchBean().getMaterialGroups().get(0).getCreateUser());
		} catch(Exception e) {
			logger.error("Error while retrieving material groups", e);
			Assert.fail("Error while retriving material groups");
		}
	}
	
	private void addMaterial() {
		try {
			assertNotNull(addMaterialJson);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterial")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addMaterialJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addMaterial.json");
			
			assertNull(uiModel.getErrorMsg());
			assertEquals("Castrol".toUpperCase(), uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialName());
			assertEquals("Castrol Engine oil", uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialDescription());
			assertEquals(1, uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialGrpId());
			assertEquals("ashish", uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getCreateUser());
		} catch(Exception e) {
			logger.error("Error while adding material", e);
			Assert.fail("Error while adding material");
		}
	}
	
	private void addAnotherMaterial1() {
		try {
			assertNotNull(addAnotherMaterialJson1);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterial")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addAnotherMaterialJson1)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addAnotherMaterial1.json");
			
			assertNull(uiModel.getErrorMsg());
			assertEquals("SeatCover".toUpperCase(), uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialName());
			assertEquals("Seat Cover", uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialDescription());
			assertEquals(2, uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialGrpId());
			assertEquals("ashish", uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getCreateUser());
		} catch(Exception e) {
			logger.error("Error while adding material", e);
			Assert.fail("Error while adding material");
		}
	}
	
	private void addAnotherMaterial2() {
		try {
			assertNotNull(addAnotherMaterialJson2);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterial")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addAnotherMaterialJson2)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addAnotherMaterial2.json");
			
			assertNull(uiModel.getErrorMsg());
			assertEquals("LongSeatCover".toUpperCase(), uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialName());
			assertEquals("Long Seat Cover", uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialDescription());
			assertEquals(2, uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialGrpId());
			assertEquals("ashish", uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getCreateUser());
		} catch(Exception e) {
			logger.error("Error while adding material", e);
			Assert.fail("Error while adding material");
		}
	}
	
	private void addAnotherMaterialForFreightCharges() {
		try {
			assertNotNull(addAnotherMaterialJson2);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterial")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addAnotherMaterialForFreightChargeJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addAnotherMaterialForFreightCharge.json");
			
			assertNull(uiModel.getErrorMsg());
		} catch(Exception e) {
			logger.error("Error while adding freight charge", e);
			Assert.fail("Error while adding freight charge");
		}
	}
	
	private void addMaterialForAnotherBranch() {
		try {
			assertNotNull(addMaterialForAnotherBranchJson);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterial")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addMaterialForAnotherBranchJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addMaterialForAnotherBranch.json");
			
			assertNull(uiModel.getErrorMsg());
		} catch(Exception e) {
			logger.error("Error while adding material", e);
			Assert.fail("Error while adding material");
		}
	}
	
	private void addAnotherMaterialForAnotherBranch() {
		try {
			assertNotNull(addAnotherMaterialForAnotherBranchJson);
			MvcResult result = this.mockMvc.perform(post("/rest/saveMaterial")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addAnotherMaterialForAnotherBranchJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/addAnotherMaterialForAnotherBranch.json");
			
			assertNull(uiModel.getErrorMsg());
		} catch(Exception e) {
			logger.error("Error while adding material", e);
			Assert.fail("Error while adding material");
		}
	}
	
	private void getMaterial() {
		try {
			MvcResult result = this.mockMvc.perform(get("/rest/getMaterial?materialId=1")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
				
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/getMaterial.json");
			assertNull(uiModel.getErrorMsg());
			assertEquals("Castrol".toUpperCase(), uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialName());
			assertEquals("Castrol Engine oil", uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialDescription());
			assertEquals(1, uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getMaterialGrpId());
			assertEquals("ashish", uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().get(0).getCreateUser());
		} catch(Exception e) {
			logger.error("Error while retriving material", e);
			Assert.fail("Error while retrieving materials");
		}
	}
	
	private void getAllMaterials() {
		try {
			MvcResult result = this.mockMvc.perform(get("/rest/getMaterial?branchId=2")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
				
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/getAllMaterials.json");
			assertNull(uiModel.getErrorMsg());
			assertEquals(4, uiModel.getBranchBean().getMaterialGroups().get(0).getMaterials().size());
		} catch(Exception e) {
			logger.error("Error while retriving material", e);
			Assert.fail("Error while retrieving materials");
		}
	}
	
	private void getAllMaterialGroups() {
		try {
			MvcResult result = this.mockMvc.perform(get("/rest/getMaterialGroup?branchId=2")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
				
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/getAllMaterialGroups.json");
			assertNull(uiModel.getErrorMsg());
			assertEquals(3, uiModel.getBranchBean().getMaterialGroups().size());
		} catch(Exception e) {
			logger.error("Error while retriving all material groups", e);
			Assert.fail("Error while retrieving all material groups");
		}
	}
	
	private UIModel getUIModel(MvcResult result)
			throws UnsupportedEncodingException, IOException,
			JsonParseException, JsonMappingException {
		String json = result.getResponse().getContentAsString();
		UIModel createBranchBean = om.readValue(json, UIModel.class);
		return createBranchBean;
	}
	
	private UIModel getUIModel(MvcResult result, String path)
			throws UnsupportedEncodingException, IOException,
			JsonParseException, JsonMappingException {
		UIModel createBranchBean = getUIModel(result);
		JunitTestUtil.writeJSONToFile(createBranchBean, path);
		return createBranchBean;
	}
	
}
