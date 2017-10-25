package com.cdboost.mongodb;

import com.cdboost.mongodb.dao.UserDao;
import com.cdboost.mongodb.model.Log;
import com.cdboost.mongodb.model.UserEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


public class UserDaoImplTest extends AbstractTest {


    @Autowired
    private UserDao userDao;

    @Test
    public void createCollectionTest(){

        UserEntity entity1 = new UserEntity();
        entity1.setAge(11);
        entity1.setBirth(new Date());
        entity1.setPassword("asdfasdf");
        entity1.setRegionName("北京");
        entity1.setWorks(1);
        String[] special = {"aaa", "bbb","ccc"};
        //entity1.setSpecial(special);
        userDao.insert(entity1);


        List<UserEntity> list = userDao.findListByAge(1);
        System.out.println(list.size());
    }

}