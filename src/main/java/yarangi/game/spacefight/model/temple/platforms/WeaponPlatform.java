package yarangi.game.spacefight.model.temple.platforms;

import java.util.LinkedList;
import java.util.List;

import yarangi.game.spacefight.controllers.BattleInterface;
import yarangi.game.spacefight.model.temple.Hexagon;
import yarangi.game.spacefight.model.temple.Platform;
import yarangi.game.spacefight.model.weapons.Weapon;

public class WeaponPlatform extends Platform 
{

	private List <Weapon> weapons;
	
	private int weaponSlots;
	private BattleInterface battleInterface;
	
	public WeaponPlatform(Hexagon hexagon, BattleInterface battleInterface, int weaponSlots) 
	{
		super(hexagon);
		
		this.weaponSlots = weaponSlots;
		
		this.battleInterface = battleInterface;
		
		weapons = new LinkedList <Weapon> ();
		
		// TODO: weapon platform specifics
		setLook(new PlatformLook());
		setBehavior(new PlatformBehavior());
	}
	
	public BattleInterface getBattleInterface()
	{ 
		return battleInterface; 
	}
	
	public void addWeapon(Weapon weapon)
	{
		if(weapons.size() > weaponSlots )
			this.weapons.add(weapon);
		addChild(weapon);
	}

}
