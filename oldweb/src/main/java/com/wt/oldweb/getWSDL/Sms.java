package com.wt.oldweb.getWSDL;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.namespace.QName;
import java.net.URL;

@org.springframework.stereotype.Service("sms")
public class Sms {

    @Value("http://111.1.15.158/webservice/services/sendmsg")
    private String url;
    @Value("http://DefaultNamespace")
    private String targetNamespace;

    private  String CallWsdl(String method,String[] parameter,Object[]...values) throws Exception {
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new URL(url));
        call.setOperationName(new QName(targetNamespace, method));
        for(String str:parameter){
            call.addParameter(new QName(targetNamespace, str), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
        }
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
        call.setUseSOAPAction(true);
        call.setSOAPActionURI(targetNamespace + method);
        String v = (String) call.invoke(values);
        return v;
    }

    /**
     * MT短信发送
     * @param corporation
     * @param message xml类型
     * @return
     * @throws Exception
     */
    public Document sendmsg(String corporation,String message) throws Exception {
        String method="sendmsg_add";
        return DocumentHelper.parseText(CallWsdl(method,new String[]{"in0","in1"},new Object[]{corporation,message}));
    }


}
