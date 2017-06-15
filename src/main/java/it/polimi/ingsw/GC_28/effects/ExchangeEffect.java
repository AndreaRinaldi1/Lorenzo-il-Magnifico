package it.polimi.ingsw.GC_28.effects;
import java.util.Scanner;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;


public class ExchangeEffect extends Effect{
	private Resource firstCost;
	private Resource secondCost;
	private boolean alternative;
	private Resource firstBonus;
	private Resource secondBonus;
	private Resource privilegeCost;
	private PrivilegesEffect privilegeBonus;
	public final EffectType type = EffectType.EXCHANGEEFFECT;

	
	public ExchangeEffect(){
		super();
	}
	
	
	public Resource getFirstCost() {
		return firstCost;
	}

	public void setFirstCost(Resource firstCost) {
		this.firstCost = firstCost;
	}

	public Resource getSecondCost() {
		return secondCost;
	}

	public void setSecondCost(Resource secondCost) {
		this.secondCost = secondCost;
	}

	public boolean isAlternative() {
		return alternative;
	}

	public void setAlternative(boolean alternative) {
		this.alternative = alternative;
	}

	public Resource getFirstBonus() {
		return firstBonus;
	}

	public void setFirstBonus(Resource firstBonus) {
		this.firstBonus = firstBonus;
	}

	public Resource getSecondBonus() {
		return secondBonus;
	}

	public void setSecondBonus(Resource secondBonus) {
		this.secondBonus = secondBonus;
	}

	public Resource getPrivilegeCost() {
		return privilegeCost;
	}

	public void setPrivilegeCost(Resource privilegeCost) {
		this.privilegeCost = privilegeCost;
	}


	public PrivilegesEffect getPrivilegeBonus() {
		return privilegeBonus;
	}


	public void setPrivilegeBonus(PrivilegesEffect privilegeBonus) {
		this.privilegeBonus = privilegeBonus;
	}


	@Override
	public void apply(FamilyMember familyMember, ClientWriter writer){
		System.out.println("apply di ExchangeEffect");
		if(!this.alternative){
			if(this.privilegeBonus != null){
				familyMember.getPlayer().reduceResources(privilegeCost);
				System.out.println(familyMember.getPlayer().getBoard().getResources().toString());
				privilegeBonus.apply(familyMember, writer);
			}
			else{
				familyMember.getPlayer().reduceResources(firstCost);
				System.out.println(familyMember.getPlayer().getBoard().getResources().toString());
				familyMember.getPlayer().addResource(writer.checkResourceExcommunication(firstBonus,familyMember.getPlayer()));

			}
		}
		else{
			if(askAlternativeExchange(firstCost, firstBonus, secondCost, secondBonus) == 1){
				familyMember.getPlayer().reduceResources(firstCost);
				System.out.println(familyMember.getPlayer().getBoard().getResources().toString());
				familyMember.getPlayer().addResource(writer.checkResourceExcommunication(firstBonus,familyMember.getPlayer()));

			}
			else{
				familyMember.getPlayer().reduceResources(secondCost);
				System.out.println(familyMember.getPlayer().getBoard().getResources().toString());
				familyMember.getPlayer().addResource(writer.checkResourceExcommunication(secondBonus,familyMember.getPlayer()));
			}
		}
	}
	
	public int askAlternativeExchange(Resource firstCost, Resource firstBonus, Resource secondCost, Resource secondBonus){
		System.out.println("Which of the following exchanges do you want to apply? [1/2]");
		System.out.println("First Possibility Cost");
		System.out.println(firstCost.toString());
		System.out.println("First Possibility Bonus");
		System.out.println(firstBonus.toString());
		System.out.println("Second Possibility Cost");
		System.out.println(secondCost.toString());
		System.out.println("Second Possibility Bonus");
		System.out.println(secondBonus.toString());
		int choice;
		Scanner scan = new Scanner(System.in);
		while(true){
			if(scan.hasNextInt()){
				choice = scan.nextInt();
				scan.nextLine();
				if(choice == 1 || choice == 2){
					scan.close();
					return choice;
				}
				else{
					System.out.println("Not valid input!");
					System.out.println("Which of the two discounts do you want to apply? [1/2]");
					scan.nextLine();
					continue;
				}
			}
			else{
				scan.nextLine();
				System.out.println("Not valid input!");
				System.out.println("Which of the two discounts do you want to apply? [1/2]");
			}
		}
	}
}
