package Service;

import Forms.MainForm;
import Forms.Object.Capacity.UnitCount.JournalCount.ValueResourceWithUnitCount;
import Service.Messages.SerializableAndSendMail;
import org.hibernate.Query;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalUnitCountEntity;

import java.util.HashSet;
import java.util.Set;

public class JournalUnitCountDAOImpl {
    public static StringBuffer messageBuffer;
    private Set<JournalUnitCountEntity> journalUnitCountEntities;

    public void writeCountToJournal(Set<ValueResourceWithUnitCount> set) {
        messageBuffer = new StringBuffer();
        if (set.size() > 0 && !set.isEmpty()){
            int countWrite = 0;
            journalUnitCountEntities = new HashSet<>();
            MainForm.session.beginTransaction();
            for(ValueResourceWithUnitCount valueResource: set){
                JournalUnitCountEntity journalUnitCountEntity = new JournalUnitCountEntity(
                        valueResource.getCurrentCount(), MainForm.currentDate,
                        valueResource.getActInstallCountEntity(),valueResource.getValue());
                if(valueResource.getJournalUnitCountEntity() == null){
                    MainForm.session.saveOrUpdate(journalUnitCountEntity);
                    journalUnitCountEntities.add(journalUnitCountEntity);
                    countWrite++;
                }else{
                    valueResource.getJournalUnitCountEntity().setCountUnit(valueResource.currentCountDouble);
                    valueResource.getJournalUnitCountEntity().setValue(valueResource.getValue());
                    double countNext = valueResource.getJournalUnitCountEntityNext().getCountUnit();
                    double countCurrent = valueResource.getJournalUnitCountEntity().getCountUnit();
                    double nextValue = countNext - countCurrent;
                    valueResource.getJournalUnitCountEntityNext().setValue(nextValue);
                    MainForm.session.update(valueResource.getJournalUnitCountEntity());
                    MainForm.session.update(valueResource.getJournalUnitCountEntityNext());
                    journalUnitCountEntities.add(valueResource.getJournalUnitCountEntity());
                    journalUnitCountEntities.add(valueResource.getJournalUnitCountEntityNext());
                    countWrite++;
                }
            }
            SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(journalUnitCountEntities);
            messageBuffer.append("Записано " + countWrite + " показаний узлов учета из " + set.size());
        }
    }

    public void updateCountToJournal(JournalUnitCountEntity journalUnitCountEntity) {
        MainForm.session.beginTransaction();
        MainForm.session.update(journalUnitCountEntity);
        MainForm.session.getTransaction().commit();
    }

    public JournalUnitCountEntity getNextCount(ActInstallCountEntity actInstallCountEntity){
        String hql = "select distinct journal from JournalUnitCountEntity journal " +
                "where journal.dateCount = (select min(journal2.dateCount) from JournalUnitCountEntity journal2 " +
                "where journal2.actInstallCountEntity = :actInstallCount " +
                "and journal2.dateCount > :today ) " +
                "and journal.actInstallCountEntity = :actInstallCount ";
        Query query = MainForm.session.createQuery(hql);
        query.setParameter("today", MainForm.currentDate);
        query.setParameter("actInstallCount", actInstallCountEntity);

        JournalUnitCountEntity journalUnitCountEntity = (JournalUnitCountEntity) query.uniqueResult();
        return journalUnitCountEntity;
    }

    public JournalUnitCountEntity getCurrentJournal(ActInstallCountEntity actInstallCountEntity){
        String hql = "select distinct journal from JournalUnitCountEntity journal " +
                "where journal.dateCount = :today " +
                "and journal.actInstallCountEntity = :actInstallCount";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("today", MainForm.currentDate);
        query.setParameter("actInstallCount", actInstallCountEntity);

        JournalUnitCountEntity journalUnitCountEntity = (JournalUnitCountEntity) query.uniqueResult();
        return journalUnitCountEntity;
    }

    public JournalUnitCountEntity getPreviewCountToJournal(ActInstallCountEntity actInstallCountEntity){
        String hql = "select distinct journal from JournalUnitCountEntity journal " +
                "where journal.dateCount = (select max(journal2.dateCount) from JournalUnitCountEntity journal2 " +
                "where journal2.actInstallCountEntity = :actInstallCount " +
                "and journal2.dateCount < :today ) " +
                "and journal.actInstallCountEntity = :actInstallCount " +
                "and journal.dateCount < :today ";
        Query query = MainForm.session.createQuery(hql);
        query.setParameter("today", MainForm.currentDate);
        query.setParameter("actInstallCount", actInstallCountEntity);

        JournalUnitCountEntity journalUnitCountEntity = (JournalUnitCountEntity) query.uniqueResult();
        return journalUnitCountEntity;
    }
}
