package abc.docker.services.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SuccessResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseMeta meta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResponseMeta getMeta() {
        return meta;
    }

    public void setMeta(ResponseMeta meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

