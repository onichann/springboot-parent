package com.wt.oldweb.getWSDL;


import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;

public class CXF {

	public static void main(String[] args) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = dcf
                .createClient("http://localhost:8080/xytdcb/ws/auto/demo/TranslateService?wsdl");
        // url为调用webService的wsdl地址
        QName name = new QName("com.gisinfo.demo.ws.TranslateService", "translate");
        // namespace是命名空间，methodName是方法名
        String xmlStr = "aaaaaaaa";
        // paramvalue为参数值
        Object[] objects;
        try {
            objects = client.invoke(name, xmlStr);
            System.out.println(objects[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
//	cxf-2.3.3.jar
//	geronimo-annotation_1.0_spec-1.1.1.jar
//	geronimo-jaxws_2.2_spec-1.0.jar
//	geronimo-stax-api_1.0_spec-1.0.1.jar
//	geronimo-ws-metadata_2.0_spec-1.1.3.jar
//	jaxb-api-2.2.1.jar
//	jaxb-impl-2.2.1.1.jar
//	neethi-2.0.4.jar
//	wsdl4j-1.6.2.jar
//	XmlSchema-1.4.7.jar
//	wstx-asl-3.2.9.jar

}
