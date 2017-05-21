package it.polimi.ingsw.GC_28.core;
import it.polimi.ingsw.GC_28.cards.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;
import java.lang.Character;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("restriction")
public class CouncilPrivilegeCreator {
	Scanner scanner = new Scanner(System.in);
	Resource resourceCost;
	int coin, wood, stone, servant, militaryPoint, victoryPoint, faithPoint;
	HashMap<Character, Resource> options;
	EnumMap<ResourceType, Integer> cost;


	static int counter = 0;
	
	public static void main(String[] args){
		CouncilPrivilegeCreator writer = new CouncilPrivilegeCreator();
		writer.startCreating();
	}
	

	private void startCreating(){
		
		Gson obj = new GsonBuilder().setPrettyPrinting().create();
		try{
			FileWriter file = new FileWriter("priv.json", true);
			String proceed;
			options = new HashMap<Character, Resource>();
			do{
				System.out.println("inserire char: ");
				Character c = scanner.nextLine().charAt(0);
						
				coin = enterCost(ResourceType.COIN.name());
				wood = enterCost(ResourceType.WOOD.name());
				stone = enterCost(ResourceType.STONE.name());
				servant = enterCost(ResourceType.SERVANT.name());
				militaryPoint = enterCost(ResourceType.MILITARYPOINT.name());	
				victoryPoint = enterCost(ResourceType.VICTORYPOINT.name());
				faithPoint = enterCost(ResourceType.FAITHPOINT.name());
				
				cost = new EnumMap<ResourceType, Integer>(ResourceType.class);

				
				cost.put(ResourceType.COIN, coin);
				cost.put(ResourceType.WOOD, wood);
				cost.put(ResourceType.STONE, stone);
				cost.put(ResourceType.SERVANT, servant);
				cost.put(ResourceType.MILITARYPOINT, militaryPoint);
				cost.put(ResourceType.VICTORYPOINT, victoryPoint);
				cost.put(ResourceType.FAITHPOINT, faithPoint);
				
				resourceCost = Resource.of(cost);
				options.put(c, resourceCost);
				
				System.out.println("Do you want to go on?");
				proceed = scanner.nextLine();	
			}while(!proceed.equals("end"));
			CouncilPrivilege privilege = CouncilPrivilege.instance();
			privilege.setOptions(options);

			String tmp = obj.toJson(privilege);
			file.write(tmp);
			file.flush();
			file.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		scanner.close();
	}
	
	
	private int enterCost(String resourceType){
		System.out.print("Enter " + resourceType + " cost: ");
		while(!scanner.hasNextInt()){
			System.out.println("Not valid input!");
			System.out.print("Enter " + resourceType + " cost: ");
			scanner.nextLine();
			scanner.hasNextInt();
		}
		int cost = scanner.nextInt();
		scanner.nextLine();
		return cost;
	}
}