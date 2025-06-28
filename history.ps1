$commits = git rev-list HEAD
$commitsList = $commits -split "`n"
$working = $null

foreach ($commit in $commitsList) {
    git checkout $commit
    Write-Host "Пробуем коммит $commit"
    & ant compile
    if ($LASTEXITCODE -eq 0) {
        $working = $commit
        Write-Host "Рабочий коммит найден: $commit"
        break
    }
}

if ($working) {
    $index = [Array]::IndexOf($commitsList, $working)
    if ($index -gt 0) {
        $next = $commitsList[$index - 1]
        git diff $working $next > diff.patch
        Write-Host "diff.patch сформирован между $working и $next"
    } else {
        Write-Host "Это самая старая ревизия"
    }
    git checkout HEAD
    & ant build
} else {
    Write-Host "Нет рабочей ревизии!"
}
