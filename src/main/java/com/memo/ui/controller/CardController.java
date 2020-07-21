package com.memo.ui.controller;

import com.memo.ui.dao.CardDao;
import com.memo.ui.domain.Card;
import com.memo.ui.domain.CardList;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "")
public class CardController {
    private final CardDao cardDao;
    private final MessageSource messageSource;

    public CardController(CardDao cardDao, MessageSource messageSource) {
        this.cardDao = cardDao;
        this.messageSource = messageSource;
    }

    @GetMapping(path="/list")
    public String list(Model model) {
        CardList cardList = this.cardDao.find();
        model.addAttribute("cards", cardList.getCards());
        model.addAttribute("alertString1", "alertString1_val");
        return "list";
    }

    @GetMapping(path="/add")
    public String add(@ModelAttribute Card card,Model model) {
        return "add";
    }

    @PostMapping(path="/add")
    public String add(@ModelAttribute Card card, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            CardList cardList = cardDao.find();
            model.addAttribute("title", "カード新規登録");
            return "add";
        }
        this.cardDao.add(card);
        return "redirect:/list";
    }

    @GetMapping(path="/edit")
    public String edit(@RequestParam(name="cardId") Long cardId, Model model) {
        Card card = this.cardDao.get(cardId);
        model.addAttribute("card", card);
        return "edit";
    }

    @PostMapping(path="/edit")
    public String edit(@ModelAttribute Card card, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "edit";
        }
        this.cardDao.set(card);
        return "redirect:/list";
    }

}
