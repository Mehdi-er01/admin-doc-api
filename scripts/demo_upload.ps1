param(
    [Parameter(Mandatory = $true)]
    [string]$FilePath,

    [string]$Type = "CIN",
    [string]$Model = "gemini-1.5-flash",
    [string]$BaseUrl = "http://localhost:8080"
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path $FilePath)) {
    throw "Fichier introuvable: $FilePath"
}

$resolvedFile = (Resolve-Path $FilePath).Path
$uri = "$BaseUrl/api/documents"

$curlArgs = @(
    "--silent",
    "--show-error",
    "--fail-with-body",
    "-X", "POST", $uri,
    "-F", "document=@$resolvedFile",
    "-F", "type=$Type",
    "-F", "model=$Model"
)

$response = & curl.exe @curlArgs

if ($LASTEXITCODE -ne 0) {
    throw "Echec de l'appel API (curl exit code: $LASTEXITCODE)."
}

try {
    $parsed = $response | ConvertFrom-Json
    $parsed | ConvertTo-Json -Depth 20
}
catch {
   
    $response
}
