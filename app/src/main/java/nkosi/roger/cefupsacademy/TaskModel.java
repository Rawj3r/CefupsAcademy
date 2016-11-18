package nkosi.roger.cefupsacademy;

/**
 * Created by lulu on 11/17/2016.
 */

public class TaskModel {

    String content, imageUri, id, datedAdded;
    String subject, marks;
    String studentSubject, subjectID;

    public TaskModel(TaskBuilder builder) {
        this.content = builder.content;
        this.imageUri = builder.imageUri;
        this.id = builder.id;
        this.datedAdded = builder.datedAdded;
    }

    public TaskModel(AssignmentBuidler buidler){
        subject = buidler.subject;
        marks = buidler.marks;
    }

    public TaskModel(SubjectsBuilder subjectsBuilder){
        this.subjectID = subjectsBuilder.id;
        this.studentSubject = subjectsBuilder.name;
    }

    public static class SubjectsBuilder{
        String name, id;
        public SubjectsBuilder setName(String name){
            this.name = name;
            return SubjectsBuilder.this;
        }

        public SubjectsBuilder setID(String id){
            this.id = id;
            return SubjectsBuilder.this;
        }

        public TaskModel buildSub(){
            return new TaskModel(SubjectsBuilder.this);
        }
    }

    public static class AssignmentBuidler{
        String subject, marks;
        public AssignmentBuidler setSubject(String s){
            subject = s;
            return AssignmentBuidler.this;
        }

        public AssignmentBuidler setMarks(String s){
            marks = s;
            return AssignmentBuidler.this;
        }

        public TaskModel buildAss(){
            return new TaskModel(AssignmentBuidler.this);
        }
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
