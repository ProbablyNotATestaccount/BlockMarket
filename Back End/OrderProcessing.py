#creates a new order
from collections import UserDict


def createOrder():
    print("a")

#cancels an order
def cancelOrder():
    print("b")

#for when an order is resolved on the purchaser's end
def orderPlaced():
    print("c")

#for when an order
def resolveOrder():
    print("d")

#checks for regularly scheduled events
def checkEvents():
    print("e")

#refreshes info regarding a user
def refreshInfo():
    print("f")

#defines a new event based off of a delay
def addEvent():
    print("g")

#used to set someone as avilable to order
def available():
    print("h")

#used to set someone as not available to order
def disable():
    print("i")

class OrderManager:
    x=1

class Order:
    x=1

class User:
    def __init__(self, UId, name, karma):
        self.UId=UId
        self.name=name
        self.karma=karma

class Orderer(User):
    def __init__(userID, UId, name, karma, locations):
        pass
