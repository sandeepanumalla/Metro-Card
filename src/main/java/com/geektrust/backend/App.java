package com.geektrust.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.geektrust.backend.AppConfig.ApplicationConfig;
import com.geektrust.backend.commands.CommandInvoker;

public class App {

	public static void run(List<String> commandArgs) {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
		BufferedReader bufferedReader;
		String inputFile = commandArgs.get(0).split("=")[1];
		String basePath = "./sample_input/";
		inputFile = basePath + inputFile;
		System.out.println("input file "+inputFile);
		try {
			 bufferedReader = new BufferedReader(new FileReader(inputFile));
			String line = bufferedReader.readLine();
			while(line != null){
				List<String> tokens = Arrays.asList(line.split(" "));
				commandInvoker.executeCommand(tokens.get(0), tokens);
				line = bufferedReader.readLine();
			} 
			bufferedReader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		List<String> commandLineArgs = new ArrayList<>(Arrays.asList(args));
		 String expectedSequence = "INPUT_FILE";
		 String actualSequence = commandLineArgs.stream()
                .map(a -> a.split("=")[0])
                .collect(Collectors.joining("$"));
				if(expectedSequence.equals(actualSequence)){
					run(commandLineArgs);
				}
	}
}
