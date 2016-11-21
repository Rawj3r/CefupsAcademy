package nkosi.roger.cefupsacademy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class APIController {
    private final String TAG = APIController.class.getSimpleName();
    private RestApiManager restApiManager;
    private HomeCallBackListener listener;
    private TaskCallBackListener taskCallBackListener;
    private StudentsCallBackListener studentsCallBackListener;
    private SubjectSCallBackListener subjectSCallBackListener;

    public APIController(SubjectSCallBackListener subjectSCallBackListener) {
        this.subjectSCallBackListener = subjectSCallBackListener;
        restApiManager = new RestApiManager();
    }

    public APIController(HomeCallBackListener listener) {
        this.listener = listener;
        restApiManager = new RestApiManager();
    }

    public APIController(StudentsCallBackListener studentsCallBackListener){
        this.studentsCallBackListener = studentsCallBackListener;
        restApiManager = new RestApiManager();
    }

    public APIController(TaskCallBackListener taskCallBackListener){
        this.taskCallBackListener = taskCallBackListener;
        restApiManager = new RestApiManager();
    }



    public void fetchPosts(){

        HashMap <String, String> map = new HashMap<>();
        map.put("method", "getposts");
        restApiManager.getHomeAPi().getPosts(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, "JSON :: " + s);
                try {
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);


                        PostModel model = new PostModel.PBuilder()
                                .setBody(jsonObject.getString("post"))
                                .setSubject(jsonObject.getString("uri"))
                                .setID(jsonObject.getString("postID"))
                                .setDateAdded(jsonObject.getString("dateAdded"))
                                .setName(jsonObject.getString("firstName")+ " " +jsonObject.getString("lastName"))
                                .buiuld();
                        listener.onFetchProgress(model);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                listener.onFetchComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error :: " + error.getMessage());
                listener.onFetchComplete();
            }
        });

    }

    public void fetchTasks(){
        HashMap <String, String> map = new HashMap<>();
        map.put("method", "getAllTasks");
        map.put("userID", "2");

        restApiManager.getHomeAPi().getTasks(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, "JSON :: " + s);

                try {
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        TaskModel model = new TaskModel.TaskBuilder()
                                .setContent(jsonObject.getString("content"))
                                .setDateAdded(jsonObject.getString("dateAdded"))
                                .setId(jsonObject.getString("idSchedule"))
                                .setImageUri(jsonObject.getString("firstName")
                                        + " " + jsonObject.getString("secondName")
                                        + " " + jsonObject.getString("lastName") )
                                .buildTask();
                        taskCallBackListener.onFetchProgress(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                taskCallBackListener.onFetchComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error :: " + error.getMessage());
                taskCallBackListener.onFetchComplete();
            }
        });
    }

    public void fetchSubjects(){
        HashMap <String, String> map = new HashMap<>();
        map.put("method", "getStudentSubjects");
        map.put("userID", "2");
        restApiManager.getHomeAPi().getSubjects(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, "JSON :: " + s);
                try {
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        SubjectModel model = new SubjectModel.SubjectsBuilder()
                                .setID(jsonObject.getString("idSubjects"))
                                .setName(jsonObject.getString("subjectName"))
                                .buildSub();
                        subjectSCallBackListener.onFetchProgress(model);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
//                studentsCallBackListener.onFetchComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                studentsCallBackListener.onFetchComplete();
            }
        });
    }

    public void fetchUsers(){

        HashMap <String, String> map = new HashMap<>();
        map.put("method", "getUsers");
        restApiManager.getHomeAPi().getProfiles(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, "JSON :: " + s);
                try {
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);

                        StudentModel model = new StudentModel.StudentBuilder()
                                .setppName(jsonObject.getString("firstName") +" " + jsonObject.getString("lastName"))
                                .setppPrice(jsonObject.getString("bio"))
                                .setpID(jsonObject.getInt("usersID"))
                                .setppImg(jsonObject.getString("uri"))
                                .buildProduct();
                        studentsCallBackListener.onFetchProgress(model);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                try {
                    studentsCallBackListener.onFetchComplete();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error :: " + error.getMessage());
                studentsCallBackListener.onFetchComplete();
            }
        });

    }


    public interface HomeCallBackListener{
        void onFetchStart();
        void onFetchProgress(PostModel model);
        void onFetchProgress(List<PostModel> modelList);
        void onFetchComplete();
        void onFetchFailed();
    }

    public interface StudentsCallBackListener{
        void onFetchStart();
        void onFetchProgress(StudentModel model);
        void onFetchProgress(List<StudentModel> models);
        void onFetchComplete();
        void onFetchFailed();
    }

    public interface TaskCallBackListener{
        void onFetchStart();
        void onFetchProgress(TaskModel model);
        void onFetchProgress(List<TaskModel> models);
        void onFetchComplete();
        void onFetchFailed();
    }

    public interface SubjectSCallBackListener{
        void onFetchStart();
        void onFetchProgress(SubjectModel model);
        void onFetchProgress(List<SubjectModel> modelList);
        void onFetchComplete();
        void onFetchFailed();
    }
}
