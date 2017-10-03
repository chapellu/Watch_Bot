#!/bin/bash

<<<<<<< HEAD
cd /Librairies
=======
cd Librairies/
>>>>>>> Mise à jour Navigation + fichier.sh

gcc -c -I/usr/lib/jvm/java-6-openjdk/include/ -I/usr/local/OpenRobotinoAPI/1/include/ -o CapteurInduction.o CapteurInduction.c
gcc -c -I/usr/lib/jvm/java-6-openjdk/include/ -I/usr/local/OpenRobotinoAPI/1/include/ -o CapteurLumineux.o CapteurLumineux.c
gcc -o libCapteurInduction.so -shared CapteurInduction.o -Wl,-rpath, /usr/local/OpenRobotinoAPI/1/lib/linux/librec_robotino_com_c.so 
gcc -o libCapteurLumineux.so -shared CapteurLumineux.o -Wl,-rpath, /usr/local/OpenRobotinoAPI/1/lib/linux/librec_robotino_com_c.so 

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/OpenRobotinoAPI/1/lib/linux/

<<<<<<< HEAD
cd..

cd /src
javac -cp .:/usr/local/OpenRobotinoAPI/1/lib/java/rec_robotino_com_wrap.jar:\* -d "classes" /cartographie/*.java
javac -cp .:/usr/local/OpenRobotinoAPI/1/lib/java/rec_robotino_com_wrap.jar:\* -d "classes" /Main/*.java

java -cp .:/usr/local/OpenRobotinoAPI/1/lib/java/rec_robotino_com_wrap.jar:../Librairies/Communication1_6.jar:classes:../Librairies/mysql-connector-java-5.1.40-bin.jar Main/Robot
=======
cd ..

cd src/
javac -cp .:/usr/local/OpenRobotinoAPI/1/lib/java/rec_robotino_com_wrap.jar:\* cartographie/*.java
javac -cp .:/usr/local/OpenRobotinoAPI/1/lib/java/rec_robotino_com_wrap.jar:../Librairies/Communication1_6.jar:classes:../Librairies/mysql-connector-java-5.1.40-bin.jar\* Main/*.java

java -cp .:/usr/local/OpenRobotinoAPI/1/lib/java/rec_robotino_com_wrap.jar:/usr/local/OpenRobotinoAPI/1/lib/linux/*:../Librairies/Communication1_6.jar:classes:../Librairies/mysql-connector-java-5.1.40-bin.jar Main/Robot
>>>>>>> Mise à jour Navigation + fichier.sh


