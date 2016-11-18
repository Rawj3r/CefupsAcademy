package nkosi.roger.cefupsacademy;

/**
 * Created by roger on 10/24/2016.
 */

public class PostModel {
    String subject, body, id, datedAdded, name;


    public PostModel(PBuilder builder) {
        this.body = builder.body;
        this.subject = builder.subject;
        this.id = builder.id;
        this.datedAdded = builder.datedAdded;
        this.name = builder.name;
    }

    public static class PBuilder{
        String subject, body, id, datedAdded, name;

        public PBuilder setName(String s){
            this.name = s;
            return PBuilder.this;
        }

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
