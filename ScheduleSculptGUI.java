/*The ScheduleSculptGUI object sets up the top-level, tabbed pane frame container for the Schedule Sculptor GUI
 * Sara was responsible for this implementation.
 * @author Sara Burns
 * @author Claire Schlenker
 */

import javax.swing.*;
import java.awt.*;


public class ScheduleSculptGUI {
  
  //main method for setting up tabbed pane GUI
  public static void main(String[] args){
  
  //this will be the user's schedule once it is populated with courses
  Schedule sched = new Schedule();
  
  //creates hashtable of all of the courses in the file
  CourseCollection courses = new CourseCollection("AllCourses.txt");
  
  //create new Frame
  JFrame frame = new JFrame("Schedule Sculptor");
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  
  //create Tabbed Pane and add tabs
  JTabbedPane tp = new JTabbedPane();
  ViewPanel vp = new ViewPanel(sched);
  tp.addTab("Add Course", new AddCoursePanel(sched, courses,vp));
  tp.addTab("Course Suggestions", new SuggestPanel(sched, courses, vp));
  tp.addTab("View Schedule", vp);
  
  frame.getContentPane().add(tp);
  
  frame.setSize(1000,600);
  frame.setVisible(true);
}
}