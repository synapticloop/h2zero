package com.synapticloop;

import com.synapticloop.h2zero.Main;


public class BookstoreTester {
	public static void main(String[] args) {
		Main.main(new String[] {
				"generate",
				"-in",
				"src/test/resources/bookstore.json",
				"-out",
				"src/debugger/java/"});
	}
}
