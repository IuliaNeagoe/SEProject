
package mta.se.models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import mta.se.views.GameScreen;

public class Shot extends ModelInstance {
	public static float SHOT_VELOCITY = 20;
	public boolean isInvaderShot;
	public boolean hasLeftField = false;
	private final static Vector3 tmpV = new Vector3();

	public Shot(Model model, Vector3 position, boolean isInvaderShot) {
		super(model, position);
		// this.position.set(position);
		this.isInvaderShot = isInvaderShot;
	}

	public void update (float delta) {
		if (isInvaderShot)
			transform.trn(0, 0, SHOT_VELOCITY * delta);
		else
			transform.trn(0, 0, -SHOT_VELOCITY * delta);

		transform.getTranslation(tmpV);
		if (tmpV.z > GameScreen.PLAYFIELD_MAX_Z) hasLeftField = true;
		if (tmpV.z < GameScreen.PLAYFIELD_MIN_Z) hasLeftField = true;
	}
}
