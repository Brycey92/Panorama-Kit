package panoramakit.render;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MouseHelper;
import panoramakit.accessor.EntityRendererAccessor;
import panoramakit.util.LockableMouseHelper;

/**
 * CompositeImage
 * 
 * @author dayanto
 */
public abstract class CompositeImageRenderer {
	private static final Minecraft mc = Minecraft.getMinecraft();

	// accessor
	private EntityRendererAccessor era = new EntityRendererAccessor();

	// saved game settings
	private boolean hideGui;
	private boolean advancedOpengl;
	private MouseHelper mouseHelper;
	private float fieldOfView;
	
	// screenshot size
	private int screenshotWidth;
	private int screenshotHeight;

	// unmodified display size
	private int displayWidth;
	private int displayHeight;

	// modified display size
//	private int renderWidth;
//	private int renderHeight;

	private TiledScreenshot screenshot;

	public CompositeImageRenderer(int screenshotWidth, int screenshotHeight) {
		this.screenshotWidth = screenshotWidth;
		this.screenshotHeight = screenshotHeight;
	}

	public final void render() throws IOException { // store the game resolution
		displayWidth = mc.displayWidth;
		displayHeight = mc.displayHeight;

		// calculate the optimal resolution not greater than the window size (to
		// avoid excess rendering)
//		double tilesX = Math.ceil((double) screenshotWidth / (double) displayWidth);
//		double tilesY = Math.ceil((double) screenshotHeight / (double) displayHeight);
//		renderWidth = (int) Math.ceil(screenshotWidth / tilesX);
//		renderHeight = (int) Math.ceil(screenshotHeight / tilesY);
		// renderWidth = mc.displayWidth;
		// renderHeight = mc.displayHeight;
//		System.out.println("Resolution:");
//		System.out.println("X - " + displayWidth + " : " + renderWidth);
//		System.out.println("Y - " + displayHeight + " : " + renderHeight);

		screenshot = new TiledScreenshot(screenshotWidth, screenshotHeight, displayWidth, displayHeight);

		applyMods();

		try {
			assembleImage();
		} finally {
			restoreMods();
		}
	}

	public abstract void assembleImage() throws IOException;

	public int[] captureScreenshot() {
		screenshot.capture();
		return screenshot.getScreenshot();
	}

	/**
	 * Borrowed method from Mineshot
	 */
	private void applyMods() { 
		// TODO Implement some sort of renderActive = true;

		// hide GUI elements, they would appear on each tile otherwise
		hideGui = mc.gameSettings.hideGUI;
		mc.gameSettings.hideGUI = true;
		
		// disable advanced OpenGL features, they cause flickering on render chunks
		advancedOpengl = mc.gameSettings.advancedOpengl;
		mc.gameSettings.advancedOpengl = false;
		
		// change the field of view to a quarter circle (90 degrees)
		fieldOfView = mc.gameSettings.fovSetting;
		mc.gameSettings.fovSetting = 90;
		
		// lock the mouse to prevent misaligned tiles
		LockableMouseHelper mouseHelperLocked = new LockableMouseHelper();
		mouseHelperLocked.setGrabbing(false);
		mouseHelperLocked.setLocked(true);
		mouseHelper = mc.mouseHelper;
		mc.mouseHelper = mouseHelperLocked;

		// disable entity frustum culling for all loaded entities
		for (Object obj : mc.theWorld.loadedEntityList) {
			Entity ent = (Entity) obj;
			ent.ignoreFrustumCheck = true;
			ent.renderDistanceWeight = 16;
		}

		
		
		// resize display to optimal resolution (to avoid excess rendering)
//		mc.displayWidth = renderWidth;
//		mc.displayHeight = renderHeight;
	}

	/**
	 * Borrowed method from Mineshot
	 */
	private void restoreMods() { // restore camera settings
		if (era != null) {
			era.setCameraZoom(1);
			era.setCameraOffsetX(0);
			era.setCameraOffsetY(0);
		}

		// restore game resolution
//		mc.displayWidth = displayWidth;
//		mc.displayHeight = displayHeight;

		// restore user settings
		mc.gameSettings.hideGUI = hideGui;
		mc.gameSettings.advancedOpengl = advancedOpengl;
		mc.gameSettings.fovSetting = fieldOfView;

		// unlock mouse
		mc.mouseHelper = mouseHelper;

		// enable entity frustum culling
		for (Object obj : mc.theWorld.loadedEntityList) {
			Entity ent = (Entity) obj;
			ent.ignoreFrustumCheck = false;
			ent.renderDistanceWeight = 1;
		}

		// TODO Implement renderActive = false;
	}
}