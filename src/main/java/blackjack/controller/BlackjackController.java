package blackjack.controller;

import blackjack.domain.betting.BettingMoney;
import blackjack.domain.betting.ProfitCalculator;
import blackjack.domain.card.Deck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participants;
import blackjack.domain.participant.Player;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import java.util.List;
import java.util.Map;

public class BlackjackController {

    public void run() {
        Deck deck = new Deck();
        Participants participants = createParticipants();

        askBettingMoney(participants);

        handOutAndPrintInitialCards(participants, deck);

        handOutMoreCards(participants, deck);

        printResult(participants);
    }

    private void askBettingMoney(Participants participants) {
        for (Player player : participants.getPlayers()) {
            BettingMoney bettingMoney = new BettingMoney(InputView.inputPlayerBettingMoney(player.getName()));
            player.createBettingMoney(bettingMoney);
        }
    }

    private Participants createParticipants() {
        return Participants.from(InputView.inputPlayerName());
    }

    private void handOutAndPrintInitialCards(Participants participants, Deck deck) {
        participants.handOutInitialCards(deck);
        OutputView.printInitialCardInformation(participants);
    }

    private void handOutMoreCards(Participants participants, Deck deck) {
        handOutMoreCardsToPlayers(participants.getPlayers(), deck);
        handOutMoreCardsToDealer(participants.getDealer(), deck);
    }

    private void handOutMoreCardsToPlayers(List<Player> players, Deck deck) {
        for (Player player : players) {
            handOutMoreCardsToPlayer(player, deck);
        }
    }

    private void handOutMoreCardsToPlayer(Player player, Deck deck) {
        boolean cardPrintFlag = isPlayerWantMoreCards(player, deck);

        if (!player.isFinished()) {
            player.stay();
        }
        if (!cardPrintFlag) {
            OutputView.printPlayerCardInformation(player);
        }
    }

    private boolean isPlayerWantMoreCards(Player player, Deck deck) {
        boolean cardPrintFlag = false;

        while (isHittable(player) && isPlayerWantToHit(player)) {
            player.draw(deck.pickCard());
            OutputView.printPlayerCardInformation(player);
            cardPrintFlag = true;
        }
        return cardPrintFlag;
    }

    private boolean isHittable(Player player) {
        if (!player.isHittable()) {
            OutputView.printPlayerHitImpossibleMessage(player.getName());
            return false;
        }
        return true;
    }

    private boolean isPlayerWantToHit(Player player) {
        return InputView.inputPlayerHit(player.getName()).equals("y");
    }

    private void handOutMoreCardsToDealer(Dealer dealer, Deck deck) {
        while (dealer.isHittable()) {
            OutputView.printDealerHitMessage();
            dealer.draw(deck.pickCard());
        }
        if (!dealer.isFinished()) {
            dealer.stay();
        }
    }

    private void printResult(Participants participants) {
        OutputView.printCardsAndPoint(participants);

        printProfitResult(participants);
    }

    private void printProfitResult(Participants participants) {
        ProfitCalculator profitCalculator = new ProfitCalculator(participants);

        profitCalculator.calculate();

        Map<Player, Long> playerProfitResult = profitCalculator.getPlayerProfit();
        long dealerProfitResult = profitCalculator.calculateDealerProfit();

        OutputView.printProfitResult(playerProfitResult, dealerProfitResult);
    }

}
