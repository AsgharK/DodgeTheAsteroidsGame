#ifndef COMMUNICATE_H
#define COMMUNICATE_H

#include <string>

/*
This class is used to communicate between our game 
thread and the client threads that the game thread makes.
That way having each thread class in a seperate header file 
can be managed easier and communication between threads are possible without a shared object 
*/


class Communicate {
public:
	// send an updated message to clients for them to render
	virtual void sendReport(int playerID){};
	
	//reports when client has lost
	virtual void reportLose(int playerID){};

	// get information from clients to calculate updates.
	virtual std::string receiveReport(){};
	
	virtual void endGame(){};
};

#endif // 
