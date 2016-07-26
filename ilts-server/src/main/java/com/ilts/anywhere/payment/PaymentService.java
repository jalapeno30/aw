package com.ilts.anywhere.payment;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilts.anywhere.coreplatform.CorePlatform;
import com.ilts.anywhere.datastore.AnywhereDB;
import com.ilts.anywhere.response.Response;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

@Service
@ComponentScan
public class PaymentService {

	@Autowired
	AnywhereDB anywhereDb;

	@Autowired
	CorePlatform cp;

	private String redirectUri = "";
	private String clientID = "ARbzymMPTNvi-mWpotVT9pehwV2iI29Z7w9ByXyn4-bajgWF_JXC432u3UngL4KGP1t-Ub7Quz_bCQFd";
	private String clientSecret = "EFtjd9CsVuz2neSBtZ3Ewxc-FxBJUvsoz-4uQc40BMrPsK0ZF6O2Fpchmc-i7SJTLzaBe7cth7OuvK_h";

                    public String getRedirectUri() {
		return this.redirectUri;
	}
	
	public void setRedirectUri(String uri) {
		this.redirectUri = uri;
	}
	
	public Boolean hasFunding(ArrayList<String> orderID, String date, String userId) {
                        System.out.println(">>>>>>> hasFunding(ArrayList<String> orderID, String date, String userId) ");
                    
                                    System.out.println(">>>>>>> hasFunding String userId "+userId);

		String paypalUri = this.paypalFunding(orderID, date, userId);
                 System.out.println(">>>>>>> hasFunding paypalUri  "+paypalUri);
		if (paypalUri != null && paypalUri.length() > 0) {
			this.setRedirectUri(paypalUri);
			return true;
		} else {
			return false;
		}
	}

	private String getPaypalAccessToken() throws PayPalRESTException {
		return new OAuthTokenCredential(this.clientID, this.clientSecret).getAccessToken();
	}

	public String paypalFunding(ArrayList<String> orderID, String date, String userId) {
		System.out.println(">>>>>>>  paypalFunding(ArrayList<String> orderID, String date, String userId) "+userId);

		// get total cost
		final String purchaseDate = date;
		
		// create paypal purchase entry in DB
		final PaypalPurchase paypalPurchase = this.anywhereDb.createPaypalPurchase(orderID, date, userId);

		String accessToken = null;
		try {
			accessToken = this.getPaypalAccessToken();
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}

		final ArrayList<PaypalOrder> PaypalItems = this.anywhereDb.getPaypalPurchases(orderID);
		final ArrayList<Object> paypalItemObjects = new ArrayList<Object>();
		for (final PaypalOrder paypalItem: PaypalItems) {
			paypalItemObjects.add(new Object(){
				String quantity = "1";//paypalItem.getQuantity().toString();
				String name = paypalItem.getGameName() + " " + paypalItem.getDrawDate();
				String price = paypalItem.getOrderCost().toString() + ".00";
				String currency = "PHP";
			});
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", accessToken);
		final String redirectEndpoint = "http://127.0.0.1:9002";
//		final String redirectEndpoint = "http://54.254.150.172";
		Object test = new Object(){
			String intent = "authorize";
			Object payer = new Object() {
				String payment_method = "paypal";
			}; 
			ArrayList<Object> transactions = new ArrayList<Object>(){{
				add(new Object(){
					Object amount = new Object() {
						String total = paypalPurchase.getCost().toString() + ".00";
						String currency = "PHP";
						Object details = new Object(){
							String subtotal = paypalPurchase.getCost().toString() + ".00";
							String tax = "0.00";
							String shipping = "0.00";
						};
					};
					String description = "Betting purchase for " + purchaseDate;
					Object item_list = new Object(){
						Object items = paypalItemObjects;
					};
				});
			}};
			Object redirect_urls = new Object(){
				String return_url = redirectEndpoint + "/#/paypal-fund-confirm/" + paypalPurchase.getConfirmCode();
				String cancel_url = redirectEndpoint + "/#/paypal-fund-cancel/" + paypalPurchase.getCancelCode();
			};
		};
		String JSON = "";
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try {
			JSON = mapper.writeValueAsString(test);
			System.out.println(JSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpEntity entity = new HttpEntity(JSON, headers);
		RestTemplate restTemplate = new RestTemplate();
		this.anywhereDb.initiateFundingRequest(paypalPurchase.getId());
				System.out.println(">>>>>>> AFTER paypalFunding(ArrayList<String> orderID, String date, String userId) "
                                        + "anywhereDb.initiateFundingRequest(paypalPurchase.getId()) "+paypalPurchase.getId());

		HttpEntity<String> result = restTemplate.exchange("https://api.sandbox.paypal.com/v1/payments/payment", HttpMethod.POST, entity, String.class, "");
		
		final PaypalResponse paypalResponse;
		
		String redirectURI = "";
		try {
			ObjectMapper paypalMapper = new ObjectMapper();
			
			paypalMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			System.out.println(" >> Result GETBODY PAYPAL FUNDING: "+result.getBody());
			paypalResponse = paypalMapper.readValue(result.getBody(), PaypalResponse.class);
                        System.out.println("PAYPAL  ****"+paypalResponse);
                         System.out.println("PAYPAL  ****"+paypalResponse.getId());
                          System.out.println("PAYPAL  ****"+paypalResponse.getIntent());
                           System.out.println("PAYPAL  ****"+paypalResponse.getState());
                            System.out.println("PAYPAL  ****"+paypalResponse.getLinks());
			ArrayList<PaypalLink> links = paypalResponse.getLinks();
			
			for (PaypalLink link: links) {
				if (link.getMethod().equals("REDIRECT")) {
					redirectURI = link.getHref();
					break;
				}
			}

			this.anywhereDb.setPaypalPurchaseLinks(paypalPurchase.getId(), links);
			System.out.println(">>>>>>> AFTER  "
                                        + "anywhereDb.setPaypalPurchaseLinks(paypalPurchase.getId(), links)) "+paypalPurchase.getId());
		} catch (JsonGenerationException e) {
 
			e.printStackTrace();
	 
		} catch (IOException e) {
	 
			e.printStackTrace();
	 
		}
		
		return "http://localhost:8080/payment/webapi/test";


	}
	
	public void confirmPaypalPurchase(String payerId, String confirmId) {
		final String payer = payerId;

		String accessToken = null;
		try {
			accessToken = this.getPaypalAccessToken();
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		
		// retrieve execute URI
		String executeUri = this.anywhereDb.getExecuteURI(confirmId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", accessToken);
		
		Object test = new Object(){
			String payer_id = payer;
		};
		String JSON = "";
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		try {
			JSON = mapper.writeValueAsString(test);
			System.out.println(JSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpEntity entity = new HttpEntity(JSON, headers);
		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<String> result = restTemplate.exchange(executeUri, HttpMethod.POST, entity, String.class, "");
		System.out.println(" >> Result GETBODY PAYPAL confirmPaypalPurchase: "+result.getBody());

		this.cp.confirmPurchase(confirmId);
	}

	public void cancelPaypalPurchase(String cancelId) {
//		AnywhereDB db = new AnywhereDB();
		this.anywhereDb.cancelPurchase(cancelId);
		
	}
	
	public Response refundBetPayment()
	{
		return null;
	}

}