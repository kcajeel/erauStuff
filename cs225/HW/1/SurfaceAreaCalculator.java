/**
Name: SurfaceAreaCalculator
Author: Jack Lee
Purpose: Calculates surface area in square centimeters and square inches. 
Last Modified: 9/1/2022
Attributes: 
	-length:double
	-width:double
	-height:double
Methods: 
	+calculateSurfaceArea(double l, double w, double h):double - returns surface area in square inches given length, width, and height
	+calculateSurfaceAreaSquareCentimeters(double l, double w, double h):double - returns surface area in square centimeters given length, width, and height
	+Setters and Getters
**/
import java.util.Scanner;

public class SurfaceAreaCalculator {
	private double length, width, height;
	
	public static void main(String args[]){
		Scanner scanny = new Scanner(System.in);
		SurfaceAreaCalculator me = new SurfaceAreaCalculator();
		System.out.println("Welcome to the Surface Area Calculator. \nPlease input the length of your object in inches. ");
		me.setLength(scanny.nextDouble());
		System.out.println("The length is "+me.getLength()+". \nNow please enter the width of the object in inches. ");
		me.setWidth(scanny.nextDouble());
		System.out.println("The width is "+me.getWidth()+". \nNow please enter the height of the object in inches. ");
		me.setHeight(scanny.nextDouble());
		System.out.println("The surface area of your shape is "+me.calculateSurfaceArea(me.getLength(),me.getWidth(),me.getHeight())+" square inches, which is equal to "
		+me.calculateSurfaceAreaSquareCentimeters(me.getLength(),me.getWidth(),me.getHeight())+" square centimeters. \n");
		System.out.println("\nTest Case 1: length, width, and height are all 0. Expected output: 0.0 square inches and 0.0 square centimeters. \nResult: "+
		me.calculateSurfaceArea(0,0,0)+" square inches and "+me.calculateSurfaceAreaSquareCentimeters(0,0,0)+" square centimeters. ");
		System.out.println("\nTest Case 2: length is 15, width is 0, and height is 10. Expected output: 300.0 square inches and 1935.48 square centimeters. \nResult: "+
		me.calculateSurfaceArea(15,0,10)+" square inches and "+me.calculateSurfaceAreaSquareCentimeters(15,0,10)+" square centimeters. ");
		System.out.println("\nTest Case 3: length is 10, width is 1, and height is 1. Expected output: 42.0 square inches and 270.97 square centimeters. \nResult: "+
		me.calculateSurfaceArea(10,1,1)+" square inches and "+me.calculateSurfaceAreaSquareCentimeters(10,1,1)+" square centimeters. ");
	}
	
	public double calculateSurfaceArea (double l, double w, double h){
		double surfaceArea = 2*(l*h + w*h + l*w);
		return surfaceArea;
	}
	public double calculateSurfaceAreaSquareCentimeters(double l, double w, double h){
		double surfaceAreaSquareCentimeters = Math.pow(2.54,2)*2*(l*h + w*h + l*w);
		return surfaceAreaSquareCentimeters;
	}
	
	//setters and getters (It's OOP now)
	public void setLength(double l){
		length = l;
	}
	public void setWidth(double w){
		width = w;
	}
	public void setHeight(double h){
		height = h;
	}
	
	public double getLength(){
		return length;
	}
	public double getWidth(){
		return width;
	}
	public double getHeight(){
		return height;
	}
}