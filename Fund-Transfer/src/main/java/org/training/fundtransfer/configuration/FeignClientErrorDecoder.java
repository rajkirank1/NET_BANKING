package org.training.fundtransfer.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.fundtransfer.exception.GlobalException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignClientErrorDecoder implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(FeignClientErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
        if (response == null) {
            log.error("Feign response is null");
            return new Exception("Feign response is null");
        }

        GlobalException globalException = extractGlobalException(response);

        switch (response.status()) {
            case 400 -> {
                if (globalException != null) {
                    log.error("{} - {}", globalException.getErrorCode(), globalException.getMessage());
                    return globalException;
                } else {
                    log.error("400 received but could not parse GlobalException from response body");
                    return new Exception("Bad Request (400) - no details available");
                }
            }
            default -> {
                log.error("General exception occurred, status: {}", response.status());
                if (globalException != null) {
                    return globalException;
                }
                return new Exception("General exception occurred");
            }
        }
    }

    private GlobalException extractGlobalException(Response response) {
        GlobalException globalException = null;
        Reader reader = null;

        try {
            if (response == null || response.body() == null) {
                log.warn("Response or response body is null; nothing to extract.");
                return null;
            }

            reader = response.body().asReader(StandardCharsets.UTF_8);
            String result = IOUtils.toString(reader);
            if (result == null || result.isBlank()) {
                log.warn("Response body is empty");
                return null;
            }

            log.error("Feign error body: {}", result);

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            try {
                globalException = mapper.readValue(result, GlobalException.class);
                log.error("Parsed GlobalException: {}", globalException);
            } catch (IOException parseEx) {
                log.error("Failed to deserialize response body to GlobalException", parseEx);
                globalException = null;
            }

        } catch (IOException e) {
            log.error("IO Exception while reading the global exception", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("IO Exception while closing the global exception reader", e);
                }
            }
        }
        return globalException;
    }
}
