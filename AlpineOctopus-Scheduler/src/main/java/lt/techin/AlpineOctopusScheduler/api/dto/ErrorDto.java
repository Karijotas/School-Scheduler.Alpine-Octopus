package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ErrorDto {

    private String url;
    private List<ErrorFieldDto> fields;
    private String message;
    private Integer status;
    private String error;
    private String path;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public ErrorDto(String url, List<ErrorFieldDto> fields, String message, Integer status, String error, String path, LocalDateTime timestamp) {
        this.url = url;
        this.fields = fields;
        this.message = message;
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ErrorFieldDto> getFields() {
        return fields;
    }

    public void setFields(List<ErrorFieldDto> fields) {
        this.fields = fields;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDto errorDto = (ErrorDto) o;
        return Objects.equals(url, errorDto.url) && Objects.equals(fields, errorDto.fields) && Objects.equals(message, errorDto.message) && Objects.equals(status, errorDto.status) && Objects.equals(error, errorDto.error) && Objects.equals(path, errorDto.path) && Objects.equals(timestamp, errorDto.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, fields, message, status, error, path, timestamp);
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "url='" + url + '\'' +
                ", fields=" + fields +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
