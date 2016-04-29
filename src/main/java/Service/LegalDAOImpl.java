package Service;

import Forms.MainForm;
import org.hibernate.Query;
import vankor.EnergyDepartment.Owner.LegalFormEntity;

import java.util.List;

public class LegalDAOImpl {

    public List<LegalFormEntity> getAllLegalForm(){
        String hql = "select legalForm from LegalFormEntity legalForm";

        Query query = MainForm.session.createQuery(hql);
        List<LegalFormEntity> list = query.list();
        return list;
    }
}
