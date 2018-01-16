package iut.algo.form.job;

/**
 * Enumération des types de base gérés par le formulaire, accompagnés
 * de leur nom dans les langues gérées par le programme
 * @author Team Infotik
 * @version 2018-01-08
 */
public enum BaseType
{
	/**
	 * Correspond aux type entier
	 */
	Integer 		(	"Entier",
					"Integer" ),
					
	/**
	 * Correspond aux type chaine de caractère
	 */
	String		(	"Chaine",
					"String" ),
					
					
	/**
	 * Correspond aux type double
	 */
	Double		(	"Double",
					"Double" ),
					
					
	/**
	 * Correspond aux type booleen
	 */
	Boolean		(	"Booléen",
					"Boolean" ),
					
					
	/**
	 * Correspond aux type caractère
	 */
	Char		(	"Caractère",
					"Character" );

	/**
	 * nom en français
	 */
	private String frValue;
	/**
	 * nom en anglais
	 */
	private String enValue;

	/**
	 * créé l'instane de BaseType
	 * @param frValue nom en français
	 * @param enValue nom en anglais
	 */
	private BaseType (String frValue, String enValue)
	{
		this.frValue = frValue;
		this.enValue = enValue;
	}

	/**
	 * Renvoie la valeur française de l'énum d'un type de base
	 * @return Le nom français du type de base
	 */
	public String getFrValue ()
	{
		return this.frValue;
	}

	/**
	 * Renvoie la valeur anglaise de l'énum d'un type de base
	 * @return Le nom anglais du type de base
	 */
	public String getEnValue ()
	{
		return this.enValue;
	}

	/**
	 * return la valeur du type pour la langue demandée
	 * @param  lang langage de la valeur à retourner
	 * @return le nom dy type dans la langue demandée
	 */
	public String getValue (Language lang)
	{
		switch (lang)
		{
			case FR:	return this.getFrValue();
			case EN:	return this.getEnValue();
		}
		return null;
	}

	/**
	 * retourne le type en fonction de son nom
	 * @param  type le nom du type à retourner en français ou en anglais
	 * @return le type demandé ou null s'il n'existe pas
	 */
	public static BaseType getBaseType (String type)
	{
		switch(type)
		{
			case "int":
			case "entier":
				return BaseType.Integer;
			case "string":
			case "chaine":
				return BaseType.String;
			case "double":
				return BaseType.Double;
			case "boolean":
			case "booleen":
				return BaseType.Boolean;
			case "char":
			case "caractere":
				return BaseType.Char;
		}

		return null;
	}
}
