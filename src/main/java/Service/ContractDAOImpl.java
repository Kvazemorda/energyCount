package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import vankor.EnergyDepartment.Owner.ContractEntity;

public class ContractDAOImpl {

    public void commitNewContract(ContractEntity contractEntity){
        MainForm.session.beginTransaction();
        MainForm.session.save(contractEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Контракт " + contractEntity.getContract() + " сохранен");
    }
}
