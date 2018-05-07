package urchin.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.user.AddAdminRequest;
import urchin.controller.api.user.ImmutableAddAdminRequest;
import urchin.model.user.Admin;
import urchin.service.AdminService;
import urchin.testutil.TestApplication;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthenticateControllerIT extends TestApplication {

    @Autowired
    private AdminService adminService;

    @Test
    public void authenticateAddAdminAndAuthenticate() {
        logoutAndReset();

        // try to authenticate

        ResponseEntity<MessageResponse> firstAuthenticatedResponse = getAuthenticatedRequest();

        assertThat(firstAuthenticatedResponse.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_REQUIRED);
        assertThat(firstAuthenticatedResponse.getBody()).isEqualTo(AuthenticateController.NO_ADMIN_CONFIGURED);

        // fulfill precondition by registering an admin

        ResponseEntity<MessageResponse> addAdminResponse = postAddAdminRequest(ImmutableAddAdminRequest.builder()
                .username(username.getValue())
                .build());

        assertThat(addAdminResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(addAdminResponse.getBody()).isEqualTo(AuthenticateController.OK);

        // try to authenticate

        ResponseEntity<MessageResponse> secondAuthenticatedResponse = getAuthenticatedRequest();

        assertThat(secondAuthenticatedResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(secondAuthenticatedResponse.getBody()).isEqualTo(AuthenticateController.UNAUTHORIZED);

        login();

        // try to authenticate

        ResponseEntity<MessageResponse> thirdAuthenticatedResponse = getAuthenticatedRequest();

        assertThat(thirdAuthenticatedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(thirdAuthenticatedResponse.getBody()).isEqualTo(AuthenticateController.OK);
    }

    private void logoutAndReset() {
        logout();
        adminService.getAdmins().stream()
                .map(Admin::getAdminId)
                .forEach(adminId -> adminService.removeAdmin(adminId));
        assertThat(adminService.getAdmins()).hasSize(0);
    }

    private ResponseEntity<MessageResponse> getAuthenticatedRequest() {
        return testRestTemplate.getForEntity(discoverControllerPath(), MessageResponse.class);
    }

    private ResponseEntity<MessageResponse> postAddAdminRequest(AddAdminRequest addAdminRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/add-first-admin", addAdminRequest, MessageResponse.class);
    }

}