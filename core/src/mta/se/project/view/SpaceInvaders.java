package mta.se.project.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.FPSLogger;
import mta.se.project.MainMenu;

public class SpaceInvaders extends Game {

    private Music music;
    private Controller controller;
    private FPSLogger fps;
    @Override
    public void render () {
        InvadersScreen currentScreen = getScreen();

//        update the screen
        currentScreen.render(Gdx.graphics.getDeltaTime());

        if (currentScreen.isDone()) {
            // dispose the resources of the current screen
            currentScreen.dispose();
         setScreen(new MainMenu(this));

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
