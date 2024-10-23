package com.fitness.controller.User_Ctrl_fxml;

import com.fitness.controller.LoginController;
import com.fitness.model.fitness.Course;
import com.fitness.model.fitness.Subscription;
import com.fitness.model.fitness.Workout;
import com.fitness.model.person.Coach;
import com.fitness.model.person.User;
import com.fitness.repositories.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

import static com.fitness.controller.LoginController.nameUser;

public class pnlMenusController {
    @FXML private Label time_myplan;
    @FXML private HBox hbox_mycourse = null;
    @FXML private Pane pane_coachProfile;
    @FXML private Pane layer_coachProfile;
    @FXML private Pane myCourseItem=null;
    @FXML private Pane pnlMenus;
    @FXML private Circle circleAva;
    @FXML private Label label_Username_myCourse=null;
    @FXML private Pane tabPane_schedule;
    @FXML private Button btn_next_workout;

    LoginController loginController=new LoginController();
    private CourseRepositoryImpl courseRepoImpl = new CourseRepositoryImpl();
    private SubscriptionRepositoryImpl subscriptionRepository=new SubscriptionRepositoryImpl();
    private UserRepositoryImpl userRepository=new UserRepositoryImpl();
    private CoachRepositoryImpl coachRepository=new CoachRepositoryImpl();
    private WorkoutRepositoryImpl workoutRepository=new WorkoutRepositoryImpl();

    List<Coach> coaches=coachRepository.getAll();
    List<Course> courses = courseRepoImpl.getAll();
    List<User> users=userRepository.getAll();
    List<Subscription> subscriptions=subscriptionRepository.getAll();
    List<Workout> workouts=workoutRepository.getAll();

    // Biến lưu trữ workout hiện tại và chỉ số
    private List<Workout> currentWorkouts = new ArrayList<>();
    private int workoutIndex = 0;

    public pnlMenusController() throws SQLException {
    }

    public void loadUserName(){
        label_Username_myCourse.setText(nameUser);
    }

    public void loadMyCourses() {
        List<Node> nodes = new ArrayList<>();
        for(Subscription subscription : subscriptions) {
            if(subscription.getMembername().equals(nameUser)) {
                for(Course course : courses) {
                    if(subscription.getCourseId().equals(course.getId())) {
                        try {
                            Node node = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/Item/itemMyCourse.fxml"));
                            node.setOnMouseClicked(event -> {
                                pane_coachProfile.setVisible(true);
                                tabPane_schedule.setVisible(true);
                                event.consume();
                            });
                            nodes.add(node);
                            hbox_mycourse.getChildren().add(node);
                            Label label_itemMyCourse = (Label) node.lookup("#label_itemMyCourse");
                            TextArea text_descriptionMycourse = (TextArea) node.lookup("#text_descriptionMycourse");
                            label_itemMyCourse.setText(course.getName());
                            text_descriptionMycourse.setWrapText(true);
                            text_descriptionMycourse.setEditable(false);
                            text_descriptionMycourse.setText(course.getDescription());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void startClock() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            String currentDateTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            time_myplan.setText(currentDateTime);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void coachProfileManager() {
        loadMyCourses();
        pane_coachProfile.setVisible(false);
        tabPane_schedule.setVisible(false);
        for (Node node : hbox_mycourse.getChildren()) {
            node.setOnMouseClicked(event -> {
                // Sử dụng lookup để tìm label của khóa học trong node được nhấp vào
                Label label_itemMyCourse = (Label) node.lookup("#label_itemMyCourse");
                if (label_itemMyCourse != null) {
                    String courseName = label_itemMyCourse.getText();
                    // Tìm khóa học và huấn luyện viên tương ứng
                    for (Course course : courses) {
                        if (course.getName().equals(courseName)) {
                            loadCoachProfile(course);
                            boolean hasWorkouts = loadWorkoutsForCourse(course);
                            pane_coachProfile.setVisible(true);
                            // Chỉ hiện tabPane_schedule nếu có workout hợp lệ
                            tabPane_schedule.setVisible(hasWorkouts);
                        }
                    }
                }
                event.consume();
            });
        }

        // Ẩn pane_coachProfile khi click ra ngoài hbox_mycourse
        pnlMenus.setOnMouseClicked(event -> {
            if (!hbox_mycourse.isHover()) {
                pane_coachProfile.setVisible(false);
                tabPane_schedule.setVisible(false);
            }
        });
    }


    private void loadCoachProfile(Course course) {
        for (Coach coach : coaches) {
            if (coach.getId().equals(course.getCoachId())) {
                Label label_coachName = (Label) pane_coachProfile.lookup("#label_coachName");
                Label label_coachExperience = (Label) pane_coachProfile.lookup("#label_coachExperience");
                Label label_coachBio = (Label) pane_coachProfile.lookup("#label_coachBio");

                label_coachName.setText(coach.getName());
                label_coachExperience.setText(String.valueOf(coach.getExperience()));
                label_coachBio.setText(coach.getBio());

                String link = coach.getLinkImg();
                Image image = new Image(getClass().getResource(link).toString());
                circleAva.setFill(new ImagePattern(image));
                pane_coachProfile.setVisible(true);
            }
        }
    }

    private boolean loadWorkoutsForCourse(Course course) {
        // Lọc danh sách workout theo course ID
        currentWorkouts.clear();
        for (Workout workout : workouts) {
            if (workout.getCourseId().equals(course.getId())) {
                currentWorkouts.add(workout);
            }
        }

        // Hiển thị workout đầu tiên nếu có
        workoutIndex = 0;
        if (!currentWorkouts.isEmpty()) {
            displayWorkout(currentWorkouts.get(workoutIndex));
            // Đặt sự kiện cho nút "next workout"
            btn_next_workout.setOnMouseClicked(event -> {
                // Tăng chỉ số workoutIndex và hiển thị workout tiếp theo
                workoutIndex = (workoutIndex + 1) % currentWorkouts.size();
                displayWorkout(currentWorkouts.get(workoutIndex));
            });
            return true;  // Có workout hợp lệ
        }

        return false;  // Không có workout hợp lệ
    }


    private void displayWorkout(Workout workout) {
        Label name_workout = (Label) tabPane_schedule.lookup("#name_workout");
        Text duration_workout = (Text) tabPane_schedule.lookup("#duration_workout");
        TextArea description_workout = (TextArea) tabPane_schedule.lookup("#description_workout");

        name_workout.setText(workout.getWorkout_name());
        duration_workout.setText(String.valueOf(workout.getDuration_minustes()));
        description_workout.setText(workout.getdescription());
        description_workout.setWrapText(true);
        description_workout.setEditable(false);
    }
}
