package urchin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.cli.UserCli;
import urchin.exception.LinuxUserNotFoundException;
import urchin.model.user.Admin;
import urchin.model.user.AdminId;
import urchin.model.user.Password;
import urchin.model.user.Username;
import urchin.repository.AdminRepository;

import java.util.List;

@Service
public class AdminService {

    private final UserCli userCli;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(UserCli userCli, AdminRepository adminRepository) {
        this.userCli = userCli;
        this.adminRepository = adminRepository;
    }

    @Transactional
    public AdminId addAdmin(Username username) {
        if (userCli.checkIfUsernameExist(username)) {
            return adminRepository.saveAdmin(username);
        }
        throw new LinuxUserNotFoundException(username);
    }

    @Transactional
    public void removeAdmin(AdminId adminId) {
        Admin admin = adminRepository.getAdmin(adminId);
        adminRepository.removeAdmin(adminId);
    }

    public AdminId addFirstAdmin(Username username) {
        if (adminsMissing()) {
            return addAdmin(username);
        }
        throw new IllegalStateException("An admin already exist");
    }

    public List<Admin> getAdmins() {
        return adminRepository.getAdmins();
    }

    public Admin getAdmin(AdminId adminId) {
        return adminRepository.getAdmin(adminId);
    }

    public boolean adminsMissing() {
        return adminRepository.getAdmins().isEmpty();
    }

    public boolean authenticateAdmin(Username username, Password password) {
        Admin admin = adminRepository.getAdminByUsername(username);

        return userCli.verifyPassword(admin.asLinuxUser(), password);
    }
}
