
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is an automated test to create the bug that a player can never reach the
 * betting limit (there will always be $5 left in the balance)
 */

class TestBug2 {


  Dice d1 = new Dice();
  Dice d2 = new Dice();
  Dice d3 = new Dice();

  Game game = new Game(d1, d2, d3);
  List<DiceValue> cdv = game.getDiceValues();

  final static int STARTING_BALANCE = 30;

  static String name = "Richard";

  static Player player = new Player(name, STARTING_BALANCE);

  final static int BET = 5;

  final static int ZERO_LIMIT = 0;

  final static int TURNS = 10;


  @BeforeAll
  public static void setupAll() {

    System.out.println("Automated Test for Bug 2: Player cannot reach betting limit");

    System.out.println("Start Game: Number of Turns = " + TURNS);
    System.out.println(String.format("%s starts with balance %d, limit %d",
            player.getName(), player.getBalance(), player.getLimit()));
    System.out.println();

  }

  @BeforeEach
  public void setupEach() {

    player = new Player(name, STARTING_BALANCE);
    player.setLimit(ZERO_LIMIT);

  }


  @Test
  public void testUnreachableBettingLimit() {

    DiceValue pick = DiceValue.CROWN;

    int winnings = 0;
    int totalWinnings = 0;

    // loop for 100 turns
    for (int i = 0; i < TURNS; i++) {

      // Play the round with pick
      winnings = game.playRound(player, pick, BET);

      // Get the dice rolled
      cdv = game.getDiceValues();

      totalWinnings += winnings;
      System.out.printf("Rolled %s, %s, %s\n", cdv.get(0), cdv.get(1), cdv.get(2));
      System.out.printf("%s bet %d on %s\n", player.getName(), BET, pick);
      System.out.printf("balance now %d | limit: %d\n\n", player.getBalance(), player.getLimit());
      System.out.printf("Winnings for this bet: %d, Total Winnings: %d\n", winnings, totalWinnings);
      System.out.println();

    }

    System.out.println("GAME OVER");

    // assert that player's balance does not equal the limit of zero

    assertNotEquals(ZERO_LIMIT, player.getBalance());

  }
}

