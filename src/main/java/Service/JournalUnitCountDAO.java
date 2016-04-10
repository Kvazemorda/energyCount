package Service;

import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalUnitCountEntity;

public interface JournalUnitCountDAO {

    public void writeCountToJournal(JournalUnitCountEntity journalUnitCountEntity);
}
