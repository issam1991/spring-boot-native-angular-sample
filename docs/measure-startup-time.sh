#!/bin/bash

# Script to measure Spring Boot Native Image startup time
# This script will build, start, and measure the actual startup time

echo "🚀 Measuring Spring Boot Native Image Startup Time"
echo "=================================================="

# Clean up any existing containers
echo "🧹 Cleaning up existing containers..."
docker-compose down -v

# Build the native image
echo "🔨 Building Spring Boot Native Image..."
./mvnw -Pnative native:compile

if [ $? -ne 0 ]; then
    echo "❌ Native compilation failed!"
    exit 1
fi

echo "✅ Native image built successfully!"

# Start the database first
echo "🗄️ Starting MariaDB database..."
docker-compose up mariadb -d

# Wait for database to be ready
echo "⏳ Waiting for database to be ready..."
sleep 10

# Start the application and measure startup time
echo "🚀 Starting Spring Boot Native Application..."
echo "📊 Measuring startup time..."

# Record start time
START_TIME=$(date +%s.%N)

# Start the application in background and capture output
docker-compose up app > app_startup.log 2>&1 &
APP_PID=$!

# Wait for application to be ready
echo "⏳ Waiting for application to start..."

# Check if application is responding
for i in {1..30}; do
    if curl -s http://localhost:8080/api/status > /dev/null 2>&1; then
        # Record end time
        END_TIME=$(date +%s.%N)
        
        # Calculate startup time
        STARTUP_TIME=$(echo "$END_TIME - $START_TIME" | bc -l)
        STARTUP_TIME_MS=$(echo "$STARTUP_TIME * 1000" | bc -l)
        
        echo "✅ Application started successfully!"
        echo "⏱️  Startup Time: ${STARTUP_TIME_MS}ms"
        echo "📊 Detailed Metrics:"
        
        # Get Docker stats
        echo "🐳 Docker Container Stats:"
        docker stats --no-stream --format "table {{.Container}}\t{{.CPUPerc}}\t{{.MemUsage}}\t{{.MemPerc}}\t{{.NetIO}}\t{{.BlockIO}}\t{{.PIDs}}" | grep native-app
        
        # Get container logs for analysis
        echo "📝 Application Logs (first 10 lines):"
        docker-compose logs app | head -10
        
        break
    else
        echo "⏳ Still starting... (attempt $i/30)"
        sleep 1
    fi
done

# Clean up
echo "🧹 Cleaning up..."
docker-compose down

echo "📊 Startup Time Measurement Complete!"
echo "====================================="
echo "Native Image Startup Time: ${STARTUP_TIME_MS}ms"
echo "Logs saved to: app_startup.log"
