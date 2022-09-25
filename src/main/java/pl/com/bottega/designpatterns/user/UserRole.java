package pl.com.bottega.designpatterns.user;

import java.time.Clock;

public abstract class UserRole implements User {

    private UserCore userCore;

    private Long id;

    public UserRole(UserCore userCore) {
        this.userCore = userCore;
    }

    UserRole() {}

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
        // TODO
    }

    @Override
    public void saveLastLoginDate(Clock clock) {
        // TODO
    }

    void setUserCore(UserCore userCore) {
        this.userCore = userCore;
    }
}
