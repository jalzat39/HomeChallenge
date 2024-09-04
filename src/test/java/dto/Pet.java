package dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Represents an immutable pet with various attributes including category, name, and status.
 */
@Getter
@ToString
@RequiredArgsConstructor
public class Pet {

    @Positive(message = "ID must be positive")
    private final int id;

    @NotNull(message = "Category cannot be null")
    private final Category category;

    @NotBlank(message = "Name cannot be blank")
    private final String name;

    private final List<String> photoUrls;

    private final List<Category> tags;

    @NotNull(message = "Status cannot be null")
    private final Status status;
}