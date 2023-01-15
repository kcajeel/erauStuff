/* 
Class: RoadController
Name: Jack Lee
Date: 11/18/2022
Purpose: contains all methods to run the traffic simulation
Attributes: 
	-cars:ArrayList<GenericCar>
	-speedLimit:double
	-weather:String
	-distanceRange:double[2]
	-isPaused:boolean
Methods: 
	+testObjects:void -used during development
	+runTestCases:void -runs test cases
	+calcTotalCars:double -returns total number of cars
	+calcAverageSpeed:double -returns average speed of cars
	+calcFlowRate:double -returns the flow rate of the cars
	+isNextLaneOpen(GenericCar):double -returns -1 if no lanes are open, otherwise returns an open lane number
	+areCarsWithinVisibility(GenericCar):boolean -boolean test
	+checkMirrors(GenericCar):void -changes car's lane if cars are within visibility and a lane is open
	+moveCar(GenericCar):void -accelerates the car and moves its position according to its velocity
	+testVisibility(GenericCar):void -if there are cars within the visibility range, slow down the given car
	+isSpeedValid(GenericCar):boolean -boolean test
	+isCarAccelerationGreater(GenericCar):boolean -boolean test
	+isCarDecelerationGreater(GenericCar):boolean -boolean test
	+isCarMaxSpeedGreater(GenericCar):boolean -boolean test
	+readFromFile(String):void -creates Cars and Drivers from file input
	+createCar(String, int, double, double, String):void -creates Cars of every type and adds them to the cars ArrayList
	+createDriver(String):GenericDriver -creates drivers given a String driver type
	+countLanes(ArrayList<Integer>):void -given an array of lane numbers, determines the highest and sets numLanes equal to it
 */
 // --keyboard.scanner
 // give driver information about the car in front (location, speed/pass the car) instead of having RoadController do everything
 // add slamming on the brakes type deceleration
 
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
 
 public class RoadController{
	private ArrayList<GenericCar> cars = new ArrayList<GenericCar>();
	private double speedLimit;
	private String weather; //enum class
	private int numLanes;
	private double[] distanceRange = new double[2];
	private boolean isPaused = false;
	
	// public void testObjects(){
		// while (!isPaused){
			// for(GenericCar i: cars){
				// if(!areCarsWithinVisibility(i) && isSpeedValid(i)){
					// i.setSpeed(i.getSpeed() + i.getAcceleration());
				// }
				// else if(areCarsWithinVisibility(i)){
					// if(isNextLaneOpen(i)){
						// i.setPosition(1, (double)numLanes);
					// }
					// else{
						// i.setSpeed(i.getSpeed() - i.getDeceleration());
					// }
				// }
				// if(isSpeedValid(i)){
					// System.out.println(i.toString());
					// i.setPosition(0,i.getPosition(0)+i.getSpeed());
				// }
			// }
			// System.out.println("Total cars: "+calcTotalCars()+"\nAverage speed: "+calcAverageSpeed()+"\nFlow rate: "+calcFlowRate());
		// }
	// }
	
	public void runTestCases(){
		GenericDriver testDriver = new GenericDriver(10,10,200,10);
		GenericCar testCar = new GenericCar(0,0,testDriver,7,7,150);		//test case 1
		cars.add(testCar);
		System.out.println("If the code is runing, Test Case 1 passed. \nTest Case 2: "+testCar.getDriver().getMaxSpeed()+"\nTest Case 3: "+testCar.getDriverInfo()+"\nTest Case 4: position before: "+testCar.getPosition(0));
		testCar.setPosition(0,45);
		System.out.println("Position after: "+testCar.getPosition(0)+"\nTest Case 5: lane before: "+testCar.getPosition(1));
		testCar.setPosition(1,3);
		System.out.println("Lane after: "+testCar.getPosition(1)+"\nTest Case 6: Distance before: "+testCar.getPosition(0));
		moveCar(testCar);
		System.out.println("Distance after: "+testCar.getPosition(0));
		System.out.println("Test Cases 7,8,9: \nTotal cars: "+calcTotalCars()+"\nAverage speed: "+calcAverageSpeed()+"\nFlow rate: "+calcFlowRate());
		GenericCar testCar2 = new GenericCar(testCar.getPosition(0)+3,0,testDriver,4,4,90);
		cars.add(testCar2);
		System.out.println("Test Case 10: \nSpeed before: "+testCar.getSpeed());
		testVisibility(testCar);
		System.out.println("Speed after: "+testCar.getSpeed());
		testCar2.setPosition(0,0);
		moveCar(testCar2);
		System.out.println("Test Case 11: \nSpeed before: "+testCar2.getSpeed());
		testVisibility(testCar2);
		System.out.println("Speed after: "+testCar2.getSpeed());
		//test case 12 skipped for P5 (will be 
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
			if((i.getPosition(0) >= distanceRange[0]) && (i.getPosition(0) <= distanceRange[1])){
				sum ++;
			}
		}
		return (calcAverageSpeed() * sum);
	}
	
	public double isNextLaneOpen(GenericCar car){
		double lane = 0;
		for(GenericCar i: cars){
			//System.out.println("isNextLaneOpen running, i lane: "+i.getPosition(1)+", car lane: "+car.getPosition(1)+", i and car positions: "+i.getPosition(0)+", "+car.getPosition(0));
			if((i.getPosition(1) != car.getPosition(1)) && (i.getPosition(0) == car.getPosition(0))){
				return -1;
			}
			//lane has to be within 1 of the car's lane and less than numLanes
			if(((lane == car.getPosition(1)-1) || (lane == car.getPosition(1)+1)) && (lane < (numLanes+1))){
				lane = i.getPosition(1);
			}
		}
		return lane;
	}
	
	public boolean areCarsWithinVisibility(GenericCar car){
		//System.out.println("areCarsWithinVisibility running: car index: "+cars.indexOf(car)+", size: "+cars.size());
		for(GenericCar i: cars){
			if((i.getPosition(0) < (car.getPosition(0) + car.getDriver().getVisibility())) && (i != car))	{
				//System.out.println("if statement passed, i position: "+i.getPosition(0)+" visibility range: "+car.getPosition(0)+" to "+(car.getPosition(0)+car.getDriver().getVisibility()));
				return true;
			}
		}
	return false;
	}
	
	public void checkMirrors(GenericCar car){
		if((isNextLaneOpen(car) != -1) && (areCarsWithinVisibility(car))){
			car.setPosition(1,isNextLaneOpen(car));
		}
		else{
			testVisibility(car);
		}
	}
	
	public void moveCar(GenericCar car){
		car.setSpeed(car.getSpeed()+car.getAcceleration());
		car.setPosition(0,car.getPosition(0)+car.getSpeed());
	}
	
	public void testVisibility(GenericCar car){
		if(areCarsWithinVisibility(car)){
			car.setSpeed(car.getSpeed()-car.getDeceleration());
			car.setPosition(0,car.getPosition(0)+car.getSpeed());
		}
	}
	
	public boolean isSpeedValid(GenericCar car){
		if((car.getSpeed() < car.getMaxSpeed()) && (car.getSpeed() < speedLimit)){
			return true;
		}
		car.setSpeed(speedLimit-car.getAcceleration());
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
	
	public void readFromFile(String filename){
		String line;
		int lineNumber = 0;
		try{
			File trafficFile = new File(filename);
			FileReader fw = new FileReader(trafficFile);
			BufferedReader br = new BufferedReader(fw);	
			ArrayList allLanes = new ArrayList();
			while((line = br.readLine()) != null){
				String[] columns = line.split(",");
				System.out.println("Line: "+line);
				for(String i: columns){
					if(i.equals("")){
						System.out.println("Error: This file cannot be interpreted. ");
						//System.exit(1); //uncomment after test cases
					}
				}
				createCar(columns[0], lineNumber, Double.parseDouble(columns[1]), Double.parseDouble(columns[2]), columns[3]);
				allLanes.add(Integer.parseInt(columns[2]));
				lineNumber++;
			}
			countLanes(allLanes);
		}
		catch(Exception e){
			// System.out.println(e); //uncomment this line to debug
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
		// System.out.println("There are "+maxLane+" lanes. ");
		numLanes = maxLane;
	}
 }