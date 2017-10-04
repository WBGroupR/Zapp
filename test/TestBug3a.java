import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This is an automated test to check if the odds ratio problem (Bug 3)
 * is caused by the DiceValue class never passing the value of one of the dice SPADE (value 5)
 * to its calling modules. It will run a large number of dice rolls to see if the SPADE value
 * is ever generated.
 */

class TestBug3a {

  final static int TURNS = 1000;

  @BeforeAll
  public static void setupAll() {

    System.out.println("Automated Test for Bug 3a: the dice never roll a SPADE");

    System.out.println("Start Game: Number of Turns = " + TURNS);

  }

  @BeforeEach
  public void setupEach() {

  }

  @Test
  public void testIfDiceEverSpade() {

    boolean foundSPADE = false;

    // loop for a number of turns
    for (int i = 0; i < TURNS; i++) {

      Dice d = new Dice();

      System.out.println(d.toString());

      if (d.getValue().equals(DiceValue.SPADE)) {

        foundSPADE = true;

      }

    }

    System.out.println("SPADE has been rolled? " + foundSPADE);


    // assert that dice will never roll a SPADE

    assertFalse(foundSPADE);

  }

}

