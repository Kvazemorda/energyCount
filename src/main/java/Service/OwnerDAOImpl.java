package Service;

import Forms.MainForm;
import org.hibernate.Query;
import vankor.EnergyDepartment.Owner.OwnerEntity;

import java.util.List;

public class OwnerDAOImpl {
    public List<OwnerEntity> getAllOwner(){
        String sql = "select owner from OwnerEntity owner";

        Query query = MainForm.session.createQuery(sql);
        List<OwnerEntity> list = query.list();
        return list;
    }
}
