package Service;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import Service.Messages.SerializableAndSendMail;
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

    public void saveLegalForm(LegalFormEntity legalFormEntity){
        MainForm.session.beginTransaction();
        MainForm.session.save(legalFormEntity);
        SerializableAndSendMail serializableAndSendMail = new SerializableAndSendMail(legalFormEntity);
        MainForm.session.getTransaction().commit();
        DialogWindow dialogWindow = new DialogWindow("Новая юр. форма " + legalFormEntity + " сохранена");
    }
}
