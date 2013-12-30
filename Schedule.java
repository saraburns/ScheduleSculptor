 /*******************************************
 * Schedule.java stores the classes that a user has added to their schedule in a LinkedList<Course>
 * -allows user to add and remove courses
 * -checks for conflicts with existing courses
 * This course was primarily implemented by Sara.
 * Bugs: No known bugs
 * @author Claire Schlenker
 * @author Sara Burns 
 * *****************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Schedule {
  
  /*******************************************
    * instance variables for Schedule class
    *****************************************/
  private int numCourses;
  private LinkedList<Course> current;
  private double credits;
  
  private final double MAXCRED = 5.75;//maximum credits for a schedule
  
/*******************************************
  * constructor, creates an empty instance of the Schedule class
  *****************************************/
  public Schedule() {
    current = new LinkedList<Course>();
    numCourses = 0;
    credits = 0;
  }
  
  /*******************************************
  * getter method for numCourses
  * @return number of Courses in the user's current schedule
  *****************************************/
  public int getNumCourses() {
    return numCourses;
  }
  
  /*******************************************
  * getter method for LinkedList<Course> current
  * @return number of Courses in the user's current schedule
  *****************************************/
  public LinkedList<Course> getCurrent() {
    return current;
  }
  
  /*******************************************
  * getter method for credits
  * @return number of credits in the user's current schedule
  *****************************************/
  public double getTotalCredits() {
    return credits;
  }
  
  /*******************************************
  * addCourse(Course c) adds a given course to user's schedule, provided it doesn't conflict
  * with any current courses
  * If they have reached their credit limit, it prints out an error message
  * @param c the course to be added to the schedule
  *****************************************/
  public void addCourse(Course c) {
    double courseCred = c.getCredit();
    //System.out.println("COURSE CREDITS: " + courseCred + "\nCREDITS: " + credits);
    if((credits + courseCred) <= MAXCRED && noConflicts(c)){
    current.add(c);
    numCourses++;
    credits += courseCred;
    }else{
      if(!noConflicts(c))
         JOptionPane.showMessageDialog(null,"This course conflicts with your current schedule.");
      else
      JOptionPane.showMessageDialog(null,"Credit limit reached. Please remove a course from your schedule.");
    }
    
  }
  
    /*******************************************
  * noConflicts(Course c) checks if the given course conflicts with any
  * of the present courses in the user's schedule
  * @param c the course to be checked 
  * @return true if there are no conflicts
  *****************************************/
  public boolean noConflicts(Course c){
    for(int i = 0; i < numCourses; i++){
      if(current.get(i).conflicts(c))
        return false;
    }
    return true;
  }
  
  /*******************************************
  * inSchedule(Course c) checks if the given course is already 
  * in the user's schedule
  * @param c the course to be checked 
  * @return true if the course is already in the schedule
  *****************************************/
  public boolean inSchedule(Course c) {
    return current.contains(c);
  }
  
  /*******************************************
  * removeCourse(Course c) removes a course from the user's schedule
  * @param c the course to be checked 
  * @return true if the course is already in the schedule
  *****************************************/
  public void removeCourse(Course c) {
    if(inSchedule(c)){
      current.remove(c);
      numCourses--;
      credits -= c.getCredit();
    }
  }
  
  /*******************************************
  * Used to create the drop down menu including course names from the user's schedule
  * @return is the Vector of names of the courses in the user's schedule
  *****************************************/
  public Vector<String> courseNames() {
    Vector<String> result = new Vector<String>();
    result.add("...");
    for(Course c: current)
      result.add(c.getName());
    return result;
  }

     /*******************************************
  * finds a course within the user's schedule
  * @param name the name of the course that is to be searched
  * @return Course which has given name
  *****************************************/
  public Course findCourse(String name){
    Course c = new Course();
    for(int i = 0; i < current.size(); i++){
      if(current.get(i).getName().equals(name))
        return current.get(i);
    }
    return c;
  }
   /*******************************************
  * returns a String representation of the Schedule object
  * @return consolidated String representation of all courses in Schedule
  *****************************************/
  public String toString() {
    String s = "";
    for(Course c: current)
      s += c.toString() + "\n";
    return s;
  }
 
  //testing main
  public static void main(String[] args){
    Schedule s = new Schedule();
    
  }
}
 
