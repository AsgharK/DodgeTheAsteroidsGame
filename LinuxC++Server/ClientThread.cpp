
#include "Communicate.h"
#include <string>
#include "Player.cpp"

class GameThread;
using namespace std;

class ClientThread : public Thread
{
private:
    Socket theSocket;
    int playerID;
    Communicate* game;
	Player* player;

public:
    ClientThread(Socket const & p1, int playerNumber, Communicate* parent, Player* play)
        : Thread(true), theSocket(p1), playerID(playerNumber)
    {
		//intialize the player of this client thread and its position depending on if the are player 1 or player 2
	    game = parent;
		player = play;
		if(playerID==1){
			player->setPosition(350, 440);
		}
		else if(playerID==2){
			player->setPosition(250, 440);
		}
    }
    long ThreadMain(void)
    {
		//ByteArray that reads the socket
        ByteArray bytes;
		bytes.v.clear();
        std::cout << "Created a socket thread!" << std::endl;
        for(;;)
        {
            try
            {
                int read = theSocket.Read(bytes);
                if (read == -1)
                {
                    std::cout << "Error in socket detected" << std::endl;
                    break;
                }
                else if (read == 0)
                {
                    std::cout << "Socket closed at remote end" << std::endl;
                    
                    break;
                }
                else
                {
                    std::string theString = bytes.ToString();

                    // check the message send from client and do action accordingly
					// the client sends a message when they move and the key is set to true accordingly
					if(theString=="left"){
						player->setLeft(true);
						game->sendReport(playerID);
					}
					else if(theString=="right"){
						player->setRight(true);
						game->sendReport(playerID);
					}
					else if(theString=="up"){
						player->setUp(true);
						game->sendReport(playerID);
					}
					else if(theString=="down"){
						player->setDown(true);
						game->sendReport(playerID);
					}
					//checks for lose and reports lose to game
					else if(theString.find("lose") != string::npos){
						game->reportLose(playerID);
						break;
					}
					else{
						continue;
					}
                }
            }
            catch (...)
            {
                cout << "error" << endl;
            }
			usleep(17000);
        }
        std::cout << "Client Thread is gracefully ending" << std::endl;
		game->endGame();
    }
    ~ClientThread(void)
    {
        terminationEvent.Wait();
    }
};
