package nz.co.thescene.dto.json;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("messageThreadSubscriber")
public class MessageThreadSubscriberRequest {

    @NotEmpty
    private String memberId;

    public MessageThreadSubscriberRequest() {}

    @JsonCreator
    public MessageThreadSubscriberRequest(@JsonProperty("memberId") String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
