package com.pauloroberto.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
//import java.util.stream.Collectors;

public class URL {
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> decodeIntList(String s) {
		String[] vetor = s.split(",");
		List<Integer> list = new ArrayList<>();
		
		for (String ss : vetor) {
			list.add(Integer.parseInt(ss));
		}
		//for (int i = 0; i < vetor.length; i++) {}
		return list;
		//return Arrays.asList(vetor).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
}
