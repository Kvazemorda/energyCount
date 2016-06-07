package Forms.Service;

import Forms.MainForm;
import Service.Messages.PropertyMsg;
import Service.Messages.TypeFiles;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.notification.EventType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.notification.GetEventsResults;
import microsoft.exchange.webservices.data.notification.ItemEvent;
import microsoft.exchange.webservices.data.notification.PullSubscription;
import microsoft.exchange.webservices.data.property.complex.Attachment;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import microsoft.exchange.webservices.data.property.complex.FolderId;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CheckMail implements Runnable{
    ExchangeService service;

    public CheckMail(ExchangeService service) {
       this.service = service;
    }

    @Override
    public void run() {
        List folder = new ArrayList();
        folder.add(new FolderId().getFolderIdFromWellKnownFolderName(WellKnownFolderName.Inbox));
        GetEventsResults eventsResults = null;

        try {
            PullSubscription subscription = service.subscribeToPullNotifications(folder,1,null, EventType.NewMail);
            while (true){
                eventsResults = subscription.getEvents();
                for(ItemEvent event: eventsResults.getItemEvents()){
                    if(event.getEventType() == EventType.NewMail){
                        EmailMessage msg = EmailMessage.bind(service, event.getItemId());
                        if(msg.getSubject().equals(PropertyMsg.subjectEmail) && msg.getHasAttachments()){
                            //парсим и сохраняем в базе сообщения
                            parserAndSaveMsg(msg);
                            }
                        }
                    }
                Thread.sleep(10000);
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("2 поток вышел");
    }

    private Boolean isIamSendMsg(String userName){
        return userName.equals(System.getProperty("user.name"))? true: false;
    }

    /**
     * mask msg: dateSerializableMsg;userSender;typeFilesFromMsg;classNameFromMsg;
     * @param msg from inbox
     */
    private void parserAndSaveMsg(EmailMessage msg){
        TypeFiles typeFiles = null;
        String msgBody = null;
        try {
            msgBody = msg.getBody().toString();
        } catch (ServiceLocalException e) {
            e.printStackTrace();
        }
        String[] msgBodySplit = msgBody.split("%");
        for(String text: msgBodySplit){
        }
        String dateSerializableMsg = msgBodySplit[1];
        String userNameMsg = msgBodySplit[2];
        String typeFilesFromMsg = msgBodySplit[3];
        String classSimpleNameFromMsg = msgBodySplit[4];
        String classNameFromMsg = msgBodySplit[5];
        typeFiles = TypeFiles.valueOf(typeFilesFromMsg);
// ПОМЕНЯЙ на !isIamSendMsg
        if(isIamSendMsg(userNameMsg)){
            switch (typeFiles){
                case SingleType:
                    // добавить здесь сохранения данных
                    break;
                case SetType:
                    try {
                        for(Attachment attachment: msg.getAttachments()){
                            attachment.load();
                            FileAttachment fileAttachment = (FileAttachment) attachment;
                            OutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + "\\src\\Serializable\\DownloadFromMail\\" + attachment.getName());
                            fileAttachment.load(outputStream);
                            outputStream.flush();
                            outputStream.close();
                            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\Serializable\\DownloadFromMail\\" + attachment.getName());
                            ObjectInputStream ois = null;
                            try {
                                ois = new ObjectInputStream(fis);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Set<?> setChilde = (Set) ois.readObject();
                            for (Object object: setChilde){
                                Object o = Class.forName(classNameFromMsg).cast(object);
                                    MainForm.session.beginTransaction();
                                    MainForm.session.merge(o);
                                    MainForm.session.getTransaction().commit();
                                System.out.println("встал куда надо");
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }else{
            System.out.println("Реплика отправлена всем пользователям");
        }
    }
}
