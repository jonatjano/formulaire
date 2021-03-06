package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;

import java.awt.Dimension;
import java.awt.Color;

import iut.algo.form.job.Language;

/**
 * Liste déroulante à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Dropdown extends Control
{
	/** Label décrivant le contôle à l'utilisateur */
	private JLabel 		labelL;
	/** Valeur de l'élément lors de sa création */
	private Object[]	baseValues;


	/**
	 * Création d'une liste déroulante, que l'utilisateur peut éditer à sa guise pendant
	 * l'utilisation du formulaire, et forcément associée au type chaine
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @param choices Valeurs d'origine associées à l'élément lors de sa création
	 * @param language le langage utilisé par le formulaire
	 */
	@SuppressWarnings("unchecked")
	public Dropdown (String label, String id, int width, int x, int y, Object[] choices, Language language)
	{
		super(label, id, BaseType.String, width, x, y, language);
		this.type 		= type;
		this.baseValues = choices;


		/* Création de la liste déroulante */

		this.labelL		= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground(Color.GRAY);
		this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );
		this.compo	= new JComboBox(choices);
		this.compo.setPreferredSize( new Dimension(width, (int) (this.panel.getSize().height - (Control.DFLT_HEIGHT / 5f))) );
		((JComboBox)this.compo).setEditable(true);


		this.panel.add( labelL );
		this.panel.add( compo );
	}

	/**
	 * Création d'une liste déroulante, que l'utilisateur peut éditer à sa guise pendant
	 * l'utilisation du formulaire, et forcément associée au type chaine
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @param choices Valeurs d'origine associées à l'élément lors de sa création
	 * @param language le langage utilisé par le formulaire
	 */
	public Dropdown (String label, String id, int x, int y, Object[] choices, Language language)
	{
		this(label, id, Control.DFLT_WIDTH, x, y, choices, language);
	}


	/**
	 * Réinitialise l'élément, le retournant au même état que lors de sa création
	 */
	@Override
	public void reset ()
	{
		// Itère à travers tous les éléments pour rétablir leur valeur initiale
		for (int i = 0; i < ((JComboBox) this.compo).getItemCount(); i++)
		{
			Object obj = ((JComboBox) this.compo).getItemAt(i);
			obj = baseValues[i];
		}

		// Enfin, la première valeur est celle mise en valeur
		((JComboBox) this.compo).setSelectedIndex(0);
	}

	/**
	 * Retourne la valeur contenue dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public String getValue ()
	{
		Object item = ((JComboBox) this.compo).getSelectedItem();

		if (item == null)	return null;
		else				return item.toString();
	}

	/**
	 * Modifie la valeur associée à l'élément
	 * @param newValue La nouvelle valeur associée à l'élément du formulaire
	 * @return vrai si la valeur a été changée
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean setValue (Object newValue)
	{
		// on s'assure que newValue est un tableau à une dimension
		if (newValue != null && newValue.getClass().isArray() && !((Object[])(newValue))[0].getClass().isArray())
		{
			Object[] valuesToSet	= (Object[]) newValue;
			this.baseValues 		= valuesToSet;

			((JComboBox) this.compo).setModel( new DefaultComboBoxModel(valuesToSet) );
			return true;
		}
		return false;
	}
}
