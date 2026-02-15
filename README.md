# Document API

API Spring Boot pour extraire des donnees structurees depuis des documents (PDF/images) via OCR + Gemini, puis enregistrer le resultat dans une base H2.

## 1. Fonctionnalites

- Upload de document en `multipart/form-data`
- Formats supportes: `application/pdf`, `image/jpeg`, `image/png`
- Extraction de texte:
  - images via `OCRService` (Tesseract)
  - PDF via `PDFService` (PDFBox)
- Generation de JSON structure via Gemini
- Validation des champs obligatoires selon le type de document
- Sauvegarde des resultats en base H2
- Gestion d'erreurs centralisee (`GlobalExceptionHandler`)
- Interface web simple sur `/`

## 2. Stack technique

- Java 17
- Spring Boot 4.0.2
- Spring Web MVC
- Spring Data JPA
- H2 Database
- Tess4J / Tesseract OCR
- Apache PDFBox
- API Google Gemini

## 3. Prerequis techniques

- JDK 17 installe et accessible dans le `PATH`
- Maven installe ou wrapper Maven (`mvnw.cmd`) present
- Tesseract OCR installe et accessible dans le `PATH`
- Cle Gemini valide renseignee dans `application.properties`

## 4. Configuration

Fichier principal: `src/main/resources/application.properties`

Variables importantes:

- `gemini_api_key`: cle API Gemini (obligatoire)
- `spring.datasource.url`: URL H2 (par defaut `jdbc:h2:file:./data/documentsdb`)
- `spring.servlet.multipart.max-file-size`: taille max upload (10MB)

### 4.1 Cle Gemini obligatoire

Pour que le projet fonctionne, renseigner la cle dans:

`src/main/resources/application.properties`

Exemple:

```properties
gemini_api_key=VOTRE_CLE_API_GEMINI
```

## 5. Installation

```bash
git clone <url-du-depot>
cd document_api
```

Compilation:

Windows:

```powershell
.\mvnw.cmd clean compile
```

Linux/macOS:

```bash
./mvnw clean compile
```

## 6. Execution

### 6.1 Lancer l'application

Windows:

```powershell
.\scripts\run.ps1
```

Alternative:

```powershell
.\mvnw.cmd spring-boot:run
```

### 6.2 Acces

- UI: `http://localhost:8080/`
- H2 Console: `http://localhost:8080/h2-console`

Parametres H2:

- JDBC URL: `jdbc:h2:file:./data/documentsdb`
- User: `sa`
- Password: `password`

## 7. Scripts fournis

### `scripts/run.ps1`

Lance l'application Spring Boot.

```powershell
.\scripts\run.ps1
```

### `scripts/test.ps1`

Verifie les prerequis techniques puis lance `mvn test`.

Verifications effectuees:

- presence de `application.properties`
- presence de `fra.traineddata`
- Java installe (+ avertissement si version != 17)
- Maven/wrapper disponible
- Tesseract disponible (`tesseract --version`)
- `gemini_api_key` non vide

Execution:

```powershell
.\scripts\test.ps1
```

### `scripts/demo_upload.ps1`

Effectue un appel de demonstration vers `POST /api/documents`.

```powershell
.\scripts\demo_upload.ps1 -FilePath ".\Exemples\Inputs\certificat-administratif.pdf" -Type "CIN" -Model "gemini-1.5-flash"
```

## 8. API REST

Base path: `/api`

### 8.1 `POST /api/documents`

Upload + extraction + validation + sauvegarde.

- `Content-Type`: `multipart/form-data`
- Parts attendues:
  - `document`: fichier PDF/image
  - `type`: type de document existant
  - `model`: modele Gemini

Exemple `curl`:

```bash
curl -X POST "http://localhost:8080/api/documents" \
  -F "document=@./Exemples/Inputs/certificat-administratif.pdf" \
  -F "type=CIN" \
  -F "model=gemini-1.5-flash"
```

Reponses:

- `200 OK`: JSON extrait
- `400 Bad Request`: document invalide / champ requis manquant
- `500 Internal Server Error`: erreur traitement fichier/JSON/interne

### 8.2 `GET /api/documents/types`

Retourne la liste des types de documents.

### 8.3 `POST /api/documents/types`

Ajoute un type de document.

Exemple:

```bash
curl -X POST "http://localhost:8080/api/documents/types" \
  -H "Content-Type: application/json" \
  -d '{
    "id": null,
    "name": "Invoice",
    "documentFields": [
      {"name":"invoice_number","type":"string"},
      {"name":"issue_date","type":"date"},
      {"name":"total_amount","type":"decimal"}
    ]
  }'
```

### 8.4 `GET /api/documents/all`

Retourne l'historique des documents traites.

### 8.5 `GET /api/models`

Retourne la liste des modeles Gemini disponibles.

## 9. Dossier d'exemples

Tes fichiers d'exemple detectes:

- `Exemples/Inputs/certificat-administratif.pdf`
- `Exemples/Inputs/attestation-de-formation.pdf`
- `Exemples/Inputs/confirmation-de-virement.pdf`
- `Exemples/Inputs/fiche-de-releve-de-decision.pdf`
- `Exemples/Inputs/CIN.png`

## 10. Structure du projet

```text
src/main/java/com/fsts/document_api/
  Controller/
  Service/
  Repository/
  Entity/
  Dto/
  Exception/
  Record/
  Utils/

src/main/resources/
  application.properties
  schema.sql
  data.sql
  static/index.html
  tessdata/fra.traineddata

scripts/
  run.ps1
  test.ps1
  demo_upload.ps1

Exemples/
  Inputs/
  Outputs/
  test_cases.md
```

## 11. Tests

Lancer:

```powershell
.\scripts\test.ps1
```

Ou directement:

```powershell
.\mvnw.cmd test
```

Test actuellement present:

- `src/test/java/com/fsts/document_api/DocumentApiApplicationTests.java`

## 12. Troubleshooting

- `Document invalide`: verifier extension et `Content-Type`
- erreur Gemini: verifier `gemini_api_key` et le modele choisi
- erreur OCR: verifier installation Tesseract + presence dans PATH
- erreur DB: verifier droits d'ecriture sur `data/`
