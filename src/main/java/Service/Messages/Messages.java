package Service.Messages;


import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

public class Messages {
        public static void main(String[] args) throws Exception {
                ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
                ExchangeCredentials credentials = new WebCredentials("iyuhohlov@vankoroil.ru", "Qwerty123456", "");
                service.setCredentials(credentials);
                service.autodiscoverUrl("iyuhohlov@vankoroil.ru");
                EmailMessage msg= new EmailMessage(service);
                msg.setSubject("Hello world!");
                msg.setBody(MessageBody.getMessageBodyFromText("Sent using the EWS Java API."));
                msg.getToRecipients().add("iyuhohlov@vankoroil.ru");
                msg.send();
        }


}
