package nz.co.thescene.client.clients;

import nz.co.thescene.dto.json.FolderRequest;
import nz.co.thescene.dto.json.hal.FolderResource;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import nz.co.thescene.dto.json.MemberRegistrationRequest;
import nz.co.thescene.dto.json.MemberUpdateRequest;
import nz.co.thescene.dto.json.hal.ApiResource;
import nz.co.thescene.dto.json.hal.CollaboratorResource;
import nz.co.thescene.dto.json.hal.GrantResource;
import nz.co.thescene.dto.json.hal.GrantedAuthorityResource;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.MemberResource.Rels;

public class MemberClient{
	
	private HttpService service = new HttpService();
	
	public MemberResource register(MemberRegistrationRequest memberRequest) { 
		ParameterizedTypeReference<MemberResource> type = new ParameterizedTypeReference<MemberResource>() {};
		return service.postToBaseUrl(type, memberRequest, Rels.MEMBERS);
	}
	
	public MemberResource getMember(MemberResource memberDTO) {
		ParameterizedTypeReference<MemberResource> resourceParameterizedTypeReference = new ParameterizedTypeReference<MemberResource>() {}; 
		return service.get(resourceParameterizedTypeReference, memberDTO, Rels.SELF);
	}
	
	public MemberResource getMember(CollaboratorResource collaborator) {
		return service.get(MemberResource.class, collaborator, Rels.MEMBER);
	}

	public Resources<MemberResource> getMembers() {
		ApiResource apiRoot = service.discover();
		ParameterizedTypeReference<Resources<MemberResource>> resourceParameterizedTypeReference =
		        new ParameterizedTypeReference<Resources<MemberResource>>() {};
		return service.get(resourceParameterizedTypeReference, apiRoot, Rels.MEMBERS);
	}

	public Resources<GrantedAuthorityResource> getGrantedAuthoritiesForMember(MemberResource member) {
		ParameterizedTypeReference<Resources<GrantedAuthorityResource>> type = new ParameterizedTypeReference<Resources<GrantedAuthorityResource>>(){};
		return service.get(type, member, Rels.GRANTS);
	}
	
	public Resources<GrantResource> getGrantsForMember(MemberResource member) { 
		ParameterizedTypeReference<Resources<GrantResource>> type = new ParameterizedTypeReference<Resources<GrantResource>>(){};
		return service.get(type, member, Rels.GRANTS);
	}
	
	public GrantResource removeGrantFromMember(GrantResource grantResource) { 
		return service.delete(GrantResource.class, grantResource, Rels.SELF);
	}

	public MemberResource updateMember(MemberResource memberResource){
		ModelMapper modelMapper = new ModelMapper();
		MemberUpdateRequest memberUpdateRequest = modelMapper.map(memberResource, MemberUpdateRequest.class);
		return service.put(MemberResource.class, memberResource, Rels.SELF, memberUpdateRequest);
	}

	public Resources<FolderResource> getFolders(MemberResource member){
		ParameterizedTypeReference<Resources<FolderResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<FolderResource>>() {};
		return service.get(resourceParameterizedTypeReference, member, Rels.FOLDERS);
	}

	public FolderResource getRootFolder(MemberResource member){
		return service.get(FolderResource.class, member, Rels.FOLDERS);
	}

	public FolderResource createFolder(MemberResource member, FolderRequest request){
		return service.post(FolderResource.class, member, Rels.FOLDERS, request);
	}

	public Resources<MemberResource> search(String searchString) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("search", searchString);
		ParameterizedTypeReference<Resources<MemberResource>> typeReference = new ParameterizedTypeReference<Resources<MemberResource>>(){};
		return service.get(typeReference, service.discover(), parameters, Rels.MEMBERS);
	}

}