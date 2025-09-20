package com.bethunter.bethunter_api.mapper;

import com.bethunter.bethunter_api.dto.lesson.LessonRequestCreate;
import com.bethunter.bethunter_api.dto.lesson.LessonRequestUpdate;
import com.bethunter.bethunter_api.dto.lesson.LessonUserProgressDTO;
import com.bethunter.bethunter_api.dto.lesson.LessonResponse;
import com.bethunter.bethunter_api.model.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public Lesson toEntity(LessonRequestCreate dto) {
        if (dto == null) return null;
        return new Lesson(dto.title());
    }

    public void updateEntity(Lesson lesson, LessonRequestUpdate dto) {
        if (lesson == null || dto == null) return;

        if (dto.title() != null) {
            lesson.setTitle(dto.title());
        }
    }

    public LessonResponse toUserResponse(Lesson lesson) {
        if (lesson == null) return null;
        return new LessonResponse(
                lesson.getId(),
                lesson.getTitle()
        );
    }

    public LessonUserProgressDTO toUserProgressDTO(Lesson lesson, int totalTopics, int completedTopics) {
        if (lesson == null) return null;

        double progressPercent = totalTopics == 0 ? 0.0 :
                (completedTopics * 100.0) / totalTopics;

        return new LessonUserProgressDTO(
                lesson.getId(),
                lesson.getTitle(),
                totalTopics,
                completedTopics,
                progressPercent
        );
    }
}