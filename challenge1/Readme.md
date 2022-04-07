# Challenge 1

### Tech used:

1) java 8
2) docker


## How to run

### 1) Docker

1) Install docker
2) Go to the project directory from the terminal. For example:
   
    `C:/challenge1>`

3) Find the docker-compose.yml file
4) Write the command below:
   
    `C:/challenge1> docker-compose up`

### 2) JVM

1) install java 8 (JDK)
2) install maven
3) go to the project directory. For example:

   `C:/challenge1>`

4) Find the pom.xml file
5) Write the command below to create a build:

   `C:/challenge1> mvn clean package`

6) go to target directory and execute the command below

    `C:/challenge1/target> java -jar csms-0.0.1-SNAPSHOT.jar`
   

## Endpoints

base url: http://localhost:8080

### /rate

- Param: 

```
{
    "rate": { "energy": 0.3, "time": 2, "transaction": 1 },
    "cdr": { "meterStart": 1204307, "timestampStart": "2021-04-05T10:04:00Z", "meterStop": 1215230, "timestampStop":
    "2021-04-05T11:27:00Z" }
}

```

- Return:

```
{
    "overall": 7.04
    "components": { "energy": 3.277, "time": 2.767, "transaction": 1 }
}
```