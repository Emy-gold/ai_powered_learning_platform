package org.backend.modules.chat.repository;

import org.backend.domains.communication.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long> {

    List<Chat> findByParticipantsUserId(Long userId);
}
