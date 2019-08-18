package ir.sircoder.sirchat.service.events;

public class ServiceEvents {
    public interface MessageInterface {
        String getUsername();
        String getMessage();
    }

    public static class InputMessages implements MessageInterface {
        private final String message;
        private final String username;

        public  InputMessages(String message, String username) {
            this.message = message;
            this.username = username;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public static class OutputMesage implements MessageInterface {
        private final String message;

        public OutputMesage(String message) {
            this.message = message;
        }

        @Override
        public String getUsername() {
            return "Me";
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}

