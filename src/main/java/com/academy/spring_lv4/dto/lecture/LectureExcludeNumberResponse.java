package com.academy.spring_lv4.dto.lecture;

import com.academy.spring_lv4.dto.teacher.TeacherExcludeNumberResponseDto;
import com.academy.spring_lv4.entity.Lecture;
import com.academy.spring_lv4.entity.LectureCategoryEnum;
import lombok.Getter;

@Getter
public class LectureExcludeNumberResponse {
    private Long id;
    private String name;
    private String introduce;
    private LectureCategoryEnum category;
    private int price;
    private TeacherExcludeNumberResponseDto teacher; // 핸드폰 번호를 제외하기 위하여 (강의 상세 조회)
    private int likes;

    public LectureExcludeNumberResponse(Lecture lecture, int likes) {
        this.id = lecture.getId();
        this.name = lecture.getName();
        this.introduce = lecture.getIntroduce();
        this.price = lecture.getPrice();
        this.category = lecture.getCategory();
        this.teacher = new TeacherExcludeNumberResponseDto(lecture.getTeacher());
        this.likes = likes;
    }
}
