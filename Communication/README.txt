1) Il faut créer un objet interface communication 
InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();

2) Pour envoyer un message : 
com.sendMessage(String destinataire, String type, String message);
Il exsite 4 types de message Ordre, Message, Erreur, Inconnu

3) Pour recevoir des messages il faut démarrer un serveur avec
com.startEcoute(InterfaceMessageRecu x);
L'objet x doit implementer InterfaceMessageRecu et c'est lui qui traite la réception des messages

4) Pour ne plus recevoir de message : 
com.closeEcoute();

5)Compilation : javac -cp /home/watchbot/Watch_bot/Communication/APPRS2018/Json/:/home/watchbot/Watch_bot/Communication/APPRS2018/lib/javax.mail.jar:. -d ./bin src/Communication/*.java
