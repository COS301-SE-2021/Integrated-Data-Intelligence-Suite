package com.Gateway_Service.Gateway_Service.rri;

import com.Gateway_Service.Gateway_Service.exception.AnalyserException;
import com.Gateway_Service.Gateway_Service.exception.GatewayException;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Component
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return httpResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @SneakyThrows
    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        System.out.println("getting there");
        System.out.println(httpResponse.getStatusCode());
        System.out.println(httpResponse.getHeaders());
        System.out.println(httpResponse.getBody());

        try (InputStream body = httpResponse.getBody()) {
            System.out.println(IOUtils.toString(body, StandardCharsets.UTF_8));
        }
        System.out.println("getting there part 2");


        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            // handle SERVER_ERROR

            throw new AnalyserException("checking here 123");
        }
        else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR

            throw new GatewayException("gateway failed to connect");
        }
    }
}
