package nz.co.thescene.dto.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("messageThread")
public class MessageThreadRequest {

    @NotEmpty
    @Size(max = 30)
    private String subject;

    public MessageThreadRequest() {}

    @JsonCreator
    public MessageThreadRequest(@JsonProperty("subject") String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
