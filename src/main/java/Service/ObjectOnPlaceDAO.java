package Service;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;
import java.util.List;

public interface ObjectOnPlaceDAO {

    //найти все объекты на площадке
    public List<ObjectOnPlaceEntity> findAllObjectOnPlace(PlaceEntity placeEntity);
}
