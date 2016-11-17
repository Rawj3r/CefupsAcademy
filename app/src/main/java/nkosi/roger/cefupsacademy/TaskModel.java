package nkosi.roger.cefupsacademy;

/**
 * Created by lulu on 11/17/2016.
 */

public class TaskModel {

    String content, imageUri, id, datedAdded;

    public TaskModel(TaskBuilder builder) {
        this.content = builder.content;
        this.imageUri = builder.imageUri;
        this.id = builder.id;
        this.datedAdded = builder.datedAdded;
    }

    public static class TaskBuilder{
        String content, imageUri, id, datedAdded;
        public TaskBuilder setContent(String content){
            this.content = content;
            return TaskBuilder.this;
        }

        public TaskBuilder setImageUri(String imageUri){
            this.imageUri = imageUri;
            return TaskBuilder.this;
        }

        public TaskBuilder setId(String id){
            this.id = id;
            return TaskBuilder.this;
        }

        public TaskBuilder setDateAdded(String dateAdded){
            this.datedAdded = dateAdded;
            return TaskBuilder.this;
        }

        public TaskModel buildTask(){
            return new TaskModel(TaskBuilder.this);
        }
    }
}
