package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import org.hibernate.Query;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.RegionEntity;

import java.util.List;

public class RegionDAOImpl {

    public List<RegionEntity> getAllRegion(){
        String hql = "select region from RegionEntity region";

        Query query = MainForm.session.createQuery(hql);

        return query.list();
    }

    public void commitRegion(RegionEntity regionEntity){
        if(checkNameRegion(regionEntity)){
            String s = "Такое имя уже существует, укажи другое имя";
            DialogWindow dialogWindow = new DialogWindow(s);
        }else{
            MainForm.session.beginTransaction();
            MainForm.session.saveOrUpdate(regionEntity);
            MainForm.session.getTransaction().commit();
            String s = "Регион " + regionEntity.getName() + " сохранен";
            DialogWindow dialogWindow = new DialogWindow(s);
        }
    }
    public Boolean checkNameRegion(RegionEntity regionEntity){
        String hql = "select region.name from RegionEntity region";
        Boolean check = false;
        Query query = MainForm.session.createQuery(hql);
        List<String> list = (List) query.list();
        if(list.size() > 0){
            for(String haveName: list){
                if(haveName.toLowerCase().equals(regionEntity.getName().toLowerCase())){
                    check = true;
                }
            }
        }
        return check;
    }
}
