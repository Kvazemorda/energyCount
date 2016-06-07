package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import Service.Messages.SerializableAndSendMail;
import org.hibernate.Session;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;

import java.util.List;

public class PlaceDAOImpl implements PlaceDAO {
    Session session;

    @Override
    public List<PlaceEntity> findALLPlace() {
        return MainForm.session.createQuery("from PlaceEntity c").list();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void commitPlace(PlaceEntity placeEntity){
            MainForm.session.beginTransaction();
            MainForm.session.saveOrUpdate(placeEntity);
            SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(placeEntity);
            MainForm.session.getTransaction().commit();
            String s = "Площадка " + placeEntity.getName() + " сохранен";
            DialogWindow dialogWindow = new DialogWindow(s);
    }
}
