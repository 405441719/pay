package com.cpdrweb;

import com.alibaba.fastjson.JSON;
import com.payinxl.model.FrontMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DemoTests {
	@Test
	public void test() throws Exception {
		Map<String,String> param=new HashMap<String, String>();
		param.put("usercode", "U2132132323");
		param.put("customno", "D343465233");
		param.put("scantype", "ap");
		param.put("money", "100.00");
		param.put("notifyurl","234234");
		String vsign= "e2eqw34eqw23wvhe535sd";
		param.put("sign", vsign);
//		System.out.println(HttpRequester.post("https://www.ei818.com/xopenapi/receiveOrder.html",param, Mode.NOCERTHTTPS));
		String cardid="342529198604231819";
		System.out.println(cardid.substring(0,2)+"**************"+cardid.substring(cardid.length()-2));


	}
	@Test
	public void f(){
//		BigDecimal money=new BigDecimal("100");
//		BigDecimal inrate=new BigDecimal("7.1");
//		BigDecimal moneyusd=money.divide(inrate,1,BigDecimal.ROUND_HALF_UP);
//		System.out.println(moneyusd.toString());

		Map<String, Object> result =null;
		System.out.println("ss="+JSON.toJSONString(result));
		Thread t=new Thread(()->System.out.println("hello world"));
		t.run();
		BinaryOperator<Long> add=(x,y)->x+y;
		System.out.println("add result="+add.apply(5l,6l));
		Predicate<Integer> atLeast=x->x>5;
		System.out.println("atLeast result="+atLeast.test(6));
		List<String> list=new ArrayList<>();
		list.add("aa");
		list.add("bbb");
		list.add("aass");
		list.add("ccaaa");
		long count=list.stream().filter(item->{System.out.println(item.toString());return item.contains("aa");}).count();
		System.out.println("count="+count);
		List<String> collected= Stream.of("aa","bb","cc").collect(Collectors.toList());
		List<String> upperlist=collected.stream().map(s->s.toUpperCase()).collect(Collectors.toList());
		System.out.println("upperlist="+upperlist);
		List<String> filterlist=collected.stream().filter(s->s.contains("aa")).collect(Collectors.toList());
		System.out.println("filterlist="+filterlist);
		List<String> flatmaplist=Stream.of(upperlist,collected).flatMap(s->s.stream()).collect(Collectors.toList());
		System.out.println("flatmaplist="+flatmaplist);
		String minstr=list.stream().max(Comparator.comparing(s->s.length())).get();
		System.out.println("minstr="+minstr);
		int sum =Stream.of(1,2,3,4).reduce(0,(x,y)->x+y);
		System.out.println("sum="+sum);
		double[] values=new double[4];
		Arrays.parallelSetAll(values,i->i+5);
		System.out.println("values="+values[1]);
	}
	@Test
	public void ff() {
		List<FrontMenu> frontMenuList=new ArrayList<>();
		FrontMenu a=new FrontMenu();
		a.setId("aaa");
		a.setPermission("aaa");
		frontMenuList.add(a);
		FrontMenu b=new FrontMenu();
		b.setId("aaa");
		b.setPermission("ccc");
		frontMenuList.add(b);
		FrontMenu c=new FrontMenu();
		c.setId("bbb");
		frontMenuList.add(c);
		FrontMenu d=new FrontMenu();
		d.setId("ddd");
		frontMenuList.add(d);
		Stream stream= frontMenuList.stream().distinct();
		frontMenuList=(List<FrontMenu>)stream.collect(Collectors.toList());
		for(FrontMenu frontMenu:frontMenuList)
		System.out.println(frontMenu);
	}
	@Test
	public void testnio(){
		try {
			RandomAccessFile aFile=new RandomAccessFile("D://使用说明.txt","rw");
			FileChannel inChannel=aFile.getChannel();
			ByteBuffer buf=ByteBuffer.allocate(48);
			int bytesRead = inChannel.read(buf);
			while (bytesRead != -1) {

				System.out.println("Read " + bytesRead);
				buf.flip();

				while(buf.hasRemaining()){
					System.out.print((char) buf.get());
				}

				buf.clear();
				bytesRead = inChannel.read(buf);
			}
			aFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testtransfer() throws FileNotFoundException {
	BigDecimal f=new BigDecimal("32.2");

	}
}
