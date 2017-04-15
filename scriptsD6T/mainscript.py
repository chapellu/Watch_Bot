import omron, time


if __name__ == '__main__':
    script = True

    omron = omron.OmronD6T()
    omron.init()



    while script:


        # On regarde toutes les secondes si on a pas arreter le script
        flagscript = open("/var/www/html/Watch_Bot/scriptsD6T/flagscript.txt", "r")

        i = 0
        with flagscript as f:
            for line in f:
                if i == 0:
                    if line == 'script=False':
                        script = False
                elif i == 1:
                    seuil = line
                i += 1

        omron.read('exact-temp', float(seuil))

        time.sleep(1)