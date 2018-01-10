package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import java.awt.Component;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.Date;

/**
 * Zone de texte à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Calendar extends Control
{

	private DateTextField date;

	public Calendar (String label, String id, int width, int x, int y)
	{
		super(label,id,BaseType.String,width,x,y);
		date = new DateTextField();
		this.panel.add(date);
	}

	public Calendar (String label, String id, int x, int y)
	{
		this(label,id, Control.DFLT_WIDTH,x,y);
	}

	@Override
	public void reset ()
	{
		date.setDate(new Date());
	}

	/**
	 * Retourne la valeur contenu dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public String obtainValues ()
	{
		return date.getDate().toString();
	}

}
