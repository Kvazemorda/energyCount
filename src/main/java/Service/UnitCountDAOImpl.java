package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import Service.Messages.SerializableAndSendMail;
import org.hibernate.Query;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UnitCountEntity.UnitCountEntity;

import java.util.List;

public class UnitCountDAOImpl {

    public List<UnitCountEntity> getNotConnectUnitCount(){

        //Выбор УУ которые были ранее подключены
        String hql0 = "select distinct unitCount from UnitCountEntity unitCount inner join unitCount.actInstallCountsById, " +
                "ActInstallCountEntity  act " +
                "where act.dateInstall > :today " +
                "and (act.dateUnInstall <= :today " +
                "or act.dateUnInstall is null) " +
                "order by unitCount.number";

        //Выбор УУ которые ранее не были установлен.
        String hql1 = "select distinct unitCount from UnitCountEntity unitCount " +
                "where (unitCount.deletedUnitCount is null " +
                "or unitCount.deletedUnitCount = false)" +
                "and unitCount.actInstallCountsById.size = 0";

        Query query = MainForm.session.createQuery(hql0);
        Query query1 = MainForm.session.createQuery(hql1);
        query.setParameter("today", MainForm.currentDate);
        List<UnitCountEntity> list = query.list();
        list.addAll(query1.list());
        return list;
    }

    public void saveUnitCount(UnitCountEntity unitCountEntity){
        MainForm.session.getTransaction().begin();
        MainForm.session.saveOrUpdate(unitCountEntity);
        SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(unitCountEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Узел учета добавлен");
    }
    public void deleteUnitCount(UnitCountEntity unitCountEntity){
        MainForm.session.getTransaction().begin();
        MainForm.session.update(unitCountEntity);
        SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(unitCountEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Узел учета удален");
    }
}
