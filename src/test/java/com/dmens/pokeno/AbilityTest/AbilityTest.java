package com.dmens.pokeno.AbilityTest;

import static org.junit.Assert.*;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.dmens.pokeno.Ability.Ability;
import com.dmens.pokeno.card.EnergyCard;
import com.dmens.pokeno.card.Pokemon;
import com.dmens.pokeno.controller.GameController;
import com.dmens.pokeno.database.AbilitiesDatabase;
import com.dmens.pokeno.database.CardsDatabase;
import com.dmens.pokeno.deck.Deck;
import com.dmens.pokeno.effect.ApplyStatus;
import com.dmens.pokeno.effect.Damage;
import com.dmens.pokeno.effect.DrawCard;
import com.dmens.pokeno.effect.Heal;
import com.dmens.pokeno.player.Player;
>>>>>>> d7b9cb630fe748fc8d89c3df0d78cd4b6b1266ad

/**
*
* @author James
*/

@RunWith(PowerMockRunner.class)
@PrepareForTest({GameController.class})
@PowerMockIgnore("javax.management.*")
public class AbilityTest {
	
	 static String mAbilityName = "Ability";
	 static String mEffectTarget = "opponent-active";
	 static int mEffectValue = 20;
	 static int mEffectValueDifferent = 10;
	 static String mEffectStatus = "asleep";
	 static String mEffectStatusDifferent = "poisoned";
	 
	 @Before
	 public void setup(){
		 AbilitiesDatabase.getInstance().initialize("abilities.txt");
		 CardsDatabase.getInstance().initialize("cards.txt");
		 
		//Mocks
		PowerMockito.mockStatic(GameController.class);
	 }

    @Test
    public void abilityTest(){
        
    	Ability ability = new Ability(mAbilityName);
    	Assert.assertEquals(ability.getName(), mAbilityName);
    	
    	// Stand alone Effects
    	Heal heal = new Heal(mEffectTarget, mEffectValue);
    	Assert.assertEquals(heal.getTarget(), mEffectTarget);
    	Assert.assertEquals(heal.getValue(), mEffectValue);
    	
    	Damage damage = new Damage(mEffectTarget, mEffectValue);
    	Assert.assertEquals(damage.getTarget(), mEffectTarget);
    	Assert.assertEquals(damage.getValue(), mEffectValue);
    	
    	ApplyStatus applyStatus = new ApplyStatus(mEffectTarget, mEffectStatus);
    	Assert.assertEquals(applyStatus.getTarget(), mEffectTarget);
    	Assert.assertEquals(applyStatus.getStatus(), mEffectStatus);
    	
    	DrawCard drawCard = new DrawCard(mEffectValue, mEffectTarget);
    	Assert.assertEquals(drawCard.getTarget(), mEffectTarget);
    	Assert.assertEquals(drawCard.getValue(), mEffectValue);
    	
    	// Add each Effect to the Ability
    	ability.addEffect(heal);
    	Assert.assertEquals(ability.getHealEffect(), heal);
    	Assert.assertEquals(ability.getHealEffect().getTarget(), mEffectTarget);
    	Assert.assertEquals(ability.getHealEffect().getValue(), mEffectValue);
    	
    	ability.addEffect(damage);
    	Assert.assertEquals(ability.getDamageEffect(), damage);
    	Assert.assertEquals(ability.getDamageEffect().getTarget(), mEffectTarget);
    	Assert.assertEquals(ability.getDamageEffect().getValue(), mEffectValue);
    	
    	ability.addEffect(applyStatus);
    	Assert.assertEquals(ability.getApplyStatusEffect(), applyStatus);
    	Assert.assertEquals(ability.getApplyStatusEffect().getTarget(), mEffectTarget);
    	Assert.assertEquals(ability.getApplyStatusEffect().getStatus(), mEffectStatus);    
    	
    	ability.addEffect(drawCard);
    	Assert.assertEquals(ability.getDrawCardEffect(), drawCard);
    	Assert.assertEquals(ability.getDrawCardEffect().getTarget(), mEffectTarget);
    	Assert.assertEquals(ability.getDrawCardEffect().getValue(), mEffectValue);
    	
    	// change effects... check that effects in abilities are unaffected
    	heal = new Heal(mEffectTarget, mEffectValueDifferent);
    	Assert.assertNotEquals(ability.getHealEffect(), heal);
    	
    	damage = new Damage(mEffectTarget, mEffectValueDifferent);
    	Assert.assertNotEquals(ability.getDamageEffect(), damage);
    	
    	applyStatus = new ApplyStatus(mEffectTarget, mEffectStatusDifferent);
    	Assert.assertNotEquals(ability.getApplyStatusEffect(), applyStatus);
    	
    	drawCard = new DrawCard(mEffectValueDifferent, mEffectTarget);
    	Assert.assertNotEquals(ability.getDrawCardEffect(), drawCard);
    }
    
    @Test
    public void testDrawEffect(){
    	Deck deck = new Deck();
    	deck.addCards(Arrays.asList(((CardsDatabase)CardsDatabase.getInstance()).queryByName("Tierno"),
    			new EnergyCard("Water","water"), new EnergyCard("Water","water"), new EnergyCard("Water", "water")));
    	Player player = new Player(deck);
    	
    	stub(method(GameController.class, "updateHand")).toReturn(0);
    	stub(method(GameController.class, "getActivePlayer")).toReturn(player);
    	// Draw Tierno
    	player.drawCardsFromDeck(1);
    	assertEquals(1, player.getHand().size());
    	assertEquals("Tierno", player.getHand().getCards().get(0).getName());
    	// Use Tierno
    	player.useCard(player.getHand().getCards().get(0));
    	// Expect hand size to be 3 now
    	assertEquals(3, player.getHand().size());
    }

}
