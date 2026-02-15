# Rapport Technique - Document API

## 1. Introduction

Ce projet consiste a developper une API de traitement documentaire capable d'extraire des informations structurees a partir de documents non structures (PDF ou images). L'objectif est d'automatiser l'extraction de donnees via OCR et intelligence artificielle, puis de stocker les resultats dans une base de donnees exploitable.

Le projet est implemente avec Spring Boot et s'appuie sur Tesseract pour l'OCR et Gemini pour l'extraction semantique des champs.

## 2. Objectifs du projet

- Accepter l'upload de documents multi-formats.
- Extraire automatiquement le texte brut du document.
- Convertir ce texte en JSON structure selon un type de document.
- Valider la coherence du resultat extrait.
- Enregistrer les donnees en base pour consultation ulterieure.
- Fournir une API REST exploitable par une interface web.

## 3. Architecture generale

L'architecture suit une separation classique en couches:

- Couche `Controller`: exposition des endpoints REST.
- Couche `Service`: logique metier (validation, OCR, appel LLM, persistance).
- Couche `Repository`: acces aux donnees via Spring Data JPA.
- Couche `Entity/DTO`: modelisation des donnees internes et externes.
- Couche `Exception`: gestion centralisee des erreurs.

Flux principal:

1. Le client envoie un fichier + type de document + modele Gemini.
2. Le service valide le fichier.
3. Le texte est extrait (OCR image ou parse PDF).
4. Le texte est envoye a Gemini avec les champs attendus.
5. Le JSON retourne est valide.
6. Les donnees sont sauvegardees en base H2.
7. La reponse JSON est renvoyee au client.

## 4. Technologies utilisees

- Java 17
- Spring Boot 4.0.2
- Spring Web MVC
- Spring Data JPA
- H2 Database
- Tess4J / Tesseract OCR
- Apache PDFBox
- API Google Gemini

## 5. Fonctionnalites implementees

### 5.1 Gestion des documents

- Endpoint d'upload: `POST /api/documents`
- Support des formats: `PDF`, `JPEG`, `PNG`
- Taille max configuree: `10MB`

### 5.2 Extraction de texte

- Images: `OCRService` avec Tesseract
- PDF: `PDFService` avec PDFBox

### 5.3 Extraction structuree

- Construction d'un prompt base sur:
  - texte brut extrait
  - liste dynamique des champs du type de document
- Appel de Gemini (`generateContent`)
- Reception d'un JSON structure en sortie

### 5.4 Validation metier

- Validation du fichier entrant (`ValidationService`)
- Validation de la reponse LLM:
  - format JSON valide
  - presence de tous les champs obligatoires

### 5.5 Persistance des donnees

- Sauvegarde des types de documents (`document_types`)
- Sauvegarde des documents extraits (`documents`)
- Historique consultable via API (`GET /api/documents/all`)

### 5.6 Gestion des erreurs

Gestion centralisee des exceptions avec `GlobalExceptionHandler`:

- `InvalidDocumentException` -> 400
- `RequiredFieldMissingException` -> 400
- `JsonDataMappingException` -> 500
- `FileProcessingException` -> 500
- `Exception` generique -> 500

## 6. API REST exposee

Base URL: `http://localhost:8080/api`

- `POST /documents`: traitement d'un document
- `GET /documents/types`: liste des types
- `POST /documents/types`: ajout d'un type
- `GET /documents/all`: historique
- `GET /models`: modeles Gemini disponibles

## 7. Base de donnees

Le projet utilise H2 en mode fichier local:

- URL: `jdbc:h2:file:./data/documentsdb`
- Console: `http://localhost:8080/h2-console`

Schema principal:

- `document_types(id, name, fields)`
- `documents(id, created_at, type_id, content)`

## 8. Prerequis d'execution

- Java 17
- Maven (ou `mvnw.cmd`)
- Tesseract installe dans le PATH
- Fichier OCR `fra.traineddata` present dans `src/main/resources/tessdata`
- Cle Gemini dans `src/main/resources/application.properties`

Exemple:

```properties
gemini_api_key=VOTRE_CLE_API_GEMINI
```

## 9. Scripts et fichiers d'exemples

Scripts fournis:

- `scripts/run.ps1`: demarrer l'application
- `scripts/test.ps1`: verifier prerequis + lancer tests
- `scripts/demo_upload.ps1`: test de l'endpoint d'upload

Fichiers d'exemples:

- `Exemples/certificat-administratif.pdf`
- `Exemples/ilide.info-attestation-de-formation.pdf`
- `Exemples/ilide.info-confirmation-de-virement.pdf`
- `Exemples/ilide.info-fiche-de-releve-de-decision.pdf`
- `Exemples/Screenshot 2026-02-15 174338.png`

## 10. Tests

Un test de contexte est present:

- `DocumentApiApplicationTests`

Commande:

```powershell
.\scripts\test.ps1
```

Ce script verifie automatiquement:

- Java
- Maven/wrapper
- Tesseract
- `fra.traineddata`
- presence de `gemini_api_key`

## 11. Limites actuelles

- Le projet contient principalement un test de contexte; la couverture de tests metier reste a etendre.
- La robustesse depend de la qualite du texte OCR pour certains documents scannes.
- La precision d'extraction varie selon le modele Gemini selectionne.

## 12. Ameliorations proposees

- Ajouter des tests unitaires et d'integration complets.
- Ajouter un systeme d'authentification (JWT).
- Ajouter la gestion asynchrone (queue) pour gros volumes.
- Ajouter une documentation OpenAPI/Swagger.
- Ajouter versionnement et normalisation avancee des schemas documentaires.

## 13. Conclusion

Le projet Document API repond aux objectifs principaux d'un pipeline intelligent de traitement documentaire: ingestion, extraction OCR, structuration IA, validation et persistance. L'architecture modulaire facilite la maintenance et l'evolution, et la base technique est adaptee a une extension future vers un environnement de production.
