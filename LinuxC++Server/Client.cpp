#include "SharedObject.h"
#include "Semaphore.h"
#include "socket.h"
#include <iostream>
#include <stdlib.h>
#include <time.h>

int main(void)
{
    try
    {
        Socket theSocket("127.0.0.1", 2000);
        theSocket.Open();
        std::string entry = "";
        while (entry != "done")
        {
            std::cout << "Enter a string to send: " << std::endl;
            FlexWait waiter(2,&cinWatcher,&theSocket);
            Blockable * result = waiter.Wait(); // blocking call FOR SURE.
            if (result == &theSocket)
            {
                
                ByteArray dummy;
                if (theSocket.Read(dummy) <= 0)
                {
                    std::cout << "We have likely received a disconnection command" << std::endl;
                    std::cout << "The socket has been closed suddenly" << std::endl;
                    break;
                }
                else
                {
                    entry = dummy.ToString();
                    std::cout << "Received: " << entry << " from master" << std::endl;
                    continue;
                }
                //break;
            }
            else
            {
                std::cin >> entry;
                ByteArray ba(entry);
                int written = theSocket.Write(ba);
                if ( written != ba.v.size())
                {
                    std::cout << "Wrote: " << written << std::endl;
                    std::cout << "The socket appears to have been closed suddenly" << std::endl;
                    break;
                }
                else
                {
                    std::cout << "Sent: " << entry << std::endl;
                }
                if (theSocket.Read(ba) <=0)
                {
                    std::cout << "The socket appears to have been closed suddenly" << std::endl;
                    break;
                }
                std::cout << "Received: " << ba.ToString() << std::endl;
            }
        }
        std::cout << "Sleep now" << std::endl;
        theSocket.Close();
        sleep(1);
    }
    catch(std::string s)
    {
        std::cout << "there was an error" << s << std::endl;

    }
    catch(...)
    {
        std::cout << "Caught unexpected exception" << std::endl;
    }

}
