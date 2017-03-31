import omron, time

if __name__ == '__main__':
    seuil = 28

    omron = omron.OmronD6T()
    omron.init()

    while True:
        omron.read('auto', seuil)

        time.sleep(1)