package nz.co.thescene.dto.json.hal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.hateoas.ResourceSupport;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("message")
public class MessageResource extends ResourceSupport {

    public static final class Rels {
        public static final String MESSAGE_THREAD = "thread";
        public static final String POSTER = "poster";
        public static final String REPLY = "reply";
        public static final String PARENT = "parent";
    }

    private String content;

    private long timestamp;

    public MessageResource() {}

    @JsonCreator
    public MessageResource(@JsonProperty("content") String content, @JsonProperty("time") long timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Time: " + timestamp +
                "\n" +
                "Content: " + content;
    }
}
