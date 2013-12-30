import java.util.*;
import java.io.*;

/* Course.java creates an academic course and provides getters/setters for course info. A Course object contains the 
 * information about a Course that is used in both the search, recommendation, and timetable view, like its 
 * name, instructor, and meeting time. Each course has a category that is used as its key in the hashtable for
 * CourseCollection, which allows the recommender to access the courses that correspond with a leaf of the decision 
 * trees. Claire created the basis of this class, but Sara did the bulk of its implementation. 
 * @author Claire Schlenker
 * @author Sara Burns 
 */


public class Course implements Comparable<Course> {
  
  /*******************************************
    * instance variables for Course class
    *****************************************/
  private String name;
  private int category;
  private String title;
  private String description;
  private String prereqs; 
  private LinkedList<String> distribution;
  private String instructor;
  private double start;
  private double end;
  private double credit;
  private String department;
  private String notes;
  private boolean lab; //does this course require a lab or discussion section?
  private boolean[] days = new boolean[5];//changed to 5 - only classes on weekdays
  private String dayString;
  
  /*******************************************
    * constructor for an empty course, instantiates instance variables as empty
    *****************************************/
  public Course() {
    name = title = description = prereqs = instructor = department = notes = dayString = "";
    category = 0;
    lab = false;
    distribution = new LinkedList<String>();
    start = end = credit = 0.0;
  }
  
  /*******************************************
    * second constructor
    * Constructs a course from a text file, reads relevant lines of course browser layout
    *@param scan scanner object used to read text file, created in CourseCollection constructor
    ******************************************/
  public Course(Scanner scan) {
    int count = 0;
//    System.out.println("Beginning scan");
    try{
    if(count < 1 && scan.hasNextLine()) {
      while(!scan.hasNext("Category"))
        scan.nextLine();
      if(scan.hasNextLine()){
        scan.nextLine();
      category = scan.nextInt();
        //System.out.println("Category: " + category);
      while(!scan.hasNext("Course")){
          scan.nextLine();
        }
        scan.nextLine();
        scan.nextLine();
        name = scan.nextLine();
        setDept(name);
        //System.out.println("Name: " + name);
       // System.out.println("Department: " + department);
        
        while(!scan.hasNext("Title")){
          scan.nextLine();
        }
        scan.nextLine();
        title = scan.nextLine();
        if(title.toLowerCase().contains("lab") || title.toLowerCase().contains("disc"))//most courses indicate in title
          //if they have a lab or discussion, but this is not a fully developed feature
          lab = true;
        
        //System.out.println("Title: " + title);
        
        while(!scan.hasNext("Credit")){
          scan.nextLine();
        }
        scan.nextLine();
        credit = scan.nextDouble();
        //System.out.println("Credit: " + credit);
        
        do{
          scan.nextLine();
        } while(!scan.hasNext("Description"));
        scan.nextLine();
        description = scan.nextLine();
        //System.out.println("Description: " + descr);
        while(!scan.hasNext("Prerequisite\\(s\\)"))
          scan.nextLine();
        scan.nextLine();
        prereqs = scan.nextLine();
        //System.out.println("Prerequisites: " + prereqs);
         while(!scan.hasNext("Distributions")){
          scan.nextLine();
        }
        scan.nextLine();
        distribution = new LinkedList<String>();
        int i = 0;
        while(!scan.hasNext("Instructor\\(s\\)") && i < 5){
          if(scan.hasNext("Notes")){
            scan.nextLine();
            notes = scan.nextLine();
          }else if(scan.hasNext("Crosslisted")){
            scan.nextLine();
            scan.nextLine();
          }else{//adds distribution requirements to a linked list until it reaches "Crosslisted Courses" or 
            //"Instructor(s")
                distribution.add(scan.nextLine());
                //System.out.println("Dist: " + distribution[i]);
                i++;
        }
        }
        scan.nextLine();
        instructor = scan.nextLine();
        //System.out.println("Instructor: " + instructor);
        while(!scan.hasNext("Meeting")){
          scan.nextLine();
        }
        scan.nextLine();
        dayString = scan.next();//the days it meets
        parseDays(dayString);//convert days to boolean array
        scan.next();
        String[] time = scan.next().split(":");
        start = Double.parseDouble(time[0]) + (Double.parseDouble(time[1]) * .01);
        //System.out.println("Start time: " + start);
        scan.next();
        scan.next();
        time = scan.next().split(":");
        end = Double.parseDouble(time[0]) + (Double.parseDouble(time[1]) * .01);
        //System.out.println("End time: " + end);
        count++;
  
      }
    }
    }catch(Exception e) {
      System.out.println("Exception in Course constructor: " + e + "Course name: " + getName());
    }
  }
  
 /*******************************************
  * sets the boolean array days according to a given string
  * @param s String to be interpreted in days array
  *****************************************/
  private void parseDays(String s) {
   String temp = s;
   //System.out.println("Days: " + s);
   if(temp.contains("M"))
     days[0] = true;
   if(temp.contains("Th")){
     days[3] = true;
     temp = temp.replace("Th", "");
     //System.out.println(temp);
   }
   if(temp.contains("T"))
     days[1] = true;
   if(temp.contains("W"))
     days[2] = true;
   if(temp.contains("F"))
     days[4] = true;
     
   }
       
  
  /*******************************************
    * getter for course name
    * @return the course name ie CS 230-01
    *****************************************/
  public String getName() {
    return name; 
  }
  
  /*******************************************
    * getter for course title
    * @return the course title ie Data Structures
    *****************************************/
  public String getTitle() {
    return title; 
  }
  
  /*******************************************
    * getter for course department
    * @return the department ie GEOS
    *****************************************/
  public String getDept() {
    return department; 
  }
  
  /*******************************************
    * getter for course description
    * @return the course's description, generally a long paragraph
    *****************************************/
  public String getDescription() {
    return description; 
  }
  
  /*******************************************
    * getter for course prereqs
    * @return the Prerequisites of the Course
    *****************************************/
  public String getPrereqs() {
    return prereqs; 
  }
  /*******************************************
  * getter for course category, determines place in hashtable
  * @return course category
  *****************************************/
  public int getCategory() {
    return category; 
  }
  /*******************************************
    * getter for course instructor
    * @return instructor(s)
    *****************************************/
  public String getInstructor() {
    return instructor; 
  }
  
  /*******************************************
    * getter for course distribution categories
    * @return all of the distribution requirements in a LinkedList<String>
    *****************************************/
  public LinkedList<String> getDistribution() {
    return distribution; 
  }
  
  /*******************************************
    * getter for course start time
    * @return a double representing the time the course starts
    *****************************************/
  public double getStart() {
    return start; 
  }
  
  /*******************************************
    * getter for course end time
    * @return a double representing the time the course ends
    *****************************************/
  public double getEnd() {
    return end; 
  }
  
  /*******************************************
    * getter for course credit amount
    * @return the amount of credit given for a certain course
    *****************************************/
  public double getCredit() {
    return credit; 
  }
  
  /*******************************************
    * getter for course days
    * @return a boolean array representing which days the course meets, days[0] = monday, etc.
    *****************************************/
  public boolean[] getDays() {
    return days; 
  }
  
  /*******************************************
    * getter for lab requirement
    * @return true if the course has a lab associated with it
    *****************************************/
  public boolean getLab() {
    return lab; 
  }
  
  /*******************************************
    * setter for course name
    * @param n the desired name
    *****************************************/
  public void setName(String n) {
    this.name = n; 
  }
  
  /*******************************************
    * setter for course title
    * @param t the desired title
    *****************************************/
  public void setTitle(String t) {
    this.title = t; 
  }
  
  /*******************************************
    * setter for course description
    * @param d the desired description
    *****************************************/
  public void setDescription(String d) {
    this.description = d; 
  }
  
  /*******************************************
    * setter for course prereqs
    * @param p the desired prerequisites
    *****************************************/
  public void setPrereqs(String p) {
    this.prereqs = p; 
  }
  
  /*******************************************
    * setter for course instructor
    * @param i the desired instructor
    *****************************************/
  public void setInstructor(String i) {
    this.instructor = i; 
  }
  
  
  /*******************************************
    * setter for course distribution categories
    * @param d a linked list of desired distribution requirement(strings)
    *****************************************/
  public void setDistribution(LinkedList<String> d) {
    this.distribution = d; 
  }
  
  /*******************************************
    * setter for course start time
    * @param s the time the course begins as a double
    *****************************************/
  public void setStart(double s) {
    this.start = s; 
  }
  
  /*******************************************
    * setter for course end time
    * @param e the time course ends as a double 
    *****************************************/
  public void setEnd(double e) {
    this.end = e; 
  }
  
  /*******************************************
    * setter for course credit amount
    * @param c the amount of credit the course is worth 
    *****************************************/
  public void setCredit(double c) {
    this.credit = c; 
  }
  
  /*******************************************
    * setter for course days
    * @param d a boolean array representing the days the course meets, ie d[0] = monday, etc.
    *****************************************/
  public void setDays(boolean[] d) {
    this.days = d; 
  }
  
    /*******************************************
    * setter for department, uses first part of course name
    * @param name the name of course, which is parsed to determine dept
    *****************************************/
  public void setDept(String name) {
    String[] parse = name.split(" ");
    department = parse[0];
  }
  
  /*******************************************
    * returns a String representation of this Course object
    * @return String representation
    ******************************************/
  public String fullString() {
    String s = "<html><h3><p style='width: 200px;height: 200px'>" + name + ": " + title + "</h3>" +
      "<br />" + dayString + " " + start + "-" + end + "<br />Instructors: " + instructor + "<br />Description: " +
      description + "<br />Dist: ";
    for(int i = 0; i < distribution.size() && distribution.get(i) != null; i++)
      s += distribution.get(i) + ", ";
    s += "<br />Prerequisites: " + prereqs + "<br />Notes: " + ((notes == null) ? "None" : notes) + "<br />Lab?: " + lab + "</p></html>";
    return s;
  }
  
   /*******************************************
    * returns a String representation of this Course object suitable for the search results
    * @return Search result String representation
    ******************************************/
  public String toString() {
    String s = name + "  " + title + "  " + "Instructor(s): " + instructor + " Notes: ";
    s += (notes == null) ? "None" : notes;
    return s;
  }
  /*******************************************
    * overrides abstract compareTo method from Comparable interface
    * returns a positive level if this is greater than c 
    * returns a 0 if the two courses are equal
    * returns a negative level if this is less than c
    * @param c the course to compare this course to
    * @return the appropriate integer depending on the comparison
    *****************************************/
  public int compareTo(Course c) {
    return this.name.compareTo(c.name);
  }
  
  
  /*******************************************
    * Returns whether this Course is equal to another Course, compares Title
    * @param c course for comparison
    * @return true if courses are equal, false if not
    *****************************************/
  public boolean equals(Course c){
    return title.equals(c.getTitle());
  }
  
  /*******************************************
    * Returns whether this course conflicts with the given course c
    * @param c course for comparison
    * @return true if courses conflict, false if not
    *****************************************/
  public boolean conflicts(Course c) {
    //System.out.println("COURSE CLASS CONFLICTS METHOD: \nCHECKING " + c + "\nAGAINST\n" + this);
    for(int i = 0; i < days.length; i++){
      //System.out.println("CHECKING COURSE DAY: " + i + " FOR CONFLICTS");
      if(days[i] && (c.getDays()[i])) { 
        //System.out.println("MEETS ON SAME DAY");
        if(c.getStart() >= start && c.getStart() <= end) {
          //System.out.println("START TIME OF COURSE TO CHECK IS WITHIN TIME SLOT");
          return true;
        }
        if(c.getEnd() >= start && c.getEnd() <= end) {
          //System.out.println("END TIME OF COURSE TO CHECK IS WITHIN TIME SLOT");
          return true;
        }
        if(start >= c.getStart() && start <= c.getEnd()) {
          //System.out.println("START TIME OF SCHEDULE COURSE IS WITHIN TIME SLOT OF CONFLICTING");
          return true;
        }
        if(end >= c.getStart() && end <= c.getEnd()) {
          //System.out.println("END TIME OF SCHEDULE COURSE IS WITHIN TIME SLOT OF CONFLICTING");
          return true;
        }
      }
    }
    return false;
  }
  
  
 /*Testing Main 
  */ 
  public static void main(String[] args) { //for testing purposes
    try{
      Scanner scan = new Scanner(new File("AllCourses.txt"));

      Course anth209 = new Course(scan);
      
      System.out.println(anth209.toString());
//      Course afr2 = new Course(scan);
//      System.out.println(afr2);
      scan.close();
      for(boolean day: anth209.getDays()){
        System.out.println(day);
      }
    }catch(Exception e){
      System.out.println(e);
    }
    
    
  } //end main
  
}//end Course