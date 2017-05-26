package asgard.prometheus.email.client.urlgenerators.email;

import asgard.prometheus.email.client.dto.UrlResponse;
import asgard.prometheus.email.client.enums.*;
import asgard.prometheus.email.client.exceptions.InvalidParameterException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Integration tests to verify the functionality of the EdeSingleRecoEmailUrlGenerator class.
 *
 */
public class EdeSingleRecoEmailUrlGeneratorIntegrationTest {
    //JUnit rule to verify the exceptions thrown
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //Email recommendations base URL
    private static final String EMAIL_RECO_BASE_URL = "https://api-atg.kohls.com/v1/ede/email/recommendations/";
    //Single email recommendation request base URLs
    private static final String SINGLE_IMAGE_RECO_BASE_URL = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single";
    private static final String SINGLE_PROD_RECO_BASE_URL = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single";

    /**
     * Private default constructor should create an instance if invoked.
     *
     */
    @Test
    public void should_create_instance_if_the_private_default_constructor_is_invoked() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<EdeSingleRecoEmailUrlGenerator> constructor = EdeSingleRecoEmailUrlGenerator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        EdeSingleRecoEmailUrlGenerator edeSingleRecoEmailUrlGenerator = constructor.newInstance();
        assertNotNull(edeSingleRecoEmailUrlGenerator);
    }

    /**
     * Argument accepting private constructor should create an instance if invoked.
     *
     */
    @Test
    public void should_create_instance_if_private_arg_accepting_constructor_is_invoked() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<EdeSingleRecoEmailUrlGenerator> constructor = EdeSingleRecoEmailUrlGenerator.class.getDeclaredConstructor(String.class, String.class);
        constructor.setAccessible(true);
        EdeSingleRecoEmailUrlGenerator edeSingleRecoEmailUrlGenerator = constructor.newInstance(SINGLE_IMAGE_RECO_BASE_URL, SINGLE_PROD_RECO_BASE_URL);
        assertNotNull(edeSingleRecoEmailUrlGenerator);

        String actualSingleImageRecBaseUrl = Whitebox.<String>getInternalState(edeSingleRecoEmailUrlGenerator, "singleImageRecBaseUrl");
        String actualSingleProductRecBaseUrl = Whitebox.<String>getInternalState(edeSingleRecoEmailUrlGenerator, "singleProductRecBaseUrl");

        assertEquals(SINGLE_IMAGE_RECO_BASE_URL, actualSingleImageRecBaseUrl);
        assertEquals(SINGLE_PROD_RECO_BASE_URL, actualSingleProductRecBaseUrl);
    }

    /**
     * InvalidParameterException should be thrown tried to create the instance passing a null base URL.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_trying_to_construct_EdeSingleRecoEmailUrlGenerator_with_null_base_url() throws InvalidParameterException {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Email recommendation base URL cannot be null or an empty string");

        SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance(null);
        assertNull(singleRecoEmailUrlGenerator);
    }

    /**
     * InvalidParameterException should be thrown tried to create the instance passing the base URL as an empty string.
     *
     */
    @Test
    public void should_throw_InvalidParameterException_when_trying_to_construct_EdeSingleRecoEmailUrlGenerator_with_empty_string_base_url() throws InvalidParameterException {
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("Email recommendation base URL cannot be null or an empty string");

        SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance("");
        assertNull(singleRecoEmailUrlGenerator);
    }

    /**
     * When a proper base URL is passed, the EdeSingleRecoEmailUrlGenerator instantiation must be successful.
     *
     */
    @Test
    public void should_create_the_proper_instance_when_tried_to_instantiate_EdeSingleRecoEmailUrlGenerator_with_proper_base_url() throws InvalidParameterException {
        SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance(EMAIL_RECO_BASE_URL);
        assertNotNull(singleRecoEmailUrlGenerator);

        String actualSingleImageRecBaseUrl = Whitebox.<String>getInternalState(singleRecoEmailUrlGenerator, "singleImageRecBaseUrl");
        String actualSingleProductRecBaseUrl = Whitebox.<String>getInternalState(singleRecoEmailUrlGenerator, "singleProductRecBaseUrl");

        assertEquals(SINGLE_IMAGE_RECO_BASE_URL, actualSingleImageRecBaseUrl);
        assertEquals(SINGLE_PROD_RECO_BASE_URL, actualSingleProductRecBaseUrl);
    }

    /**
     * Should generate proper single recommendation image URL when all parameters including ccp is provided
     *
     */
    @Test
    public void should_generate_the_expected_single_image_url_when_all_params_including_ccp_is_provided() throws InvalidParameterException {
        String expectedGeneratedSingleRecoImageUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuNTAiLCJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTAuMDAiLCJjdXN0b21lckVtYWlsSGFzaCI6Ijg3NjhBU0tKQkQ4dzhyeXF3ZUAkIyhcdTAwMjYlLTc2MjgzN2tqYnNmZFx1MDAyNl4qJCpSVCIsImtvaGxzQ2FzaEFtb3VudCI6IjUwLjAwIn0";

        SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance(EMAIL_RECO_BASE_URL);

        Map<String, String> ccp = new HashMap<>();
        ccp.put(ChannelContextParamName.KOHLS_CASH_AMOUNT.toString(), "50.00");
        ccp.put(ChannelContextParamName.KOHLS_CASH_LOWER_LIMIT.toString(), "10.00");
        ccp.put(ChannelContextParamName.KOHLS_CASH_UPPER_LIMIT.toString(), "25.50");
        ccp.put(ChannelContextParamName.CUSTOMER_EMAIL_HASH.toString(), "8768ASKJBD8w8ryqwe@$#(&%-762837kjbsfd&^*$*RT");

        UrlResponse singleImageRecoResponse = singleRecoEmailUrlGenerator.generateSingleRecoImageUrl(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                ccp);

        assertEquals(expectedGeneratedSingleRecoImageUrl, singleImageRecoResponse.getUrl());
        assertEquals(UrlType.EMAIL_SINGLE_IMG_REQ, singleImageRecoResponse.getUrlType());
    }

    /**
     * Should generate proper single recommendation image URL when all parameters with a null ccp is provided
     *
     */
    @Test
    public void should_generate_the_expected_single_image_url_when_all_params_with_a_null_ccp_is_provided() throws InvalidParameterException {
        String expectedGeneratedSingleRecoImageUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0";

        SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance(EMAIL_RECO_BASE_URL);

        UrlResponse singleImageRecoResponse = singleRecoEmailUrlGenerator.generateSingleRecoImageUrl(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null);

        assertEquals(expectedGeneratedSingleRecoImageUrl, singleImageRecoResponse.getUrl());
        assertEquals(UrlType.EMAIL_SINGLE_IMG_REQ, singleImageRecoResponse.getUrlType());
    }

    /**
     * Should generate proper single recommendation image URL when all parameters with an empty ccp is provided
     *
     */
    @Test
    public void should_generate_the_expected_single_image_url_when_all_params_with_an_empty_ccp_is_provided() throws InvalidParameterException {
        String expectedGeneratedSingleRecoImageUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/imgreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0";

        SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance(EMAIL_RECO_BASE_URL);

        UrlResponse singleImageRecoResponse = singleRecoEmailUrlGenerator.generateSingleRecoImageUrl(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                new HashMap<String, String>());

        assertEquals(expectedGeneratedSingleRecoImageUrl, singleImageRecoResponse.getUrl());
        assertEquals(UrlType.EMAIL_SINGLE_IMG_REQ, singleImageRecoResponse.getUrlType());
    }

    /**
     * Should generate proper single recommendation product URL when all parameters including ccp is provided
     *
     */
    @Test
    public void should_generate_the_expected_single_product_url_when_all_params_including_ccp_is_provided() throws InvalidParameterException {
        String expectedGeneratedSingleRecoProductUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0&ccp=eyJrb2hsc0Nhc2hVcHBlckxpbWl0IjoiMjUuNTAiLCJrb2hsc0Nhc2hMb3dlckxpbWl0IjoiMTAuMDAiLCJjdXN0b21lckVtYWlsSGFzaCI6Ijg3NjhBU0tKQkQ4dzhyeXF3ZUAkIyhcdTAwMjYlLTc2MjgzN2tqYnNmZFx1MDAyNl4qJCpSVCIsImtvaGxzQ2FzaEFtb3VudCI6IjUwLjAwIn0";

        SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance(EMAIL_RECO_BASE_URL);

        Map<String, String> ccp = new HashMap<>();
        ccp.put(ChannelContextParamName.KOHLS_CASH_AMOUNT.toString(), "50.00");
        ccp.put(ChannelContextParamName.KOHLS_CASH_LOWER_LIMIT.toString(), "10.00");
        ccp.put(ChannelContextParamName.KOHLS_CASH_UPPER_LIMIT.toString(), "25.50");
        ccp.put(ChannelContextParamName.CUSTOMER_EMAIL_HASH.toString(), "8768ASKJBD8w8ryqwe@$#(&%-762837kjbsfd&^*$*RT");

        UrlResponse singleProductRecoResponse = singleRecoEmailUrlGenerator.generateSingleRecoProductUrl(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                ccp);

        assertEquals(expectedGeneratedSingleRecoProductUrl, singleProductRecoResponse.getUrl());
        assertEquals(UrlType.EMAIL_SINGLE_PROD_REQ, singleProductRecoResponse.getUrlType());
    }

    /**
     * Should generate proper single recommendation product URL when all parameters with a null ccp is provided
     *
     */
    @Test
    public void should_generate_the_expected_single_product_url_when_all_params_with_a_null_ccp_is_provided() throws InvalidParameterException {
        String expectedGeneratedSingleRecoProductUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0";

        SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance(EMAIL_RECO_BASE_URL);

        UrlResponse singleProductRecoResponse = singleRecoEmailUrlGenerator.generateSingleRecoProductUrl(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                null);

        assertEquals(expectedGeneratedSingleRecoProductUrl, singleProductRecoResponse.getUrl());
        assertEquals(UrlType.EMAIL_SINGLE_PROD_REQ, singleProductRecoResponse.getUrlType());
    }

    /**
     * Should generate proper single recommendation product URL when all parameters with a null ccp is provided
     *
     */
    @Test
    public void should_generate_the_expected_single_product_url_when_all_params_with_an_empty_ccp_is_provided() throws InvalidParameterException {
        String expectedGeneratedSingleRecoProductUrl = "https://api-atg.kohls.com/v1/ede/email/recommendations/prdreq/single?cid=MktEmail&etype=KohlsCashGeneric&plid=Horizontal&pos=0";

        SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance(EMAIL_RECO_BASE_URL);

        UrlResponse singleProductRecoResponse = singleRecoEmailUrlGenerator.generateSingleRecoProductUrl(ChannelId.MKT_EMAIL,
                EmailType.KOHLS_CASH_GENERIC,
                PlacementId.HORIZONTAL,
                0,
                new HashMap<String, String>());

        assertEquals(expectedGeneratedSingleRecoProductUrl, singleProductRecoResponse.getUrl());
        assertEquals(UrlType.EMAIL_SINGLE_PROD_REQ, singleProductRecoResponse.getUrlType());
    }
}

