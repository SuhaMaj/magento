package asgard.prometheus.email.client.urlgenerators.email;

import asgard.prometheus.email.client.dto.UrlResponse;
import asgard.prometheus.email.client.enums.ChannelId;
import asgard.prometheus.email.client.enums.EmailType;
import asgard.prometheus.email.client.enums.PlacementId;
import asgard.prometheus.email.client.exceptions.InvalidParameterException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Integration tests to verify the functionality of the EdeEmailUrlGenerator class
 *
 */
public class EdeEmailUrlGeneratorIntegrationTest {
    //JUnit rule to verify the exceptions thrown
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //Constants
    private static final String INVALID_PROTOCOL = "ftp";
    private static final int INVALID_PORT = 70000;
    private static final String VALID_PROTOCOL = "https";
    private static final int VALID_NON_ZERO_PORT = 443;
    private static final int VALID_ZERO_PORT = 0;
    private static final String HOST_NAME = "api-atg.kohls.com";
    private static final int EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION = 2;

    /**
     * Private default constructor should create an instance if invoked.
     *
     */
    @Test
    public void should_create_instance_if_the_private_default_constructor_is_invoked() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<EdeEmailUrlGenerator> constructor = EdeEmailUrlGenerator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        EdeEmailUrlGenerator edeEmailUrlGenerator = constructor.newInstance();
        assertNotNull(edeEmailUrlGenerator);
    }

    /**
     * Argument accepting private constructor should create an instance if invoked.
     *
     */
    @Test
    public void should_create_instance_if_private_arg_accepting_constructor_is_invoked() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<EdeEmailUrlGenerator> constructor = EdeEmailUrlGenerator.class.getDeclaredConstructor(String.class, String.class, int.class);
        constructor.setAccessible(true);
        EdeEmailUrlGenerator edeEmailUrlGenerator = constructor.newInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(edeEmailUrlGenerator);

        String actualProtocol = Whitebox.<String>getInternalState(edeEmailUrlGenerator, "protocol");
        String actualhostname = Whitebox.<String>getInternalState(edeEmailUrlGenerator, "hostname");
        int actualPort = Whitebox.getInternalState(edeEmailUrlGenerator, "port");

        assertEquals(VALID_PROTOCOL, actualProtocol);
        assertEquals(HOST_NAME, actualhostname);
        assertEquals(VALID_NON_ZERO_PORT, actualPort);
    }

    /**
     * Argument accepting private constructor should lower case the string arguments before assigning to the local variables.
     *
     */
    @Test
    public void should_lower_case_the_string_arguments_before_setting_local_variables_when_private_arg_accepting_constructor_is_invoked() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<EdeEmailUrlGenerator> constructor = EdeEmailUrlGenerator.class.getDeclaredConstructor(String.class, String.class, int.class);
        constructor.setAccessible(true);
        //Capitalize some letters in the string parameters
        EdeEmailUrlGenerator edeEmailUrlGenerator = constructor.newInstance("HtTpS", "HoStNaMe", VALID_NON_ZERO_PORT);
        assertNotNull(edeEmailUrlGenerator);

        String actualProtocol = Whitebox.<String>getInternalState(edeEmailUrlGenerator, "protocol");
        String actualhostname = Whitebox.<String>getInternalState(edeEmailUrlGenerator, "hostname");

        //Assert against lower cased strings
        assertEquals("https", actualProtocol);
        assertEquals("hostname", actualhostname);
    }

    /**
     * InvalidParameterException must be thrown when tried to instantiate EdeEmailUrlGenerator with invalid
     * protocol.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_trying_to_construct_EdeEmailUrlGenerator_with_invalid_protocol() throws InvalidParameterException {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Protocol must be either http or https");

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(INVALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);
        assertNull(emailUrlGenerator);
    }

    /**
     * InvalidParameterException must be thrown when tried to instantiate EdeEmailUrlGenerator with null protocol
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_trying_to_construct_EdeEmailUrlGenerator_with_null_protocol() throws InvalidParameterException {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Protocol must be either http or https");

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(null, HOST_NAME, VALID_ZERO_PORT);
        assertNull(emailUrlGenerator);
    }

    /**
     * InvalidParameterException must be thrown when tried to instantiate EdeEmailUrlGenerator with empty protocol
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_trying_to_construct_EdeEmailUrlGenerator_with_empty_protocol() throws InvalidParameterException {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Protocol must be either http or https");

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance("", HOST_NAME, VALID_ZERO_PORT);
        assertNull(emailUrlGenerator);
    }

    /**
     * InvalidParameterException must be thrown when tried to instantiate EdeEmailUrlGenerator with null hostname
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_trying_to_construct_EdeEmailUrlGenerator_with_null_hostname() throws InvalidParameterException {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Hostname cannot be null or an empty string");

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, null, INVALID_PORT);
        assertNull(emailUrlGenerator);
    }

    /**
     * InvalidParameterException must be thrown when tried to instantiate EdeEmailUrlGenerator with empty hostname
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_trying_to_construct_EdeEmailUrlGenerator_with_empty_hostname() throws InvalidParameterException {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Hostname cannot be null or an empty string");

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, "", INVALID_PORT);
        assertNull(emailUrlGenerator);
    }

    /**
     * InvalidParameterException must be thrown when tried to instantiate EdeEmailUrlGenerator with invalid port
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_trying_to_construct_EdeEmailUrlGenerator_with_invalid_port() throws InvalidParameterException {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Port must be in the valid range 0 - 65535");

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, INVALID_PORT);
        assertNull(emailUrlGenerator);
    }

    /**
     * Should create the proper EdeEmailUrlGenerator instance when the input protocol and port are valid (non-zero port)
     *
     */
    @Test
    public void should_create_the_instance_properly_when_trying_to_construct_EdeEmailUrlGenerator_with_proper_protocol_and_non_zero_port() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);
        String setProtocol = Whitebox.getInternalState(emailUrlGenerator, "protocol");
        String setHostname = Whitebox.getInternalState(emailUrlGenerator, "hostname");
        int setPort = Whitebox.getInternalState(emailUrlGenerator, "port");
        String setEmailRecommendationsBaseUrl = Whitebox.getInternalState(emailUrlGenerator, "emailRecommendationsBaseUrl");

        assertEquals(VALID_PROTOCOL, setProtocol);
        assertEquals(HOST_NAME, setHostname);
        assertEquals(VALID_NON_ZERO_PORT, setPort);
        assertEquals("https://api-atg.kohls.com:443/v1/ede/email/recommendations/", setEmailRecommendationsBaseUrl);
    }

    /**
     * Should create the proper EdeEmailUrlGenerator instance when the input protocol and port are valid (zero port)
     *
     */
    @Test
    public void should_create_the_instance_properly_when_trying_to_construct_EdeEmailUrlGenerator_with_proper_protocol_and_zero_port() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);
        assertNotNull(emailUrlGenerator);
        String setProtocol = Whitebox.getInternalState(emailUrlGenerator, "protocol");
        String setHostname = Whitebox.getInternalState(emailUrlGenerator, "hostname");
        int setPort = Whitebox.getInternalState(emailUrlGenerator, "port");
        String setEmailRecommendationsBaseUrl = Whitebox.getInternalState(emailUrlGenerator, "emailRecommendationsBaseUrl");

        assertEquals(VALID_PROTOCOL, setProtocol);
        assertEquals(HOST_NAME, setHostname);
        assertEquals(VALID_ZERO_PORT, setPort);
        assertEquals("https://api-atg.kohls.com/v1/ede/email/recommendations/", setEmailRecommendationsBaseUrl);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate Kohl's cash recommendations URLs with a null
     * position
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_kohls_cash_reco_urls_with_null_position() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Position cannot be null or negative");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                null,
                50.00,
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate Kohl's cash recommendations URLs with a negative
     * position
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_kohls_cash_reco_urls_with_negative_position() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Position cannot be null or negative");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                -2,
                50.00,
                null,
                null,
                null);
        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate Kohl's cash recommendations URLs with a null
     * channel id.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_kohls_cash_reco_urls_passing_a_null_channel_id() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("ChannelId cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(null,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                1,
                100.00,
                null,
                null,
                null);
        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate Kohl's cash recommendations URLs with a null
     * email type.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_kohls_cash_reco_urls_passing_a_null_email_type() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("EmailType cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                null,
                PlacementId.HORIZONTAL,
                1,
                100.00,
                null,
                null,
                null);
        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate Kohl's cash recommendations URLs with a null
     * placement id.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_kohls_cash_reco_urls_passing_a_null_placement_id() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("PlacementId cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                null,
                1,
                100.00,
                null,
                null,
                null);
        assertNull(urlResponses);
    }

    /**
     * Proper kohls cash email URLs should be generated when only all the required parameters are provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_only_all_required_params_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null,
                null,
                null,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters and customer email address
     * is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMifQ";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMifQ";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null,
                null,
                null,
                "bathiya.priyadarshana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters and Kohl's cash upper limit is
     * provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashUpperLimit_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMTUuMCJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMTUuMCJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null,
                null,
                15.00,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash upper limit and
     * customer email address is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashUpperLimit_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMTUuMCIsImN1c3RvbWVyRW1haWxIYXNoIjoiLTIwMTc0OTMxMzEtMTc2Mjc1MDkyMyJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMTUuMCIsImN1c3RvbWVyRW1haWxIYXNoIjoiLTIwMTc0OTMxMzEtMTc2Mjc1MDkyMyJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null,
                null,
                15.00,
                "bathiya.priyadarshana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters and Kohl's cash lower limit is
     * provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashLowerLimit_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiNTAuMCJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiNTAuMCJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null,
                50.00,
                null,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash lower limit and
     * customer email address is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashLowerLimit_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiNTAuMCIsImN1c3RvbWVyRW1haWxIYXNoIjoiLTIwMTc0OTMxMzEtMTc2Mjc1MDkyMyJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiNTAuMCIsImN1c3RvbWVyRW1haWxIYXNoIjoiLTIwMTc0OTMxMzEtMTc2Mjc1MDkyMyJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null,
                50.00,
                null,
                "bathiya.priyadarshana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash lower limit and
     * Kohl's cash upper limit is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashLowerLimit_kohlsCashUpperLimit_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuMCIsImtvaGxzQ2FzaExvd2VyTGltaXQiOiI1MC4wIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuMCIsImtvaGxzQ2FzaExvd2VyTGltaXQiOiI1MC4wIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null,
                50.00,
                25.00,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash lower limit,
     * Kohl's cash upper limit and customer email address is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashLowerLimit_kohlsCashUpperLimit_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuMCIsImtvaGxzQ2FzaExvd2VyTGltaXQiOiI1MC4wIiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuMCIsImtvaGxzQ2FzaExvd2VyTGltaXQiOiI1MC4wIiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null,
                50.00,
                25.00,
                "bathiya.priyadarshana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters and Kohl's cash amount is
     * provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashAmount_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                100.00,
                null,
                null,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash amount and
     * customer email is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashAmount_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMiLCJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMiLCJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                100.00,
                null,
                null,
                "bathiya.priyadarshana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash amount and
     * Kohl's cash upper limit is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashAmount_kohlsCashUpperLimit_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiNTAuNzUiLCJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiNTAuNzUiLCJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                100.00,
                null,
                50.75,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash amount,
     * Kohl's cash upper limit and customer email is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashAmount_kohlsCashUpperLimit_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=3&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiNTAuNzUiLCJjdXN0b21lckVtYWlsSGFzaCI6Ii0xMTk3OTU1MTkwLTk5NzI3NjAxNCIsImtvaGxzQ2FzaEFtb3VudCI6IjEwMC4wIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=3&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiNTAuNzUiLCJjdXN0b21lckVtYWlsSGFzaCI6Ii0xMTk3OTU1MTkwLTk5NzI3NjAxNCIsImtvaGxzQ2FzaEFtb3VudCI6IjEwMC4wIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                3,
                100.00,
                null,
                50.75,
                "rajeev.khurana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash amount and
     * Kohl's cash lower limit is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashAmount_kohlsCashLowerLimit_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=3&ccp=eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTUuNjUiLCJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=3&ccp=eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTUuNjUiLCJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                3,
                100.00,
                15.65,
                null,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash amount,
     * Kohl's cash lower limit and customer email is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashAmount_kohlsCashLowerLimit_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=3&ccp=eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTUuNjUiLCJjdXN0b21lckVtYWlsSGFzaCI6IjE3NDE4NDQ2MTkxNjY4MTE4Mjc0Iiwia29obHNDYXNoQW1vdW50IjoiMTAwLjAifQ";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=3&ccp=eyJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTUuNjUiLCJjdXN0b21lckVtYWlsSGFzaCI6IjE3NDE4NDQ2MTkxNjY4MTE4Mjc0Iiwia29obHNDYXNoQW1vdW50IjoiMTAwLjAifQ";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                3,
                100.00,
                15.65,
                null,
                "sunny.x.malik@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash amount,
     * Kohl's cash lower limit and Kohl's cash upper limit is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashAmount_kohlsCashLowerLimit_kohlsCashUpperLimit_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=2&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuNzYiLCJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTUuNjUiLCJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=2&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuNzYiLCJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTUuNjUiLCJrb2hsc0Nhc2hBbW91bnQiOiIxMDAuMCJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                2,
                100.00,
                15.65,
                25.76,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper kohls cash email URLs should be generated when all the required parameters, Kohl's cash amount,
     * Kohl's cash lower limit, Kohl's cash upper limit and customer email is provided
     *
     */
    @Test
    public void should_generate_proper_kohls_cash_email_urls_when_all_required_params_and_kohlsCashAmount_kohlsCashLowerLimit_kohlsCashUpperLimit_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=1&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuNzYiLCJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTUuNjUiLCJjdXN0b21lckVtYWlsSGFzaCI6Ii03NDg5ODU4MTExMjE0NDEzODI2Iiwia29obHNDYXNoQW1vdW50IjoiMTAwLjAifQ";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=1&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuNzYiLCJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTUuNjUiLCJjdXN0b21lckVtYWlsSGFzaCI6Ii03NDg5ODU4MTExMjE0NDEzODI2Iiwia29obHNDYXNoQW1vdW50IjoiMTAwLjAifQ";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                1,
                100.00,
                15.65,
                25.76,
                "thesus03@gmail.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * InvalidParameterException must be thrown when tried to generate shipment recommendations URLs with a null
     * position
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_shipment_reco_urls_with_null_position() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Position cannot be null or negative");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.PARTIAL_SHIPMENT,
                PlacementId.HORIZONTAL,
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate shipment recommendations URLs with a negative
     * position
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_shipment_reco_urls_with_negative_position() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Position cannot be null or negative");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.PARTIAL_SHIPMENT,
                PlacementId.HORIZONTAL,
                -2,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate shipment recommendations URLs with a null
     * channel id.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_shipment_reco_urls_passing_a_null_channel_id() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("ChannelId cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(null,
                EmailType.PARTIAL_SHIPMENT,
                PlacementId.HORIZONTAL,
                1,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate shipment recommendations URLs with a null
     * email type.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_shipment_reco_urls_passing_a_null_email_type() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("EmailType cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                null,
                PlacementId.HORIZONTAL,
                1,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate shipment recommendations URLs with a null
     * placement id.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_shipment_reco_urls_passing_a_null_placement_id() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("PlacementId cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.PARTIAL_SHIPMENT,
                null,
                1,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * Proper Partial shipment email URLs should be generated when only all the required parameters are provided
     *
     */
    @Test
    public void should_generate_proper_partial_shipment_email_urls_when_only_all_required_params_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=PartialShipment&plid=Horizontal&pos=0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=PartialShipment&plid=Horizontal&pos=0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.PARTIAL_SHIPMENT,
                PlacementId.HORIZONTAL,
                0,
                null,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper Complete shipment email URLs should be generated when only all the required parameters are provided
     *
     */
    @Test
    public void should_generate_proper_complete_shipment_email_urls_when_only_all_required_params_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=CompleteShipment&plid=Horizontal&pos=0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=CompleteShipment&plid=Horizontal&pos=0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.COMPLETE_SHIPMENT,
                PlacementId.HORIZONTAL,
                0,
                null,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper partial shipment email URLs should be generated when all the required parameters and customer email address
     * is provided
     *
     */
    @Test
    public void should_generate_proper_partial_shipment_email_urls_when_all_required_params_and_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=PartialShipment&plid=Horizontal&pos=0&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMifQ";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=PartialShipment&plid=Horizontal&pos=0&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMifQ";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.PARTIAL_SHIPMENT,
                PlacementId.HORIZONTAL,
                0,
                null,
                "bathiya.priyadarshana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper Complete shipment email URLs should be generated when all the required parameters and customer email address
     * is provided
     *
     */
    @Test
    public void should_generate_proper_complete_shipment_email_urls_when_all_required_params_and_customerEmail_is_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=CompleteShipment&plid=Horizontal&pos=0&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMifQ";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=CompleteShipment&plid=Horizontal&pos=0&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMifQ";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.COMPLETE_SHIPMENT,
                PlacementId.HORIZONTAL,
                0,
                null,
                "bathiya.priyadarshana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper partial shipment email URLs should be generated when all the required parameters and product numbers
     * are provided
     *
     */
    @Test
    public void should_generate_proper_partial_shipment_email_urls_when_all_required_params_and_product_numbers_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=PartialShipment&plid=Horizontal&pos=0&ccp=eyJwcm9kdWN0TnVtYmVycyI6IjEwMDAwNDcsMTAwMDA2NCwyMjEzMjIxIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=PartialShipment&plid=Horizontal&pos=0&ccp=eyJwcm9kdWN0TnVtYmVycyI6IjEwMDAwNDcsMTAwMDA2NCwyMjEzMjIxIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.PARTIAL_SHIPMENT,
                PlacementId.HORIZONTAL,
                0,
                Arrays.asList("1000047", "1000064", "2213221"),
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper Complete shipment email URLs should be generated when all the required parameters and product numbers
     * are provided
     *
     */
    @Test
    public void should_generate_proper_complete_shipment_email_urls_when_all_required_params_and_product_numbers_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=CompleteShipment&plid=Horizontal&pos=0&ccp=eyJwcm9kdWN0TnVtYmVycyI6IjEwMDAwNDcsMTAwMDA2NCwyMjEzMjIxIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=CompleteShipment&plid=Horizontal&pos=0&ccp=eyJwcm9kdWN0TnVtYmVycyI6IjEwMDAwNDcsMTAwMDA2NCwyMjEzMjIxIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.COMPLETE_SHIPMENT,
                PlacementId.HORIZONTAL,
                0,
                Arrays.asList("1000047", "1000064", "2213221"),
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper partial shipment email URLs should be generated when all the required parameters, customer email and
     * product numbers are provided
     *
     */
    @Test
    public void should_generate_proper_partial_shipment_email_urls_when_all_required_params_customer_email_and_product_numbers_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=PartialShipment&plid=Horizontal&pos=0&ccp=eyJwcm9kdWN0TnVtYmVycyI6IjEwMDAwNDcsMTAwMDA2NCwyMjEzMjIxIiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=PartialShipment&plid=Horizontal&pos=0&ccp=eyJwcm9kdWN0TnVtYmVycyI6IjEwMDAwNDcsMTAwMDA2NCwyMjEzMjIxIiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.PARTIAL_SHIPMENT,
                PlacementId.HORIZONTAL,
                0,
                Arrays.asList("1000047", "1000064", "2213221"),
                "bathiya.priyadarshana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper Complete shipment email URLs should be generated when all the required parameters, customer email and
     * product numbers are provided
     *
     */
    @Test
    public void should_generate_proper_complete_shipment_email_urls_when_all_required_params_customer_email_and_product_numbers_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=CompleteShipment&plid=Horizontal&pos=0&ccp=eyJwcm9kdWN0TnVtYmVycyI6IjEwMDAwNDcsMTAwMDA2NCwyMjEzMjIxIiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=CompleteShipment&plid=Horizontal&pos=0&ccp=eyJwcm9kdWN0TnVtYmVycyI6IjEwMDAwNDcsMTAwMDA2NCwyMjEzMjIxIiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateShipmentSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.COMPLETE_SHIPMENT,
                PlacementId.HORIZONTAL,
                0,
                Arrays.asList("1000047", "1000064", "2213221"),
                "bathiya.priyadarshana@kohls.com");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup URLs with a null
     * position
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_with_null_position() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Position cannot be null or negative");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                null,
                new LinkedList<String>(),
                new LinkedList<String>(),
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with a negative
     * position
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_with_negative_position() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Position cannot be null or negative");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                -2,
                new LinkedList<String>(),
                new LinkedList<String>(),
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with a null
     * channel id.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_urls_passing_a_null_channel_id() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("ChannelId cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(null,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                new LinkedList<String>(),
                new LinkedList<String>(),
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with a null
     * email type.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_passing_a_null_email_type() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("EmailType cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                null,
                PlacementId.HORIZONTAL,
                1,
                new LinkedList<String>(),
                new LinkedList<String>(),
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with a null
     * placement id.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_passing_a_null_placement_id() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("PlacementId cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                null,
                1,
                new LinkedList<String>(),
                new LinkedList<String>(),
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with a null
     * product number list
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_passing_a_null_product_number_list() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Product numbers list cannot be null or empty");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                null,
                new LinkedList<String>(),
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with an empty
     * product number list
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_passing_an_empty_product_number_list() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Product numbers list cannot be null or empty");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                new LinkedList<String>(),
                new LinkedList<String>(),
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with a null
     * store number list
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_passing_a_null_store_number_list() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Store numbers list cannot be null or empty");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                Arrays.asList("12345","45678"),
                null,
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with an empty
     * store number list
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_passing_an_empty_store_number_list() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Store numbers list cannot be null or empty");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                Arrays.asList("12345","45678"),
                new LinkedList<String>(),
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with a null
     * customer email address
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_passing_a_null_customer_email_address() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Customer email cannot be null or an empty string");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                Arrays.asList("12345","45678"),
                Arrays.asList("9991","9998"),
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS prior to pickup recommendations URLs with an empty
     * customer email address
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_prior_to_pickup_reco_urls_passing_an_empty_customer_email_address() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Customer email cannot be null or an empty string");

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                Arrays.asList("12345","45678"),
                Arrays.asList("9991","9998"),
                "",
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * Proper BOPUS prior to pickup URLs should be generated when all the required parameters are provided
     *
     */
    @Test
    public void should_generate_proper_BOPUS_prior_to_pickup_email_urls_when_all_required_params_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=Bopus1StoreOrderDelay&plid=Horizontal&pos=1&ccp=eyJzaGlwTm9kZXMiOiI5OTkxLDk5OTgiLCJwcm9kdWN0TnVtYmVycyI6IjEyMzQ1LDQ1Njc4IiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=Bopus1StoreOrderDelay&plid=Horizontal&pos=1&ccp=eyJzaGlwTm9kZXMiOiI5OTkxLDk5OTgiLCJwcm9kdWN0TnVtYmVycyI6IjEyMzQ1LDQ1Njc4IiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                Arrays.asList("12345", "45678"),
                Arrays.asList("9991", "9998"),
                "bathiya.priyadarshana@kohls.com",
                null,
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper BOPUS prior to pickup URLs should be generated when all the required parameters and order number is provided
     *
     */
    @Test
    public void should_generate_proper_BOPUS_prior_to_pickup_email_urls_when_all_required_params_and_order_number_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=Bopus1StoreOrderDelay&plid=Horizontal&pos=1&ccp=eyJzaGlwTm9kZXMiOiI5OTkxLDk5OTgiLCJwcm9kdWN0TnVtYmVycyI6IjEyMzQ1LDQ1Njc4Iiwib3JkZXJOdW1iZXIiOiI2Nzg5OCIsImN1c3RvbWVyRW1haWxIYXNoIjoiLTIwMTc0OTMxMzEtMTc2Mjc1MDkyMyJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=Bopus1StoreOrderDelay&plid=Horizontal&pos=1&ccp=eyJzaGlwTm9kZXMiOiI5OTkxLDk5OTgiLCJwcm9kdWN0TnVtYmVycyI6IjEyMzQ1LDQ1Njc4Iiwib3JkZXJOdW1iZXIiOiI2Nzg5OCIsImN1c3RvbWVyRW1haWxIYXNoIjoiLTIwMTc0OTMxMzEtMTc2Mjc1MDkyMyJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                Arrays.asList("12345", "45678"),
                Arrays.asList("9991", "9998"),
                "bathiya.priyadarshana@kohls.com",
                "67898",
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper BOPUS prior to pickup URLs should be generated when all the required parameters and Atg Id is provided
     *
     */
    @Test
    public void should_generate_proper_BOPUS_prior_to_pickup_email_urls_when_all_required_params_and_atg_id_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=Bopus1StoreOrderDelay&plid=Horizontal&pos=1&ccp=eyJzaGlwTm9kZXMiOiI5OTkxLDk5OTgiLCJwcm9kdWN0TnVtYmVycyI6IjEyMzQ1LDQ1Njc4IiwiYXRnSWQiOiI0NTY3ODkzNTY0IiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=Bopus1StoreOrderDelay&plid=Horizontal&pos=1&ccp=eyJzaGlwTm9kZXMiOiI5OTkxLDk5OTgiLCJwcm9kdWN0TnVtYmVycyI6IjEyMzQ1LDQ1Njc4IiwiYXRnSWQiOiI0NTY3ODkzNTY0IiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                Arrays.asList("12345", "45678"),
                Arrays.asList("9991", "9998"),
                "bathiya.priyadarshana@kohls.com",
                null,
                "4567893564");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper BOPUS prior to pickup URLs should be generated when all the required parameters and with both order number and Atg id
     *
     */
    @Test
    public void should_generate_proper_BOPUS_prior_to_pickup_email_urls_when_all_required_params_and_both_order_number_and_atg_id_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=Bopus1StoreOrderDelay&plid=Horizontal&pos=1&ccp=eyJzaGlwTm9kZXMiOiI5OTkxLDk5OTgiLCJwcm9kdWN0TnVtYmVycyI6IjEyMzQ1LDQ1Njc4Iiwib3JkZXJOdW1iZXIiOiI2Nzg5OCIsImF0Z0lkIjoiNDU2Nzg5MzU2NCIsImN1c3RvbWVyRW1haWxIYXNoIjoiLTIwMTc0OTMxMzEtMTc2Mjc1MDkyMyJ9";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=Bopus1StoreOrderDelay&plid=Horizontal&pos=1&ccp=eyJzaGlwTm9kZXMiOiI5OTkxLDk5OTgiLCJwcm9kdWN0TnVtYmVycyI6IjEyMzQ1LDQ1Njc4Iiwib3JkZXJOdW1iZXIiOiI2Nzg5OCIsImF0Z0lkIjoiNDU2Nzg5MzU2NCIsImN1c3RvbWVyRW1haWxIYXNoIjoiLTIwMTc0OTMxMzEtMTc2Mjc1MDkyMyJ9";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_ORDER_DELAY_SINGLE_STORE,
                PlacementId.HORIZONTAL,
                1,
                Arrays.asList("12345", "45678"),
                Arrays.asList("9991", "9998"),
                "bathiya.priyadarshana@kohls.com",
                "67898",
                "4567893564");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    System.out.println(urlResponse.getUrl());
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS after pickup URLs with a null
     * position
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_after_pickup_reco_urls_with_null_position() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Position cannot be null or negative");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION,
                PlacementId.HORIZONTAL,
                null,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS after pickup recommendations URLs with a negative
     * position
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_after_pickup_reco_urls_with_negative_position() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Position cannot be null or negative");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION,
                PlacementId.HORIZONTAL,
                -2,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS after pickup recommendations URLs with a null
     * channel id.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_after_pickup_urls_passing_a_null_channel_id() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("ChannelId cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateAfterPickupBopusSingleRecoImageAndProductUrls(null,
                EmailType.BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION,
                PlacementId.HORIZONTAL,
                1,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS after pickup recommendations URLs with a null
     * email type.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_after_pickup_reco_urls_passing_a_null_email_type() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("EmailType cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                null,
                PlacementId.HORIZONTAL,
                1,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS after pickup recommendations URLs with a null
     * placement id.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_after_pickup_reco_urls_passing_a_null_placement_id() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("PlacementId cannot be null");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION,
                null,
                1,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS after pickup recommendations URLs with a null
     * customer email address
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_after_pickup_reco_urls_passing_a_null_customer_email_address() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Customer email cannot be null or an empty string");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION,
                PlacementId.HORIZONTAL,
                1,
                null,
                null);

        assertNull(urlResponses);
    }

    /**
     * InvalidParameterException must be thrown when tried to generate BOPUS after pickup recommendations URLs with an empty
     * customer email address
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_tyring_to_generate_BOPUS_after_pickup_reco_urls_passing_an_empty_customer_email_address() throws InvalidParameterException {
        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_NON_ZERO_PORT);
        assertNotNull(emailUrlGenerator);

        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Customer email cannot be null or an empty string");

        List<UrlResponse> urlResponses = emailUrlGenerator.generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION,
                PlacementId.HORIZONTAL,
                1,
                "",
                null);

        assertNull(urlResponses);
    }

    /**
     * Proper BOPUS after pickup URLs should be generated when all the required parameters are provided
     *
     */
    @Test
    public void should_generate_proper_BOPUS_after_pickup_email_urls_when_all_required_params_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=BopusPickupPersonPickedUpConfirmation&plid=Horizontal&pos=1&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMifQ";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=BopusPickupPersonPickedUpConfirmation&plid=Horizontal&pos=1&ccp=eyJjdXN0b21lckVtYWlsSGFzaCI6Ii0yMDE3NDkzMTMxLTE3NjI3NTA5MjMifQ";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION,
                PlacementId.HORIZONTAL,
                1,
                "bathiya.priyadarshana@kohls.com",
                null);

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }

    /**
     * Proper BOPUS after pickup URLs should be generated when all the required parameters and atg id is provided
     *
     */
    @Test
    public void should_generate_proper_BOPUS_after_pickup_email_urls_when_all_required_params_and_atg_id_are_given() throws InvalidParameterException {
        String expectedSingleImgUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=TransEmail&etype=BopusPickupPersonPickedUpConfirmation&plid=Horizontal&pos=1&ccp=eyJhdGdJZCI6IjY3ODU5Nzk0OTc5NTg3IiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";
        String expectedSingleProdUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=TransEmail&etype=BopusPickupPersonPickedUpConfirmation&plid=Horizontal&pos=1&ccp=eyJhdGdJZCI6IjY3ODU5Nzk0OTc5NTg3IiwiY3VzdG9tZXJFbWFpbEhhc2giOiItMjAxNzQ5MzEzMS0xNzYyNzUwOTIzIn0";

        EmailUrlGenerator emailUrlGenerator = EdeEmailUrlGenerator.getInstance(VALID_PROTOCOL, HOST_NAME, VALID_ZERO_PORT);

        List<UrlResponse> urlResponses = emailUrlGenerator.generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId.TRANS_EMAIL,
                EmailType.BOPUS_PICKUP_PERSON_PICKEDUP_CONFIRMATION,
                PlacementId.HORIZONTAL,
                1,
                "bathiya.priyadarshana@kohls.com",
                "67859794979587");

        assertNotNull(urlResponses);
        assertEquals(EXPECTED_URL_COUNT_FOR_SINGLE_RECO_GENERATION, urlResponses.size());
        for(UrlResponse urlResponse : urlResponses) {
            switch(urlResponse.getUrlType()) {
                case EMAIL_SINGLE_IMG_REQ:
                    assertEquals(expectedSingleImgUrl, urlResponse.getUrl());
                    break;
                case EMAIL_SINGLE_PROD_REQ:
                    assertEquals(expectedSingleProdUrl, urlResponse.getUrl());
                    break;
                default:
                    fail("Unknown UrlType");
            }
        }
    }
}

