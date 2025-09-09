package org.training.account.service.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.account.service.exception.GlobalException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignClientErrorDecoder implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(FeignClientErrorDecoder.class);

    /**
     * Decode the response and return an Exception object.
     *
     * @param s        the response string
     * @param response the HTTP response object
     * @return an Exception object representing the decoded response
     */
    @Override
    public Exception decode(String s, Response response) {

        if (response == null) {
            log.error("Feign response is null");
            return new Exception("Feign response is null");
        }

        GlobalException globalException = extractGlobalException(response);

        log.info("Feign response status: {}", response.status());

        if (response.status() == 400) {
            if (globalException != null) {
                log.error("Error in request went through feign client: {} - {}", globalException.getErrorMessage(), globalException.getErrorCode());
                return globalException;
            } else {
                log.error("400 received but could not parse GlobalException from response body");
                return new Exception("Bad Request (400) - no details available");
            }
        }

        // for other statuses, prefer returning parsed GlobalException if available
        if (globalException != null) {
            log.error("Non-400 error returned via feign client: status={} error={}", response.status(), globalException);
            return globalException;
        }

        log.error("General exception went through feign client, status: {}", response.status());
        return new Exception("General exception occurred, status: " + response.status());
    }

    /**
     * Extracts a GlobalException object from the response.
     *
     * @param response The response object containing the exception information
     * @return The GlobalException object extracted from the response, or null if extraction fails
     */
    private GlobalException extractGlobalException(Response response) {

        if (response == null) {
            return null;
        }

        Reader reader = null;
        try {
            if (response.body() == null) {
                log.debug("Feign response body is null, nothing to extract.");
                return null;
            }

            reader = response.body().asReader(StandardCharsets.UTF_8);
            String result = IOUtils.toString(reader);

            if (result == null || result.isBlank()) {
                log.debug("Feign response body is empty");
                return null;
            }

            log.debug("Feign error body: {}", result);

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            try {
                GlobalException globalException = mapper.readValue(result, GlobalException.class);
                log.debug("Parsed GlobalException: {}", globalException);
                return globalException;
            } catch (IOException parseEx) {
                log.warn("Failed to deserialize response body to GlobalException", parseEx);
                return null;
            }

        } catch (IOException e) {
            log.warn("IO Exception while reading exception message", e);
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.warn("IO Exception while closing reader for exception message", e);
                }
            }
        }
    }
}
