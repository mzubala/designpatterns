package pl.com.bottega.designpatterns.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserFactoryTest  {

    private UserFactory userFactory = new UserFactory();

    @Test
    public void adminShouldHaveInvoiceCorrectorRole() {
        User admin = userFactory.adminUser("test", "test");

        InvoiceCorrectorRole invoiceCorrector = admin.getRole(InvoiceCorrectorRole.class);

        assertThat(invoiceCorrector).isNotNull();
    }

    @Test
    public void adminShouldHaveOrderCorrectorRole() {
        User admin = userFactory.adminUser("test", "test");

        OrderCorrectorRole orderCorrectorRole = admin.getRole(OrderCorrectorRole.class);

        assertThat(orderCorrectorRole).isNotNull();
    }

    @Test
    public void supervisorShouldHaveInvoiceCorrectorRole() {
        User supervisor = userFactory.supervisorUser("test", "test");

        InvoiceCorrectorRole invoiceCorrector = supervisor.getRole(InvoiceCorrectorRole.class);

        assertThat(invoiceCorrector).isNotNull();
    }


    @Test
    public void standardUserShouldntHaveInvoiceCorrectorRole() {
        User standardUser = userFactory.standardUser("test", "test");

        assertThatThrownBy(() -> standardUser.getRole(InvoiceCorrectorRole.class)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void standardUserShouldntHaveOrderCorrectorRole() {
        User standardUser = userFactory.standardUser("test", "test");

        assertThatThrownBy(() -> standardUser.getRole(OrderCorrectorRole.class)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void changesUserViaTheRoleObject() {
        // given
        User supervisorUser = userFactory.supervisorUser("test", "test");

        // when
        supervisorUser.addRole(new OrderCorrectorRole());

        // then
        assertThat(supervisorUser.getRole(OrderCorrectorRole.class)).isInstanceOf(OrderCorrectorRole.class);
    }

}
