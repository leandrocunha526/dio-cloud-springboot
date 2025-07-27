package me.dio.cloud.service.impl;

import me.dio.cloud.service.exception.BusinessException;
import me.dio.cloud.service.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import me.dio.cloud.domain.model.User;
import me.dio.cloud.domain.repository.UserRepository;
import me.dio.cloud.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class UserServiceImpl implements UserService {

    /**
     * Criação de algumas regras para mantê-lo integro.
     */
    private static final Long UNCHANGEABLE_USER_ID = 1L;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void validateChangeableId(Long id, String operation) {
        if(UNCHANGEABLE_USER_ID.equals(id)){
            throw new BusinessException("User with ID %d can not be %s".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User create(User user) {
        ofNullable(user)
                .orElseThrow(() -> new BusinessException("User to create must not be null"));
        ofNullable(user.getAccount())
                .orElseThrow(() -> new BusinessException("User account to create must not be null"));
        ofNullable(user.getCard())
                .orElseThrow(() -> new BusinessException("User card to create must not be null"));
        this.validateChangeableId(user.getId(), "created");
        if(userRepository.existsByAccountNumber(user.getAccount().getNumber())){
            throw new BusinessException("This account number already exists");
        }
        if (userRepository.existsByCardNumber(user.getCard().getNumber())){
            throw new BusinessException("This card number already exists");
        }
        return this.userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User user) {
        this.validateChangeableId(id, "updated");
        User userDB = this.findById(id);
        if(!userDB.getId().equals((user.getId()))){
            throw new BusinessException("Update IDs must be the same");
        }
        userDB.setName(user.getName());
        userDB.setAccount(user.getAccount());
        userDB.setCard(user.getCard());
        userDB.setFeatures(user.getFeatures());
        userDB.setNews(user.getNews());

        return this.userRepository.save(userDB);
    }

    @Transactional
    public void delete(Long id) {
        this.validateChangeableId(id, "deleted");
        User userDB = this.findById(id);
        this.userRepository.delete(userDB);
    }
}
