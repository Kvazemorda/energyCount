package Forms;

import Forms.Object.Capacity.UnitCount.JournalCount.JournalAddNewCountOrValue;
import Forms.Object.CapacityConnectToObject;
import Forms.Object.ObjectAddNewOnPlace;
import Forms.Owner.ContractAddNew;
import Service.HibernateSessionFactory;
import Service.UsersDAOImp;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UsersEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс из которого созадется основное окно приложения.
 * @link MainForm#createDatePicker() - вверху создается календарь
 * @link MainForm#createTreeView() - дерево доступных модулей. Модули открываются во вкладках в центре
 * @link MainForm#createTabPane() - вкладки модулей
 * @autor Khokhlov Il'ya
 * ilyavanavara@hotmail.com, +7-923-317-8678
 * @version 1.0
 */
public class MainForm extends Application{
    public static Date currentDate;
    public static Session session;
    private Scene scene;
    private BorderPane borderPane;
    public TabPane tabPane;
    private Tab tabReports, tabReportsCTVS, tabAddNewObject, tabConnectObjectByResource, tabAddNewContract;
    private MenuBar menuBar;
    private String reports, reportCTVS, addNewObject, connectObjectToResource, addNewContract;
    public static JournalAddNewCountOrValue journalAddNewCountOrValue;
    private static TreeItem<String> treeReports, treeReportCTVS, treeItemAddNewObject, treeItemConnectObjByResource, treeAddNewContract;
    private Map<TreeItem<String>, Tab> mapTabs;
    public static DatePicker datePicker;

    public static void main(String[] args) {
        System.out.println("start Hibernate");
        MainForm.session = HibernateSessionFactory.getSessionFactory().openSession();
        UsersDAOImp usersDAOImp = new UsersDAOImp();
        UsersEntity usersEntity;
        usersEntity = usersDAOImp.getCurrentUser(System.getProperty("user.name"));
        if (usersEntity != null){
            launch(args);
            MainForm.session.close();
            HibernateSessionFactory.shutdown();
        }

        String s = "2. Решить как сделать общий баланс за текущий месяц \n" +
                "3. Решить вопрос по сведению баланса в день, по РВСам и по дням когда добавляются по НПР \n" +
                "4. Продумать логику по закрытию периода на перед или случаев корректировки показаний \n" +
                "4.2.1 Если за текущую дату период счтается закрытым, то исправлять показание запрещено \n" +
                "4.2.2 Посчитать по среднему: Если закрываем показание на перед, добавить форму расчета по среднему. \n" +
                "Указываешь дату по какую необходимо посчитать и нажимаешь расчет. Вносятся средние показания за текущий месяц \n" +
                "кроме налива воды контрагентам(по наливу воды указывается 0) \n" +
                "3. Добавить кнопку на сохранение показаний всех узлов учета и проектных \n " +
                "4. Добавить ДАО на сохранение данных в базе по показаниям узлов учета \n " +
                "5. Добавить базу договоров и контрагентов \n" +
                "6. Добавить привязку объекта к договору \n" +
                "7. Добавить привязку налива воды к договору \n" +
                "5. Сделать шаблон формирования справок за период \n" +
                "6. Сделать шаблон формирования актов выполненных работ за период \n" +
                "7. Сделать шаблон справки для формирования на определенный период \n" +
                "8. Сделать отправку данных через почту, для сохранения данных у всех пользователей \n " +
                "9. Собрать всю базу данных узлов и объектов. \n " +
                "10. Внедрить прогу :-)";
        System.out.println(s);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        root.setAutoSizeChildren(true);
        root.getChildren().add(createBorderPane());
        scene = new Scene(root, 980,800);
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        menuBar.prefWidthProperty().bind(scene.widthProperty());
        primaryStage.setTitle("Управление энергетики, отчеты ЦТВС");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Верхнее меню. Не задействовано
     * @return MenuBar
     */
    private MenuBar createTopMenu(){
        menuBar = new MenuBar();
        Menu menu = new Menu("About");
        menuBar.getMenus().add(menu);
        return menuBar;
    }

    /**
     * Основной бордер в нем располагаются все боксы
     * @return BorderPane
     */
    private BorderPane createBorderPane(){
        HBox hBox = new HBox();
        HBox hBoxMenu = new HBox();
        hBoxMenu.getChildren().add(createDatePicker());
        hBoxMenu.getChildren().add(createTopMenu());
        hBox.getChildren().add(createTreeView());
        borderPane = new BorderPane();
        borderPane.setTop(hBoxMenu);
        borderPane.setLeft(hBox);
        borderPane.setCenter(tabPane);
        return borderPane;
    }

    /**
     * Дерево всех доступных меню с сылкой на открытие меню во вкладке
     * @return TreeView
     */
    private TreeView<TreeItem> createTreeView(){
        createTabPane();
        treeReports = new TreeItem(tabReports.getText());
        treeReportCTVS = new TreeItem(tabReportsCTVS.getText());
        treeItemAddNewObject = new TreeItem(tabAddNewObject.getText());
        treeItemConnectObjByResource = new TreeItem(tabConnectObjectByResource.getText());
        treeAddNewContract = new TreeItem(tabAddNewContract.getText());
        mapTabs = new HashMap<>();
        mapTabs.put(treeReports, tabReports);
        mapTabs.put(treeReportCTVS, tabReportsCTVS);
        mapTabs.put(treeItemAddNewObject, tabAddNewObject);
        mapTabs.put(treeItemConnectObjByResource, tabConnectObjectByResource);
        mapTabs.put(treeAddNewContract, tabAddNewContract);
        treeReports.setExpanded(true);
        treeReports.getChildren().add(0,treeReportCTVS);
        treeReports.getChildren().add(1,treeItemAddNewObject);
        treeReports.getChildren().add(2,treeItemConnectObjByResource);
        treeReports.getChildren().add(3,treeAddNewContract);
        final TreeView<TreeItem> treeView = new TreeView(treeReports);
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<TreeItem>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<TreeItem>> observable, TreeItem<TreeItem> oldValue, TreeItem<TreeItem> newValue) {
                try {
                    if (!tabPane.getTabs().contains(mapTabs.get(newValue))) {
                        tabPane.getTabs().add(mapTabs.get(newValue));
                        tabPane.getSelectionModel().select(mapTabs.get(newValue));
                    } else {
                        tabPane.getSelectionModel().select(mapTabs.get(newValue));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        return treeView;
    }

    /**
     * Панель вкладок всех меню:
     * @see JournalAddNewCountOrValue#createPane() - создается вкладка "Отчет ЦТВС" с формой ввода показаний узлов учет
     * и в случае отсутсвия узлов учета вводятся показания часов работы оборудования (считается объем по наработке)
     * @see ObjectAddNewOnPlace#createBorderPane() - создается вкладка "Создать новый объект" в которой добавляются
     * новые объекты на площадку с обязательным подключением к ресурсу.
     * @see CapacityConnectToObject#createCapacityConnectToObject() - создается вкладка "Подключить/Откл. ресурс к объекту",
     * где можно добавить новую мощность к объекту или удалить старую.
     * @return TabPane
     */
    private TabPane createTabPane() {
        reportCTVS = "Отчет ЦТВС";
        reports = "Отчеты";
        addNewObject = "Создать новый объект";
        connectObjectToResource = "Подкл./откл. ресурс к объекту";
        addNewContract = "Добавить новый договор";
        tabPane = new TabPane();
        tabReports = new Tab(reports);
        tabReportsCTVS = new Tab(reportCTVS);
        tabConnectObjectByResource = new Tab(connectObjectToResource);
        tabAddNewObject = new Tab(addNewObject);
        tabAddNewContract = new Tab(addNewContract);
        journalAddNewCountOrValue = new JournalAddNewCountOrValue();
        ObjectAddNewOnPlace objectAddNewOnPlace = new ObjectAddNewOnPlace();
        CapacityConnectToObject capacityConnectToObject = new CapacityConnectToObject();
        tabReports.setContent(new Label("Здесь будут отчеты"));
        tabReportsCTVS.setContent(journalAddNewCountOrValue.createPane());
        tabAddNewObject.setContent(objectAddNewOnPlace.createBorderPane());
        tabConnectObjectByResource.setContent(capacityConnectToObject.createCapacityConnectToObject());
        ContractAddNew contractAddNew = new ContractAddNew();
        tabAddNewContract.setContent(contractAddNew.getBorderPaneBasicForm());
        return tabPane;
    }

    /**
     * Календарь. Начинается  с сегодняшнего дня и любые действия с объектами записываются датой указанной в календаре.
     * @return DatePecker
     */
    public static DatePicker createDatePicker(){
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setMinWidth(248);
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        currentDate = Date.from(instant);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate localDate1 = datePicker.getValue();
            Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
            currentDate = Date.from(instant1);
        });
        return datePicker;
    }

    public static Date getCurrentDate() {
        return currentDate;
    }
}