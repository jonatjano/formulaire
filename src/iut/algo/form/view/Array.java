package iut.algo.form.view;

import iut.algo.form.job.BaseType;
import iut.algo.form.job.Language;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Component;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Tableau à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Array extends Control
{
	class ArrayListener implements ActionListener, KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			int deltaR = 0;
			int deltaC = 0;

			switch (e.getKeyCode())
			{
				case KeyEvent.VK_KP_DOWN:
				case KeyEvent.VK_DOWN:
					deltaR--;
					break;

				case KeyEvent.VK_KP_UP:
				case KeyEvent.VK_UP:
					deltaR++;
					break;

				case KeyEvent.VK_KP_LEFT:
				case KeyEvent.VK_LEFT:
					deltaC--;
					break;

				case KeyEvent.VK_KP_RIGHT:
				case KeyEvent.VK_RIGHT:
					deltaC++;
					break;

				default:
					return;
			}

			if (KeyEvent.getKeyModifiersText(e.getModifiers()).equals("Ctrl"))
			{
				deltaR *= 5;
				deltaC *= 5;
			}
			if (KeyEvent.getKeyModifiersText(e.getModifiers()).contains("Maj"))
			{
				if (deltaR != 0)
					deltaR *= tabValues.length;
				if (deltaC != 0)
					deltaC *= tabValues[0].length;
			}

			int goToR = Math.max( 0, Math.min(tabValues.length -1, deltaR + prevR + oriR) ); //6
			int goToC = Math.max( 0, Math.min(tabValues[0].length -1, deltaC + prevC + oriC) ); //6
			moveTo(goToR, goToC);

		}

		public void keyReleased(KeyEvent e)
		{}

		public void keyTyped(KeyEvent e)
		{}

		public void actionPerformed (ActionEvent e)
		{
			if (e.getSource() instanceof JButton)
			{
				// Récupère la commande d'action associée à l'événement, contenant
				// "<ligne>;<colonne>"
				String[] pos = e.getActionCommand().split(";");


				int row = Integer.parseInt( pos[0] );
				int col = Integer.parseInt( pos[1] );

				// Enregistrement dans le tableau de valeurs

				moveTo(row + oriR,col + oriC);
			}
		}
	}


	/** Nombre de colonne minimum à afficher */
	private static final int MIN_COL = 1;
	/** Nombre de ligne minimum à afficher */
	private static final int MIN_ROW = 1;
	/** Nombre de colonne maximum à afficher */
	private static final int MAX_COL = 5;
	/** Nombre de ligne maximum à afficher */
	private static final int MAX_ROW = 5;

	/** Padding sur l'axe des abscisses à placer à droite et à gauche */
	private static final int GAP_X		= 10;
	/** Dimension d'une cellule du tbaleau */
	private static final int CELL_SIZE	= 25;

	/** Couleur de base des cellules du tableau */
	private static final Color normalColor			= new Color(255,255,255);
	/** Couleur prise par la ligne sélectionnée */
	private static final Color selectedRowColor 	= new Color(0,0,0);
	/** Couleur prise par la colonne sélectionnée */
	private static final Color selectedColColor 	= new Color(0,0,0);
	/** Couleur prise par la cellule sélectionnée  */
	private static final Color selectedCellColor	= new Color(255,0,0);

	/** Label décrivant le contôle à l'utilisateur */
	private JLabel 			labelL;

	/** Index de la ligne de la cellule servant d'origine à afficher du tableau */
	private int				oriR;
	/** Index de la colonne de la cellule servant d'origine à afficher du tableau */
	private int				oriC;

	/** Index de la ligne de la cellule sélectionnée précédente */
	private int 			prevR;
	/** Index de la colonne de la cellule sélectionnée précédente */
	private int 			prevC;

	/** Panel contenant uniquement les cellules le tableau */
	private JPanel 			arrayP;
	/** Panel contenant l'élément permettant de modifier la valeur de la cellule sélectionnée */
	private JPanel 			valuePanel;
	/** Controle interactible pour modifier la valeur de la cellule sélectionnée */
	private Control			valueControl;

	/* Ensemble des labels affichant l'index des lignes affichées */
	private List<JLabel>	rowLabels;
	/* Ensemble des labels affichant l'index des colonnes affichées */
	private List<JLabel>	colLabels;

	/** Ensemble des cellules affichées du tableau */
	private JButton[][]		tabButtons;
	/** Ensemble des valeurs du tableau */
	private Object[][]		tabValues;


	/**
	 * Création d'un tableau, s'affichant par un maximum de 5 cases sur 5 cases,
	 * avec lesquelles l'utilisateur peut interargir pour en modifier le contenu
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
 	 * @param type Type associé à l'élément
	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @param nbR Nombre total de lignes du tableau
	 * @param nbC Nombre total de colonnes du tableau
	 * @return L'élément créé
	 */
	public Array (String label, String id, BaseType type, int width, int x, int y, int nbR, int nbC)
	{
		super(label, id, type, width, x, y);
		this.type	= type;
		this.oriC	= 0;
		this.oriR	= 0;

		// Les dimensions du tableau
		int tabWidth	= (Math.min(5, nbC) + 2) * Array.CELL_SIZE;
		int tabHeight	= (Math.min(5, nbR) + 2) * Array.CELL_SIZE;

		this.panel.setLayout( null );
		this.panel.setBounds( x, y, tabWidth + width + Control.LABEL_WIDTH + Array.GAP_X, tabHeight );

		this.typePanel.setBounds( x + width + Control.LABEL_WIDTH + tabWidth + Array.GAP_X, y, 100, Control.DFLT_HEIGHT );


		/*  Label */
		
		this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setBounds( 0, 0, Control.LABEL_WIDTH, Control.DFLT_HEIGHT );
		this.labelL.setForeground(Color.GRAY);

		this.panel.add( this.labelL );


		/*~~~~~~~~~~~~~~~~~~~~~*/
		/* Création du tableau */
		/*~~~~~~~~~~~~~~~~~~~~~*/

		this.colLabels		= new ArrayList<JLabel>();
		this.rowLabels		= new ArrayList<JLabel>();

		int clampedCol 		= Math.max( 0, Math.min(Array.MAX_COL, nbC) );
		int clampedRow 		= Math.max( 0, Math.min(Array.MAX_ROW, nbR) );


		this.arrayP			= new JPanel( new GridLayout(clampedRow + 2, clampedCol + 2) );
		this.arrayP.setBounds( Control.LABEL_WIDTH, 0, tabWidth, tabHeight );
		this.arrayP.setBackground( Color.lightGray );
		this.arrayP.setBorder( new CompoundBorder(BorderFactory.createLoweredBevelBorder(), new EmptyBorder(0,Array.GAP_X,0,0)) ); // BorderFactory.createLineBorder(Color.black)

		this.panel.add( this.arrayP );


		/* Création du tableau logique */

		if		( this.type == BaseType.String)		this.tabValues = new String[nbR][nbC];
		else if ( this.type == BaseType.Int)		this.tabValues = new Integer[nbR][nbC];
		else if ( this.type == BaseType.Double)		this.tabValues = new Double[nbR][nbC];
		else if ( this.type == BaseType.Char)		this.tabValues = new Character[nbR][nbC];
		else if ( this.type == BaseType.Boolean)	this.tabValues = new Boolean[nbR][nbC];


		Font baseFont	= this.panel.getFont();
		Font newFont	= baseFont.deriveFont(baseFont.getStyle() | Font.BOLD);


		/* Création du tableau sur l'interface */

		tabButtons = new JButton[clampedRow][clampedCol];
		for (int i = -1; i < clampedRow + 1; i++)
		{
			boolean inRowBounds = i >= 0 && i < clampedRow;

			// Ajout des labels des colonnes à la liste correspondante
			JLabel labelRow = null;
			if (inRowBounds)
			{
				labelRow = new JLabel( String.format("%d", clampedRow - i - 1) );
				this.rowLabels.add( labelRow );
			}
			else
			{
				labelRow = new JLabel();
			}
			this.arrayP.add( labelRow );


			/* CORPS */

			for (int j = 0; j < clampedCol; j++)
			{
				boolean inColBounds = j >= 0 && j < clampedCol;

				if (inColBounds)
				{
					if (inRowBounds)
					{
						JButton cellB = new JButton();
						tabButtons[clampedRow - i - 1][j] = cellB;
						cellB.addActionListener( new ArrayListener() );
						cellB.setActionCommand( String.format("%d;%d", clampedRow - i - 1, j) );
						cellB.setBackground(normalColor);
						this.arrayP.add( cellB );
					}
					else
					{
						// Ajout des labels des lignes à la liste correspondante
						JLabel colLabels = null;
						if (i >= 0)
						{
							colLabels = new JLabel( String.format("%d", j), SwingConstants.CENTER );
							this.colLabels.add( colLabels );
						}
						else
						{
							colLabels = new JLabel();
						}
						this.arrayP.add( colLabels );
					}
				}
				else
					this.arrayP.add( new JLabel() );
			}

			// Ajoute des label vide pour combler les de la bordure
			this.arrayP.add( new JLabel() );
		}
		setTabBackground(-1, -1, 0, 0);


		/*~~~~~~~~~~~~~~~~~~~~~~~~~*/
		/*  Panel de modification  */
		/*~~~~~~~~~~~~~~~~~~~~~~~~~*/

		this.valuePanel = new JPanel();
		this.valuePanel.setBackground( Frame.obtainFormColor() );
		this.valuePanel.setLayout( new GridLayout(2, 1) );


		this.valuePanel.setBounds( tabWidth + Control.LABEL_WIDTH + Array.GAP_X, 0, width, (int) (Array.CELL_SIZE*5 / 2) );


		String valueStr = "Value";
		if (Frame.getLang() == Language.FR)
			valueStr = "Valeur";

		JLabel valueL = new JLabel( String.format("%s :", valueStr), SwingConstants.LEFT );
		valueL.setForeground( Color.GRAY );

		this.valuePanel.add( valueL );

		if (type == BaseType.Boolean)		this.valueControl	= new Checkbox("cdfz", width - 11, 0, 0);
		else								this.valueControl	= new Text("dzdz", type, width - 11, 0, 0);

		this.valuePanel.add( valueControl.getPanel() );

		for (Component c : Frame.getAllComponents(valuePanel))
			c.addKeyListener(new ArrayListener());
		for (Component c : Frame.getAllComponents(arrayP))
			c.addKeyListener(new ArrayListener());

		this.panel.add( this.valuePanel );
	}

	/**
	 * Création d'un tableau, s'affichant par un maximum de 5 cases sur 5 cases,
	 * avec lesquelles l'utilisateur peut interargir pour en modifier le contenu
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
 	 * @param type Type associé à l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @param nbR Nombre total de lignes du tableau
	 * @param nbC Nombre total de colonnes du tableau
	 * @return L'élément créé
	 */
	public Array (String label, String id, BaseType type, int x, int y, int nbR, int nbC)
	{
		this(label, id, type, Control.DFLT_WIDTH, x, y, nbR, nbC);
	}


	/**
	 * Vérifie si le tableau de valeurs n'a qu'une dimension
	 * @return Vrai si le tableau n'a qu'une dimension, sinon faux
	 */
	public boolean hasOneDimension ()
	{
		if (this.tabValues.length == 0)		return true;
		else								return this.tabValues.length == 1 || this.tabValues[0].length == 1;
	}

	private void moveTo (int row, int col)
	{
		Object value = valueControl.getValue();

		tabValues[oriR + prevR][oriC + prevC] = value;

		//



		int deltaR = row - prevR - oriR;
		int deltaC = col - prevC - oriC;
		int numbutR = prevR;
		int numbutC = prevC;
		int deplacement = 0;
		int eq = 0;

		if (deltaR > 0)
			eq = Array.MAX_ROW - numbutR - 2;
		else
			eq = numbutR -1;

		if (eq == -1)
			deltaR = 0;
		else
		{
			if ( Math.abs(deltaR) > eq)
				deplacement = eq * deltaR / Math.abs(deltaR);
			else
				deplacement = deltaR;

			numbutR += deplacement;
			deltaR -= deplacement;
		}

		if (deltaC > 0)
			eq = Array.MAX_COL - numbutC - 2;
		else
			eq = numbutC -1;

		if (eq == -1)
			deltaC = 0;
		else
		{
			if ( Math.abs(deltaC) > eq)
				deplacement = eq * deltaC / Math.abs(deltaC);
			else
				deplacement = deltaC;

			numbutC += deplacement;
			deltaC -= deplacement;
		}

			int[] tabDep = shiftDisplay(deltaR,deltaC);
			if (tabDep[0] != deltaR)
				numbutR += deltaR / Math.abs(deltaR);
			if (tabDep[1] != deltaC)
				numbutC += deltaC / Math.abs(deltaC);


		setTabBackground(prevR, prevC, numbutR, numbutC);


		prevR = numbutR;
		prevC = numbutC;

		valueControl.setValue(tabValues[numbutR + oriR][numbutC + oriC]);
		valueControl.requestFocus();

		// Change le focus pour le mettre sur l'élément à modifier
	}



	/**
	 * Décale l'affichage du tableau
	 * @param deltaR Décalage de "deltaR" ligne(s)
	 * @param deltaC Décalage de "deltaC" colonne(s)
	 */
	private int[] shiftDisplay (int deltaR, int deltaC)
	{
		int oriRTemp = oriR;
		int oriCTemp = oriC;

		int maxRow = Array.MAX_ROW - 1;
		if (tabValues.length < Array.MAX_ROW - 1 )
			maxRow = tabValues.length -1;

		int maxCol = Array.MAX_COL - 1;
		if (tabValues[0].length < Array.MAX_COL - 1 )
			maxCol = tabValues.length -1;

		oriR = Math.max(0, Math.min(tabValues.length - 1 - maxRow, oriR + deltaR));
		oriC = Math.max(0, Math.min(tabValues[0].length - 1 - maxCol, oriC + deltaC));


		for (int i = 0; i < rowLabels.size(); i++)
			rowLabels.get(i).setText((oriR + rowLabels.size() - 1 - i) + "");

		for (int i = 0; i < colLabels.size(); i++)
			colLabels.get(i).setText((oriC + i) + "");

		return new int[] {oriR - oriRTemp, oriC - oriCTemp};
	}

	/**
	 * Crée ou met à jour l'affichage du tableau
	 * @param prevR La précédente ligne sélectionnée
	 * @param prevC La précédente colonne sélectionnée
	 * @param row La précédente colonne sélectionnée
	 * @param col La précédente colonne sélectionnée
	 */
	public void setTabBackground (int prevR, int prevC, int row, int col)
	{
		if (prevR != -1)
		{
			for (int i = 0; i < tabButtons.length; i++)
			{
				tabButtons[i][prevC].setBackground(normalColor);
			}
			for (int i = 0; i < tabButtons[prevR].length; i++)
			{
				tabButtons[prevR][i].setBackground(normalColor);
			}
		}

		for (int i = 0; i < tabButtons.length; i++)
		{
			tabButtons[i][col].setBackground(selectedRowColor);
		}
		for (int i = 0; i < tabButtons[row].length; i++)
		{
			tabButtons[row][i].setBackground(selectedColColor);
		}

		tabButtons[row][col].setBackground(selectedCellColor);
	}

	/**
	 * Réinitialise l'élément, le retournant au même état que lors de sa création
	 */
	@Override
	public void reset ()
	{
		int posR = oriR + prevR;
		int posC = oriC + prevC;

		for (int i = 0; i < tabValues.length; i++)
			for (int j = 0; j < tabValues[i].length; j++)
				tabValues[i][j] = null;

		moveTo(0,0);
		tabValues[posR][posC] = null;
	}

	/**
	 * Retourne la valeur contenue dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public Object[][] getValue ()
	{
		return this.tabValues;
	}

	/**
	 * Modifie la valeur associée à l'élément
	 * @param newValue La nouvelle valeur associée à l'élément du formulaire
	 */
	@Override
	public boolean setValue (Object newValue)
	{
		//TODO
		return false;
	}
}
