package Service.Messages;

import microsoft.exchange.webservices.data.core.exception.service.remote.ServiceRequestException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

public class SerializableAndSendMail {
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.mm.yyyy z HH:mm:ss");

    public SerializableAndSendMail(Set<?> objects){
        File file = new File(System.getProperty("user.dir") + "\\src\\Serializable\\" + objects.iterator().next().getClass().getSimpleName());

        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(objects);
            EmailMessage msg = new EmailMessage(MyExchangeService.service);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            StringBuffer messageBody = new StringBuffer("%");
            messageBody.append(simpleDateFormat.format(date));
            messageBody.append("%");
            messageBody.append(System.getProperty("energyresource"));
            messageBody.append("%");
            messageBody.append("SetType");
            messageBody.append("%");
            messageBody.append(objects.iterator().next().getClass().getSimpleName());
            messageBody.append("%");
            messageBody.append(objects.iterator().next().getClass().getName());
            messageBody.append("%");
            msg.setBody(MessageBody.getMessageBodyFromText(messageBody.toString()));
            //msg.setBody(MessageBody.getMessageBodyFromText(date.toString() + ";" + System.getProperty("user.name")));
            msg.setSubject(PropertyMsg.subjectEmail);
            msg.getAttachments().addFileAttachment(file.getPath());
            msg.getToRecipients().add(PropertyMsg.recipientEmail);
            msg.send();
        } catch(ServiceRequestException exp){
            exp.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public SerializableAndSendMail(Object object){
        File file = new File(System.getProperty("user.dir") + "\\src\\Serializable\\" + object.getClass().getSimpleName());

        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(object);
            EmailMessage msg = new EmailMessage(MyExchangeService.service);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("KRAT"));
            StringBuffer messageBody = new StringBuffer("%");
            messageBody.append(simpleDateFormat.format(date));
            messageBody.append("%");
            messageBody.append(System.getProperty("energyresource"));
            messageBody.append("%");
            messageBody.append("SingleType");
            messageBody.append("%");
            messageBody.append(object.getClass().getSimpleName());
            messageBody.append("%");
            messageBody.append(object.getClass().getName());
            messageBody.append("%");
            msg.setBody(MessageBody.getMessageBodyFromText(messageBody.toString()));
            msg.setSubject(PropertyMsg.subjectEmail);
            msg.getAttachments().addFileAttachment(file.getPath());
            msg.getToRecipients().add(PropertyMsg.recipientEmail);
            msg.send();

        }catch(ServiceRequestException exp){
            exp.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
