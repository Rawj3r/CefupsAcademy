package nkosi.roger.cefupsacademy;

/**
 * Created by roger on 10/24/2016.
 */

public class PostModel {
    String subject, body, id, datedAdded;


    public PostModel(PBuilder builder) {
        this.body = builder.body;
        this.subject = builder.subject;
        this.id = builder.id;
        this.datedAdded = builder.datedAdded;
    }

    public static class PBuilder{
        String subject, body, id, datedAdded;

        public PBuilder setDateAdded(String dateAdded){
            this.datedAdded = dateAdded;
            return PBuilder.this;
        }

        public  PBuilder setSubject(String subject){
            this.subject = subject;
            return PBuilder.this;
        }

        public PBuilder setBody(String body){
            this.body = body;
            return PBuilder.this;
        }

        public PBuilder setID(String id){
            this.id = id;
            return PBuilder.this;
        }

        public PostModel buiuld(){
            return new PostModel(PBuilder.this);
        }
    }
}
