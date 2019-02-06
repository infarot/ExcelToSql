package com.dawid.dao;

import com.dawid.Main;
import com.dawid.entity.Result;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ResultDAOImpl implements ResultDAO {

    private SessionFactory factory = Main.factory;


    @Override
    public List<Result> getAll() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query<Result> query = session.createQuery("from Result", Result.class);
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public void saveOrUpdate(Result result) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(result);
        session.getTransaction().commit();
    }
}
