package yarangi.game.temple.model.temple;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import yarangi.game.temple.controllers.TempleController;
import yarangi.game.temple.model.temple.platforms.CommandPlatform;
import yarangi.game.temple.model.temple.platforms.EmptyPlatform;
import yarangi.game.temple.model.temple.platforms.WeaponPlatform;
import yarangi.game.temple.model.temple.structure.Connectable;
import yarangi.game.temple.model.temple.structure.Hexagon;
import yarangi.game.temple.model.temple.structure.HexagonObserver;
import yarangi.game.temple.model.temple.structure.MeshNode;
import yarangi.game.temple.model.temple.structure.PowerConnector;
import yarangi.game.temple.model.temple.structure.Hexagon.HexagonId;
import yarangi.game.temple.model.weapons.Minigun;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.CompositeSceneEntity;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;

public class TempleStructure extends CompositeSceneEntity implements Connectable
{

	private static final long serialVersionUID = 1922785697163737467L;

	private CommandPlatform commandPlatform;
	
	private Map <HexagonId, Hexagon> hexagons = new HashMap <HexagonId, Hexagon> ();
	
	private List <MeshNode> perimeter;
	
	private double boundingRadiusSquare;
	private double boundingRadius;
	
	
	private double shieldRadius;
	
	public static final double hexRadius = 2;
	
	public PowerConnector [] connectors;
	
	public TempleStructure(TempleEntity temple, TempleController controller)
	{
		super();
		
		setArea(Area.EMPTY);
		Hexagon centerHexagon = new Hexagon();
		
		this.commandPlatform = new CommandPlatform(temple, centerHexagon);
		
		addChild(commandPlatform);
		
		for(int idx = 0; idx < 6; idx ++)
		{
			MeshNode node = commandPlatform.getHexagon().getMeshNode(idx);
			node.setLocation(new Vector2D(hexRadius*Math.cos(idx*Math.PI/3), hexRadius*Math.sin(idx*Math.PI/3)));
		}
		hexagons.put(centerHexagon.getId(), centerHexagon);
		
		Hexagon temp; 
		Hexagon bottom = linkHexagon(centerHexagon, Hexagon.BOTTOM, new Hexagon());
		WeaponPlatform testWeaponPlatform1 = new WeaponPlatform(bottom, commandPlatform, 10);
		
		addChild(testWeaponPlatform1);
			temp = linkHexagon(bottom, Hexagon.BOTTOM_LEFT, new Hexagon());
			addChild(new EmptyPlatform(temp));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.BOTTOM, new Hexagon())));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.BOTTOM_LEFT, new Hexagon())));
			temp = linkHexagon(bottom, Hexagon.BOTTOM_RIGHT,new Hexagon());
			addChild(new EmptyPlatform(temp));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.BOTTOM, new Hexagon())));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.BOTTOM_RIGHT, new Hexagon())));
				
//		Weapon weapon1 = new FlakCannon(testWeaponPlatform1, 0,0,0, veil.getEntityIndex());
//		testWeaponPlatform1.addWeapon(weapon1);
//		controller.addFireable(weapon1);
//		Weapon		weapon1 = new Minigun(commandPlatform, 0,000,0);
//		testWeaponPlatform1.addWeapon(weapon1);
//		controller.addFireable(weapon1);
//		weapon1 = new Minigun(commandPlatform, 0,000,0);
//		testWeaponPlatform1.addWeapon(weapon1);
//		controller.addFireable(weapon1);
		
//		linkPlatform(center, Hexagon.BOTTOM_LEFT, new Hexagon());
		
		Hexagon hiLeft = linkHexagon(centerHexagon, Hexagon.HIGH_LEFT, new Hexagon());
		WeaponPlatform testWeaponPlatform2 = new WeaponPlatform(hiLeft, commandPlatform, 10);
		addChild(testWeaponPlatform2);
		
			temp = linkHexagon(hiLeft, Hexagon.TOP, new Hexagon());
			addChild(new EmptyPlatform(temp));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.HIGH_LEFT, new Hexagon())));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.TOP, new Hexagon())));
			temp = linkHexagon(hiLeft, Hexagon.BOTTOM_LEFT, new Hexagon());
			addChild(new EmptyPlatform(temp));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.BOTTOM_LEFT, new Hexagon())));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.HIGH_LEFT, new Hexagon())));
//		linkPlatform(center, Hexagon.TOP, new Hexagon());
//		Weapon weapon2 = new FlakCannon(testWeaponPlatform2, 0,0,0);
//		testWeaponPlatform2.addWeapon(weapon2);
//		controller.addFireable(weapon2);
/*		Weapon	weapon2 = new Minigun(testWeaponPlatform2, 00,00,0);
				testWeaponPlatform2.addWeapon(weapon2);
				controller.addFireable(weapon2);
		weapon2 = new Minigun(testWeaponPlatform2, 00,00,0);
		testWeaponPlatform2.addWeapon(weapon2);
		controller.addFireable(weapon2);*/
		
		Hexagon hiRite = linkHexagon(centerHexagon, Hexagon.HIGH_RIGHT, new Hexagon());
		WeaponPlatform testWeaponPlatform3 = new WeaponPlatform(hiRite, commandPlatform, 10);
		
		addChild(testWeaponPlatform3);
			temp = linkHexagon(hiRite, Hexagon.TOP, new Hexagon());
			addChild(new EmptyPlatform(temp));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.HIGH_RIGHT, new Hexagon())));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.TOP, new Hexagon())));
			temp = linkHexagon(hiRite, Hexagon.BOTTOM_RIGHT, new Hexagon());
			addChild(new EmptyPlatform(temp));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.HIGH_RIGHT, new Hexagon())));
				addChild(new EmptyPlatform(linkHexagon(temp, Hexagon.BOTTOM_RIGHT, new Hexagon())));
//		Weapon weapon3 = new FlakCannon(testWeaponPlatform3, 0,0,0);
//		testWeaponPlatform3.addWeapon(weapon3);
//		controller.addFireable(weapon3);
/*		Weapon				weapon3 = new Minigun(testWeaponPlatform3, -0,0,0);
		testWeaponPlatform3.addWeapon(weapon3);
				controller.addFireable(weapon3);
		weapon3 = new Minigun(testWeaponPlatform3, -0,0,0);
		testWeaponPlatform3.addWeapon(weapon3);
		controller.addFireable(weapon3);*/
		/*		weapon3 = new LightningEmitter(testWeaponPlatform3, 0,0,0);
				testWeaponPlatform3.addWeapon(weapon3);
				controller.addFireable(weapon3); */
		
//		linkPlatform(center, Hexagon.BOTTOM_RIGHT, new Hexagon());
		
		centerHexagon.search(new PerimetringHexagonObserver());
		
		
		setLook(new TempleLook());
		setBehavior(new TempleBehavior());

		boundingRadius = Math.sqrt(boundingRadiusSquare);
		shieldRadius = boundingRadius*hexRadius + 1;
		
		
//		ShieldEntity shield = new ShieldEntity(commandPlatform);
//		
//		addChild(shield);
		
/*		connectors = new PowerConnector[6];
		int idx = 0;
		for(double a = 0.01; a < Angles.PI_2; a += Angles.PI_div_3)
			connectors[idx++] = new PowerConnector(this.getAABB(), a);*/
	}
	
	public BattleInterface getBattleInterface() { return commandPlatform; }
	
	public Hexagon linkHexagon(Hexagon target, int position, Hexagon hex)
	{

		target.linkNeighbor(position, hex, hexRadius);
		
		MeshNode refNode = target.getMeshNode(position);
		
		// reconstructing center of target hexagon:
		double refA = position*Math.PI/3.0;
		Vector2D ref = refNode.getLocation().minus(new Vector2D(Math.cos(refA), Math.sin(refA)));
		
		// creating vector to center of linked platform:
		double distance = 2.0*Math.cos(Math.PI/6.0);
		double angle = refA + Math.PI/6.0;
		Vector2D platfCenter = ref.plus(new Vector2D(distance*Math.cos(angle), distance*Math.sin(angle)));
		
		// changing the location of linked nodes:
		for(int idx = 0; idx < 6; idx ++)
		{
			MeshNode node = hex.getMeshNode(idx);
			if (node.getLocation() != null)
				continue;
			// node vector inside linked hexagon:
			Vector2D nodeRef = new Vector2D(hexRadius*Math.cos(idx*Math.PI/3.0), hexRadius*Math.sin(idx*Math.PI/3.0));
			node.setLocation(platfCenter.plus(nodeRef));
//			System.out.println(node.getLocation());
		}
		
		
		Map <HexagonId, Integer> map = hex.getNeighbors();
		
		// linking mesh nodes
		for(HexagonId id : map.keySet())
		{
			if( !hexagons.containsKey(id))
				continue;
			int pos = map.get(id);
			
			Hexagon neighbor = hexagons.get(id);
			hex.linkNeighbor(pos, neighbor, hexRadius);
			
			// linking the corners of hexagons:
			// for each neighbor at specified index:
			// the clockwise corner of each edge is considered to have the same index as the edge.
			// at hexagon linking we have to merge two corners (mesh nodes) that are now became
			// the same. 
			int meshId10 = pos + 3; // opposite 
			int meshId11 = pos + 4;	// opposite+1
			MeshNode node1 = neighbor.getMeshNode(meshId11);
			MeshNode replacedNode1 = hex.getMeshNode(pos);
			for(int idx = 0; idx < 6; idx ++)
			{
				if(replacedNode1.getHexagon(idx) != null)
					node1.setHexagon(idx, replacedNode1.getHexagon(idx));
				if(replacedNode1.getNeighbor(idx) != null)
					node1.linkNeighbor(idx, replacedNode1.getNeighbor(idx));
			}
			hex.setMeshNode(pos, node1);
			
			MeshNode node2 = neighbor.getMeshNode(meshId10);
			MeshNode replacedNode2 = hex.getMeshNode(pos+1);
			for(int idx = 0; idx < 6; idx ++)
			{
				if(replacedNode2.getHexagon(idx) != null)
					node2.setHexagon(idx, replacedNode2.getHexagon(idx));
				if(replacedNode2.getNeighbor(idx) != null)
					node2.linkNeighbor(idx, replacedNode2.getNeighbor(idx));

			}
			hex.setMeshNode(pos+1, node2);
		}
		
		hexagons.put(hex.getId(), hex);
		return hex;
	}
	
	public CommandPlatform getCenter() { return commandPlatform; }
	public Collection <Hexagon> getHexagons() { return hexagons.values(); }
	public List <MeshNode> getPerimeter() { return perimeter; }
	
	class PerimetringHexagonObserver implements HexagonObserver
	{

		public void hexagonFound(Hexagon hexagon, int x, int y) {
			if(perimeter != null)
				return;
			
			// checking if this hexagon is on the perimeter:
			int perimeterDir = -1;
			for(int idx = 0; idx < 6; idx ++)
			{
				if(hexagon.getNeighbor(idx) != null)
					continue;
				
				perimeterDir = idx;
				break;
			}
			
			if (perimeterDir == -1) // not a perimeter hexagon
				return;
			
			perimeter = new LinkedList <MeshNode> ();
			MeshNode startingNode = hexagon.getMeshNode(perimeterDir);
			MeshNode node = startingNode;
			MeshNode prevNode = null;
			do
			{
				perimeter.add(node);
				double distance = Geometry.calcHypotSquare(0,0, node.getLocation().x, node.getLocation().y); 
				if ( distance > boundingRadiusSquare)
					boundingRadiusSquare = distance;
				
				for(int dir = 0; dir < 6; dir ++)
				{
					MeshNode testNode = node.getNeighbor(dir);
					if(testNode == null || testNode == prevNode)
						continue;
					
					if ( testNode.getHexagon(dir+4) != null && testNode.getHexagon(dir+2) != null)
						continue;
					
					prevNode = node;
					node = testNode;
					break;
				}
			}
			while(node != startingNode);
			perimeter.add(startingNode);
			
		}
		
	}
	
//	public double getHexRadius() { return hexRadius; }

	public double getBoundingRadiusSquare() { return boundingRadiusSquare; }
	public double getShieldRadius() { return shieldRadius; }
	
	public Vector2D toTempleCoordinates(Vector2D vec)
	{
		return toEntityCoordinates(vec.x, vec.y, hexRadius);
	}
	public Vector2D toTempleCoordinates(double x, double y)
	{
		return toEntityCoordinates(x, y, hexRadius);
	}
	public Vector2D toEntityCoordinates(double x, double y, double scale)
	{
		double aRad = Angles.toRadians(0);
		double sina = Math.sin(aRad);
		double cosa = Math.cos(aRad);
		return new Vector2D(x*scale*cosa-y*scale*sina, x*scale*sina+y*scale*cosa );
	}	

	@Override
	public boolean isPickable() { return true; }


	public PowerConnector[] getConnectors() { return connectors; }

}
