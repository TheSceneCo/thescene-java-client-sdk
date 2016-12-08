package nz.co.thescene.client.clients;


import nz.co.thescene.dto.json.MessageRequest;
import nz.co.thescene.dto.json.MessageThreadRequest;
import nz.co.thescene.dto.json.MessageThreadSubscriberRequest;
import nz.co.thescene.dto.json.hal.MemberResource;
import nz.co.thescene.dto.json.hal.MessageResource;
import nz.co.thescene.dto.json.hal.MessageThreadResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;

public class MessageClient {

    public final String SELF = "self";

    private HttpService httpService = new HttpService();

    public MessageThreadResource createMessageThread(MemberResource member, MessageThreadRequest request){
             return httpService.post(MessageThreadResource.class, member, MemberResource.Rels.THREADS, request);
    }

    public MessageThreadResource getMessageThread(MessageThreadResource resource){
        return httpService.get(MessageThreadResource.class, resource, SELF);
    }

    public MemberResource addSubscriber(MessageThreadResource threadResource, MemberResource member){
        MessageThreadSubscriberRequest request = new MessageThreadSubscriberRequest();
        request.setMemberId(member.getMemberId());
        return httpService.post(MemberResource.class, threadResource, MessageThreadResource.Rels.SUBSCRIBERS, request);
    }

    public Resources<MemberResource> getSubscribers(MessageThreadResource threadResource){
        ParameterizedTypeReference<Resources<MemberResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<MemberResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, threadResource, MessageThreadResource.Rels.SUBSCRIBERS);
    }

    public MemberResource deleteSubscriber(MessageThreadResource messageThreadResource, MemberResource resource){
        return httpService.delete(MemberResource.class, resource, MessageThreadResource.Rels.DELETE_SUBSCRIBER, messageThreadResource);
    }

    public Resources<MessageThreadResource> getMessageThreadsOfMember(MemberResource memberResource){
        ParameterizedTypeReference<Resources<MessageThreadResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<MessageThreadResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, memberResource, MemberResource.Rels.THREADS);
    }

    public MessageThreadResource deleteMessageThread(MessageThreadResource messageThreadResource){
        return httpService.delete(MessageThreadResource.class, messageThreadResource, SELF);
    }

    public MessageThreadResource updateThread(MessageThreadResource messageThreadResource, MessageThreadRequest request){
        return httpService.put(MessageThreadResource.class, messageThreadResource, SELF, request);
    }

    public Resources<MessageResource> getMessagesInThread(MessageThreadResource messageThreadResource){
        ParameterizedTypeReference<Resources<MessageResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<MessageResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, messageThreadResource, MessageThreadResource.Rels.MESSAGES);
    }

    public MessageResource sendMessage(MessageThreadResource messageThreadResource, MessageRequest request){
        return httpService.post(MessageResource.class, messageThreadResource, MessageThreadResource.Rels.MESSAGES, request);
    }

    public MessageResource replyInMessage(MessageRequest request, MessageResource resource){
        return httpService.post(MessageResource.class, resource, MessageResource.Rels.REPLY, request);
    }

    public Resources<MessageResource> getReplies(MessageResource resource){
        ParameterizedTypeReference<Resources<MessageResource>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<MessageResource>>() {};
        return httpService.get(resourceParameterizedTypeReference, resource, MessageResource.Rels.REPLY);
    }

    public MessageResource deleteMessage(MessageResource resource){
        return httpService.delete(MessageResource.class, resource, SELF);
    }





}
