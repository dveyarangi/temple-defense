package yarangi.game.spacefight.model.enemies.bubbles;

import java.util.ArrayList;
import java.util.List;

import yarangi.game.spacefight.model.temple.TempleEntity;
import yarangi.graphics.quadraturin.SceneVeil;
import yarangi.graphics.quadraturin.objects.AggregateEntity;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class BubbleSwarm extends AggregateEntity
{

	public List <SpawnerBubble> spawners = new ArrayList <SpawnerBubble> ();
	private TempleEntity temple;
	public BubbleSwarm(SceneVeil sceneVeil, TempleEntity temple) {
		super(sceneVeil);
		
		this.temple = temple;
	}

	@Override
	public boolean behave(double time) 
	{
		if(RandomUtil.getRandomInt(200) == 0)
		{
			double a = RandomUtil.getRandomGaussian(RandomUtil.getRandomDouble(Angles.PI_2), 0.1);

			double rad = 300;
			
			SceneEntity target = temple;
			double speed = RandomUtil.getRandomDouble(0.1)+0.2;
			double x = rad*Math.cos(a);
			double y = rad*Math.sin(a);
			double ang = Math.atan2(target.getAABB().y-y, target.getAABB().x-x);
			Vector2D velocity = new Vector2D(speed*Math.cos(ang), speed*Math.sin(ang));
			
			SpawnerBubble spawner = new SpawnerBubble(getSceneVeil(),
					x, y, 180.+Angles.toDegrees(a), velocity, target, 20);
			spawners.add(spawner);
			getSceneVeil().addEntity(spawner);
		}
		return false;
	}

}
