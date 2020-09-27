package engine.model;

public class Result {
    private final boolean success;
    private final String feedback;
    private static final String RESULT_RIGHT="Congratulations, you're right!";
    private static final String RESULT_WRONG="Wrong answer! Please, try again.";

    public Result(boolean success) {
        this.success = success;
        this.feedback = success?RESULT_RIGHT:RESULT_WRONG;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
