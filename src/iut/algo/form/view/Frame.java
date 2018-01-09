package iut.algo.form.view;

import iut.algo.form.job.Language;
import iut.algo.form.job.BaseType;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * Structure de base de la fenêtre contenant le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Frame extends JFrame implements ActionListener
{
	private FormKeyListener fKeyListener;
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

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


	public Frame (String title, int width, int height, int x, int y)
	{
		super();

		this.controls	= new ArrayList<Control>();


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
		this.upperPanel.setPreferredSize( new Dimension((int) (width - 1/15f * width), Math.max(0, height - 100)) );
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

		fKeyListener = new FormKeyListener(this);
		addKeyListener(fKeyListener);

		this.mainPanel.add( secondaryPanel );
		this.add( BorderLayout.CENTER, mainPanel );
		this.setVisible(true);
	}
	public Frame (int width, int height, int x, int y)
	{
		this("", width, height, x, y);
	}

	public static void createFrame (Element root)
	{
		NodeList listElements = root.getChildNodes();
		Language lang;
		if ( root.getNodeName().equals("fenetre") )	lang = Language.FR;
		else										lang = Language.EN;


		// Création de la fenêtre
		String	title	= null;
		int 	width	= 0;
		int 	length	= 0;
		int 	frameX	= Integer.parseInt( root.getAttribute("x") );
		int 	frameY	= Integer.parseInt( root.getAttribute("y") );
		switch (lang)
		{
			case FR:
				title	= root.getAttribute("titre");
				width	= Integer.parseInt( root.getAttribute("largeur") );
				length	= Integer.parseInt( root.getAttribute("longueur") );
				break;
			case EN:
				title	= root.getAttribute("title");
				width	= Integer.parseInt( root.getAttribute("width") );
				length	= Integer.parseInt( root.getAttribute("length") );
				break;
		}
		Frame frame = new Frame(title, width, length, frameX, frameY);


		// Création du formulaire
		for (int i = 0; i < listElements.getLength(); i++)
		{
			Node 			nodeElement	= listElements.item(i);
			NamedNodeMap	attrElement	= nodeElement.getAttributes();

			if (attrElement != null)
			{
				Node		nodeX		= attrElement.getNamedItem("x");
				Node		nodeY		= attrElement.getNamedItem("y");

				String		controlName	= nodeElement.getNodeName();
				String		label		= attrElement.getNamedItem("label").getNodeValue();
				String		id			= attrElement.getNamedItem("id").getNodeValue();
				int			x			= (nodeX == null) ? Control.DFLT_WIDTH : Integer.parseInt( nodeX.getNodeValue() );
				int			y			= (nodeY == null) ? Control.DFLT_WIDTH : Integer.parseInt( nodeY.getNodeValue() );

				NodeList	listChoices;
				Object[]	choices;
				Control		control 	= null;

				// Recherche les éléments en fonction de la langue utilisateur
				switch (controlName)
				{
					case "texte":
						String type = attrElement.getNamedItem("type").getNodeValue();

						BaseType baseType = BaseType.Int;
						switch (type)
						{
							case "chaine":
							case "string":
								baseType = BaseType.String;
								break;

							case "entier":
							case "int":
								baseType = BaseType.Int;
								break;

							case "double":
								baseType = BaseType.Double;
								break;

							case "caractere":
							case "char":
								baseType = BaseType.Char;
								break;
						}

						control = new Text( label, id, baseType, x, y );
						break;

					case "menu":
					case "dropdown":
						listChoices	= nodeElement.getChildNodes();
						choices		= new Object[listChoices.getLength()];

						/* Recherche des différents choix */
						for (int j = 0; j < listChoices.getLength(); j++)
						{
							Node			nodeChoice	= listChoices.item(j);
							NamedNodeMap	attrChoice	= nodeChoice.getAttributes();

							// Si le noeud est un élément, ajoute l'attribut label aux choix possibles
							if ( attrChoice != null &&
								 (nodeChoice.getNodeName().equals("choix") || nodeChoice.getNodeName().equals("choice")) )
								choices[j] = attrChoice.getNamedItem("label").getNodeValue();
						}

						control = new Dropdown( label, id, x, y, choices );
						break;

					case "case":
					case "checkbox":
						control = new Checkbox( label, id, x, y );
						break;

					case "tableau":
					case "array":
						control = null;
						break;

					case "boutons":
					case "buttons":
						listChoices	= nodeElement.getChildNodes();
						choices		= new Object[listChoices.getLength()];

						/* Recherche des différents choix */
						for (int j = 0; j < listChoices.getLength(); j++)
						{
							Node nodeChoice	= listChoices.item(j);

							// Si le noeud est un bouton ajoute la valeur du noud au choix possible
							if ( nodeChoice.getNodeName().equals("bouton") || nodeChoice.getNodeName().equals("button") )
								choices[j] = nodeChoice.getTextContent();
						}

						control = new Buttons( label, id, x, y, choices );
						break;
				}

				frame.addControl( control );
			}
		}
	}

	/**
	 * Ajoute un élément dans le formulaire
	 */
	public void addControl (Control control)
	{
		this.upperPanel.add( control.getPanel() );		// Ajoute l'élément physiquement à l'interface
		this.controls.add( control );					// Ajoute l'élément à la liste des éléments du formulaire

		this.upperPanel.add( control.getIdPanel() );

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

	private void addKeyListenerToAllComponents()
	{
		List<Component> compList = getAllComponents(this);
		for (Component comp : compList)
		{
			if (comp.getListeners(FormKeyListener.class).length == 0)
			{
				comp.addKeyListener(fKeyListener);
			}
		}
	}

	private static List<Component> getAllComponents(Container c)
	{
	    Component[] comps = c.getComponents();
	    List<Component> compList = new ArrayList<Component>();
	    for (Component comp : comps) {
	        compList.add(comp);
	        if (comp instanceof Container)
	            compList.addAll(getAllComponents((Container) comp));
	    }
	    return compList;
	}


	public static void main (String[] args)
	{
		/*Frame frame = new Frame(1200, 600, 200, 50);

		Checkbox 	checkbox 	= new Checkbox("Mangeable", "a01", 20, 25);
		Text 		text1 		= new Text("Nom", "a02", BaseType.String, 20, 50);
		Text 		text2 		= new Text("Age", "a03", BaseType.Int, 20, 75);
		Dropdown 	dropdown 	= new Dropdown("Type", "a04", 20, 100, new String[] {"Soues", "Sos", "Soas"});
		Buttons 	buttons 	= new Buttons("Boustifaille", "a05", 20, 125, new String[] {"Saucisse", "Merguez", "Chipo", "Truc", "Machin"});
		Text 		text3 		= new Text("Taille", "a06", BaseType.Double, 20, 275);
		Array 		array	 	= new Array("Tableau", "a07", 500, 125, new String[][] { {"Vanillakipferl", "Ta mère", "La mienne"}, {"Eh ouais"} });

		frame.addControl( text1 );
		frame.addControl( text2 );
		frame.addControl( checkbox );
		frame.addControl( dropdown );
		frame.addControl( buttons );
		frame.addControl( text3 );
		frame.addControl( array );*/
	}
}
