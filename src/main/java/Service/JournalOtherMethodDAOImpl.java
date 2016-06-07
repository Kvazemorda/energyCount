package Service;

import Forms.MainForm;
import Forms.Object.Capacity.UnitCount.JournalCount.ValueResourceWithoutUnitCount;
import Forms.Service.DialogWindow;
import Service.Messages.SerializableAndSendMail;
import org.hibernate.Query;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalOtherMethodEntity;

import java.util.HashSet;
import java.util.Set;

public class JournalOtherMethodDAOImpl {
    private Set<JournalOtherMethodEntity> journalOtherMethodEntities = new HashSet<>();

    public void writeValueToJournal(Set<ValueResourceWithoutUnitCount> set){
        if (set.size() > 0 && !set.isEmpty()){
            MainForm.session.beginTransaction();
            int countWrite = 0;
            for(ValueResourceWithoutUnitCount valueResource: set){
                if(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()) != null ){
                    getCurrentJournal(valueResource.getCapacitySourceObjectEntity()).setTimeWork(valueResource.getWorkHours());
                    getCurrentJournal(valueResource.getCapacitySourceObjectEntity()).setValue(valueResource.valueOfPeriod);
                        MainForm.session.update(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()));
                    journalOtherMethodEntities.add(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()));
                    countWrite++;
                }else{
                    MainForm.session.saveOrUpdate(valueResource.getJournalOtherMethodEntityCurrent());
                    journalOtherMethodEntities.add(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()));
                    countWrite++;
                }
            }
            MainForm.session.getTransaction().commit();
            //Сереализую сет показаний другими методами
            SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(journalOtherMethodEntities);
            JournalUnitCountDAOImpl.messageBuffer.append("\nЗаписано " + countWrite + " объемов по наработке из " + set.size() + " подключенных");
            DialogWindow dialogWindow = new DialogWindow(JournalUnitCountDAOImpl.messageBuffer.toString());
            JournalUnitCountDAOImpl.messageBuffer.delete(0,JournalUnitCountDAOImpl.messageBuffer.length());
        }
    }

    public JournalOtherMethodEntity getCurrentJournal(CapacitySourceObjectEntity capacitySourceObjectEntity){
        String hql = "select distinct journal from JournalOtherMethodEntity journal " +
                "where journal.capacitySourceObjectEntity = :capacity " +
                "and journal.date = :today";

        Query query = MainForm.session.createQuery(hql);
        query.setDate("today", MainForm.getCurrentDate());
        query.setParameter("capacity", capacitySourceObjectEntity);

        JournalOtherMethodEntity journalOtherMethodEntity = (JournalOtherMethodEntity) query.uniqueResult();

        return journalOtherMethodEntity;
    }
}
