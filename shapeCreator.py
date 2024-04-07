import time
start = 0
pi = 3.141592653589793238

# This is a project I made during my senior year of high school. Back then I created a program that draws the  listed shapes in the array except for circle,
# using formatting and math to determine where to draw the lines and the total area.
def Draw(shape):
    if shape == "Square":
        length = input("Length of Square? ")
        checker = str(length.isdigit())
        while checker == "False":
            length = input("Must Be A Number")
            checker = str(length.isdigit())
        width = input("Width of Square?")
        checker = str(width.isdigit())
        while checker == "False":
            width = input("Must Be A Number")
            checker = str(width.isdigit())
        lengthprint = "-"*length
        print(f'{lengthprint}')
        spaceprint = " "*length
        print(f'|{spaceprint}|'*width)
        print(f'{lengthprint}')

def shape(type):
    shapes = ["Triangle", "Circle", "Square"]
    type = input("")
    if str(type) == "Quit":
        return type
    else:
        checker = str(type.isdigit())
        while checker == "True":
            type = input("Cannot Be A Number Must Be A Shape")
        else:
            max = 0
            in_shape = False
            for item in shapes:
                if type == shapes[max]:
                    shape = shapes[max]
                    in_shape = True
                else:
                    max = max + 1
                    in_shape = False
            if in_shape != False:
                print(shape)
            else:
                print("We Do Not Have That Kind Of Shape, Or What You Typed in Isnt a Shape")

def numparams(num,type):
    num = input(f'Enter {type} ')
    checker = str(num.isdigit())
    while checker == "False":
        num = input(f'{type} Must Be A Number ')
        checker = str(num.isdigit())
    else:
        return num

#length = numparams(0, "Length")
#width = numparams(0, "Width")


i = 90
while i <= 100:
    print(f'Loading {i}%')
    print(f'{100 - int(i)} Assets Left')
    i = i+1
    time.sleep(0.1)

key = " "
while key != "Quit":
    print("Welcome To The Shape Creator, Which Shape Would You Like To Create?(Type In STOP to quit)")
    key = shape(0)
    if key != "Quit":
        Draw(str(key))
    else:
        if key == "Quit":
            break
