#!/bin/bash

# Load test script for Quarkus endpoints
# Usage: ./loadtest.sh <endpoint> <name> <iterations>

ENDPOINT=$1
NAME=$2
ITERATIONS=${3:-1000}
BASE_URL=${BASE_URL:-http://localhost:8080}

echo "Starting load test for $NAME endpoint: $ENDPOINT"
echo "Number of iterations: $ITERATIONS"

# Sample JSON payload
JSON_PAYLOAD='{
  "name": "John Doe",
  "age": 30,
  "email": "john.doe@example.com",
  "primaryAddress": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zip": "10001",
    "country": "USA"
  },
  "addresses": [
    {
      "street": "123 Main St",
      "city": "New York",
      "state": "NY",
      "zip": "10001",
      "country": "USA"
    },
    {
      "street": "456 Oak Ave",
      "city": "Los Angeles",
      "state": "CA",
      "zip": "90001",
      "country": "USA"
    }
  ],
  "phones": [
    {
      "type": "mobile",
      "number": "+1-555-1234"
    },
    {
      "type": "work",
      "number": "+1-555-5678"
    }
  ],
  "orders": [
    {
      "orderId": "ORD-001",
      "product": "Laptop",
      "amount": 999.99,
      "date": "2024-01-15"
    },
    {
      "orderId": "ORD-002",
      "product": "Mouse",
      "amount": 29.99,
      "date": "2024-01-20"
    }
  ]
}'

# Warm-up requests
echo "Performing warm-up requests..."
for i in {1..10}; do
  curl -s -X POST "$BASE_URL$ENDPOINT" \
    -H "Content-Type: application/json" \
    -d "$JSON_PAYLOAD" > /dev/null 2>&1
done

# Actual load test
echo "Starting actual load test..."
START_TIME=$(date +%s%N)

for i in $(seq 1 $ITERATIONS); do
  RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL$ENDPOINT" \
    -H "Content-Type: application/json" \
    -d "$JSON_PAYLOAD" 2>&1)
  
  HTTP_CODE=$(echo "$RESPONSE" | tail -n 1)
  
  if [ "$HTTP_CODE" != "200" ]; then
    echo "Error: Request $i failed with status code $HTTP_CODE"
    echo "Response: $RESPONSE"
    exit 1
  fi
  
  # Show progress every 100 requests
  if [ $((i % 100)) -eq 0 ]; then
    echo "Completed $i/$ITERATIONS requests"
  fi
done

END_TIME=$(date +%s%N)

# Calculate duration
DURATION_NS=$((END_TIME - START_TIME))
DURATION_MS=$((DURATION_NS / 1000000))
DURATION_S=$((DURATION_MS / 1000))
AVG_MS=$((DURATION_MS / ITERATIONS))

echo ""
echo "=========================================="
echo "Load Test Results for $NAME"
echo "=========================================="
echo "Endpoint: $ENDPOINT"
echo "Iterations: $ITERATIONS"
echo "Total Duration: ${DURATION_S}s (${DURATION_MS}ms)"
echo "Average Response Time: ${AVG_MS}ms"
echo "Requests per Second: $((ITERATIONS * 1000 / DURATION_MS))"
echo "=========================================="
echo ""

# Export results for GitHub Actions
if [ -n "$GITHUB_STEP_SUMMARY" ]; then
  echo "### Load Test Results: $NAME" >> $GITHUB_STEP_SUMMARY
  echo "" >> $GITHUB_STEP_SUMMARY
  echo "| Metric | Value |" >> $GITHUB_STEP_SUMMARY
  echo "|--------|-------|" >> $GITHUB_STEP_SUMMARY
  echo "| Endpoint | \`$ENDPOINT\` |" >> $GITHUB_STEP_SUMMARY
  echo "| Iterations | $ITERATIONS |" >> $GITHUB_STEP_SUMMARY
  echo "| Total Duration | ${DURATION_S}s (${DURATION_MS}ms) |" >> $GITHUB_STEP_SUMMARY
  echo "| Average Response Time | ${AVG_MS}ms |" >> $GITHUB_STEP_SUMMARY
  echo "| Requests per Second | $((ITERATIONS * 1000 / DURATION_MS)) |" >> $GITHUB_STEP_SUMMARY
  echo "" >> $GITHUB_STEP_SUMMARY
fi

# Save results to file
echo "$NAME,$ENDPOINT,$ITERATIONS,$DURATION_MS,$AVG_MS" >> loadtest_results.csv

exit 0
