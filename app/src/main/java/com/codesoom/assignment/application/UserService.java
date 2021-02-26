package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(
            Mapper dozerMapper,
            UserRepository userRepository
    ) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return findUser(id);
    }

    public User createProduct(UserData userData) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        User user = mapper.map(userData, User.class);

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);

        user.updateWith(mapper.map(userData, User.class));

        return user;
    }

    public User deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user;
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
