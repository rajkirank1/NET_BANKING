package org.training.sequence.generator.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.training.sequence.generator.model.entity.Sequence;
import org.training.sequence.generator.reporitory.SequenceRepository;
import org.training.sequence.generator.service.SequenceService;

@Service
public class SequenceServiceImpl implements SequenceService {

    private static final Logger log = LoggerFactory.getLogger(SequenceServiceImpl.class);

    private final SequenceRepository sequenceRepository;

    // Constructor initializing the final field
    public SequenceServiceImpl(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    /**
     * Create a new account number.
     *
     * @return The newly created account number.
     */
    @Override
    public Sequence create() {
        log.info("creating an account number");

        return sequenceRepository.findById(1L)
                .map(sequence -> {
                    sequence.setAccountNumber(sequence.getAccountNumber() + 1);
                    return sequenceRepository.save(sequence);
                })
                .orElseGet(() -> sequenceRepository.save(Sequence.builder().accountNumber(1L).build()));
    }
}
