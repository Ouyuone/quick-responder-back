package tech.ouyu.quickResponder.back.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Time{
    private String realSeveralLesson;
    private String severalLesson;
    private boolean checked;
    private int userId;
}