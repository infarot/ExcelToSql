package com.dawid.dao;

import com.dawid.Main;
import com.dawid.entity.Result;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ResultDAOImpl implements ResultDAO {

    private SessionFactory factory = Main.getFactory();

    @Override
    public void saveOrUpdate(Result result) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(result);
        session.getTransaction().commit();
    }
}
