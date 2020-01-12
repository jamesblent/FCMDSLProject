package abc.docker.services.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TOPIC")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Topic {
    public Topic() {
    }

    @DatabaseField(columnName="TOPIC_ID" ,id = true)
    private String topicId;

    @DatabaseField(columnName = "TOPIC_NAME", canBeNull = false)
    private String topicName;

    @DatabaseField( columnName = "is_active", canBeNull = false)
    private String isActive;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
