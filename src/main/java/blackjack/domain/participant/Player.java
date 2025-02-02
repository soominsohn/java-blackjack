package blackjack.domain.participant;

import blackjack.domain.betting.BettingMoney;

public class Player extends Participant {

    private static final int HIT_THRESHOLD_NUMBER = 21;

    private static final String DEALER_NAME = "딜러";

    private static final String PLAYER_NAME_EMPTY_ERROR_MESSAGE = "[ERROR] 플레이어 이름에 빈 값이 올 수 없습니다.";
    private static final String PLAYER_NAME_BLANK_ERROR_MESSAGE = "[ERROR] 플레이어 이름에 공백만 올 수 없습니다.";
    private static final String PLAYER_NAME_DEALER_ERROR_MESSAGE = "[ERROR] 플레이어 이름은 딜러가 될 수 없습니다.";

    private BettingMoney bettingMoney;

    public Player(String name) {
        validateName(name);
        this.name = name;
    }

    @Override
    public boolean isHittable() {
        return getScore() < HIT_THRESHOLD_NUMBER;
    }

    public void createBettingMoney(BettingMoney bettingMoney) {
        this.bettingMoney = bettingMoney;
    }

    @Override
    public String getName() {
        return name;
    }

    public long getProfit(Dealer dealer) {
        return (long) state.profit((bettingMoney.getBettingMoney()), dealer.state);
    }

    public BettingMoney getBettingMoney() {
        return bettingMoney;
    }

    private void validateName(String name) {
        checkNullAndEmpty(name);
        checkBlank(name);
        checkNameDealer(name);
    }

    private void checkNameDealer(String name) {
        if (name.equals(DEALER_NAME)) {
            throw new IllegalArgumentException(PLAYER_NAME_DEALER_ERROR_MESSAGE);
        }
    }

    private void checkNullAndEmpty(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(PLAYER_NAME_EMPTY_ERROR_MESSAGE);
        }
    }

    private void checkBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(PLAYER_NAME_BLANK_ERROR_MESSAGE);
        }
    }

}
