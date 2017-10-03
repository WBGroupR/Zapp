
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is an automated test to create the bug that a player's balance never
 * increases when he/she wins on one matching die
 */

class TestBug1 {


  Dice d1 = new Dice();
  Dice d2 = new Dice();
  Dice d3 = new Dice();

  Game game = new Game(d1, d2, d3);
  List<DiceValue> cdv = game.getDiceValues();

  final static int STARTING_BALANCE = 30;

  static String name = "Sally";

  static Player player = new Player(name, STARTING_BALANCE);

  final static int BET = 5;

  static int limit = 0;

  @BeforeAll
  public static void setupAll () {

    System.out.println("Automated Test for Bug 1: Game does not pay at correct level");

    System.out.println("Start Game");
    System.out.println(String.format("%s starts with balance %d, limit %d",
                player.getName(), player.getBalance(), player.getLimit()));
    System.out.println();

  }

  @BeforeEach
  public void setupEach () {

    player = new Player(name, STARTING_BALANCE);
    player.setLimit(limit);

  }

  @Test
  public void testBalanceOneMatch () {

    DiceValue pick = DiceValue.CROWN;

    int numMatches = 1;
    int accumulateWinnings = 0;

    while (numMatches < 11) {

      // Play the round with pick
      int winnings = game.playRound(player, pick, BET);

      // Get the dice rolled
      cdv = game.getDiceValues();
      System.out.printf("Rolled %s, %s, %s\n", cdv.get(0), cdv.get(1), cdv.get(2));
      System.out.printf("%s bet %d on %s\n", player.getName(), BET, pick);
      System.out.printf("balance now %d\n\n", player.getBalance());
      System.out.printf("Winnings: %d\n", winnings);
      System.out.println();

      // Keep looping if match > 1 until 10 single matches found
      if (winnings == BET) {

        numMatches++;
        accumulateWinnings += winnings;

      }

      winnings = 0;

    }


    // assert that balance is not increased by single match over the 10 rolls
    // where single match occurred

    assertEquals(0, player.getBalance() - STARTING_BALANCE);

  }


}
