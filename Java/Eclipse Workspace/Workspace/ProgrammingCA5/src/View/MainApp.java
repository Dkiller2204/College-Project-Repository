package View;

import java.io.IOException;
import Controller.ShowPriceRangeFilter;
import Controller.ShowGenreFilter;
import Controller.ShowManager;
import Controller.ShowNameFilter;
import Model.MenuOptions;
import Model.Show;
import Utility.FileUtility;
import Utility.ScannerUtility;

public class MainApp
{
	String strPath = "";
	String strName = "";
	String strPermissions = "rw";
	public static ShowManager manager = null;
	
	private MenuOptions mainMenu, findMenu, fileMenu;

	public static void main(String[] args)
	{
		MainApp theApp = new MainApp();
		try {
			theApp.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() throws IOException
	{
		initialiseMenus();
		showMainMenu();
	}

	private void showMainMenu()
	{
		/* initialising choice */
		int choice = 0;

		do {
			choice = this.mainMenu.showMenuGetChoice("Enter your choice: ");

			 if (choice == 1) {
				 createFile();
				 showMainMenu();
			}
			else if (choice == 2) {
				boolean opened = openFile();
				if(opened){
					showFileMenu();
				}
				else{
					showMainMenu();
				}
			}
		     else if (choice == 3) {
				deleteFile();
			}
			else if (choice == 4) {
				exit();
			}
		}
		while (choice != this.mainMenu.getIndexOfExitOption());

	}

	private void initialiseMenus()
	{
		// main menu
		this.mainMenu = new MenuOptions("Main Menu", 4);
		this.mainMenu.add("Create");
		this.mainMenu.add("Open");
		this.mainMenu.add("Delete");
		this.mainMenu.add("Exit");

		// file menu
		this.fileMenu = new MenuOptions("File Menu", 9);
		this.fileMenu.add("Add");
		this.fileMenu.add("Edit");
		this.fileMenu.add("Find menu");
		this.fileMenu.add("Delete");
		this.fileMenu.add("Delete All");
		this.fileMenu.add("Print All");
		this.fileMenu.add("Count");
		this.fileMenu.add("Show Main menu");
		this.fileMenu.add("Exit");

		// find menu
		this.findMenu = new MenuOptions("Find Menu", 5);
		this.findMenu.add("Find by name");
		this.findMenu.add("Find by genre");
		this.findMenu.add("Find by price");
		this.findMenu.add("Show File menu");
		this.findMenu.add("Exit");

	}

	private void showFindMenu()
	{

		int choice = 0;

		do {
			choice = this.findMenu.showMenuGetChoice("Enter your choice: ");

			if (choice == 1) {
				 findByName();
			}
			else if (choice == 2) {
				 findByGenre();
			}
			else if (choice == 3) {
               findByPrice();
			}
			else if (choice == 4) {
				showFileMenu();
			}
			else if (choice == 5) {
				exit();
			}

		}
		while (choice != this.mainMenu.getIndexOfExitOption());

	}
	
	private void showFileMenu()
	{
			int choice = 0;

			do {
				choice = this.fileMenu.showMenuGetChoice("Enter your choice: ");

				if (choice == 1) {
					addToFile();
				}
				else if (choice == 2) {
					editFile();
				}
				else if (choice == 3) {
                     showFindMenu();
				}
				else if (choice == 4) {
                   delete();
				}
				else if (choice == 5) {
					deleteAll();
				}
				else if (choice == 6) {
					printAll();
				}
				else if (choice == 7) {
					count();
				}
				else if (choice == 8) {
					showMainMenu();
				}
				
				else if (choice == 9) {
					exit();
				}

			}
			while (choice != this.mainMenu.getIndexOfExitOption());

		}

	private void printAll()
	{
		manager.open();
		manager.print();
		manager.close();
	}

	private void delete()
	{
		System.out.println("Please enter the index of the entry you want to delete: ");
		int index = ScannerUtility.getInt("Index: ");
		manager.open();
		manager.delete(index);
		manager.close();
	}
	
	private void deleteAll()
	{
		manager.open();
		manager.deleteAll();
		manager.close();
	}
	
	private void editFile()
	{
		manager.open();
		System.out.println("Please enter the index of the entry you want to edit: ");
		int index = ScannerUtility.getInt("Index: ");
		System.out.println("Please enter the show details:");
		String name = ScannerUtility.getString("Name: ");
		String producer = ScannerUtility.getString("Producer: ");
		String genre = ScannerUtility.getString("genre: ");
		double price = ScannerUtility.getDouble("price: ");
		int episodeCount = ScannerUtility.getInt("Episode Count: ");
		int duration = ScannerUtility.getInt("Duration: ");
		
		manager.write(new Show(name, producer, genre, price, episodeCount, duration), index);
		manager.close();
	}

	private void addToFile()
	{
		manager.open();
		System.out.println("Please enter the show details:");
		String name = ScannerUtility.getString("Name: ");
		String producer = ScannerUtility.getString("Producer: ");
		String genre = ScannerUtility.getString("genre: ");
		double price = ScannerUtility.getDouble("price: ");
		int episodeCount = ScannerUtility.getInt("Episode Count: ");
		int duration = ScannerUtility.getInt("Duration: ");
		
		manager.write(new Show(name, producer, genre, price, episodeCount, duration));
		manager.close();
	}
	
	private void count()
	{
		System.out.println("The database has "+manager.size()+" records");
	}

	private void exit()
	{
		System.out.println("GoodBye");
		System.exit(0);
	}
	

	
	private void findByGenre()
	{
		String str = ScannerUtility.getString("Enter the genre you want to search for: ");
		
		manager.open();
		manager.find(new ShowGenreFilter(str));
		manager.close();
	}

	private void findByName()
	{
		String str = ScannerUtility.getString("Enter the name you want to search for: ");
		
		manager.open();
		manager.find(new ShowNameFilter(str));
		manager.close();
	}

	private void findByPrice()
	{
		double lo = ScannerUtility.getDouble("Enter the lowest price: ");
		double hi = ScannerUtility.getDouble("Enter the Highest price: ");
		
		manager.open();
		manager.find(new ShowPriceRangeFilter(lo, hi));
		manager.close();
	}
	
	private boolean deleteFile()
	{
		strPath = ScannerUtility.getString("Enter the path for the database: ");
		strName = ScannerUtility.getString("Enter the name for the database: ");
		
		boolean deleted = FileUtility.delete(strPath, strName);
		return deleted;
	}
	
	private boolean createFile()
	{
		strPath = ScannerUtility.getString("Enter the path for the database: ");
		strName = ScannerUtility.getString("Enter the name for the database: ");
		
		boolean created = FileUtility.create(strPath, strName);
		return created;
	}
	
	private boolean openFile()
	{
		strPath = ScannerUtility.getString("Enter the path for the database: ");
		strName = ScannerUtility.getString("Enter the name for the database: ");
		
		boolean exists = FileUtility.open(strPath, strName, strPermissions);
		return exists;
	}
}





