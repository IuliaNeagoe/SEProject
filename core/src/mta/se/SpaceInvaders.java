package mta.se;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.FPSLogger;
import mta.se.views.GameLoopScreens;
import mta.se.views.InvadersScreen;
import mta.se.views.MainMenuScreen;

public class SpaceInvaders extends Game {

    //private Music music;
    private Controller controller;
    private FPSLogger fps;

    public Controller getController() {
        return controller;
    }

    @Override
    public void render () {
        InvadersScreen currentScreen = getScreen();

//        update the screen
        currentScreen.render(Gdx.graphics.getDeltaTime());

        if (currentScreen.isDone()) {
            // dispose the resources of the current screen
            currentScreen.dispose();

            // if the current screen is a main menu screen we switch to
            // the game loop
            if (currentScreen instanceof MainMenuScreen)
                setScreen(new GameLoopScreens(this));

        }

        fps.log();
    }

    @Override
    public void create () {
        setScreen(new MainMenuScreen(this));
//        music = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/music.mp3", Files.FileType.Internal));
//        music.setLooping(true);
//        music.play();


        fps = new FPSLogger();
    }

    /** For this game each of our screens is an instance of InvadersScreen.
     * @return the currently active {@link InvadersScreen}. */
    @Override
    public InvadersScreen getScreen () {
        return (InvadersScreen)super.getScreen();
    }

}