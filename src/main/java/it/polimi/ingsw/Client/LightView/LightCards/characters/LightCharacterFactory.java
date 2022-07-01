package it.polimi.ingsw.Client.LightView.LightCards.characters;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cards.characters.GrandmaHerbs;
import it.polimi.ingsw.model.cards.characters.Jester;
import it.polimi.ingsw.model.cards.characters.Priest;
import it.polimi.ingsw.model.cards.characters.Princess;

//y
import java.util.Arrays;

/**
 * Contains the Factory method used in the active and inactive decks to create LightCharacterCards dynamically given a CharacterCard
 */
public class LightCharacterFactory 
{
    /**
     * The factory method, creates a LightCharacterCard given the corresponding CharacterCard. It sets the character descriptions,
     * which will be used in game to illustrate, in a quite silly fashion, the card effects (with a sprinkle of invented character's
     * lore too)
     * @param card the given character card
     * @return
     */
    public LightCharacterCard characterCreator(CharacterCard card)
    {
        String[] description = new String[7];
        Arrays.fill(description, "");
        switch (card.getCharacterName())
        {
            case HERALD:
                description[0] += "The herald lets you ignore any rules of decency by letting you start the influence calculation ";
                description[1] += "on an island of your choice, even if Mother Nature hasn't ended her movement there. That's right, ";
                description[2] += "you won't need to wait for anyone to make your dreams of conquest come true!";
                description[3] += "Use this effect in combination with Mother Nature to try and conquer more than one island ";
                description[4] += "in just one turn, and announce your feats to your adversaries in the most pompous ways!";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(), 
                        card.getUses(), 
                        card.getCurrentCost(), 
                        -1, 
                        null, 
                        description, 
                        LightCharacterType.INTEGER_1);
                
            case KNIGHT:
                description[0] += "To arms! This is one of the most offensive character you can even dream to summon. Once part of the ";
                description[1] += "Princess-warrior guard, he was discharged with dishonour after one-too-many - hilarious he tells us - ";
                description[2] += "offensive joke: we told you he was the most offensive character after all. If you manage to bear this insufferable ";
                description[3] += "jokester, you may find out that he is quite skilled in conquering islands; he's a knight after all! ";
                description[4] += "In fact, while this card is active, you and your team gain 2 more influence points to aid in the crusade ";
                description[5] += "against the enemy! ";
                description[6] += "Use this card to aid in the conquest of the most well defended outposts!";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(), 
                        card.getUses(), 
                        card.getCurrentCost(), 
                        -1, 
                        null, 
                        description, 
                        LightCharacterType.NONE);
                
            case PRINCESS:
                description[0] += "The Princess-warrior of the lands has host of valorous and combat ready knights, always at her service. ";
                description[1] += "Their loyalty is without question; but, like everything, it has a price. For as small as two coins, ";
                description[2] += "you can convince one of these skilled warriors to sit for the whole game at your dining room, ";
                description[3] += "patiently waiting for your - we are sure - genius strategies to unfold. ";
                description[4] += "Use this card to grant you a significant advantage in the professors - control race!";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        ((Princess) card).getStudents(),
                        description,
                        LightCharacterType.INTEGER_1);
                
            case PRIEST:
                description[0] += "The priest lives with his apprentices in a secluded convent, located amongst the hills of an unspecified island.";
                description[1] += "For a small fee, he will instruct one of his students to do your bidding for the rest of the game. ";
                description[2] += "You could order him to make some delicious coffee; or you could send him to an island of your choice ";
                description[3] += "(which actually is what you should make him do regardless). ";
                description[4] += "Use this card to help you gain some critical influence on contested islands.";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        ((Priest) card).getStudents(),
                        description,
                        LightCharacterType.INTEGER_2);
                
            case CENTAUR:
                description[0] += "The Centaur is quite the sight to behold in the Eriantys landscape, his fame preceded only by ";
                description[1] += "his joyful chants and quite strong fragrance. What makes him so peculiar though, is his unconditional ";
                description[2] += "hatred for towers, in his words \"a counter nature attempt to make people dwell in the skies\". ";
                description[3] += "With the years he learned how to capitalize on this burning hatred of his and, for a small fee, he will ";
                description[4] += "help you get rid of any towers during an island influence calculation - just don't tell him your ";
                description[5] += "goal is to place as many towers as possible around.... we are not sure how he will react. ";
                description[3] += "Use this card to help you conquer an island owned by your enemies!";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        null,
                        description,
                        LightCharacterType.NONE);
                
            case POSTMAN:
                description[0] += "The Postman is quite the industrious chap. Thanks to the nature of his job, he developed excellent orienteering ";
                description[1] += "skills, skills that he uses to navigate efficiently around the cluttered Eriantys world.";
                description[2] += "If you choose him as your companion, he will teach you some of his tricks for the current turn, ";
                description[3] += "adding 2 extra movements to the maximum movements mother nature can perform!";
                description[4] += "Use this card to reach very distant islands.";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        null,
                        description,
                        LightCharacterType.NONE);
                
                
            case GRANDMA_HERBS:
                description[0] += "Grandma Herbs have never loved Mother Nature very much. She always loved tidy, well organized gardens and ";
                description[1] += "orchards; but wherever Mother Nature went, she left a kind of energy that made all kinds of plants ";
                description[2] += "and herbs to grow too much, ruining the order and aesthetic of it all. So, after years of ill-concealed ";
                description[3] += "antipathy, Grandma decided tos truck an agreement with her: Mother Nature would not have caused havoc onto ";
                description[4] += "properties with the NoEntry sign on. For a small price, Grandma Herbs can place one of her four NoEntry signs ";
                description[5] += "on the island you desire, keeping mother nature still and quiet and avoiding the influence calculation on said island. ";
                description[6] += "Be advised though: before leaving mother nature will surely return the sign to Granny Herbs, as she is an educated fellow.";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        ((GrandmaHerbs) card).getNoEntry(),
                        null,
                        description,
                        LightCharacterType.INTEGER_1);
                
                
            case TRUFFLE_HUNTER:
                description[0] += "This peculiar individual, as the name might imply, is a mushroom enthusiast, his \"hunting\" sessions consisting ";
                description[1] += "of slow paced walks in the woods, looking under every fern and heap of grass to find another specimen to add to his prized possessions.";
                description[2] += "What on earth could this man offer to you? Well, for a significant cost, he will bring \"hunting\" with him all the students of ";
                description[3] += "the selected color that he will find on the island on which mother nature has ended her movement, ensuring that they do not count ";
                description[4] += "towards the influence calculation.";
                description[5] += "Use this character to help you mitigate your incompetence in gaining control of professors, or just to demonstrate that ";
                description[6] += "you too, like the Knight, can conquer a well defended island, albeit with a little help.";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        null,
                        description,
                        LightCharacterType.COLOR);

            case COOK:
                description[0] += "The Cook is responsible for devising and executing all the \"gourmet\" recipes served in all of Eriantys schools. ";
                description[1] += "They may not be the most exquisite dishes of the three continents, but at least is something! After all, the ";
                description[2] += "Erianti are quite simple in taste, and quite eager to eat, feast, and drink. The Professors are well known ";
                description[3] += "in their appreciation for Cook's endeavours, and, when he is activated, quite literally flee from their positions ";
                description[4] += "to join the professor table of your school; if there more or at least the same students than in the schools they abandoned, ";
                description[5] += "of course. Professors hold their following more dear than their guts after all...";
                description[6] += "Use this character to instantly gain much needed professors, even in case of a draw! ";
                 return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        null,
                        description,
                        LightCharacterType.NONE);

            case MINSTREL:
                description[0] += "The Minstrel composes and sings only for the most noble Kings and the fairiest Queens; at least that's ";
                description[1] += "what he claims he used to do. Now he is employed as a janitor in various schools (for the minimum wage), ";
                description[2] += "with the duty of watching  over the Entrances, making sure that the students behave. Still, his voice holds the ";
                description[3] += "power to seduce and confuse  people through intricate and well-composed verses. For quite a small fee, you can ask ";
                description[4] += "him to convince up to two  students of your choice, located in your entrance, to swap places with up to two students, ";
                description[5] += "also of your choice, located in your dining room. Use this card to help the survival of this fallen artist and to ";
                description[6] += "reorganize your assets in a single blow! ";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        null,
                        description,
                        LightCharacterType.INTEGER_2);

            case THIEF:
                description[0] += "The shadiest and most mischievous of all characters you may encounter, the Thief will do anything for the right ";
                description[1] += "amount of gold. Well, except things that don't involve thieving around. Some say that he is, in reality, an agent of ";
                description[2] += "the King, working in disguise; others say he's just a petty thief. For a considerable amount of gold, he will steal for you ";
                description[3] += "up to three students of the specified color from every school dining room (including yours), and will deliver them ";
                description[4] += "to you in no time. Of course he will be wondering what are you actually going to do with those students; he doesn't know ";
                description[5] += "you just want to put them back into the game bag.... anyways, who cares about what he may think, he's probably just a petty ";
                description[6] += "thief after all! Use this card to negate all enemies their advantages in controlling professors, but be careful not to sabotage yourself! ";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        null,
                        description,
                        LightCharacterType.COLOR);

            case JESTER:
                description[0] += "The jolliest of them all, the Jester helps others by bringing joy to their otherwise helpless and unfulfilling lives. ";
                description[1] += "He runs a circus of his own, and for a small fee he will disguise up to three of his trained clowns (whatever that means) ";
                description[2] += "to swap positions with up to three students from your entrance; of course, you will be able to choose both of them, clowns ";
                description[3] += "and students. The students will be brought to the Jester's circus and instructed in the \"art of clowning\" as he calls it, and ";
                description[4] += "will serve the exact same purpose of the other clowns that preceded them and that are now learning something useful in your school. ";
                description[5] += "It's a rather strange business model, but to each their own we guess.... ";
                description[6] += "Use this card to quickly change the composition of your entrance.";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        ((Jester) card).getStudents(),
                        description,
                        LightCharacterType.INTEGER_2);

        }
        return null;
    }
}
