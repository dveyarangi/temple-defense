package yarangi.game.temple.actions;

import java.util.Map;

import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.ViewPoint2D;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.actions.IActionController;
import yarangi.graphics.quadraturin.config.InputConfig;
import yarangi.graphics.quadraturin.config.QuadConfigFactory;
import yarangi.graphics.quadraturin.events.UserActionEvent;

public class DefaultActionFactory 
{
	public static Map <String, IAction> fillNavigationActions(final Scene scene, IActionController controller)
	{
		final ViewPoint2D vp = (ViewPoint2D)scene.getViewPoint();
		InputConfig config = QuadConfigFactory.getConfig().getInputConfig();
		
		final double scrollStep = config.getScrollStep();
		final double scaleStep = config.getScaleStep();
		final double initialFrameLength = scene.getFrameLength();
		
		Map <String, IAction> actions = controller.getActions();
		
		actions.put("scroll-right", new IAction() {
			public void act(UserActionEvent event) { vp.getCenter().add(-scrollStep/vp.getScale(), 0); }}
			);
		actions.put("scroll-left", new IAction() {
			public void act(UserActionEvent event) { vp.getCenter().add(scrollStep/vp.getScale(), 0); }}
		);
		actions.put("scroll-up", new IAction() {
			public void act(UserActionEvent event) { vp.getCenter().add(0, -scrollStep/vp.getScale()); }}
		);
		actions.put("scroll-down", new IAction() {
			public void act(UserActionEvent event) { vp.getCenter().add(0, scrollStep/vp.getScale()); }}
		);
		actions.put("zoom-in", new IAction() {
			public void act(UserActionEvent event) {
				double s = vp.getScale() * scaleStep;
				vp.setScale(s > vp.getMinScale() ? s : vp.getMinScale()); 
				scene.setFrameLength(initialFrameLength*vp.getScale());
			}}
		);
		actions.put("zoom-out", new IAction() {
			public void act(UserActionEvent event) {
				double s = vp.getScale() / scaleStep;
				vp.setScale(s < vp.getMaxScale() ? s : vp.getMaxScale());
				scene.setFrameLength(vp.getScale()*initialFrameLength);
			 }}
		);

		return actions;
	}
}
