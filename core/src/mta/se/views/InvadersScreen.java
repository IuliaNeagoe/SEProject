package mta.se.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import mta.se.controllers.SpaceInvaders;

public abstract class InvadersScreen implements Screen{
    private Music music;
    protected SpaceInvaders invaders;

    public InvadersScreen(SpaceInvaders invaders) {
        this.invaders = invaders;
    }

    /** Called when the screen should update itself, e.g. continue a simulation etc. */
    public abstract void update (float delta);

    /** Called when a screen should render itself */
    public abstract void draw (float delta);

    /** Called by GdxInvaders to check whether the screen is done.
     * @return true when the screen is done, false otherwise */
    public abstract boolean isDone();



    @Override
    public void render (float delta) {
        update(delta);
      draw(delta);
//        music = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/music.mp3", Files.FileType.Internal));
//        music.setLooping(true);
//        music.play();
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void show () {
    }

    @Override
    public void hide () {
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
    }
}