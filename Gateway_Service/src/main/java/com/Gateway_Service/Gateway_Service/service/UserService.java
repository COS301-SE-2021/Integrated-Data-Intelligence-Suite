package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.GetAllUsersRequest;
import com.Gateway_Service.Gateway_Service.dataclass.GetAllUsersResponse;
import com.Gateway_Service.Gateway_Service.dataclass.ManagePermissionsRequest;
import com.Gateway_Service.Gateway_Service.dataclass.ManagePermissionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    public ManagePermissionsResponse managePermissions(ManagePermissionsRequest request) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ManagePermissionsRequest> requestEntity = new HttpEntity<ManagePermissionsRequest>(request, requestHeaders);
        ResponseEntity<ManagePermissionsResponse> responseEntity = restTemplate.exchange("http://User-Service/User/changepermission", HttpMethod.POST, requestEntity, ManagePermissionsResponse.class);
        return responseEntity.getBody();
    }

    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GetAllUsersRequest> requestEntity = new HttpEntity<GetAllUsersRequest>(request, requestHeaders);
        ResponseEntity<GetAllUsersResponse> responseEntity = restTemplate.exchange("http://User-Service/User/allusers", HttpMethod.POST, requestEntity, GetAllUsersResponse.class);
        return responseEntity.getBody();
    }
}


