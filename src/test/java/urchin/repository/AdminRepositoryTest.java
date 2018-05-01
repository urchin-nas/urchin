package urchin.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.exception.AdminNotFoundException;
import urchin.model.user.Admin;
import urchin.model.user.AdminId;
import urchin.model.user.Username;
import urchin.testutil.TestApplication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class AdminRepositoryTest extends TestApplication {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void crd() {
        LocalDateTime now = LocalDateTime.now();
        Username username = Username.of("username");

        AdminId adminId = adminRepository.saveAdmin(username);
        Admin readAdmin = adminRepository.getAdmin(adminId);

        assertThat(readAdmin.getUsername()).isEqualTo(username);
        assertThat(now.isAfter(readAdmin.getCreated())).isFalse();

        adminRepository.removeAdmin(adminId);

        try {
            adminRepository.getAdmin(adminId);
            fail("expected exception");
        } catch (AdminNotFoundException ignore) {

        }
    }

    @Test
    public void getAdminByUsernameReturnsAdmin() {
        Username username = Username.of("username_2");
        adminRepository.saveAdmin(username);

        Admin admin = adminRepository.getAdminByUsername(username);

        assertThat(admin).isNotNull();
        assertThat(admin.getUsername()).isEqualTo(username);
    }

    @Test
    public void getAdminsReturnsAllAdmins() {
        Username username_1 = Username.of("username_3");
        Username username_2 = Username.of("username_4");
        Username username_3 = Username.of("username_5");
        adminRepository.saveAdmin(username_1);
        adminRepository.saveAdmin(username_2);
        adminRepository.saveAdmin(username_3);

        List<Admin> admins = adminRepository.getAdmins();

        List<Username> usernames = admins.stream()
                .map(Admin::getUsername)
                .collect(Collectors.toList());
        assertThat(usernames.contains(username_1)).isTrue();
        assertThat(usernames.contains(username_3)).isTrue();
        assertThat(usernames.contains(username_2)).isTrue();
    }
}