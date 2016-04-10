package Service;

import Forms.MainForm;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

import java.util.List;

public class TypeResourceDAOImpl implements TypeResourceDAO {

    @Override
    public List<TypeResourceEntity> findAllTypeResource() {

        return MainForm.session.createQuery("from TypeResourceEntity d").list();
    }

    @Override
    public List<TypeResourceEntity> findTypeResourceOnObject(ObjectOnPlaceEntity object) {

      /*  Criteria objectOnPlaceCriteria = FormForAddCountToJournal.session.createCriteria(ObjectOnPlaceEntity.class);
        List<ActInputEquipmentEntity> list1 = objectOnPlaceCriteria.add(Restrictions.eq("objectOnPlaceEntity", object)).list();
        List<TypeResourceEntity> list = new ArrayList<>();

        for(ActInputEquipmentEntity actInputEquipmentEntity: list1){
            if (actInputEquipmentEntity.getObjectOnPlaceEntity().equals(object)){
                list.add(actInputEquipmentEntity.getEquipmentByEquipment().getCEEREntities());
            }
        }*/

        return null;
    }

}
