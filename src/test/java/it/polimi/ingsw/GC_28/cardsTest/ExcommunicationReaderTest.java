package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import it.polimi.ingsw.GC_28.cards.ExcommunicationReader;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.FinalReduceEffect;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.effects.ModifyDiceEffect;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.NoFinalBonusEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.ServantEffect;

public class ExcommunicationReaderTest {

	private ExcommunicationReader excommunicationReader;
	private List<ExcommunicationTile> excomm = new ArrayList<>();
	private List<ExcommunicationTile> test1 = new ArrayList<>();
	private List<ExcommunicationTile> test2 = new ArrayList<>();

	private	Type requestListTypeToken = new TypeToken<ArrayList<ExcommunicationTile>>() {}.getType();
	private JsonReader read;	
	private final RuntimeTypeAdapterFactory<Effect> typeFactory = RuntimeTypeAdapterFactory
			.of(Effect.class, "type")
			.registerSubtype(DiscountEffect.class, "DISCOUNTEFFECT")
			.registerSubtype(IncrementCardEffect.class, "INCREMENTCARDEFFECT")
			.registerSubtype(IncrementHPEffect.class, "INCREMENTHPEFFECT")
			.registerSubtype(NoFinalBonusEffect.class, "NOFINALBONUSEFFECT")
			.registerSubtype(MultiplierEffect.class, "MULTIPLIEREFFECT")
			.registerSubtype(ModifyDiceEffect.class, "MODIFYDICEEFFECT")
			.registerSubtype(OtherEffect.class, "OTHEREFFECT")
			.registerSubtype(ServantEffect.class, "SERVANTEFFECT")
			.registerSubtype(FinalReduceEffect.class, "FINALREDUCEEFFECT");
	
	private Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();
	
	@Before
	public void excommunicationReader() throws IOException{
		//excomm.equals(excommunicationReader.startRead());
		read = new JsonReader(new FileReader("excommunication.json"));
		excommunicationReader = new ExcommunicationReader();
		this.excommunicationReader.startRead();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testStartRead() throws IOException {	
		for(int l = 0; l < this.excommunicationReader.startRead().size(); l++){
			this.excomm.add(this.excommunicationReader.startRead().get(l));
		}
		//this.excomm.equals(this.excommunicationReader.startRead()); 
		/*excomm = gson.fromJson(read, requestListTypeToken);
		read.close();*/
		System.out.println(this.excomm);
		System.out.println(this.excommunicationReader.startRead());
		for(int i = 0; i < excomm.size(); i++){
			for (int j = 0; j<excommunicationReader.startRead().size(); j++){
				if(this.excomm.get(i).getEra() == this.excommunicationReader.startRead().get(j).getEra()){
					test1.add(this.excomm.get(i));
					test2.add(this.excommunicationReader.startRead().get(j));
				}
			}		
		}
		System.out.println(this.test1);
		System.out.println(this.test2);
		assertArrayEquals(this.test1.toArray(), this.test2.toArray());
		System.out.println(1);
	}

}
