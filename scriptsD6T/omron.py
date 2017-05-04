import pigpio, time, smbus, socket
from datetime import datetime

class OmronD6T(object):
    chemin = '/var/www/html/Watch_Bot/scriptsD6T/'

    def init(self, rasPiChannel = 1, omronAddress = 0x0a, omronSize = 16):
        self.MAX_RETRIES = 5
        self.roomTemp = 0 #Init temp
        self.rasPiChannel = rasPiChannel	#Numero de bus dans la raspberry
        self.omronAddress = omronAddress #Adresse du capteur dans la Rasp
        self.arraySize = omronSize #Taille des donnees du capteur
        self.BUFFER_LENGTH=(self.arraySize * 2) + 3       # Taille du buffer
        self.piGPIO = pigpio.pi()
        self.piGPIOver = self.piGPIO.get_pigpio_version()
        self.i2cBus = smbus.SMBus(1)
        time.sleep(0.1)

            # initialize the device based on Omron's appnote 1
        self.retries = 0
        self.result = 0
        for i in range(0,self.MAX_RETRIES):
            time.sleep(0.05)                               # Wait a short time
            self.handle = self.piGPIO.i2c_open(self.rasPiChannel, self.omronAddress) # open Omron D6T device at address 0x0a on bus 1
            print self.handle
            if self.handle > 0:
                self.result = self.i2cBus.write_byte(self.omronAddress,0x4c)
            break
        else:
            print ''
            print '***** Omron init error ***** handle='+str(self.handle)+' retries='+str(self.retries)
            self.retries += 1


	# function to read the omron temperature array

    #Fonction qui recupere la temperature
    def read(self, mode, seuil):
        self.temperature_data_raw=[0]*self.BUFFER_LENGTH
        self.temperature=[0.0]*self.arraySize         # holds the recently measured temperature
        self.values=[0]*self.BUFFER_LENGTH

        # read the temperature data stream - if errors, retry
        retries = 0
        for i in range(0,self.MAX_RETRIES):
            time.sleep(0.05)                               # Wait a short time
            (self.bytes_read, self.temperature_data_raw) = self.piGPIO.i2c_read_device(self.handle, self.BUFFER_LENGTH)

            # Handle i2c error transmissions
            if self.bytes_read != self.BUFFER_LENGTH:
                print '***** Omron Byte Count error ***** - bytes read: '+str(self.bytes_read)
                self.retries += 1                # start counting the number of times to retry the transmission


            else:
                t = (self.temperature_data_raw[1] << 8) | self.temperature_data_raw[0]
                self.tPATc = float(t) / 10

                # Convert Raw Values to Temperature ('F)
                a = 0

                for i in range(2, len(self.temperature_data_raw) - 2, 2):
                    self.temperature[a] = float(
                        (self.temperature_data_raw[i + 1] << 8) | self.temperature_data_raw[i]) / 10
                    a += 1

                for i in range(0, self.bytes_read):
                    self.values[i] = self.temperature_data_raw[i]

        #Affichage selon les modes


        if mode=='exact-temp':
            self.printExactTemp()


        elif mode=='symbols':
            self.printSymbols(seuil)


        elif mode=='auto':
            self.printAuto(seuil)
    #End function to read temperature and print

    # Fonction qui permet de recuperer la date au bon format
    def date(self):
        date = datetime.today()

        date1 = date.ctime().split(" ")
        d = ""
        d1 = ""
        for i in range(0, 5):
            if date.timetuple()[i] < 10:
                d = d + "0" + str(date.timetuple()[i]) + "-"
        else:
            d = d + str(date.timetuple()[i]) + "-"
        d = d + str(date.timetuple()[5])
        d1 = d1 + date1[1] + " " + date1[2] + ", " + date1[4] + " " + date1[3] + " "

        if date.timetuple()[3] >= 12:
            d1 = d1 + "PM"
        else:
            d1 = d1 + "AM"

        return (d, d1)

    #
    # Methodes qui gerent l affichage selon le mode
    #
    #Mode automatique avec ecriture dans log
    def printAuto(self, seuil):
        values = []
        # Recupere les valeurs sous formes dun tableau
        for j in range(0, 4):
            for i in range(0, 4):
                values.append(round(self.temperature[i + 4 * j], 1))

        # Compteur de valeurs superieurs au seuil
        compteurDeColonnes = 0
        for j in range(0, 4):
            compteurDePixels = 0

            for i in range(0, 4):
                tempPixel = values[j + 4 * i]  # Recupere les valeurs des pixels colonne par colonne
                if tempPixel >= seuil and tempPixel < 50:
                    compteurDePixels += 1
                    numpixel = i + j * 4
                    print 'pixel detecte:', numpixel
            if compteurDePixels >= 3: #Si on a plus de 3 pixels dans une meme colonne au dessus du seuil
                compteurDeColonnes += 1

        if compteurDeColonnes >= 1: #Si on a plus de x colonne on considere quon a detecte un humain et on fait sonner lalarme
            #On ecrit dans un log
            print "On detecte"
            log = open(self.chemin + "log.txt", "a")
            log.write("<tr><td>" + datetime.now().strftime('%Y-%m-%d %H:%M:%S') + "-- Humain detecte" + "</td></tr>\n")
            log.close()

            file = open(self.chemin + "log.txt", "r")
            text = file.read()
            file.close()
            text = text.replace('\n', '')

            log = open(self.chemin + "log.txt", "w")
            log.write(text)
            log.close()
            #Fin ecriture log

            #On envoie linfo au serveur quon a detecte une intrusion
            hote = "localhost"
            port = 50003

            socketServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            socketServer.connect((hote, port))
            (d, d1) = self.date()
            socketServer.send('{"AuteurPrecedent":{"nom":"Raspberry","IP":"193.48.125.196"},"Destinataire":{"nom":"Raspberry","IP":"193.48.125.196"},"Date":{"date_string":' + d + ',"date":"' + d1 + '"},"type":Ordre,"message":"intruderDetected"}')
            socketServer.close()

            time.sleep(5)  # On attend avant dafficher une prochaine fois qu un humain a ete detecte
    #End print auto

    #Avec les temperatures exactes
    def printExactTemp(self):
        offset = 0
        values = []
        for j in range(0, 4):
            for i in range(0, 4):
                values.append(round(self.temperature[i + offset], 1))
                values.append("------")
            print values
            values = []
            offset += 4
            print ''
            print ''
    #End temp exactes

    #Avec les symboles
    def printSymbols(self, seuil):
        offset = 0
        values = []
        for j in range(0, 4):
            for i in range(0, 4):
                if (self.temperature[i + offset] > seuil):
                    values.append("x ")
                else:
                    values.append("o ")
            print values
            values = []
            offset += 4
            print ''
            print ''
    #End symbols

