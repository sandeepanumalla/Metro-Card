package com.geektrust.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.AppConfig.ApplicationConfig;
import com.geektrust.backend.commands.Command;
import com.geektrust.backend.commands.CommandInvoker;

public class App {
	public static void run(List<String> commandArgs) {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
		String inputFile = commandArgs.get(0);
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));) {
			String line = bufferedReader.readLine();
			while(line != null){
				List<String> tokens = Arrays.asList(line.split(" "));
				try {
					commandInvoker.executeCommand(Command.valueOf(tokens.get(0)), tokens);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				line = bufferedReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		public static void main(String[] args) {
			List<String> commandLineArgs = new ArrayList<>(Arrays.asList(args));
				run(commandLineArgs);
		}
}
