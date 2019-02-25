package com.dawid.dao;

import com.dawid.Main;
import com.dawid.entity.ShiftProduction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShiftProductionDAOImpl implements ShiftProductionDAO {
    private SessionFactory factory = Main.getFactory();

    @Override
    public void saveOrUpdate(ShiftProduction shiftProduction) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(shiftProduction);
        session.getTransaction().commit();
    }
}
