import omron, time

if __name__ == '__main__':
    seuil = 28
    omron = omron.OmronD6T()
    omron.init()

    while True:
        omron.read('auto',seuil)

        time.sleep(1)

        # for j in range(0,4):
        # 	for i in range(0,4):
        # 		if(omron.temperature[i+offset]>seuil):
        # 			values.append("x ")
        # 		else:
        # 			values.append("o ")
        # 	print values
        # 	values = []
        # 	print ''
        # 	offset+=4
        # time.sleep(1)