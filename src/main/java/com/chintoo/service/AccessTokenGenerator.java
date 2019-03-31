package com.chintoo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Version;

@Service
public class AccessTokenGenerator {
	
	@Value("${fb.accessToken}")
	private String accessToken;
	
	@Value("${fb.myAppId}")
	private String myAppId;
	
	@Value("${fb.myAppSecret}")
	private String myAppSecret;

	public String tokenGenerator()
	{
		
			AccessToken accessTokenGenerated =
					  new DefaultFacebookClient(Version.LATEST).obtainExtendedAccessToken(myAppId,
							  myAppSecret, accessToken);

					System.out.println("My extended access token: " + accessTokenGenerated);
					
					return accessTokenGenerated.toString();
		
	}
	
	}
