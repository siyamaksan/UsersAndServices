package com.example.san.Model.DAO.Imp;


import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.DAO.IDaoService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class DaoService extends San_Crud implements IDaoService {

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public List<San_Service> getUserService(String userId) {
        try {
            Query query = entityManager.createQuery("select service from SAN_SERVICE service\n" +
                    "inner join San_UserService us on us.Service.id=service.id\n" +
                    "inner join San_User suser on suser.id=us.User.id where suser.userName=:un");
            query.setParameter("un", userId);

            List<San_Service> services= (List<San_Service>) query.getResultList();
            return services;
        }catch (Exception e){
            return  null;
        }

    }

    @Override
    public List<San_Service> findByName(String name) {
        Query query = entityManager.createQuery("select service from SAN_SERVICE service where service.ServiceName=:name");
        query.setParameter("name", name);

        List<San_Service> services = (List<San_Service>) query.getResultList();
        return services;
    }

    @Override
    protected Class getDomainClass() {
        return San_Service.class;
    }
}
