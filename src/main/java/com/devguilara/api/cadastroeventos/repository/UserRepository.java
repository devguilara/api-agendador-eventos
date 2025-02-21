package com.devguilara.api.cadastroeventos.repository;

import com.devguilara.api.cadastroeventos.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByEmail(String email);
    public User findById(int id);
}
