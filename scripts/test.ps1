param(
    [string]$ProjectRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
)

$ErrorActionPreference = "Stop"

function Test-CommandExists {
    param([string]$Name)
    return [bool](Get-Command $Name -ErrorAction SilentlyContinue)
}

function Assert-FileExists {
    param([string]$PathToCheck, [string]$Message)
    if (-not (Test-Path $PathToCheck)) {
        throw $Message
    }
}

Push-Location $ProjectRoot
try {
    Write-Host "=== Verification des prerequis techniques ==="

    Assert-FileExists ".\\src\\main\\resources\\application.properties" "application.properties introuvable (src/main/resources)."
    Assert-FileExists ".\\src\\main\\resources\\tessdata\\fra.traineddata" "fichier OCR introuvable: src/main/resources/tessdata/fra.traineddata"

    if (-not (Test-CommandExists "java")) {
        throw "Java introuvable dans le PATH. Installez JDK 17 puis reessayez."
    }

    $javaVersionOutput = & java -version 2>&1 | Out-String
    if ($javaVersionOutput -notmatch 'version\s+"17') {
        Write-Warning "Version Java detectee differente de 17. Le projet est configure pour Java 17."
    }
    else {
        Write-Host "Java 17 detecte."
    }

    $hasWrapper = Test-Path ".\\mvnw.cmd"
    $hasMaven = Test-CommandExists "mvn"
    if (-not $hasWrapper -and -not $hasMaven) {
        throw "Maven introuvable. Installez Maven ou conservez mvnw.cmd a la racine du projet."
    }

    if (-not (Test-CommandExists "tesseract")) {
        throw "Tesseract introuvable dans le PATH. Installez Tesseract OCR puis reessayez."
    }

    $tesseractVersion = & tesseract --version 2>&1 | Select-Object -First 1
    Write-Host "Tesseract detecte: $tesseractVersion"

    $propertiesPath = ".\\src\\main\\resources\\application.properties"
    $propertiesContent = Get-Content $propertiesPath -Raw
    $apiKeyMatch = [regex]::Match($propertiesContent, '(?m)^\s*gemini_api_key\s*=\s*(.*)\s*$')

    if (-not $apiKeyMatch.Success -or [string]::IsNullOrWhiteSpace($apiKeyMatch.Groups[1].Value)) {
        throw "gemini_api_key manquante dans application.properties."
    }

    Write-Host "Cle Gemini detectee dans application.properties."
    Write-Host "Prerequis OK."
    Write-Host "=== Lancement des tests Maven ==="

    if ($hasWrapper) {
        & .\mvnw.cmd test
    }
    else {
        mvn test
    }
}
finally {
    Pop-Location
}
