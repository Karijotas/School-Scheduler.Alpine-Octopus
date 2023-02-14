package lt.techin.AlpineOctopusScheduler.exception;

public class SchedulerServiceDisabledException extends RuntimeException{

    public SchedulerServiceDisabledException() {
    }

    public SchedulerServiceDisabledException(String message) {
        super(message);
    }
}
