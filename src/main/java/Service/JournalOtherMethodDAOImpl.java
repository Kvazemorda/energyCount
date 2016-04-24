package Service;

import Forms.MainForm;
import Forms.Object.Capacity.UnitCount.JournalCount.ValueResourceWithoutUnitCount;
import Forms.Service.DialogWindow;
import org.hibernate.Query;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalOtherMethodEntity;

import java.util.Set;

public class JournalOtherMethodDAOImpl {

    public void writeValueToJournal(Set<ValueResourceWithoutUnitCount> set){
        if (set.size() > 0 && !set.isEmpty()){
            int countWrite = 0;
            for(ValueResourceWithoutUnitCount valueResource: set){
                if(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()) != null ){
                    getCurrentJournal(valueResource.getCapacitySourceObjectEntity()).setTimeWork(valueResource.getWorkHours());
                    getCurrentJournal(valueResource.getCapacitySourceObjectEntity()).setValue(valueResource.valueOfPeriod);
                        MainForm.session.update(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()));
                }else{
                    MainForm.session.saveOrUpdate(valueResource.getJournalOtherMethodEntityCurrent());
                }
            }
            MainForm.session.getTransaction().commit();
            countWrite++;
            StringBuffer stringBuffer = new StringBuffer(JournalUnitCountDAOImpl.message);
            stringBuffer.append("\nЗаписано " + countWrite + " объемов по наработке из " + set.size() + " подключенных");
            DialogWindow dialogWindow = new DialogWindow(stringBuffer.toString());
        }else{
            if (JournalUnitCountDAOImpl.message != ""){
                DialogWindow dialogWindow = new DialogWindow(JournalUnitCountDAOImpl.message);
            }
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
