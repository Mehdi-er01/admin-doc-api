# Jeu de tests - Document API

Ce document recense les cas de test utilises pour valider l'API.

## 1. Contexte de test

- Base URL: `http://localhost:8080`
- Endpoint principal: `POST /api/documents`
- Script utilise: `scripts/demo_upload.ps1`
- Pre-requis: application demarree + `gemini_api_key` configuree

## 2. Fichiers d'entree (bruts)

- `Exemples/certificat-administratif.pdf`
- `Exemples/ilide.info-attestation-de-formation.pdf`
- `Exemples/ilide.info-confirmation-de-virement.pdf`
- `Exemples/ilide.info-fiche-de-releve-de-decision.pdf`
- `Exemples/Screenshot 2026-02-15 174338.png`

## 3. Cas usuels

| ID | Type de cas | Fichier entree | Commande | Resultat attendu | Resultat obtenu | Statut |
|---|---|---|---|---|---|---|
| U1 | PDF valide | `Exemples/certificat-administratif.pdf` | `./scripts/demo_upload.ps1 -FilePath "./Exemples/certificat-administratif.pdf" -Type "CIN" -Model "gemini-1.5-flash"` | `200 OK` + JSON non vide | A completer | A completer |
| U2 | Image valide | `Exemples/Screenshot 2026-02-15 174338.png` | `./scripts/demo_upload.ps1 -FilePath "./Exemples/Screenshot 2026-02-15 174338.png" -Type "CIN" -Model "gemini-1.5-flash"` | `200 OK` + JSON non vide | A completer | A completer |
| U3 | PDF valide | `Exemples/ilide.info-attestation-de-formation.pdf` | `./scripts/demo_upload.ps1 -FilePath "./Exemples/ilide.info-attestation-de-formation.pdf" -Type "CIN" -Model "gemini-1.5-flash"` | `200 OK` + JSON non vide | A completer | A completer |

## 4. Cas particuliers / erreurs

| ID | Type de cas | Entree | Commande | Resultat attendu | Resultat obtenu | Statut |
|---|---|---|---|---|---|---|
| P1 | Fichier inexistant | `Exemples/introuvable.pdf` | `./scripts/demo_upload.ps1 -FilePath "./Exemples/introuvable.pdf" -Type "CIN" -Model "gemini-1.5-flash"` | Erreur script: `Fichier introuvable` | A completer | A completer |
| P2 | Type de document inexistant | PDF valide + `Type="TYPE_FAUX"` | `./scripts/demo_upload.ps1 -FilePath "./Exemples/certificat-administratif.pdf" -Type "TYPE_FAUX" -Model "gemini-1.5-flash"` | Erreur serveur (`500` ou message type introuvable) | A completer | A completer |
| P3 | Modele Gemini invalide | PDF valide + `Model="modele-invalide"` | `./scripts/demo_upload.ps1 -FilePath "./Exemples/certificat-administratif.pdf" -Type "CIN" -Model "modele-invalide"` | Erreur API (`500`/message Gemini) | A completer | A completer |
| P4 | Type mime non supporte | Fichier `.txt` | Appel `POST /api/documents` avec un `.txt` | `400 Bad Request` (`Document invalide`) | A completer | A completer |
| P5 | Fichier vide | Fichier PDF/image vide | Appel `POST /api/documents` | `400 Bad Request` (`Document invalide`) | A completer | A completer |

## 5. JSON generes

Enregistrer les sorties JSON dans un dossier dedie, par exemple:

- `Exemples/outputs/U1_certificat-administratif.json`
- `Exemples/outputs/U2_screenshot.json`
- `Exemples/outputs/U3_attestation.json`

## 6. Commandes utiles

### Lancer l'application

```powershell
./scripts/run.ps1
```

### Verifier pre-requis + tests Maven

```powershell
./scripts/test.ps1
```

### Executer un test d'upload

```powershell
./scripts/demo_upload.ps1 -FilePath "./Exemples/certificat-administratif.pdf" -Type "CIN" -Model "gemini-1.5-flash"
```

## 7. Critere de validation

Un cas est considere **Valide** si:

- le code de retour correspond au resultat attendu
- le comportement correspond au cas defini
- pour les cas usuels, le JSON est bien forme et exploitable
