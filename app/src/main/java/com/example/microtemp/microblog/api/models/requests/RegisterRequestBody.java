package com.example.microtemp.microblog.api.models.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequestBody extends RequestBody {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean admin;

    @Override
    public String getUrl() {
        return "/user/signup";
    }
}
