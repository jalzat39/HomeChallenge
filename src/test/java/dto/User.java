package dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * Represents an immutable user with attributes including ID, username, and contact details.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class User {

    @Positive(message = "ID must be positive")
    private final int id;

    @NotBlank(message = "Username cannot be blank")
    private final String username;

    @NotBlank(message = "First name cannot be blank")
    private final String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private final String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private final String email;

    @NotBlank(message = "Password cannot be blank")
    private final String password;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private final String phone;

    @Positive(message = "User status must be positive")
    private final int userStatus;
}
