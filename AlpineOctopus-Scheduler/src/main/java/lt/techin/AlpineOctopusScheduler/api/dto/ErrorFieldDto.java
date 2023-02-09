package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class ErrorFieldDto {

    private String name;
    private String error;

    private String rejectedValue;

    public ErrorFieldDto() {
    }

    public ErrorFieldDto(String name, String error, String rejectedValue) {
        this.name = name;
        this.error = error;
        this.rejectedValue = rejectedValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorFieldDto that = (ErrorFieldDto) o;
        return Objects.equals(name, that.name) && Objects.equals(error, that.error) && Objects.equals(rejectedValue, that.rejectedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, error, rejectedValue);
    }

    @Override
    public String toString() {
        return "ErrorFieldDto{" +
                "name='" + name + '\'' +
                ", error='" + error + '\'' +
                ", rejectedValue='" + rejectedValue + '\'' +
                '}';
    }
}