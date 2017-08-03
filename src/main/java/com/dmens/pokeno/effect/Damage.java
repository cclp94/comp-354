package com.dmens.pokeno.effect;

import com.dmens.pokeno.card.Card;
import com.dmens.pokeno.card.Pokemon;
import com.dmens.pokeno.condition.*;
import com.dmens.pokeno.services.*;
import com.dmens.pokeno.services.handlers.TargetServiceHandler;

import java.util.List;

/*
 * A Damage effect.
 *
 * @author James
 */
public class Damage extends Effect {

	private int mValue;
	private String mCountInfo;
	
	/*
	 * Constructor
	 * 
	 * @param		tar		Target.
	 * @param		val		Integer value (amount).
	 * @param		con		Condition.
	 */
	public Damage(String tar, int val, Condition con, String countInfo)
	{
		super(tar, con);
		this.mValue = val;
		this.mCountInfo = countInfo;
	}
	
	/*
	 * Copy Constructor
	 * 
	 * @param		d		Damage Effect.
	 */
	public Damage(Damage d)
	{
		this.mTarget = d.mTarget;
		this.mValue = d.mValue;
		
		if(d.mCondition instanceof Flip)
			this.mCondition = new Flip();
	}
	
	/*
     * Get the value of this Effect.
     * 
     * @return		The value as an integer.
     */
	public int getValue()
	{
		return this.mValue;
	}
	
	
	/*
     * Get the value of this count info.
     * 
     * @return		The count info as an integer.
     */
	public String getCountInfo()
	{
		return this.mCountInfo;
	}
	
	
	@Override
	public void execute() 
	{
		System.out.println(mTarget);
		int count = 1;
		if(this.mCountInfo != "") {
			System.out.println("Good Count info: " + mCountInfo);
			count = CountService.getInstance().getCount(this.mCountInfo);
		}
		List<Card> targetPokemon = (TargetServiceHandler.getInstance()).getTarget(mTarget);
		for(Card pokemon: targetPokemon) {
			System.out.println(pokemon.getName());
			((Pokemon) pokemon).addDamage(count * mValue);
		}
		System.out.println(mValue);
	}

	@Override
	public String toString()
	{
		return String.format("Damage: Target: %s, Value: %d", this.mTarget, this.mValue);
	}
	
	//TODO: condition check
	@Override
	public boolean equals(Object obj)
	{
		Damage d = (Damage) obj;
		if(d.mTarget.equals(this.mTarget) && d.mValue == this.mValue)
			return true;
		
		return false;
	}
}