package urchin.testutil;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import static urchin.api.support.validation.ValidationConstants.FIELD_MISSING;

public class TestRequestDto {

    @NotNull(message = FIELD_MISSING)
    private String value;

    @NotNull
    private String valueWithoutCustomValidationMessage;

    @Max(value = 2, message = "FIELD_TOO_LARGE;Max value for field is 2")
    private int numberValue;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueWithoutCustomValidationMessage() {
        return valueWithoutCustomValidationMessage;
    }

    public void setValueWithoutCustomValidationMessage(String valueWithoutCustomValidationMessage) {
        this.valueWithoutCustomValidationMessage = valueWithoutCustomValidationMessage;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
    }
}
