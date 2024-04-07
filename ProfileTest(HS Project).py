import time

# Made This Project During High School Senior Year
# A Profile System That Takes In Your Name, Birthday, and any additional information you want to add to it.
def numtobm(number):
    months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
    selected_month = months[number-1]
    return selected_month

def FixBirthDate(bm, type): #Changing Birth Month To Number
    if type == "Month":
        boolchecker = str(bm.isdigit())
        while boolchecker == "False":
            bm = input("Choose A Month (Must Choose A Number)")
            boolchecker = str(bm.isdigit())
        else:
            while int(bm) < 1:
                bm = input("Choose A Month (Must Be A Number From 1-12)")
        return bm
    elif type == "Day":
        boolchecker = str(bm.isdigit())
        while boolchecker == "False":
            bm = input("Choose A Day (Must Choose A Number)")
            boolchecker = str(bm.isdigit())
        else:
            while int(bm) < 1 or int(bm) > 31:
                bm = input("Choose A Day (Must Be A Number From 1-12)")
    elif type == "Year":
        boolchecker = str(bm.isdigit())
        while boolchecker == "False":
            bm = input("Choose A year (Must Choose A Number)")
            boolchecker = str(bm.isdigit())
        else:
            return bm

ProfileName = [input("State Your First Name "), input("State Your Last Name ")]
BirthMonthCheck1 = input("Birth Month ")
BirthMonth = FixBirthDate(BirthMonthCheck1, "Month")
BirthDayCheck1 = input("Birth Day ")
BirthDay = FixBirthDate(BirthDayCheck1, "Month")
BirthYearCheck1 = input("Birth Year ")
BirthYear = FixBirthDate(BirthYearCheck1, "Year")
CurrentMonthCheck1 = input("Current Month ")
CurrentMonth = FixBirthDate(CurrentMonthCheck1, "Month")
CurrentDayCheck1 = input("Current Day ")
CurrentDay = FixBirthDate(CurrentDayCheck1, "Month")
CurrentYearCheck1 = input("Current Year ")
CurrentYear = FixBirthDate(CurrentYearCheck1, "Month")


ProfileBirthDate = [BirthMonth, BirthDay, BirthYear]
CurrentDate = [CurrentMonth, CurrentDay, CurrentYear]


MaxAge = int(CurrentYear) - int(BirthYear)
BM = int(BirthMonth)
CM = int(CurrentMonth)
BD = int(BirthDay)
CD = int(CurrentDay)
Age = 0
if CM != BM:
    if CM - BM > 0:
        Age = MaxAge
    else:
        Age = MaxAge - 1
else:
    if BD - CD >= 0:
        Age = MaxAge
    else:
        Age = MaxAge - 1

load = 1
while load < 100:
    print(f'Loading {load}%')
    load = load + 1
    time.sleep(0.1)

Profile = { #Dictionary
    "Name": ProfileName,
    "BirthDate": [numtobm(int(ProfileBirthDate[0])), ProfileBirthDate[1], ProfileBirthDate[2]],
    "Age": Age
}
print(f'Hi {Profile.get("Name")[0] + " " +  Profile.get("Name")[1]} You Were Born On {str(Profile.get("BirthDate")[0]) + " " + str(Profile.get("BirthDate")[1]) +  " " + str(Profile.get("BirthDate")[2])} And You Are {Profile.get("Age")} Years Old')


i = 1
while i != 999:
    print(f'Welcome To The Main Menu {Profile.get("Name")[0]}, For The Options Type in 1, To quit type in 999 ')
    i = int(input(""))
    if i == 999:
        i = 999
    elif i == 1:
        print("Type In 2 to view your ID, Type in 3 To Add Additional Information ")
        secondans = int(input(""))
        if secondans == 2:
            max = 0
            for itemz in Profile:
                max = max + 1

            card = [
                [f'-----------------------------------------------'],
            ]

            for item in Profile:
                card.append([f'{item}: {Profile.get(str(item))}'])
            card.append([f'-----------------------------------------------'],)

            for m in card:
                num = 0
                print(m[num])
                num = num + 1
        elif secondans == 3:
            print("Add Your Additional Information ")
            additionalinfo = input("Name of Additional Information (Anything You Chose) ")
            Profile[f'{additionalinfo}'] = input("Description of the Additional Info ")
