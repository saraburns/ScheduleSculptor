import javafoundations.*; // for the LinkedBinaryTree and all
/* RecommenderTrees.java instantiate the decision trees for the Suggest panel's Course Recommender
 * Contains 3 trees: Math & Sciences, Arts & Literature, and History, Philosophy, & Society
 * Trees are LinkedBinaryTree<String>
 * Claire and Sara shared the effort for this implementation.
 * @author Claire Schlenker
 * @author Sara Burns 
 */
public class RecommenderTrees {
  
  private LinkedBinaryTree<String> mathscience;
  private LinkedBinaryTree<String> artlit;
  private LinkedBinaryTree<String> histphilos;
  
  /*Constructor method, sets up the decision trees used in the Course Recommender. Adds the questions to the
   * correct LinkedBinaryTree<String>
   */
  public RecommenderTrees(){
    //Questions for Math & Sciences
    String ms1 = "Do you enjoy problem solving?";
    String ms2 = "Did you enjoy your science classes in high school?";
    String ms3 = "What about applying your knowledge?";
    String ms4 = "Have you taken any non-core science classes here?";
    String ms5 = "Have you taken any core science classes here?";
    String ms6 = "Do you have experience with math classes?";
    String ms7 = "Have you ever taken a CS class?";
    String ms8 = "1";
    String ms9 = "2";
    String ms10 = "3";
    String ms11 = "4";
    String ms12 = "5";
    String ms13 = "6";
    String ms14 = "7";
    String ms15 = "8";
    
    //create the LinkedBinaryTrees for Math & Science
    LinkedBinaryTree<String> noncore = new LinkedBinaryTree<String>(ms4, new LinkedBinaryTree<String>(ms8), 
                                                                    new LinkedBinaryTree<String>(ms9));
    LinkedBinaryTree<String> core = new LinkedBinaryTree<String>(ms5, new LinkedBinaryTree<String>(ms10), 
                                                                 new LinkedBinaryTree<String>(ms11));
    LinkedBinaryTree<String> math = new LinkedBinaryTree<String>(ms6, new LinkedBinaryTree<String>(ms12), 
                                                                 new LinkedBinaryTree<String>(ms13));
    LinkedBinaryTree<String> cs = new LinkedBinaryTree<String>(ms7, new LinkedBinaryTree<String>(ms14), 
                                                               new LinkedBinaryTree<String>(ms15));
    LinkedBinaryTree<String> science = new LinkedBinaryTree<String>(ms2, noncore, core);
    LinkedBinaryTree<String> problem = new LinkedBinaryTree<String>(ms3, math, cs);
    mathscience = new LinkedBinaryTree<String>(ms1, science , problem);
    
    
    //Questions for History, Philosophy, Society
    String hp1 = "Are you interested in classes that deal with a specific culture or time period?";
    String hp2 = "Do you feel comfortable with self-examination?";
    String hp3 = "Do you want to study the Western World?";
    String hp4 = "Do you see yourself in a position of power in the future?";
    String hp5 = "Would you like to look at human interaction?";
    String hp6 = "Do you want to affect change in the world?";
    String hp7 = "Would you like to study the United States?";
    String hp8 = "17";
    String hp9 = "18";
    String hp10 = "19";
    String hp11 = "20";
    String hp12 = "21";
    String hp13 = "22";
    String hp14 = "23";
    String hp15 = "24";
    //create the LinkedBinaryTrees for History, Philosophy, Society
    LinkedBinaryTree<String> power = new LinkedBinaryTree<String>(hp4, new LinkedBinaryTree<String>(hp8), 
                                                                    new LinkedBinaryTree<String>(hp9));
    LinkedBinaryTree<String> human = new LinkedBinaryTree<String>(hp5, new LinkedBinaryTree<String>(hp10), 
                                                                 new LinkedBinaryTree<String>(hp11));
    LinkedBinaryTree<String> change = new LinkedBinaryTree<String>(hp6, new LinkedBinaryTree<String>(hp12), 
                                                                 new LinkedBinaryTree<String>(hp13));
    LinkedBinaryTree<String> america = new LinkedBinaryTree<String>(hp7, new LinkedBinaryTree<String>(hp14), 
                                                               new LinkedBinaryTree<String>(hp15));
    LinkedBinaryTree<String> self = new LinkedBinaryTree<String>(hp2, power, human);
    LinkedBinaryTree<String> west = new LinkedBinaryTree<String>(hp3, change, america);
    histphilos = new LinkedBinaryTree<String>(hp1, self , west);
    
    //Questions for Languages and Literature/Arts
    String al1 = "Are you driven to learn by a love of knowledge?";
    String al2 = "Are you interested in art?";
    String al3 = "Do you have a great story to tell?";
    String al4 = "Would you like to learn a new language?";
    String al5 = "Do you have experience with visual art?";
    String al6 = "Are you interested in ancient cultures?";
    String al7 = "Would you mind a reading-intensive course?";
    String al8 = "9";
    String al9 = "10";
    String al10 = "11";
    String al11 = "12";
    String al12 = "13";
    String al13 = "14";
    String al14 = "15";
    String al15 = "16";
    
    //create the LinkedBinaryTree for Arts & Literature
    LinkedBinaryTree<String> lang = new LinkedBinaryTree<String>(al4, new LinkedBinaryTree<String>(al8), 
                                                                new LinkedBinaryTree<String>(al9));
     LinkedBinaryTree<String> visualart = new LinkedBinaryTree<String>(al5, new LinkedBinaryTree<String>(al10), 
                                                                new LinkedBinaryTree<String>(al11));
      LinkedBinaryTree<String> ancient = new LinkedBinaryTree<String>(al6, new LinkedBinaryTree<String>(al12), 
                                                                new LinkedBinaryTree<String>(al13));
       LinkedBinaryTree<String> read = new LinkedBinaryTree<String>(al7, new LinkedBinaryTree<String>(al14), 
                                                                new LinkedBinaryTree<String>(al15));
        LinkedBinaryTree<String> art = new LinkedBinaryTree<String>(al2, lang, visualart);
        LinkedBinaryTree<String> story = new LinkedBinaryTree<String>(al3, ancient, read);
        artlit = new LinkedBinaryTree<String>(al1, art, story);

    
  }
  /*getter method for the mathscience tree
   * @return the mathscience LinkedBinaryTree<String> decision tree
   */ 
  public LinkedBinaryTree<String> getMathScience() {
    return mathscience;
  }
   /*getter method for the arts and literature tree
   * @return the artlit LinkedBinaryTree<String> decision tree
   */ 
  public LinkedBinaryTree<String> getArtLit() {
    return artlit;
  }
   /*getter method for the history, philosophy, and society tree
   * @return the histphilos LinkedBinaryTree<String> decision tree
   */ 
  public LinkedBinaryTree<String> getHistPhilos() {
    return histphilos;
  }
  /*Testing main
   */ 
  public static void main(String[] args) {
    RecommenderTrees trees = new RecommenderTrees();
    System.out.println(trees.mathscience);
    System.out.println(trees.histphilos);
  }
}
