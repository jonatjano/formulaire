<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html>
			<head>
				<link rel="stylesheet" href="preview.css"/>
				<script>
					<!-- le timeout est nécessaire car le xsl prend un peu de temps à générer la page -->
					window.setTimeout(function()
					{
						<!-- on recupere la date d'aujourd'hui -->
						var today = new Date();
						<!-- on recupère tous les objets date -->
						var listDate = document.getElementsByClassName("date");
						for(var i = 0; i != listDate.length; i++)
						{
							<!-- on leur met comme valeur la date en enlevant la fin qui correspond au fuseau horraire -->
							listDate[i].value = today.toJSON().slice(0, 10);
						}
					}, 100)
				</script>
				<xsl:apply-templates select="form/fenetre" mode="head"/>
				<xsl:apply-templates select="form/window" mode="head"/>
			</head>
			<body>
				<xsl:apply-templates select="form/fenetre" mode="body"/>
				<xsl:apply-templates select="form/window" mode="body"/>
			</body>
		</html>
	</xsl:template>

	<!-- ajout du style position : absolute si tous les elements ont une coordonnée x et y -->
	<xsl:template match="window|fenetre" mode="head">
		<xsl:if test="count(*[@x]) = count(*) and count(*[@y]) = count(*)">
			<style>
				.element
				{
					position : absolute
				}
			</style>
			<title><xsl:value-of select="./@titre"/><xsl:value-of select="./@title"/></title>
		</xsl:if>
	</xsl:template>


	<xsl:template match="window|fenetre" mode="body">
		<div id="mainDiv" style="top: {@y}px; left: {@x}px; height: {@longueur}{@length}px; width: {@largeur}{@width}px;">
			<xsl:apply-templates/>
		</div>
	</xsl:template>


	<xsl:template match="label">
		<div class="element" style="top: {@y}px; left: {@x}px; height: {@longueur}{@length}px; width: {@largeur}{@width}px;">
			<xsl:value-of select="./@label"/>
		</div>
	</xsl:template>


	<xsl:template match="texte|text">
		<div class="element" style="top: {@y}px; left: {@x}px; height: {@longueur}{@length}px; width: {@largeur}{@width}px;">
			<!-- <xsl:if test="@type=entier or @type=int"> -->
				<xsl:value-of select="./@label"/> : <input type="text"/>
			<!-- </xsl:if> -->
		</div>
	</xsl:template>


	<xsl:template match="menu|dropdown">
		<div class="element" style="top: {@y}px; left: {@x}px; height: {@longueur}{@length}px; width: {@largeur}{@width}px;">
			<xsl:apply-templates select="@label"/> :
			<select name="{@label}">
				<xsl:apply-templates select="choice"/>
			</select>
		</div>
	</xsl:template>

	<xsl:template match="choice">
			<option><xsl:value-of select="@label"/></option>
	</xsl:template>

	<xsl:template match="case|checkbox">
		<div class="element" style="top: {@y}px; left: {@x}px; height: {@longueur}{@length}px; width: {@largeur}{@width}px;">
			<xsl:value-of select="./@label"/> : <input type="checkbox"/>
		</div>
	</xsl:template>


	<xsl:template match="tableau|array">
		<div class="element" style="top: {@y}px; left: {@x}px; height: {@longueur}{@length}px; width: {@largeur}{@width}px;">
			<xsl:value-of select="./@label"/>
			<xsl:variable name="type"><xsl:value-of select="@type"/></xsl:variable>
			<xsl:value-of select="$type"/>
			<table>
				<tr>
					<td>4</td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<xsl:choose>
						<xsl:when test="$type='boolean' or $type='booleen'">
							<td>Valeur : <input type="checkbox"/></td>
						</xsl:when>
						<xsl:otherwise>
							<td>Valeur : <input type="text"/></td>
						</xsl:otherwise>
					</xsl:choose>
				</tr>
				<tr>
					<td>3</td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
				</tr>
				<tr>
					<td>2</td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
				</tr>
				<tr>
					<td>1</td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
				</tr>
				<tr>
					<td>0</td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
					<td><input type="button" name="{@label}" value=" "/></td>
				</tr>
				<tr>
					<td></td>
					<td>&#160;&#160;0</td>
					<td>&#160;&#160;1</td>
					<td>&#160;&#160;2</td>
					<td>&#160;&#160;3</td>
					<td>&#160;&#160;4</td>
				</tr>
			</table>
		</div>
	</xsl:template>


	<xsl:template match="boutons|buttons">
		<div class="element" id="RADIO" style="top: {@y}px; left: {@x}px; height: {@longueur}{@length}px; width: {@largeur}{@width}px;">
			<xsl:value-of select="./@label"/>
			<table>
				<xsl:apply-templates select="button">
					<xsl:sort select="@ordinal"/>
				</xsl:apply-templates>
			</table>
		</div>
	</xsl:template>

	<xsl:template match="button|bouton">
		<tr>
			<td><xsl:value-of select="."/></td>
			<td><input type="radio" name="{../@label}"/></td>
		</tr>
	</xsl:template>

	<xsl:template match="calendrier|calendar">
		<div class="element" style="top: {@y}px; left: {@x}px; height: {@longueur}{@length}px; width: {@largeur}{@width}px;">
			<xsl:value-of select="./@label"/> :
			<input class="date" type="date"/>
		</div>
	</xsl:template>

</xsl:stylesheet>
