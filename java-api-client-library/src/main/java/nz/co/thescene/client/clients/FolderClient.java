package nz.co.thescene.client.clients;

import nz.co.thescene.dto.json.FolderRequest;
import nz.co.thescene.dto.json.hal.ApiResource;
import nz.co.thescene.dto.json.hal.FileResource;
import nz.co.thescene.dto.json.hal.FolderResource;
import nz.co.thescene.dto.json.hal.ImageMetaInfoResource;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import static nz.co.thescene.dto.json.hal.FolderResource.Rels;

public class FolderClient {

    private HttpService httpService = new HttpService();

    public Resources<FolderResource> getAllFolder(){
        ParameterizedTypeReference<Resources<FolderResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<FolderResource>>() {
        };
        ApiResource discover = httpService.discover();
        return httpService.get(resourceParameterizedTypeReference, discover, Rels.FOLDERS);
    }

    public FolderResource getFolder(FolderResource folder){
        ParameterizedTypeReference<FolderResource> resourceParameterizedTypeReference = new ParameterizedTypeReference<FolderResource>() {};
        return httpService.get(resourceParameterizedTypeReference, folder, Rels.SELF);
    }

    public FolderResource updateFolder(FolderResource folderResource){
        ModelMapper modelMapper = new ModelMapper();
		FolderRequest eventRequest = modelMapper.map(folderResource, FolderRequest.class);
		return httpService.put(FolderResource.class, folderResource, Rels.SELF, eventRequest);
    }

    public FileResource delete(FileResource fileResource){
        return httpService.delete(FileResource.class, fileResource, Rels.SELF);
    }

    public Resources<ImageMetaInfoResource> getImagesInFolder(FileResource folderResource){
        ParameterizedTypeReference<Resources<ImageMetaInfoResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<ImageMetaInfoResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, folderResource, Rels.IMAGES);
    }

	public FolderResource getParentFolder(FolderResource currentFolder) {
		return httpService.get(FolderResource.class, currentFolder, Rels.PARENT);
	}
    
	public Resources<FolderResource> getChildFolders(FolderResource folderResource) {
		ParameterizedTypeReference<Resources<FolderResource>> typeReference = new ParameterizedTypeReference<Resources<FolderResource>>(){};
		return httpService.get(typeReference,  folderResource, Rels.FOLDERS);
	}

	public Resources<FileResource> getFilesAndFolders(FolderResource currentFolder) {
		ParameterizedTypeReference<Resources<FileResource>> typeReference = new ParameterizedTypeReference<Resources<FileResource>>(){};
		return httpService.get(typeReference, currentFolder, Rels.FILES_AND_FOLDERS);
	}
	
	public Resources<FileResource> getFiles(FolderResource currentFolder) { 
		ParameterizedTypeReference<Resources<FileResource>> typeReference = new ParameterizedTypeReference<Resources<FileResource>>(){};
		return httpService.get(typeReference,  currentFolder, Rels.FILES);
	}

}
