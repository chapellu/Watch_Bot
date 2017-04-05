import omron, time


if __name__ == '__main__':
    seuil = 28
    script = True

    omron = omron.OmronD6T()
    omron.init()



    while script:
        omron.read('auto', seuil)

        # On regarde toutes les secondes si on a pas arreter le script
        flagscript = open("flagscript.txt", "r")
        text = flagscript.read()
        flagscript.close()
        if text == 'script=False':
            script = False


        time.sleep(1)