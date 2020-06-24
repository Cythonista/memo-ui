package com.memo.ui.dao;

import com.memo.ui.configuration.ApiCallConfigurationProperties;
import com.memo.ui.domain.CardList;
import com.memo.ui.domain.CardSelector;
import java.util.Collections;
import java.util.Map;
import javax.smartcardio.Card;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CardDaoImpl implements CardDao, InitializingBean {

    private ApiCallConfigurationProperties properties;

    private final RestOperations restOperations;

    private String cardApiUrlPrefix;

    public CardDaoImpl(ApiCallConfigurationProperties properties,RestOperations restOperations) {
        this.properties = properties;
        this.restOperations = restOperations;
    }

    @Override
    public CardList find(CardSelector cardSelector) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.cardApiUrlPrefix);
        return this.restOperations.getForObject(builder.build().toUriString(), CardList.class);
    }

    @Override
    public Card get(Long cardId) {
        String getApiUrl = this.cardApiUrlPrefix + "/{cardId}";
        Map<String, String> params = Collections.singletonMap("cardId", cardId.toString());
        ResponseEntity<Card> responseEntity = this.restOperations.getForEntity(getApiUrl, Card.class, params);
        return responseEntity.getBody();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.cardApiUrlPrefix = "http://" + this.properties.getHost() + ":" + this.properties.getPort() + "/list";
    }

}