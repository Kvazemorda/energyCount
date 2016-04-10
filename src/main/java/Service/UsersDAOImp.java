package Service;

import Forms.MainForm;
import org.hibernate.Query;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UsersEntity;

public class UsersDAOImp {

    public UsersEntity getCurrentUser(String name) {
        String hql = "select distinct user from UsersEntity user " +
                "where user.userName =:nameUser";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("nameUser", name);
        UsersEntity usersEntity = (UsersEntity) query.uniqueResult();

        return usersEntity;
    }

}
