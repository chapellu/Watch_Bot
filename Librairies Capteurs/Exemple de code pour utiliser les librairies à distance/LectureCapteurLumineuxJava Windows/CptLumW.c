#include "CptLumW.h" //Fichier d'entete genere

//Librairies nécessaires au programme
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <jni.h> //Provient du jdk
#include <jni_md.h>

//Librairie de Robotino pour étblir la communication
#include "rec\robotino\com\c\Com.h"

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

//Fonction en C qui permet de récuperer une information des capteurs lumineux
boolean valeurCapteurLumineux_c(int iNumPin,const char* hostname){

	//Déclaration et initialisation des variables
	ComId com;
	DigitalInputId pinCapteur;
	FILE * fp=fopen("LogCptLumW.txt","w+");
	
	boolean connectSetCom = 0;
	boolean connectPin = 0;
	boolean bvalue = 0;
	int tempsInit = 100; //Temps d'initialisation avant d'acquérir une valeur
	time_t t = time(NULL); 

	if(fp != NULL){
		fprintf(fp, "<log><record>\n");
	}
	else{
		return 0;
	}
	
 	//Connection à Robotino (à distance)
	com = Com_construct();
	Com_setAddress(com, hostname);

	if( FALSE == Com_connect(com) )
	{
		if(fp != NULL){
			fprintf(fp, "<date> %s </date>\n", ctime(&t));
			fprintf(fp, "<level> WARNING </level>\n");
			fprintf(fp, "<class> valeurCapteurLumineux_c </class>\n");
			fprintf(fp, "<message> Com_connect </message>\n");
			fprintf(fp, "</log></record>\n");
			fclose(fp);
		}
		return 0;
	}
	else
	{
		char addressBuffer[256];
		Com_address( com, addressBuffer, 256 );
		if(fp != NULL){
			fprintf(fp, "<date> %s </date>\n", ctime(&t));
			fprintf(fp, "<level> INFO </level>\n");
			fprintf(fp, "<class> valeurCapteurLumineux_c </class>\n");
			fprintf(fp, "<method> Com_address </method>\n");
			fprintf(fp, "<message> Connected to %s </message>\n", addressBuffer);
		}
	}
	
	//Construction de l'objet digital : numero du pin allant de 0 à 7 (DI1 -> DI8)
	pinCapteur = DigitalInput_construct(iNumPin-1);

	//On associe notre objet digital à notre interface de communication
	connectSetCom = DigitalInput_setComId(pinCapteur, com);	

	if(connectSetCom == 0){
		if(fp != NULL){
			fprintf(fp, "<date> %s </date>\n", ctime(&t));
			fprintf(fp, "<level> WARNING </level>\n");
			fprintf(fp, "<class> valeurCapteurLumineux_c </class>\n");
			fprintf(fp, "<method> DigitalInput_setComId </method>\n");
			fprintf(fp, "<message> Error on connectSetComId pin </message>\n");
			fprintf(fp, "</log></record>\n");
			fclose(fp);
		}
		return 0;
	}

	//On associe le numéro de pin à notre objet
	connectPin = DigitalInput_setInputNumber(pinCapteur, iNumPin-1);
	
	if(connectPin == 0){
		if(fp != NULL){
			fprintf(fp, "<date> %s </date>\n", ctime(&t));
			fprintf(fp, "<level> WARNING </level>\n");
			fprintf(fp, "<class> valeurCapteurLumineux_c </class>\n");
			fprintf(fp, "<method> DigitalInput_setInputNumber </method>\n");
			fprintf(fp, "<message> Erreur Pin %d Connexion </message>\n", iNumPin);
			fprintf(fp, "</log></record>\n");
			fclose(fp);
		}
		return 0;
	}

	//Initialisation capteur avec usleep (en microseconde)
	Sleep(tempsInit); //Il faut utiliser Sleep sous windows et usleep sous Unix
			
	//Récuperation de la valeur du capteur
	bvalue = DigitalInput_value(pinCapteur);
	
	//Si on veut afficher la valeur en C
	//fprintf(stdout, "EN C Valeur lumineux : %i \n", bvalue);

	//On détruit les objets associés à notre interface de communication
	DigitalInput_destroy(pinCapteur);
	Com_destroy(com);
	fprintf(fp, "</log></record>\n");
	fclose(fp);

	return bvalue;
}

//Fonction qui nous renvoie la valeur du capteur lumineux passé en paramètre d'entrée : 
//Permet de "transferer"/"traduire" le code C en Java 
//Définition du nom de la fonction : JNIEXPORT typeValeurRetournée JNICALL Java_nomClasse_nomMéthodeJava
JNIEXPORT jboolean JNICALL Java_CptLumW_valeurCptLumW
  (JNIEnv *env, jobject obj, jint iNumPin, jstring hostname){
	
	//Fonction permettant de transformer le type natif jstring en const char*
	const char* nativeString = (*env)->GetStringUTFChars(env, hostname, NULL);
	
	if(nativeString == NULL){
		return -1;
	}
	else{
		//Retourne au programme Java la valeur du capteur lumineux passé en paramètre d'entrée
		return valeurCapteurLumineux_c(iNumPin, nativeString);

		//Fonction permettant de libérer la variable de caractère
		(*env)->ReleaseStringUTFChars(env, hostname,nativeString);
	}

}
