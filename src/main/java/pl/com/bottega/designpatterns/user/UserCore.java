package pl.com.bottega.designpatterns.user;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserCore implements User {

    private Set<UserRole> roles = new HashSet<>();

    private Long id;

    private String login, password;

    private LocalDateTime lastLogin;

    UserCore() {
    }

    UserCore(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public <T extends UserRole> T getRole(Class<T> roleClass) {
        // TODO
        return null;
    }

    @Override
    public void addRole(UserRole userRole) {
        // TODO
    }

    @Override
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    @Override
    public void saveLastLoginDate(Clock clock) {
        this.lastLogin = LocalDateTime.now(clock);
    }
}
