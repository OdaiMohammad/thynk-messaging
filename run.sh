#echo "Taking down services in case they exist..."
#docker-compose down

echo "Building projects..."
./mvnw -s mvn-settings.xml clean install -DskipTests

echo "Spinning up services..."
docker-compose up -d --build

#echo "Waiting for Kafka Connect to be ready..."
#sleep 60
#echo "Creating MongoDb connector..."
#curl -X POST -H "Content-Type: application/json" --data @kafka-connect-mongodb-source-config.json localhost:28082/connectors