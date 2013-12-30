
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.util.*;
/*The AddCoursePanel class creates a panel which allows the user to add courses to their current schedule.
 * The user may browse available courses, refining by department, distribution requirement, and meeting time.
 * Courses which match the given criteria are displayed below, and may be selected and added to the student schedule.
 * Sara created this class.
 * @author Sara Burns
 * @author Claire Schlenker */


public class AddCoursePanel extends JPanel{
  //instance variables
  //north panel
  //label containing instructions on the AddCoursePanel
  private JLabel instructions;
  
   //center panel
  //labels for each of the components
  private JLabel deptlabel, distrlabel, timelabel;
  //combo boxes for each of search criteria
  private JComboBox dept, distr, time;
  //instance variables which store selected department, distr, time;
  private String d, di, t;
  //button to search for a course
  private JButton findcourses;
  
  //south panel
  private JLabel coursesfound;
  private JScrollPane scroll;
  private JList found;
  private JButton addSched;
  
  //Schedule object & CourseCollection object, ViewPanel object
  private Schedule sched;
  private CourseCollection courses;
  private ViewPanel vp;
  
  private final Color BACKGROUND = Color.WHITE;
  
  /*Constructor method. Assigns the instance variables which connect this panel to the rest
   * of the program(ie the Schedule class, the CourseCollection, and the ViewPanel)and adds the
   * other panels to a GridLayout.
   * @param sched the User's current schedule
   * @param courses the CourseCollection built from a text file of course information
   * @param vp the ViewPanel, which is used to update the timetable with added courses
   */
  public AddCoursePanel(Schedule sched, CourseCollection courses, ViewPanel vp) {
    
    this.sched = sched;
    this.courses = courses;
    this.vp = vp;
    setLayout(new GridLayout(3,1,20,20));
    setBackground(BACKGROUND);
    setPreferredSize(new Dimension(1000,400));
    
    //add the three panels
    add(makeInstructionsPanel());
    add(makeCenterPanel());
    add(makeSouthPanel());
  }
  
  /*
   * Returns the Instructions Panel, which simply contains the title and instructions on how to use the Panel
   * @return the Instructions Panel 
   *
   */
  private JPanel makeInstructionsPanel() {
    //creates the panel and sets the background color
    JPanel instructionsPanel = new JPanel();
    instructionsPanel.setBackground(new Color(153,204,255));
    //creates and adds instructions label
    JLabel title = new JLabel("<html><h1><center>Schedule Sculptor</center></h1></html>");
    JPanel titleFiller = new JPanel();
    titleFiller.setPreferredSize(new Dimension(900,10));
    titleFiller.setOpaque(false);
    instructions = new JLabel("<html><h3><center>Refine your search using the criteria below, then click \"Find Courses\"</center></h3></html>");
    instructionsPanel.add(title);
    instructionsPanel.add(titleFiller);
    instructionsPanel.add(instructions);
    
    return instructionsPanel;
  }
  
    /*
   * Makes the center panel, contains the components used to search for Courses
   * @return the center panel
   */
  private JPanel makeCenterPanel() {
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(BACKGROUND);
    
    
    
   //create each criteria and its combo box
    deptlabel = new JLabel("Dept: ");
    dept = new JComboBox(courses.allDepts());
    centerPanel.add(deptlabel);
    centerPanel.add(dept);
  
    
    distrlabel = new JLabel("Distribution: ");
    distr = new JComboBox(new String[] {"...","Language and Literature", "Arts, Music, Theatre, Film and Video"
      , "Social and Behavioral Analysis", "Epistemology and Cognition", "Religion, Ethics and Moral Philosophy", 
      "Historical Studies", "Natural and Physical Science", "Mathematical Modeling"});
    centerPanel.add(distrlabel);
    centerPanel.add(distr);
    
    timelabel = new JLabel("Time: ");
    time = new JComboBox(new String[] {"...", "8:30-9:40", "9:50-11:00", "11:10-12:20", "1:30-2:40", "2:50-4:00"});
    centerPanel.add(timelabel);
    centerPanel.add(time);
    
    //create and add the addschools button
    findcourses = new JButton("Find Courses");
    findcourses.addActionListener(new ButtonListener());
    centerPanel.add(findcourses);
    
    
    return centerPanel;
  }
  
  
 /*Returns the south panel, which contains the jlist with search results and a button to add that course to the user's
  * schedule
  * @return the south panel
  */ 
  public JPanel makeSouthPanel() {
    JPanel southPanel = new JPanel();
    southPanel.setBackground(new Color(51,102,255));
    
    coursesfound = new JLabel("Courses found: ");
    southPanel.add(coursesfound);
    
    found = new JList();
    found.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
    found.addListSelectionListener(new CourseSelectListener());
    scroll = new JScrollPane(found,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setPreferredSize(new Dimension(600,150));
    southPanel.add(scroll);
    
    addSched = new JButton("Add to Schedule");
    addSched.addActionListener(new ButtonListener());
    southPanel.add(addSched);
    
    return southPanel;
  }
  
  /*Helper method which translates the options of the time ComboBox to search terms, allows the user to refine their
   * results by time.
   * @param results the list to assign the results of the time Search to, to be used as the content of the JList
   * @return the results to be returned in the JList
   */ 
  private Vector<Course> timeTranslate(Vector<Course> results) {
      String[] hour = time.getSelectedItem().toString().split(":");
      Integer selected = Integer.parseInt(hour[0]);
      switch(selected){
        case 8:
          results = courses.coursesAtTime(8.3, 9.4);
          break;
        case 9:
          results = courses.coursesAtTime(9.5, 11.0);
          break;
        case 11:
          results = courses.coursesAtTime(11.1, 12.2);
          break;
        case 1:
          results = courses.coursesAtTime(1.3, 2.4);
          break;
        case 2:
          results = courses.coursesAtTime(2.5, 4.0);
          break;
      }
      return results;
  }
   /*Helper method which translates the options of the time ComboBox to search terms. Rather than returning all the 
    * courses which meet at that time, this method returns only the courses that meet at that time and meet the user's
    * other search criteria as well. 
   * @param results the list to assign the results of the time Search to, to be used as the content of the JList
   * @return the results to be returned in the JList
   */ 
  private Vector<Course> timeRefine(Vector<Course> results) {
    String[] hour = time.getSelectedItem().toString().split(":");
      Integer selected = Integer.parseInt(hour[0]);
      switch(selected){
        case 8:
          results = courses.refineByTime(8.3, 9.4, results);
          break;
        case 9:
          results = courses.refineByTime(9.5, 11.0, results);
          break;
        case 11:
          results = courses.refineByTime(11.1, 12.2, results);
          break;
        case 1:
          results = courses.refineByTime(1.3, 2.4, results);
          break;
        case 2:
          results = courses.refineByTime(2.5, 4.0, results);
          break;
      }
      
    return results;
  }
   
/*ButtonListener is used for all the buttons in the Panel. It differentiates the response by determining which 
 * button is the source of the action. If the user presses Find Courses, the listener responds by searching according
 * to the fields the user has selected and returning the search results to the JList "found", which is part of a 
 * JScrollPane. If the user presses Add to Schedule, the course selected, if any, is added to their schedule.
 * @author Sara Burns 
 */ 
private class ButtonListener implements ActionListener {
  /*Responds depending on the button which is pressed. Searches for courses depending on input if the event comes from
   * Find Courses, it searches the course collection and returns relevant courses in a JList. If the event comes from 
   * Add to Schedule, it adds the selected course to the schedule, after checking whether a course is selected.
   * @param event when the user clicks on a button
   */ 
  public void actionPerformed(ActionEvent event) {
    if(event.getSource() == findcourses){
      Vector<Course> results = new Vector<Course>();
      if(!dept.getSelectedItem().equals("...")){//user has selected a department
        results = courses.coursesInDepartment(dept.getSelectedItem().toString());
        if(!distr.getSelectedItem().equals("...")){//use has also selected a distribution
//             System.out.println("Entered Distribution block");
             results = courses.refineByDistribution(distr.getSelectedItem().toString(), results);
        }
        if(!time.getSelectedItem().equals("...")){//user has also selected a time
           results = timeRefine(results);
        }
          }else if(!distr.getSelectedItem().equals("...")){//user did not select a department, but did select a distr
        results = courses.coursesInDistribution(distr.getSelectedItem().toString());
        if(!time.getSelectedItem().equals("..."))//user also selected a time
          results = timeRefine(results);
      }else if(!time.getSelectedItem().equals("...")){//user only selected a time
        results = timeTranslate(results);
    
      }
          
      found.setListData(results);
    }
    if(event.getSource() == addSched){
      Course selected = (Course)found.getSelectedValue();
      if(selected != null){//check to make sure a course is selected
      sched.addCourse(selected);
      vp.updateDisplay();//updates the viewpanel with the new schedule
      found.clearSelection();
      }
    }
      
  }
}
/*CourseSelectListener responds if the user selects a course from the JList. When they select a Course, a dialog box
 * appears containing more information on the Course. They can close this dialog, and then choose to add it to their 
 * schedule by hitting the Add to Schedule button.
 */ 
private class CourseSelectListener implements ListSelectionListener {
  /*This method responds if the user has changed the value of the JList, by selecting a Course. It displays more info
   * in a Combo Box.
   * @param e a selection has been made from the list
   */ 
  public void valueChanged(ListSelectionEvent e){
    try{
      if(found.getSelectedValue() != null){
    Course selected = (Course)found.getSelectedValue();
    JOptionPane.showMessageDialog(null, selected.fullString());
      }
    }catch(NullPointerException exc) {
      System.out.println("Add Course Panel: " + e);
    }
  }
}

  }


