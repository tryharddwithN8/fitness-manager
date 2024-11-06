package com.fitness.controller.User_Ctrl_fxml;

import com.fitness.config.ConnectionDB;
import com.fitness.model.fitness.Course;
import com.fitness.model.fitness.CourseOrder;
import com.fitness.model.person.Coach;
import com.fitness.repositories.CoachRepositoryImpl;
import com.fitness.repositories.CourseRepositoryImpl;
import com.fitness.repositories.OrderRepository;
import com.fitness.repositories.SubscriptionRepositoryImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.fitness.utility.UtilityAlert.showConfirm;

public class pnlOrdersController {
    @FXML private  VBox vbox_order=null;
    @FXML private Pane pnlOrders;
    private CourseRepositoryImpl courseRepoImpl = new CourseRepositoryImpl();
    private CoachRepositoryImpl coachRepository= new CoachRepositoryImpl();
    private OrderRepository orderRepository=new OrderRepository();
    private SubscriptionRepositoryImpl subscriptionRepository=new SubscriptionRepositoryImpl();
    private pnlMenusController pnlMenusController;

    private pnlKhaoSatController pnlKhaoSatController = new pnlKhaoSatController();

    List<CourseOrder> listOrderDB=orderRepository.getAllOrder();
    List<Coach> coaches=coachRepository.getListCoach();
    List<Course> courses = courseRepoImpl.getAll();
    public static List<Course> orderList=new ArrayList<>();
    public static List<Course> subcriptions=new ArrayList<>();
    public pnlOrdersController() throws SQLException {
    }
    public Connection getConnection() throws SQLException {
        return ConnectionDB.getConnection();    
    }
    public void setPnlMenusController(pnlMenusController pnlMenusController) {
        this.pnlMenusController = pnlMenusController;
    }

    public void addOrder(String idCourse) {
        boolean courseExists = false;
        for (Course course : orderList) {
            if (course.getId().equals(idCourse)) {
                courseExists = true;
                break;
            }
        }
        if (courseExists) {
            System.out.println("Khóa học đã tồn tại trong danh sách: " + idCourse);
            return;
        }
        for (Course course : courses) {
            if (course.getId().equals(idCourse)) {
                for (Coach coach : coaches) {
                    if (course.getCoachId().equals(coach.getId())) {
                        try {
                            Pane orderPane = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/Item/orderItem.fxml"));
                            vbox_order.getChildren().add(orderPane);
                            Label nameCourse_order = (Label) orderPane.lookup("#nameCourse_order");
                            Text costCourse_order = (Text) orderPane.lookup("#costCourse_order");
                            Text discountCourse_order = (Text) orderPane.lookup("#discountCourse_order");
                            Label nameCoach_order = (Label) orderPane.lookup("#nameCoach_order");
                            Button btn_delete_order = (Button) orderPane.lookup("#btn_delete_order");
                            Button btn_buy_order=(Button)orderPane.lookup("#btn_buy_order");
                            nameCourse_order.setText(course.getName());
                            costCourse_order.setText(String.valueOf(course.getFee()));
                            discountCourse_order.setText(String.valueOf(course.getDiscount()));
                            nameCoach_order.setText(course.getCoachName());
                            btn_buy_order.setOnAction(event ->{
                                int k=showConfirm("Confirm","Buy order ?");
                                if(k==1){
                                    subcriptions.add(course);
                                    subscriptionRepository.saveSubDB();
                                    pnlKhaoSatController.showSurvey();
                                }
                            } );
                            btn_delete_order.setOnAction(event -> {
                                int k=showConfirm("Confirm","Delete order ?");
                                if(k==1){
                                    orderList.remove(course);
                                    vbox_order.getChildren().remove(orderPane);
                                    orderRepository.deleteOrder();
                                    orderRepository.saveToDB();
                                }
                                                            });
                            orderList.add(course);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void loadOrder() {
        for (CourseOrder courseOrder : listOrderDB) {
            try {
                Pane orderPane = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/Item/orderItem.fxml"));
                vbox_order.getChildren().add(orderPane);
                Label nameCourse_order = (Label) orderPane.lookup("#nameCourse_order");
                Text costCourse_order = (Text) orderPane.lookup("#costCourse_order");
                Text discountCourse_order = (Text) orderPane.lookup("#discountCourse_order");
                Label nameCoach_order = (Label) orderPane.lookup("#nameCoach_order");
                Button btn_delete_order = (Button) orderPane.lookup("#btn_delete_order");
                Button btn_buy_order=(Button)orderPane.lookup("#btn_buy_order");
                nameCourse_order.setText(courseOrder.getName());
                costCourse_order.setText(String.valueOf(courseOrder.getFee()));
                discountCourse_order.setText(String.valueOf(courseOrder.getDiscount()));
                nameCoach_order.setText(courseOrder.getCoachName());
                btn_delete_order.setOnAction(event -> {
                    int k=showConfirm("Confirm","Delete order ?");
                    if(k==1){
                        vbox_order.getChildren().remove(orderPane);
                        Course courseToRemove = null;
                        for (Course course : orderList) {
                            if (course.getId().equals(courseOrder.getId())) {
                                courseToRemove = course;
                                break;
                            }
                        }
                        if (courseToRemove != null) {
                            orderList.remove(courseToRemove);
                        }
                        orderRepository.deleteOrder();
                        orderRepository.saveToDB();
                        orderRepository.print();
                    }
                });
                btn_buy_order.setOnAction(event ->{
                    int k=showConfirm("Confirm","Buy order ?");
                    if(k==1){
                        subcriptions.add(courseOrder);
                        subscriptionRepository.saveSubDB();

                    }
                } );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}





