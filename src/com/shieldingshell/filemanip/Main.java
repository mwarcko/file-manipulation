package com.shieldingshell.filemanip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		String currentDirectory = "C:\\ressources";
		boolean exit = false;
		Scanner sc = new Scanner(System.in);
		System.out.println(
				"What do you want to do (commands : \"help \", \"cat\", \"cd\", \"ls\", \"mkdir\", \"touch\", \"rm\", \"rd\", \"exit\") ?");
		while (!exit) {
			System.out.print(currentDirectory + " : ");
			String input = sc.nextLine();
			String commande = "";
			String param = "";
			if (input.equals("ls")) {
				commande = "ls";
				param = "";
			} else if (input.equals("exit")) {
				commande = "exit";
				param = "";
			} else if (input.equals("help")) {
				commande = "help";
				param = "";
			} else {
				String[] inputSplitted = input.split(" ");
				commande = inputSplitted[0];
				param = input.substring(commande.length() + 1, input.length());
				System.out.println(param);
			}
			switch (commande) {
			case "help":
				System.out.println("==============HELP=============");
				System.out.println("cd directory to go to the specified directory");
				System.out.println("ls to list the files in this specified directory");
				System.out.println("cat file to see the content of a file");
				System.out.println("cp file1 file2 to copy file1 to file2");
				System.out.println("mkdir directory to create a directory");
				System.out.println("touch file to create a file");
				System.out.println("rm file to remove a file");
				System.out.println("rd directory to remove a directory");
				System.out.println("exit to quit");
				System.out.println("==============HELP=============");
				break;
			case "cd":
				if (param.equals("..")) {
					String reg = "\\\\";
					String[] dirTab = currentDirectory.split(reg);
					int lenDirTab = dirTab.length - 1;
					String lastFile = dirTab[lenDirTab];
					currentDirectory = currentDirectory.substring(0, currentDirectory.length() - lastFile.length() - 1);
				} else {
					currentDirectory = currentDirectory + "\\" + changeDirectory(param);
				}
				break;
			case "ls":
				System.out.println(Arrays.toString(listDirectory(currentDirectory)));
				break;
			case "cat":
				readFile(currentDirectory, param);
				break;
			case "cp":
				String[] cpSplitted = param.split(" ");
				copyFile(currentDirectory, cpSplitted[0], cpSplitted[1]);
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
				if (testFile(currentDirectory + "\\" + param)) {
					removeFile(currentDirectory, param);
				} else {
					System.out.println(param + " is not a file");
				}
				break;
			case "rd":
				if (removeDirectory(currentDirectory, param)) {
					System.out.println(param + " directory deleted");
				} else {
					System.out.println(param + " directory not empty");
				}
				break;
			case "exit":
				exit = true;
				System.out.println("bye bye");
				break;
			default:
				System.out.println("unknow command");
				break;
			}

		}
		sc.close();

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

	public static void readFile(String currentDirectory, String fileName) {
		File fileToRead = new File(currentDirectory + "\\" + fileName);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileToRead);
			int data;
			while ((data = fis.read()) >= 0) {
				System.out.println(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void copyFile(String currentDirectory, String param1, String param2) throws IOException {
		createFile(currentDirectory, param2);
		File fileToRead = new File(currentDirectory + "\\" + param1);
		File fileToWrite = new File(currentDirectory + "\\" + param2);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(fileToRead);
			fos = new FileOutputStream(fileToWrite);
			int data;
			while ((data = fis.read()) >= 0) {
				fos.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
