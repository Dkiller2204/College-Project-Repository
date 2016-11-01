package Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Controller.ShowGenreFilter;
import Controller.ShowNameFilter;
import Controller.ShowPriceRangeFilter;
import Model.Show;
import Utility.FileUtility;
import View.MainApp;

public class testCases {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
		// create first
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		boolean create = FileUtility.create(path, name);
		assertEquals(create, true);
	}

	@Test
	public void testCreate_NotFound() {
		// try creating something with no name or path
		String path = "";
		String name = "";
		boolean create = FileUtility.create(path, name);
		assertEquals(create, false);
	}

	@Test
	public void testCreateUnique() {
		// create first
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		// try creating again but already exists
		String path2 = "";
		String name2 = "test1.txt";
		boolean create2 = FileUtility.create(path2, name2);
		assertEquals(create2, false);
	}

	@Test
	public void testOpen() {
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		boolean open = FileUtility.open(path, name, "rw");
		assertEquals(open, true);
	}

	@Test
	public void testOpen_NotFound() {
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		boolean open = FileUtility.open(path, name, "rw");
		assertEquals(open, false);
	}

	@Test
	public void testDelete() {
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		boolean create = FileUtility.create(path, name);
		assertEquals(create, true);
		boolean delete = FileUtility.delete(path, name);
		assertEquals(delete, true);
	}

	@Test
	public void testDelete_NotFound() {
		String path = "";
		String name = "test1.txt";
		boolean delete = FileUtility.delete(path, name);//incase it exists form previos tests
		boolean delete1 = FileUtility.delete(path, name);
		assertEquals(delete1, false);
	}

	@Test
	public void testAdd() {
		Show show;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		boolean write =MainApp.manager.write(show);
		MainApp.manager.deleteAll();
		MainApp.manager.close();
		assertEquals(write, true);
	}

	@Test
	public void testAddUnique() {
		Show show;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		boolean write =MainApp.manager.write(show);
		assertEquals(write, true);
		boolean write1 =MainApp.manager.write(show);
		assertEquals(write1, false);
		MainApp.manager.deleteAll();
		MainApp.manager.close();
	}

	@Test
	public void testEdit() {
		Show show,show1,show2,showEdit,readShow;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		show1 = new Show("The Monkey", "Peter", "Explicit", 69.27, 1, 180);
		show2 = new Show("The Start", "Daniel", "Comedy", 1.99, 1, 5);
		showEdit = new Show("The Super Monkey", "Peter", "Explicit", 99.99, 1, 200);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		MainApp.manager.write(show);
		MainApp.manager.write(show1);
		MainApp.manager.write(show2);
		boolean write = MainApp.manager.write(showEdit, 1);
		assertEquals(write, true);
		readShow = MainApp.manager.read(1);
		assertEquals(showEdit, readShow);
		MainApp.manager.deleteAll();
		MainApp.manager.close();
	}

	@Test
	public void testEdit_NotFound() {
		Show show,show1,show2,showEdit;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		show1 = new Show("The Monkey", "Peter", "Explicit", 69.27, 1, 180);
		show2 = new Show("The Start", "Daniel", "Comedy", 1.99, 1, 5);
		showEdit = new Show("The Super Monkey", "Peter", "Explicit", 99.99, 1, 200);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		MainApp.manager.write(show);
		MainApp.manager.write(show1);
		MainApp.manager.write(show2);
		boolean write = MainApp.manager.write(showEdit, 9);
		assertEquals(write, false);
		MainApp.manager.deleteAll();
		MainApp.manager.close();
	}

	@Test
	public void testSearchByCriteria() {
		int amount;
		Show show,show1,show2,show3;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		show1 = new Show("The Monkey", "Peter", "Explicit", 69.27, 1, 180);
		show2 = new Show("The Start", "Daniel", "Comedy", 1.99, 1, 7);
		show3 = new Show("The Middle", "Daniel", "Action", 1.99, 1, 6);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		MainApp.manager.write(show);
		MainApp.manager.write(show1);
		MainApp.manager.write(show2);
		MainApp.manager.write(show3);
		amount = MainApp.manager.find(new ShowGenreFilter("Action"));
		assertEquals(amount, 2);
		amount = MainApp.manager.find(new ShowNameFilter("The End"));
		assertEquals(amount, 1);
		amount = MainApp.manager.find(new ShowPriceRangeFilter(1,10));
		assertEquals(amount, 3);
		MainApp.manager.deleteAll();
		MainApp.manager.close();
	}

	@Test
	public void testSearchByCriteria_NotFound() {
		int amount;
		Show show,show1,show2,show3;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		show1 = new Show("The Monkey", "Peter", "Explicit", 69.27, 1, 180);
		show2 = new Show("The Start", "Daniel", "Comedy", 1.99, 1, 7);
		show3 = new Show("The Middle", "Daniel", "Action", 1.99, 1, 6);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		MainApp.manager.write(show);
		MainApp.manager.write(show1);
		MainApp.manager.write(show2);
		MainApp.manager.write(show3);
		amount = MainApp.manager.find(new ShowGenreFilter("Fantasy"));
		assertEquals(amount, 0);
		amount = MainApp.manager.find(new ShowNameFilter("The End 2"));
		assertEquals(amount, 0);
		amount = MainApp.manager.find(new ShowPriceRangeFilter(1000,1001));
		assertEquals(amount, 0);
		MainApp.manager.deleteAll();
		MainApp.manager.close();
	}

	@Test
	public void testDelete2() {
		Show show,show1,show2;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		show1 = new Show("The Monkey", "Peter", "Explicit", 69.27, 1, 180);
		show2 = new Show("The Start", "Daniel", "Comedy", 1.99, 1, 5);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		MainApp.manager.write(show);
		MainApp.manager.write(show1);
		MainApp.manager.write(show2);
		assertEquals(MainApp.manager.size(), 3);
		boolean delete = MainApp.manager.delete(1);
		assertEquals(delete, true);
		assertEquals(MainApp.manager.size(), 2);
		MainApp.manager.deleteAll();
		MainApp.manager.close();
	}

	@Test
	public void testDelete_NotFound2() {
		Show show,show1,show2;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		show1 = new Show("The Monkey", "Peter", "Explicit", 69.27, 1, 180);
		show2 = new Show("The Start", "Daniel", "Comedy", 1.99, 1, 5);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		MainApp.manager.write(show);
		MainApp.manager.write(show1);
		MainApp.manager.write(show2);
		assertEquals(MainApp.manager.size(), 3);
		boolean delete = MainApp.manager.delete(5);
		assertEquals(delete, false);
		assertEquals(MainApp.manager.size(), 3);
		MainApp.manager.deleteAll();
		MainApp.manager.close();
	}

	@Test
	public void testDeleteAll() {
		Show show,show1,show2;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		show1 = new Show("The Monkey", "Peter", "Explicit", 69.27, 1, 180);
		show2 = new Show("The Start", "Daniel", "Comedy", 1.99, 1, 5);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		MainApp.manager.write(show);
		MainApp.manager.write(show1);
		MainApp.manager.write(show2);
		MainApp.manager.deleteAll();
		assertEquals(0, MainApp.manager.size());
		MainApp.manager.close();
	}

	@Test
	public void testShowAll() {
		Show show,show1,show2;
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		FileUtility.create(path, name);
		FileUtility.open(path, name, "rw");
		show = new Show("The End", "Daniel", "Action", 1.99, 1, 5);
		show1 = new Show("The Monkey", "Peter", "Explicit", 69.27, 1, 180);
		show2 = new Show("The Start", "Daniel", "Comedy", 1.99, 1, 5);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		MainApp.manager.write(show);
		MainApp.manager.write(show1);
		MainApp.manager.write(show2);
		int size = MainApp.manager.size();
		int printAmount =MainApp.manager.print();
		assertEquals(size, printAmount);
		MainApp.manager.deleteAll();
		MainApp.manager.close();
	}

	@Test
	public void testShowAll_NoData() {
		String path = "";
		String name = "test1.txt";
		FileUtility.delete(path, name);
		boolean create = FileUtility.create(path, name);
		assertEquals(create, true);
		boolean open = FileUtility.open(path, name, "rw");
		assertEquals(open, true);
		MainApp.manager.open();
		MainApp.manager.deleteAll();
		int printAmount =MainApp.manager.print();
		assertEquals(0, printAmount);
		MainApp.manager.close();
	}
}
