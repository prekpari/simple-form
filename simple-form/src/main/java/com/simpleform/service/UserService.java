package com.simpleform.service;

import com.simpleform.model.Users;
import com.simpleform.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


@Service
public class UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users registerUser(String login, String password, String email) {
        if (StringUtils.isNotBlank(login) && StringUtils.isNotBlank(password)) {
            Users user = new Users();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);

            return usersRepository.save(user);
        }
        return null;
    }

    public Users authenticateUser(String login, String password) {
        return usersRepository.findByLoginAndPassword(login, password).orElse(null);
    }

    public int validateDuplicateEmail(String email){
        Integer count = usersRepository.countByEmail(email);
        return Objects.nonNull(count) ? count.intValue() : 0;
    }
}
