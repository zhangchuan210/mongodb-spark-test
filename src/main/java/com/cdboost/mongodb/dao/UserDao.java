package com.cdboost.mongodb.dao;

import com.cdboost.mongodb.model.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author zc
 * @desc 测试dao类
 * @create 2017-09-04 15:13
 **/
public interface UserDao {

    void createCollection();

    List<UserEntity> findList(int skip, int limit);

    List<UserEntity> findListByAge(int age);

    UserEntity findOne(String id);

    UserEntity findOneByUsername(String username);

    void insert(UserEntity entity);

    void update(UserEntity entity);

}