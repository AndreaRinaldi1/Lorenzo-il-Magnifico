package it.polimi.ingsw.GC_28.boards;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FinalBonusWriter {
	
	Scanner scanner = new Scanner(System.in);
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	List<Resource> l = new ArrayList<>();
	
	public static void main(String[] args) {
		FinalBonusWriter fi = new FinalBonusWriter();
		fi.startCreateBonus();	
	}
	
	private void startCreateBonus(){
		setFinalBonus();
	}
	
	private int enterBonus(String resourceType){
		System.out.print("Enter " + resourceType + " bonus: ");
		while(!scanner.hasNextInt()){
			System.out.println("Not valid input!");
			System.out.print("Enter " + resourceType + " bonus: ");
			scanner.nextLine();
			scanner.hasNextInt();
		}
		int cost = scanner.nextInt();
		scanner.nextLine();
		return cost;
	}
	
	private void enterResourceBonus(EnumMap<ResourceType, Integer> bonus){
		
		int coin = enterBonus(ResourceType.COIN.name());
		int wood = enterBonus(ResourceType.WOOD.name());
		int stone = enterBonus(ResourceType.STONE.name());
		int servant = enterBonus(ResourceType.SERVANT.name());
		int victoryPoint = enterBonus(ResourceType.VICTORYPOINT.name());
		int militaryPoint = enterBonus(ResourceType.MILITARYPOINT.name());
		int faithPoint = enterBonus(ResourceType.FAITHPOINT.name());
		
		bonus.put(ResourceType.COIN, coin);
		bonus.put(ResourceType.WOOD, wood);
		bonus.put(ResourceType.STONE, stone);
		bonus.put(ResourceType.SERVANT, servant);
		bonus.put(ResourceType.VICTORYPOINT, victoryPoint);
		bonus.put(ResourceType.MILITARYPOINT, militaryPoint);
		bonus.put(ResourceType.FAITHPOINT, faithPoint);
	}
	
	
	private void setFinalBonus(){
		/*System.out.println("Territories final bonus");
		for(int i = 0; i< 6; i++){
			Integer p = i+1;
			String place = p.toString();
			System.out.println("Bonus for "+ place + " place:");
			EnumMap<ResourceType, Integer> r = new EnumMap<>(ResourceType.class);
			enterResourceBonus(r);
			Resource resource = Resource.of(r);
			finalBonus.getFinalTerritoriesBonus().add(resource);
		}
		System.out.println("Characters final bonus");
		//ArrayList<Resource> characterBonus = new ArrayList<>();
		for(int i = 0; i< 6; i++){
			Integer p = i+1;
			String place = p.toString();
			System.out.println("Bonus for "+ place + " place:");
			EnumMap<ResourceType, Integer> r = new EnumMap<>(ResourceType.class);
			enterResourceBonus(r);
			Resource resource = Resource.of(r);
			finalBonus.getFinalCharactersBonus().add(resource);
		}
		FinalBonus finalBonus = FinalBonus.instance();
		for(int i = 0; i< 4; i++){
			Integer p = i+1;
			String place = p.toString();
			System.out.println("Bonus for "+ place + " place:");
			EnumMap<ResourceType, Integer> r = new EnumMap<>(ResourceType.class);
			enterResourceBonus(r);
			Resource resource = Resource.of(r);
			l.add(resource);
		}
		finalBonus.setFinalMilitaryTrack(l);
		System.out.println("Enter Resource divide value for resource final bonus:");
		Integer resourceBonus = scanner.nextInt();
		scanner.nextLine();
		finalBonus.setResourceFactor(resourceBonus);
		System.out.println("Necessary Resource for get more territories:");
		for(int i = 0; i< 6; i++){
			Integer p = i+1;
			String place = p.toString();
			System.out.println("Bonus for "+ place + " place:");
			EnumMap<ResourceType, Integer> r = new EnumMap<>(ResourceType.class);
			enterResourceBonus(r);
			Resource resource = Resource.of(r);
			finalBonus.getResourceForTerritories().add(resource);
		}*/
		FinalBonus finalBonus = FinalBonus.instance();
		for(int i = 0; i< 15; i++){
			Integer p = i+1;
			String place = p.toString();
			System.out.println("Bonus for "+ place + " place:");
			EnumMap<ResourceType, Integer> r = new EnumMap<>(ResourceType.class);
			enterResourceBonus(r);
			Resource resource = Resource.of(r);
			l.add(resource);
		}
		finalBonus.setFaithPointTrack(l);
		try{
			FileWriter f = new FileWriter("finalBonus.json", true);
			String tmp = gson.toJson(finalBonus);
			f.write(tmp);
			f.flush();
			f.close();
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot init file" + e);
		}
		
	}
	
}
