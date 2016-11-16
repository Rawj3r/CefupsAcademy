package nkosi.roger.cefupsacademy;

public class StudentModel {
    String pname, pprice, pimg;
    int pid;

    public StudentModel(StudentBuilder builder) {
        pname = builder.ppname;
        pprice = builder.ppprice;
        pimg = builder.ppimg;
        pid = builder.ppid;

    }

    public static class StudentBuilder{
        String ppname, ppprice, ppimg;
        int ppid;

        public StudentBuilder setppName(String name){
            ppname = name;
            return StudentBuilder.this;
        }

        public StudentBuilder setppPrice(String price){
            ppprice = price;
            return StudentBuilder.this;
        }

        public StudentBuilder setppImg(String image){
            ppimg = image;
            return StudentBuilder.this;
        }

        public StudentBuilder setpID(int i){
            ppid = i;
            return StudentBuilder.this;
        }

        public StudentModel buildProduct(){
            return new StudentModel(StudentBuilder.this);
        }
    }
}