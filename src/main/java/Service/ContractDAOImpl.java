package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import org.hibernate.Query;
import vankor.EnergyDepartment.Owner.ContractEntity;

import java.util.List;

public class ContractDAOImpl {

    public List<ContractEntity> getAllContracts(){
        String hql = "select contracts from ContractEntity contracts";

        Query query = MainForm.session.createQuery(hql);
        List<ContractEntity> list = query.list();
        return list;
    }

    public void commitNewContract(ContractEntity contractEntity){
        MainForm.session.beginTransaction();
        MainForm.session.save(contractEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Контракт " + contractEntity.getContract() + " сохранен");
    }
}
