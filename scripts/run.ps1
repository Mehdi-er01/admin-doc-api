param(
    [string]$ProjectRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
)

$ErrorActionPreference = "Stop"

Push-Location $ProjectRoot
try {
    if (Test-Path ".\\mvnw.cmd") {
        & .\mvnw.cmd spring-boot:run
    }
    elseif (Get-Command mvn -ErrorAction SilentlyContinue) {
        mvn spring-boot:run
    }
    else {
        throw "Maven introuvable. Installez Maven ou utilisez mvnw.cmd."
    }
}
finally {
    Pop-Location
}
