package mta.se.models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;


public class Block extends ModelInstance {
	public final static float BLOCK_RADIUS = 0.5f;

	public Block (Model model, float x, float y, float z) {
		super(model, x, y, z);
	}
}