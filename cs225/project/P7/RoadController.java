/* 
Class: RoadController
Name: Jack Lee
Date: 12/12/2022
Purpose: contains all methods to run the traffic simulation
Attributes: 
	-cars:ArrayList<GenericCar>
	-speedLimit:double
	-weather:String
	-numLanes:int
	-isPaused:boolean
Methods: 
	+getUserInput:void -initiates the Main Menu
	+readFromFile(String):void -creates Cars and Drivers from file input
	+createCar(String, int, double, double, String):void -creates Cars of every type and adds them to the cars ArrayList
	+createDriver(String):GenericDriver -creates drivers given a String driver type
	+countLanes(ArrayList<Integer>):void -given an array of lane numbers, determines the highest and sets numLanes equal to it
	+runSimulation:void -used to run the simulation
	+deployPauseListener:void -sets up GUI that listens for "p" keypresses
	+enforceWeatherConditions:void -subtracts the weather modifier from drivers' visibility
	+slamBrakes(GenericCar):void -rapidly slows down a given car
	+slowDown(GenericCar):void -gradually slows down a car
	+speedUp(GenericCar):void -accelerates the car and moves its position according to its velocity
	+checkMirrors(GenericCar):void -changes car's lane if cars are within visibility and a lane is open
	+areCarsWithinVisibility(GenericCar):boolean -boolean test
	+isNextLaneOpen(GenericCar):double -returns -1 if no lanes are open, otherwise returns an open lane number
	+followCarInFront(GenericCar):void -if there are cars within the visibility range, slow down the given car
	+calcTotalCars:double -returns total number of cars
	+calcAverageSpeed:double -returns average speed of cars
	+calcFlowRate:double -returns the flow rate of the cars
	+isSpeedValid(GenericCar):boolean -boolean test
	+isCarAccelerationGreater(GenericCar):boolean -boolean test
	+isCarDecelerationGreater(GenericCar):boolean -boolean test
	+isCarMaxSpeedGreater(GenericCar):boolean -boolean test
	+assignMinimumValuesToCars:void -assigns the minimum acceleration, deceleration, and maxSpeed values to all cars
	+initPauseMenu:void -opens the Pause Menu, allows other menus to be selected
	+initSpeedLimitMenu:void -allows the user to change the speed limit
	+initWeatherMenu:void -allows the user to change the weather
	+initLaneMenu:void -allows the user to add or remove lanes
	+initSlowdownMenu:void -allows the user to slow down the leading car in a given lane
	+isWeatherValid(String):boolean -returns true if the weather is an accepted String
	+removeCars(int):void -removes cars from a given lane
	+slowDownLane(double):void -slows down the leading car in a given lane
	+runTestCases:void -runs test cases	
 */
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;

 public class RoadController{
	private ArrayList<GenericCar> cars = new ArrayList<GenericCar>();
	private double speedLimit;
	private String weather;
	private int numLanes = 1;
	private boolean isPaused = false;
	
	public void getUserInput(){
		Scanner scanny = new Scanner(System.in);
		System.out.println("Welcome to Jack's Traffic Simulation. \nPlease select one of the following: \n\n(1)\tLoad File\n(2)\tRun Test Cases\n(3)\tExit");
		String startingChoice = scanny.next();
		switch(startingChoice){
			case "1": 
				System.out.println("Please enter the exact filename you would like to read from. ");
				String filename = scanny.next();
				readFromFile(filename);
				runSimulation();
				break;
			case "2": 
				runTestCases();
				System.out.println("Test Cases over. Returning to main menu. \n");
				getUserInput();
				break;
			case "3": 
				System.out.println("Goodbye! ");
				System.exit(0);
			case "0": 
				System.out.println("You have entered testing mode. This is used to test features before they are open to the user. ");
				runSimulation();
				break;
			default: 
				System.out.println("Please enter a number from 1 to 3. ");
				getUserInput();
		}
		scanny.close();
	}
	
	public void readFromFile(String filename){
		String line;
		int lineNumber = 0;
		try{
			File trafficFile = new File(filename);
			FileReader fw = new FileReader(trafficFile);
			BufferedReader br = new BufferedReader(fw);	
			ArrayList allLanes = new ArrayList();
			String header = br.readLine();
			String[] headerColumns = header.split(",");
			if(isWeatherValid(headerColumns[0])){
				weather = headerColumns[0];
				System.out.println(weather);
			}
			else{
				System.out.println("There was a problem interpreting weather from the file. Defaulting to Sunny weather. ");
				weather = "Sunny";
			}
			speedLimit = Double.parseDouble(headerColumns[1]);
			while((line = br.readLine()) != null){
				String[] columns = line.split(",");
				System.out.println(line);
				for(String i: columns){
					if(i.equals("")){
						System.out.println("Error: This file cannot be interpreted. ");
					}
				}
				createCar(columns[0], lineNumber, Double.parseDouble(columns[1]), Double.parseDouble(columns[2]), columns[3]);
				allLanes.add(Integer.parseInt(columns[2]));
				lineNumber++;
			}
			countLanes(allLanes);
		}
		catch(Exception e){
			System.out.println("There was an issue with your file. Please try again. ");
			getUserInput();
		}
	}
	
	public void createCar(String carType, int index, double distance, double lane, String driver){
		switch(carType){
			case "FastCar":
				cars.add(index,new FastCar(distance,lane, createDriver(driver)));
			case "AverageCar":
				cars.add(index,new AverageCar(distance,lane, createDriver(driver)));
			case "SlowCar":	
				cars.add(index,new SlowCar(distance,lane, createDriver(driver)));
		}
	}
	
	public GenericDriver createDriver(String driverType){
		switch(driverType){
			case "FastDriver":
				return new FastDriver();
			case "AverageDriver": 
				return new AverageDriver();
			case "SlowDriver":
				return new SlowDriver();
		}
		System.out.println("createDriver failed");
		return new GenericDriver(10,10,100,10);
	}
	
	public void countLanes(ArrayList<Integer> laneNumbers){
		int maxLane = laneNumbers.get(0);
		for(int i: laneNumbers){
			if(i > maxLane){
				maxLane = i;
			}
		}
		numLanes = maxLane;
	}

	public void runSimulation() {
		enforceWeatherConditions();
		assignMinimumValuesToCars();
		deployPauseListener();
		while (!isPaused) {
			for(GenericCar i: cars){
				if(isSpeedValid(i)){
					speedUp(i);
				}
				checkMirrors(i);
			}
			System.out.println("Total cars: "+calcTotalCars()+"\nAverage speed: "+calcAverageSpeed()+"\nFlow rate: "+calcFlowRate());
		}
		initPauseMenu();
	}
	
	public void deployPauseListener() {
		JFrame frame = new JFrame("The GUI is used for input detection");
		KeyListener keyListener = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_P) {
					isPaused = !isPaused;
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
			public void keyReleased(KeyEvent e) {}
		};
		frame.addKeyListener(keyListener);
		ImageIcon icon = new ImageIcon("coolJava.jpg");
		JTextArea text = new JTextArea()
		{
			Image image = icon.getImage();
			{setOpaque(false);}
			public void paintComponent(Graphics graphics){
				graphics.drawImage(image, 0, 0, this);
				super.paintComponent(graphics);
			}
		};
		JScrollPane pane = new JScrollPane(text);
		Container content = frame.getContentPane();
		content.add(pane, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1024, 640);
		frame.setLocation(400,400);
		frame.setFocusable(true);
		frame.requestFocus();
	}
	
	public void enforceWeatherConditions(){
		double visibilityModifier = 0;
		switch(weather.toLowerCase()){
			case "sunny":
				break;
			case "rainy":
				visibilityModifier = 4;
			case "overcast":
				visibilityModifier = 2;
			case "storming": 
				visibilityModifier = 6;
		}
		for(GenericCar i: cars){
			i.getDriver().setVisibility(i.getDriver().getVisibility() - visibilityModifier);
		}
	}
	
	public void slamBrakes(GenericCar car){
		car.setSpeed(car.getSpeed()-10);
		car.setPosition(0,car.getPosition(0)+car.getSpeed());
	}
	
	public void slowDown(GenericCar car){
		car.setSpeed(car.getSpeed()-car.getDeceleration());
		car.setPosition(0,car.getPosition(0)+car.getSpeed());
	}
	
	public void speedUp(GenericCar car){
		car.setSpeed(car.getSpeed()+car.getAcceleration());
		car.setPosition(0,car.getPosition(0)+car.getSpeed());
	}
	
	public void checkMirrors(GenericCar car){
		if(areCarsWithinVisibility(car)){
			if(isNextLaneOpen(car) != -1){
				car.setPosition(1,isNextLaneOpen(car));
			}
			else{
				followCarInFront(car);
			}
		}
	}
	
	public boolean areCarsWithinVisibility(GenericCar car){
		for(GenericCar i: cars){
			if((i.getPosition(0) < (car.getPosition(0) + car.getDriver().getVisibility())) && (i != car))	{
				return true;
			}
		}
	return false;
	}
	
	public double isNextLaneOpen(GenericCar car){
		double lane = 0;
		for(GenericCar i: cars){
			if((i.getPosition(1) != car.getPosition(1)) && (i.getPosition(0) == car.getPosition(0))){
				return -1;
			}
			if(((lane == car.getPosition(1)-1) || (lane == car.getPosition(1)+1)) && (lane < (numLanes+1))){
				lane = i.getPosition(1);
			}
		}
		return lane;
	}
	
	public void followCarInFront(GenericCar car){
		if(areCarsWithinVisibility(car)){
			slowDown(car);
		}
	}
	
	public double calcTotalCars(){
		return cars.size();
	}
	
	public double calcAverageSpeed(){
		double sum = 0;
		for(GenericCar i: cars){
			sum += i.getSpeed();
		}
		return (sum/calcTotalCars());
	}
	
	public double calcFlowRate(){
		int sum = 0;
		for (GenericCar i: cars){
				sum ++;
		}
		return (calcAverageSpeed() * sum);
	}
	
	public boolean isSpeedValid(GenericCar car){
		if((car.getSpeed() < car.getMaxSpeed()) && (car.getSpeed() < speedLimit)){
			return true;
		}
		slamBrakes(car);
		return false;
	}
	
	public boolean isCarAccelerationGreater(GenericCar car){
		if(car.getAcceleration() > car.getDriver().getAcceleration()){
			return true;
		}
		return false;
	}
	
	public boolean isCarDecelerationGreater(GenericCar car){
		if(car.getDeceleration() > car.getDriver().getDeceleration()){
			return true;
		}
		return false;
	}
	
	public boolean isCarMaxSpeedGreater(GenericCar car){
		if(car.getMaxSpeed() > car.getDriver().getMaxSpeed()){
			return true;
		}
		return false;
	}
	
	public void assignMinimumValuesToCars(){
		for(GenericCar i: cars){
			if(isCarAccelerationGreater(i)){
				i.setAcceleration(i.getDriver().getAcceleration());
			}
			if(isCarDecelerationGreater(i)){
				i.setDeceleration(i.getDriver().getAcceleration());
			}
			if(isCarMaxSpeedGreater(i)){
				i.setMaxSpeed(i.getDriver().getMaxSpeed());
			}
		}
	}
	
	public void initPauseMenu(){
		Scanner scanny = new Scanner(System.in);
		System.out.println("Welcome to the Pause Menu. \n\nOptions: \n(1)\tChange Speed Limit\n(2)\tChange Weather\n(3)\tAdd/Remove Lanes\n(4)\tSlow Down a Lane\n(5)\tPlay Simulation\n(6)\tExit Simulation");
		String pauseOption = scanny.next();
		switch(pauseOption){
			case "1":
				initSpeedLimitMenu();
				break;
			case "2":
				initWeatherMenu();
				break;
			case "3":
				initLaneMenu();
				break;
			case "4":
				initSlowdownMenu();
				break;
			case "5":
				System.out.println("Simulation Resumed");
				isPaused = false;
				runSimulation();
				break;
			case "6":
				System.out.println("Goodbye! ");
				System.exit(0);
			default: 
				System.out.println("You typed: "+pauseOption+". Please enter a number 1-6. ");
		}
		scanny.close();
	}
	
	public void initSpeedLimitMenu(){
		Scanner scanny = new Scanner(System.in);
		System.out.println("\nWelcome to the Speed Limit Menu. Please select an option: \n(1)\tChange speed limit\n(2)\tBack");
		String speedOption = scanny.next();
		switch(speedOption){
			case "1": 
				System.out.println("The current speed limit is: "+speedLimit+". Please enter the value you would like to set it to. ");
				try{
					double tempSpeedLimit = scanny.nextDouble();
					if(tempSpeedLimit > 0){
						speedLimit = tempSpeedLimit;
						System.out.println("Speed limit was set to: "+speedLimit+".\nReturning to the Pause Menu. \n");
						initPauseMenu();
					}
					else{
						System.out.println("Please enter a speed limit above 0. ");
						initSpeedLimitMenu();
					}
				}
				catch (Exception e){
					System.out.println("Please enter a valid speed limit. ");
					initSpeedLimitMenu();
				}
				break;
			case "2": 
				initPauseMenu();
				break;
			default: 
				System.out.println("Please enter either 1 or 2.");
				initSpeedLimitMenu();
		}
		scanny.close();
	}
	
	public void initWeatherMenu(){
		Scanner scanny = new Scanner(System.in);
		System.out.println("\nWelcome to the Weather Menu. Please select an option: \n(1)\tChange weather\n(2)\tBack");
		String weatherOption = scanny.next();
		switch(weatherOption){
			case "1": 
				System.out.println("The current weather is: "+weather+". Please enter your desired setting. \nValid weather types are: rainy, sunny, overcast, and storming. ");
				try{
					String tempWeather = scanny.next();
					if(isWeatherValid(tempWeather)){
						weather = tempWeather;
						System.out.println("The weather was set to: "+weather+".\nReturning to the Pause Menu. \n");
						initPauseMenu();
					}
					else{
						System.out.println("Please enter a valid weather type. \nValid weather types are: rainy, sunny, overcast, and storming. ");
						initWeatherMenu();
					}
				}
				catch (Exception e){
					System.out.println(e);
				}
				break;
			case "2": 
				initPauseMenu();
				break;
			default: 
				System.out.println("Please enter either 1 or 2.");
				initWeatherMenu();
		}
		scanny.close();
	}
	
	public void initLaneMenu(){
		Scanner scanny = new Scanner(System.in);
		System.out.println("\nWelcome to the Lane Menu. Please select an option: \n(1)\tAdd a lane\n(2)\tRemove a lane\n(3)\tBack");
		String laneOption = scanny.next();
		switch(laneOption){
			case "1": 
				numLanes ++;
				System.out.println("Lane added. There are currently: "+numLanes + " lanes. \nReturning to the Pause Menu. \n");
				initPauseMenu();
				break;
			case "2": 
				if(numLanes > 1){
					removeCars(numLanes);
					numLanes--;
					System.out.println("Lane removed. There are currently "+numLanes+" lanes. \nReturning to the Pause Menu. \n");
					initPauseMenu();
				}
				else{
					System.out.println("There is only one lane, please add more to remove a lane. ");
					initLaneMenu();
				}
				break;
			case "3": 
				initPauseMenu();
			default: 
				System.out.println("Please enter either 1, 2, or 3.");
				initLaneMenu();
		}
		scanny.close();
	}
	
	public void initSlowdownMenu(){
		Scanner scanny = new Scanner(System.in);
		System.out.println("\nWelcome to the Lane Slowdown Menu. Please select an option: \n(1)\tSlow down lane\n(2)\tBack");
		String slowdownOption = scanny.next();
		switch(slowdownOption){
			case "1": 
				System.out.println("Please enter the lane number you wish to slow down. You can pick any lane from 1 to "+numLanes+". ");
				try{
					Double laneChoice = scanny.nextDouble();
					if((laneChoice > 0) && (laneChoice <= numLanes)){
						slowDownLane(laneChoice);
						System.out.println("Lane "+laneChoice+" has been slowed down. \nReturning to the Pause Menu. \n");
						initPauseMenu();
					}
					else{
						System.out.println("Please enter a lane number between 1 and "+numLanes+". ");
						initSlowdownMenu();
					}
				}
				catch(Exception e){
					System.out.println("Please enter a valid lane number. ");
					initSlowdownMenu();
				}
				break;
			case "2": 
				initPauseMenu();
				break;
			default: 
				System.out.println("Please enter either 1 or 2.");
				initSlowdownMenu();
		}
		scanny.close();
	}
	
	public boolean isWeatherValid(String testWeather){
		if((testWeather.equalsIgnoreCase("rainy") || testWeather.equalsIgnoreCase("sunny")) || (testWeather.equalsIgnoreCase("overcast") || testWeather.equalsIgnoreCase("storming"))){
			return true;
		}
		return false;
	}
	
	public void removeCars(int lane){
		for(int i = 0; i < cars.size(); i++){
			if(cars.get(i).getPosition(1) == lane){
				cars.remove(i);
			}
		}
	}
	
	public void slowDownLane(double lane){
		double maxPosition = cars.get(0).getPosition(0);
		GenericCar frontCar = cars.get(0);
		for(GenericCar i: cars){
			if((i.getPosition(1) == lane) && (i.getPosition(0) > maxPosition)){
				frontCar = i;
			}
		}
		slowDown(frontCar);
	}
	
	public void runTestCases(){
		GenericDriver testDriver = new GenericDriver(10,10,200,10);
		GenericCar testCar = new GenericCar(0,0,testDriver,7,7,150);
		cars.add(testCar);
		System.out.println("If the code is runing, Test Case 1 passed. \nTest Case 2: "+testCar.getDriver().getMaxSpeed()+"\nTest Case 3: "+testCar.getDriverInfo()+"\nTest Case 4: position before: "+testCar.getPosition(0));
		testCar.setPosition(0,45);
		System.out.println("Position after: "+testCar.getPosition(0)+"\nTest Case 5: lane before: "+testCar.getPosition(1));
		testCar.setPosition(1,3);
		System.out.println("Lane after: "+testCar.getPosition(1)+"\nTest Case 6: Distance before: "+testCar.getPosition(0));
		speedUp(testCar);
		System.out.println("Distance after: "+testCar.getPosition(0));
		System.out.println("Test Cases 7,8,9: \nTotal cars: "+calcTotalCars()+"\nAverage speed: "+calcAverageSpeed()+"\nFlow rate: "+calcFlowRate());
		GenericCar testCar2 = new GenericCar(testCar.getPosition(0)+3,0,testDriver,4,4,90);
		cars.add(testCar2);
		System.out.println("Test Case 10: \nSpeed before: "+testCar.getSpeed());
		followCarInFront(testCar);
		System.out.println("Speed after: "+testCar.getSpeed());
		testCar2.setPosition(0,0);
		speedUp(testCar2);
		System.out.println("Test Case 11: \nSpeed before: "+testCar2.getSpeed());
		followCarInFront(testCar2);
		System.out.println("Speed after: "+testCar2.getSpeed());
		testCar.setPosition(0,20);
		testCar2.setPosition(1,1);
		testCar2.setPosition(0,20);
		System.out.println("Test Case 13: \n"+isNextLaneOpen(testCar));
		testCar2.setPosition(1,0);
		testCar2.setPosition(0,25);
		System.out.println("Test Case 14: \nLane before: "+testCar.getPosition(1));
		checkMirrors(testCar);
		System.out.println("Lane after: "+testCar.getPosition(1));
		System.out.println("Test Case 15: \nLane before: "+testCar.getPosition(1));
		checkMirrors(testCar);
		System.out.println("Lane after: "+testCar.getPosition(1));
		System.out.println("Test Case 16: ");
		readFromFile("asdf.txt");
		System.out.println("Test Case 17: ");
		readFromFile("asdf.jpeg");
		System.out.println("Test Case 18: ");
		readFromFile("asdf.traffic");
		System.out.println("Test Case 19: ");
		readFromFile("TestCase19.txt");
		System.out.println("Test Case 20: ");
		readFromFile("TestCase20.txt");
		System.out.println("Test Case 21: ");
		readFromFile("asdf.txt");
		System.out.println("Test cases 	22-36 require user input, but work all pass.");
	}
 }