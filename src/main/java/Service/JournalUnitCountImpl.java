package Service;

import Forms.MainForm;
import org.hibernate.Query;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalUnitCountEntity;

public class JournalUnitCountImpl{


    public double getLastCount(JournalUnitCountEntity journalUnitCountEntity){
        String hql = "select distinct countUnit from JournalUnitCountEntity journal " +
                "where current_date > :today order by dateCount desc";

        Query query = MainForm.session.createQuery(hql);
        query.setParameter("today", MainForm.currentDate);
        double count = (double) query.setFirstResult(0).uniqueResult();
        return count;
    }
}
