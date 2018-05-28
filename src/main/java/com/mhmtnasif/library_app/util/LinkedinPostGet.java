package com.mhmtnasif.library_app.util;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.mhmtnasif.library_app.beans.Constants;
import org.json.JSONObject;

public class LinkedinPostGet {

	public LinkedInProfile sendPost(String code ) throws Exception {

		String url = "https://www.linkedin.com/oauth/v2/accessToken";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Host", "www.linkedin.com");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		String urlParameters = "grant_type=authorization_code&code="+code+"&redirect_uri="+Constants.redirect_url+"&client_id="+Constants.client_id+"&client_secret="+Constants.client_secret+"";
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JSONObject jsonObj = new JSONObject(response.toString());
		String access_token = jsonObj.getString("access_token");

		LinkedInProfile linkedInProfile=sendGet(access_token);
		return linkedInProfile;

	}
	
	
	
	private static LinkedInProfile sendGet(String access_token) throws Exception {
		
		LinkedInProfile linkedInProfile=new LinkedInProfile();
		String url = "https://api.linkedin.com/v1/people/~?format=json";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Host", "api.linkedin.com");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Authorization", "Bearer "+	access_token);
		
		

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JSONObject jsonObj = new JSONObject(response.toString());
		linkedInProfile.setFirstName(jsonObj.getString("firstName"));
		linkedInProfile.setLastName(jsonObj.getString("lastName"));
		linkedInProfile.setHeadline(jsonObj.getString("headline"));
		linkedInProfile.setId(jsonObj.getString("id"));
		return linkedInProfile;
	}
	

}