package Service;

import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

import java.util.List;

public interface TypeResourceDAO {

    public List<TypeResourceEntity> findAllTypeResource();

    public List<TypeResourceEntity> findTypeResourceOnObject(ObjectOnPlaceEntity object);

}
