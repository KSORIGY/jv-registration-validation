package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null) {
            throw new RegistrationException("User fields cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login " + user.getLogin() + " is already exists");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Username/Login must be at least 6 characters");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Age must be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
