
package GameStateManager;

import GameStateManager.LevelState.LevelState;
import GameStateManager.GameState;
//import Main.client.ConnectButtonListener;

import java.awt.Graphics2D;

public class GameStateManager {

    private GameState[] gameState;
    private int currentState;
    private int oldState;
    private int numsGameState = 10;
    public static final int MENUSTATE = 0;
    public static final int LEVELSTATE = 1;
    public static final int PAUSEDSTATE = 7;


    public GameStateManager() {
        gameState = new GameState[numsGameState];
        currentState = MENUSTATE;
        loadState(currentState);

    }

    public void loadPausedState()
    {
        oldState = currentState;
        currentState = PAUSEDSTATE;
        loadState(PAUSEDSTATE);
    }
    
    public void unPausedState()
    {
        unloadState(currentState);
        currentState = oldState ;
    }
    
    
    public void loadState(int state) {
        if (state == MENUSTATE) {
            gameState[state] = new MenuState(this);
        }
        if (state ==  LEVELSTATE) {
            gameState[state] = new LevelState(this);

        }
         if (state == PAUSEDSTATE) {

            gameState[state] = new PausedState(this);

        }
    }

    public void unloadState(int state) {
        gameState[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(state);
    }

    public void update() {
        try {
            gameState[currentState].update();

        } catch (Exception e) {
        }

    }

    public void draw(Graphics2D g) {
        
        try {
            gameState[currentState].draw(g);

        } catch (Exception e) {
        }
    }


}
