package com.Gateway_Service.Gateway_Service.controller;


import com.Gateway_Service.Gateway_Service.GatewayServiceApplication;
import com.Gateway_Service.Gateway_Service.service.AnalyseService;
import com.Gateway_Service.Gateway_Service.service.ImportService;
import com.Gateway_Service.Gateway_Service.service.ParseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayServiceApplication.class)
public class GatewayServiceControllerTest {
    @InjectMocks
    private GatewayServiceController controller;

    @Mock
    private ImportService importClient;

    @Mock
    private ParseService parseClient;

    @Mock
    private AnalyseService analyseClient;


    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("When Gateway is requested")
    public void gatewayRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
