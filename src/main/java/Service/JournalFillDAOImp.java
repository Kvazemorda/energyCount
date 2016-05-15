package Service;

import Forms.MainForm;
import Forms.Object.Capacity.UnitCount.JournalCount.ValueResourceZeroCapacity;
import Forms.Service.DialogWindow;
import org.hibernate.Query;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalFillEntity;

import java.util.Set;

public class JournalFillDAOImp {

    public void writeCountToJournal(Set<ValueResourceZeroCapacity> set){
        if (set.size() > 0 && !set.isEmpty()){
            int countWrite = 0;
            for(ValueResourceZeroCapacity valueResource: set){
                if(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()) != null ){
                    getCurrentJournal(valueResource.getCapacitySourceObjectEntity()).setValue(valueResource.valueOfPeriod);
                    MainForm.session.update(getCurrentJournal(valueResource.getCapacitySourceObjectEntity()));
                }else{
                    MainForm.session.saveOrUpdate(valueResource.getJournalFillEntity());
                }
            }
            MainForm.session.getTransaction().commit();
            countWrite++;
            StringBuffer stringBuffer = new StringBuffer(JournalUnitCountDAOImpl.message);
            stringBuffer.append("\n Записано " + countWrite + " объемов по факту отпуска " + set.size() + " подключенных");
            DialogWindow dialogWindow = new DialogWindow(stringBuffer.toString());
        }else{
            if (JournalUnitCountDAOImpl.message != ""){
                DialogWindow dialogWindow = new DialogWindow(JournalUnitCountDAOImpl.message);
            }
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
