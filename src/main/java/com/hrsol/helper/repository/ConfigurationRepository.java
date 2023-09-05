package com.hrsol.helper.repository;

import com.hrsol.helper.entity.Configuration;
import com.hrsol.helper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    List<Configuration> findByUser(User user);

    void deleteByUser(User user);
}
