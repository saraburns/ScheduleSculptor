
import java.io.*;
import java.util.*;
/*Creates a collection of courses from a text file using a Scanner. These courses are contained
 * in linked lists within a hashtable, where the key is an integer. Integers, which are the categories
 * of the courses, obtained from the text file, map to linked lists of courses which share that category.
 * From this point, the collection can be searched various ways, and is used in the Add Course Panel and the
 * Suggest Panel. This course was partially implemented by each team member. Claire started the implementation, and
 * Sara added the refining search methods and completed the constructor.
 * 
 * Bugs: Creates an extra empty Course, appears to be an issue with the Scanner
 * @author Sara Burns
 * @author Claire Schlenker
 */


public class CourseCollection {
  
  //instance variables
  private Hashtable<Integer, LinkedList<Course>> collection;
  private int number;//number of Courses
  private int numCat;//number of categories
  private final int NUM_CATEGORIES = 24;
  
  
  /***************************************************************
    * empty constructor
    *************************************************************/
  public CourseCollection() {
    this.collection = new Hashtable<Integer, LinkedList<Course>>();
    this.number = 0;
    this.numCat = 0;
  }
  
  /***************************************************************
    * Constructor which reads in from a text file. Organizes courses in the
    * appropriate linked list in the hashtable based on their category.
    * @param filename the file scanned for course information
    *************************************************************/
  public CourseCollection(String filename) {
    this();
    try {
      Scanner reader = new Scanner(new File(filename));
      while (reader.hasNextLine()) {
        if(reader.hasNextLine()){
        Course c = new Course(reader);//creates a new course
        number++;
        int category = c.getCategory();
        if(collection.containsKey(category))//if its category exists, adds it to that linked list
          collection.get(category).add(c);
        else{//creates a new category and linked list
          LinkedList<Course> newcat = new LinkedList<Course>();
          newcat.add(c);
          collection.put(category, newcat);
          numCat++;
        }
      }
      }   
    } catch (IOException exception) {
      System.out.println("****ERROR****: invalid file: " + exception);
    }
  }
  
  /************************************************************
    * returns a String representation of all the courses in a given category
    * @param category the category to represent
    * @return a String containing info from each course in the category
    ***********************************************************/ 
  public String categoryString(Integer category) {
    String s = "";
    for(Course c: collection.get(category))
      s += c.toString() + "\n";
    return s;
  }
  
  
  /************************************************************
    * returns a Vector of all current departments in the course collection
    * @param dist is the String[] of distributions
    * @return a Vector<String> of all the departments, to be passed into the dept ComboBox in the Add Course Panel
    ***********************************************************/
  public Vector<String> allDepts() {
    Vector<String> depts = new Vector<String>();
    depts.add("...");
    for (int i = 0; i < numCat; i++) {
      LinkedList<Course> cat = collection.get(i);
      for (int j = 0; j < cat.size(); j++) {
        String current = cat.get(j).getDept();
        if (!depts.contains(current) && current != null)
          depts.add(current);
      }
    }
    return depts;
  }
  
  
  /************************************************************
    * returns a Vector of Courses which belong in a certain
    * distribution requirement
    * if no courses fit into that distribution requirement, returns
    * an empty Vector. 
    * @param dist is the String[] of distributions
    * @return a Vector<Course> of all the courses in the given distribution, to be passed into the JList of results
    * in the Add Course Panel 
    ***********************************************************/
  public Vector<Course> coursesInDistribution(String dist) {
    Vector<Course> result = new Vector<Course>();
    for (int i = 0; i < numCat; i++) {
      LinkedList<Course> cat = collection.get(i);
      for (int j = 0; j < cat.size(); j++) {
        if (cat.get(j).getDistribution() != null && ((cat.get(j)).getDistribution().contains(dist)) && !result.contains(cat.get(j)))
          result.add(cat.get(j));
      }
    }
    return result;
  }
  
  
  /************************************************************
    * returns a Vector of Courses which belong in a certain
    * department
    * if no courses fit into that department, returns an empty 
    * Vector. 
    * @param dept is the String representation of the
    * name of a department
    * @return a Vector<Course> containing all of the courses with the given department
    ***********************************************************/
  public Vector<Course> coursesInDepartment(String dept) {
    Vector<Course> result = new Vector<Course>();
    for (int i = 0; i < numCat; i++) {
      LinkedList<Course> cat = collection.get(i);
      for (int j = 0; j < cat.size(); j++) {
        if (cat.get(j).getDept() != null && ((cat.get(j)).getDept()).equals(dept) && !result.contains(cat.get(j)))
          result.add(cat.get(j));
      }
    }
    return result;
  }
  
   /************************************************************
    * refines a given Vector<Course> by a given Distribution
    * if no courses fit into that distribution, returns an empty 
    * Vector. 
    * @param distribution the distribution to refine the results by
    * @param resultsSoFar the first step of the search
    * @return a Vector<Course> of the courses which, in addition to fitting previous criteria, also fit into
    * given distribution
    ***********************************************************/
  public Vector<Course> refineByDistribution(String distribution, Vector<Course> resultsSoFar) {
//    System.out.println("Entered Distribution method. Distribution: " + distribution);
    Vector<Course> results = new Vector<Course>();
    for(int i = 0; i < resultsSoFar.size(); i++){
//      System.out.println("Course Distributions " + resultsSoFar.get(i).getDistribution());
      if((resultsSoFar.get(i).getDistribution()).contains(distribution))
        results.add(resultsSoFar.get(i));
  }
    return results;
  }
  
     /************************************************************
    * refines a given Vector<Course> by a given Time
    * if no courses fit into that time, returns an empty 
    * Vector. 
    * @param start the start time of the desired search
    * @param end   the end time of the desired search
    * @param resultsSoFar the first step of the search
    * @return a Vector<Course> of the courses which, in addition to fitting previous criteria, also fit into
    * given time
    ***********************************************************/
  public Vector<Course> refineByTime(double start, double end, Vector<Course> resultsSoFar) {
    Vector<Course> results = new Vector<Course>();
    for(int i = 0; i < resultsSoFar.size(); i++){
//      System.out.println("Course Time " + resultsSoFar.get(i).getStart() + "-" + resultsSoFar.get(i).getEnd());
      if(resultsSoFar.get(i).getStart() == start && resultsSoFar.get(i).getEnd() == end)
        results.add(resultsSoFar.get(i));
  }
    return results;
  }
  
  /************************************************************
   * returns a Vector of Courses which belong in a certain
   * department
   * if no courses fit into that department, returns an empty 
   * Vector. 
   * @param dept is the String representation of the
   * name of a department
   * @return a Vector<Course> of all the courses which meet at the specified time
    ***********************************************************/
  public Vector<Course> coursesAtTime(double start, double end) {
    Vector<Course> result = new Vector<Course>();
    for (int i = 0; i < numCat; i++) {
      LinkedList<Course> cat = collection.get(i);
      for (int j = 0; j < cat.size(); j++) {
        if (cat.get(j).getDept() != null && ((cat.get(j)).getStart()) == start && 
            ((cat.get(j)).getEnd()) == end && !result.contains(cat.get(j)))
          result.add(cat.get(j));
      }
    }
    return result;
  }
  /************************************************************
    * returns a randomly chosen Course from the possible Course
    * options, that doesn't conflict with the user's schedule
    * @param sched The user's current schedule, to check for conflicts
    * @return a random course that does not conflict with user's schedule
    ***********************************************************/
  public Course getRandomCourse(Schedule sched) {
    LinkedList<Integer> checked = new LinkedList<Integer>();
    Random generator = new Random();
    while (checked.size() < NUM_CATEGORIES) {
      int num = getUnusedCategoryNumber(checked);
      checked.add(num);
      LinkedList<Course> courseList = collection.get(num);
      Course result = getNonconflicting(courseList, sched);
      if (result != null) { 
        //System.out.println("FOUND A NONCONFLICTING COURSE: " + result);
        return result;
      }
    }
    return null;
  }
  
 /*Helper method used in getRandomCourse() to ensure that the method does not enter an infinite loop
  * @param checked a list of the categories that have already been checked for a random, non-conflicting course
  * @return the index of a random category that has not already been checked
  */ 
  private int getUnusedCategoryNumber (LinkedList<Integer> checked) {
    Random generator = new Random();
    //get a new random number that is a possible category
    int num = (generator.nextInt(NUM_CATEGORIES-1)+1);
    //if that category has already been checked
    while (checked.contains(num)) {
      num = (generator.nextInt(NUM_CATEGORIES-1)+1);
    }
    return num;
  }
  /*Helper method used in getRandomCourse. Returns a course from the given category that does not conflict with 
   * the given Schedule.
   * @param c the given category for the method to search
   * @param sched the user's schedule, for checking for conflicts
   * @return a course from the category that does not conflict
   */ 
  private Course getNonconflicting(LinkedList<Course> c, Schedule sched) {
    Random generator = new Random();
    //go through the whole LinkedList to get a course which doesn't conflict
    while (c.size() > 0) { //while more courses to check
      int num = (generator.nextInt(c.size()));
      //if the random course within the linkedlist does not conflict
      //with the user's schedule
      if (sched.noConflicts(c.get(num))) {
        return c.get(num);
      } else {
        c.remove(c.get(num));
      }
    }
    return null;
  }
  /*gets the specified category from the course Collection, removing any courses that conflict 
   * with the user's schedule
   * @param cat the given category
   * @param s the user's schedule, to check for conflicts
   * @return the courses from the category which do not conflict
   */ 
  public LinkedList<Course> getCategory(int cat, Schedule s) {
    LinkedList<Course> result = collection.get(cat);
    for (int i = 0; i < result.size();) {
      if (!s.noConflicts(result.get(i))) {
        result.remove(i);
      } else {
        i++;
      }
    }
    return result; 
  }
  
    /************************************************************
    * getter method, returns the Hashtable collection
    * @return collection, the Hashtable instance variable
    ***********************************************************/
  public Hashtable<Integer, LinkedList<Course>> getCollection() {
    return collection;
  }
  
  /*main method for testing purposes
   */
  public static void main(String[] args) {
    CourseCollection courses = new CourseCollection("AllCourses.txt");
    System.out.println(courses.categoryString(1));
    Vector<Course> dept = courses.coursesInDepartment("CHIN");
    for(Course c: dept)
      System.out.println(c.toString());
    Vector<Course> distr = courses.coursesInDistribution("Language and Literature");
    for(Course c: distr)
      System.out.println(c.toString());
   
    
    
  }
  
} //end CourseCollection