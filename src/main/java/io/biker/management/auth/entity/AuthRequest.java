package io.biker.management.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// FIXME: Change location to DTO's
public class AuthRequest {
    private String username;
    private String password;
}
