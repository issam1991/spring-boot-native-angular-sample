# PowerShell script to measure Spring Boot Native Image startup time
# This script will build, start, and measure the actual startup time

Write-Host "🚀 Measuring Spring Boot Native Image Startup Time" -ForegroundColor Green
Write-Host "==================================================" -ForegroundColor Green

# Clean up any existing containers
Write-Host "🧹 Cleaning up existing containers..." -ForegroundColor Yellow
docker-compose down -v

# Build the native image
Write-Host "🔨 Building Spring Boot Native Image..." -ForegroundColor Yellow
./mvnw.cmd -Pnative native:compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Native compilation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "✅ Native image built successfully!" -ForegroundColor Green

# Start the database first
Write-Host "🗄️ Starting MariaDB database..." -ForegroundColor Yellow
docker-compose up mariadb -d

# Wait for database to be ready
Write-Host "⏳ Waiting for database to be ready..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# Start the application and measure startup time
Write-Host "🚀 Starting Spring Boot Native Application..." -ForegroundColor Yellow
Write-Host "📊 Measuring startup time..." -ForegroundColor Yellow

# Record start time
$StartTime = Get-Date

# Start the application in background
Write-Host "⏳ Starting application container..." -ForegroundColor Yellow
docker-compose up app > app_startup.log 2>&1 &
$AppProcess = $!

# Wait for application to be ready
Write-Host "⏳ Waiting for application to start..." -ForegroundColor Yellow

# Check if application is responding
$MaxAttempts = 30
$Attempt = 0
$ApplicationReady = $false

while ($Attempt -lt $MaxAttempts -and -not $ApplicationReady) {
    try {
        $Response = Invoke-WebRequest -Uri "http://localhost:8080/api/status" -TimeoutSec 2 -ErrorAction SilentlyContinue
        if ($Response.StatusCode -eq 200) {
            $ApplicationReady = $true
            $EndTime = Get-Date
            $StartupTime = ($EndTime - $StartTime).TotalMilliseconds
            
            Write-Host "✅ Application started successfully!" -ForegroundColor Green
            Write-Host "⏱️  Startup Time: $([math]::Round($StartupTime, 2))ms" -ForegroundColor Cyan
            Write-Host "📊 Detailed Metrics:" -ForegroundColor Yellow
            
            # Get Docker stats
            Write-Host "🐳 Docker Container Stats:" -ForegroundColor Yellow
            docker stats --no-stream --format "table {{.Container}}\t{{.CPUPerc}}\t{{.MemUsage}}\t{{.MemPerc}}\t{{.NetIO}}\t{{.BlockIO}}\t{{.PIDs}}" | Select-String "native-app"
            
            # Get container logs for analysis
            Write-Host "📝 Application Logs (first 10 lines):" -ForegroundColor Yellow
            docker-compose logs app | Select-Object -First 10
            
            break
        }
    }
    catch {
        # Application not ready yet
    }
    
    $Attempt++
    Write-Host "⏳ Still starting... (attempt $Attempt/$MaxAttempts)" -ForegroundColor Yellow
    Start-Sleep -Seconds 1
}

if (-not $ApplicationReady) {
    Write-Host "❌ Application failed to start within timeout!" -ForegroundColor Red
    Write-Host "📝 Check logs:" -ForegroundColor Yellow
    Get-Content app_startup.log | Select-Object -Last 20
}

# Clean up
Write-Host "🧹 Cleaning up..." -ForegroundColor Yellow
docker-compose down

Write-Host "📊 Startup Time Measurement Complete!" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green
if ($ApplicationReady) {
    Write-Host "Native Image Startup Time: $([math]::Round($StartupTime, 2))ms" -ForegroundColor Cyan
}
Write-Host "Logs saved to: app_startup.log" -ForegroundColor Yellow
