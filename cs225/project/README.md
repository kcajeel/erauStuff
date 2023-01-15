# CS225 Project
My final project for CS225, which teaches students the Java language and OOP (Object-Oriented Paradigm) of programming. 
For this project, I decided to make a simple program that simulates road traffic. The project was developed in seven sprints which are labelled here as  
`P(sprint number)`. 

The program works by interpreting traffic files and running the traffic simulation with the gathered data. Traffic files must follow a specific format in order to be 
correctly interpreted. Here is the format: 

- The first line of the file is the header line, written in the following order: 
  `Weather,Speed Limit`
- The following lines contain the Car and Driver data. They are written like this: 
  `CarType,Distance,Lane Number,DriverType`

Valid weather types are: `rainy, sunny, overcast, and storming`.  
The weather modifies the Drivers' visibility attribute, which controls the distance ahead that a Driver can detect other Cars.

The program contains three different types of Cars and Drivers: `Slow, Average, and Fast`.  
Car objects need Driver objects, and all Driver types can "drive" all Car types. 
The program uses the Driver-Car pairs to determine the Car's behavior, taking the minimum attributes of the Driver and Car into account.  
*For example, a FastCar being driven by a SlowDriver will not be as fast as a FastCar driven by a FastDriver*.

That should be everything needed to understand the project. If you need more help, read the `.docx` files under P7 for further documentation. 
