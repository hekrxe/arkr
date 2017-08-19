package com.arkr.near;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hztanhuayou on 2017/8/16
 */
@Service
public class DemoServiceImpl implements DemoService {
    private Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    private Map<Long, Demo> db = new HashMap<>();

    @Override
    public int save(Demo demo) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        db.put(demo.getId(), demo);
        return 0;
    }

    @Override
    public Demo getById(Long id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("From DB!");
        return db.get(id);
    }

    @Override
    public int saveEx(Demo demo) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Demo newDemo = demo.clone().setName(demo.getName() + "Ex");
            db.put(demo.getId() + 1, newDemo);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Demo getByIdEx(Long id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("From DB!");
        return db.get(id + 1);
    }
}
