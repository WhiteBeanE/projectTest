package com.bsm.projectTest.jwt.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Aa {

	public static void main(String[] args) {
		
		Random rn = new Random();
		HashSet<Integer> set = new HashSet<>();
		while (set.size() <= 6) {
			set.add(rn.nextInt(46));
		}
		List<Integer> list = new ArrayList<>(set);
		System.out.println(set);
		Collections.sort(list);
		System.out.println(list);
		
		String a = "123456";
		a.length();
		
		char[] ch = a.toCharArray();
		String result = "";
		for(int i = ch.length - 1; i >= 0; i--) {
			result += ch[i];
		}
		System.out.println(result);
		for(int i = a.length() - 1; i >= 0; i--) {
			result += a.charAt(i);
		}
		System.out.println(result);
	}
}
