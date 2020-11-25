package com.pkm.snork.javadevgym.interfacechallenge_012519;

import java.util.function.Function;

public class InterfaceChallenge {

	interface Jedi {
		String MASTER = "Yoda";
		
		default String attack() {
			String s = jump(jedi -> String.join(jedi, useSaber(), useForce()));
			return s;
		}

		private String jump(Function<String, String> function) {
			String s = function.apply("Luke");
			return s;
		}

		private static String useSaber() {
			return "S";
		}

		private String useForce() {
			return "F";
		}
	}
	
	public static void main(String... starWars) {
		Jedi j = new Jedi() {
			public String useForce() { return "X"; } };
			
		String one = j.attack();
		String two = Jedi.useSaber();
		String three = Jedi.MASTER;
		
		System.out.println(one + two + three);
	}
}
