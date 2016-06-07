package Service;

import Forms.MainForm;
import Forms.Object.Capacity.UnitCount.JournalCount.ValueResourceZeroCapacity;
import Service.Messages.SerializableAndSendMail;
import org.hibernate.Query;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalFillEntity;

import java.util.HashSet;
import java.util.Set;

public class JournalFillDAOImp {
    private Set<JournalFillEntity> journalFillEntities = new HashSet<>();

    public void writeCountToJournal(Set<ValueResourceZeroCapacity> set){
        if (set.size() > 0 && !set.isEmpty()){
            MainForm.session.beginTransaction();
            int countWrite = 0;
            for(ValueResourceZeroCapacity valueResource: set){
                if(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()) != null ){
                    getCurrentJournal(valueResource.getCapacitySourceObjectEntity()).setValue(valueResource.valueOfPeriod);
                    MainForm.session.update(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()));
                    journalFillEntities.add(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()));
                }else{
                    MainForm.session.saveOrUpdate(valueResource.getJournalFillEntity());
                    journalFillEntities.add(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()));
                }
            }
            countWrite++;
            SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(journalFillEntities);
            JournalUnitCountDAOImpl.messageBuffer.append("\nЗаписано " + countWrite + " объемов по факту отпуска " + set.size() + " подключенных");
        }
    }

    public JournalFillEntity getCurrentJournal(CapacitySourceObjectEntity capacitySourceObjectEntity){
        String hql = "select journal from JournalFillEntity journal " +
                "where journal.capacitySourceObjectEntity = :capacity " +
                "and journal.date = :today ";

        Query query = MainForm.session.createQuery(hql);
        query.setDate("today", MainForm.getCurrentDate());
        query.setParameter("capacity", capacitySourceObjectEntity);

        JournalFillEntity journalFillEntity = (JournalFillEntity) query.uniqueResult();

        return journalFillEntity;
    }
}
