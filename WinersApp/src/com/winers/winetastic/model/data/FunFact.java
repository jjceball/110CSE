package com.winers.winetastic.model.data;


import java.util.ArrayList;


public class FunFact{
	ArrayList<String> facts;
	int Min = 0;
	int Max;
	
	public FunFact(){
		facts = new ArrayList<String>();
		facts.add("The smell of young wine is called an \"aroma\" while a more mature wine offers a more subtle \"bouquet.\"");
		facts.add("In ancient Greece, a dinner host would take the first sip of wine to assure guests the wine was not poisoned, hence the phrase \"drinking to one's health.\" \"Toasting\" started in ancient Rome when the Romans continued the Greek tradition but started dropping a piece of toasted bread into each wine glass to temper undesirable tastes or excessive acidity.");
		facts.add("A \"cork-tease\" is someone who constantly talks about the wine he or she will open but never does.");
		facts.add("Since wine tasting is essentially wine smelling, women tend to be better wine testers because women, particularly of reproductive ages, have a better sense of smell than men.");
		facts.add("An Italian study argues that women who drink two glasses of wine a day have better sex than those who don't drink at all.");
		facts.add("Red wines are red because fermentation extracts color from the grape skins. White wines are not fermented with the skins present.");
		facts.add("In the whole of the Biblical Old Testament, only the Book of Jonah has no reference to the vine or wine.");
		facts.add("Early Roman women were forbidden to drink wine, and a husband who found his wife drinking was at liberty to kill her. Divorce on the same grounds was last recorded in Rome in 194 B.C.");
		facts.add("The world's oldest bottle of wine dates back to A.D. 325 and was found near the town of Speyer, Germany, inside one of two Roman sarcophaguses. It is on display at the town's Historisches Museum der Pfalz.");
		facts.add("There is increasing scientific evidence that moderate, regular wine drinking can reduce the risk of heart disease, Alzheimer's disease, stroke, and gum disease.");
		facts.add("While wine offers certain medical benefits, it may slightly increase the risk of contracting certain kinds of cancer of the digestive tract, particularly the esophagus. There is also a slightly increased risk of breast cancer.");
		facts.add("Red wine, typically more than white wine, has antioxidant properties and contains resveratrol, which seems to be important in the cardio-protective effects of wine.");
		facts.add("California, New York, and Florida lead the United States in wine consumption.");
		facts.add("California is the fourth-largest wine producer in the world, after France, Italy, and Spain.");
		facts.add("Wine testers swirl their glass to encourage the wine to release all of its powerful aromas. Most don't fill the glass more than a third full in order to allow aromas to collect and to not spill it during a swirl.");	
	}
	
	public String randomFact(){
		Max = facts.size()-1;
		int rand = Min + (int)(Math.random() * ((Max - Min) + 1));
		return facts.get(rand);
	}
}
