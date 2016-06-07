package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import Service.Messages.SerializableAndSendMail;
import microsoft.exchange.webservices.data.core.exception.service.remote.ServiceRequestException;
import org.hibernate.Query;
import sun.applet.Main;
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

    public void saveNewUser(UsersEntity usersEntity){
            MainForm.session.beginTransaction();
            MainForm.session.saveOrUpdate(usersEntity);
            SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(usersEntity);
            MainForm.session.getTransaction().commit();
            DialogWindow dialogWindow = new DialogWindow("Пользователь " + usersEntity.getName() + " cохранен");
    }

}
