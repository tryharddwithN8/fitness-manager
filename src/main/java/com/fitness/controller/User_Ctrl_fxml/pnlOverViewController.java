package com.fitness.controller.User_Ctrl_fxml;

import com.fitness.model.fitness.Course;
import com.fitness.model.person.Coach;
import com.fitness.repositories.CoachRepositoryImpl;
import com.fitness.repositories.CourseRepositoryImpl;
import com.fitness.repositories.OrderRepository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.fitness.utility.UtilityAlert.showConfirm;

public class pnlOverViewController {
    @FXML
    private VBox pnItems = null;
    @FXML
    private VBox Vbox_ad = null;
    @FXML private Button btn_back_ad;
    @FXML private Button btn_next_ad,btn_exit_modal,btn_book_pt;
    @FXML private Pane modal_pnlOverview;
    @FXML private HBox hbox_modal;
    private ADController adController;
    @FXML private Label label_itemOverviewName;
    private final String[] adFiles = {
            "/fxml/User_fxml/AD/ad1.fxml",
            "/fxml/User_fxml/AD/ad2.fxml",
            "/fxml/User_fxml/AD/ad3.fxml"
    };
    private CourseRepositoryImpl courseRepoImpl = new CourseRepositoryImpl();
    private CoachRepositoryImpl coachRepository=new CoachRepositoryImpl();
    private pnlOrdersController pnlOrdersController = new pnlOrdersController();
    private OrderRepository orderRepository=new OrderRepository();
    List<Coach> coaches=coachRepository.getListCoach();
    List<Course> courses = courseRepoImpl.getAll();
    private ExecutorService executor = Executors.newFixedThreadPool(4); // 4 thread
    public pnlOverViewController() throws SQLException {
    }

    public void setPnlOrdersController(pnlOrdersController pnlOrdersController) {
        this.pnlOrdersController = pnlOrdersController;
    }


    public void advertiseController(){
        adController = new ADController(Vbox_ad, adFiles);
        adController.loadAdWithAnimation();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            adController.loadAdWithAnimation();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        try {
            Pane adPane = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/AD/ad1.fxml"));
            Vbox_ad.getChildren().setAll(adPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_back_ad.setOnAction(event -> showPreviousAd());
        btn_next_ad.setOnAction(event -> showNextAd());
    }

    public void loadItem1() {
        List<Node> nodes = new ArrayList<>();
        int itemsPerRow = 3;
        HBox currentHBox = null;
        int i = 0;
        Set<String> addedCourseNames = new HashSet<>();

        for (Course course : courses) {
            if (addedCourseNames.contains(course.getName())) {
                continue;
            }
            try {
                Node node = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/Item/itemOverView.fxml"));
                nodes.add(node);
                if (i % itemsPerRow == 0) {
                    currentHBox = new HBox();
                    currentHBox.setSpacing(10);
                    pnItems.getChildren().add(currentHBox);
                }
                if (currentHBox != null) {
                    currentHBox.getChildren().add(node);

                    Label label_itemOverview = (Label) node.lookup("#label_itemOverview");
                    Text price1 = (Text) node.lookup("#price1");
                    Text price2 = (Text) node.lookup("#price2");
                    Button itemOverview_button = (Button) node.lookup("#itemOverview_button");

                    String price = String.valueOf(course.getFee());
                    String price2Value = String.valueOf(course.getDiscount());
                    label_itemOverview.setText(course.getName());
                    price1.setText(price);
                    price2.setText(price2Value);

                    itemOverview_button.setOnAction(event -> {
                        loadModalItem(course.getName());
                        modal_pnlOverview.setVisible(true);
                    });

                    addedCourseNames.add(course.getName());
                }
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        modal_pnlOverview.setVisible(false);
    }

    public void loadModalItem( String courseName) {
        hbox_modal.getChildren().clear();
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                for (Coach coach : coaches) {
                    if (coach.getId().equals(course.getCoachId())) {
                        try {
                            HBox hboxModal = (HBox) modal_pnlOverview.lookup("#hbox_modal");
                            Pane modalItemPane = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/Item/modal_item.fxml"));
                            hboxModal.getChildren().add(modalItemPane);

                            if (modalItemPane != null) {
                                Text coachName_modal_overview = (Text) modalItemPane.lookup("#coachName_modal_overview");
                                TextArea coachExper_modal_overview = (TextArea) modalItemPane.lookup("#coachExper_modal_overview");
                                Text coachBio_modal_overview = (Text) modalItemPane.lookup("#coachBio_modal_overview");
                                Button btn_book_pt = (Button) modalItemPane.lookup("#btn_book_pt");
                                coachName_modal_overview.setText(coach.getFullName());
                                coachExper_modal_overview.setText(coach.getExperience());
                                coachBio_modal_overview.setText(coach.getBio());
                                coachExper_modal_overview.setWrapText(true);
                                coachExper_modal_overview.setEditable(false);
                                btn_book_pt.setOnAction(actionEvent -> {
                                    int k=showConfirm("Confirm","add new order");
                                    if(k==1){
                                        pnlOrdersController.addOrder(course.getId());
                                        orderRepository.saveToDB();
                                        orderRepository.print();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        modal_pnlOverview.setVisible(true);
    }
    private void showPreviousAd() {
        int previousIndex = (adController.currentAdIndex - 1 + adController.adFiles.length) % adController.adFiles.length;
        adController.loadAdByIndex(previousIndex);
    }
    private void showNextAd() {
        int nextIndex = (adController.currentAdIndex + 1) % adController.adFiles.length;
        adController.loadAdByIndex(nextIndex);
    }

    public void handleClick(ActionEvent actionEvent) {
        if(actionEvent.getSource()==btn_exit_modal){
            modal_pnlOverview.setVisible(false);
        }
    }

    public void bookClick(ActionEvent actionEvent) {
        if(actionEvent.getSource()==btn_book_pt){

        }
    }
}
