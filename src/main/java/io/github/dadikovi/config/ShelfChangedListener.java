package io.github.dadikovi.config;

import io.github.dadikovi.domain.ShelfChangedMessage;
import io.github.dadikovi.repository.BookRepository;
import io.github.dadikovi.service.StatCalculatorService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "shelfChanged")
@Service
public class ShelfChangedListener {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StatCalculatorService calculatorService;

    @RabbitHandler
    public void handle(ShelfChangedMessage message) {
        switch ( message.getChangeType() ) {
            case CREATE:
            case UPDATE:
                bookRepository.save(message.getChangedBook());
                break;
            case DELETE:
                bookRepository.deleteById(message.getChangedBook().getId());
                break;
        }

        calculatorService.calculateAllStats();

    }
}
