
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is an automated test to create the bug that the odds ratio for the game
 * is incorrect (should be approximately 4.2 win:win+lose ratio)
 */

class TestBug3 {

  Dice d1 = new Dice();
  Dice d2 = new Dice();
  Dice d3 = new Dice();

  Game game = new Game(d1, d2, d3);
  List<DiceValue> cdv = game.getDiceValues();

  final static int STARTING_BALANCE = 1000000;

  static String name = "Anna";

  static Player player = new Player(name, STARTING_BALANCE);

  final static int BET = 5;

  final static int ZERO_LIMIT = 0;

  final static int TURNS = 50000;

  final static float DESIRED_RATIO = 4.2f;

  @BeforeAll
  public static void setupAll() {

    System.out.println("Automated Test for Bug 3: Odds ratio is not approx. 4.2%");

    System.out.println("Start Game: Number of Turns = " + TURNS);
    System.out.println(String.format("%s starts with balance %d, limit %d",
            player.getName(), player.getBalance(), player.getLimit()));
    System.out.println();

  }

  @BeforeEach
  public void setupEach() {

    player = new Player(name, STARTING_BALANCE);
    player.setLimit(ZERO_LIMIT);

    game = new Game(d1,d2,d3);

  }

  @Test
  public void testIncorrectOddsRatio() {

    DiceValue pick = DiceValue.CROWN;

    int winnings = 0;
    int totalWinnings = 0;
    int totalWins = 0;
    int totalLosses = 0;
    float ratio = 0.0f;

    // loop for a number of turns
    for (int i = 0; i < TURNS; i++) {

      int winCount = 0;
      int loseCount = 0;

      Dice d1 = new Dice();
      Dice d2 = new Dice();
      Dice d3 = new Dice();

      game = new Game(d1,d2,d3);

      // Play the round with pick
      winnings = game.playRound(player, pick, BET);

      // Get the dice rolled
      cdv = game.getDiceValues();

      totalWinnings += winnings;

      System.out.println("Turn " + (i+1));
      System.out.printf("Rolled %s, %s, %s\n", cdv.get(0), cdv.get(1), cdv.get(2));
      System.out.printf("%s bet %d on %s\n", player.getName(), BET, pick);
      System.out.printf("balance now %d | limit: %d\n", player.getBalance(), player.getLimit());
      System.out.printf("Winnings for this bet: %d, Total Winnings: %d\n", winnings, totalWinnings);

      if (winnings > 0) {
        System.out.printf("%s won %d, balance now %d\n",
                player.getName(), winnings, player.getBalance());
        winCount++;
      }
      else {
        System.out.printf("%s lost, balance now %d\n",
                player.getName(), player.getBalance());
        loseCount++;
      }

      System.out.println(String.format("Win count = %d, Lose Count = %d, %.2f",
              winCount, loseCount, (float) winCount/(winCount+loseCount)));

      totalWins += winCount;
      totalLosses += loseCount;

      System.out.println(String.format("Total Win count = %d, Total Loss Count = %d",
              totalWins, totalLosses));

      ratio = (float)(totalWins * 100) / (totalWins + totalLosses);

      System.out.println(String.format("Overall win rate = %.1f%%", ratio));
      System.out.println();

    }

    System.out.println("GAME OVER");

    // assert that odds ratio is not approximately 4.2% (i.e. within +/- 0.1)

    assertFalse((ratio > DESIRED_RATIO - 0.1f) && (ratio < DESIRED_RATIO + 0.1f));

  }

}

