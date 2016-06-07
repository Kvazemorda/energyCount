package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import Service.Messages.SerializableAndSendMail;
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
        SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(contractEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Контракт " + contractEntity.getContract() + " сохранен");
    }
}
