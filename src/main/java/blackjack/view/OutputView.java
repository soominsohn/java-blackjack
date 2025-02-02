package blackjack.view;

import blackjack.domain.card.Card;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Participants;
import blackjack.domain.participant.Player;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    private static final String NAME_DELIMITER = ", ";
    private static final String HANDOUT_MESSAGE = "\n딜러와 %s에게 2장의 카드를 나누어 주었습니다.\n";
    private static final String CARD_INFORMATION_FORMAT = "%s카드: %s";
    private static final String DEALER_HIT_MESSAGE = "딜러는 16이하라 한장의 카드를 더 받았습니다.\n";
    private static final String PARTICIPANT_POINT_RESULT = " - 결과: %d";
    private static final String PARTICIPANT_PROFIT_RESULT_MESSAGE = "\n## 최종 수익";
    private static final String DEALER_DIRECTION = "딜러:";
    private static final String RESULT_DELIMITER = ": ";
    private static final String PLAYER_HIT_IMPOSSIBLE_MESSAGE = "%s는 가진 카드의 합이 21이상이면 카드를 더 받을 수 없습니다.\n";

    public static void printInitialCardInformation(Participants participants) {
        List<String> participantName = participants.getPlayers().stream()
            .map(Player::getName)
            .collect(Collectors.toList());

        System.out.printf(HANDOUT_MESSAGE, String.join(NAME_DELIMITER, participantName));

        printInitialDealerCardInformation(participants.getDealer());
        printInitialPlayersCardInformation(participants.getPlayers());
        System.out.println();
    }

    public static void printPlayerCardInformation(Player player) {
        printCards(player);
        System.out.println();
    }

    public static void printDealerHitMessage() {
        System.out.println();
        System.out.print(DEALER_HIT_MESSAGE);
    }

    public static void printPlayerHitImpossibleMessage(String playerName) {
        System.out.printf(PLAYER_HIT_IMPOSSIBLE_MESSAGE, playerName);
    }

    public static void printCardsAndPoint(Participants participants) {
        System.out.println();
        for (Participant participant : participants.getParticipants()) {
            printCards(participant);
            printPoint(participant);
        }
    }

    public static void printProfitResult(Map<Player, Long> playerProfitResult, long dealerProfitResult) {
        System.out.println();
        System.out.println(PARTICIPANT_PROFIT_RESULT_MESSAGE);

        System.out.println(DEALER_DIRECTION + dealerProfitResult);

        playerProfitResult.forEach(
            (key, value) -> System.out.println(
                key.getName() + RESULT_DELIMITER + value));
    }

    private static void printInitialDealerCardInformation(Dealer dealer) {
        Card dealerFirstCard = dealer.getFirstCard();

        System.out.printf(CARD_INFORMATION_FORMAT, dealer.getName(),
            dealerFirstCard.getDenominationName() + dealerFirstCard.getSuitName());

        System.out.println();
    }

    private static void printInitialPlayersCardInformation(List<Player> players) {
        for (Player player : players) {
            printCards(player);
            System.out.println();
        }
    }

    private static void printCards(Participant participant) {
        List<String> participantCardInfo = participant.getCards()
            .stream()
            .map(x -> x.getDenominationName() + x.getSuitName())
            .collect(Collectors.toList());

        String cardInfo = String.join(NAME_DELIMITER, participantCardInfo);

        System.out.printf(CARD_INFORMATION_FORMAT, participant.getName(), cardInfo);
    }

    private static void printPoint(Participant participant) {
        System.out.printf(PARTICIPANT_POINT_RESULT, participant.getScore());
        System.out.println();
    }

}
