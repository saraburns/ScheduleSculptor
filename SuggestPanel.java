/*
 * The SuggestPanel sets up a panel for course suggestions. Course suggestions are based on a series of questions to the 
 * user, such as, "What type of course are you looking for?", and will not conflict with the user's current schedule.
 * The user may also ask the program, "What do you think?", which will prompt a suggestion based on the user's current 
 * schedule. Finally, the user can ask for a random class that does not conflict with their schedule.
 * This panel was a joint effort.
 * @author Sara Burns and Claire Schlenker 
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javafoundations.*;
import java.util.*;

public class SuggestPanel extends JPanel{
  
  //instance variables
  private JLabel title, subjectArea, randomCourse, rCourse;
  private JComboBox subjects;
  private final String[] combos = new String[]{"Please Choose a Subject:","Math and Sciences", 
    "Arts and Literature", "History, Philosophy, and Society"};
  private JButton yes, no, addSched, newCourse, startOver;
  private JLabel question, randomCourseInstructions;
  private JButton randomSearch;
  private Schedule sched;
  private CourseCollection collect; 
  private Course currentCourse;
  private ViewPanel vp;
  private RecommenderTrees trees;
  private LinkedBinaryTree<String> current;
  private JTextPane area;
  private JScrollPane scroll;
  private LinkedList<Course> list;
  private int categoryIndex;
  
  //constructor
  public SuggestPanel(Schedule s, CourseCollection collection, ViewPanel vp) {
    
    //sets the layout and look of the frame and of the container panel @elements
    this.setLayout(new BorderLayout());
    JPanel elements = new JPanel();
    elements.setBackground(Color.WHITE);
    elements.setOpaque(true);
    elements.setLayout(new GridLayout(3,1,20,20));
    
    //init instance variables
    title = new JLabel("<html><h1>Course Recommender</h1><html>");
    JLabel instructions = new JLabel("<html><h3><center>Not sure which courses to take? Let our Course Recommender help you out!</center></h3></html>");
    subjectArea = new JLabel("<html><h3><center>Please Select a Subject Area</center></h3><html>");
    randomCourse = new JLabel("<html><h3>Or, Find a Random Course</h3></html>");
    randomCourseInstructions = new JLabel("<html><h4><center>Click on the Course name to see details and to add it to your schedule!</center></h4></html>");
    subjects = new JComboBox(combos);
    question = new JLabel("The questions will go here");
    yes = new JButton("Yes");
    no = new JButton("No");
    randomSearch = new JButton("Search");
    addSched = new JButton("Add to Schedule");
    newCourse = new JButton("Try again");
    startOver = new JButton("Start over");
    sched = s; 
    collect = collection;
    rCourse = new JLabel();
    currentCourse = new Course();
    list = new LinkedList<Course>();
    this.vp = vp; 
    trees = new RecommenderTrees();
    current = new LinkedBinaryTree<String>();
    area = new JTextPane();
    area.setContentType("text/html");
    area.setPreferredSize(new Dimension(0,0));
    scroll = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setPreferredSize(new Dimension(0,0));
 
    
    
    /*
     * adds listeners to various components so that the user can interact with the panel
     */
    subjects.addActionListener(new SubjectListener());
    randomSearch.addActionListener(new ButtonListener());
    rCourse.addMouseListener(new CourseLabelListener());
    yes.addActionListener(new YesNoListener());
    no.addActionListener(new YesNoListener());
    addSched.addActionListener(new ButtonListener());
    newCourse.addActionListener(new ButtonListener());
    startOver.addActionListener(new ButtonListener());
    
    //for filler
    JPanel leftFiller = new JPanel();
    leftFiller.setPreferredSize(new Dimension(100,100));
    leftFiller.setBackground(new Color(0,46,184));
    leftFiller.setOpaque(true);
    JPanel rightFiller = new JPanel();
    rightFiller.setPreferredSize(new Dimension(100,100));
    rightFiller.setBackground(new Color(0,46,184));
    rightFiller.setOpaque(true);
    
    //for organization
    //@topPanel contains the title of the panel and instructions for use
    JPanel topPanel = new JPanel();
    topPanel.setBackground(new Color(153,204,255));
    topPanel.setOpaque(true);
    topPanel.add(title);
    topPanel.add(instructions);
    
    //@centerPanel uses RecommenderTrees to suggest a course to the user
    //based off of user input
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(Color.WHITE);
    centerPanel.setOpaque(true);
    centerPanel.add(subjectArea);
    centerPanel.add(subjects);
    centerPanel.add(question);
    //small filler panels were added just to make it look better
    JPanel filler1 = new JPanel();
    filler1.setBackground(Color.WHITE);
    filler1.setOpaque(true);
    filler1.setPreferredSize(new Dimension(750,10));
    centerPanel.add(scroll);
    centerPanel.add(filler1);
    centerPanel.add(yes);
    centerPanel.add(no);
    centerPanel.add(addSched);
    centerPanel.add(newCourse);
    centerPanel.add(startOver);
    
    //certain components remain invisible until the user takes action
    question.setVisible(false);
    yes.setVisible(false);
    no.setVisible(false);
    addSched.setVisible(false);
    newCourse.setVisible(false);
    startOver.setVisible(false);
    
    //@bottomPanel contains the random search 
    JPanel bottomPanel = new JPanel();
    bottomPanel.setBackground(new Color(51,102,255));
    bottomPanel.setOpaque(true);
    bottomPanel.add(randomCourse);
    //small filler panels were added for aesthetics
    JPanel bottomFiller2 = new JPanel();
    bottomFiller2.setPreferredSize(new Dimension(900,5));
    bottomFiller2.setOpaque(false);
    bottomPanel.add(bottomFiller2);
    bottomPanel.add(randomCourseInstructions);
    //another small filler panel
    JPanel bottomFiller = new JPanel();
    bottomFiller.setPreferredSize(new Dimension(900,5));
    bottomFiller.setOpaque(false);
    bottomPanel.add(bottomFiller);
    bottomPanel.add(randomSearch);
    bottomPanel.add(rCourse);
    
    //adds panels containing elements to the container
    elements.add(topPanel);
    elements.add(centerPanel);
    elements.add(bottomPanel);
    
    this.add(elements, BorderLayout.CENTER);
    this.add(leftFiller, BorderLayout.WEST);
    this.add(rightFiller, BorderLayout.EAST);
  }
  
  /******************************************************
    * private helper method which sets certain components
    * as visible/invisible, dependent on action taken by the user
    * @see subject area drop down box and subject area label
    *****************************************************/
  private void reset() {
    addSched.setVisible(false);
    newCourse.setVisible(false);
    startOver.setVisible(false);
    scroll.setVisible(false);
    subjects.setVisible(true);
    subjectArea.setVisible(true);
    question.setVisible(false);
    
  }
  
  /********************************************************
    * creates a listener for buttons on the panel
    * which will set variables appropriately when 
    * the button is pressed
    ******************************************************/
  private class ButtonListener implements ActionListener {
    
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == randomSearch){
      Course c = collect.getRandomCourse(sched);
      rCourse.setText(c.getName());
      currentCourse = c;
      }
      if(e.getSource() == addSched) {
        sched.addCourse(currentCourse);
        vp.updateDisplay();
      }
      if(e.getSource() == newCourse) {
        categoryIndex = (categoryIndex + 1) % list.size();
        currentCourse = list.get(categoryIndex);
        area.setText(list.get(categoryIndex).fullString());
      }
      if(e.getSource() == startOver){
        reset();
        
      }
       
    }
  }
  
  /********************************************************
    * creates a listener for the drop down menu 
    * which determines the correct RecommenderTrees 
    * object to traverse
    ******************************************************/
  private class SubjectListener implements ActionListener {
    
    public void actionPerformed(ActionEvent e) {
      //when a subject is selected, certain components become invisible
      subjects.setVisible(false);
      subjectArea.setVisible(false);
      question.setVisible(true);
      //gets appropriate RecommenderTrees object based upon user input
      String getTree = subjects.getSelectedItem().toString();
//      System.out.println(subjects.getSelectedItem().toString());
      if (getTree.equals("Math and Sciences")) {
        current = trees.getMathScience();
      } else if (getTree.equals("Arts and Literature")) {
        current = trees.getArtLit();
      } else {
        current = trees.getHistPhilos();
      }
      question.setText(current.getRootElement());
      yes.setVisible(true);
      no.setVisible(true);
      
    }
  }
  
  
  /********************************************************
    * Creates a listener for the yes and no buttons
    * which will appropriately traverse the RecommenderTrees
    * object as necessary
    ******************************************************/
  private class YesNoListener implements ActionListener {
    
    public void actionPerformed(ActionEvent e) {
      //if there are more questions to be asked
      if (current.height() > 1) {
        if (e.getSource() == yes) {
          current = current.getRight();
        } else {
          current = current.getLeft();
        }
        //gets next question for the user
        question.setText(current.getRootElement());
      } else { //if no more questions to be asked
        //gets final response and final leaf
        if (e.getSource() == yes) {
          current = current.getRight();
        } else {
          current = current.getLeft();
        }
        //returns a list of Courses corresponding to the leaf of the decision tree
        list = collect.getCategory(Integer.parseInt(current.getRootElement()), sched);
        if(list.size() > 0) {
        question.setText("Have you considered:");
        scroll.setVisible(true);
        scroll.setPreferredSize(new Dimension(300,100));
        currentCourse = list.get(0);
        area.setText(list.get(0).fullString());
        yes.setVisible(false);
        no.setVisible(false);
        addSched.setVisible(true);
        newCourse.setVisible(true);
        startOver.setVisible(true);
        }else {
          JOptionPane.showMessageDialog(null,"<html><p>Looks like every course here conflicts with your current schedule!" +
        " Maybe try another subject area, or remove a course from your schedule?</p></html>");
        }
      }
    }
  }
  
    
  
  /********************************************************
    * creates a listener for the course label from the random
    * search, and pops up a dialog box containing course details
    * when the label is clicked
    ******************************************************/
  private class CourseLabelListener implements MouseListener {
    
    //mouseClicked requires that the 
    //mouse is pressed and released within the same pixel
    //the mouse cannot be moved between press and release.
    //thus, this listener uses just mousePressed
    public void mouseClicked(MouseEvent event) {
  
    }
      
      public void mouseExited(MouseEvent event) {
        
      }
      
      public void mouseEntered(MouseEvent event) {
        
      }
      
      public void mousePressed(MouseEvent event) {
        int pane = JOptionPane.showConfirmDialog(null, (currentCourse.fullString() + "\nAdd to Schedule?"), currentCourse.getName(),
                                      JOptionPane.YES_NO_OPTION);
        if (pane == JOptionPane.YES_OPTION) {
          sched.addCourse(currentCourse);
          vp.updateDisplay();
        }
      }
      
      public void mouseReleased(MouseEvent event) {
        
      }
      
      
    }
  

}