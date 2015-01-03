
package GameStateManager;

import Background.Background;
import Handler.FontConvert;
import Handler.ImageLoader;
import Handler.Keys;
import static Handler.Keys.DOWN;
import static Handler.Keys.ENTER;
import static Handler.Keys.UP;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MenuState extends GameState {

	private Background bg;


	private static final BufferedImage logo = ImageLoader.logo;

	private int currentChoice = 0;

	private static final String[] options = { "Start", "Quit" };

	public MenuState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {

		bg = new Background();

	}

	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		g.drawImage(logo, 150, 10, null);

		for (int i = 0; i < options.length; i++) {
			if (i != currentChoice) {

				g.drawImage(FontConvert.convertString2Image(options[i], true),
						210, 200 + i * 40, null);

			} else {

				g.drawImage(
						FontConvert.convertString2Image(options[i], false),
						210, 200 + i * 40, null);
			}
		}
	}

	@Override
	public void handleInput() {
		if (Keys.isFirstPress(UP)) {
			currentChoice -= 1;

			if (currentChoice < 0) {
				currentChoice = options.length - 1;
			}
		}
		if (Keys.isFirstPress(DOWN)) {
			currentChoice += 1;

			if (currentChoice == options.length) {
				currentChoice = 0;

			}

		}
		if (Keys.isFirstPress(ENTER)) {
			selective();
		}
	}

	public void selective() {
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.LEVELSTATE);
		}
		if (currentChoice == 1) {
			System.exit(0);
		}
	}

}
