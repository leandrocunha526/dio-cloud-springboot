package me.dio.cloud.domain.repository;

import me.dio.cloud.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccountNumber(String number);
    boolean existsByCardNumber(String number);
}
