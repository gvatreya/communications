package in.gvatreya.communications.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * This class provides reliable structure for Validation checks.
 * The {@code isValid} is {@code true}, when the object being validated
 * is valid, and {@code false} otherwise.
 * The {@code problems} will contain the list of problems, if any.
 */
@Getter @Setter @ToString
public class ValidationResponse {

    private boolean isValid;
    private List<String> problems;
}
