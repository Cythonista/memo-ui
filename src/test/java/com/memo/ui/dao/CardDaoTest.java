package com.memo.ui.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import com.memo.ui.UiApplication;
import com.memo.ui.domain.Card;
import com.memo.ui.domain.CardList;
import com.memo.ui.test.JsonConverter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = UiApplication.class)
public class CardDaoTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CardDaoImpl target;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFind() {
        List<Card> cards = new ArrayList<>();
        Card card = new Card();
        card.setCardId(1L);
        card.setCardName("カード1");
        cards.add(card);
        CardList cardList = new CardList();
        cardList.setCards(cards);

        String expectJson = JsonConverter.toString(cardList);
        MockRestServiceServer mockRestServiceServer= MockRestServiceServer.bindTo(restTemplate).build();
        mockRestServiceServer.expect(requestTo("http://localhost:8080/list"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectJson, MediaType.APPLICATION_JSON_UTF8));

        CardList findResult = target.find();
        assertEquals(1, findResult.getCards().size());
        Card actual = findResult.getCards().get(0);
        assertEquals(card.getCardId(), actual.getCardId());
        assertEquals(card.getCardName(), actual.getCardName());
        mockRestServiceServer.verify();
    }

    @Test
    public void testAdd() {
        Card card = new Card();
        card.setCardId(1L);
        card.setCardName("カードチャンネル");

        String expectJson = JsonConverter.toString(card);
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockRestServiceServer
                .expect(requestTo("http://localhost:8080/v1/list"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().string(expectJson))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON_UTF8));

        target.add(card);
        mockRestServiceServer.verify();

    }
}
