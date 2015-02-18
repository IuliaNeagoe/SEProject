package mta.se.controllers;

import com.badlogic.gdx.*;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.utils.Array;
import mta.se.views.GameOver;
import mta.se.views.InvadersScreen;
import mta.se.views.MainMenuScreen;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;


public class SpaceInvaders extends Game {


    private FPSLogger fps;

    private Controller controller;
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

        // update the screen
        currentScreen.render(Gdx.graphics.getDeltaTime());

        // When the screen is done we change to the
        // next screen. Ideally the screen transitions are handled
        // in the screen itself or in a proper state machine.
        if (currentScreen.isDone()) {
            // dispose the resources of the current screen
            currentScreen.dispose();

            // if the current screen is a main menu screen we switch to
            // the game loop
            if (currentScreen instanceof MainMenuScreen) {
                setScreen(new GameLoop(this));
            } else {
                // if the current screen is a game loop screen we switch to the
                // game over screen
                if (currentScreen instanceof GameLoop) {
                    try {
                        setScreen(new GameOver(this));
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (currentScreen instanceof GameOver) {
                    // if the current screen is a game over screen we switch to the
                    // main menu screen
                    setScreen(new MainMenuScreen(this));
                }
            }
        }

        fps.log();
    }

    @Override
    public void create () {
        Array<Controller> controllers = Controllers.getControllers();
        if (controllers.size > 0) {
            controller = controllers.first();
        }
        Controllers.addListener(controllerListener);

        setScreen(new MainMenuScreen(this));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp (int keycode) {
                if (keycode == Input.Keys.ENTER && Gdx.app.getType() == Application.ApplicationType.WebGL) {
                    if (!Gdx.graphics.isFullscreen()) Gdx.graphics.setDisplayMode(Gdx.graphics.getDisplayModes()[0]);
                }
                return true;
            }
        });

        fps = new FPSLogger();
    }

    /** For this game each of our screens is an instance of InvadersScreen.
     * @return the currently active {@link InvadersScreen}. */
    @Override
    public InvadersScreen getScreen () {
        return (InvadersScreen)super.getScreen();
    }
}
