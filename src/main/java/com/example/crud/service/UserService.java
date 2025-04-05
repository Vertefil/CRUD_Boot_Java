package com.example.crud.service;

import com.example.crud.repository.User;
import com.example.crud.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service // Позволяет реализовать dependency injection
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        user.setAge(Period.between(user.getDob(), LocalDate.now()).getYears());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            throw new IllegalStateException("User with id: "+id+" does not exist");
        }
        userRepository.deleteById(id);
    }

    @Transactional //Пометка для отката изменений, если выбросился exception
    public void update(Long id, String email, String name) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            throw new IllegalStateException("User already exists");
        }
        User user = optionalUser.get();
        if(!email.equals(user.getEmail()) && email != null) {
            Optional<User> foundByEmail = userRepository.findByEmail(email);
            if(foundByEmail.isPresent()) {
                throw new IllegalStateException("User already exists");
            }
            user.setEmail(email);
        }
        if(name != null && !name.equals(user.getName()) ) {
            user.setName(name);
        }

        //userRepository.save(user); не обязательно, так как через сеттеры
    }
}
