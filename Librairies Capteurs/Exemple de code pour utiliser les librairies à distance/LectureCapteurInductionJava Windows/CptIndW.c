//  Copyright (C) 2004-2008, Robotics Equipment Corporation GmbH

#include "CptIndW.h" //Fichier d'entete genere

//Librairies nécessaires au programme
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <jni.h> //Provient du jdk
#include <jni_md.h>

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

float valeurCapteurInduction_c(int iNumPin, const char* hostname){

	//Déclaration et initialisation des variables
	ComId com;
	AnalogInputId pinCapteur;

	int i;
	boolean connectSetCom = 0;
	boolean connectPin = 0;
	boolean tempsInit = 1;
	float fvalue = -1.0;

	int tailleFenetreValue = 20;
	int nbValueMoyenne = 10;
	float value[20];

	//Connection à Robotino (via adresse IP)
	com = Com_construct();
	Com_setAddress(com,hostname);

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
	
	//Construction de l'objet analogique : numero du pin allant de 0 à 7 (AIN1 -> AIN8)
	pinCapteur = AnalogInput_construct(iNumPin-1);

	//On associe notre objet analogique à notre interface de communication
	connectSetCom = AnalogInput_setComId(pinCapteur, com);	

	if(connectSetCom == 0){
		fprintf(stderr, "Error on connectSetComId pin\n");
		return -1.0;
	}

	//On associe le numéro de pin à notre objet
	connectPin = AnalogInput_setInputNumber(pinCapteur, iNumPin-1);
	
	//Initialisation capteur
	while(tempsInit){

		if(connectPin == 0){
			printf( "Erreur Pin Connexion \n" );
			break;
		}

		//Permet d'initialiser notre capteur : on sort de la boucle une fois que l'on lit une valeur > 0
		if(AnalogInput_value(pinCapteur) > 0.001){
			tempsInit = 0;
		}
	}

	//Récuperation valeur capteur
	for(i=0;i<tailleFenetreValue;i++){
		value[i] = AnalogInput_value(pinCapteur);
	}
		 
	//Moyenne des X dernières valeurs afin d'éliminer des erreurs de mesures
	for(i=nbValueMoyenne;i<tailleFenetreValue;i++){
		if(fvalue == -1.0){
			fvalue = 0;
		}
		fvalue = fvalue + value[i];
	}
	
	//Valeur moyenne du capteur induction
	fvalue = fvalue/(tailleFenetreValue-nbValueMoyenne);
	fprintf(stdout, "Valeur Induction : %f \n", fvalue);
	
	//On détruit les objets associés à notre interface de communication
	AnalogInput_destroy(pinCapteur);
	Com_destroy(com);

	return fvalue;
}

//Fonction qui nous renvoie la valeur du capteur d'induction passé en paramètre d'entrée : 
//Permet de "transferer"/"traduire" le code C en Java 
//Définition du nom de la fonction : JNIEXPORT typeValeurRetournée JNICALL Java_nomClasse_nomMéthodeJava
JNIEXPORT jfloat JNICALL Java_CptIndW_valeurCptIndW
  (JNIEnv *env, jobject obj, jint iNumPin, jstring hostname){//Fonction en C qui va récupérer la valeur du capteur d'induction passé en paramètre d'entrée
	  
	const char* nativeString = (*env)->GetStringUTFChars(env, hostname, NULL);
	
	if(nativeString == NULL){
		return -1;
	}
	else{
		return valeurCapteurInduction_c(iNumPin, nativeString);

		(*env)->ReleaseStringUTFChars(env, hostname,nativeString);
	}
	  
}

