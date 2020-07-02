package com.memo.ui.dao;

import com.memo.ui.domain.Card;
import com.memo.ui.domain.CardList;


public interface CardDao {
    CardList find();
    Card get(Long cardId);
    void add(Card card);
    void set(Card card);
    void remove(Long cardId);
}
