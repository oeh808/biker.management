package io.biker.management.auth.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.biker.management.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "User Roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoles {
    @Id
    private int userId;
    private User user;
    private List<String> roles;
}
