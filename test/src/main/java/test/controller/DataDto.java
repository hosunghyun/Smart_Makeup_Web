package test.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DataDto {
    private String value;

    public DataDto() {
    }

    public DataDto(String value) {
        this.value = value;
    }
}
