import omron, time

if __name__ == '__main__':
    seuil = 28
    omron = omron.OmronD6T()
    omron.init()

    values = []
    while True:
        omron.read()
        offset= 0

        for j in range(0,4):
            for i in range(0,4):
                values.append(round(omron.temperature[i+offset],1))
                values.append("------")
            print values
            values = []
            offset+=4
        print ''
        print ''
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