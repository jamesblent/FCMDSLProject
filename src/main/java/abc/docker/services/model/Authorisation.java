package abc.docker.services.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Authorisation {
    @JsonProperty("AuthorisationType")
    private String authorisationType;
    @JsonProperty("CompletionDateTime")
    private String completionDateTime;

    public String getAuthorisationType() {
        return authorisationType;
    }

    public void setAuthorisationType(String authorisationType) {
        this.authorisationType = authorisationType;
    }

    public String getCompletionDateTime() {
        return completionDateTime;
    }

    public void setCompletionDateTime(String completionDateTime) {
        this.completionDateTime = completionDateTime;
    }
}
