package rwg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import java.util.stream.*;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Choice;
import java.awt.Label;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.TextArea;
import java.awt.Button;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class rwg {

	private JFrame frame;
	private Choice langChoice;
	private TextArea wordList;
	JButton stopBtn = new JButton("Stop" );
	JButton startBtn= new JButton("Start");
	private volatile boolean runFlag = false; //Ensures that the while loop can be stopped.
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (Throwable e)
		{
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					rwg window = new rwg();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public rwg() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(0, 0, 684, 561);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		langChoice = new Choice();
		langChoice.setFont(new Font("Dialog", Font.PLAIN, 13));
		langChoice.setBounds(10, 27, 132, 20);
		langChoice.addItem("English");
		langChoice.addItem("Latin");
		panel.add(langChoice);
		langChoice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

			}
		});

		Label label = new Label("Language");
		label.setFont(new Font("Dialog", Font.PLAIN, 13));
		label.setBounds(10, 6, 132, 20);
		panel.add(label);

		wordList = new TextArea();
		wordList.setEditable(false);
		wordList.setBounds(10, 57, 664, 480);
		panel.add(wordList);
		stopBtn.setAction(action_1);
		stopBtn.setEnabled(false);

		stopBtn.setBounds(604, 27, 70, 22);
		panel.add(stopBtn);
		startBtn.setAction(action);

		startBtn.setBounds(524, 27, 70, 22);
		panel.add(startBtn);
	}
	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	int k, i = 0, j, max = 5;
	String   word = "";
	boolean finished = false, parityTest = false;
	String[] phon = {"","a","ai","ay","au","aw","augh","ar","b","bu","c","cei","ch","ci","ck","d","dge","e","ea","ee","ew","eigh","ei","ey","er","ed","ear","f","g","gn","gu","h","i","ie","igh","ir","j","k","kn","l","m","n","ng","o","oa","oe","oi","oy","oo","ou","ow","or","ough","p","ph","qu","r","s","sh","si","t","tch","th","ti","u","ui","ur","v","w","wh","wr","wor","x","y","z"};
	int   [] test = new int[max];
	Random r = new Random();
	public void startThread()
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				runFlag = true;				
				while(runFlag)
				{
					while(!finished) {
						k = r.nextInt(74) + 1;
						if(i==0 && IntStream.of(14,16,19,41,48,61).anyMatch(v->v==k)) {System.out.println("break 155: "+i+" , "+k+" ; "+test[i]); break;}
						if(i>0) {//TODO: Check for 3 vowel/consonant trains
							if((test[i-1]==k && IntStream.of(8,10,15,17,27,28,39,40,41,43,53,56,57,60).noneMatch(v->v==k))									 										|| //check for duplicates of non-binary phonograms in any position 
							   (IntStream.of(18,44).anyMatch(v->v==test[i-1]) && IntStream.of(1,2,3,4,5,6,7).anyMatch(v->v==k))								 										|| //check for aa
							   ((test[i-1]==48 || (i>1 && test[i-2]==43 && test[i-1]==43)) && IntStream.of(43,44,45,46,47,48,49,50,51,52).anyMatch(v->v==k))									    || //check for ooo
							   ((test[i-1]==19 || (i>1 && IntStream.of(16,17,33,45).anyMatch(v->v==test[i-2]) && test[i-1]==17)) && IntStream.of(17,18,19,20,21,22,23,24,25,26).anyMatch(v->v==k))  || //check for eee
							   (IntStream.of(6,12,21,34,52,54,58,61,62,69).anyMatch(v->v==test[i-1]) && k==31)												 										|| //check for hh
							   (IntStream.of(2,11,13,22,32,46,59,63,65).anyMatch(v->v==test[i-1]) && IntStream.of(32,33,34,35).anyMatch(v->v==k))				 									|| //check for ii
							   (test[i-1]==37 && IntStream.of(37,38).anyMatch(v->v==k))																		 										|| //check for kk
							   (IntStream.of(4,9,30,49,55).anyMatch(v->v==test[i-1]) && IntStream.of(55,64,65,66).anyMatch(v->v==k))																|| //check for uu
							   (IntStream.of(5,20,50,68).anyMatch(v->v==test[i-1]) && IntStream.of(68,69,70,71).anyMatch(v->v==k))																	|| //check for ww
							   (IntStream.of(3,23,47,73).anyMatch(v->v==test[i-1]) && k==73))																										   //check for yy
							{System.out.println("break 164: "+i+" , "+k+" ; "+test[i]); break;}
							if(i==1) {//B,D,E,G,N,O,P,S,T
								if(test[0]==k 																  || //check for any duplicates at beginning
								  (test[0]==8  && k==9) 													  || //check for bb 
								  (test[0]==15 && k==16) 													  || //check for dd
								  (test[0]==17 && IntStream.of(18,19,20,21,22,23,24,25,26).anyMatch(v->v==k)) || //check for ee
								  (test[0]==28 && IntStream.of(29,30).anyMatch(v->v==k)) 					  || //check for gg
								  (test[0]==41 && k==42) 													  || //check for nn
								  (test[0]==43 && IntStream.of(44,45,46,47,48,49,50,51,52).anyMatch(v->v==k)) || //check for oo
								  (test[0]==53 && k==54) 													  || //check for pp
								  (test[0]==57 && IntStream.of(58,59).anyMatch(v->v==k)) 					  || //check for ss
								  (test[0]==60 && IntStream.of(61,62,63).anyMatch(v->v==k)))					 //check for tt
								{System.out.println("break 176: "+i+" , "+k+" ; "+test[i]); break;}
								if(k==39 && IntStream.of(10,57,60).anyMatch(v->v==test[0])) {System.out.println("break 177: "+i+" , "+k+" ; "+test[i]); break;}//if L follows T,C,S
							}
						}
						test[i] = k;
						if(IntStream.of(6,21,34,52).anyMatch(v->v==k) && i<max-1)              //if augh,eigh,igh,ough are first, follow with T
							test[++i] = 60;
						if(i>=max-1 || (r.nextInt(5)==0 && word.length()>3))
							finished = true;
						else i++;
					}
					for(j = 0 ; j < test.length-1 ; ++j) word += phon[test[j]];
					j=0;
					if(word.length()>3) {
						do {
							parityTest = !((IntStream.of('a','e','i','o','u').anyMatch(v->v==word.charAt(j)) && IntStream.of('a','e','i','o','u').anyMatch(v->v==word.charAt(j+1)) && IntStream.of('a','e','i','o','u').anyMatch(v->v==word.charAt(j+2)) && IntStream.of('a','e','i','o','u').anyMatch(v->v==word.charAt(j+2))) || (!IntStream.of('a','e','i','o','u').anyMatch(v->v==word.charAt(j)) && !IntStream.of('a','e','i','o','u').anyMatch(v->v==word.charAt(j+1)) && !IntStream.of('a','e','i','o','u').anyMatch(v->v==word.charAt(j+2)) && !IntStream.of('a','e','i','o','u').anyMatch(v->v==word.charAt(j+2))));
							System.out.println(word.charAt(j)+" "+word.charAt(j+1)+" "+word.charAt(j+2)+" "+word.charAt(j+3)+" "+parityTest);
							j++;
						}while(j+3<word.length() && parityTest);
						if(parityTest) setWordListText(getWordListText() + "\n" + word);
					}
					i = 0;
					word = "";
					Arrays.fill(test, 0);
					finished = false;
				}
			}
		});t.start();
	}			

	public Choice getLangChoice() {
		return langChoice;
	}
	public String getWordListText() {
		return wordList.getText();
	}
	public void setWordListText(String text) {
		wordList.setText(text);
	}
	@SuppressWarnings("serial")
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Start");
			putValue(SHORT_DESCRIPTION, "Start Generating Words");
		}
		public void actionPerformed(ActionEvent e) {
			stopBtn .setEnabled(true );
			startBtn.setEnabled(false);
			startThread();
		}
	}
	public boolean getStartBtnEnabled() {
		return startBtn.isEnabled();
	}
	public void setStartBtnEnabled(boolean enabled) {
		startBtn.setEnabled(enabled);
	}
	public boolean getStopBtnEnabled() {
		return stopBtn.isEnabled();
	}
	public void setStopBtnEnabled(boolean enabled_1) {
		stopBtn.setEnabled(enabled_1);
	}
	@SuppressWarnings("serial")
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Stop");
			putValue(SHORT_DESCRIPTION, "Stop Generating Words");
		}
		public void actionPerformed(ActionEvent e) {
			stopBtn .setEnabled(false);
			startBtn.setEnabled(true );
			runFlag = false;
		}
	}
}
