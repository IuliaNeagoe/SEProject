package mta.se.models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class Invader extends ModelInstance {
	public static float INVADER_ROTATION = 45f;
	public static float INVADER_RADIUS = 0.75f;
	public static float INVADER_VELOCITY = 1;
	public static int INVADER_POINTS = 40;
	public final static int STATE_MOVE_LEFT = 0;
	public final static int STATE_MOVE_DOWN = 1;
	public final static int STATE_MOVE_RIGHT = 2;

	public int state = STATE_MOVE_LEFT;
	public boolean wasLastStateLeft = true;
	public float movedDistance = Simulation.PLAYFIELD_MAX_X / 2;;

	public Invader (Model model, float x, float y, float z) {
		super(model, x, y, z);
	}

	public void update (float delta, float speedMultiplier) {
		movedDistance += delta * INVADER_VELOCITY * speedMultiplier;
		if (state == STATE_MOVE_LEFT) {
			transform.trn(-delta * INVADER_VELOCITY * speedMultiplier, 0, 0);
			if (movedDistance > Simulation.PLAYFIELD_MAX_X) {
				state = STATE_MOVE_DOWN;
				movedDistance = 0;
				wasLastStateLeft = true;
			}
		}
		if (state == STATE_MOVE_RIGHT) {
			transform.trn(delta * INVADER_VELOCITY * speedMultiplier, 0, 0);
			if (movedDistance > Simulation.PLAYFIELD_MAX_X) {
				state = STATE_MOVE_DOWN;
				movedDistance = 0;
				wasLastStateLeft = false;
			}
		}
		if (state == STATE_MOVE_DOWN) {
			transform.trn(0, 0, delta * INVADER_VELOCITY * speedMultiplier);
			if (movedDistance > 1) {
				if (wasLastStateLeft)
					state = STATE_MOVE_RIGHT;
				else
					state = STATE_MOVE_LEFT;
				movedDistance = 0;
			}
		}
		transform.rotate(0, 1, 0, INVADER_ROTATION * delta);
	}
}