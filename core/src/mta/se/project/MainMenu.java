package mta.se.project;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import mta.se.project.view.InvadersScreen;
import mta.se.project.view.SpaceInvaders;

/**
 * Created by IuliS on 10.01.2015.
 */
public class MainMenu extends InvadersScreen {
    /** the SpriteBatch used to draw the background, logo and text **/
    private final SpriteBatch spriteBatch;
    /** the background texture **/
    private final Texture background;
    /** the logo texture **/
    private final Texture logo;
    /** the font **/
    private final BitmapFont font;
    /** is done flag **/
    private boolean isDone = false;
    /** view & transform matrix **/
    private final Matrix4 viewMatrix = new Matrix4();
    private final Matrix4 transformMatrix = new Matrix4();

    public MainMenu (SpaceInvaders invaders) {
        super(invaders);

        spriteBatch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("data/planet.jpg"));
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        logo = new Texture(Gdx.files.internal("data/logo.png"));
        logo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

        if (invaders.getController() != null) {
            invaders.getController().addListener(new ControllerAdapter() {
                @Override
                public boolean buttonUp(Controller controller, int buttonIndex) {
                    controller.removeListener(this);
                    isDone = true;
                    return false;
                }
            });
        }
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void update (float delta) {
        if (Gdx.input.justTouched()) {
            isDone = true;
        }
    }

    @Override
    public void draw (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewMatrix.setToOrtho2D(0, 0, 480, 320);
        spriteBatch.setProjectionMatrix(viewMatrix);
        spriteBatch.setTransformMatrix(transformMatrix);
        spriteBatch.begin();
        spriteBatch.disableBlending();
        spriteBatch.setColor(Color.WHITE);
        spriteBatch.draw(background, 0, 0, 480, 320, 0, 0, 512, 512, false, false);
        spriteBatch.enableBlending();
        spriteBatch.draw(logo, 0, 320 - 128, 480, 128, 0, 0, 512, 256, false, false);
        spriteBatch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        String text = "To start touch the screen";
        float width = font.getBounds(text).width;
        font.draw(spriteBatch, text, 240 - width / 2, 128);
        if (Gdx.app.getType() == Application.ApplicationType.WebGL) {
            text = "Press Enter for Fullscreen Mode";
            width = font.getBounds(text).width;
            font.draw(spriteBatch, "Press Enter for Fullscreen Mode", 240 - width / 2, 128 - font.getLineHeight());
        }
        spriteBatch.end();
    }

    @Override
    public void dispose () {
        spriteBatch.dispose();
        background.dispose();
        logo.dispose();
        font.dispose();
    }
}
