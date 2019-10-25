package com.wt.springboot.oldweb.getWSDL;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

public class AXIS {

	public static void main(String[] args) {

		final String method = "ImportFileToSde";
		String url="http://192.168.0.124/ImportToSdeService/ImportCAD2SDEWebService.asmx?wsdl";
		String  soapaction="http://tempuri.org/";//"http://tempuri.org/";
		Map m=new HashMap();
		m.put("featid", "7ceff8c504914799a16f1f93102ee14f");
		String json="{\"featid\": \"7ceff8c504914799a16f1f93102ee14f\"}";
		json="{'featid': '7c00e14d39f744828263d0e857630380'}";
		json="sss";
		//json=JSON.toJSONString(m);
		Service service = new Service();
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(soapaction, method));
			call.addParameter(new QName(soapaction, "json"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(soapaction + method);
			String v = (String) call.invoke(new Object[] {json});
			System.out.println(v);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
}
