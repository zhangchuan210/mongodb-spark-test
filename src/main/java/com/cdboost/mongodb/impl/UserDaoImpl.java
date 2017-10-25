package com.cdboost.mongodb.impl;

import com.cdboost.mongodb.dao.UserDao;
import com.cdboost.mongodb.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDao")
public class UserDaoImpl implements UserDao {

    public static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void createCollection() {
        if (!this.mongoTemplate.collectionExists(UserEntity.class)) {
            this.mongoTemplate.createCollection(UserEntity.class);
        }
    }

    @Override
    public List<UserEntity> findList(int skip, int limit) {
        Query query = new Query();
        query.with(new Sort(new Order(Direction.ASC, "_id")));
        query.skip(skip).limit(limit);
        return this.mongoTemplate.find(query, UserEntity.class);
    }

    @Override
    public List<UserEntity> findListByAge(int age) {
        Query query = new Query();
        query.addCriteria(new Criteria("age").is(age));
        return this.mongoTemplate.find(query, UserEntity.class);
    }

    @Override
    public UserEntity findOne(String id) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        return this.mongoTemplate.findOne(query, UserEntity.class);
    }

    @Override
    public UserEntity findOneByUsername(String username) {
        Query query = new Query();
        query.addCriteria(new Criteria("name.username").is(username));
        return this.mongoTemplate.findOne(query, UserEntity.class);
    }

    @Override
    public void insert(UserEntity entity) {
        this.mongoTemplate.insert(entity);

    }

    @Override
    public void update(UserEntity entity) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(entity.getId()));
        Update update = new Update();
        update.set("age", entity.getAge());
        update.set("password", entity.getPassword());
        update.set("regionName", entity.getRegionName());
        update.set("special", entity.getSpecial());
        update.set("works", entity.getWorks());
        update.set("name", entity.getName());
        this.mongoTemplate.updateFirst(query, update, UserEntity.class);

    }

}