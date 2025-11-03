# Simple method to measure startup time using Docker Compose
# Run this after building your native image

Write-Host "Measuring Native Image Startup Time with Docker Compose" -ForegroundColor Green
Write-Host "=========================================================" -ForegroundColor Green

# Clean up
docker-compose down -v

# Start database
Write-Host "Starting database..." -ForegroundColor Yellow
docker-compose up mariadb -d
Start-Sleep -Seconds 5

# Measure startup time
Write-Host "Starting application and measuring time..." -ForegroundColor Yellow
$StartTime = Get-Date

# Start application
docker-compose up app -d

# Wait for application to be ready
Write-Host "Waiting for application to respond..." -ForegroundColor Yellow
$MaxWait = 30
$WaitTime = 0

while ($WaitTime -lt $MaxWait) {
    try {
        $Response = Invoke-WebRequest -Uri "http://localhost:8080/api/status" -TimeoutSec 1 -ErrorAction SilentlyContinue
        if ($Response.StatusCode -eq 200) {
            $EndTime = Get-Date
            $StartupTime = ($EndTime - $StartTime).TotalMilliseconds
            
            Write-Host "Application ready!" -ForegroundColor Green
            Write-Host "Startup Time: $([math]::Round($StartupTime, 2))ms" -ForegroundColor Cyan
            
            # Show Docker stats
            Write-Host "Container Stats:" -ForegroundColor Yellow
            docker stats --no-stream --format "table {{.Container}}\t{{.CPUPerc}}\t{{.MemUsage}}\t{{.MemPerc}}" | Select-String "native-app"
            
            break
        }
    }
    catch {
        # Still starting
    }
    
    $WaitTime++
    Start-Sleep -Seconds 1
}

# Show logs
Write-Host "Application Logs:" -ForegroundColor Yellow
docker-compose logs app | Select-Object -Last 5

Write-Host "Cleaning up..." -ForegroundColor Yellow
docker-compose down