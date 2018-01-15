package iut.algo.form.job;

/**
 * Enumération des types de base gérés par le formulaire, accompagnés
 * de leur nom dans les langues gérées par le programme
 * @author Team Infotik
 * @version 2018-01-08
 */
public enum BaseType
{
	Integer 		(	"Entier",
					"Integer" ),
	String		(	"Chaine",
					"String" ),
	Double		(	"Double",
					"Double" ),
	Boolean		(	"Booléen",
					"Boolean" ),
	Char		(	"Caractère",
					"Character" );

	private String frValue;
	private String enValue;

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

	public String getValue (Language lang)
	{
		switch (lang)
		{
			case FR:	return this.getFrValue();
			case EN:	return this.getEnValue();
		}
		return null;
	}


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
