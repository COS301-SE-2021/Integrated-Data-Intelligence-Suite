package com.Gateway_Service.Gateway_Service.rri;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;


@Component
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    /*@Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return  true;
    }*/

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            // handle SERVER_ERROR

        }
        else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR

            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {

            }
        }
    }
}
