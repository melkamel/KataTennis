package secondsprint.secondstory;

import domain.model.Player;
import firstsprint.secondstory.GameWithDeuceCase;
import java.util.Optional;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import secondsprint.firststory.Set;


@Slf4j
@Builder
public class SetWithTieBreak {

  private Player playerOne;
  private Player playerTwo;


  public Optional<Player> getSetWinner(){

    if (playerOne.getGameWon() == 6 && playerTwo.getGameWon() <= 4){
      return Optional.of(playerOne);
    }

    if (playerTwo.getGameWon() == 6 && playerOne.getGameWon() <= 4){
      return Optional.of(playerTwo);
    }

    if (playerOne.getGameWon() == 5 && playerTwo.getGameWon() == 7){
      return Optional.of(playerTwo);
    }

    if (playerTwo.getGameWon() == 5 && playerOne.getGameWon() == 7){
      return Optional.of(playerOne);
    }

    if (playerTwo.getGameWon() == 6 && playerOne.getGameWon() == 6){
      return TieBreak.builder().playerOne(playerOne).playerTwo(playerTwo).build().playTieBreak();
    }
    return Optional.empty();
  }

  public Player playSet() {
    Optional<Player> setPlayerWinner;
    while(!(setPlayerWinner = getSetWinner()).isPresent()) {
      GameWithDeuceCase gameWithDeuceCase = GameWithDeuceCase.builder().playerOne(playerOne).playerTwo(playerTwo).build();
      gameWithDeuceCase.playGame();
    }
    log.info(" {} wins set with score : {} - {}", setPlayerWinner.get().getName(), playerOne.getGameWon(), playerTwo.getGameWon());
    this.reset();
    return  setPlayerWinner.get();
  }

  private void reset(){
    this.playerOne.setGameWon(0);
    this.playerTwo.setGameWon(0);
  }

  public static void main(String[] args) {
    Player playerOne = Player.builder().score(0).name("Djokovich").build();
    Player playerTwo = Player.builder().score(0).name("Federrer").build();
    Set set = Set.builder().playerOne(playerOne).playerTwo(playerTwo).build();
    set.playSet();
  }



}
