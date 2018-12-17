package tms.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MonitorBaseDao extends BaseDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    protected Session getCurrentSession() {
        Session ss = sessionFactory.openSession();
        return ss;
    }
}
