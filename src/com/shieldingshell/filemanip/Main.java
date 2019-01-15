package com.shieldingshell.filemanip;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		String currentDirectory = "no directory selected";
		boolean exit = false;
		while (!exit) {
			Scanner sc = new Scanner(System.in);
			System.out.println(
					"What do you want to do (commands : \"cd\", \"ls\", \"mkdir\", \"touch\", \"rm\", \"rd\", \"exit\" ?");
			System.out.println("You are in the directory : " + currentDirectory);
			String input = sc.nextLine();
			String[] inputSplitted = input.split(" ");
			String commande = inputSplitted[0];
			String param = inputSplitted[1];
			switch (commande) {
			case "cd":
				currentDirectory = changeDirectory(param);
				System.out.println("current directory : " + param);
				break;
			case "ls":
				System.out.println(Arrays.toString(listDirectory(currentDirectory)));
				break;
			case "mkdir":
				if (makeDirectory(currentDirectory, param)) {
					System.out.println(param + " directory created");
				} else {
					if (exist(currentDirectory + "\\" + param)) {
						System.out.println("the directory already exist !");
					} else {
						System.out.println("unknow error !");
					}
				}
				break;
			case "touch":
				if (createFile(currentDirectory, param)) {
					System.out.println(param + " file created");
				} else {
					System.out.println("file already exist!");
				}
				break;
			case "rm":
				if (testFile(currentDirectory+"\\"+param)){
					removeFile(currentDirectory, param);
				}else {
					System.out.println(param + " is not a file");
				}
				break;
			case "rd":
				if (testFile(currentDirectory+"\\"+param)) {
					if(removeDirectory(currentDirectory, param)) {
						System.out.println(param + " directory deleted");
					}else {
						System.out.println(param + " directory not empty");
					}
				}
				break;
			case "exit":
				exit = true;
				break;
			default:
				System.out.println("unknow command");
				break;
			}

			sc.close();
		}
	}

	public static String changeDirectory(String repo) {
		return repo;
	}

	public static String[] listDirectory(String currentDirectory) {
		File file = new File(currentDirectory);
		return file.list();
	}

	public static boolean makeDirectory(String currentDirectory, String directoryNameToCreate) {
		File file = new File(currentDirectory + "\\" + directoryNameToCreate);
		return file.mkdir();
	}

	public static boolean exist(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static boolean createFile(String currentDirectory, String fileNameToCreate) throws IOException {
		File file = new File(currentDirectory + "\\" + fileNameToCreate);
		return file.createNewFile();
	}
	
	public static boolean removeDirectory(String currentDirectory, String directoryToDelete) {
		File file = new File(currentDirectory + "\\" + directoryToDelete);
		return file.delete();
	}
	
	public static boolean removeFile(String currentDirectory, String fileToDelete) {
		File file = new File(currentDirectory + "\\" + fileToDelete);
		return file.delete();
	}
	
	public static boolean testFile(String fileToTest) {
		File file = new File(fileToTest);
		return file.isFile();
	}
}
