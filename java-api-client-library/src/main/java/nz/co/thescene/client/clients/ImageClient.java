package nz.co.thescene.client.clients;

import nz.co.thescene.dto.json.ProfilePhotoRequest;
import nz.co.thescene.dto.json.hal.FileResource;
import nz.co.thescene.dto.json.hal.FolderResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.ProfileResource;
import org.springframework.core.ParameterizedTypeReference;

import nz.co.thescene.dto.json.hal.ImageMetaInfoResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.hateoas.Resources;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ImageClient {

	private HttpService httpService = new HttpService();
	
	public ImageMetaInfoResource getImageMetaInfo(FileResource imageMetaInfoResource){
		ParameterizedTypeReference<ImageMetaInfoResource> resourceParameterizedTypeReference = new ParameterizedTypeReference<ImageMetaInfoResource>() {};
		return httpService.get(resourceParameterizedTypeReference, imageMetaInfoResource, "self");
	}
	
	public BufferedImage getImage(ImageMetaInfoResource imageMetaInfoResource) throws MalformedURLException, IOException { 
		return httpService.getImage(imageMetaInfoResource);
	}

	public ImageMetaInfoResource deleteImage(ImageMetaInfoResource imageMetaInfoResource){
		return httpService.delete(ImageMetaInfoResource.class, imageMetaInfoResource, "self");
	}

	public Resources<ImageMetaInfoResource> uploadImage(FolderResource folder, List<String> imageLocations) {

		MultiValueMap<String, Object> requestParameters = new LinkedMultiValueMap<String, Object>();

		for(String filePath : imageLocations) {
			Path path = Paths.get(filePath);

			ByteArrayResource contentsAsResource = null;
			try {
				contentsAsResource = new ByteArrayResource(Files.readAllBytes(path)) {
					@Override
					public String getFilename() {
						return path.getFileName().toString();
					}
				};
			} catch (IOException e) {
				e.printStackTrace();
			}

			requestParameters.add("files", contentsAsResource);

		}

		ParameterizedTypeReference<Resources<ImageMetaInfoResource>> returnType = new ParameterizedTypeReference<Resources<ImageMetaInfoResource>>() {};
		return httpService.postFiles(returnType, folder, "images", requestParameters);
	}


	public ImageMetaInfoResource getProfilePhoto(ProfileResource profile){
		return httpService.get(ImageMetaInfoResource.class, profile, "imageMetaInfo");
	}

	public ImageMetaInfoResource setProfilePhoto(ProfileResource profile, ImageMetaInfoResource imageMetaInfoResource){
		ProfilePhotoRequest request = new ProfilePhotoRequest();
		request.setImageMetaInfo(imageMetaInfoResource.getFileId());
		return httpService.put(ImageMetaInfoResource.class, profile, "imageMetaInfo", request);
	}

	public ImageMetaInfoResource deleteProfilePhoto(ProfileResource profile){
		return httpService.delete(ImageMetaInfoResource.class, profile, "imageMetaInfo");
	}

	public ImageMetaInfoResource getMemberPhoto(MemberResource member){
		return httpService.get(ImageMetaInfoResource.class, member, "imageMetaInfo");
	}

	public ImageMetaInfoResource setMemberPhoto(MemberResource member, ImageMetaInfoResource imageMetaInfoResource){
		ProfilePhotoRequest request = new ProfilePhotoRequest();
		request.setImageMetaInfo(imageMetaInfoResource.getFileId());
		return httpService.put(ImageMetaInfoResource.class, member, "imageMetaInfo", request);
	}

	public ImageMetaInfoResource deleteMemberPhoto(MemberResource member){
		return httpService.delete(ImageMetaInfoResource.class, member, "imageMetaInfo");
	}



}
