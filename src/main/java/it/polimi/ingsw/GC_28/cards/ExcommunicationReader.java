package it.polimi.ingsw.GC_28.cards;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.FinalReduceEffect;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;

import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.NoFinalBonusEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.ModifyDiceEffect;
import it.polimi.ingsw.GC_28.effects.ServantEffect;

/**
 * This class read the json file excommunication.json and create a list of all the  excommunication of the game
 * @author nicoloscipione
 * @version 1.0, 03/072017
 * @see ExcommunicationTile
 */

public class ExcommunicationReader {

	/**
	 * This method create a list of all excommunicationTile of the game. It use a Runtimetypeadapterfactory to let
	 * gson api recognize all effect and associate the excommunicationTile to its effect, while it reads the json file.
	 * @return The list of all excommunicationTile
	 * @throws IOException
	 */
	public List<ExcommunicationTile> startRead()throws IOException{
		List<ExcommunicationTile> excomm;

		Type requestListTypeToken = new TypeToken<ArrayList<ExcommunicationTile>>() {}.getType();
		
		final RuntimeTypeAdapterFactory<Effect> typeFactory = RuntimeTypeAdapterFactory
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
		
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();
		JsonReader read = new JsonReader(new FileReader("excommunication.json"));
		excomm = gson.fromJson(read, requestListTypeToken);
		read.close();

		return excomm;
	}
}
