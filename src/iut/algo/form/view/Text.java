package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerModel;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.InputMap;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;

import java.awt.event.FocusListener;

import java.util.LinkedList;

/**
 * Zone de texte à placer dans le formulaire s'adaptant au type de base auquel elle est attachée
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Text extends Control
{
	/* Historique des modifications de l'élément */
	public LinkedList<String> history;
	/** Label décrivant le contôle à l'utilisateur */
	private JLabel 		labelL;
	/** Valeur de l'élément lors de sa création */
	private String		baseValue;


	/**
	 * Crée un objet Texte, qui comprend un label et une zone de texte avec laquelle il est possible d'interargir
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param type Type associé à l'élément
	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	@SuppressWarnings("unchecked")
	public Text (String label, String id, BaseType type, int width, int x, int y)
	{
		super(label, id, type, width, x, y);
		this.type 		= type;
		this.baseValue	= "";
		this.history	= new LinkedList<String>();


		/* Création de la zone texte */

		if (label != null)
		{
			this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
			this.labelL.setForeground(Color.GRAY);
			this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );

			this.panel.add( labelL );
		}


		SpinnerModel spinnerModel;

		// Vérification du type de la zone de texte
		InputMap im;
		switch (type)
		{
			case Int:
				spinnerModel	= new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
				this.compo		= new JSpinner(spinnerModel);
				im = ((JSpinner)(this.compo)).getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
				im.put(KeyStroke.getKeyStroke("DOWN"), "none");
				im.put(KeyStroke.getKeyStroke("UP"), "none");
				break;

			case Double:
				spinnerModel	= new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0.1f);
				this.compo		= new JSpinner(spinnerModel);
				im = ((JSpinner)(this.compo)).getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
				im.put(KeyStroke.getKeyStroke("DOWN"), "none");
				im.put(KeyStroke.getKeyStroke("UP"), "none");
				break;

			case String:
				this.compo		= new JTextField();
				break;

			case Char:
				this.compo		= new JTextFieldLimit(1);
				break;
		}


		/* Ajout du focus listener */

		JTextField toFocus = null;
		if (this.compo instanceof JSpinner)
		{
			DefaultEditor editor = (DefaultEditor) ((JSpinner) this.compo).getEditor();
			toFocus = editor.getTextField();
		}
		else
		{
			toFocus = ((JTextField) this.compo);
		}
		toFocus.addKeyListener( new TextKeyListener(this) );
		this.history.add( toFocus.getText() );

		// Création du focus listener
		toFocus.addFocusListener( new FocusListener() {
			public void focusGained (java.awt.event.FocusEvent e) {}

			public void focusLost (java.awt.event.FocusEvent e)
			{
				if 		(e.getSource() instanceof JTextField)
				{
					JTextField	src = (JTextField) e.getSource();
					String		str	= src.getText();

					if ( type != BaseType.String )
						str = str.replaceAll("[^0-9\\-]","");

					addToHistory(str);
				}
			}
		});


		this.compo.setPreferredSize( new Dimension(width, (int) (this.panel.getSize().height - (Control.DFLT_HEIGHT / 5f))) );
		this.panel.add( compo );
	}

	/**
	 * Crée un objet Texte, qui comprend un label et une zone de texte avec laquelle il est possible d'interargir
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param type Type associé à l'élément
	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	public Text (String label, String id, BaseType type, int x, int y)
	{
		this(label, id, type, Control.DFLT_WIDTH, x, y);
	}

	/**
	 * Crée un objet Texte, qui comprend une zone de texte avec laquelle il est possible d'interargir
	 * @param id Identifiant unique de l'élément
	 * @param type Type associé à l'élément
	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	public Text (String id, BaseType type, int width, int x, int y)
	{
		this( null, id, type, width, x, y );
	}


	/**
	 * Ajoute à l'historique des actions
	 */
	public void addToHistory (String toAdd)
	{
		if ( this.history.size() == 0 || !toAdd.equals(this.history.getLast()) )
			this.history.add( toAdd );
	}

	/**
	 * Rétablit la valeur précédente du contenu de l'élément
	 */
	public void revert ()
	{
		String value;
		if ( this.history.size() == 1 )	value = this.history.getLast();
		else							value = this.history.pollLast();

		this.setValue( this.history.getLast() );
	}


	/**
	 * Réinitialise l'élément, le retournant au même état que lors de sa création
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void reset ()
	{
		if (this.compo instanceof JTextField)
		{
			JTextField field = (JTextField) (this.compo);
			field.setText( baseValue );
		}
		else
		{
			JSpinner field = (JSpinner) (this.compo);
			field.setValue(0);
		}
	}

	/**
	 * Retourne la valeur contenue dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public Object getValue ()
	{
		switch (type)
		{
			case Int:
				return (Integer) (((JSpinner) (compo)).getValue());
			case Double:
				return (Double) (((JSpinner) (compo)).getValue());
			case String:
				return ((JTextField) (compo)).getText();
			case Char:
				return ((JTextField) (compo)).getText();
			default:
				return null;
		}
	}

	/**
	 * Modifie la valeur associée à l'élément
	 * @param newValue La nouvelle valeur associée à l'élément du formulaire
	 */
	@Override
	public boolean setValue (Object newValue)
	{
		if		(this.type == BaseType.Int)
		{
			if (newValue == null)	newValue = 0;
			try
			{
		 		newValue = new Integer( Integer.parseInt(newValue.toString()) );
		 		((JSpinner) (this.compo)).setValue( newValue );
			}
			catch (Exception e) { }
		}
		else if (this.type == BaseType.Double)
		{
			if (newValue == null)	newValue = 0;
			try
			{
		 		newValue = new Double( Double.parseDouble(newValue.toString()) );
		 		((JSpinner) (this.compo)).setValue( newValue );
			}
			catch (Exception e) { }
		}
		else
		{
			if (newValue == null)
			{
				if (this.type == BaseType.String)	newValue = "";
				else								newValue = Character.MIN_VALUE;
			}
			((JTextField) (this.compo)).setText( newValue.toString() );
		}
		return false;
	}
}
