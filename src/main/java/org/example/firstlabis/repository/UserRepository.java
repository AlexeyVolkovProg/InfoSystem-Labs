package org.example.firstlabis.repository;

import jakarta.validation.constraints.NotNull;
import org.example.firstlabis.model.security.Role;
import org.example.firstlabis.model.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByPassword(String password);

    boolean existsByRole(Role role);

    boolean existsByUsername(String username);

    Page<User> findAllByEnabledFalse(@NotNull Pageable pageable);
}
