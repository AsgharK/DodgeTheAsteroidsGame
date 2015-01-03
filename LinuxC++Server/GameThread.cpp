
#include "ClientThread.cpp"
#include "Communicate.h"
#include <string>
#include <cmath>
#include <ostream>

using namespace std;


class GameThread : public Thread, public Communicate
{
private:
    Socket theSocket1;
    Socket theSocket2;
    std::vector<ClientThread *> clientThreads;
	std::string message;
   	Player player1;
	Player player2;
	bool flag;

public:
    GameThread(Socket const & p1, Socket const & p2)
        : Thread(true),theSocket1(p1), theSocket2(p2)
    {
        ByteArray ba("CONNECTED1");
		ByteArray bb("CONNECTED2");
        theSocket1.Write(ba);
        theSocket2.Write(bb);
		flag=false;
    }

    /*
        receivereport will send information back to the client threads 
        with updated information for clients to render.
    */
	virtual std::string receiveReport()
	{
		string Result;
		stringstream convert; 
		convert << ",p1,"<<player1.getX()<<","<<player1.getY()<<","<<"p2,"<<player2.getX()<<","<<player2.getY();
		Result = convert.str();
		return Result;
	}

	//this function reports the lose to the winner so it knows the other player has lost
	virtual void reportLose(int playerID)
	{
		if(playerID==1){
			string Result="lose\n";
			ByteArray bx(Result);
            theSocket2.Write(bx);
		}
		else if(playerID==2){
			string Result="lose\n";
			ByteArray bx(Result);
            theSocket1.Write(bx);
		}
	}
	//to tell gamethread the game has ended
	virtual void endGame(){
		flag=true;
	}


    /*  
       	This is updateing player after the Client has pressed a move key.
		Also it is setting all the keys back to false since update is done.
      	
    */
	virtual void sendReport(int playerID)
	{
		if(playerID==1){
			player1.update();
			player1.setAll();
		}
		else if(playerID==2){
			player2.update();
			player2.setAll();
		}
	}

	//main that keeps running until game ends or sleep is called
    long ThreadMain(void)
    {
        int player1Num = 1;
        int player2Num = 2;

        std::cout << "Created a thread to use 2 clients together!" << std::endl;
        clientThreads.push_back(new ClientThread(theSocket1, player1Num, this, &player1));
        clientThreads.push_back(new ClientThread(theSocket2, player2Num, this, &player2));

        while(1)
        {
			
            //fire is a check so a new asteroid is sent at random instead of always
			//x is the new x value of the asteroid
			int fire = (int)(rand() % (100));
			int x = (int)(rand() % (600) + 20);
			string Result;
			//convert x to a string to be sent
			stringstream convert; 
			convert << x;
			Result = convert.str();

			//asteriod x value equal to zero so no asteroid would be rendered
			if(fire<=97){			
				Result = "0";
			}
			/*
				receive report about the position of both players and add the information about asteroid and send
				it to client through socket so client can render the graphics based on information
			*/
            std::string toSend = receiveReport();
            Result=Result+toSend+"\n";
            ByteArray bx(Result);
            theSocket1.Write(bx);
        	theSocket2.Write(bx);
		
			//if game ended break
			if(flag){
				break;
			}
			
            usleep(17000);
 

        }
        //if game ended then stop the client threads
        for (int i=0;i<clientThreads.size();i++)
        {
            cout <<"Stopping Thread" << endl;
			clientThreads[i]->Stop();
        }
        
		cout<<"Stopping game thread."<<endl;
		Stop();
        
    }
    ~GameThread(void)
    {
		for (int i=0;i<clientThreads.size();i++)
        {
			delete clientThreads[i];
        }
        terminationEvent.Wait();
        theSocket1.Close();
        theSocket2.Close();
    }
};
