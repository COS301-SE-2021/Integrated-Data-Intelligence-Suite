package com.Gateway_Service.Gateway_Service.controller;

import com.Analyse_Service.Analyse_Service.controller.AnalyseServiceController;
import com.Gateway_Service.Gateway_Service.GatewayServiceApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayServiceApplication.class)
public class GatewayServiceControllerTest {
    @InjectMocks
    private GatewayServiceController controller;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
}
