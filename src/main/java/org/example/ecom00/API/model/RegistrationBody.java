package org.example.ecom00.API.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

public class RegistrationBody {
    /** The username. */
    @NotNull
    @NotBlank
    @Size(min=3, max=255)
    private String username;
    /** The email. */
    @NotNull
    @NotBlank
    @Email
    private String email;
    /** The password. */
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    @Size(min=6, max=32)
    private String password;
    /** The first name. */
    @NotNull
    @NotBlank
    private String firstName;
    /** The last name. */
    @NotNull
    @NotBlank
    private String lastName;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 255) String username) {
        this.username = username;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public void setPassword(@NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$") @Size(min = 6, max = 32) String password) {
        this.password = password;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotBlank String lastName) {
        this.lastName = lastName;
    }
}
