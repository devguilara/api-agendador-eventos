package com.devguilara.api.cadastroeventos.service;

import com.devguilara.api.cadastroeventos.DTO.*;
import com.devguilara.api.cadastroeventos.exceptions.EventNotFoundException;
import com.devguilara.api.cadastroeventos.model.Event;
import com.devguilara.api.cadastroeventos.model.User;
import com.devguilara.api.cadastroeventos.model.Subscription;
import com.devguilara.api.cadastroeventos.repository.EventRepository;
import com.devguilara.api.cadastroeventos.repository.SubscriptionRepository;
import com.devguilara.api.cadastroeventos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Locale.filter;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionRes createNewSubscription(String eventName, User user, Integer userId){
        Event event = eventRepository.findByPrettyName(eventName);
        if (event == null){
            throw  new EventNotFoundException("Evento  : " +" ' "+ eventName +" ' "+ " nao existe");
        }

        User userFromDB = userRepository.findByEmail(user.getEmail());
        if(userFromDB == null){
            userFromDB = userRepository.save(user);
        }

        User indicador =  null;
        if(userId != null ){
            indicador = userRepository.findById(userId).orElse(null);
            if (indicador == null) {
                throw new UserIndicadorNotFoundException("Usuário " + userId + " nao encontrado");
            }
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(userFromDB);
        subscription.setIndication(indicador);

        Subscription tmpSub = subscriptionRepository.findByEventAndSubscriber(event, userFromDB);
        if (tmpSub != null){
            throw new SubscriptionConflictException("Já existe uma inscrição para o email : " + userFromDB.getEmail());
        }

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return new SubscriptionRes(savedSubscription.getSubscriptionNumber(),
                "http://localhost:8080/subscription/"+savedSubscription.getEvent().getPrettyName()+"/"+savedSubscription.getSubscriber().getId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName) {
        Event event = eventRepository.findByPrettyName(prettyName);
        if(event == null){
            throw new EventNotFoundException("Ranking do evento : "+ prettyName + "não existe");
        }
        return subscriptionRepository.generateRanking(event.getEventId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId) {
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream().filter(i -> i.userId().equals(userId)).findFirst().orElse(null);
        if(item == null){
            throw new UserIndicadorNotFoundException("O usuário não possui ranking");
        }
        Integer position = IntStream.range(0, ranking.size())
                .filter(i -> ranking.get(i).userId().equals(userId))
                .findFirst().getAsInt();

        return new SubscriptionRankingByUser(item, position+1);
    }
}
