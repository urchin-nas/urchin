package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.ImmutableMessageResponse;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.user.AddAdminRequest;
import urchin.model.user.Username;
import urchin.security.UrchinSecurityContext;
import urchin.service.AdminService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/authenticate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthenticateController {

    static final MessageResponse OK = ImmutableMessageResponse.of("OK");
    static final MessageResponse UNAUTHORIZED = ImmutableMessageResponse.of("UNAUTHORIZED");
    static final MessageResponse NO_ADMIN_CONFIGURED = ImmutableMessageResponse.of("NO_ADMIN_CONFIGURED");

    private final AdminService adminService;

    @Autowired
    public AuthenticateController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<MessageResponse> authenticated() {
        if (adminService.adminsMissing()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(NO_ADMIN_CONFIGURED);
        } else if (UrchinSecurityContext.isAuthorized()) {
            return ResponseEntity.ok(OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UNAUTHORIZED);
    }

    @PostMapping(value = "add-first-admin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse addFirstAdmin(@Valid @RequestBody AddAdminRequest addAdminRequest) {
        adminService.addFirstAdmin(Username.of(addAdminRequest.getUsername()));
        return OK;
    }
}
