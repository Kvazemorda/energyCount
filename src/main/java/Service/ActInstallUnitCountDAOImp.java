package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import Service.Messages.SerializableAndSendMail;
import org.hibernate.Query;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;

import java.util.HashSet;
import java.util.Set;

public class ActInstallUnitCountDAOImp {
    private Set<ActInstallCountEntity> actInstallCountEntitySet = new HashSet<>();

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
        SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(actInstallCountEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Узел учета подключен к объекту");

    }

    public  void unInstallUnitCount(ActInstallCountEntity actInstallCountEntity){
        MainForm.session.beginTransaction();
        MainForm.session.update(actInstallCountEntity);
        SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(actInstallCountEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Узел учета снят");

    }
}
