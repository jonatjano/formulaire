# Voici ce qu'il faut faire par ordre d'importance
### Legende
- [ ] Je ne suis pas fini
- [x] Je suis fini mais pas testé
- [x] **Je suis fini et testé**

## Primordial
#### [x] Paramétrage du formulaire
- [x] **Le formulaire devra être paramétré dans un fichier texte**
- [x] **Les 5 types de bases devront être gérés :**
	- [x] **int**
	- [x] **boolean**
	- [x] **String**
	- [x] **char**
	- [x] **boolean**
- [x] **L'utilisateur pourra définir plusieurs formulaires et utiliser plusieurs formulaires dans un même programme.**

#### [x] Les différents Contrôles à gérer.
Les différents Contrôles devront posséder :
- [x] **une étiquette associée**
- [x] <u>**un  identifiant numérique, indépendant de l'ordre dans lequel les contrôles sont définis**</u>
- [x] **un positionnement Sans valeur x y les différents Controles seront placées les uns en dessous des autres dans l'ordre de leur identifiant**
- [x] **la définition de la largeur (qui par défaut sera de 150)**
- [x] **la hauteur sera fixe, et correspondra à une hauteur standard**
- [x] **L'association à un des 5 types de base**

#### [x] Les différents Contrôles à gérer sont
- [x] **La zone de texte**
- [x] **Une case à cocher**

#### [x] Un  bouton  valider,  permettra  de  rendre  la main  au  programme  appelant.  Cela  provoquera  la fermeture de la fenêtre liée au formulaire
#### [x] Chargement du formulaire
- [x] **Chargement d'un Objet Formulaire à partir du nom d'un fichier**
- [x] **Ouverture du Formulaire à l'aide d'une primitive simple. Tant  que  le  formulaire  n'aura  pas  été  définitivement  fermé,  l'ouverture affichera le formulaire dans son état précédent.**
- [x] **L'utilisateur  pourra  initialiser  chaque  champ  du  formulaire,  à  l'aide  d'une  primitive spécifique, avant l'ouverture du formulaire.**
- [x] **Fermeture définitive du formulaire**

#### [ ] Documentation
- [ ] Documentation Programmeur Avec une Cartographie du programme, une table des matières par fonctionnalité
- [ ] Documentation Utilisateur

### [x] Rajout
- [x] **Gérer toutes les erreurs XML** :
	- [ ] Type incorrect
	- [x] **Oubli Chevron**
	- [x] **Erreur DTD**

## Important

#### [x] Paramétrage du formulaire
- [x] **Il devra y avoir des règles de nommage pour le fichier**
- [x] **Les éléments du langage et option ne devront pas donner lieu à un long apprentissage, 15 min doivent suffire pour appréhender le langage**

#### [x] Les différents Contrôles à gérer sont :
- [x] <u>**Un groupement de bouton radio, pour lesquels il faudra définir la liste des boutons radio associés, l'utilisateur devra définir un ordinal pour chacun des boutons radio.**</u>
- [x] <u>**Une Liste déroulante éditable, les différentes valeurs de la liste déroulante seront définis par le programme utilisateur à l'aide d'un tableau.**</u>

#### [x] Un bouton effacer, permettra d'effacer l'ensemble des champs de saisie.

#### [x] Cinématique.
- [ ] Naviguez entre les différents Controle

#### [x] Chargement du formulaire
- [ ] Récupération des différentes valeurs à l'aide d'une primitive spécifique pour chaque type de bases. Chaque primitives prendra en argument le nom de l'étiquette ou l'identifiant associé au Contrôle que l'on souhaite récupérer.

#### [ ] Documentation
- [ ] Accompagné d'exemples : formulaire + code java

## Non négligeable

#### [x] Les différents Contrôles à gérer sont :
- [x] **Une zone pour la saisie des tableaux, les dimensions du tableau seront définies par le programme utilisateur. On ne visualisera que 5 cases à la fois.**

#### [x] Cinématique.
- [x] **Pouvoir visualiser l'identifiant des différents Controles.(cf illustration 2)**
- [x] **Pouvoir visualiser les types associés aux différents Controles(cf Illustration 3)**
- [x] **Controle des valeurs pour les champs numériques** // verif zone entier
- [x] **Utilisation d'une barre de défilement pour les formulaires trop grand.C’est-à-dire ne tenant pas dans la zone définie en début de fichier de paramétrage.Nota : les deux boutons valider et effacer seront toujours visible à l'écran**

#### [x] Chargement du formulaire
- [x] <u>**En cas de non chargement l'utilisateur pourra récupérer un message d'erreur du type
Fichier inexistant**</u>

#### [x] Documentation
- [x] **Aide en ligne**

## Ajouté par nous
### Controles
- [x] **label seul**
- [x] **ajout d'une marge de 20-30 sur la gauche des controles**
- [x] **Changement de langue**

## Avenants
### Avenant n°1
- [x] **Définir un Controle Calendrier qui permettra la saisie d'une date à l'aide d'une grille graphique. La date sera stockée sous forme de chaine au format jj/mm/aaaa.**
### Avenant n°2
- [ ] Définir une feuille de style xsl, permettant de prévisualiser un formulaire dans un navigateur.
### Avenant n°3
- [x] **Gérer une historisation des saisies sur les controles zone de texte (référencé par l'étiquette associé au Controle)**
