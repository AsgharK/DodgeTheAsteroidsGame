#include "SharedObject.h"
#include "Semaphore.h"
#include "thread.h"
#include "socketserver.h"
#include <stdlib.h>
#include <time.h>
#include <list>
#include <pthread.h>
#include <vector>
#include <string>
#include <sstream>
#include "GameThread.cpp"

using namespace std;


int main(void)
{
	//creating a socket server that listens at port 2000 and a vector that holds all the gamethreads
    std::cout << "I am a socket server.  Type 'quit' to exit" << std::endl;
    SocketServer theServer(2000);
    std::vector<GameThread *> gameThreads;

	//for loop that keeps going accepting new players and creating new game threads for each pair until server is closed
    for(;;)
    {
        try
        {
            FlexWait waiter(2,&theServer,&cinWatcher);
            Blockable * result = waiter.Wait();
            if (result == &cinWatcher)
            {
                std::string s;
                std::cin >> s;
                if (s=="quit")
                {
                    // No need to call SocketServer::Shutdown.  It isn't active.
					// stoping the threads and deleting the pointers
                    for (int i=0;i<gameThreads.size();i++)
                    {
                        cout << "deleting a gamethread..." << endl;
                        gameThreads[i]->Stop();
						gameThreads[i] = NULL;
                        delete gameThreads[i];
                    }
                    break;
                }
                else
                    continue;
            }
            //Accept is a blocking call so it waits for two players to connect before it creates a new gamethread
            Socket newSocket1 = theServer.Accept();
            Socket newSocket2 = theServer.Accept();
            std::cout << "Creating a game..." << std::endl;
            gameThreads.push_back(new GameThread(newSocket1, newSocket2));
        }
        catch(TerminationException e)
        {
            std::cout << "The socket server is no longer listening. Exiting now." << std::endl;
            break;
        }
        catch(std::string s)
        {
            std::cout << "thrown " << s << std::endl;
            break;
        }
        catch(...)
        {
            std::cout << "caught unknown exception" << std::endl;
            break;
        }
    }
    std::cout << "Sleep now" << std::endl;
    sleep(1);
    std::cout << "End of main" << std::endl;

}
