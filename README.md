# FACULTÉ POLYDISCIPLINAIRE À LARACHE
# Projet de Fin de Module Développement Mobile_Aiha Fraoua DCC0027/24
Réalisé par : Aiha Fraoua DCC0027/24

Encadré par : Pr. KOUISSI Mohamed

# 🦋 Petit Papillon :
Le dépôt contient l’application mobile Petit Papillon, développée en Kotlin avec Jetpack Compose. Il s’agit d’une application e-commerce moderne et intuitive, dédiée à la vente de vêtements et d’accessoires pour bébés, aussi bien pour les garçons que pour les filles.
L’interface a été conçue pour être douce, accueillante et épurée, mettant en valeur les articles pour nourrissons dans un environnement chaleureux.
L’application suit l’architecture MVI (Model - View - Intent), garantissant une meilleure séparation des responsabilités, une gestion fluide des états, ainsi qu’une structure de code claire et maintenable.

# 🛠️  Technologies utilisées
Kotlin

Jetpack Compose

Architecture MVI

Gradle
# 📸 Aperçu de l'application
# 📱 Écran de chargement – Petit Papillon
L’écran de chargement constitue le premier contact visuel entre l’utilisateur et l’application Petit Papillon. Il met en avant l’identité visuelle de la marque à travers des animations fluides telles que le fondu et la pulsation, réalisées avec Jetpack Compose.
Pendant que l’application se prépare en arrière-plan, des images et slogans inspirants défilent pour offrir une expérience douce, moderne et engageante.
À l’issue de cette animation, l’utilisateur est automatiquement redirigé vers l’interface principale, assurant une transition fluide et sans attente.

 ==>Succession d’images illustrant les catégories de produits (vêtements, jouets, accessoires).

![image](https://github.com/user-attachments/assets/7c6a7561-296f-4f49-b372-7034ab13d42d) ![image](https://github.com/user-attachments/assets/5d40bce1-c570-456a-ad73-28e7895e473f)

# 🏠 Page d’accueil – Petit Papillon

La page d’accueil de Petit Papillon est le point d’entrée principal de l’application, pensée pour offrir une expérience fluide, intuitive et visuellement agréable aux jeunes parents.
L’en-tête affiche le logo et le nom de l’application sur un fond violet dégradé, avec une icône de langue pour gérer le multilingue.
Elle est suivie d’une barre de recherche et d’un menu horizontal de catégories (Bébés, Garçons, Filles, Chaussures…) illustré par des icônes arrondis.
Les produits en promotion sont présentés sous forme de cartes contenant image, nom, prix réduit, ancien prix barré, étiquette "Promo -%", note en étoiles, et bouton "Voir détails".
Enfin, une barre de navigation inférieure permet d’accéder rapidement aux sections clés : Accueil, Favoris, Panier, Offres et Profil.
L’ensemble garantit une navigation simple et une expérience d’achat agréable.

![image](https://github.com/user-attachments/assets/31fef41f-f455-4453-8072-09267c88361e) ![image](https://github.com/user-attachments/assets/852533ac-a8a0-45c1-80ab-3c31d2855485)

 ==>Une fois que l’utilisateur clique sur l’icône de langue, une liste des langues s’affiche pour lui permettre de changer la langue des d’application, présentée de manière simple et élégante.
 
 ![image](https://github.com/user-attachments/assets/1e1836c4-814e-4ef8-ae7e-bb53644ef81a)
 
 ==>Voila Application en English
 
 ![image](https://github.com/user-attachments/assets/4c9ac8c8-7405-48b2-9440-05c3e5e4ce8d)
 
 ==>Lorsqu’un utilisateur clique sur une catégorie spécifique, telle que « Garçon», « chaussures», la page affiche automatiquement tous les produits appartenant à ce type
 
 ![image](https://github.com/user-attachments/assets/9d74e002-b26a-4d2d-b310-745e4ab6f098) ![image](https://github.com/user-attachments/assets/4c6bfa73-2a2e-4baf-82c5-a2f03a14bfa5)
 
==>Lorsqu’une recherche est effectuée par titre par, par exemple «Puzzle », ou par catégorie, par exemple « Pyjamas »
La page affiche automatiquement tous les produits correspondant à ce critère.

 ![image](https://github.com/user-attachments/assets/e7119140-2988-4d46-8aa6-cc05da6a5c4a) ![image](https://github.com/user-attachments/assets/1aca77f0-dd35-449a-a0ce-3299d7932577)

==>	Lorsqu’un utilisateur clique sur le bouton ‘Voir Details’’ d’un produit, une page ou une section s’ouvre affichant toutes les informations détaillées du produit, notamment le titre, le prix, la tailles disponible ainsi qu’une description complète.

![image](https://github.com/user-attachments/assets/d77ba792-bb3f-4d63-8f19-4714d6fdd322)

==>Sur la page de détails d’un produit, le bouton « Ajouter au panier » est désactivé par défaut. Il devient actif automatiquement dès que l’utilisateur sélectionne une taille.
En cliquant dessus, le produit est ajouté au panier et le compteur de l’icône panier s’incrémente instantanément, assurant une interaction claire, fluide et intuitive.

![image](https://github.com/user-attachments/assets/3ef7a78a-1ec8-4bf4-bdcd-c6ad3364b2b7) ![image](https://github.com/user-attachments/assets/fc49ccf3-e9ec-4620-aee0-6eca2d6c64e7)

# page d'inscription
Pour accéder à toutes les fonctionnalités, l’utilisateur non connecté doit remplir un formulaire d’inscription contenant : prénom, nom, email, mot de passe et rôle (client, administrateur ou agent).Une fois complété, il peut valider en cliquant sur « S’inscrire ».

![image](https://github.com/user-attachments/assets/0f62fe7d-bc56-4511-9783-252ee08981a0)

==>Si le mot de passe n’est pas confirmé ou que le numéro de téléphone est invalide, le champ concerné est mis en surbrillance en rouge et le bouton « S’inscrire » reste désactivé jusqu’à correction.Si l’utilisateur existe déjà, un message d’erreur s’affiche.

![image](https://github.com/user-attachments/assets/66ca3791-7342-4472-a49d-69567dc5405c) ![image](https://github.com/user-attachments/assets/12bdf609-227e-4896-a1e7-481c73a0acfe)

#  Page de connexion
Sur cette page, lorsque l’utilisateur clique sur l’icône de profil, il est automatiquement redirigé vers la page d’authentification. Celle-ci propose un formulaire de connexion. S’il possède déjà un compte, il lui suffit de saisir son adresse e-mail et son mot de passe, puis de cliquer sur « Se connecter » pour accéder à son espace personnel. S’il n’est pas encore inscrit, il peut cliquer sur « S’inscrire », ce qui le redirige vers une nouvelle page contenant un formulaire de création de compte. 

![image](https://github.com/user-attachments/assets/0871ec98-d8fa-46ec-b576-36a9882072b1)

==>En cas d’erreur dans l’adresse email ou le mot de passe, un message d’alerte s’affiche pour informer l’utilisateur que les informations saisies sont incorrectes.

![image](https://github.com/user-attachments/assets/ad48ad2b-d79a-4287-86c3-15af76c5a147)

# ❤️ Page des favoris
Cette page affiche l’ensemble des produits que l’utilisateur a ajoutés. Chaque produit est présenté sous forme de carte contenant une image, un titre, un prix, la quantité disponible en stock, ainsi qu’une icône de suppression (corbeille) et une icône permettant d’ajouter l’article au panier.

![image](https://github.com/user-attachments/assets/c0330526-5932-4e22-a53f-eb8c4603447b)

# 🛍️ Page des promotions
Cette page affiche l’ensemble des produits que l’utilisateur a ajoutés à son panier, avec tous les détails correspondants.
 L’utilisateur peut gérer la quantité de chaque produit qu’il souhaite acheter, ce qui met automatiquement à jour le montant total à payer.
Un bouton est également disponible pour permettre la suppression d’un produit du panier.

![image](https://github.com/user-attachments/assets/ce658cdf-8629-4ed3-8b36-140529d4c5ac)

# 🛒 Page du panier
Cette page affiche tous les produits en promotion avec leur taux de réduction bien visible.
Chaque carte produit présente : image, nom, ancien prix barré, prix remisé, note en étoiles, et un bouton « Voir détails » pour plus d’informations.

![image](https://github.com/user-attachments/assets/4915b905-a416-4515-ac1f-0b90b4c92bfb)

==>	Pour finaliser votre commande, veuillez sélectionner au moins un article en cochant la case correspondante. Vous devez également vous connecter à votre compte ou en créer un si vous n’en avez pas. 
« L’authentification est obligatoire pour sécuriser vos informations et valider la commande avec succès. »

![image](https://github.com/user-attachments/assets/65179032-47e3-4562-bc02-6e91a369bb1a)

#📦 Formulaire de commande
Après authentification, l’utilisateur est redirigé vers un formulaire de commande.Il doit renseigner : nom, e-mail, adresse de livraison, et mode de paiement (carte, PayPal, etc.).
Une fois les champs obligatoires remplis, il clique sur « Continuer » pour valider la commande.

![image](https://github.com/user-attachments/assets/d1645a0a-4018-4503-be8e-190b1ee526b7)

# 💳 Page de paiement
Après avoir choisi le paiement par carte bancaire, l’utilisateur est redirigé vers une page dédiée.Il y renseigne : numéro de carte, date d’expiration et code de sécurité (CVV).Une fois les informations saisies, il clique sur « Valider le paiement » pour finaliser la transaction.

![image](https://github.com/user-attachments/assets/fbfe87c7-8105-4bc6-b9e3-79a12ba734f7)

==>	Enfin, une fois que l’utilisateur clique sur « Valider le paiment » s’affiche.
Ce reçu contient toutes les informations saisies par l’utilisateur ainsi que les détails des produits achetés, incluant les quantités et le montant total payé.

![image](https://github.com/user-attachments/assets/8277b863-abe8-49b6-9e34-e2a467a3b555) ![image](https://github.com/user-attachments/assets/9bcd5461-93c6-442a-bc6d-90497c5f9e7d)

# Comment exécuter l'application

1.Cloner le dépôt :

https://github.com/Aicha2002954/TP_Android_aicha_fraoua.git

