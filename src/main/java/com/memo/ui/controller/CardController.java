package com.memo.ui.controller;

import com.memo.ui.dao.CardDao;
import com.memo.ui.domain.CardList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/services/v1/cards")
public class CardController {
    private final CardDao cardDao;

    public CardController(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @GetMapping(path="/list")
    public String list(Model model) {
        CardList cardList = this.cardDao.find();
        model.addAttribute("cards", cardList.getCards());
        return "card/list";
    }
}
