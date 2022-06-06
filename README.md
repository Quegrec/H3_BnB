# Rent

apk : https://www.transfernow.net/dl/20220606nQsI67vy

# Sommaire : 
1) Présentation du projet 
2) Les fonctions
    - Liste des fonctions
    - Description détaillée de l'ensemble des fonctions
3) Le modèle des données

# Présentation du projet
L'objectif est de réaliser une application mobile permetta,t d'offrir des services de localisation saisonnières (par exemple maison au bord de la mer, chalet à la montagne ...) entre particuliers.
L'application nécessitera un compte pour effectuer toute action.

# Les fonctions - Liste des fonctions 
**Pour les clients**
- Rechercher un bien
- Effectuer une réservation
- Laisser un commentaire 
- Echanger avec un loueur (poser une questions, obtenir des informations complémentaires)

**Pour les loueur**
- Mettre à disposition un ou plusieurs biens différents
- Gérer les demandes de réservations
- Avoir un relevé des gains visible dans l'application
- Echanger avec le locataire

**Processus de réservation**
- Le locataire effectue une demande de réservation avec une date de début, une date de fin et un nombre de voyageurs. Cela calcule automatiquement le montant (nbr jours * prix journalier + frais). Les frais sont de 3€/voyageur par jour de réservation, ces frais ne reviendront pas au loueur mais au gestionnaire de la plateforme.
- Le loueur voit s'afficher une demande de réservation dans son interface, il peut soir la valider soit la refuser.

# Les fonctions - Description détaillée de l’ensemble des fonctions

**Un visiteur  doit pouvoir se connecter pour confirmer son identité et ainsi avoir accès a l'application.**

- Nom cas d'utilisation : Connexion 
- Pré conditions : Aucun
- Post condition : L’utilisateur est authentifié
- Scénario normal : 
  - La page de connexion d’affiche 
  - L’utilisateur saisit des champs et valide 
  - La page d’accueil s’affiche avec les pages accessibles
- Exceptions:
  - L’utilisateur commet une erreur sur la saisie de l’identifiant ou du mot de passe
  - Le système en informe l’utilisateur, retour à l'étape 2

![connexion](https://user-images.githubusercontent.com/78727838/172208005-ca951bb9-699f-49ba-9b37-e62bb2766390.jpg)

**Un nouvelle utilisateur doit pouvoir créer un compte**

- Nom cas d'utilisation : Créer un compte 
- Pré conditions : Aucun
- Post condition : Un nouvelle utilisateur est créer
- Scénario normal : 
  - L’utilisateur se rend sur la page de connexion 
  - L’utilisateur clique sur le bouton d'inscription
  - L’utilisateur peut alors remplir les champs
  - Le système contrôle la validité des champs saisis 
  - Retour sur la page de connexion
- Exceptions : 
  - L’administrateur commet une erreur sur la saisie d’un ou plusieurs champs 
  - Le système en informe l’utilisateur, retour à l'étape 3

![cree](https://user-images.githubusercontent.com/78727838/172208309-8d46331d-d93f-4e00-9891-f6dd53e7cde0.jpg)

![cree2](https://user-images.githubusercontent.com/78727838/172208335-2758b839-624a-493d-824c-d97da3651c91.jpg)


**Un visiteur doit voir afficher la list des biens lorsqu’il est sur la page d’accueil de l'application**

- Nom cas d'utilisation : Consultation des thèmes 
- Pré conditions : Aucun
- Post condition : List des thèmes 
- Scénario normal : 
  - L’utilisateur arrive sur la page d’accueil  
  - La liste des biens s’affiche 

![homepage](https://user-images.githubusercontent.com/78727838/172209883-b5adcbd6-64a0-4529-a6db-7c88e1a67686.jpg)

**Un visiteur a la possibilité d'afficher la page détail d’un thème**

- Nom cas d'utilisation : Afficher des détails d’un bien
- Pré conditions : Aucun
- Post condition : Les détails du bien sont affichés
- Scénario normal :  
  - L’utilisateur clique sur le bien permettant d’affichage des détails 
  - Une nouvelle page s’affiche avec le détail du thème choisi

![etaille](https://user-images.githubusercontent.com/78727838/172212694-f1b8d547-eacf-4666-bfe5-7accb8659969.jpg)

![detaomme](https://user-images.githubusercontent.com/78727838/172212729-871c4905-1926-4695-a105-4a384bf5ec16.jpg)


**Un utilisateur doit avoir la possibilité d’ajouter un bien.**

- Nom cas d'utilisation : Ajouter un bien
- Pré conditions : Être authentifié
- Post condition : Le bien est ajouté
- Scénario normal : 
  - L'utilisateur se rend sur la page Utilisateur
  - L'utilisateur clique sur le button ajouter un bien
  - L'utilisateur saisit les champs et valide
  - Le système contole la validité des champs
  - Retour sur la page d'accueil 
- Exceptions : 
  - L’administrateur commet une erreur sur la saisie d’un ou plusieurs champs
  - Le système en informe l’utilisateur, retour à l'étape 3

![cree](https://user-images.githubusercontent.com/78727838/172213562-c9f3c4ae-637d-416c-96d4-58a876a2a247.jpg)

![cree2](https://user-images.githubusercontent.com/78727838/172213576-63a5d65e-8a94-4371-b968-c442b53c71ac.jpg)


**Un loueur doit avoir la possibilité de modifier son bien**

- Nom cas d'utilisation : Modifier un bien
- Pré conditions : Être a l'origine du bien 
- Post condition : Le bien est modifié
- Scénario normal : 
  - Le loueur se rend sur la page detaillé du bien
  - Le loueur clique sur le bouton de modification  
  - Le loueur peut alors modifier les champs souhaités et valider
  - Le système contrôle la validité des champs modifiés
  - Retour au descriptif du thème 
- Exceptions :
  - L’utilisateur commet une erreur sur la modification d’un ou plusieur champs
  - Le système en infirme d’utilisateur, retour à l'étape 3

![modifier](https://user-images.githubusercontent.com/78727838/172214175-a68369d3-8304-4142-8acc-cd2b824f1836.jpg)

![odifier2](https://user-images.githubusercontent.com/78727838/172214195-b3da30b8-b787-4046-8f8a-09d2e9335f0c.jpg)


**Un loueur doit avoir la possibilité de supprimer un bien**

- Nom cas d'utilisation : Supprimer un bien
- Pré conditions : Être a l'origine du bien 
- Post condition : Le bien est supprimé
- Scénario normal : 
  - Le loueur se rend sur la page detaillé du bien
  - Le loueur peut alors cliquer sur le bouton de suppression
  - Retour à la page d’accueil

![supprimer](https://user-images.githubusercontent.com/78727838/172214895-2508876f-3d03-4f81-bc09-7d330041a8f2.jpg)


**Un utilisateur doit avoir la possibilité de réserver un bien une date et un lieu.**

- Nom cas d'utilisation : Réserver un bien
- Pré conditions : Être identifier comme utilisateur
- Post condition : Un bien est réservé
- Scénario normal : 
  - L'utilisateur se rend sur la page detaillé du bien
  - L'utilisateur clique sur le button de réservation
  - L’utilisateur remplie de formulaire de réservation 
  - Le système contrôle la validité des champs saisis
  - Redirection vers la page des commandes
- Exceptions : 
  - L’utilisateur commet une erreur sur la saisie d’un ou plusieurs champs
  - Le système en informe l’utilisateur, retour à l'étape 3

![reserver](https://user-images.githubusercontent.com/78727838/172216609-22e63f07-cfca-4f6b-873b-a2ef9d321054.jpg)

![reserver2](https://user-images.githubusercontent.com/78727838/172216620-f157a9ef-0127-4a72-ab28-68bedf899aa2.jpg)


**Un loueur doit avoir la possibilité de voire la liste de tous les biens réservé**

- Nom cas d'utilisation : Consulter la list des biens réservé
- Pré conditions :  Être identifier
- Post condition : Voir la liste des biens réservé
- Scénario normal : 
  - L’administrateur clique sur le bouton dans la barre de navigation
  - Le système redirige le loueur sur le page des réservation future

![aVenir](https://user-images.githubusercontent.com/78727838/172217035-c67863e9-b92e-44a1-bab1-770e33357c80.jpg)



**Un loueur doit avoir un relevé des gains visible dans l'application**

- Nom cas d'utilisation : Consulter le relevé des gains
- Pré conditions :  Être identifier
- Post condition : Voir le relevé des gains
- Scénario normal : 
  - Le loueur clique sur le bouton dans la barre de navigation pour se rendre sur la page user
  - Le loueur clique sur le bouton des gains
  - Le systeme affiche les gains

![compte](https://user-images.githubusercontent.com/78727838/172218005-9e020927-6ede-4481-9f94-fe211910d2d1.jpg)

**Les utilisateurs doivent avoir la possibilité de communiquer entre eux**

- Nom cas d'utilisation : Envoyer des messages 
- Pré conditions :  Être identifier
- Post condition : Message envoyé
- Scénario normal : 
  - L'utilisateur clique sur le bouton dans la barre de navigation pour se rendre sur la page des messages
  - L'utilisateur choisi avec que contact echanger
  - L'utilisateur peut écrir et envoyer un message

![essage](https://user-images.githubusercontent.com/78727838/172218813-6f412294-3374-48aa-adb5-407862a7d9f4.jpg)


**L’utilisateur doit pouvoir se déconnecter s’il le souhaite**

- Nom cas d'utilisation : Déconnexion
- Pré conditions :  Être authentifier
- Post condition : L’utilisateur est déconnecté
- Scénario normal : 
  - L’utilisateur à la possibilité de clique sur un bouton qui permet de se déconnecter dans la page user
  - Si l’utilisateur choisit de se déconnecter, il clique sur le bouton
  - L’utilisateur est alors déconnecter
  - La page connexion

![deconnexion](https://user-images.githubusercontent.com/78727838/172217227-be060ec8-b39b-4939-bf5c-d6ff9e0ff7f2.jpg)


# Les données - Le modèle des données 
