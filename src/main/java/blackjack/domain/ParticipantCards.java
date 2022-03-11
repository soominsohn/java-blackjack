package blackjack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticipantCards {

    private final static int NO_COUNT = 0;

    private final List<Card> cards;

    public ParticipantCards(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public int calculateScore() {
        int totalScore = cards.stream().mapToInt(Card::getPoint).sum();
        int aceCount = getAceCount();
        if (aceCount != NO_COUNT) {
            return calculateScoreWithAce(aceCount, totalScore);
        }
        return totalScore;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    private int getAceCount() {
        return (int) cards.stream().filter(Card::isAce).count();
    }

    private int calculateScoreWithAce(int aceCount, int totalScore) {
        while (aceCount > 0 && totalScore > 21) {
            totalScore = totalScore - 10;
            aceCount--;
        }
        return totalScore;
    }


    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
