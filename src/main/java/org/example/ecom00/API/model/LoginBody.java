package org.example.ecom00.API.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginBody {
    /** The username to log in with. */
    @NotNull
    @NotBlank
    private String username;
    /** The password to log in with. */
    @NotNull
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
