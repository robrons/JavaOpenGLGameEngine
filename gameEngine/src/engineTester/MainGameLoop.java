package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		RawModel model2 = OBJLoader.loadObjModel("bunny", loader);
		RawModel model3 = OBJLoader.loadObjModel("dragon", loader);

		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel staticModel2 = new TexturedModel(model2,new ModelTexture(loader.loadTexture("white")));
		TexturedModel staticModel3 = new TexturedModel(model2,new ModelTexture(loader.loadTexture("white")));

		ModelTexture texture = staticModel.getTexture();
		texture.setReflectivity(1);
		texture.setShineDamper(10);
		
		ModelTexture texture2 = staticModel2.getTexture();
		texture2.setReflectivity(1);
		texture2.setShineDamper(10);
		
		List<Entity> entities = new ArrayList<Entity>();
		
		entities.add(new Entity(staticModel, new Vector3f(9,0,-20),0,0,0,3));
		entities.add(new Entity(staticModel2, new Vector3f(-9,0,-20),0,0,0,0.6f));
		entities.add(new Entity(staticModel3, new Vector3f(0,0,-20),0,0,0,0.6f));

	
		
		Light light = new Light(new Vector3f(10,10,10),new Vector3f(1,1,1));
		
		
		Camera camera = new Camera();	
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
			camera.move();
			
			
		
			for(Entity entity:entities){
	
				entity.increaseRotation(0, 0, 0);
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
