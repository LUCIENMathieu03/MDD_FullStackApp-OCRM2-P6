# MDD API-OCRM2-P6

Frontend et API Pour l'application le monde des dev (MDD) développée en Java et Spring Boot.

## Pré-requis

- **Angular 14 minimum**
- **Java 21**
- **Maven 3.8+**
- **MySQL** (version 8.x conseillée) installé et fonctionnel sur ta machine
- **Git**

## 1. Cloner le projet depuis GitHub

Ouvre un terminal, puis :

```
git clone https://github.com/LUCIENMathieu03/MDD_FullStackApp-OCRM2-P6.git
```

## 2. Installer le projet

Se rendre dans le dossier front puis installer:

```
npm install
```

Se rendre dans le dossier Back puis installer:

```
mvn clean install
```

## 3. Configuration de l’environnement (.env)

Crée un fichier nommé `.env` à la racine du projet avec le contenu suivant :

```
DB_USERNAME=ton_nom_utilisateur_bdd
DB_PASSWORD=ton_mot_de_passe_bdd
DB_URL=jdbc:mysql://localhost:xxxx/nom_de_la_base_de_donnees
JWT_SECRET=une_chaine_de_caracteres_secrete_longue
SERVER_PORT=3001
BASE_URL=http://localhost:3001
```

| Variable      | Description                                                                                                 |
| ------------- | ----------------------------------------------------------------------------------------------------------- |
| `DB_USERNAME` | Identifiant de connexion à la base de données MySQL.                                                        |
| `DB_PASSWORD` | Mot de passe de connexion à la base de données MySQL.                                                       |
| `DB_URL`      | URL de connexion JDBC vers la base de données MySQL.<br>Exemple : `jdbc:mysql://localhost:3306/rentalocrm2` |
| `JWT_SECRET`  | Clé secrète utilisée pour signer et vérifier les tokens JWT (garde-la confidentielle !).                    |
| `SERVER_PORT` | Port sur lequel le serveur backend écoute (ex. : `3001` pour accès à `http://localhost:3001`).              |
| `BASE_URL`    | URL de base de l’API (doit correspondre au port configuré dans `SERVER_PORT`).                              |

**Important** : Ne commit jamais ce fichier `.env` dans le dépôt Git, car il contient des informations sensibles.

## 4. Installer et lancer l’API localement

1. Vérifie les versions installées :

```
java -version
mvn -version
```

Assure-toi que Java 21 et Maven 3.8 ou plus sont installés.

2. Compile et installe les dépendances du projet :

```
mvn clean install
```

3. Démarre l’application :

```
mvn spring-boot:run
```

4. L’API sera disponible à :  
   [http://localhost:3001](http://localhost:3001) (si tu utilise le port 3001)

## 5. Documentation de l’API

La documentation interactive Swagger UI est accessible ici quand l'api est lancé:  
[http://localhost:3001/swagger-ui.html](http://localhost:3001/swagger-ui.html) (si tu utilise le port 3001)

## 6. Notes supplémentaires

- Gère tes secrets (comme JWT_SECRET, DB_PASSWORD) avec précaution.
- Pour toute modification, commits et push, vérifie que `.env` n’est jamais ajouté.
- En cas de questions ou d’aide, n’hésite pas à ouvrir une issue sur GitHub.

---
