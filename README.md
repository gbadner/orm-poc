# orm-poc
How to test 
------------

1) We assume that you have docker install on your Laptop if not please install docker

2) Install and start a mysql docker container 

$deploy-mysql.sh

3) In the parent "pom.xml" change the <env.MYSQL_SERVICE_HOST> with your IP adresse of you docker-machine ip

4) Change hostname of JDBC_URL in the Java Class AbstractIT with with your IP adresse of you docker-machine ip

5) Start the test
mvn clean test -Pit -Dtest=PartnerServiceIT#shouldTest
