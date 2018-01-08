//package iut.algo.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Structure de base de la fenêtre contenant le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Frame extends JFrame implements ActionListener
{
	private Font			labelFont;

	/** Liste des éléments intégrés au formulaire */
	private List<Control>	controls;

	/** Panel principal prenant l'intégralité de la frame */
	private JPanel 			mainPanel;
	/** Panel contenant le formulaire, avec un léger décalage par rapport au bord */
	private JPanel 			secondaryPanel;
	/** Panel supérieur du formulaire */
	private JPanel 			upperPanel;
	/** Panel inférieur du formulaire */
	private JPanel 			lowerPanel;


	public Frame (int width, int height, int x, int y)
	{
		super();
		
		this.controls	= new ArrayList<Control>();
		this.labelFont	= null;


		/*~~~~~~~~~~~~~~~~~~~*/
		/*     INTERFACE     */
		/*~~~~~~~~~~~~~~~~~~~*/

		try
		{
			UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel" );
		}
		catch (Exception e) { }

		this.setTitle("");
		this.setSize(width, height);					// Est choisi par l'utilisateur
		this.setLocation(x, y);							// Est choisi par l'utilisateur
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout( new BorderLayout() );

		this.mainPanel	= new JPanel();
		

		/*~~~~~~~~~~~~~~~~~~~*/
		/*  Panel formulaire */
		/*~~~~~~~~~~~~~~~~~~~*/

		this.secondaryPanel	= new JPanel();
		this.secondaryPanel.setLayout( new BoxLayout(this.secondaryPanel, BoxLayout.Y_AXIS) );


		/*~~~~~~~~~~~~~~~~~~~*/
		/*  Panel supérieur  */
		/*~~~~~~~~~~~~~~~~~~~*/

		this.upperPanel	= new JPanel();
		this.upperPanel.setLayout( null );
		this.upperPanel.setBackground( Frame.obtainFormColor() );
		this.upperPanel.setPreferredSize( new Dimension((int) (width - 1/15f * width), 500) );
		this.upperPanel.setBorder( BorderFactory.createLineBorder(Color.black) );

		this.secondaryPanel.add( BorderLayout.CENTER, upperPanel );


		/*~~~~~~~~~~~~~~~~~~~*/
		/*  Panel inférieur  */
		/*~~~~~~~~~~~~~~~~~~~*/

		this.lowerPanel	= new JPanel();
		this.lowerPanel.setLayout( new FlowLayout(FlowLayout.LEFT) );
		JButton validateB	= new JButton("Valider");
		JButton deleteB		= new JButton("Effacer");
		deleteB.addActionListener(this);

		this.lowerPanel.add(validateB);
		this.lowerPanel.add(deleteB);

		this.secondaryPanel.add( BorderLayout.SOUTH, lowerPanel );


		this.mainPanel.add( secondaryPanel );
		this.add( BorderLayout.CENTER, mainPanel );
		this.setVisible(true);
	}

	public static Frame CreateFrame ()//Element root
	{
		return null;
	}

	/**
	 * Ajoute un élément dans le formulaire
	 */
	public void addControl (Control control)
	{
		this.upperPanel.add( control.getPanel() );		// Ajoute l'élément physiquement à l'interface
		this.controls.add( control );					// Ajoute l'élément à la liste des éléments du formulaire

		this.revalidate();
		this.repaint();
	}


	/*-------------------*/
	/*       EVENTS      */
	/*-------------------*/

	/**
	 * Reset tous les éléments du formulaire à leur état initial
	 */
	public void actionPerformed (ActionEvent e)
	{
		for (Control control : controls)
			control.reset();
	}


	/*-------------------*/
	/*      GETTERS      */
	/*-------------------*/

	public static Color obtainFormColor ()
	{
		return Color.white;
	}

	/**
	 * Retourne le panel contenant les éléments du formulaire
	 * @return Panel du formulaire
	 */
	public JPanel obtainForm ()
	{
		return this.upperPanel;
	}

	public List<Control> getControls ()
	{
		return this.controls;
	}


	public static void main (String[] args)
	{
		Frame frame = new Frame(1200, 600, 200, 50);

		Checkbox 	checkbox 	= new Checkbox("Mangeable", 20, 25);
		Text 		text1 		= new Text("Nom", BaseType.String, 20, 50);
		Text 		text2 		= new Text("Age", BaseType.Int, 20, 75);
		Dropdown 	dropdown 	= new Dropdown("Type", 20, 100, new String[] {"Soues", "Sos", "Soas"});
		Buttons 	buttons 	= new Buttons("Boustifaille", 20, 125, new String[] {"Saucisse", "Merguez", "Chipo", "Truc", "Machin"});
		Array 		array	 	= new Array("Tableau", 500, 125, new String[] {"Vanillakipferl", "Ta mère", "La mienne"});

		frame.addControl( text1 );
		frame.addControl( text2 );
		frame.addControl( checkbox );
		frame.addControl( dropdown );
		frame.addControl( buttons );
		frame.addControl( array );
	}
}
