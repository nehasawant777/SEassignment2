package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Controller
public class ProfileController {
	
	@Value("${access_id}")
	String id;
	@Value("${access_key}")
	String key;
	
	@GetMapping(value="")
	public ModelAndView renderPage() {
		
		ModelAndView indexPage= new ModelAndView();
		
		indexPage.setViewName("index");
		return indexPage;
		
		
	}
	
	@PostMapping(value="/upload")
	public ModelAndView uploadToS3(
			@RequestParam("file") MultipartFile image) {
		ModelAndView ProfilePage= new ModelAndView();
		BasicAWSCredentials cred= new BasicAWSCredentials(id,key);
		AmazonS3 s3client= AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(cred))
				.withRegion (Regions.US_EAST_2)
				.build();
		try {
			PutObjectRequest putReq=new PutObjectRequest
					("nehasawant",image.getOriginalFilename(),image.getInputStream(),new ObjectMetadata())
		.withCannedAcl(CannedAccessControlList.PublicRead);
			
			s3client.putObject(putReq);
			
			String imgSrc= "http://"+ "nehasawant" + ".s3.amazonaws.com/" + image.getOriginalFilename();
			ProfilePage.addObject("imgSrc",imgSrc);
			ProfilePage.setViewName("ProfilePage");
			return ProfilePage;
			
		}
		catch(IOException e) {
			e.printStackTrace();
			ProfilePage.setViewName("error");
			return ProfilePage;
		}	
	}

}
