/*The ViewPanel class creates a panel containing a timetable layout of the user's current schedule. Courses
 * may be selected from a list, which will show more information about the course.
 * Claire was primarily responsible for this implementation.
 * @author Sara Burns and Claire Schlenker */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ViewPanel extends JPanel{
  
  //instance variables
  private JLabel current, infoInstructions;
  private final Dimension ONE_BLOCK = new Dimension(80,40);
  private final Dimension TWO_BLOCK = new Dimension(80,80);
  private final Dimension THREE_BLOCK = new Dimension(80,120);
  private final Dimension FOUR_BLOCK = new Dimension(80, 160);
  private final Color COURSE_BACKGROUND = new Color(51,102,255);
  private Schedule sched; 
  private JPanel calendar;
  private JComboBox list;
  private JLayeredPane pane;
  
  //constructor, takes an instance of the schedule class
  public ViewPanel(Schedule s) {
    
    this.setLayout(new BorderLayout());
    //creates a panel to center all of the elements
    JPanel elements = new JPanel();
    elements.setBackground(Color.WHITE);
    elements.setOpaque(true);
    BoxLayout layout = new BoxLayout(elements, BoxLayout.Y_AXIS);
    
    //initialize instance variables
    sched = s;
    list = new JComboBox();
    list.addActionListener(new ListListener()); //adds listener to list
    this.current = new JLabel("<html><h1>Current Student Schedule</h1></html>");
    this.infoInstructions = new JLabel("<html><h3>Select a Course to View Course Details</h3><html>");
    
    //@see calendar object with panels for courses
    drawGrid();
    updateDisplay();
    
    //The following two panels act as filler 
    //they keep things in the right place and make things look pretty
    JPanel filler1 = new JPanel();
    filler1.setPreferredSize(new Dimension(100,100));
    filler1.setBackground(new Color(0,46,184));
    filler1.setOpaque(true);
    
    JPanel filler2 = new JPanel();
    filler2.setPreferredSize(new Dimension(100,100));
    filler2.setBackground(new Color(0,46,184));
    filler2.setOpaque(true);
    
    //adds everything to the Panel
//    System.out.println("All other elements");
    elements.add(current);
    elements.add(pane);
    elements.add(infoInstructions);
    elements.add(list);
    this.add(elements, BorderLayout.CENTER);
    this.add(filler1, BorderLayout.WEST);
    this.add(filler2, BorderLayout.EAST);
  }
  
  
    /****************************************************************
      * draws the calendar, with its days and times
      * @see the background with labels for days and times
      **************************************************************/
    private void drawGrid(){
    /****************************************************************
      * @calendar holds all of the information contained in a student's schedule
      * it creates a visual representation using a GridBagLayout
      * it is a piece of a layered pane (so as to incorporate the background grid image)
      **************************************************************/
    calendar = new JPanel(new GridBagLayout());
    calendar.setPreferredSize(new Dimension(520,380));
    calendar.setOpaque(false);
    calendar.setBounds(0,0,520,380);
    
    //constraints for elements within calendar
    GridBagConstraints daysConstraints = new GridBagConstraints();
    daysConstraints.gridx = 1;
    daysConstraints.gridy = 0;
    daysConstraints.weightx = 0.5;
    daysConstraints.insets = new Insets(2,2,2,2);
    
    /****************************************************************
      * creates date and time labels for schedule
      * @see labels for hours and days of the week
      **************************************************************/
    JPanel monday = new JPanel();
    monday.setOpaque(false);
    monday.setPreferredSize(new Dimension(85,30));
    JLabel mon = new JLabel("Monday");
    monday.add(mon);
    calendar.add(monday, daysConstraints);
    
    daysConstraints.gridx = 2;
    JPanel tuesday = new JPanel();
    tuesday.setOpaque(false);
    tuesday.setPreferredSize(new Dimension(85,30));
    JLabel tues = new JLabel("Tuesday");
    tuesday.add(tues);
    calendar.add(tuesday, daysConstraints);
    
    daysConstraints.gridx = 3;
    JPanel wednesday = new JPanel();
    wednesday.setOpaque(false);
    wednesday.setPreferredSize(new Dimension(85,30));
    JLabel wed = new JLabel("Wednesday");
    wednesday.add(wed);
    calendar.add(wednesday, daysConstraints);
    
    daysConstraints.gridx = 4;
    JPanel thursday = new JPanel();
    thursday.setOpaque(false);
    thursday.setPreferredSize(new Dimension(85,30));
    JLabel thurs = new JLabel("Thursday");
    thursday.add(thurs);
    calendar.add(thursday, daysConstraints);
    
    daysConstraints.gridx = 5;
    JPanel friday = new JPanel();
    friday.setOpaque(false);
    friday.setPreferredSize(new Dimension(85,30));
    JLabel fri = new JLabel("Friday");
    friday.add(fri);
    calendar.add(friday, daysConstraints);
    
    //constraints for time objects
    GridBagConstraints timeConstraints = new GridBagConstraints();
    timeConstraints.gridx = 0;
    timeConstraints.gridy = 1;
    timeConstraints.weightx = 0.5;
    timeConstraints.anchor = GridBagConstraints.WEST;
    timeConstraints.insets = new Insets(2,2,2,2);
    
    JPanel p8am = new JPanel();
    p8am.setOpaque(false);
    p8am.setPreferredSize(new Dimension(50,30));
    JLabel p8amL = new JLabel("8 a.m.");
    p8am.add(p8amL);
    calendar.add(p8am, timeConstraints);
    
    timeConstraints.gridy = 2;
    JPanel p9am = new JPanel();
    p9am.setOpaque(false);
    p9am.setPreferredSize(new Dimension(50,30));
    JLabel p9amL = new JLabel("9 a.m.");
    p9am.add(p9amL);
    calendar.add(p9am, timeConstraints);
    
    timeConstraints.gridy = 3;
    JPanel p10am = new JPanel();
    p10am.setOpaque(false);
    p10am.setPreferredSize(new Dimension(50,30));
    JLabel p10amL = new JLabel("10 a.m.");
    p10am.add(p10amL);
    calendar.add(p10am, timeConstraints);
    
    timeConstraints.gridy = 4;
    JPanel p11am = new JPanel();
    p11am.setOpaque(false);
    p11am.setPreferredSize(new Dimension(50,30));
    JLabel p11amL = new JLabel("11 a.m.");
    p11am.add(p11amL);
    calendar.add(p11am, timeConstraints);
    
    timeConstraints.gridy = 5;
    JPanel p12pm = new JPanel();
    p12pm.setOpaque(false);
    p12pm.setPreferredSize(new Dimension(50,30));
    JLabel p12pmL = new JLabel("12 p.m.");
    p12pm.add(p12pmL);
    calendar.add(p12pm, timeConstraints);
    
    timeConstraints.gridy = 6;
    JPanel p1pm = new JPanel();
    p1pm.setOpaque(false);
    p1pm.setPreferredSize(new Dimension(50,30));
    JLabel p1pmL = new JLabel("1 p.m.");
    p1pm.add(p1pmL);
    calendar.add(p1pm, timeConstraints);
    
    timeConstraints.gridy = 7;
    JPanel p2pm = new JPanel();
    p2pm.setOpaque(false);
    p2pm.setPreferredSize(new Dimension(50,30));
    JLabel p2pmL = new JLabel("2 p.m.");
    p2pm.add(p2pmL);
    calendar.add(p2pm, timeConstraints);
    
    timeConstraints.gridy = 8;
    JPanel p3pm = new JPanel();
    p3pm.setOpaque(false);
    p3pm.setPreferredSize(new Dimension(50,30));
    JLabel p3pmL = new JLabel("3 p.m.");
    p3pm.add(p3pmL);
    calendar.add(p3pm, timeConstraints);
    
    timeConstraints.gridy = 9;
    JPanel p4pm = new JPanel();
    p4pm.setOpaque(false);
    p4pm.setPreferredSize(new Dimension(50,30));
    JLabel p4pmL = new JLabel("4 p.m.");
    p4pm.add(p4pmL);
    calendar.add(p4pm, timeConstraints);
    
    timeConstraints.gridy = 10;
    JPanel p5pm = new JPanel();
    p5pm.setOpaque(false);
    p5pm.setPreferredSize(new Dimension(50,30));
    JLabel p5pmL = new JLabel("5 p.m.");
    p5pm.add(p5pmL);
    calendar.add(p5pm, timeConstraints);
    
    //this creates the background image for the calendar
    JLabel calendarImage = new JLabel();
    ImageIcon icon = new ImageIcon("cal_background1.jpg");
    calendarImage.setIcon(icon);
    calendarImage.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
    
    //@pane is used so as to layer the calendar (with all of its labels and data
    //overtop of the background grid image
    pane = new JLayeredPane();
    pane.setPreferredSize(new Dimension(520,380));
    //System.out.println("CalendarImage added to JLayeredPane");
    pane.add(calendarImage, new Integer(0));
    //System.out.println("Calendar added to JLayeredPane");
    pane.add(calendar, new Integer(1));
    

  }
    
    
  
  public void updateDisplay() {
   /**********************************************************
      * this section of code creates the calendar panels based
      * upon the courses that have been added to a user's 
      * schedule
      * @see courses added to the schedule 
      *********************************************************/ 
    //constraints for elements within calendar
    GridBagConstraints courseConstraints = new GridBagConstraints();
    courseConstraints.gridx = 1; 
    courseConstraints.gridy = 1; 
    
    //get courses from schedule and create panels for them
    LinkedList<Course> scheds = sched.getCurrent();
    for (int i = 0; i < sched.getNumCourses(); i++) {
      Course current = scheds.get(i);
      //System.out.println("CREATING PANELS: CURRENT COURSE: " + current);
      JPanel c = new JPanel(); 
      c.setOpaque(true);
      c.setBackground(COURSE_BACKGROUND);
      //determines how tall to make panels to accurately represent course
      double timeLength = (current.getEnd() - current.getStart());
      //System.out.println("COURSE TIMELENGTH: " + timeLength);
      if (timeLength < 2) { //if course is less than two hours
        c.setPreferredSize(ONE_BLOCK);
        //System.out.println("LESS THAN TWO HOURS");
        //grid height must be one greater than number of hours so that when 
        //offset is added to course position, course remains within bounds of grid
        courseConstraints.gridheight = 2; 
      } else if (timeLength >= 2 && timeLength < 3) {
        c.setPreferredSize(TWO_BLOCK);
        //System.out.println("2-3 Hours");
        courseConstraints.gridheight = 3;
      } else if (timeLength >= 3 && timeLength < 4) {
        c.setPreferredSize(THREE_BLOCK);
        //System.out.println("3-4 Hours");
        courseConstraints.gridheight = 4;
      } else {
        c.setPreferredSize(FOUR_BLOCK);
        //System.out.println("MORE THAN FOUR HOURS");
        courseConstraints.gridheight = 5;
      }
      //calculates offset so as to position class correctly within calendar
      //@offset represents number of minutes into the hour a course starts
      int offset = (int) Math.round((current.getStart()%1)*50);
      //System.out.println("CURRENT START TIME: " + current.getStart() + "\nOFFSET: " + offset);
      courseConstraints.insets = new Insets(offset,2,2,2);
      int time = (int)(current.getStart())/1;
      //if course is before noon, position course correctly within grid
      if (time > 5) {
        courseConstraints.gridy = (time-7);
      } else { //if course if after noon
        courseConstraints.gridy = (time+5);
      }
      //adds course name to @c (the panel acting as a course)
      JLabel cLabel = new JLabel(current.getName());
      c.add(cLabel);
      calendar.add(c, courseConstraints);
      //clones the appropriate amount of panels and adds them
      //on the correct days
      boolean[] courseDays = current.getDays();
      //@alreadyAdded: if @c has already been added to the grid, must clone
      //because cannot add a panel more than once
      boolean alreadyAdded = false; 
      //go through @courseDays, find days when course meets, add panel to those days
      for (int k = 0; k < courseDays.length; k++) {
        if (courseDays[k]) {
          courseConstraints.gridx = (k+1); //put panel within correct day
          if (!alreadyAdded) { //no need to clone
            //System.out.println("COURSE IS BEING ADDED FOR FIRST TIME");
            alreadyAdded = true;
            calendar.add(c, courseConstraints);
          } else { //need to clone
            //System.out.println("COURSE IS BEING CLONED");
            JPanel clonedPanel = new JPanel(); 
            JLabel cloneLabel = new JLabel(current.getName());
            clonedPanel.add(cloneLabel);
            clonedPanel.setOpaque(true);
            clonedPanel.setBackground(COURSE_BACKGROUND);
            Dimension d = c.getPreferredSize();
            //System.out.println("DIMENSION D = " + d); 
            clonedPanel.setPreferredSize(d);
            calendar.add(clonedPanel, courseConstraints);
          }
        }
      }
      
    } 
    //creates combo box with course names so that user can get course details
    if(!sched.getCurrent().isEmpty())
    list.setModel(new DefaultComboBoxModel(sched.courseNames()));
  }
  
//creates a listener for the comboBox @list so as to give more details about a course
private class ListListener implements ActionListener {
  public void actionPerformed(ActionEvent e) {
    if(!list.getSelectedItem().toString().equals("...")){
       Course selected = sched.findCourse(list.getSelectedItem().toString());
       JOptionPane.showMessageDialog(null, selected.fullString());
    }
  }
}

}