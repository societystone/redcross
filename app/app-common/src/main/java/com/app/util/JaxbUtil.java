package com.app.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
/**
 * javabean与xml互转 
 * @author wangtw
 *
 */
public class JaxbUtil {
	private final static String xmlTop = "<?xml version=\"1.0\" encoding = \"GBK\"?>";
	private final static String xmlRoot = "CMS";
	/**
	 * javabean to xml
	 * @param clz
	 * @param obj
	 * @return
	 */
	public static String convertToXml(Class<?> clz,Object obj) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            //格式化输出，即按标签自动换行，否则就是一行输出
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //设置编码（默认编码就是utf-8）
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //是否省略xml头信息，默认不省略（false）
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
//            return writer.toString();
            return xmlTop+"\n<"+xmlRoot+">\n"+writer.toString()+"\n</"+xmlRoot+">";
        } catch (JAXBException e) {
        	throw new RuntimeException(e);
        }
    }

	/**
	 * xml to javabean
	 * @param clz
	 * @param xml
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public static <T> T convertToJavaBean(Class<T> clz, String xml) {
        try {
        	int beginIndex = xml.indexOf("<"+xmlRoot+">")+xmlRoot.length()+2;
        	int endIndex = xml.indexOf("</"+xmlRoot+">");
        	xml = xml.substring(beginIndex, endIndex);
            JAXBContext jaxbContext = JAXBContext.newInstance(clz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            T t = (T) unmarshaller.unmarshal(reader);
            return t;
        } catch (JAXBException e) {
        	throw new RuntimeException(e);
        }
    }
}
