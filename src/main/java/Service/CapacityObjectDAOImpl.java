package Service;

import Forms.MainForm;
import org.hibernate.Query;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

import java.util.List;

public class CapacityObjectDAOImpl {

    public List<CapacitySourceObjectEntity> getCapacityOnPlaceConnectedResource(PlaceEntity placeEntity, TypeResourceEntity typeResourceEntity){
        String hql = "select capacity from CapacitySourceObjectEntity capacity " +
                "where capacity.typeResourceEntity = :resource " +
                "and  capacity.objectOnPlaceEntity = (select objectOnPlace from ObjectOnPlaceEntity objectOnPlace " +
                "where objectOnPlace.placeByPlace = :place)";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("place", placeEntity);
        query.setParameter("resource", typeResourceEntity);
        List<CapacitySourceObjectEntity> capacitySourceObjectEntities = (List) query.list();
        return capacitySourceObjectEntities;
    }

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
        String hql = "select distinct capacityResource from CapacitySourceObjectEntity  capacityResource, ActInstallCountEntity act" +
                " where capacityResource.objectOnPlaceEntity = :objectOnPlace " +
                "and capacityResource.dateInstall <= :today " +
                "and (capacityResource.dateUnInstall > :today " +
                "or capacityResource.dateUnInstall is null) " +
                "and capacityResource.typeResourceEntity = :typeResource " +
                "and (capacityResource.actInstallCountEntities is empty " +
                "or capac = (select act1 from ActInstallCountEntity act1" +
                "where act1.dateInstall > :today and (act1.dateUnInstall <= :today " +
                "or act1.dateUnInstall is null))) ";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("objectOnPlace", objectOnPlaceEntity);
        query.setParameter("today", MainForm.currentDate);
        query.setParameter("typeResource", typeResourceEntity);
        List<CapacitySourceObjectEntity> list = query.list();
            System.out.println(list.size());
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