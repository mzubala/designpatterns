package pl.com.bottega.designpatterns.user;

import java.time.Clock;

public interface User {

    <T extends UserRole> T getRole(Class<T> roleClass);

    void addRole(UserRole userRole);

    void changePassword(String newPassword);

    void saveLastLoginDate(Clock clock);

}
