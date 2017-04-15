 //  Copyright (C) 2004-2008, Robotics Equipment Corporation GmbH

#include <jni.h> //Provient du jdk
#include "CapteurLumineux.h" //Fichier d'entete genere

//Librairies nécessaires au programme
#include <stdio.h>
#include <stdlib.h>
#include "rec/robotino/com/c/Com.h"

#ifdef WIN32
#include <windows.h>
// _getch
#include <conio.h>
#else
// getchar
#include <stdio.h>
// usleep
#include <unistd.h>
#endif

//Type Booleen
typedef int bool;
#define true 1
#define false 0


bool valeurCapteurLumineux_c(int iNumPin){

	//Déclaration et initialisation des variables
	ComId com;
	DigitalInputId pinCapteur;

	int i;
	bool connectSetCom = false;
	bool connectPin = false;
	bool bvalue = false;
	int tempsInit = 100000;
	
	//Connection à Robotino (en local)
	com = Com_construct();
	Com_setAddress(com,"127.0.0.1");

	if( FALSE == Com_connect(com) )
	{
		fprintf(stderr, "Error on connect\n");
		return -1.0;
	}
	else
	{
		char addressBuffer[256];
		Com_address( com, addressBuffer, 256 );
		printf( "Connected to %s\n", addressBuffer );
	}
	
	//Construction de l'objet digital : numero du pin allant de 0 à 7 (DI1 -> DI8)
	pinCapteur = DigitalInput_construct(iNumPin-1);

	//On associe notre objet digital à notre interface de communication
	connectSetCom = DigitalInput_setComId(pinCapteur, com);	

	if(connectSetCom == false){
		fprintf(stderr, "Error on connectSetComId pin\n");
		return -1.0;
	}

	//On associe le numéro de pin à notre objet
	connectPin = DigitalInput_setInputNumber(pinCapteur, iNumPin-1);
	
	//Initialisation capteur
	if(connectPin == false){
		printf( "Erreur Pin Connexion \n" );
		return -1.0;
	}
	//Initialisation capteur avec usleep (en microseconde)
	usleep(tempsInit);
			
	//Récuperation valeur capteur
	bvalue = DigitalInput_value(pinCapteur);
	
	//Si on veut afficher la valeur en C
	fprintf(stdout, "EN C Valeur lumineux : %i \n", bvalue);

	//On détruit les objets associés à notre interface de communication
	DigitalInput_destroy(pinCapteur);
	Com_destroy(com);

	return bvalue;
}

//Fonction qui nous renvoie la valeur du capteur d'induction passé en paramètre d'entrée : 
//Permet de "transferer"/"traduire" le code C en Java 
//Définition du nom de la fonction : JNIEXPORT typeValeurRetournée JNICALL Java_nomClasse_nomMéthodeJava
JNIEXPORT jboolean JNICALL Java_CapteurLumineux_valeurCapteurLumineux
  (JNIEnv *env, jobject obj, jint iNumPin){
	  //Fonction en C qui va récupérer la valeur du capteur lumineux passé en paramètre d'entrée
	  return valeurCapteurLumineux_c(iNumPin);
}

