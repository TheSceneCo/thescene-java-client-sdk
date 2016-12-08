package nz.co.thescene.dto.json.hal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.hateoas.ResourceSupport;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("messageThread")
public class MessageThreadResource extends ResourceSupport {

    public static final class Rels {
        public static final String MESSAGES = "messages";
        public static final String SUBSCRIBERS = "subscribers";
        public static final String CREATOR = "creator";
        public static final String THREAD = "thread";
        public static final String DELETE_SUBSCRIBER = "out";
    }

    private String subject;

    public MessageThreadResource() {}

    @JsonCreator
    public MessageThreadResource(@JsonProperty("subject") String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "MessageThreadResource{" +
                "subject='" + subject + '\'' +
                '}';
    }
}
