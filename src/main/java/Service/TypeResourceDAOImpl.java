package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import org.hibernate.Query;
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

    public void commitTypeResource(TypeResourceEntity typeResourceEntity){
            if(checkNameTypeResource(typeResourceEntity)){
                String s = "Такое имя уже существует, укажи другое имя";
                DialogWindow dialogWindow = new DialogWindow(s);
            }else{
                MainForm.session.beginTransaction();
                MainForm.session.saveOrUpdate(typeResourceEntity);
                MainForm.session.getTransaction().commit();
                String s = "Ресурс " + typeResourceEntity.getName() + " сохранен";
                DialogWindow dialogWindow = new DialogWindow(s);
            }
    }
    public Boolean checkNameTypeResource(TypeResourceEntity typeResourceEntity){
        String hql = "select typeResource.name from TypeResourceEntity typeResource";
        Boolean check = false;
        Query query = MainForm.session.createQuery(hql);
        List<String> list = (List) query.list();
        if(list.size() > 0){
            for(String haveName: list){
                if(haveName.toLowerCase().equals(typeResourceEntity.getName().toLowerCase())){
                    check = true;
                }
            }
    }
    return check;
    }
}