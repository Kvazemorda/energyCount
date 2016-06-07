package Service.Messages;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;

import java.net.URISyntaxException;

public class MyExchangeService {
    public static ExchangeService service;

    public MyExchangeService() {
        //подключаю почту
        PropertyMsg propertyMsg = new PropertyMsg();
        service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
        ExchangeCredentials credentials = new WebCredentials(propertyMsg.user, propertyMsg.password);
        service.setCredentials(credentials);
        try {
            service.setUrl(new java.net.URI(propertyMsg.url + "/EWS/Exchange.asmx"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
