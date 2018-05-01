package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urchin.controller.api.ImmutableMessageResponse;
import urchin.controller.api.MessageResponse;
import urchin.security.UrchinSecurityContext;
import urchin.service.AdminService;

@RestController
@RequestMapping("api/authenticate")
public class AuthenticateController {

    private static final MessageResponse OK = ImmutableMessageResponse.of("OK");
    private static final MessageResponse UNAUTHORIZED = ImmutableMessageResponse.of("UNAUTHORIZED");
    private static final MessageResponse NO_ADMIN_CONFIGURED = ImmutableMessageResponse.of("NO_ADMIN_CONFIGURED");
    private final AdminService adminService;

    @Autowired
    public AuthenticateController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MessageResponse> authenticated() {
        if (adminService.adminsMissing()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(NO_ADMIN_CONFIGURED);
        } else if (UrchinSecurityContext.isAuthenticated()) {
            return ResponseEntity.ok(OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UNAUTHORIZED);

    }


}
