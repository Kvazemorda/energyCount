package Service;

import Forms.MainForm;
import org.hibernate.Query;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalUnitCountEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UnitCountEntity.UnitCountEntity;

import java.util.List;

public class JournalUnitCountDAOImpl implements JournalUnitCountDAO{

    @Override
    public void writeCountToJournal(JournalUnitCountEntity journalUnitCountEntity) {
        MainForm.session.beginTransaction();
        MainForm.session.saveOrUpdate(journalUnitCountEntity);
        MainForm.session.getTransaction().commit();
    }

    public void updateCountToJournal(JournalUnitCountEntity journalUnitCountEntity) {
        MainForm.session.beginTransaction();
        MainForm.session.merge(journalUnitCountEntity);
        MainForm.session.getTransaction().commit();
    }

    public double getNextCount(JournalUnitCountEntity journalUnitCountEntity, double lastCount){
        String hql = "select distinct countUnit from JournalUnitCountEntity journal " +
                "where journal = :journalUnitCount " +
                "and journal.dateCount > :today " +
                "order by journal.dateCount";

        Query query = MainForm.session.createQuery(hql);
        query.setDate("today", MainForm.currentDate);
        query.setParameter("journalUnitCount", journalUnitCountEntity);
        double count = lastCount;
        try {
            count = (double) query.getFirstResult();
        }catch (org.hibernate.exception.GenericJDBCException e){
            System.out.println(e + " ошибка в JournalUnitCountDAOImpl");
        }
        return count;
    }

    public JournalUnitCountEntity getCurrentJournal(ActInstallCountEntity actInstallCountEntity){
        String hql = "select distinct journal from JournalUnitCountEntity journal " +
                "where journal.actInstallCountEntity = :actInstallCount " +
                "and dateCount = :today";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("today", MainForm.currentDate);
        query.setParameter("actInstallCount", actInstallCountEntity);
        JournalUnitCountEntity journalUnitCountEntity;
        if(query.list().size() == 0) {
            journalUnitCountEntity = null;
        } else{
            journalUnitCountEntity = (JournalUnitCountEntity) query.list().stream().findFirst().get();
        }
        return journalUnitCountEntity;
    }

    public JournalUnitCountEntity getLastCountToJournal(ActInstallCountEntity actInstallCountEntity){
        String hql = "select distinct journal from JournalUnitCountEntity journal " +
                "where journal.dateCount = (select max(journal2.dateCount) from JournalUnitCountEntity journal2 " +
                "where journal2.actInstallCountEntity = :actInstallCount)";
        Query query = MainForm.session.createQuery(hql);
        //query.setParameter("today", MainForm.currentDate);
        query.setParameter("actInstallCount", actInstallCountEntity);
        List<JournalUnitCountEntity> list = query.list();
        //JournalUnitCountEntity lastCount = (JournalUnitCountEntity) query.uniqueResult();
        for(JournalUnitCountEntity journalUnitCountEntity: list){
            System.out.println("UnitCount " + journalUnitCountEntity.getActInstallCountEntity().getUnitCountByUnitCount().getNumber() + " " +  journalUnitCountEntity.getCountUnit());
        }
        return null;
    }
}
