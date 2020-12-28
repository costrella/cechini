web: java $JAVA_OPTS -Xmx256m -jar web/target/*.jar --spring.profiles.active=prod,heroku,no-liquibase --server.port=$PORT 
release: cp -R web/src/main/resources/config config && ./mvnw -ntp liquibase:update -Pprod,heroku
