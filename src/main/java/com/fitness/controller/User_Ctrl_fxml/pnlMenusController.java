package com.fitness.controller.User_Ctrl_fxml;

import com.fitness.controller.LoginController;
import com.fitness.model.fitness.Course;
import com.fitness.model.fitness.Subscription;
import com.fitness.model.person.Coach;
import com.fitness.model.person.User;
import com.fitness.repositories.CoachRepositoryImpl;
import com.fitness.repositories.CourseRepositoryImpl;
import com.fitness.repositories.SubscriptionRepositoryImpl;
import com.fitness.repositories.UserRepositoryImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    LoginController loginController=new LoginController();
    private CourseRepositoryImpl courseRepoImpl = new CourseRepositoryImpl();
    List<Course> courses = courseRepoImpl.getAll();
    private SubscriptionRepositoryImpl subscriptionRepository=new SubscriptionRepositoryImpl();
    List<Subscription> subscriptions=subscriptionRepository.getAll();
    private UserRepositoryImpl userRepository=new UserRepositoryImpl();
    List<User> users=userRepository.getAll();
    private CoachRepositoryImpl coachRepository=new CoachRepositoryImpl();
    List<Coach> coaches=coachRepository.getAll();
    public pnlMenusController() throws SQLException {
    }
    public void loadUserName(){
        label_Username_myCourse.setText(nameUser);
    }

    //    public void loadMyCourses() {
//        List<Node> nodes = new ArrayList<>();
//        for(Course course:courses){
//            try{
//                Node node=FXMLLoader.load(getClass().getResource("/fxml/User_fxml/Item/itemMyCourse.fxml"));
//                node.setOnMouseClicked(event -> {
//                    pane_coachProfile.setVisible(true);
//                    event.consume();
//                });
//                nodes.add(node);
//                hbox_mycourse.getChildren().add(node);
//                Label label_itemMyCourse=(Label) node.lookup("#label_itemMyCourse");
//                TextArea text_descriptionMycourse=(TextArea) node.lookup("#text_descriptionMycourse");
//                label_itemMyCourse.setText(course.getName());
//                text_descriptionMycourse.setText(course.getDescription());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
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
                                event.consume();
                            });
                            nodes.add(node);
                            hbox_mycourse.getChildren().add(node);
                            Label label_itemMyCourse = (Label) node.lookup("#label_itemMyCourse");
                            TextArea text_descriptionMycourse = (TextArea) node.lookup("#text_descriptionMycourse");
                            label_itemMyCourse.setText(course.getName());
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
    public void loadCoachAva(){
        Image image = new Image(getClass().getResource("/img/coach1.png").toString());
        circleAva.setFill(new ImagePattern(image));
        pane_coachProfile.setVisible(false);
    }

    public void coachProifileManager() {
        loadMyCourses(); // Tải danh sách các khóa học
        loadCoachAva();  // Tải hình đại diện của coach

        // Duyệt qua các node con của hbox_mycourse
        for (Node node : hbox_mycourse.getChildren()) {
            // Lắng nghe sự kiện khi người dùng nhấp vào từng khóa học
            node.setOnMouseClicked(event -> {
                // Sử dụng lookup để tìm label của khóa học trong node được nhấp vào
                Label label_itemMyCourse = (Label) node.lookup("#label_itemMyCourse");

                if (label_itemMyCourse != null) {
                    String courseName = label_itemMyCourse.getText();

                    // Tìm khóa học và huấn luyện viên tương ứng
                    for (Course course : courses) {
                        if (course.getName().equals(courseName)) {
                            for (Coach coach : coaches) {
                                if (coach.getId().equals(course.getCoachId())) {
                                    // Hiển thị thông tin huấn luyện viên khi nhấp vào
                                    Label label_coachName = (Label) pane_coachProfile.lookup("#label_coachName");
                                    Label label_coachExperience = (Label) pane_coachProfile.lookup("#label_coachExperience");
                                    Label label_coachBio = (Label) pane_coachProfile.lookup("#label_coachBio");

                                    label_coachName.setText(coach.getName());
                                    label_coachExperience.setText(String.valueOf(coach.getExperience()));
                                    label_coachBio.setText(coach.getBio());
                                    String link=coach.getLinkImg();
                                    Image image = new Image(getClass().getResource(link).toString());
                                    circleAva.setFill(new ImagePattern(image));
                                    pane_coachProfile.setVisible(true);  // Hiển thị pane chứa thông tin huấn luyện viên
                                }
                            }
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
            }
        });
    }


}
