package com.ail.test.common;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {

	public String readFile(String fileName) throws IOException {
		try (BufferedReader br = new BufferedReader(new java.io.FileReader(fileName))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		}
	}

}
