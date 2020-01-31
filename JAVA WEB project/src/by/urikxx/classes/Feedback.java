package by.urikxx.classes;

public class Feedback {
    private String feedback;
    private Integer mark;

    public Feedback(String feedback, Integer mark) {
        this.feedback = feedback;
        this.mark = mark;
    }

    public Feedback() {
        this.feedback = null;
        this.mark = null;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) throws StudentException {
        if (mark < 0 && mark > 10)
            throw new StudentException("Оценка должна быть от 0 до 10");
        this.mark = mark;
    }
}
