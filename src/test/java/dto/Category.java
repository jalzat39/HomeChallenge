package dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * Represents an immutable category with a unique ID and name.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Category {

    @Positive(message = "ID must be positive")
    private final int id;

    @NotBlank(message = "Name cannot be blank")
    private final String name;
}
