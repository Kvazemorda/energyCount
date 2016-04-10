package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import org.hibernate.Query;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;

public class ActInstallUnitCountDAOImp {

    public ActInstallCountEntity getActInstallCount(CapacitySourceObjectEntity capacitySourceObjectEntity){
        String hql = "select distinct act from ActInstallCountEntity act join act.capacitySourceObjectEntity " +
                "where act.dateInstall <= :today " +
                "and (act.dateUnInstall > :today " +
                "or act.dateUnInstall is null) " +
                "and act.capacitySourceObjectEntity = :capacityObject";
        Query query = MainForm.session.createQuery(hql);
        query.setParameter("today", MainForm.currentDate);
        query.setParameter("capacityObject", capacitySourceObjectEntity);
        ActInstallCountEntity list = (ActInstallCountEntity) query.uniqueResult();
        return list;
    }

    public void saveActInstallCount(ActInstallCountEntity actInstallCountEntity){
        MainForm.session.beginTransaction();
        MainForm.session.saveOrUpdate(actInstallCountEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Узел учета подключен к объекту");
    }

    public  void unInstallUnitCount(ActInstallCountEntity actInstallCountEntity){
        MainForm.session.beginTransaction();
        MainForm.session.update(actInstallCountEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Узел учета снят");
    }
}
