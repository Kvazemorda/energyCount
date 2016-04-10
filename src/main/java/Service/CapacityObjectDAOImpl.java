package Service;

import Forms.MainForm;
import org.hibernate.Query;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

import java.util.List;

public class CapacityObjectDAOImpl {

    public List<CapacitySourceObjectEntity> getCapacityConnectedToObject(ObjectOnPlaceEntity objectOnPlaceEntity){
        String hql = "select capacityResource from CapacitySourceObjectEntity  capacityResource " +
                "where capacityResource.objectOnPlaceEntity = :objectOnPlace " +
                "and capacityResource.dateInstall <= :today and (capacityResource.dateUnInstall > :today " +
                "or capacityResource.dateUnInstall is null)";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("objectOnPlace", objectOnPlaceEntity);
        query.setParameter("today", MainForm.currentDate);
        List<CapacitySourceObjectEntity> list = query.list();
        return list;
    }

    public List<CapacitySourceObjectEntity> getCapacityResourceConnectedToObjectSource(ObjectOnPlaceEntity objectOnPlaceEntity,
                                                                               TypeResourceEntity typeResourceEntity){
        String hql = "select distinct capacityResource from CapacitySourceObjectEntity  capacityResource " +
                "where capacityResource.objectOnPlaceEntity = :objectOnPlace " +
                "and capacityResource.dateInstall <= :today and (capacityResource.dateUnInstall > :today " +
                "or capacityResource.dateUnInstall is null) " +
                "and capacityResource.typeResourceEntity = :typeResource " +
                "and capacityResource.source = true";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("objectOnPlace", objectOnPlaceEntity);
        query.setParameter("today", MainForm.currentDate);
        query.setParameter("typeResource", typeResourceEntity);
        List<CapacitySourceObjectEntity> list = query.list();
        return list;

    }
    public List<CapacitySourceObjectEntity> getCapacityResourceConnectedToObjectConsumer(ObjectOnPlaceEntity objectOnPlaceEntity,
                                                                                 TypeResourceEntity typeResourceEntity){
        String hql = "select distinct capacityResource from CapacitySourceObjectEntity  capacityResource " +
                "where capacityResource.objectOnPlaceEntity = :objectOnPlace " +
                "and capacityResource.dateInstall <= :today and (capacityResource.dateUnInstall > :today " +
                "or capacityResource.dateUnInstall is null) " +
                "and capacityResource.typeResourceEntity = :typeResource " +
                "and capacityResource.consumer = true";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("objectOnPlace", objectOnPlaceEntity);
        query.setParameter("today", MainForm.currentDate);
        query.setParameter("typeResource", typeResourceEntity);
        List<CapacitySourceObjectEntity> list = query.list();
        return list;
    }

        public List<CapacitySourceObjectEntity> getCapacityWithoutUnitCount(ObjectOnPlaceEntity objectOnPlaceEntity, TypeResourceEntity typeResourceEntity){
        String hql = "select distinct capacityResource from CapacitySourceObjectEntity  capacityResource, ActInstallCountEntity act " +
                "where capacityResource.objectOnPlaceEntity = :objectOnPlace " +
                "and capacityResource.dateInstall <= :today " +
                "and (capacityResource.dateUnInstall > :today or capacityResource.dateUnInstall is null) " +
                "and capacityResource.typeResourceEntity = :typeResource " +
                "and (capacityResource.actInstallCountEntities.size = 0 " +
                "or (act.dateInstall > :today and act.dateUnInstall <= :today))";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("objectOnPlace", objectOnPlaceEntity);
        query.setParameter("today", MainForm.currentDate);
        query.setParameter("typeResource", typeResourceEntity);
        List<CapacitySourceObjectEntity> list = query.list();
        return list;
    }
    public void connectCapacityToObject(CapacitySourceObjectEntity capacitySourceObjectEntity){
        MainForm.session.beginTransaction();
        MainForm.session.saveOrUpdate(capacitySourceObjectEntity);
        MainForm.session.getTransaction().commit();
    }

    public void disconnectCapacityFromObject(CapacitySourceObjectEntity capacitySourceObjectEntity){
        MainForm.session.beginTransaction();
        MainForm.session.saveOrUpdate(capacitySourceObjectEntity);
        MainForm.session.getTransaction().commit();
    }


}