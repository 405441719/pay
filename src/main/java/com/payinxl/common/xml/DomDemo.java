package com.payinxl.common.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.List;

/**
 * 
 * @author hongliang.dinghl DOM生成与解析XML文档
 */
public class DomDemo {

	public static void main(String[] args) {
		DomDemo demo = new DomDemo();
		String str = "<?xml version=\"1.0\" encoding=\"GB2312\"?>"
				+ "<fill>"
				+ "<items>"
				+ "<item name=\"state\" value=\"0\"/>"
				+ "<item name=\"errcode\" value=\"1002\"/>"
				+ "<item name=\"errmsg\" value=\"订单号重复\"/>"
				+ "<item name=\"mark\" value=\"shanhai\"/>"
				+ "</items>"
				+ "</fill>";
		
		 SAXReader saxreader = new SAXReader();

		StringReader sbs=new StringReader(str);
		try {
			Document doc = saxreader.read(sbs);
			Element root = doc.getRootElement();
			 List<Element> list = root.element("items").elements("item");
			 System.out.print(list.size());
			for(Element e : list){
			System.out.println(e.attributeValue("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}