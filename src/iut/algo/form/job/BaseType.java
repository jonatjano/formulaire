package iut.algo.form.job;

public enum BaseType
{
	Int,
	String,
	Double,
	Boolean,
	Char;

	public static BaseType getBaseType(String type)
	{
		switch(type)
		{
			case "int":
			case "entier":
				return BaseType.Int;
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
