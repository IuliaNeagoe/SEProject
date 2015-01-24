package mta.se.views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import mta.se.MainMenu;

public class SpaceInvaders extends Game {

    private Music music;
    private Controller controller;
    private FPSLogger fps;
    private ControllerAdapter controllerListener = new ControllerAdapter(){
        @Override
        public void connected(Controller c) {
            if (controller == null) {
                controller = c;
            }
        }
        @Override
        public void disconnected(Controller c) {
            if (controller == c) {
                controller = null;
            }
        }
    };
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
            if (currentScreen instanceof MainMenu)
                setScreen(new GameLoopScreens(this));

        }

        fps.log();
    }

    @Override
    public void create () {
        setScreen(new MainMenu(this));
        music = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/music.mp3", Files.FileType.Internal));
        music.setLooping(true);
        music.play();
//        Gdx.input.setInputProcessor(new InputAdapter() {
//            @Override
//            public boolean keyUp (int keycode) {
//                if (keycode == Input.Keys.ENTER && Gdx.app.getType() == Application.ApplicationType.WebGL) {
//                    if (!Gdx.graphics.isFullscreen()) Gdx.graphics.setDisplayMode(Gdx.graphics.getDisplayModes()[0]);
//                }
//                return true;
//            }
//        });

        fps = new FPSLogger();
    }

    /** For this game each of our screens is an instance of InvadersScreen.
     * @return the currently active {@link InvadersScreen}. */
    @Override
    public InvadersScreen getScreen () {
        return (InvadersScreen)super.getScreen();
    }

}