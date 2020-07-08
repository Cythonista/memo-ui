package com.memo.ui.controller;

import com.memo.ui.dao.CardDao;
import com.memo.ui.domain.Card;
import com.memo.ui.domain.CardList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1")
public class CardController {
    private final CardDao cardDao;

    public CardController(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @GetMapping(path="/list")
    public String list(Model model) {
        CardList cardList = this.cardDao.find();
        model.addAttribute("cards", cardList.getCards());
        model.addAttribute("alertString1", "alertString1_val");
        return "list";
    }

    @GetMapping(path="/add")
    public String add(@ModelAttribute Card card, Model model) {
        model.addAttribute("card", new Card());
        return "add";
    }

    @PostMapping(path="/add")
    public String add(@Validated Card card, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "カード新規登録");
            return "add";
        }
        this.cardDao.add(card);
        return "redirect:list";
    }
}
