package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import Service.Messages.SerializableAndSendMail;
import org.hibernate.Query;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;

import java.util.List;

public class ObjectOnPlaceDAOImpl implements ObjectOnPlaceDAO {

    public List<ObjectOnPlaceEntity> findAllObject() {

        return MainForm.session.createQuery("from ObjectOnPlaceEntity c").list();
    }

    @Override
    public List<ObjectOnPlaceEntity> findAllObjectOnPlace(PlaceEntity placeEntity) {

        String hql = "select distinct objectOnPlace from ObjectOnPlaceEntity objectOnPlace " +
                "where objectOnPlace.placeByPlace = :place " +
                "and (objectOnPlace.deleteObject = false or objectOnPlace.deleteObject is null) " +
                "order by objectOnPlace.id";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("place", placeEntity);
        List<ObjectOnPlaceEntity> list = query.list();
        return list;
    }

    public List<ObjectOnPlaceEntity> getObjectOnPlaceConnectedResource(PlaceEntity placeEntity) {

        String hql = "select distinct objectOnPlace from ObjectOnPlaceEntity objectOnPlace " +
                "join objectOnPlace.capacityObjectEntities " +
                "where objectOnPlace.placeByPlace = :place " +
                "and objectOnPlace.deleteObject = false " +
                "order by objectOnPlace.id";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("place", placeEntity);
        List<ObjectOnPlaceEntity> list = query.list();
        return list;
    }

    public void saveObjectOnPlace(ObjectOnPlaceEntity objectOnPlaceEntity){
        MainForm.session.beginTransaction();
        MainForm.session.saveOrUpdate(objectOnPlaceEntity);
        SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(objectOnPlaceEntity);
        MainForm.session.getTransaction().commit();
        String message = "Объект " + objectOnPlaceEntity.getName() + " добавлен";
        DialogWindow dialogWindow = new DialogWindow(message);
    }

    public void deleteObjectOnPlace(ObjectOnPlaceEntity objectOnPlaceEntity){
        MainForm.session.beginTransaction();
        MainForm.session.saveOrUpdate(objectOnPlaceEntity);
        SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(objectOnPlaceEntity);
        MainForm.session.getTransaction().commit();

        String message = "Объект " + objectOnPlaceEntity.getName() + " удален";
        DialogWindow dialogWindow = new DialogWindow(message);
    }
}
