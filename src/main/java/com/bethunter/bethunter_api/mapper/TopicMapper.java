package com.bethunter.bethunter_api.mapper;

import com.bethunter.bethunter_api.dto.topic.TopicRequestCreate;
import com.bethunter.bethunter_api.dto.topic.TopicRequestUpdate;
import com.bethunter.bethunter_api.dto.topic.TopicResponse;
import com.bethunter.bethunter_api.dto.topic.TopicProgressResponse;
import com.bethunter.bethunter_api.model.Lesson;
import com.bethunter.bethunter_api.model.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public Topic toEntity(TopicRequestCreate dto, Lesson lesson) {
        if (dto == null || lesson == null) return null;
        return new Topic(lesson);
    }

    public void updateEntity(Topic topic, TopicRequestUpdate dto, Lesson lesson) {
        if (topic == null || dto == null || lesson == null) return;
        topic.setLesson(lesson);
    }

    public TopicResponse toResponseDTO(Topic topic) {
        if (topic == null) return null;
        return new TopicResponse(
                topic.getId(),
                topic.getLesson().getId()
        );
    }

    public TopicProgressResponse toProgressDTO(Topic topic, boolean completed) {
        if (topic == null) return null;
        return new TopicProgressResponse(
                topic.getId(),
                topic.getLesson().getId(),
                completed
        );
    }
}
