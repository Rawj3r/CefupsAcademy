package nkosi.roger.cefupsacademy;

/**
 * Created by lulu on 11/19/2016.
 */

public class SubjectModel {

    String studentSubject, subjectID;

    public SubjectModel(SubjectsBuilder subjectsBuilder){
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

        public SubjectModel buildSub(){
            return new SubjectModel(SubjectsBuilder.this);
        }


    }
}
