package abc.docker.services.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "GROUP")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {
    public Group() {
    }

    @DatabaseField(columnName="GROUP_ID" ,id = true)
    private String groupId;

    @DatabaseField(columnName = "GROUP_NAME", canBeNull = false)
    private String groupName;

    @DatabaseField( columnName = "is_active", canBeNull = false)
    private String isActive;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
