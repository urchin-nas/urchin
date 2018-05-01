package urchin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import urchin.cli.UserCli;
import urchin.exception.LinuxUserNotFoundException;
import urchin.model.user.*;
import urchin.repository.AdminRepository;

import java.time.LocalDateTime;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {

    private static final Username USERNAME = Username.of("username");
    private static final AdminId ADMIN_ID = AdminId.of(1);
    private static final Password PASSWORD = Password.of("superSecret007");
    private static final Admin ADMIN = ImmutableAdmin.builder()
            .adminId(ADMIN_ID)
            .username(USERNAME)
            .created(LocalDateTime.now())
            .build();

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private UserCli userCli;
    @InjectMocks
    private AdminService adminService;

    @Test
    public void addAdminIsSavedInAdminRepository() {
        when(userCli.checkIfUsernameExist(USERNAME)).thenReturn(true);
        when(adminRepository.saveAdmin(USERNAME)).thenReturn(ADMIN_ID);

        AdminId adminId = adminService.addAdmin(USERNAME);

        assertThat(adminId).isEqualTo(ADMIN_ID);
        verify(userCli).checkIfUsernameExist(USERNAME);
        verify(adminRepository).saveAdmin(USERNAME);
    }

    @Test(expected = LinuxUserNotFoundException.class)
    public void addAdminThatDoesNotExistThrowsException() {
        when(userCli.checkIfUsernameExist(USERNAME)).thenReturn(false);

        adminService.addAdmin(USERNAME);
        verifyZeroInteractions(adminRepository);
    }

    @Test
    public void removeAdminRemovesAdminFromAdminRepository() {
        adminService.removeAdmin(ADMIN_ID);

        verify(adminRepository).removeAdmin(ADMIN_ID);
    }

    @Test
    public void addFirstAdminIsSavedInAdminRepository() {
        when(userCli.checkIfUsernameExist(USERNAME)).thenReturn(true);
        when(adminRepository.getAdmins()).thenReturn(Collections.emptyList());
        when(adminRepository.saveAdmin(USERNAME)).thenReturn(ADMIN_ID);

        AdminId adminId = adminService.addFirstAdmin(USERNAME);

        assertThat(adminId).isEqualTo(ADMIN_ID);
        verify(userCli).checkIfUsernameExist(USERNAME);
        verify(adminRepository).saveAdmin(USERNAME);
    }

    @Test(expected = IllegalStateException.class)
    public void addFirstAdminWhenAdminAlreadyExistThrowsException() {

        when(adminRepository.getAdmins()).thenReturn(singletonList(ADMIN));

        adminService.addFirstAdmin(USERNAME);
    }

    @Test
    public void adminIsAuthenticated() {
        when(adminRepository.getAdminByUsername(USERNAME)).thenReturn(ADMIN);
        when(userCli.verifyPassword(ADMIN.asLinuxUser(), PASSWORD)).thenReturn(true);

        boolean authenticated = adminService.authenticateAdmin(USERNAME, PASSWORD);

        assertThat(authenticated).isTrue();
    }

}