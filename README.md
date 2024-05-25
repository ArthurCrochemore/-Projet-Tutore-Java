## Projet initialialement sur GitLab : https://gitlab.com/ArthurCrochemore2/projet-tutore-java

_Réalisé par : Jerome Lecuyer, Alâa Chakori Semmane, Arthur Crochemor et Mickael Vital_

##

# Projet Tutore Java

group MAAJ<br>
Repo : https://gitlab.com/ArthurCrochemore2/projet-tutore-java<br>
Video demo : https://youtu.be/rmrxzqgWFEc

This project contains 2 applications :
- Punching Management : Main/Central application
- Punching Machine : Punching emulator

The data of Punching Management are saved in `company.ser`.<br>
If company.ser doesn't exist, you are asked if you want to add a test company.<br>
If you answer yes, you are asked again if you want to add more employees with random data.

The test company is composed of 2 employees and 3 departments.<br>
The employees are :
- Jean Dupont, ID `f9aa8da9-7b4b-4c75-9a9b-850e1dddd254`
- Pierre Martin, ID `fc8d0eb7-e899-4139-8c87-93edae47a75d`

For the Punching Machine, you are asked if you want to fill the id with the id of Jean Dupont.

You can also use the files in the `cards` folder by dropping them in the id field.

## Setup with Eclipse

After cloning :
- Import... > Maven > Existing Maven Projects
- Root Directory > Browse... > Select the root folder of the project
- The 3 projects should be selected
- Finish
- Run As > Java Application > MainApp
- If needed : Right click on the project > Maven > Update Project... (Alt+F5)

## Run the tests on Eclipse

- Right click on the project > Run As > JUnit Test

## Setup with Maven

After cloning and if needed :

```bash
cd Common
mvn clean install
cd ../PunchingMachine
mvn clean install
cd ../PunchingManagement
mvn clean install
cd ..
```

## Run the tests with maven

```bash
cd PunchingMachine
mvn test
cd ../PunchingManagement
mvn test
cd ..
```
## Javadoc

- [PunchingCommon](Common/Javadoc/index.html)
- [PunchingMachine](PunchingMachine/Javadoc/index.html)
- [PunchingManagement](PunchingManagement/Javadoc/index.html)

