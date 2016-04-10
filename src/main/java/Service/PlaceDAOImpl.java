package Service;

import Forms.MainForm;
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
}
