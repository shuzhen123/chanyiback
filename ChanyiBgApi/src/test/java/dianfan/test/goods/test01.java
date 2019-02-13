package dianfan.test.goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import dianfan.md5.MD5;

public class test01 {

	public static void main(String[] args) {
		List<String> arr1 = new ArrayList<>();
		arr1.add("a");
		arr1.add("b");
		arr1.add("c");
		arr1.add("d");
		arr1.add("e");
		
		Collections.sort(arr1);
		System.out.println(StringUtils.join(arr1, ","));
		
//		List<String> arr2 = new ArrayList<>();
//		arr2.add("a");
//		arr2.add("b");
//		arr2.add("f");
//		System.out.println(arr2.removeAll(arr1));
//		System.out.println("------------------");
//		System.out.println(arr1);
//		System.out.println("------------------");
//		System.out.println(arr2);
	}
}
